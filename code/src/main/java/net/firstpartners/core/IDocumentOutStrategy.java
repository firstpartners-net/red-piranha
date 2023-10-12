package net.firstpartners.core;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.data.RangeList;

/**
 * Marks classes as being able to output documents in the system
 *
 * @author PBrowne
 * @version $Id: $Id
 */
public interface IDocumentOutStrategy {



	/**
	 * String representation of where our output will be going to
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getOutputDestination();


	/**
	 * Update our Original Document with new data
	 *
	 * @param ranges a {@link net.firstpartners.data.RangeList} object
	 * @throws java.io.IOException
	 * @param fileToProcess a {@link net.firstpartners.core.file.OfficeDocument}
	 *                      object
	 */
	void setUpdates(OfficeDocument fileToProcess, RangeList ranges) throws IOException;

	/**
	 * Allows the program ot set a directory containing this sample to assist it in
	 * finding files
	 * 
	 * @param directorySampleIsIn
	 */
	void setSubDirectory(String subDirectory);

	/**
	 * Process the output from the system
	 *
	 * @throws java.io.IOException
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
	 */
	public void processOutput() throws IOException, InvalidFormatException;


	public void setAdditionalOutputData(Map<String, String> additionalData);

}
