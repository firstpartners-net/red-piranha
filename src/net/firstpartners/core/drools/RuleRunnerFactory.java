package net.firstpartners.core.drools;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

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
import net.firstpartners.core.word.WordInputStrategy;
import net.firstpartners.core.word.WordOutputStrategy;
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
	public static final String SUFFIX_EXCELX = ".xlsx";

	// mappings between the suffix and class we want to load
	static Map<String, Class<?>> inputSuffixMaps = null;
	static Map<String, Class<?>> outputSuffixMaps = new HashMap<String, Class<?>>();

	static void resetReferenceTables() {
		inputSuffixMaps = null;
		outputSuffixMaps = null;
	}

	/**
	 * Build the cache of strategy classes we can use to handle different input and
	 * output files
	 */
	static void buildReferenceTables() {

		if (inputSuffixMaps == null) {
			inputSuffixMaps = new HashMap<String, Class<?>>();
			inputSuffixMaps.put(SUFFIX_WORD, WordInputStrategy.class);
			inputSuffixMaps.put(SUFFIX_WORDX, WordXInputStrategy.class);
			inputSuffixMaps.put(SUFFIX_EXCEL, ExcelInputStrategy.class);
			inputSuffixMaps.put(SUFFIX_EXCELX, ExcelInputStrategy.class); // same
		}

		if (outputSuffixMaps == null) {
			outputSuffixMaps = new HashMap<String, Class<?>>();
			outputSuffixMaps.put(SUFFIX_WORD, WordOutputStrategy.class);
			outputSuffixMaps.put(SUFFIX_WORDX, WordXOutputStrategy.class);
			outputSuffixMaps.put(SUFFIX_EXCEL, ExcelOutputStrategy.class);
			outputSuffixMaps.put(SUFFIX_EXCELX, ExcelOutputStrategy.class); // same
		}

	}

	/**
	 * Get the class that maps to our filename based on .xls, .doc etc
	 * 
	 * @param string - full filename
	 * @return
	 */
	static Class<?> getInputMapping(String fileName) {

		assert fileName != null;

		buildReferenceTables();

		int splitPoint = fileName.lastIndexOf(".");
		if(splitPoint==-1) {
			//nothing found
			return null;
		}
		String suffix = fileName.substring(splitPoint, fileName.length());

		log.debug("Looking for input Mapping against suffix:" + suffix);

		return inputSuffixMaps.get(suffix);
	}

	/**
	 * Get the class that maps to our filename based on .xls, .doc etc
	 * 
	 * @param string - full filename
	 * @return
	 */
	static Class<?> getOutputMapping(String fileName) {

		assert fileName != null;

		buildReferenceTables();

		int splitPoint = fileName.lastIndexOf(".");
		String suffix = fileName.substring(splitPoint, fileName.length());

		log.debug("Looking for output Mapping against suffix:" + suffix);

		return outputSuffixMaps.get(suffix);
	}

	/**
	 * Convenience constructor, state rules as single string
	 * 
	 * @param inputFileName
	 * @param mySourceAsString
	 * @param outputFileName
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static RuleRunner getRuleRunner(String inputFileName, String mySourceAsString, String outputFileName)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

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
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static RuleRunner getRuleRunner(String inputFileName, RuleDTO ruleSource, String outputFileName)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		// check our incoming params
		assert inputFileName != null;
		assert ruleSource != null;
		assert outputFileName != null;

		// Make sure we get the right type of loader
		IRuleLoaderStrategy ruleLoaderStrategy = getRuleLoader(ruleSource.getRulesLocation()[0]);

		// Decide on our input strategy
		inputFileName = inputFileName.toLowerCase();
		Class<?> strategyClass = getInputMapping(inputFileName);
		if(strategyClass ==null) {
			throw new IllegalArgumentException("No Input Strategy Found for:"+inputFileName);
		}
		log.debug("trying to create Strategy Object from class:" + strategyClass);
		Constructor<?> constructor = strategyClass.getConstructor(String.class);
		IDocumentInStrategy inputStrat = (IDocumentInStrategy) constructor.newInstance(inputFileName);

		
		
		// Decide on our output strategy
		outputFileName = outputFileName.toLowerCase();
		strategyClass =null;
		strategyClass = getOutputMapping(outputFileName);
		if(strategyClass ==null) {
			throw new IllegalArgumentException("No Output Strategy Found for:"+inputFileName);
		}
		log.debug("trying to create Strategy Object from class:" + strategyClass);
		constructor = strategyClass.getConstructor(String.class);
		IDocumentOutStrategy outputStrat = (IDocumentOutStrategy) constructor.newInstance(outputFileName);

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
