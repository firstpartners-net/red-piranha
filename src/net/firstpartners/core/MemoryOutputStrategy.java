package net.firstpartners.core;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

import net.firstpartners.RedConstants;
import net.firstpartners.core.excel.RangeConvertor;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.SpreadSheetLogger;
import net.firstpartners.data.RangeHolder;

/**
 * Strategy Class for Output of Documents to memory Doesn't actually output
 * anything, but holds data so we can unit test.
 * 
 * @author paul
 *
 */
public class MemoryOutputStrategy implements IDocumentOutStrategy {

	private SpreadSheetLogger docLogger;
	// Name of the outputfile
	private Workbook processedWorkbook = null;

	/**
	 * Write out and logs we hold to the document
	 * 
	 * @param excelWorkBook
	 * @param nameOfLogSheet
	 */
	@Override
	public void flush() {
		this.docLogger.flush(processedWorkbook, RedConstants.EXCEL_LOG_WORKSHEET_NAME);

	}

	/**
	 * Write out any documents we hold to anybody else interested
	 * 
	 * @param logger
	 */
	@Override
	public void flush(ILogger logger) {
		this.flush();
		this.docLogger.flush(logger);

	}

	/**
	 * Makes more sense for other implementions of this interface - tells users
	 * where the data is going
	 */
	@Override
	public String getOutputDestination() {

		return "Held in Memory";
	}

	protected Workbook getProcessedWorkbook() {
		return processedWorkbook;
	}

	/**
	 * Get the last outputted workbooks
	 * 
	 * @return
	 */
	public Object getWorkbook() {

		return processedWorkbook;
	}

	/**
	 * Hold the output file for later testing
	 * 
	 * @param fileToProcess
	 * @param outputFileName
	 * @throws IOException
	 */
	public void processOutput() throws IOException {

		// do nothing - we just keep it in momeory
	}

	/**
	 * Allows us to set a Logger that will flush to an Excel Spreadheet
	 * 
	 * @param spreadSheetLogger
	 */
	@Override
	public void setDocumentLogger(SpreadSheetLogger spreadSheetLogger) {
		this.docLogger = spreadSheetLogger;

	}

	protected void setProcessedWorkbook(Workbook processedWorkbook) {
		this.processedWorkbook = processedWorkbook;
	}

	/**
	 * Update a copy of our Original Document with new data
	 * 
	 * @param ranges
	 * @throws IOException
	 */
	public void updateCopyOfOriginalDocument(Workbook fileToProcess, RangeHolder range) throws IOException {

		this.processedWorkbook = fileToProcess;
		RangeConvertor.updateRedRangeintoPoiExcel(fileToProcess, range);

	}

}
