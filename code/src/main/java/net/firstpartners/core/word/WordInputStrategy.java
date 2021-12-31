package net.firstpartners.core.word;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.data.RangeList;

/**
 * Specific steps needed for feeding Word (pre 2003) Documents into and out of the Rule
 * Engine
 * 
 * @author PBrowne
 *
 */
public class WordInputStrategy implements IDocumentInStrategy {

	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private String wordInputFileName = null;
	private XWPFDocument poiDoc = null;

	/**
	 * Construct a new Strategy Object
	 * 
	 * @param wordInputFileName
	 */
	public WordInputStrategy(String wordInputFileName) {
		this.wordInputFileName = wordInputFileName;
	}

	@Override
	public OfficeDocument getOriginalDocument() {
		return new OfficeDocument(poiDoc);
	}

	public String getInputName() {
		return wordInputFileName;
	}

	/**
	 * Access a Stream, convert it to Red JavaBeans (representing Office Ojbects)
	 * 
	 * @return RangeList
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	@Override

	public RangeList getJavaBeansFromSource() throws EncryptedDocumentException, IOException, InvalidFormatException {

		log.debug("converting incoming word stream to Javabeans");
		
		InputStream inputAsStream = new FileInputStream(wordInputFileName);
		POIFSFileSystem fs = new POIFSFileSystem(inputAsStream);
		HWPFDocument poiDoc = new HWPFDocument(fs);
		
	
		RangeList myRange = DocumentConvertor.convertFromPoiWordIntoRedRange(poiDoc);
		inputAsStream.close();

		return myRange;

	}

	public void setInputFileName(String wordInputFileName) {
		this.wordInputFileName = wordInputFileName;
	}

	@Override
	public void setOriginalDocument(OfficeDocument originalDoc) {
		this.poiDoc = originalDoc.getOriginalAsPoiWordDoc();
	}

}
