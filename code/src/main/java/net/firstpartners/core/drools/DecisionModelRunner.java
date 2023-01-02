package net.firstpartners.core.drools;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNModel;
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

	/**
	 * Run the Decision Model
	 * 
	 * @param model - contaiing inputfile, outputfile and decision model name
	 * @return Collection of <Cell> objects as a response
	 * @throws RPException
	 */
	Collection<Cell> runModel(RedModel model)
			throws RPException {

		// The most common operation on a rulebase is to create a new rule
		// session; either stateful or stateless.
		log.debug("Finding Decision Model new rule base");
		DMNModel modelToRun = getDmnModel("", model.getRuleFileLocation());

		boolean showFullRuleEngineLogs = appConfig.getShowFullRuleEngineLogs();

		log.debug("running Decision Model");
		// return runDecisionModel(masterRulebase, model.getFacts(), model.getGlobals(),
		// showFullRuleEngineLogs, model);

		log.debug("Creating new stateless working memory");
/*
		KieContainer kc = KieServices.Factory.get().newKieContainer(preBuiltKnowledgeBase.getReleaseId());
		StatelessKieSession kSession = kc.newStatelessKieSession();

		// Add the logger
		log.debug("Inserting handle to logger (via global)");
		kSession.setGlobal("log", modelAsLogger);

		log.debug("Checking for globals");
		if (globals != null) {
			for (String o : globals.keySet()) {
				log.debug("Inserting global name: " + o + " value:" + globals.get(o));
				kSession.setGlobal(o, globals.get(o));
			}
		}

		// We need to 'listen' for new cells being created so we can add to our results
		SessionCellListener cellListener = new SessionCellListener();
		kSession.addEventListener(cellListener);

		// setup listeners
		if (logRuleDetails) {
			kSession.addEventListener(new DebugAgendaEventListener());
			kSession.addEventListener(new DebugRuleRuntimeEventListener());
		}

		// Set up a file based audit logger
		KnowledgeRuntimeLoggerFactory.newFileLogger(kSession, "log/WorkItemConsequence.log");

		log.debug("==================== Starting Rules ====================");

		// Fire using the facts
		kSession.execute(facts);

		log.debug("==================== Rules Complete ====================");
		List<Cell> additionalFacts = cellListener.getNewCells(facts);

		return additionalFacts;
 */

 return null;
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

		log.debug("Looking for model:" + decisionModelName);

		// remove .dmn from model name
		if ((decisionModelName != null) && (decisionModelName.toLowerCase().endsWith(".dmn"))) {
			decisionModelName = decisionModelName.substring(0, decisionModelName.length() - 4);
			log.debug("Updated DecisionModelName to:" + decisionModelName);

		}

		// First pass - use KIE to find based on namespace and name
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		DMNRuntime dmnRuntime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);
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

		if (dmnModel == null) {
			throw new RPException("DMNModel not found");
		}

		return dmnModel;

	}

}
