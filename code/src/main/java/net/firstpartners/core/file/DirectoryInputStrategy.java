package net.firstpartners.core.file;

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
import net.firstpartners.data.RangeList;

/**
 * Class to read info on our samples from a JSON file.
 * It means we can be consistent between the samples we display in the WebApp
 * And the samples we use in our integration tests
 *
 * @author paulf
 * @version $Id: $Id
 */
public class DirectoryInputStrategy implements IDocumentInStrategy {

	
	private Config appConfig;

	private String directoryInputFileName;
	
	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private String subDirectory;

	/**
	 * Construct a new Input Strategy Object
	 *
	 * @param jsonInputFileName a {@link java.lang.String} object
	 */
	public DirectoryInputStrategy(String jsonInputFileName) {
		this.directoryInputFileName = jsonInputFileName;
	}

	public String getSubDirectory() {
		return subDirectory;
	}

	/**
	 * Set the base Directory
	 */
	public void setSubDirectory(String subDirectory){
		this.subDirectory= subDirectory;
	}

	/** {@inheritDoc} */
	@Override
	public String getInputName() {
		return directoryInputFileName;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Access a Stream, convert it to Red JavaBeans (representing grid like data)
	 */
	@Override
	public RangeList getJavaBeansFromSource() throws EncryptedDocumentException, IOException, InvalidFormatException {


		//TODO ## still to be Converted
		//TODO ## if we loop then *all* beans will be loaded into memory, then output

		log.debug("Convert Json to RedBeans");
		ObjectMapper objectMapper = new ObjectMapper();
		File jsonSource = ResourceFinder.getFileResourceUsingConfig(directoryInputFileName, appConfig);
		
		RangeList myRange  = objectMapper.readValue(jsonSource, RangeList.class);
		JsonNode rootNode = objectMapper.readTree(jsonSource);
		
		//beware - the following can generate a lot of Json into the log! 
		//log.debug("Found:"+rootNode);
		rootNode =null;
		

		
		return myRange;
	}

	/**
	 * <p>Getter for the field <code>jsonInputFileName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getDirectoryInputFileName() {
		return directoryInputFileName;
	}



	/** {@inheritDoc} */
	@Override
	public OfficeDocument getOriginalDocument() {
		return null;
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
	public void setDirectoryInputFileName(String jsonInputFileName) {
		this.directoryInputFileName = jsonInputFileName;
	}


	/** {@inheritDoc} */
	@Override
	public void setOriginalDocument(OfficeDocument officeDocument) {
		// Deliberately null
	}


}

