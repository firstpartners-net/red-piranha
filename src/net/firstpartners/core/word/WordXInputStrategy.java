package net.firstpartners.core.word;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.data.RangeList;

/**
 * Specific steps needed for feeding Excel Documents into and out of the Rule
 * Engine
 * 
 * @author PBrowne
 *
 */
public class WordXInputStrategy implements IDocumentInStrategy {

	// Handle to the loggers
	private static final Logger log = RpLogger.getLogger(WordXInputStrategy.class.getName());

	private String wordInputFileName = null;
	private XWPFDocument poiDoc = null;

	/**
	 * Construct a new Strategy Object
	 * 
	 * @param wordInputFileName
	 */
	public WordXInputStrategy(String excelInputFileName) {
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
	 * @throws InvalidFormatException
	 */
	@Override

	public RangeList getJavaBeansFromSource() throws EncryptedDocumentException, IOException, InvalidFormatException {

		log.debug("converting incoming word stream to Javabeans");
		

		InputStream inputAsStream = new FileInputStream(this.wordInputFileName);
		poiDoc= new XWPFDocument(OPCPackage.open(inputAsStream));

		RangeList myRange = DocumentXConvertor.convertFromPoiWordIntoRedRange(poiDoc);
		inputAsStream.close();

		return myRange;

	}

	public void setInputFileName(String excelInputFileName) {
		this.wordInputFileName = excelInputFileName;
	}

	@Override
	public void setOriginalDocument(OfficeDocument originalDoc) {
		this.poiDoc = originalDoc.getOriginalAsPoiWordDoc();
	}

}
