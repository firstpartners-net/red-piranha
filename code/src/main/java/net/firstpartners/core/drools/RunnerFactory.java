package net.firstpartners.core.drools;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.excel.ExcelInputStrategy;
import net.firstpartners.core.excel.ExcelOutputStrategy;
import net.firstpartners.core.file.CSVOutputStrategyMultiLine;
import net.firstpartners.core.file.PDFOutputStrategy;
import net.firstpartners.core.file.ResourceFinder;
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
			outputSuffixMaps.put(SUFFIX_CSV, CSVOutputStrategyMultiLine.class);
			outputSuffixMaps.put(SUFFIX_PDF, PDFOutputStrategy.class);
			outputSuffixMaps.put(SUFFIX_EXCEL, ExcelOutputStrategy.class);
			outputSuffixMaps.put(SUFFIX_EXCELX, ExcelOutputStrategy.class); // same
			outputSuffixMaps.put(SUFFIX_JSON, JsonOutputStrategy.class);

		}

	}


	/**
	 * Get the class that maps to our filename based on .xls, .doc etc
	 * 
	 * @param subDir we are operating in
	 * @param string - full filename
	 * @param fileHandle - if we have already a handle ot the file -can be null 
	 * @throws Illegal Argument exception if we don't have a mapping for this class
	 * @return
	 * @throws RPException
	 */
	static List<ClassAndLocation> getInputMapping(String subDir, String fileName,File fileHandle ) throws RPException {

		//check incoming values
		assert fileName != null;
		fileName = fileName.toLowerCase();

		//build reference tables if first time here
		if(inputSuffixMaps==null || outputSuffixMaps ==null){
			buildReferenceTables();
		}

		// handle for our return value(s)		
		List<ClassAndLocation> returnMappingList = new ArrayList<ClassAndLocation>();

		//Check for directory - only when we are called the first time (filehanle will be null) 
		if(fileHandle==null){
			log.debug("checking for directory on:"+fileName);
			returnMappingList.addAll(handleDirectoryInput(subDir,fileName));
		}
		

		//No results from Directory, then try individual file mapping
		if(returnMappingList ==null|| returnMappingList.size()==0){


			//try and map our values to a source
			log.debug("Checking to get mapping for files of type:"+fileName);

			int splitPoint = fileName.lastIndexOf(".");


			if(splitPoint>0){
				String suffix = fileName.substring(splitPoint, fileName.length());

				log.debug("Looking for input Mapping against suffix:" + suffix);
				Class<?> strategyClass = inputSuffixMaps.get(suffix);

				log.debug("Found strategy class:" + strategyClass);

				//try to get a handle to this file
				// try {
				// 	File tmpFile = ResourceFinder.getFileResourceUsingConfig(subDir, fileName);
				// 	log.debug("found:"+tmpFile);
				// } catch (IOException e) {
				// 	log.warn("##### test errro ###",e);
				// 	throw new RPException("Cannot handle file:",e);
				// }


				if (strategyClass == null) {
					throw new RPException("No Input Strategy Found to read files of type:" + suffix);
				}

				ClassAndLocation cAndL =  new ClassAndLocation(strategyClass, fileName,fileHandle);
				log.debug("Created Input Mapping:"+cAndL);


				returnMappingList.add(cAndL);
			}



		}

		return returnMappingList;

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

		// build reference tables if first time here
		if (inputSuffixMaps == null || outputSuffixMaps == null) {
			buildReferenceTables();
		}

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
	 * Create a properly configured RuleRunner for the Input / Output file types we
	 * are passing within the RedModel cargo object
	 *
	 * @param redModel  - where we get the data from
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
	public static IRunner getRuleRunner(RedModel redModel)
			throws RPException
			 {

		// check our incoming params
		assert redModel != null;
		assert redModel.getInputFileLocation() != null;
		assert redModel.getRuleFileLocation() != null;
		assert redModel.getOutputFileLocation() != null;

		// handle on our strategy objects
		IRunner myRunner;
		List<IDocumentInStrategy> inputStrategies=null;
		IDocumentOutStrategy outputStrat =null;

		//Call the the submethods to get our input and output strategies
		inputStrategies = defineInputStrategies(redModel);
		outputStrat = defineOutputStrategy(redModel);


		// Decide on the Strategy (runner) we want to execute this model
		if (redModel.getRuleFileLocation().toLowerCase().endsWith(".dmn")) {

			myRunner = new DecisionModelRunner(inputStrategies, outputStrat);
		} else {
			myRunner = new RuleRunner(inputStrategies, outputStrat);

		}

		
		//Log the different strategies being used
		for (IDocumentInStrategy inStrat : inputStrategies){
			log.debug("Using DocumentInputStrategy:" + inStrat.getClass()+" from file:"+inStrat.getInputDetails()); 
		}
		log.debug("Using Runner:"+myRunner.getClass());
		log.debug("Using DocumentOutputStrategy:" + outputStrat.getClass());

		return myRunner;

	}

	/**
	 * Use the available information to define our Input strategy(s)
	 * @param redModel
	 * @return
	 * @throws RPException
	 */
	private static List<IDocumentInStrategy> defineInputStrategies(RedModel redModel) throws RPException {

		//check our incoming values
		assert redModel!=null;


		//setup our return list		
		List <IDocumentInStrategy>inputStrategies = new ArrayList<IDocumentInStrategy>();
		
		// Decide on our input strategy(s)
		log.debug("Trying to create strategy(s) to input:"+redModel.getInputFileLocation());
		List<ClassAndLocation> strategyClassList = getInputMapping(redModel.getSubDirectory(),redModel.getInputFileLocation(),null);

		//Now loop through and create these
		for(ClassAndLocation cAndL :strategyClassList ){

		
			log.debug("trying to create Strategy Object from class:" + cAndL.classHolder+" file location:"+cAndL.locationText);
			Constructor<?> constructor;
			try {
				constructor = cAndL.classHolder.getConstructor(ClassAndLocation.class);

				//Update our cAndL
				if(cAndL.fileLocation==null){
					cAndL.locationText = redModel.getSubDirectory()+redModel.getInputFileLocation();
					log.debug("Updated locationText to use incoming red values:"+cAndL.locationText);
				}

				IDocumentInStrategy tmpStrategy = (IDocumentInStrategy) constructor
						.newInstance(cAndL);

				// pass in the config
				tmpStrategy.setSubDirectory(redModel.getSubDirectory());
				//tmpStrategy.setInputDetails(cAndL);

				//Add this out our returnList
				inputStrategies.add(tmpStrategy);

			} catch (NoSuchMethodException | SecurityException | InstantiationException | InvocationTargetException
					| IllegalAccessException e) {
				log.warn("Error when creating strategy",e);
				throw new RPException("Error when creating input strategy Object", e);
			}
		}
		return inputStrategies;
	}

	/**
	 * Use the available information to define our output strategy
	 * @param redModel
	 * @return
	 * @throws RPException
	 */
	private static IDocumentOutStrategy defineOutputStrategy(RedModel redModel)
			throws RPException 
			{

		 IDocumentOutStrategy outputStrat;
		Class<?> strategyClass;
		Constructor<?> constructor;
		
		// Decide on our output strategy
		strategyClass = null;
		strategyClass = getOutputMapping(redModel.getOutputFileLocation());

		log.debug("trying to create Strategy Object from class:" + strategyClass);
		try {
			constructor = strategyClass.getConstructor(String.class);
			outputStrat = (IDocumentOutStrategy) constructor
					.newInstance(redModel.getSubDirectory() + redModel.getOutputFileLocation());

		} catch (NoSuchMethodException | SecurityException | InstantiationException | InvocationTargetException
				| IllegalAccessException e) {
			throw new RPException("Error when creating output strategy Object", e);
		}

		// pass in the config
		outputStrat.setSubDirectory(redModel.getSubDirectory());
		return outputStrat;
	}

	/**
	 * check if we have directory input, review the files in that directory, and get an input strategy for each file
	 */
	static List<ClassAndLocation> handleDirectoryInput(String subDir, String fileName) {


		// handle for our return value(s)		
		List<ClassAndLocation> returnClassList = new ArrayList<ClassAndLocation>();

		String searchFile = subDir+"/"+fileName;

		log.debug("Testing for directory based on subdir:"+searchFile);

		
		if(!searchFile.equals("")){

			log.debug("Attemping to generate strategies for files in Directory:"+subDir);

			List<File> filesInDirectory=ResourceFinder.getDirectoryFiles(subDir);

			for(File currentFile : filesInDirectory){
				
				String tmpFileName="";

				//do a recursive call to get the strategy for this single file
				try {
					tmpFileName = subDir+currentFile.getName();


					log.debug("Recursive call to identify strategy for:"+tmpFileName);
					returnClassList.addAll(getInputMapping(subDir,tmpFileName,currentFile));

					log.debug("Number of Valid Input Strategies:"+returnClassList.size());

				//} catch (IOException e) {
				//	log.warn("Ignoring IO Error when trying to look at:"+tmpFileName);
				} catch (RPException rpe) {
					log.info("No strategy found for file:"+tmpFileName);
				}
			}
			

		} else {
			log.debug("Not a directory:"+fileName+"- will attempt to treat later as an input file");
		}

		return returnClassList;

	}

}
