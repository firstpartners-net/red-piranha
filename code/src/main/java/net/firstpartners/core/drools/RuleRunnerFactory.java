package net.firstpartners.core.drools;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.drools.loader.FileRuleLoader;
import net.firstpartners.core.drools.loader.IRuleLoaderStrategy;
import net.firstpartners.core.drools.loader.RuleConfig;
import net.firstpartners.core.drools.loader.URLRuleLoaderStrategy;
import net.firstpartners.core.excel.ExcelInputStrategy;
import net.firstpartners.core.excel.ExcelOutputStrategy;
import net.firstpartners.core.file.CSVOutputStrategy;
import net.firstpartners.core.file.JsonOutputStrategy;
import net.firstpartners.core.file.PDFOutputStrategy;
import net.firstpartners.core.word.WordInputStrategy;
import net.firstpartners.core.word.WordXInputStrategy;

/**
 * Return an instance of RuleRunner, Appropriately configured with the various
 * IDocumentStrategy etc to handle the current document request
 * 
 * @author PBrowne
 *
 */
public class RuleRunnerFactory {

	// Handle to the logger
	private static final Logger log = LoggerFactory.getLogger(RuleRunnerFactory.class);
	
	// How we identify file types
	public static final String SUFFIX_WORD = ".doc";
	public static final String SUFFIX_WORDX = ".docx";
	public static final String SUFFIX_EXCEL = ".xls";
	public static final String SUFFIX_EXCELX = ".xlsx";
	public static final String SUFFIX_CSV = ".csv";
	public static final String SUFFIX_PDF = ".pdf";
	public static final String SUFFIX_JSON = ".json";

	// mappings between the suffix and class we want to load
	static Map<String, Class<?>> inputSuffixMaps = null;
	static Map<String, Class<?>> outputSuffixMaps = null;

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
			outputSuffixMaps.put(SUFFIX_CSV, CSVOutputStrategy.class);
			outputSuffixMaps.put(SUFFIX_PDF, PDFOutputStrategy.class);
			outputSuffixMaps.put(SUFFIX_EXCEL, ExcelOutputStrategy.class);
			outputSuffixMaps.put(SUFFIX_EXCELX, ExcelOutputStrategy.class); // same
			outputSuffixMaps.put(SUFFIX_JSON, JsonOutputStrategy.class); // same
			
			
		}

	}

	/**
	 * Get the class that maps to our filename based on .xls, .doc etc
	 * 
	 * @param string - full filename
	 * @throws Illegal Argument exception if we don't have a mapping for this class
	 * @return
	 */
	static Class<?> getInputMapping(String fileName) {

		assert fileName != null;
		
		//change to lower case
		fileName = fileName.toLowerCase();

		buildReferenceTables();

		int splitPoint = fileName.lastIndexOf(".");
		if (splitPoint == -1) {
			// nothing found
			throw new IllegalArgumentException("Unable to guess the type of file (based on where '. is) for:" + fileName);
		}
		String suffix = fileName.substring(splitPoint, fileName.length());

		log.debug("Looking for input Mapping against suffix:" + suffix);

		Class<?> strategyClass = inputSuffixMaps.get(suffix);
		
		log.debug("Found strategy class:" + strategyClass);

		
		if (strategyClass == null) {
			throw new IllegalArgumentException("No Input Strategy Found to read files of type:" + suffix);
		}

		return strategyClass;

	}

	/**
	 * Get the class that maps to our filename based on .xls, .doc etc
	 * 
	 * @param string - full filename
	 * @return
	 */
	static Class<?> getOutputMapping(String fileName) {

		assert fileName != null;

		fileName = fileName.toLowerCase();
		
		buildReferenceTables();

		int splitPoint = fileName.lastIndexOf(".");
		String suffix = fileName.substring(splitPoint, fileName.length());

		log.debug("Looking for output Mapping against suffix:" + suffix);
		Class<?> foundStrategy = outputSuffixMaps.get(suffix);
		log.debug("Found Strategy:" + foundStrategy);

		if (foundStrategy == null) {
			throw new IllegalArgumentException("No Output Strategy found to write files of type :" + suffix);
		}

		return foundStrategy;
	}

	/**
	 * Convenience constructor, state rules as single string
	 * 
	 * @param inputFileName      - where we get the data from
	 * @param ruleSourceAsString - name of a single rule file
	 * @param outputFileName     - where we will output the file to
	 * @return RuleRunner Object with the correct input / output Strategies
	 *         configured
	 * @throws InvocationTargetException - from underlying input - output libs
	 * @throws IllegalArgumentException- from underlying input - output libs
	 * @throws IllegalAccessException    - from underlying input - output libs
	 * @throws InstantiationException    - from underlying input - output libs
	 * @throws SecurityException         - from underlying input - output libs
	 * @throws NoSuchMethodException     - from underlying input - output libs
	 */
	public static RuleRunner getRuleRunner(String inputFileName, String ruleSourceAsString, String outputFileName)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		RuleConfig mySource = new RuleConfig();
		mySource.setRulesLocation(ruleSourceAsString);

		return getRuleRunner(inputFileName, mySource, outputFileName);
	}

	/**
	 * Create a properly configured RuleRunner for the Input / Output file types we
	 * are passing
	 * 
	 * @param inputFileName      - where we get the data from
	 * @param ruleSourceAsString - name of a multiple rule files
	 * @param outputFileName     - where we will output the file to
	 * @return RuleRunner Object with the correct input / output Strategies
	 *         configured
	 * @throws InvocationTargetException - from underlying input - output libs
	 * @throws IllegalArgumentException- from underlying input - output libs
	 * @throws IllegalAccessException    - from underlying input - output libs
	 * @throws InstantiationException    - from underlying input - output libs
	 * @sthrows SecurityException         - from underlying input - output libs
	 * @throws NoSuchMethodException     - from underlying input - output libs
	 */
	public static RuleRunner getRuleRunner(String inputFileName, RuleConfig ruleSource, String outputFileName)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		// check our incoming params
		assert inputFileName != null;
		assert ruleSource != null;
		assert outputFileName != null;

		// Make sure we get the right type of loader
		IRuleLoaderStrategy ruleLoaderStrategy = getRuleLoader(ruleSource);

		// Decide on our input strategy
		Class<?> strategyClass = getInputMapping(inputFileName);

		log.debug("trying to create Strategy Object from class:" + strategyClass);
		Constructor<?> constructor = strategyClass.getConstructor(String.class);
		IDocumentInStrategy inputStrat = (IDocumentInStrategy) constructor.newInstance(inputFileName);

		// Decide on our output strategy
		strategyClass = null;
		strategyClass = getOutputMapping(outputFileName);

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
	public static IRuleLoaderStrategy getRuleLoader(RuleConfig ruleLocation) {

		IRuleLoaderStrategy ruleLoader;
		String firstRuleLocation = ruleLocation.getRulesLocation()[0].toLowerCase();
		

		if (firstRuleLocation.startsWith("http")) {
			ruleLoader = new URLRuleLoaderStrategy();
		} else {
			ruleLoader = new FileRuleLoader(ruleLocation);
		}

		// Default - url rule loader
		return ruleLoader;
	}

}
