package net.firstpartners.core.word;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.data.OfficeDocument;
import net.firstpartners.data.RangeHolder;

/**
 * Specific steps needed for feeding Excel Documents into and out of the Rule
 * Engine
 * 
 * @author PBrowne
 *
 */
public class WordInputStrategy implements IDocumentInStrategy {

	// Handle to the loggers
	private static final Logger log = RpLogger.getLogger(WordInputStrategy.class.getName());

	private String wordInputFileName = null;
	private XWPFDocument poiDoc = null;

	/**
	 * Construct a new Strategy Object
	 * 
	 * @param wordInputFileName
	 */
	public WordInputStrategy(String excelInputFileName) {
		this.wordInputFileName = excelInputFileName;
	}

	@Override
	public OfficeDocument getOriginalDocument() {
		return new OfficeDocument(poiDoc);
	}

	public String getInputName() {
		return wordInputFileName;
	}

	/**
	 * Access a Stream, convert it to Red JavaBeans (representing Excel Ojbects)
	 * 
	 * @param inputFromExcel
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	@Override

	public RangeHolder getJavaBeansFromSource() throws EncryptedDocumentException, IOException {

		
		log.debug("converting incoming excel stream to Javabeans");
		throw new IllegalArgumentException("method not implmenetd");
		
//		InputStream inputAsStream = new FileInputStream(this.wordInputFileName);
//
//		
//		excelWorkBook = WorkbookFactory.create(inputAsStream);
//		RangeHolder myRange = DocumentConvertor.convertFromPoiWordIntoRedRange(excelWorkBook);
//		inputAsStream.close();
//		return myRange;

	}

	public void setInputFileName(String excelInputFileName) {
		this.wordInputFileName = excelInputFileName;
	}

	@Override
	public void setOriginalDocument(OfficeDocument originalDoc)
	{
		this.poiDoc = originalDoc.getOriginalAsPoiWordDoc();
	}

}
