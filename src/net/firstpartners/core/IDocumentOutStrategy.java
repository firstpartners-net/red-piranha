package net.firstpartners.core;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.SpreadSheetLogger;
import net.firstpartners.data.OfficeDocument;
import net.firstpartners.data.RangeList;

/**
 * Marks classes as being able to output documents in the system
 * @author PBrowne
 *
 */
public interface IDocumentOutStrategy {
	
	/**
	 * Write out and logs we hold to the document
	 * @param excelWorkBook
	 * @param nameOfLogSheet
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
	 */
	public void processOutput() throws IOException, InvalidFormatException;
	
	/**
	 * Allows us to set a Logger that will flush to an Excel Spreadheet
	 * @param spreadSheetLogger
	 */
	void setDocumentLogger(SpreadSheetLogger spreadSheetLogger);
	
	/**
	 * Update our Original Document with new data
	 * @param ranges
	 * @throws IOException
	 */
	void updateCopyOfOriginalDocument(OfficeDocument fileToProcess,RangeList ranges) throws IOException;

}
