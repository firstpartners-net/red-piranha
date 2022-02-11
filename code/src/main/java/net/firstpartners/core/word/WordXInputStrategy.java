package net.firstpartners.core.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.Config;
import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.core.file.ResourceFinder;
import net.firstpartners.data.RangeList;

/**
 * Specific steps needed for feeding WordX (latest Office) Documents into and out of the Rule
 * Engine
 *
 * @author PBrowne
 *
 */
public class WordXInputStrategy implements IDocumentInStrategy {

	private Config appConfig;

	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private XWPFDocument poiDoc = null;

	private String wordInputFileName = null;

	/**
	 * Construct a new Strategy Object
	 *
	 * @param wordxInputFileName that we are reading from
	 */
	public WordXInputStrategy(String wordxInputFileName) {
		this.wordInputFileName = wordxInputFileName;
	}

	@Override
	public String getInputName() {
		return wordInputFileName;
	}

	/**
	 * Access a Stream, convert it to Red JavaBeans (representing Excel Ojbects)
	 *
	 * @return RangeList
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	@Override

	public RangeList getJavaBeansFromSource() throws EncryptedDocumentException, IOException, InvalidFormatException {

		log.debug("converting incoming word stream to Javabeans");
		File wordFile = ResourceFinder.getFileResourceUsingConfig(this.wordInputFileName, appConfig);

		InputStream inputAsStream = new FileInputStream(wordFile);
		poiDoc= new XWPFDocument(OPCPackage.open(inputAsStream));

		RangeList myRange = DocumentXConvertor.convertFromPoiWordIntoRedRange(poiDoc);
		inputAsStream.close();

		return myRange;

	}

	@Override
	public OfficeDocument getOriginalDocument() {
		return new OfficeDocument(poiDoc);
	}

	@Override
	public void setConfig(Config appConfig) {
		this.appConfig = appConfig;
	}

	public void setInputFileName(String wordInputFileName) {
		this.wordInputFileName = wordInputFileName;
	}

	@Override
	public void setOriginalDocument(OfficeDocument originalDoc) {
		this.poiDoc = originalDoc.getOriginalAsPoiWordDoc();
	}

}
