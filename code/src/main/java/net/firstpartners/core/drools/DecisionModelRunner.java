package net.firstpartners.core.drools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNMessage;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

import net.firstpartners.core.Config;
import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.data.Cell;

/**
 * Call JBoss Drools (Rules Engine) passing in Document data as Java Objects
 *
 * This class uses an IDocumentStrategy Object to handle different types
 *
 * @author paulf
 * @version $Id: $Id
 */
public class DecisionModelRunner extends AbstractRunner {

	// Handle to KIE resources needed.
	KieServices kieServices;
	KieContainer kieContainer;
	DMNRuntime dmnRuntime;

	/**
	 * Construct a new Runner.
	 *
	 * @see RunnerFactory in this package which we use to build a properly
	 *      constructed instance of this class
	 * @param documentStrategy a {@link net.firstpartners.core.IDocumentInStrategy}
	 *                         object
	 * @param outputStrategy   a {@link net.firstpartners.core.IDocumentOutStrategy}
	 *                         object
	 * @param appConfig        a {@link net.firstpartners.core.Config} object
	 */
	protected DecisionModelRunner(IDocumentInStrategy documentStrategy, IDocumentOutStrategy outputStrategy,
			Config appConfig) {
		this.inputStrategy = documentStrategy;
		this.outputStrategy = outputStrategy;
		this.appConfig = appConfig;

	}

	protected void instantiateKIEifNecessary(){

		if(this.dmnRuntime==null){
			this.kieServices = KieServices.Factory.get();
			this.kieContainer = kieServices.getKieClasspathContainer();
			this.dmnRuntime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);
		}

	}

	/**
	 * Use the KIE Tools to access the Decision Models in this package
	 * 
	 * @param nameSpace         - optional, we will interate over available models
	 * @param decisionModelName
	 * @return DMNRuntime corresponding to this name
	 * @throws RPException if cannot find any models
	 */
	DMNModel getDmnModel(String nameSpace, String decisionModelName) throws RPException {

		//make sure our KIE environment is setup
		instantiateKIEifNecessary();

		log.debug("Looking for model:" + decisionModelName);

		// remove .dmn from model name
		if ((decisionModelName != null) && (decisionModelName.toLowerCase().endsWith(".dmn"))) {
			decisionModelName = decisionModelName.substring(0, decisionModelName.length() - 4);
			log.debug("Updated DecisionModelName to:" + decisionModelName);

		}

		// First pass - use KIE to find based on namespace and name
		DMNModel dmnModel = dmnRuntime.getModel(nameSpace, decisionModelName);

		// check if this was successful
		if (dmnModel != null) {
			return dmnModel;
		} else {
			log.debug("Did Not match using namespace - interating over models ");
		}

		// Debugging code to see the models available to us
		List<DMNModel> modelList = dmnRuntime.getModels();
		Iterator<DMNModel> modelLoop = modelList.iterator();
		while (modelLoop.hasNext()) {

			DMNModel thisModel = modelLoop.next();
			String name = thisModel.getName().toLowerCase();

			log.debug("Testing match against Name:" + thisModel.getName());
			// log.debug("NameSpace:" + thisModel.getNamespace());

			if (name.equalsIgnoreCase(decisionModelName)) {
				log.debug("Matched");
				return thisModel;
			}

		}

		//Check that we have a model with that name
		assert dmnModel != null : "Unable to find any DNMModel with that Name";
		
		return dmnModel;

	}

	/**
	 * Run the Decision Model
	 * 
	 * @param model - containing inputfile, outputfile and decision model name
	 * @return Collection of <Cell> objects as a response
	 * @throws RPException
	 */
	Collection<Cell> runModel(RedModel model)
			throws RPException {

		//make sure our KIE environment is setup
		instantiateKIEifNecessary();

		//our results objects
		ArrayList<Cell> outputCells = new ArrayList<Cell>();

		// Find the Decision Model
		log.debug("Finding Decision Model new rule base");
		DMNModel modelToRun = getDmnModel("", model.getRuleFileLocation());

		// Create the runtime using the KIE system we initialised earlier
		log.debug("running Decision Context ");
		DMNContext dmnContext = this.dmnRuntime.newContext();

		// set cells into context
		List<Cell> convertList = model.getFactsAsList();
		log.debug("Number of Cells passed in:" + convertList.size());
		dmnContext.set("InputCells", convertList);

		// evaluate the runtime
		DMNResult dmnResult = this.dmnRuntime.evaluateAll(modelToRun, dmnContext);
		for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {

			log.debug("Status:" + dr.getEvaluationStatus());
			log.debug("Result:" + dr.getDecisionName() + " : " + dr.getResult());
			outputCells.addAll(convertDecisionResultToCells(dr.getResult()));

		}

		// Loop through messages and raise exception if needed
		boolean warningFlag = false;
		for (DMNMessage dm : dmnResult.getMessages()) {
			log.warn("Message:" + dm.getText());
			warningFlag = true;
		}
		if (warningFlag) {
			throw new RPException("Errors when executing Decision Model - please check logs for details");
		}

		return outputCells;
	}

	/**
	 * Convert the Result of the Decision Model into Cell Objects
	 * 
	 * @param inputObject
	 * @return
	 */
	Collection<Cell> convertDecisionResultToCells(Object inputObject) {

		ArrayList<Cell> cellResult = new ArrayList<Cell>();

		// Check for a list
		if (inputObject instanceof List) {

			List loopList = (List)inputObject;

			for (int i = 0; i < loopList.size(); i++) {
				cellResult.addAll(convertDecisionResultToCells(loopList.get(i)));
			}
			return cellResult;
		}

		if (inputObject != null) {
			log.debug("Converting Decision Result of Type:" + inputObject.getClass());

			if (inputObject instanceof Cell) {
				cellResult.add((Cell) inputObject);
			} else {

				// generic conversation based on cell toString
				Cell tmpCell = new Cell("", inputObject.toString());
				cellResult.add(tmpCell);
			}

		} else {
			log.warn("convertDecisionResultToCells called with a null value - unlikely to be what you want");
		}

		return cellResult;
	}

}
