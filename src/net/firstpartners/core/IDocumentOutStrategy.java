package net.firstpartners.core;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Marks classes as being able to output documents in the system
 * @author PBrowne
 *
 */
public interface IDocumentOutStrategy {
	
	/**
	 * Process the output from the system
	 * @param fileToProcess
	 * @param outputFileName
	 * @throws IOException
	 */
	public void processOutput(Workbook fileToProcess) throws IOException;

	/**
	 * String representation of where our output will be going to
	 * @return
	 */
	public String getOutputDestination();
}
