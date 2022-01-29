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
 * @author paulf
 *
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
	 * @param jsonInputFileName
	 */
	public JsonInputStrategy(String jsonInputFileName) {
		this.jsonInputFileName = jsonInputFileName;
	}

	@Override
	public String getInputName() {
		return jsonInputFileName;
	}

	/**
	 * Access a Stream, convert it to Red JavaBeans (representing grid like data)
	 *
	 * @return RangeList of data from our json
	 * @throws EncryptedDocumentException
	 * @throws IOException
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

	public String getJsonInputFileName() {
		return jsonInputFileName;
	}



	@Override
	public OfficeDocument getOriginalDocument() {
		return officeDocument;
	}



	public void setConfig(Config appConfig) {
		this.appConfig = appConfig;
	}

	public void setJsonInputFileName(String jsonInputFileName) {
		this.jsonInputFileName = jsonInputFileName;
	}


	@Override
	public void setOriginalDocument(OfficeDocument officeDocument) {
		this.officeDocument = officeDocument;
	}


}

