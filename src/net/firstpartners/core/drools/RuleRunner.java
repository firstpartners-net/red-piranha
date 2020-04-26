package net.firstpartners.core.drools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.drools.KnowledgeBase;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.drools.loader.IRuleLoader;
import net.firstpartners.core.drools.loader.RuleSource;
import net.firstpartners.core.log.IGiveFeedbackToUsers;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.core.log.SpreadSheetLogger;
import net.firstpartners.core.spreadsheet.ExcelOutputStrategy;
import net.firstpartners.data.Cell;
import net.firstpartners.data.RangeHolder;

/**
 * Call JBoss Drools (Rules Engine) passing in Document data as Java Objects
 * 
 * This class uses an IDocumentStrategy Object to handle different types
 */
public class RuleRunner {

	// Handle to the logger
	private static final Logger log = RpLogger.getLogger(RuleRunner.class.getName());

	// Handle to loader
	private final IRuleLoader loader;

	// Handle to the Strategy Class for specific incoming document (Excel, Word etc
	// tasks)
	private IDocumentInStrategy strategyIn = null;

	// Handle to the strategy Class to write out the document
	private IDocumentOutStrategy outputStrategy = null;

	/**
	 * Handle to the the Strategy Delegate we use for outputting
	 * 
	 * @return the strategy object
	 */
	public IDocumentOutStrategy getOutputStrategy() {
		return outputStrategy;
	}
	
	public void setOutputStrategy (IDocumentOutStrategy newStrategy) {
		this.outputStrategy = newStrategy;
	}

	/**
	 * The strategy we use for dealing with incoming documents
	 * 
	 * @return the strategy object
	 */
	protected IDocumentInStrategy getDocumentOutStrategy() {
		return strategyIn;
	}

	/**
	 * Construct a new RuleRunner.
	 * 
	 * @see RuleRunnerFactory in this package which we use to build a properly
	 *      constructed instance of this class
	 * @param documentStrategy
	 * @param ruleLoader
	 * @param outputStrategy
	 */
	protected RuleRunner(IDocumentInStrategy documentStrategy, IRuleLoader ruleLoader,
			ExcelOutputStrategy outputStrategy) {
		loader = ruleLoader;
		strategyIn = documentStrategy;
		this.outputStrategy = outputStrategy;
	}

	/**
	 * Call the rules engine - using the Excel Data provided
	 * 
	 * @param inputFromExcel  - the excel data sheet as already opened as a Java
	 *                        Stream
	 * @param args
	 * @param nameOfLogSheet
	 * @param userDataDisplay - if not null, we can log log items back to the user
	 *                        (such as pre and post excel data)
	 * @throws DroolsParserException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidFormatException
	 * 
	 */
	public void callRules(InputStream inputFromExcel, RuleSource ruleSource, String nameOfLogSheet,
			IGiveFeedbackToUsers userDataDisplay, ILogger userMessages)
			throws DroolsParserException, IOException, ClassNotFoundException, InvalidFormatException {

		// Create a new Excel Logging object
		strategyIn.setDocumentLogger(new SpreadSheetLogger());
		if (userDataDisplay != null) {
			userDataDisplay.notifyProgress(10);
		}

		// Convert the cell and log if we have a handle
		RangeHolder ranges = strategyIn.getJavaBeansFromStream(inputFromExcel);

		if (userDataDisplay != null) {
			userDataDisplay.notifyProgress(25);
			userDataDisplay.showPreRulesSnapShot(ranges);
			userDataDisplay.notifyProgress(45);
		}

		// Call the overloaded method to actually run the rules and log output if we
		// have a handle
		// Log the cell contents

		// Add the Spreadsheet contents as facts
		ruleSource.addFacts(ranges.getAllRangesAndCells());
		if (userDataDisplay != null) {
			userDataDisplay.notifyProgress(65);
		}

		// Load and fire our rules files against the data
		runStatelessRules(ruleSource, userMessages);

		if (userDataDisplay != null) {
			userDataDisplay.showPostRulesSnapShot(ranges);
			userDataDisplay.notifyProgress(80);
		}

		// update the excel spreadsheet with the result of our rules
		strategyIn.updateOriginalDocument(ranges);

		if (userDataDisplay != null) {
			userDataDisplay.notifyProgress(90);
		}

		// update the excel spreadsheet with our log file
		strategyIn.flush(nameOfLogSheet);
		strategyIn.flush(userMessages);

		// Close our input work book
		inputFromExcel.close();
		if (userDataDisplay != null) {
			userDataDisplay.notifyProgress(100);
		}

		
		
		// Process our output
		userMessages.info("Write to Excel Output file:" + outputStrategy.getOutputDestination());
		outputStrategy.processOutput(strategyIn.getExcelWorkBook());
		

	}

	/**
	 * Call the rules engine - using the Excel Data provided
	 * 
	 * @param inputFromExcel
	 * @param ruleSource
	 * @param nameOfLogSheet
	 * @throws DroolsParserException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidFormatException
	 */
	public void callRules(InputStream inputFromExcel, RuleSource ruleSource, String nameOfLogSheet)
			throws DroolsParserException, IOException, ClassNotFoundException, InvalidFormatException {

		callRules(inputFromExcel, ruleSource, nameOfLogSheet, null, null);
	}

