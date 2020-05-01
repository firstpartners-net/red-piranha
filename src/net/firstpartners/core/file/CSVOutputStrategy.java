package net.firstpartners.core.file;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.core.log.SpreadSheetLogger;
import net.firstpartners.data.OfficeDocument;
import net.firstpartners.data.RangeList;

/**
 * Strategy class of output of Word Document
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
	 * @todo refector out this method or implement - doesn't really apply for word
	 *       docs
	 */
	@Override
	public void flush() {

	}

	@Override
	public void flush(ILogger logger) {
		// TODO Auto-generated method stub

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
	public void processOutput() throws IOException, InvalidFormatException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

		
		  // List<MyBean> beans comes from somewhere earlier in your code.
	     Writer writer = new FileWriter(outputFileName);
	     log.debug("outputting to:"+outputFileName);
	     StatefulBeanToCsv<Object> beanToCsv = new StatefulBeanToCsvBuilder<Object>(writer).build();
	     beanToCsv.write(outputRange);
	     writer.close();
		
	}

	/**
	 * @todo refector out this method or implement - doesn't really apply for word
	 *       docs
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

}
