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
 * @version $Id: $Id
 */
public class WordXInputStrategy implements IDocumentInStrategy {

	private Config appConfig;

	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private XWPFDocument poiDoc = null;

	private String wordInputFileName = null;

	private String baseDirectory;

	/**
	 * Construct a new Strategy Object
	 *
	 * @param wordxInputFileName that we are reading from
	 */
	public WordXInputStrategy(String wordxInputFileName) {
		this.wordInputFileName = wordxInputFileName;
	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	/**
	 * Set the base Directory
	 */
	public void setBaseDirectory(String baseDirectory){
		this.baseDirectory= baseDirectory;
	}

	/** {@inheritDoc} */
	@Override
	public String getInputName() {
		return wordInputFileName;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Access a Stream, convert it to Red JavaBeans (representing Excel Ojbects)
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

	/** {@inheritDoc} */
	@Override
	public OfficeDocument getOriginalDocument() {
		return new OfficeDocument(poiDoc);
	}

	/** {@inheritDoc} */
	@Override
	public void setConfig(Config appConfig) {
		this.appConfig = appConfig;
	}

	/**
	 * <p>setInputFileName.</p>
	 *
	 * @param wordInputFileName a {@link java.lang.String} object
	 */
	public void setInputFileName(String wordInputFileName) {
		this.wordInputFileName = wordInputFileName;
	}

	/** {@inheritDoc} */
	@Override
	public void setOriginalDocument(OfficeDocument originalDoc) {
		this.poiDoc = originalDoc.getOriginalAsPoiWordDoc();
	}

}
