package net.firstpartners.core;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.SpreadSheetLogger;
import net.firstpartners.data.RangeList;

/**
 * Marks classes as being able to output documents in the system
 * @author PBrowne
 *
 */
public interface IDocumentOutStrategy {
	
	/**
	 * Write out and logs we hold to the document
	 */
	void flush();

	/**
	 * Write out any documents we hold to anybody else interested
	 * @param logger
	 */
	void flush(ILogger logger);
	
	/**
	 * String representation of where our output will be going to
	 * @return
	 */
	public String getOutputDestination();

	/**
	 * Process the output from the system
	 * @param fileToProcess
	 * @param outputFileName
	 * @throws IOException
	 * @throws InvalidFormatException 
	 * @throws CsvRequiredFieldEmptyException 
	 * @throws CsvDataTypeMismatchException 
	 */
	public void processOutput() throws IOException, InvalidFormatException;
	
	/**
	 * Allows us to set a Logger that will flush within a document if the Strategy supports it
	 * @param documentLogger
	 */
	void setDocumentLogger(SpreadSheetLogger documentLogger);
	
	/**
	 * Update our Original Document with new data
	 * @param ranges
	 * @throws IOException
	 */
	void setUpdates(OfficeDocument fileToProcess,RangeList ranges) throws IOException;

}
