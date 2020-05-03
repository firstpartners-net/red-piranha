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
 * 
 * CSV will try to append to an existing CSV file. Only cells marked with
 * 'isModified' will be output. All Output will be on one line in format
 * 
 * cell1.name | cell2.name | cell3.name cell1.value | cell2.value | cell3.value
 * 
 * 
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
		CSVPrinter printer = CSVFormat.DEFAULT.withHeader("ID", "Name", "Program", "New","University").print(writer);
		
		

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
