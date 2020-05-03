package net.firstpartners.core.file;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.core.log.SpreadSheetLogger;
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
 * </p><p>
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
 * While the Outputer will not overwrite any data present, it will not check
 * for duplicates; running this five times will add five lines of equal data. We
 * leave it up to your Excel skills to detect duplicates!
 * </p>
 * <p>
 * Some best practices should include
 * </p>
 * <ul>
 * <li>Including a filename or some other unique identifier in the csv output file. That
 * way you can spot and filter out any duplicate runs.</li>
 * <li>In your rule file, map your incoming value to an specific outgoing value.
 * That way your output name should not change if if there are changes to the format
 * of the source document.</li>
 * </ul>
 * </p>
 * <img src=
 * "https://paulbrowne-irl.github.io/red-piranha/images/GuiRangeHolder.png" alt="Red Piranha GUI showing RangeHolder Data></img>
 * 
 * @author paul
 *
 */
public class CSVOutputStrategy implements IDocumentOutStrategy {

	// Logger
	private static final Logger log = RpLogger.getLogger(CSVOutputStrategy.class.getName());

	// Name of the outputfile
	private String outputFileName = null;

	private RangeList outputRange;

	/**
	 * Constructor - takes the name of the file we intend outputting to
	 * 
	 * @param outputFileName
	 */
	public CSVOutputStrategy(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	/**
	 * Not needing to be implemented as part of this strategy
	 */
	@Override
	public void flush() {

	}

	/**
	 * We implement this is part of the interface, but don't do anything with it.
	 */
	@Override
	public void flush(ILogger logger) {

	}

	/**
	 * String representing where our output is going to
	 */
	@Override
	public String getOutputDestination() {
		return "File:" + outputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	/**
	 * Process the output from the system
	 * 
	 * @param fileToProcess
	 * @param outputFileName
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws CsvRequiredFieldEmptyException
	 * @throws CsvDataTypeMismatchException
	 */
	public void processOutput() throws IOException, InvalidFormatException {

		// create a writer
		Writer writer = new FileWriter(outputFileName, true);

		// write CSV file
		CSVPrinter printer = CSVFormat.DEFAULT.withHeader("ID", "Name", "Program", "New", "University").print(writer);

		// create a list
		List<Object[]> data = new ArrayList<>();
		data.add(new Object[] { 1, "John Mike", "Engineering", "MIT" });
		data.add(new Object[] { 2, "Jovan Krovoski", "Medical", "Harvard" });
		data.add(new Object[] { 3, "Lando Mata", "Computer Science", "TU Berlin" });
		data.add(new Object[] { 4, "Emma Ali", "Mathematics", "Oxford" });

		// write list to file
		printer.printRecords(data);

		// flush the stream
		printer.flush();

		// close the writer
		writer.close();

	}

	/**
	 * Not needing to be implemented as part of this strategy
	 */
	@Override
	public void setDocumentLogger(SpreadSheetLogger spreadSheetLogger) {

	}

	/**
	 * Update a copy of our Original Document with new data
	 * 
	 * @param ranges
	 * @throws IOException
	 */
	public void setUpdates(OfficeDocument fileToProcess, RangeList range) throws IOException {

		// this converter ignores any original , we just store the range output
		this.outputRange = range;

	}

	/**
	 * Access a Stream, convert it to Red JavaBeans (representing CSV Object)
	 * 
	 * @return RangeList
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	RangeList getJavaBeansFromSource() throws EncryptedDocumentException, IOException {

//		   List<MyBean> beans = new CsvToBeanBuilder(FileReader(this.csvInputFullName))
//			       .withType(Visitors.class).build().parse();
//		
		log.debug("converting incoming office stream to Javabeans");
//		excelWorkBook = WorkbookFactory.create(inputAsStream);
//		RangeList myRange = SpreadSheetConvertor.convertNamesFromPoiWorkbookIntoRedRange(excelWorkBook);
//		
//		
//		inputAsStream.close();
		return null;

	}

}
