package net.firstpartners.core.json;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.data.RangeList;

/**
 * Class to read info on our samples from a JSON file.
 * It means we can be consistent between the samples we display in the WebApp
 * And the samples we use in our integration tests
 * @author paulf
 *
 */
public class JsonInputStrategy implements IDocumentInStrategy {

	
	private String jsonInputFileName;

	/**
	 * Construct a new Strategy Object
	 * 
	 * @param jsonInputFileName
	 */
	public JsonInputStrategy(String jsonInputFileName) {
		this.jsonInputFileName = jsonInputFileName;
	}
	
	@Override
	public OfficeDocument getOriginalDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInputName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RangeList getJavaBeansFromSource() throws EncryptedDocumentException, IOException, InvalidFormatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOriginalDocument(OfficeDocument originalDoc) {
		// TODO Auto-generated method stub
		
	}

}
