package net.firstpartners.core.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.data.RangeList;

/**
 * Strategy class to output of CSV Document.
 * <p>
 * CSV will try to append to a *New or Existing* CSV file.
 * 
 * In general, one Cell value processed equals one line in the CSV file.
 *
 * <p>
 * The process by which the output strategy works is as follows:
 * <ul>
 * <li>
 * It uses the data from * the RangeHolder (our version of the Word / Excel /
 * Other document that was
 * passed in, that the rules then modified).
 * <li>
 * <li>
 * 
 * Happily, this data is displayed in the Red Piranha GUI - picture on the main
 * page of the GitHub project site - so
 * you can see the data you have to work with.</li>
 * </p>
 * <ol>
 * <li>The Outputer looks for a csv file of the name given (normally in the
 * params passed in when calling via the web - see smample web application).
 * </li>
 * <li>
 * If the file is not found it will be created.</li>
 * <li>It will loop through the availble cells and output one line of CSV output
 * for each.</li>
 * </ol>
 * <p>
 * While the Outputer will not overwrite any data present, it will not check for
 * duplicates; running this five times will add five lines of equal data. We
 * leave it up to your Excel skills to detect duplicates!
 * </p>
 * <p>
 * Some best practices should include
 * </p>
 * <ul>
 * <li>Including a filename or some other unique identifier in the csv output
 * file. That way you can spot and filter out any duplicate runs.</li>
 * <li>In your rule file, map your incoming value to an specific outgoing value.
 * That way your output name should not change if if there are changes to the
 * format of the source document.</li>
 * </ul>
 * </p>
 *
 * @author paul
 * @version $Id: $Id
 */
public class CSVOutputStrategyMultiLine implements IDocumentOutStrategy {

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// Hold the data until we are asked to process it
	private RangeList dataToOutput;

	// sub directory e.g. for samples
	private String subDirectory;

	// Name of the outputfile
	private String outputFileName = null;

	//Additional data we wish to output
	Map<String, String> additionalDataToInclude=null;

	//Associated Settor
	public void setAdditionalOutputData(Map<String, String> additionalData){
		this.additionalDataToInclude=additionalData;
	}

	/**
	 * Constructor - takes the name of the file we intend outputting to
	 *
	 * @param outputFileName - file we want to output to
	 */
	public CSVOutputStrategyMultiLine(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public String getSubDirectory() {
		return subDirectory;
	}

	public void setSubDirectory(String subDirectory) {
		this.subDirectory = subDirectory;
	}

	/**
	 * {@inheritDoc}
	 *
	 * String representing where our output is going to
	 */
	@Override
	public String getOutputDestination() {
		return "File:" + outputFileName;
	}

	/**
	 * <p>
	 * getOutputFileName.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getOutputFileName() {
		return outputFileName;
	}


	/**
	 * Process the output from the system
	 *
	 * @throws java.io.IOException                                        - from
	 *                                                                    underlying
	 *                                                                    libs
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException - from
	 *                                                                    underlying
	 *                                                                    libs
	 */
	public void processOutput() throws IOException, InvalidFormatException {


		//check the data before processing
		assert dataToOutput!=null : "No data available to output";

		String outputFileDir = ResourceFinder.getBaseDirOfAllSamples();
		
		//Construct the output file including directory
		Path outputPath;
		if(outputFileDir!=""){
			outputPath = Paths.get(outputFileDir+"/"+outputFileName);
		} else {
			outputPath = Paths.get(outputFileName);
		}

		//placeholders for the  CSV Output classes we will output next
		BufferedWriter writer = null;
		CSVPrinter csvPrinter  =null;
		File tmpOutputFileConfirm =null;

		//confirm outfile exists or not - open for append if it is
		try{
			tmpOutputFileConfirm = ResourceFinder.getFileResourceUsingConfig(outputPath.toString());
	
		} catch (FileNotFoundException fnfe){
			log.debug("CSV Output file "+outputPath.toString()+" not found when checking - this is ok as we will create");
		}


		//check if we need to create teh file from scratch, or create a new one
		if(tmpOutputFileConfirm==null||!tmpOutputFileConfirm.exists()){

			//create a new file
			log.debug("Will to output CSV to new file:"+outputPath);

			//define our CSV headers
			String[] headers = generateHeaders(additionalDataToInclude);

			writer = Files.newBufferedWriter(outputPath);
			csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers));

		} else {
			// reuse the existing (i.e. we don't need to add headers)
			log.debug("Will to add CSV to existing file:"+outputPath);
			writer = Files.newBufferedWriter(outputPath, StandardOpenOption.APPEND);
			csvPrinter = new CSVPrinter(writer,CSVFormat.DEFAULT.withSkipHeaderRecord());
		}



