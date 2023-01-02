package net.firstpartners.core.drools;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.drools.drl.parser.DroolsParserException;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.logger.KnowledgeRuntimeLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.Config;
import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.loader.RuleBuilder;
import net.firstpartners.core.log.IStatusUpdate;
import net.firstpartners.data.Cell;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeList;

/**
 * Call JBoss Drools (Rules Engine) passing in Document data as Java Objects
 *
 * This class uses an IDocumentStrategy Object to handle different types
 *
 * @author paulf
 * @version $Id: $Id
 */
public class RuleRunner implements IRunner {

	// Application Config - if passed in
	private Config appConfig;

	// Handle to the Strategy Class for specific incoming document (Excel, Word etc
	// tasks)
	// Setup by RunnerFactory
	private IDocumentInStrategy inputStrategy = null;

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// Handle to the strategy Class to write out the document
	// Setup by RunnerFactory
	private IDocumentOutStrategy outputStrategy = null;

	/**
	 * Construct a new RuleRunner.
	 *
	 * @see RunnerFactory in this package which we use to build a properly
	 *      constructed instance of this class
	 * @param documentStrategy a {@link net.firstpartners.core.IDocumentInStrategy}
	 *                         object
	 * @param outputStrategy   a {@link net.firstpartners.core.IDocumentOutStrategy}
	 *                         object
	 * @param appConfig        a {@link net.firstpartners.core.Config} object
	 */
	protected RuleRunner(IDocumentInStrategy documentStrategy, IDocumentOutStrategy outputStrategy, Config appConfig) {
		this.inputStrategy = documentStrategy;
		this.outputStrategy = outputStrategy;
		this.appConfig = appConfig;
	}

	/**
	 * Call the rule Engine - data input / output has already been set during the
	 * creation of this class
	 *
	 * @return our Model with all the information so we can display back to the user
	 * @throws java.lang.RPException
	 * @param ruleModel a {@link net.firstpartners.core.RedModel} object
	 */
	public RedModel callRules(RedModel ruleModel)
			throws RPException {

		// Convert the cell and log if we have a handle
		ruleModel.addUIInfoMessage("Opening Input :" + this.inputStrategy.getInputName());
		RangeList ranges = inputStrategy.getJavaBeansFromSource();

		// Set the Modified flag on the cells
		if (ranges != null) {
			ranges.cascadeResetIsModifiedFlag();
		}

		ruleModel.setPreRulesSnapShot(ranges);
		ruleModel.setUIProgressStatus(10);

		// Add the document contents as facts into the working Memory
		ruleModel.addUIInfoMessage("Adding Excel Cells as facts into Rule Engine Memory");

		if (ranges != null) {
			ruleModel.addFacts(ranges.getAllCellsInAllRanges());
		} else {
			assert ranges != null : "No Data (Ranges =null) was passed in, this is unlikely to be what you want";
		}

		ruleModel.setUIProgressStatus(30);

		// Load and fire our rules files against the data
		Collection<Cell> newFacts = runStatelessRules(ruleModel);

		// update the progress bar
		ruleModel.setUIProgressStatus(60);

		// Make a note of any new facts added
		ruleModel.addUIInfoMessage("Collecting New Cells and put them into Excel");
		Range newRange = new Range("New Facts");
		newRange.put(newFacts);
		ranges.add(newRange);
		ruleModel.setUIProgressStatus(80);

		// update a copy of the original document (to be saved as copy) with the result
		// of our rules
		log.debug("RunRules - object " + inputStrategy.getOriginalDocument());
		outputStrategy.setUpdates(inputStrategy.getOriginalDocument(), ranges);

		ruleModel.addUIInfoMessage("Write to Output file:" + outputStrategy.getOutputDestination());
		ruleModel.setUIProgressStatus(90);

		// update our post rules snapshot
		ruleModel.setPostRulesSnapShot(ranges);

		// make sure both get written (to disk?)
		outputStrategy.processOutput();
		ruleModel.setUIProgressStatus(100);

		return ruleModel;

	}

	/**
	 * <p>
	 * Getter for the field <code>appConfig</code>.
	 * </p>
	 *
	 * @return a {@link net.firstpartners.core.Config} object
	 */
	public Config getAppConfig() {
		return appConfig;
	}

	/**
	 * The strategy we use for dealing with incoming documents
	 *
	 * @return the strategy object
	 */
	public IDocumentInStrategy getDocumentInputStrategy() {
		return inputStrategy;
	}

	/**
	 * Handle to the the Strategy Delegate we use for outputting
	 *
	 * @return the strategy object
	 */
	public IDocumentOutStrategy getDocumentOutputStrategy() {
		return outputStrategy;
	}

	/**
	 * <p>
	 * Setter for the field <code>outputStrategy</code>.
	 * </p>
	 *
	 * @param newStrategy a {@link net.firstpartners.core.IDocumentOutStrategy}
	 *                    object
	 */
	public void setDocumentOutputStrategy(IDocumentOutStrategy newStrategy) {
		this.outputStrategy = newStrategy;
	}


	/**
	 * Run the rules
	 *
	 * @param rulesUrl   - array of rule files that we need to load
	 * @param dslFileUrl - optional dsl file name (can be null)
	 * @param facts      - Javabeans to pass to the rule engine
	 * @param globals    - global variables to pass to the rule engine
	 * @param logger     - handle to a logging object
	 * @return
	 * @throws Exception
	 */
	private Collection<Cell> runStatelessRules(RedModel model)
			throws RPException {

		// The most common operation on a rulebase is to create a new rule
		// session; either stateful or stateless.
		log.debug("Creating new rule base");
		KieModule masterRulebase = new RuleBuilder().loadRules(model, appConfig).getKieModule();

		boolean showFullRuleEngineLogs = appConfig.getShowFullRuleEngineLogs();

		log.debug("running stateless rules");
		return runStatelessRules(masterRulebase, model.getFacts(), model.getGlobals(), showFullRuleEngineLogs, model);
	}

	/**
	 * Run Stateless rules using a pre built knowledge base
	 *
	 * @param preBuiltKnowledgeBase
	 * @param facts
	 * @param globals
	 * @param logger
	 * @return any new facts created by the working memory
	 * @throws DroolsParserException
	 * @throws IOException
	 */
	private Collection<Cell> runStatelessRules(KieModule preBuiltKnowledgeBase, Collection<Cell> facts,
			HashMap<String, Cell> globals, boolean logRuleDetails, IStatusUpdate modelAsLogger) throws IOException {

		log.debug("Creating new stateless working memory");

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

	}


}
