package net.firstpartners.core;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.firstpartners.core.drools.ClassAndLocation;
import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.core.word.WordXInputStrategy;
import net.firstpartners.data.RangeList;

/**
 * Implements the Strategy Command Pattern so that we can read data from
 * different sources
 *
 * @see WordXInputStrategy @see WordStrategy
 * @author PBrowne
 * @version $Id: $Id
 */
public interface IDocumentInStrategy {

	/**
	 * Get a handle to the original source document
	 *
	 * @return a {@link net.firstpartners.core.file.OfficeDocument} object
	 */
	OfficeDocument getOriginalDocument();

	
	/**
	 * State where out input is coming from
	 * @return class and locaiton of input strategy
	 *
	 * @return a {@link java.lang.String} object
	 */
	ClassAndLocation getInputDetails();

	/**
	 * Set where the input is coming from
	 * @param class and locaiton of input strategy
	 */
	void setInputDetails(ClassAndLocation inputDetails);

	/**
	 * Access a Stream, convert it to Red JavaBeans (representing and Office Objects)
	 *
	 * @return RangeList
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
	 * @throws org.apache.poi.EncryptedDocumentException
	 * @throws java.io.IOException
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	RangeList getJavaBeansFromSource() throws EncryptedDocumentException, IOException, InvalidFormatException, ResourceException, ScriptException;

	/**
	 * Update the document we are dealing with
	 *
	 * @param originalDoc a {@link net.firstpartners.core.file.OfficeDocument} object
	 */
	void setOriginalDocument(OfficeDocument originalDoc);

	/**
	 * Allows the program ot set a directory containing this sample to assist it in finding files
	 * @param directorySampleIsIn
	 */
	void setSubDirectory(String subDirectory);

}
