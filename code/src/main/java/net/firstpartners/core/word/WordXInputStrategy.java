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

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.drools.ClassAndLocation;
import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.core.file.ResourceFinder;
import net.firstpartners.data.RangeList;

/**
 * Specific steps needed for feeding WordX (latest Office) Documents into and
 * out of the Rule
 * Engine
 *
 * @author PBrowne
 * @version $Id: $Id
 */
public class WordXInputStrategy implements IDocumentInStrategy {

	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private XWPFDocument poiDoc = null;

	private ClassAndLocation inputDetails = null;

	private String subDirectory;

	/**
	 * Construct a new Strategy Object
	 *
	 * @param wordxInputFileName that we are reading from
	 */
	public WordXInputStrategy(ClassAndLocation inputDetails) {
		this.inputDetails = inputDetails;
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
		return inputDetails;
	}

	public void setInputDetails(ClassAndLocation inputDetails) {
		this.inputDetails = inputDetails;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Access a Stream, convert it to Red JavaBeans (representing Excel Ojbects)
	 */
	@Override
	public RangeList getJavaBeansFromSource() throws EncryptedDocumentException, IOException, InvalidFormatException {

		log.debug("converting incoming word stream to Javabeans");
		File wordFile = ResourceFinder.getFile(this.inputDetails);

		InputStream inputAsStream = new FileInputStream(wordFile);
		poiDoc = new XWPFDocument(OPCPackage.open(inputAsStream));

		RangeList myRange = DocumentXConvertor.convertFromPoiWordIntoRedRange(poiDoc);
		inputAsStream.close();

		return myRange;

	}

	/** {@inheritDoc} */
	@Override
	public OfficeDocument getOriginalDocument() {
		return new OfficeDocument(poiDoc);
	}

	/**
	 * <p>
	 * setInputFileName.
	 * </p>
	 *
	 * @param wordInputFileName a {@link java.lang.String} object
	 */
	public void setInputFileName(ClassAndLocation inputDetails) {
		this.inputDetails = inputDetails;
	}

	/** {@inheritDoc} */
	@Override
	public void setOriginalDocument(OfficeDocument originalDoc) {
		this.poiDoc = originalDoc.getOriginalAsPoiWordDoc();
	}

	@Override
	public void complete() {
		this.poiDoc=null;
		this.inputDetails=null;
	}


}
