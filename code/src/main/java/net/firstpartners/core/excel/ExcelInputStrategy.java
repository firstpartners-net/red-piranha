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
import net.firstpartners.core.script.PreProcessor;
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

	private String subDirectory;

	/**
	 * Construct a new Strategy Object
	 *
	 * @param excelInputFileName a {@link java.lang.String} object
	 */
	public ExcelInputStrategy(String excelInputFileName) {
		this.excelInputFileName = excelInputFileName;
	}

	/**
	 * Set the sub Directory
	 */
	public void setSubDirectory(String subDirectory){
		this.subDirectory= subDirectory;
	}
	
	/** {@inheritDoc} */
	public void setConfig(Config appConfig) {
		log.debug("Config set");
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

		//check incoming values	
		assert appConfig!=null: "App Config should not be null";

		// load our Excel file and convert to our internal beans
		File xlFile = ResourceFinder.getFileResourceUsingConfig(excelInputFileName, appConfig);
		InputStream inputAsStream = new FileInputStream(xlFile);
		log.debug("converting incoming excel stream to Javabeans");
		excelWorkBook = WorkbookFactory.create(inputAsStream);

		//call the processor if available
		if(appConfig.getPreprocessScript()!=null){

			log.debug("Configured to use pre-processor subDir:"+subDirectory+" script:"+appConfig.getPreprocessScript());
			PreProcessor preProcess = new PreProcessor(appConfig);
			excelWorkBook= preProcess.preprocessXlWorkbook(subDirectory,appConfig.getPreprocessScript(), excelWorkBook);

		} else {
			log.debug("No pre-processor configured");
		}

		//Get handle and use convertor
		SpreadSheetConvertor convertor = new SpreadSheetConvertor(appConfig);
		RangeList myRange = convertor.convertNamesFromPoiWorkbookIntoRedRange(excelWorkBook);
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
