package net.firstpartners.core.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.Config;
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

	private Config appConfig;

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// Hold the data until we are asked to process it
	private RangeList dataToOutput;

	// sub directory e.g. for samples
	private String subDirectory;

	// Name of the outputfile
	private String outputFileName = null;

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
	 * To conform to the interface - not (yet) implemented in this strategy
	 */
	public void setAdditionalOutputData(Map<String, String> ignored) {
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


		assert dataToOutput!=null : "No data available to output";

		String outputFileDir = ResourceFinder.getDirectoryResourceUsingConfig(appConfig);
		
		//Construct the output file including directory
		Path outputPath;
		if(outputFileDir!=""){
			outputPath = Paths.get(outputFileDir+"/"+outputFileName);
		} else {
			outputPath = Paths.get(outputFileName);
		}

		log.debug("trying to output Excel to:"+outputPath);

		//define our CSV headers
		String[] headers = generateHeaders();

		// Get all the Cells that we have been keeping track of
		Map<String, net.firstpartners.data.Cell> allCells = dataToOutput.getAllCellsWithNames();

		//Setup the CSV Writer
		BufferedWriter writer = Files.newBufferedWriter(outputPath);
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers));
        
        
		// Loop through the cells
		Iterator<net.firstpartners.data.Cell> redCells = allCells.values().iterator();
		while (redCells.hasNext()) {

			net.firstpartners.data.Cell thisRedCell = redCells.next();

			Object[] bodyRecord = generateBodyRow(
									thisRedCell.getName(),
									thisRedCell.getValueAsText(),
									thisRedCell.getOriginalTableReference(),
									thisRedCell.getOriginalCellReference(),
									"",
									"",
									"",
									"",
									"");

			csvPrinter.printRecord(bodyRecord);
	
		}
		csvPrinter.flush(); 
		csvPrinter.close(); 

	}



	/** {@inheritDoc} */
	public void setConfig(Config appConfig) {
		this.appConfig = appConfig;
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
	 * @return
	 */
	 String[] generateHeaders() {
		
		String[] returnValue = {"Name","Value","Sheet","Ref","Table","Row","Col","Date","Source"};
		return returnValue;
	}

	/**
	 * Generate the body needed needed for the CSV File
	 * @return
	 */
	 String[] generateBodyRow(String ... params) {
		
		String[] returnValue = params;
		return returnValue;
	}
}

