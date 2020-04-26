package net.firstpartners.core.spreadsheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import net.firstpartners.RedConstants;
import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.core.log.SpreadSheetLogger;
import net.firstpartners.data.RangeHolder;

/**
 * Specific steps needed for feeding Excel Documents into and out of the Rule Engine
 * @author PBrowne
 *
 */
public class ExcelInputStrategy implements IDocumentInStrategy {

	
	//Handle to the logger
	private static final Logger log = RpLogger.getLogger(ExcelInputStrategy.class
			.getName());
	
	private String excelInputFileName =null;
	private Workbook excelWorkBook =null;
	private SpreadSheetLogger spreadSheetLogger;
	
	/**
	 * Construct a new Strategy Object
	 * @param excelInputFileName
	 */
	public ExcelInputStrategy(String excelInputFileName) {
		this.excelInputFileName=excelInputFileName;
	}
	
	
	/**
	 * Write out any documents we hold to anybody else interested
	 * @param logger
	 */
	@Override
	public void flush(ILogger logger) {
		this.flush();
		this.spreadSheetLogger.flush(logger);
		
	}

	/**
	 * Write out and logs we hold to the document
	 * @param excelWorkBook
	 * @param nameOfLogSheet
	 */
	@Override
	public void flush() {
		this.spreadSheetLogger.flush(excelWorkBook,RedConstants.EXCEL_LOG_WORKSHEET_NAME);
		
	}

	public String getInputName() {
		return excelInputFileName;
	}

	@Override
	public Workbook getExcelWorkBook() {
		return excelWorkBook;
	}

	/**
	 * Access a Stream, convert it to Red JavaBeans (representing Excel Ojbects)
	 * @param inputFromExcel
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	@Override
	
	public RangeHolder getJavaBeansFromSource()  throws EncryptedDocumentException, IOException {
		
		InputStream inputAsStream = new FileInputStream(this.excelInputFileName);
		
		log.debug("converting incoming excel stream to Javabeans");
		excelWorkBook=WorkbookFactory.create(inputAsStream);
		RangeHolder myRange= RangeConvertor.convertNamesFromPoiWorkbookIntoRedRange(excelWorkBook);
		inputAsStream.close();
		return myRange;
		
	}

	/**
	 * Allows us to set a Logger that will flush to an Excel Spreadheet
	 * @param spreadSheetLogger
	 */
	@Override
	public void setDocumentLogger(SpreadSheetLogger spreadSheetLogger) {
		this.spreadSheetLogger = spreadSheetLogger;
		
	}

	public void setExcelInputFileName(String excelInputFileName) {
		this.excelInputFileName = excelInputFileName;
	}
	@Override
	public void setExcelWorkBook(Workbook excelWorkBook) {
		this.excelWorkBook = excelWorkBook;
	}

	/**
	 * Update our Original Document with new data
	 * @param ranges
	 * @throws IOException
	 */
	@Override
	public void updateOriginalDocument(RangeHolder ranges) throws IOException {
		RangeConvertor.updateRedRangeintoPoiExcel(excelWorkBook, ranges);
		
	}

}