		// Get all the Cells that we have been keeping track of
		Map<String, net.firstpartners.data.Cell> allCells = dataToOutput.getAllCellsWithNames();

        
		// Loop through the cells
		Iterator<net.firstpartners.data.Cell> redCells = allCells.values().iterator();
		while (redCells.hasNext()) {

			net.firstpartners.data.Cell thisRedCell = redCells.next();

			String [] subRecords = splitFieldName(thisRedCell.getName());

			//Generate the main body line
			Object[] bodyRecord = generateBodyRow(additionalDataToInclude,subRecords,
									thisRedCell.getName(),
									thisRedCell.getValueAsText(),
									thisRedCell.getOriginalTableReference(),
									//thisRedCell.getOriginalCellReference() // blank as we can't trust for the moment
									""
									);

			csvPrinter.printRecord(bodyRecord);
	
		}
		csvPrinter.flush(); 
		csvPrinter.close(); 
		writer.close();

	}



	/**
	 * {@inheritDoc}
	 *
	 * Update a copy of our Original Document with new data
	 */
	public void setUpdates(OfficeDocument ignored, RangeList incomingData) throws IOException {

		// this converter ignores any original , we just store the range output
		dataToOutput = incomingData;

		log.debug("Set Data - not null:" + (incomingData != null));

	}

	String[] getCsvFileAsStringArray() throws IOException {

		File file = new File(outputFileName);
		FileInputStream fis = new FileInputStream(file);
		byte[] byteArray = new byte[(int) file.length()];
		fis.read(byteArray);
		String data = new String(byteArray);
		fis.close();
		String[] stringArray = data.split("\r\n");

		return stringArray;
		
	}

	/**
	 * Count the number of entries there are in the CSV file
	 * 
	 * @return number of lines in CSV file
	 * @throws IOException
	 */
	int getNumberOfRowsInFile() throws IOException {

		String[] stringArray = getCsvFileAsStringArray();

		return stringArray.length;
	}

	/**
	 * Generate the headers needed for the CSV File
	 * Takes additional data to Output and 
	 * @return
	 */
	 String[] generateHeaders(Map<String,String> additionalDataToInclude) {

		//convert our must-include values
		String[] mustIncludeAdditionalKeys = additionalDataToInclude.keySet().toArray(new String[0]);

		//Our standard Headers
		String[] standardHeaders = {"Name","Value","Sheet","Ref","Table","Row","Col"};

		//concatenate the arrays
		String[] result = ArrayUtils.addAll(mustIncludeAdditionalKeys,standardHeaders);
	
		return result;
	}

	/**
	 * Generate the body needed needed for the CSV File
	 * @param additionalDataToInclude - often including file name and dates
	 * @param subrecords, extracted directly from the cell
	 * @param params - 3 key fields extracted from name of cell
	 * @return
	 */
	 String[] generateBodyRow(Map<String,String> additionalDataToInclude,String[]subRecords,String ... params) {
		
		//convert our must-include values
		String[] mustIncludeAdditionalData = additionalDataToInclude.values().toArray(new String[0]);

		//concatenate the arryas.
		String[] result = ArrayUtils.addAll(mustIncludeAdditionalData,params);
		result = ArrayUtils.addAll(result,subRecords);

		return result;
	}

		/**
	 * split a generalized names into 3 tokens for our cvs
	 * @return
	 */
	 String[] splitFieldName(String fieldName) {

		assert fieldName!=null : "Incoming Value should not be null";

		//default return values 
		String part1="";
		String part2="";
		String part3="";

		log.debug("Splitting fields from:"+fieldName);

		//If text ends with _0 remove
		if(fieldName.endsWith("_0")){
			fieldName = fieldName.substring(0,fieldName.length()-2);
		}

		//check that we are not in a single field (e.g. companyname - no table refernecs)
		if(fieldName.indexOf("_")<0){
			part1=fieldName;
		} else {

			// attempt to split out the fields
			//get first part
			int match1 = fieldName.indexOf("_",0);
			part1 = fieldName.substring(0, match1);

			//get second aprt
			int match2 = fieldName.indexOf("_",match1+1);
			part2 = fieldName.substring(match1+1, match2);

			//get third part
			part3 = fieldName.substring(match2+1,fieldName.length());

		}




		//Combine and return
		String[] returnValue= {part1,part2,part3};
		return returnValue;
	}

	
}

