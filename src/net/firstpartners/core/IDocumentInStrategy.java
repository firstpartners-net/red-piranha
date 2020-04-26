package net.firstpartners.core;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;

import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.SpreadSheetLogger;
import net.firstpartners.core.spreadsheet.ExcelInputStrategy;
import net.firstpartners.data.RangeHolder;
/**
 * Implements the Strategy Command Pattern so that we can read data from different sources
 * @see ExcelInputStrategy @see WordStrategy
 * @author PBrowne
 *
 */
public interface IDocumentInStrategy {

	Workbook getExcelWorkBook();

	void setExcelWorkBook(Workbook excelWorkBook);

	/**
	 * Allows us to set a Logger that will flush to an Excel Spreadheet
	 * @param spreadSheetLogger
	 */
	void setDocumentLogger(SpreadSheetLogger spreadSheetLogger);

	/**
	 * Write out and logs we hold to the document
	 * @param excelWorkBook
	 * @param nameOfLogSheet
	 */
	void flush(String nameOfLogSheet);

	/**
	 * Write out any documents we hold to anybody else interested
	 * @param logger
	 */
	void flush(ILogger logger);

	/**
	 * Access a Stream, convert it to Red JavaBeans (representing Excel Objects)
	 * @param inputFromExcel
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	RangeHolder getJavaBeansFromStream(InputStream inputFromExcel) throws EncryptedDocumentException, IOException;

	/**
	 * Update our Original Document with new data
	 * @param ranges
	 * @throws IOException
	 */
	void updateOriginalDocument(RangeHolder ranges) throws IOException;

}