package net.firstpartners.core.json;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.firstpartners.core.Config;
import net.firstpartners.core.IDocumentInStrategy;
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

	
	private Config appConfig;

	private String jsonInputFileName;
	
	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private OfficeDocument officeDocument;
	
	

	/**
	 * Construct a new Strategy Object
	 *
	 * @param jsonInputFileName a {@link java.lang.String} object
	 */
	public JsonInputStrategy(String jsonInputFileName) {
		this.jsonInputFileName = jsonInputFileName;
	}

	/** {@inheritDoc} */
	@Override
	public String getInputName() {
		return jsonInputFileName;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Access a Stream, convert it to Red JavaBeans (representing grid like data)
	 */
	@Override
	public RangeList getJavaBeansFromSource() throws EncryptedDocumentException, IOException, InvalidFormatException {

		ObjectMapper objectMapper = new ObjectMapper();

		File jsonSource = ResourceFinder.getFileResourceUsingConfig(jsonInputFileName, appConfig);
		
		//RangeList myRange  = objectMapper.readValue(jsonSource, RangeList[].class);
		JsonNode rootNode = objectMapper.readTree(jsonSource);
		log.debug("Found:"+rootNode);
		
		log.debug("Convert Json to RedBeans");
		
		return null;
	}

	/**
	 * <p>Getter for the field <code>jsonInputFileName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getJsonInputFileName() {
		return jsonInputFileName;
	}



	/** {@inheritDoc} */
	@Override
	public OfficeDocument getOriginalDocument() {
		return officeDocument;
	}



	/** {@inheritDoc} */
	public void setConfig(Config appConfig) {
		this.appConfig = appConfig;
	}

	/**
	 * <p>Setter for the field <code>jsonInputFileName</code>.</p>
	 *
	 * @param jsonInputFileName a {@link java.lang.String} object
	 */
	public void setJsonInputFileName(String jsonInputFileName) {
		this.jsonInputFileName = jsonInputFileName;
	}


	/** {@inheritDoc} */
	@Override
	public void setOriginalDocument(OfficeDocument officeDocument) {
		this.officeDocument = officeDocument;
	}


}

