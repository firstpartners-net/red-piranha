package net.firstpartners.core.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.data.Cell;
import net.firstpartners.data.RangeList;

/**
 * Strategy class of output of CSV Document.
 * <p>
 * CSV will try to append to an existing CSV file. In general, one document
 * processed equals one line in the CSV file.
 *
 * The table below might help understand this Outputers behaviour.
 * <table style="border: 1px solid black;">
 * <tr>
 * <td>heading-1</td>
 * <td>heading-2</td>
 * <td>heading-3</td>
 * </tr>
 * <tr>
 * <td>file-a-cell1.value&nbsp;</td>
 * <td>file-a-cell2.value&nbsp;</td>
 * <td>file-a-cell3.value&nbsp;</td>
 * </tr>
 * <td>file-b-cell1.value&nbsp;</td>
 * <td>file-b-cell2.value&nbsp;</td>
 * <td>file-b-cell3.value&nbsp;</td>
 * </tr>
 * </table>
 * <p>
 * The process by which the Outputer works is as follows. It uses the data from
 * the RangeHolder (our version of the Word / Excel / Other document that was
 * passed in, that the rules then modified).
 * </p>
 * <p>
 * Happily, this data is displayed in the Red Piranha GUI - picture below - so
 * you can see the data you have to work with.
 * </p>
 * <ol>
 * <li>The Outputer looks for a csv file of the name given (normally in the
 * config file); if it does not find it will throw an error.</li>
 * <li>The Outputer will take the headers (the first line of the CSV file). In
 * the above example it will be heading-1, heading-2 and heading-3.</li>
 * <li>For each of these header values, the outputer will search for a @see Cell
 * of the same cellName from the RangeHolder data.</li>
 * <li>If it finds a match, it will take the value of that Cell Object and use
 * it when outputing the line.</li>
 * <li>After it has tried to find matches, it will then add one line to the CSV
 * file, using the values found.</li>
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
 * <p>
 * <img src=
 * "https://paulbrowne-irl.github.io/red-piranha/images/GuiRangeHolder.png"
 * alt="Red Piranha GUI showing RangeHolder Data></img>
 * </p>
 *
 * @author paul
 * @version $Id: $Id
 */
public class CSVOutputStrategySingleLine implements IDocumentOutStrategy {

	// Name of the output file
	private String appendFileName = null;

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// Hold the data until we are asked to process it
	// @SuppressWarnings("unused") // eclipse mistakenly marks this as unused
	private RangeList processedRange;

	// sub directory e.g. for samples
	private String subDirectory;

	// Additional data we wish to output
	Map<String, String> additionalDataToInclude = null;

	// Associated Settor
	public void setAdditionalOutputData(Map<String, String> additionalData) {
		this.additionalDataToInclude = additionalData;
	}

	/**
	 * Constructor - takes the name of the file we intend outputting to
	 *
	 * @param outputFileName - file we want to output to
	 */
	public CSVOutputStrategySingleLine(String outputFileName) {
		this.appendFileName = outputFileName;
	}

	public String getSubDirectory() {
		return subDirectory;
	}

	public void setSubDirectory(String subDirectory) {
		this.subDirectory = subDirectory;
	}

	/**
	 * /**
	 * Get the values from our Beans (RangeList) that match the headers
	 *
	 * @param headers  that we are looking to match in the data
	 * @param beanData that we have collected.
	 * @return Map <header-key, matching-value>
	 */
	public Map<String, String> getMatchingValues(List<String> headers, RangeList beanData) {

		Map<String, String> returnValues = new HashMap<String, String>();

		// Loop and try to find data matching our header values
		for (String thisHeader : headers) {
			Cell matchingCell = beanData.findCell(thisHeader);
			String value = null;
			if (matchingCell != null) {
				value = matchingCell.getValueAsText();
			}

			returnValues.put(thisHeader, value);
		}

		return returnValues;
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

		// Get the headers in the incoming file
		List<String> headers = getHeadersFromFile();

		// Extract data from our bean tree using these headers
		Map<String, String> outputValues = getMatchingValues(headers, processedRange);

		// create a list to write out
		Object[] dataToWrite = new Object[headers.size()];
		int counter = 0;

		// Loop and add the values in order
		for (String thisHeader : headers) {

			dataToWrite[counter] = outputValues.get(thisHeader);
			log.info("CSV Output for header:" + thisHeader + " value:" + dataToWrite[counter]);

			counter++;
		}

		// create a writer - set to append (true)
		Writer writer = new FileWriter(appendFileName, true);

		// open-write-flush-close CSV file
		CSVPrinter printer = CSVFormat.EXCEL.print(writer);
		printer.printRecord(dataToWrite);
		printer.flush();
		writer.close();

	}

	/**
	 * {@inheritDoc}
	 *
	 * Update a copy of our Original Document with new data
	 */
	public void setUpdates(OfficeDocument ignored, RangeList incomingData) throws IOException {

		// this converter ignores any original , we just store the range output
		processedRange = incomingData;

	}

	List<String> getHeadersFromFile() throws IOException {

		// We must have a pre existing file
		File appendFile = ResourceFinder.getFile(appendFileName);

		if (!appendFile.exists()) {
			throw new IllegalArgumentException(
					"For writing to a CSV file " + appendFileName + " should already exist with headers in first row");
		}

		log.debug("Found CSV:" + appendFileName);

		File readerFile = ResourceFinder.getFile(appendFileName);

		// Open in a reader
		Reader reader = new BufferedReader(new FileReader(readerFile));

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

		File file =  ResourceFinder.getFile(appendFileName);
		FileInputStream fis = new FileInputStream(file);
		byte[] byteArray = new byte[(int) file.length()];
		fis.read(byteArray);
		String data = new String(byteArray);
		String[] stringArray = data.split("\r\n");
		fis.close();

		return stringArray.length;
	}

}
