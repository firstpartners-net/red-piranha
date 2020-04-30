package net.firstpartners.core;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.firstpartners.core.word.WordXInputStrategy;
import net.firstpartners.data.OfficeDocument;
import net.firstpartners.data.RangeList;

/**
 * Implements the Strategy Command Pattern so that we can read data from
 * different sources
 * 
 * @see WordXInputStrategy @see WordStrategy
 * @author PBrowne
 *
 */
public interface IDocumentInStrategy {

	/**
	 * Get a handle to the original source document
	 * @return
	 */
	OfficeDocument getOriginalDocument();

	/**
	 * State where out input is coming from
	 * 
	 * @return
	 */
	String getInputName();

	/**
	 * Access a Stream, convert it to Red JavaBeans (representing Excel Objects)
	 * 
	 * @param inputFromExcel
	 * @return
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	RangeList getJavaBeansFromSource() throws EncryptedDocumentException, IOException, InvalidFormatException;

	/**
	 * Update the document we are dealing with
	 * @param originalDoc
	 */
	void setOriginalDocument(OfficeDocument originalDoc);

}