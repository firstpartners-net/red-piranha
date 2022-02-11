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
 *
 */
public class ExcelInputStrategy implements IDocumentInStrategy {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private String excelInputFileName = null;
	private Workbook excelWorkBook = null;

	private Config appConfig;

	public void setConfig(Config appConfig) {
		this.appConfig = appConfig;
	}
	
	/**
	 * Construct a new Strategy Object
	 * 
	 * @param excelInputFileName
	 */
	public ExcelInputStrategy(String excelInputFileName) {
		this.excelInputFileName = excelInputFileName;
	}

	@Override
	public OfficeDocument getOriginalDocument() {
		return new OfficeDocument(excelWorkBook);
	}

	public String getInputName() {
		return excelInputFileName;
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

	public RangeList getJavaBeansFromSource() throws EncryptedDocumentException, IOException {

		File xlFile = ResourceFinder.getFileResourceUsingConfig(excelInputFileName, appConfig);
		
		InputStream inputAsStream = new FileInputStream(xlFile);

		log.debug("converting incoming excel stream to Javabeans");
		excelWorkBook = WorkbookFactory.create(inputAsStream);
		RangeList myRange = SpreadSheetConvertor.convertNamesFromPoiWorkbookIntoRedRange(excelWorkBook);
		inputAsStream.close();
		return myRange;

	}

	public void setExcelInputFileName(String excelInputFileName) {
		this.excelInputFileName = excelInputFileName;
	}

	@Override
	public void setOriginalDocument(OfficeDocument excelWorkBook) {
		this.excelWorkBook = excelWorkBook.getOriginalAsPoiWorkbook();
	}

}
