package net.firstpartners.core.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.data.RangeHolder;

/**
 * Specific steps needed for feeding Excel Documents into and out of the Rule
 * Engine
 * 
 * @author PBrowne
 *
 */
public class ExcelInputStrategy implements IDocumentInStrategy {

	// Handle to the logger
	private static final Logger log = RpLogger.getLogger(ExcelInputStrategy.class.getName());

	private String excelInputFileName = null;
	private Workbook excelWorkBook = null;

	/**
	 * Construct a new Strategy Object
	 * 
	 * @param excelInputFileName
	 */
	public ExcelInputStrategy(String excelInputFileName) {
		this.excelInputFileName = excelInputFileName;
	}

	@Override
	public Workbook getExcelWorkBook() {
		return excelWorkBook;
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

	public RangeHolder getJavaBeansFromSource() throws EncryptedDocumentException, IOException {

		InputStream inputAsStream = new FileInputStream(this.excelInputFileName);

		log.debug("converting incoming excel stream to Javabeans");
		excelWorkBook = WorkbookFactory.create(inputAsStream);
		RangeHolder myRange = RangeConvertor.convertNamesFromPoiWorkbookIntoRedRange(excelWorkBook);
		inputAsStream.close();
		return myRange;

	}

	public void setExcelInputFileName(String excelInputFileName) {
		this.excelInputFileName = excelInputFileName;
	}

	@Override
	public void setExcelWorkBook(Workbook excelWorkBook) {
		this.excelWorkBook = excelWorkBook;
	}

}
