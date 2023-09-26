package net.firstpartners.core.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.firstpartners.core.Config;
import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.core.file.ResourceFinder;
import net.firstpartners.data.RangeList;

/**
 * Specific steps needed for feeding Excel Documents into and out of the Rule
 * Engine
 *
 * @author PBrowne
 * @version $Id: $Id
 */
public class ExcelInputStrategy implements IDocumentInStrategy {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private String excelInputFileName = null;
	private Workbook excelWorkBook = null;

	private Config appConfig;

	private String baseDirectory;

	/**
	 * Construct a new Strategy Object
	 *
	 * @param excelInputFileName a {@link java.lang.String} object
	 */
	public ExcelInputStrategy(String excelInputFileName) {
		this.excelInputFileName = excelInputFileName;
	}

	/**
	 * Set the base Directory
	 */
	public void setBaseDirectory(String baseDirectory){
		this.baseDirectory= baseDirectory;
	}
	
	/** {@inheritDoc} */
	public void setConfig(Config appConfig) {
		this.appConfig = appConfig;
	}

	/** {@inheritDoc} */
	@Override
	public OfficeDocument getOriginalDocument() {
		return new OfficeDocument(excelWorkBook);
	}

	/**
	 * <p>getInputName.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getInputName() {
		return excelInputFileName;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Access a Stream, convert it to Red JavaBeans (representing Excel Ojbects)
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	@Override
	public RangeList getJavaBeansFromSource() throws EncryptedDocumentException, IOException, ResourceException, ScriptException {

		File xlFile = ResourceFinder.getFileResourceUsingConfig(excelInputFileName, appConfig);
		
		InputStream inputAsStream = new FileInputStream(xlFile);

		log.debug("converting incoming excel stream to Javabeans");
		excelWorkBook = WorkbookFactory.create(inputAsStream);
		RangeList myRange = SpreadSheetConvertor.convertNamesFromPoiWorkbookIntoRedRange(baseDirectory,appConfig.getPreprocessScript(),excelWorkBook);
		inputAsStream.close();
		return myRange;

	}

	/**
	 * <p>Setter for the field <code>excelInputFileName</code>.</p>
	 *
	 * @param excelInputFileName a {@link java.lang.String} object
	 */
	public void setExcelInputFileName(String excelInputFileName) {
		this.excelInputFileName = excelInputFileName;
	}

	/** {@inheritDoc} */
	@Override
	public void setOriginalDocument(OfficeDocument excelWorkBook) {
		this.excelWorkBook = excelWorkBook.getOriginalAsPoiWorkbook();
	}

}
