package net.firstpartners.core.log;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * An Adaptor that allows us to log to an Excel File
 * 
 * @author paulbrowne
 *
 */
public class SpreadSheetLogger implements ILogger {

	// We also allow this to be configured to log to console
	private static final Logger log = RpLogger.getLogger(SpreadSheetLogger.class.getName());

	// For holding logging data until we can flush it
	List<String> loggedItems = new ArrayList<String>();

	public void debug(String output) {
		log.debug(output);
		loggedItems.add("finest:" + output);
	}

	/**
	 * Delete then recreate a log sheet in a workbook
	 * 
	 * @param wb - Apache Poi Workbook
	 * @param worksheetName - that will hold the log
	 */
	private void deleteCreateWorksheet(Workbook wb, String worksheetName) {

		try {
			wb.createSheet(worksheetName);
		} catch (IllegalArgumentException iae) {

			// worksheet already exists , so remove it
			Sheet sheet = wb.getSheet(worksheetName);

			int sheetIndex = wb.getSheetIndex(sheet);

			wb.removeSheetAt(sheetIndex);

			// now try creating it again
			wb.createSheet(worksheetName);

		}

	}

	/** 
	 * Log an Exception
	 */
	public void exception(String output, Throwable t) {
		log.error(output + t);
		loggedItems.add("exception:" + output + " " + t.getMessage());
	}

	/**
	 * prints all our previously logged items as text into a log stream
	 * 
	 * @param logger to print into
	 */
	public void flush(ILogger logger) {

		StringBuffer returnText = new StringBuffer();

		for (String s : loggedItems) {

			returnText.append(s);
			returnText.append("\n");
		}

		logger.info(returnText.toString());

	}

	/**
	 * Flush all the items that we have noted in the log into the workbook
	 * 
	 * @param wb
	 */
	public void flush(Workbook wb, String logWorkSheetName) {

		// Delete and recreate
		deleteCreateWorksheet(wb, logWorkSheetName);

		// Get our sheet
		Sheet sheet = wb.getSheet(logWorkSheetName);

		// Start at A1, now work our way down , adding the logging output

		short currentRow = 0;

		for (String s : loggedItems) {

			// Create a row and put some cells in it. Rows are 0 based.
			Row row = sheet.createRow(currentRow);

			// Create a cell and put a value in it.
			Cell cell = row.createCell((short) 0);
			cell.setCellValue(s);

			currentRow++;
		}

	}

	/**
	 * Log at Info Level
	 */
	public void info(String output) {
		log.debug(output);
		loggedItems.add("info:" + output);
	}

}
