package net.firstpartners.core;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;

import net.firstpartners.core.spreadsheet.ExcelInputStrategy;
import net.firstpartners.data.RangeHolder;

/**
 * Implements the Strategy Command Pattern so that we can read data from
 * different sources
 * 
 * @see ExcelInputStrategy @see WordStrategy
 * @author PBrowne
 *
 */
public interface IDocumentInStrategy {

	Workbook getExcelWorkBook();

	/**
	 * State where out input is coming from
	 * 
	 * @return
	 */
	String getInputName();

	/**
	 * Access a Stream, convert it to Red JavaBeans (representing Excel Objects)
	 * 
	 * @param inputFromExcel
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	RangeHolder getJavaBeansFromSource() throws EncryptedDocumentException, IOException;

	void setExcelWorkBook(Workbook excelWorkBook);

}