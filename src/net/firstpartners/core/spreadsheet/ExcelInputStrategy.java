package net.firstpartners.core.spreadsheet;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

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
	
	private SpreadSheetLogger spreadSheetLogger;
	private Workbook excelWorkBook =null;
	
	
	@Override
	public Workbook getExcelWorkBook() {
		return excelWorkBook;
	}

	@Override
	public void setExcelWorkBook(Workbook excelWorkBook) {
		this.excelWorkBook = excelWorkBook;
	}

	/**
	 * Allows us to set a Logger that will flush to an Excel Spreadheet
	 * @param spreadSheetLogger
	 */
	@Override
	public void setDocumentLogger(SpreadSheetLogger spreadSheetLogger) {
		this.spreadSheetLogger = spreadSheetLogger;
		
	}

	/**
	 * Write out and logs we hold to the document
	 * @param excelWorkBook
	 * @param nameOfLogSheet
	 */
	@Override
	public void flush(String nameOfLogSheet) {
		this.spreadSheetLogger.flush(excelWorkBook,nameOfLogSheet);
		
	}

	/**
	 * Write out any documents we hold to anybody else interested
	 * @param logger
	 */
	@Override
	public void flush(ILogger logger) {
		this.spreadSheetLogger.flush(logger);
		
	}
	/**
	 * Access a Stream, convert it to Red JavaBeans (representing Excel Ojbects)
	 * @param inputFromExcel
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	@Override
	public RangeHolder getJavaBeansFromStream(InputStream inputFromExcel) throws EncryptedDocumentException, IOException {
		log.debug("converting incoming excel stream to Javabeans");
		excelWorkBook=WorkbookFactory.create(inputFromExcel);
		return RangeConvertor.convertNamesFromPoiWorkbookIntoRedRange(excelWorkBook);
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