	/**
	 * Call Rules against and Excel data file, then return workbook
	 * 
	 * @param urlOfExcelDataFile
	 * @param ruleSource
	 * @param excelLogSheet
	 * @throws DroolsParserException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidFormatException
	 */
	public void callRules(URL urlOfExcelDataFile, RuleSource ruleSource, String excelLogSheet)
			throws DroolsParserException, IOException, ClassNotFoundException, InvalidFormatException {

		InputStream inputFromExcel = null;

		try {
			log.info("Looking for url:" + urlOfExcelDataFile);

			inputFromExcel = urlOfExcelDataFile.openStream();

			log.info("found url:" + urlOfExcelDataFile);

		} catch (MalformedURLException e) {

			log.log(Priority.WARN, "Malformed URL Exception Loading rules", e);
			throw e;

		} catch (IOException e) {
			log.log(Priority.WARN, "IO Exception Loading rules", e);
			throw e;

		}

		callRules(inputFromExcel, ruleSource, excelLogSheet);

	}

	/**
	 * Call Rules against and Excel data file, then return workbook
	 * 
	 * @param locationOfExcelDataFile
	 * @param ruleSource
	 * @param excelLogSheet
	 * @throws IOException
	 * @throws DroolsParserException
	 * @throws ClassNotFoundException
	 * @throws InvalidFormatException
	 */
	public void callRules(File locationOfExcelDataFile, RuleSource ruleSource, String excelLogSheet)
			throws IOException, DroolsParserException, ClassNotFoundException, InvalidFormatException {

		InputStream inputFromExcel = null;

		// Sanity checks on the incoming file
		if (locationOfExcelDataFile == null) {
			throw new IOException("java.io.File cannot be null");
		}

		if (!locationOfExcelDataFile.exists()) {
			throw new IOException("no file at location:" + locationOfExcelDataFile.getAbsolutePath());
		}

		try {

			log.info("Looking for file:" + locationOfExcelDataFile.getAbsolutePath());
			inputFromExcel = new FileInputStream(locationOfExcelDataFile);

			log.info("found file:" + locationOfExcelDataFile);

		} catch (IOException e) {
			log.log(Priority.WARN, "IO Exception Loading rules", e);
			throw e;

		}

		callRules(inputFromExcel, ruleSource, excelLogSheet);

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
	void runStatelessRules(RuleSource ruleSource, ILogger logger)
			throws DroolsParserException, IOException, ClassNotFoundException {

		// The most common operation on a rulebase is to create a new rule
		// session; either stateful or stateless.
		log.info("Creating master rule base");
		KnowledgeBase masterRulebase = loader.loadRules(ruleSource);

		log.info("running stateless rules");
		runStatelessRules(masterRulebase, ruleSource.getFacts(), ruleSource.getGlobals(), logger);

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
	void runStatelessRules(KnowledgeBase preBuiltKnowledgeBase, Collection<Cell> facts, HashMap<String, Cell> globals,
			ILogger logger) throws DroolsParserException, IOException {

		// Create a new stateless session
		log.info("Creating new working memory");
		StatelessKnowledgeSession workingMemory = preBuiltKnowledgeBase.newStatelessKnowledgeSession();

		log.info("Checking for globals");
		if (globals != null) {
			for (String o : globals.keySet()) {
				log.info("Inserting global name: " + o + " value:" + globals.get(o));
				workingMemory.setGlobal(o, globals.get(o));
			}
		}
		// Add the logger
		log.info("Inserting handle to logger (via global)");
		workingMemory.setGlobal("log", logger);

		log.info("Using facts:" + facts);

		log.info("==================== Calling Rule Engine ====================");

		// Fire using the facts
		workingMemory.execute(facts);

		log.info("==================== Rules Complete ====================");

	}

	/**
	 * Run the rules
	 *
	 * @param rulesUrl    - array of rule files that we need to load
	 * @param dslFileUrl  - optional dsl file name (can be null)
	 * @param ruleFlowUrl - optional ruleFlow file name (can be null)
	 * @param facts       - Javabeans to pass to the rule engine
	 * @param globals     - global variables to pass to the rule engine
	 * @param logger      - handle to a logging object
	 * @throws IOException
	 * @throws DroolsParserException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	StatefulKnowledgeSession getStatefulSession(RuleSource ruleSource, ILogger logger)
			throws DroolsParserException, IOException, ClassNotFoundException {

		// The most common operation on a rulebase is to create a new rule
		// session; either stateful or stateless.
		KnowledgeBase masterRulebase = loader.loadRules(ruleSource);

		return getStatefulSession(masterRulebase, ruleSource.getGlobals(), logger);

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

		for (String o : globals.keySet()) {
			log.info("Inserting global name: " + o + " value:" + globals.get(o));
			workingMemory.setGlobal(o, globals.get(o));
		}

		// Add the logger
		log.info("Inserting handle to logger (via global)");
		workingMemory.setGlobal("log", logger);

		return workingMemory;

	}

}