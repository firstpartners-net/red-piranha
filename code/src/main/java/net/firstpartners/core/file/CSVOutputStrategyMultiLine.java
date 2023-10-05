package net.firstpartners.core.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
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
 *  It uses the data from  * the RangeHolder (our version of the Word / Excel / Other document that was
 * passed in, that the rules then modified).
 * <li>
 * <li>

 * Happily, this data is displayed in the Red Piranha GUI - picture on the main page of the GitHub project site - so
 * you can see the data you have to work with.</li>
 * </p>
 * <ol>
 * <li>The Outputer looks for a csv file of the name given (normally in the
 * params passed in when calling via the web - see smample web application).
 * </li><li>
 * If the file is not found it will be created.</li>
 * <li>It will loop through the availble cells and output one line of CSV output for each.</li>
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

	// Name of the output file
	private String appendFileName = null;

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// Hold the data until we are asked to process it
	private RangeList dataToOutput;

	// sub directory e.g. for samples
	private String subDirectory;

	/**
	 * Constructor - takes the name of the file we intend outputting to
	 *
	 * @param outputFileName - file we want to output to
	 */
	public CSVOutputStrategyMultiLine(String outputFileName) {
		this.appendFileName = outputFileName;
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
		return "File:" + appendFileName;
	}

	/**
	 * <p>
	 * getOutputFileName.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getOutputFileName() {
		return appendFileName;
	}

	/**
	 * To conform to the interface - not (yet) implemented in this strategy
	 */
	public void setAdditionalOutputData(Map<String,String> ignored){}

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

		// Get all the Cells that we have been keeping track of
		Map<String, net.firstpartners.data.Cell> allCells = dataToOutput.getAllCellsWithNames();

		// Loop through the cells
		Iterator<net.firstpartners.data.Cell> redCells = allCells.values().iterator();
		while (redCells.hasNext()) {

			net.firstpartners.data.Cell thisRedCell = redCells.next();
			String orignalSheetRef = thisRedCell.getOriginalTableReference();
			String originalPoiRef = thisRedCell.getOriginalCellReference();

			if (originalPoiRef == null || orignalSheetRef == null) {
				log.debug("Cells has no ref to original sheet or cell - ignoring:" + thisRedCell);
			} else {

				log.debug("Would Output"+thisRedCell);
			}

		}

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

		log.debug("Set Data - not null:"+(incomingData!=null));

	}

	List<String> getHeadersFromFile() throws IOException {

		// We must have a pre existing file
		File appendFile = ResourceFinder.getFileResourceUsingConfig(appendFileName, appConfig);

		if (!appendFile.exists()) {
			throw new IllegalArgumentException(
					"For writing to a CSV file " + appendFileName + " should already exist with headers in first row");
		}

		log.debug("Found CSV:" + appendFileName);

		// Open in a reader
		Reader reader = new BufferedReader(new FileReader(appendFileName));

		@SuppressWarnings("deprecation")
		CSVParser csvParser = CSVParser.parse(reader, CSVFormat.EXCEL.withFirstRecordAsHeader());

		List<String> returnValues = csvParser.getHeaderNames();
		log.debug("Found Headers" + returnValues + " headers");

		// close everything off
		reader.close();
		reader = null;

		return returnValues;
	}

	/**
	 * Count the number of entries there are in the CSV file
	 * 
	 * @return number of lines in CSV file
	 * @throws IOException
	 */
	int getNumberOfRowsInFile() throws IOException {

		File file = new File(appendFileName);
		FileInputStream fis = new FileInputStream(file);
		byte[] byteArray = new byte[(int) file.length()];
		fis.read(byteArray);
		String data = new String(byteArray);
		String[] stringArray = data.split("\r\n");
		fis.close();

		return stringArray.length;
	}

}
