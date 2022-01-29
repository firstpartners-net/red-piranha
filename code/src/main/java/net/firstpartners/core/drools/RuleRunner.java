package net.firstpartners.core.drools;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.drools.compiler.compiler.DroolsParserException;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.logger.KnowledgeRuntimeLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.drools.loader.RuleBuilder;
import net.firstpartners.core.log.IStatusUpdate;
import net.firstpartners.core.log.SpreadSheetStatusUpdate;
import net.firstpartners.data.Cell;
import net.firstpartners.data.Config;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeList;
import net.firstpartners.data.RedModel;

/**
 * Call JBoss Drools (Rules Engine) passing in Document data as Java Objects
 *
 * This class uses an IDocumentStrategy Object to handle different types
 */
public class RuleRunner {

	//Application Config - if passed in
	private Config appConfig = new Config();

	// Handle to the Strategy Class for specific incoming document (Excel, Word etc
	// tasks)
	// Setup by RuleRunnerFactory
	private IDocumentInStrategy inputStrategy = null;

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// Handle to the strategy Class to write out the document
	// Setup by RuleRunnerFactory
	private IDocumentOutStrategy outputStrategy = null;


	/**
	 * Construct a new RuleRunner.
	 *
	 * @see RuleRunnerFactory in this package which we use to build a properly
	 *      constructed instance of this class
	 * @param documentStrategy
	 * @param ruleLoader
	 * @param outputStrategy
	 */
	protected RuleRunner(IDocumentInStrategy documentStrategy, 	IDocumentOutStrategy outputStrategy) {
		this.inputStrategy = documentStrategy;
		this.outputStrategy = outputStrategy;
	}

	/**
	 * Call the rule Engine - data input / output has already been set during the
	 * creation of this class
	 *
	 * @param userDataDisplay - feedback e.g. to GUI
	 * @param userMessages    - to display
	 * @throws DroolsParserException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidFormatException
	 * @throws CsvRequiredFieldEmptyException
	 * @throws CsvDataTypeMismatchException
	 * @return our Model with all the information so we can display back to the user
	 */
	public RedModel callRules(IStatusUpdate userMessages, RedModel ruleModel)
			throws IOException, ClassNotFoundException, InvalidFormatException, DroolsParserException {

		// Create a new Logging object
		outputStrategy.setDocumentLogger(new SpreadSheetStatusUpdate());
		if (userMessages != null) {
			userMessages.notifyProgress(20);
			userMessages.info("Opening Input :" + this.inputStrategy.getInputName());
		}

		// Convert the cell and log if we have a handle
		RangeList ranges = inputStrategy.getJavaBeansFromSource();
		ranges.cascadeResetIsModifiedFlag();

		if (userMessages != null) {
			userMessages.notifyProgress(35);
			userMessages.setPreRulesSnapShot(ranges);
			userMessages.notifyProgress(50);
		}

		// Add the document contents as facts
		ruleModel.addFacts(ranges.getAllCellsInAllRanges());
		if (userMessages != null) {
			userMessages.notifyProgress(60);
		}

		// Load and fire our rules files against the data
		Collection<Cell> newFacts = runStatelessRules(ruleModel, userMessages);

		if (userMessages != null) {
			userMessages.setPostRulesSnapShot(ranges);
			userMessages.notifyProgress(80);
		}

		// Make a note of any new facts added
		Range newRange = new Range("New Facts");
		newRange.put(newFacts);
		ranges.add(newRange);

		// update a copy of the original document (to be saved as copy) with the result
		// of our rules
		log.debug("RunRules - object " + inputStrategy.getOriginalDocument());
		outputStrategy.setUpdates(inputStrategy.getOriginalDocument(), ranges);

		if (userMessages != null) {
			userMessages.notifyProgress(90);
			userMessages.info("Write to Output file:" + outputStrategy.getOutputDestination());

		}

		// update the document (e.g. excel spreadsheet) with our log file as appropriate
		outputStrategy.flush(userMessages);

		// make sure both get written (to disk?)
		outputStrategy.processOutput();

		// signal to GUI we are complete
		if (userMessages != null) {
			userMessages.notifyProgress(100);
		}

		return ruleModel;

	}

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
			HashMap<String, Cell> globals, IStatusUpdate logger, boolean logRuleDetails) throws IOException {

		log.debug("Creating new stateless working memory");

		KieContainer kc = KieServices.Factory.get().newKieContainer(preBuiltKnowledgeBase.getReleaseId());
		StatelessKieSession kSession = kc.newStatelessKieSession();

		// Add the logger
		log.debug("Inserting handle to logger (via global)");
		kSession.setGlobal("log", logger);

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
		if(logRuleDetails) {
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

	/**
	 * Run the rules
	 *
	 * @param rulesUrl   - array of rule files that we need to load
	 * @param dslFileUrl - optional dsl file name (can be null)
	 * @param facts      - Javabeans to pass to the rule engine
	 * @param globals    - global variables to pass to the rule engine
	 * @param logger     - handle to a logging object
	 * @return
	 * @throws IOException
	 * @throws DroolsParserException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	private Collection<Cell> runStatelessRules(RedModel ruleSource, IStatusUpdate logger)
			throws IOException, ClassNotFoundException, DroolsParserException {

		// The most common operation on a rulebase is to create a new rule
		// session; either stateful or stateless.
		log.debug("Creating new rule base");
		KieModule masterRulebase = new RuleBuilder().loadRules(ruleSource,appConfig).getKieModule();
		
		boolean showFullRuleEngineLogs = appConfig.getShowFullRuleEngineLogs();


		log.debug("running stateless rules");
		return runStatelessRules(masterRulebase, ruleSource.getFacts(), ruleSource.getGlobals(), logger,showFullRuleEngineLogs);
	}

	public void setAppConfig(Config appConfig) {
		this.appConfig = appConfig;
	}


	public void setOutputStrategy(IDocumentOutStrategy newStrategy) {
		this.outputStrategy = newStrategy;
	}



}