package net.firstpartners.core.drools;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
// previous drools
import org.drools.KnowledgeBase;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;

// new kie
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.drools.loader.IRuleLoaderStrategy;
import net.firstpartners.core.drools.loader.RedRuleBuilder;
import net.firstpartners.core.log.EmptyStatusUpdate;
import net.firstpartners.core.log.IStatusUpdate;
import net.firstpartners.core.log.SpreadSheetStatusUpdate;
import net.firstpartners.data.Cell;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeList;
import net.firstpartners.data.RedModel;

/**
 * Call JBoss Drools (Rules Engine) passing in Document data as Java Objects
 * 
 * This class uses an IDocumentStrategy Object to handle different types
 */
public class RuleRunner {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

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
	public RedModel callRules() throws IOException, ClassNotFoundException, InvalidFormatException {

		// Add the logger
		// prevent a null pointer in our rules
		IStatusUpdate userMessages = new EmptyStatusUpdate();

		return callRules(userMessages);
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
	public RedModel callRules(IStatusUpdate userMessages)
			throws IOException, ClassNotFoundException, InvalidFormatException {

		// Load our rules first - catches any compilation errors early
		RedModel ruleModel = ruleLoader.getRuleSource();

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
			userMessages.showPreRulesSnapShot(ranges);
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
			userMessages.showPostRulesSnapShot(ranges);
			userMessages.notifyProgress(80);
		}

		//Make a note of any new facts added
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
	public IDocumentOutStrategy getDocumenttOutputStrategy() {
		return outputStrategy;
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
			IStatusUpdate logger) throws DroolsParserException, IOException {
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
	private Collection<Cell> runStatelessRules(KnowledgeBase preBuiltKnowledgeBase, Collection<Cell> facts,
			HashMap<String, Cell> globals, IStatusUpdate logger) throws DroolsParserException, IOException {

		// Create a new stateless session
		log.debug("Creating new working memory");
		StatelessKnowledgeSession workingMemory = preBuiltKnowledgeBase.newStatelessKnowledgeSession();
		
		// Add the logger
		log.debug("Inserting handle to logger (via global)");
		workingMemory.setGlobal("log", logger);

		log.debug("Checking for globals");
		if (globals != null) {
			for (String o : globals.keySet()) {
				log.debug("Inserting global name: " + o + " value:" + globals.get(o));
				workingMemory.setGlobal(o, globals.get(o));
			}
		}

		
		//We need to 'listen' for new cells being created so we can add to our results
		WorkingMemoryCellListener cellListener = new WorkingMemoryCellListener();
		workingMemory.addEventListener(cellListener);

		log.debug("==================== Starting Rules ====================");

		// Fire using the facts
		workingMemory.execute(facts);

		log.debug("==================== Rules Complete ====================");
		List<Cell> additionalFacts =cellListener.getNewCells(facts);
		
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
			throws IOException, ClassNotFoundException {

		
		KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kSession = kc.newKieSession("RedKS");
		
		
		
		// The most common operation on a rulebase is to create a new rule
		// session; either stateful or stateless.
		log.debug("Creating master rule base");
		//@Todo refactor into this patter
		//KnowledgeBase masterRulebase = ruleLoader.loadRules(ruleSource);
		KieModule masterRulebase = RedRuleBuilder.getRulesFromDisk(ruleSource);

		
		//refactorign from here
		
		log.debug("running stateless rules");
		Collection<Cell> tmpCollection = runStatelessRules(masterRulebase, ruleSource.getFacts(), ruleSource.getGlobals(), logger);
		return tmpCollection;
	}

	public void setOutputStrategy(IDocumentOutStrategy newStrategy) {
		this.outputStrategy = newStrategy;
	}

}