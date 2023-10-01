package net.firstpartners.core.drools;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.Config;
import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.excel.ExcelInputStrategy;
import net.firstpartners.core.excel.ExcelOutputStrategy;
import net.firstpartners.core.file.CSVOutputStrategy;
import net.firstpartners.core.file.PDFOutputStrategy;
import net.firstpartners.core.json.JsonInputStrategy;
import net.firstpartners.core.json.JsonOutputStrategy;
import net.firstpartners.core.word.WordInputStrategy;
import net.firstpartners.core.word.WordXInputStrategy;

/**
 * Return an instance of RuleRunner, Appropriately configured with the various
 * IDocumentStrategy etc to handle the current document request
 *
 * @author PBrowne
 * @version $Id: $Id
 */
public class RunnerFactory {

	// Handle to the logger
	private static final Logger log = LoggerFactory.getLogger(RunnerFactory.class);

	// How we identify file types
	/** Constant <code>SUFFIX_WORD=".doc"</code> */
	public static final String SUFFIX_WORD = ".doc";
	/** Constant <code>SUFFIX_WORDX=".docx"</code> */
	public static final String SUFFIX_WORDX = ".docx";
	/** Constant <code>SUFFIX_EXCEL=".xls"</code> */
	public static final String SUFFIX_EXCEL = ".xls";
	/** Constant <code>SUFFIX_EXCELX=".xlsx"</code> */
	public static final String SUFFIX_EXCELX = ".xlsx";
	/** Constant <code>SUFFIX_CSV=".csv"</code> */
	public static final String SUFFIX_CSV = ".csv";
	/** Constant <code>SUFFIX_PDF=".pdf"</code> */
	public static final String SUFFIX_PDF = ".pdf";
	/** Constant <code>SUFFIX_JSON=".json"</code> */
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
			inputSuffixMaps.put(SUFFIX_JSON, JsonInputStrategy.class);

		}

		if (outputSuffixMaps == null) {
			outputSuffixMaps = new HashMap<String, Class<?>>();
			outputSuffixMaps.put(SUFFIX_CSV, CSVOutputStrategy.class);
			outputSuffixMaps.put(SUFFIX_PDF, PDFOutputStrategy.class);
			outputSuffixMaps.put(SUFFIX_EXCEL, ExcelOutputStrategy.class);
			outputSuffixMaps.put(SUFFIX_EXCELX, ExcelOutputStrategy.class); // same
			outputSuffixMaps.put(SUFFIX_JSON, JsonOutputStrategy.class);

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

		// change to lower case
		fileName = fileName.toLowerCase();

		buildReferenceTables();

		int splitPoint = fileName.lastIndexOf(".");
		if (splitPoint == -1) {
			// nothing found
			throw new IllegalArgumentException(
					"Unable to guess the type of file (based on where '. is) for:" + fileName);
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
		if (splitPoint == -1) {
			// nothing found
			throw new IllegalArgumentException(
					"Unable to guess the type of Output file (based on where '. is) for:" + fileName);
		}

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
	 * Overloaded method, for convenience
	 *
	 * @param dataModel a {@link net.firstpartners.core.RedModel} object
	 * @throws RPException
	 * @return a {@link net.firstpartners.core.drools.RuleRunner} object
	 */
	public static IRunner getRuleRunner(RedModel dataModel) throws RPException {

		return getRuleRunner(dataModel, new Config());

	}

	/**
	 * Create a properly configured RuleRunner for the Input / Output file types we
	 * are passing within the RedModel cargo object
	 *
	 * @param redModel - where we get the data from
	 * @param appConfig - application Configuration
	 * @return RuleRunner Object with the correct input / output Strategies
	 *         configured
	 * @throws java.lang.reflect.InvocationTargetException - from underlying input -
	 *                                                     output libs
	 * @throws java.lang.IllegalAccessException            - from underlying input -
	 *                                                     output libs
	 * @throws java.lang.InstantiationException            - from underlying input -
	 *                                                     output libs
	 * @sthrows SecurityException - from underlying input - output libs
	 * @throws java.lang.NoSuchMethodException    - from underlying input - output
	 *                                            libs
	 * @throws java.lang.SecurityException        if any.
	 * @throws java.lang.IllegalArgumentException if any.
	 */
	public static IRunner getRuleRunner(RedModel redModel, Config appConfig)
			throws RPException {

		// check our incoming params
		assert redModel != null;
		assert redModel.getInputFileLocation() != null;
		assert redModel.getRuleFileLocation() != null;
		assert redModel.getOutputFileLocation() != null;


		// handle on our strategy objects
		IRunner myRunner;
		IDocumentInStrategy inputStrat;
		IDocumentOutStrategy outputStrat;

		// Decide on our input strategy
		Class<?> strategyClass = getInputMapping(redModel.getInputFileLocation());

		log.debug("trying to create Strategy Object from class:" + strategyClass);
		Constructor<?> constructor;
		try {
			constructor = strategyClass.getConstructor(String.class);
			inputStrat = (IDocumentInStrategy) constructor
					.newInstance(redModel.getBaseDirectory()+redModel.getInputFileLocation());

			// pass in the config
			inputStrat.setSubDirectory(redModel.getBaseDirectory());
			inputStrat.setConfig(appConfig);

		} catch (NoSuchMethodException | SecurityException |InstantiationException | InvocationTargetException |IllegalAccessException e) {
			throw new RPException("Error when creating input strategy Object", e);
		}

		// Decide on our output strategy
		strategyClass = null;
		strategyClass = getOutputMapping(redModel.getOutputFileLocation());

		log.debug("trying to create Strategy Object from class:" + strategyClass);
		try {
			constructor = strategyClass.getConstructor(String.class);
			 outputStrat = (IDocumentOutStrategy) constructor
			.newInstance(redModel.getBaseDirectory()+redModel.getOutputFileLocation());

		} catch (NoSuchMethodException | SecurityException |InstantiationException | InvocationTargetException |IllegalAccessException e) {
			throw new RPException("Error when creating output strategy Object", e);
		}

		// pass in the config
		outputStrat.setSubDirectory(redModel.getBaseDirectory());
		outputStrat.setConfig(appConfig);

		log.debug("Using DocumentInputStrategy:" + inputStrat.getClass());
		log.debug("Using DocumentOutputStrategy:" + outputStrat);

		// Decide on the Strategy (runner) we want to execute this model
		if (redModel.getRuleFileLocation().toLowerCase().endsWith(".dmn")) {

			myRunner = new DecisionModelRunner(inputStrat, outputStrat, appConfig);
		} else {
			myRunner = new RuleRunner(inputStrat, outputStrat, appConfig);

		}

		return myRunner;

	}

}
