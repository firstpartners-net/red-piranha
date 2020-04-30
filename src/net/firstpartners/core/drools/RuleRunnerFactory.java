package net.firstpartners.core.drools;

import org.apache.log4j.Logger;

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.drools.loader.FileRuleLoader;
import net.firstpartners.core.drools.loader.IRuleLoaderStrategy;
import net.firstpartners.core.drools.loader.RuleDTO;
import net.firstpartners.core.drools.loader.URLRuleLoaderStrategy;
import net.firstpartners.core.excel.ExcelInputStrategy;
import net.firstpartners.core.excel.ExcelOutputStrategy;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.core.word.WordXInputStrategy;
import net.firstpartners.core.word.WordXOutputStrategy;

/**
 * Return an instance of RuleRunner, Appropriately configured with the various
 * IDocumentStrategy etc to handle the current document request
 * 
 * @author PBrowne
 *
 */
public class RuleRunnerFactory {

	// Handle to the logger
	private static final Logger log = RpLogger.getLogger(RuleRunner.class.getName());

	// How we identify file types
	public static final String SUFFIX_WORD = ".doc";
	public static final String SUFFIX_WORDX = ".docx";
	public static final String SUFFIX_EXCEL = ".xls";
	public static final String SUFFIX_EXCELX = ".xlx";

	/**
	 * Private Constructor, part of singleton Method
	 */
	private RuleRunnerFactory() {
	}

	/**
	 * Convenience constructor, state rules as single string
	 * 
	 * @param inputFileName
	 * @param mySourceAsString
	 * @param outputFileName
	 * @return
	 */
	public static RuleRunner getRuleRunner(String inputFileName, String mySourceAsString, String outputFileName) {

		RuleDTO mySource = new RuleDTO();
		mySource.setRulesLocation(mySourceAsString);

		return getRuleRunner(inputFileName, mySource, outputFileName);
	}

	/**
	 * Create a properly configured RuleRunner for the Input / Output file types we
	 * are passing
	 * 
	 * @param inputFileName
	 * @param ruleSource
	 * @param outputFileName
	 * @return
	 */
	public static RuleRunner getRuleRunner(String inputFileName, RuleDTO ruleSource, String outputFileName) {

		// check our incoming params
		assert inputFileName != null;
		assert ruleSource != null;
		assert outputFileName != null;

		// Make sure we get the right type of loader
		IRuleLoaderStrategy ruleLoaderStrategy = getRuleLoader(ruleSource.getRulesLocation()[0]);

		// Decide on our input strategy - these default to xl
		inputFileName = inputFileName.toLowerCase();
		IDocumentInStrategy inputStrat;
		if (inputFileName.endsWith(SUFFIX_WORD) || inputFileName.endsWith(SUFFIX_WORDX)) {
			inputStrat = new WordXInputStrategy(inputFileName);

		} else {
			inputStrat = new ExcelInputStrategy(inputFileName);

		}
		//decide on our output strategy - these default to xl
		outputFileName = outputFileName.toLowerCase();
		IDocumentOutStrategy outputStrat;
		if (outputFileName.endsWith(SUFFIX_WORD) || outputFileName.endsWith(SUFFIX_WORDX)) {
			outputStrat = new WordXOutputStrategy(outputFileName);

		} else {
			outputStrat = new ExcelOutputStrategy(outputFileName);

		}


		log.debug("Using DocumentInputStrategy:" + inputStrat.getClass());
		log.debug("Using RuleLoader:" + ruleLoaderStrategy);
		log.debug("Using DocumentOutputStrategy:" + outputStrat);

		return new RuleRunner(inputStrat, ruleLoaderStrategy, outputStrat);

	}

	/**
	 * Get a handle to the rule loader we will be using, based on the ruleLocation
	 * 
	 * @param ruleLocation
	 * @return
	 */
	public static IRuleLoaderStrategy getRuleLoader(String ruleLocation) {

		IRuleLoaderStrategy ruleLoader;

		if (ruleLocation.startsWith("http")) {
			ruleLoader = new URLRuleLoaderStrategy();
		} else {
			ruleLoader = new FileRuleLoader(ruleLocation);
		}

		// Default - url rule loader
		return ruleLoader;
	}

}
