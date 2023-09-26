package net.firstpartners.core;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import groovy.util.ResourceException;
import groovy.util.ScriptException;
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
	 * Allows us to pass details of the environment
	 *
	 * @param config a {@link net.firstpartners.core.Config} object
	 */
	void setConfig(Config config);
	
	/**
	 * State where out input is coming from
	 *
	 * @return a {@link java.lang.String} object
	 */
	String getInputName();

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

}
