package net.firstpartners.core.json;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.drools.ClassAndLocation;
import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.core.file.ResourceFinder;
import net.firstpartners.data.RangeList;

/**
 * Class to read info on our samples from a JSON file.
 * It means we can be consistent between the samples we display in the WebApp
 * And the samples we use in our integration tests
 *
 * @author paulf
 * @version $Id: $Id
 */
public class JsonInputStrategy implements IDocumentInStrategy {

	private ClassAndLocation jsonInput;

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private OfficeDocument officeDocument; 

	private String subDirectory;

	/**
	 * Construct a new Strategy Object
	 *
	 * @param jsonInputFileName a {@link java.lang.String} object
	 */
	public JsonInputStrategy(ClassAndLocation jsonInput) {
		this.jsonInput = jsonInput;
	}

	public String getSubDirectory() {
		return subDirectory;
	}

	/**
	 * Set the base Directory
	 */
	public void setSubDirectory(String subDirectory) {
		this.subDirectory = subDirectory;
	}

	/** {@inheritDoc} */
	@Override
	public ClassAndLocation getInputDetails() {
		return jsonInput;
	}

	public void setInputDetails(ClassAndLocation jsonInput) {
		this.jsonInput = jsonInput;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Access a Stream, convert it to Red JavaBeans (representing grid like data)
	 */
	@Override
	public RangeList getJavaBeansFromSource() throws EncryptedDocumentException, IOException, InvalidFormatException {

		log.debug("Convert Json to RedBeans");
		ObjectMapper objectMapper = new ObjectMapper();
		File jsonSource = ResourceFinder.getFileResourceUsingConfig(jsonInput);

		RangeList myRange = objectMapper.readValue(jsonSource, RangeList.class);
		JsonNode rootNode = objectMapper.readTree(jsonSource);

		// beware - the following can generate a lot of Json into the log!
		// log.debug("Found:"+rootNode);
		assertNotNull(rootNode);

		return myRange;
	}

	/**
	 * <p>
	 * Getter for the field <code>jsonInputFileName</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public ClassAndLocation getJsonInputFileName() {
		return jsonInput;
	}

	/** {@inheritDoc} */
	@Override
	public OfficeDocument getOriginalDocument() {
		return officeDocument;
	}

	/**
	 * <p>
	 * Setter for the field <code>jsonInputFileName</code>.
	 * </p>
	 *
	 * @param jsonInputFileName a {@link java.lang.String} object
	 */
	public void setJsonInputFileName(ClassAndLocation jsonInput) {
		this.jsonInput = jsonInput;
	}

	/** {@inheritDoc} */
	@Override
	public void setOriginalDocument(OfficeDocument officeDocument) {
		this.officeDocument = officeDocument;
	}

}
