package net.firstpartners.core.drools;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.drools.KnowledgeBase;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.drools.loader.IRuleLoaderStrategy;
import net.firstpartners.core.drools.loader.RuleConfig;
import net.firstpartners.core.log.EmptyLogger;
import net.firstpartners.core.log.GiveLogFeedback;
import net.firstpartners.core.log.IGiveFeedbackToUsers;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.core.log.SpreadSheetLogger;
import net.firstpartners.data.Cell;
import net.firstpartners.data.RangeList;

/**
 * Call JBoss Drools (Rules Engine) passing in Document data as Java Objects
 * 
 * This class uses an IDocumentStrategy Object to handle different types
 */
public class RuleRunner {

	// Handle to the logger
	private static final Logger log = RpLogger.getLogger(RuleRunner.class.getName());

	// Handle to the strategy Class to write out the document
	private IDocumentOutStrategy outputStrategy = null;

	// Handle to loader
	private final IRuleLoaderStrategy ruleLoader;

	// Handle to the Strategy Class for specific incoming document (Excel, Word etc
	// tasks)
	private IDocumentInStrategy inputStrategy = null;

	/**
	 * Construct a new RuleRunner.
	 * 
	 * @see RuleRunnerFactory in this package which we use to build a properly
	 *      constructed instance of this class
	 * @param documentStrategy
	 * @param ruleLoader
	 * @param outputStrategy
	 */
	protected RuleRunner(IDocumentInStrategy documentStrategy, IRuleLoaderStrategy ruleLoader,
			IDocumentOutStrategy outputStrategy) {
		this.ruleLoader = ruleLoader;
		this.inputStrategy = documentStrategy;
		this.outputStrategy = outputStrategy;
	}

