package net.firstpartners.core;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.data.RangeList;

/**
 * Marks classes as being able to output documents in the system
 * @author PBrowne
 *
 */
public interface IDocumentOutStrategy {
	

	/**
	 * String representation of where our output will be going to
	 * @return
	 */
	public String getOutputDestination();
	
	/**
	 * Allows us to pass details of the environment
	 */
	void setConfig(Config config);

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
	 * Update our Original Document with new data
	 * @param ranges
	 * @throws IOException
	 */
	void setUpdates(OfficeDocument fileToProcess,RangeList ranges) throws IOException;

}