	/**
	 * call the rule Engine - data input / output has already been set during the
	 * creation of this class
	 * 
	 * @param nameOfLogSheet
	 * @throws DroolsParserException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidFormatException
	 * @throws CsvRequiredFieldEmptyException
	 * @throws CsvDataTypeMismatchException
	 */
	public void callRules() throws DroolsParserException, IOException, ClassNotFoundException, InvalidFormatException {

		// Add the logger
		// prevent a null pointer in our rules
		ILogger logger = new EmptyLogger();
		IGiveFeedbackToUsers userFeedback = new GiveLogFeedback();

		callRules(userFeedback, logger);
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
	 */
	public void callRules(IGiveFeedbackToUsers userDataDisplay, ILogger userMessages)
			throws DroolsParserException, IOException, ClassNotFoundException, InvalidFormatException {

		//Load our rules first - catches any compilation errors early
		RuleConfig ruleSource = ruleLoader.getRuleSource();
		
		// Create a new Logging object
		outputStrategy.setDocumentLogger(new SpreadSheetLogger());
		if (userDataDisplay != null) {
			userDataDisplay.notifyProgress(20);
			userMessages.info("Opening Input :" + this.inputStrategy.getInputName());
		}

		// Convert the cell and log if we have a handle
		RangeList ranges = inputStrategy.getJavaBeansFromSource();
		ranges.cascadeResetIsModifiedFlag();

		if (userDataDisplay != null) {
			userDataDisplay.notifyProgress(45);
			userDataDisplay.showPreRulesSnapShot(ranges);
			userDataDisplay.notifyProgress(55);
		}

		// Call the overloaded method to actually run the rules and log output if we
		// have a handle
		// Log the cell contents

		// Add the Spreadsheet contents as facts
		
		ruleSource.addFacts(ranges.getAllCellsInAllRanges());
		if (userDataDisplay != null) {
			userDataDisplay.notifyProgress(70);
		}

		// Load and fire our rules files against the data
		runStatelessRules(ruleSource, userMessages);

		if (userDataDisplay != null) {
			userDataDisplay.showPostRulesSnapShot(ranges);
			userDataDisplay.notifyProgress(80);
		}

		// update a copy of the original document (to be saved as copy) with the result
		// of our rules
		log.debug("RunRules - object " + inputStrategy.getOriginalDocument());
		outputStrategy.setUpdates(inputStrategy.getOriginalDocument(), ranges);

		if (userDataDisplay != null) {
			userDataDisplay.notifyProgress(90);
		}

		// Close our input work book
		if (userDataDisplay != null) {
			userDataDisplay.notifyProgress(100);
		}

		// Process our output
		userMessages.info("Write to Output file:" + outputStrategy.getOutputDestination());

		// update the document (e.g. excel spreadsheet) with our log file as appropriate
		outputStrategy.flush(userMessages);

		// make sure both get written (to disk?)
		outputStrategy.processOutput();

	}

	/**
	 * Handle to the the Strategy Delegate we use for outputting
	 * 
	 * @return the strategy object
	 */
	public IDocumentOutStrategy getDocumenttOutputStrategy() {
		return outputStrategy;
	}

	/**
	 * The strategy we use for dealing with incoming documents
	 * 
	 * @return the strategy object
	 */
	public IDocumentInStrategy getDocumentInputStrategy() {
		return inputStrategy;
	}

	public IRuleLoaderStrategy getRuleLoader() {
		return ruleLoader;
	}

	/**
	 * Get a Stateful Session for a pre built knowledgebase
	 *
	 * @param preBuiltKnowledgeBase
	 * @param globals
	 * @param logger
	 * @throws DroolsParserException
	 * @throws IOException
	 */
	StatefulKnowledgeSession getStatefulSession(KnowledgeBase preBuiltKnowledgeBase, HashMap<String, Cell> globals,
			ILogger logger) throws DroolsParserException, IOException {
		// Create a new stateful session
		StatefulKnowledgeSession workingMemory = preBuiltKnowledgeBase.newStatefulKnowledgeSession();

		log.debug("Inserting handle to logger (via global) type:" + logger.getClass());
		workingMemory.setGlobal("log", logger);

		for (String o : globals.keySet()) {
			log.debug("Inserting global name: " + o + " value:" + globals.get(o));
			workingMemory.setGlobal(o, globals.get(o));
		}

		return workingMemory;

	}

	/**
	 * Run Stateless rules using a prebuilt knowledgebase
	 *
	 * @param preBuiltKnowledgeBase
	 * @param facts
	 * @param globals
	 * @param logger
	 * @throws DroolsParserException
	 * @throws IOException
	 */
	private void runStatelessRules(KnowledgeBase preBuiltKnowledgeBase, Collection<Cell> facts,
			HashMap<String, Cell> globals, ILogger logger) throws DroolsParserException, IOException {

		// Create a new stateless session
		log.debug("Creating new working memory");
		StatelessKnowledgeSession workingMemory = preBuiltKnowledgeBase.newStatelessKnowledgeSession();

		log.debug("Checking for globals");
		if (globals != null) {
			for (String o : globals.keySet()) {
				log.debug("Inserting global name: " + o + " value:" + globals.get(o));
				workingMemory.setGlobal(o, globals.get(o));
			}
		}
		// Add the logger
		log.debug("Inserting handle to logger (via global)");
		workingMemory.setGlobal("log", logger);

		// log.debug("Using facts:" + facts);

		log.debug("==================== Calling Rule Engine ====================");

		// Fire using the facts
		workingMemory.execute(facts);

		log.debug("==================== Rules Complete ====================");

	}

	/**
	 * Run the rules
	 *
	 * @param rulesUrl   - array of rule files that we need to load
	 * @param dslFileUrl - optional dsl file name (can be null)
	 * @param facts      - Javabeans to pass to the rule engine
	 * @param globals    - global variables to pass to the rule engine
	 * @param logger     - handle to a logging object
	 * @throws IOException
	 * @throws DroolsParserException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	private void runStatelessRules(RuleConfig ruleSource, ILogger logger)
			throws DroolsParserException, IOException, ClassNotFoundException {

		// The most common operation on a rulebase is to create a new rule
		// session; either stateful or stateless.
		log.debug("Creating master rule base");
		KnowledgeBase masterRulebase = ruleLoader.loadRules(ruleSource);

		log.debug("running stateless rules");
		runStatelessRules(masterRulebase, ruleSource.getFacts(), ruleSource.getGlobals(), logger);

	}

	public void setOutputStrategy(IDocumentOutStrategy newStrategy) {
		this.outputStrategy = newStrategy;
	}

}