package net.firstpartners.core;

import java.io.IOException;

import net.firstpartners.core.excel.SpreadSheetConvertor;
import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.SpreadSheetLogger;
import net.firstpartners.data.RangeList;

/**
 * Strategy Class for Output of Documents to memory Doesn't actually output
 * anything, but holds data so we can unit test.
 * 
 * @author paul
 *
 */
public class MemoryOutputStrategy implements IDocumentOutStrategy {

	private ILogger docLogger;

	// Name of the outputfile
	private OfficeDocument processedDoc = null;

	/**
	 * Write out and logs we hold to the document
	 * 
	 */
	@Override
	public void flush() {

	}

	/**
	 * Write out any documents we hold to anybody else interested
	 * 
	 * @param logger
	 */
	@Override
	public void flush(ILogger logger) {

		if (logger instanceof SpreadSheetLogger) {

			((SpreadSheetLogger) this.docLogger).flush(logger);

		}
	}

	/**
	 * Makes more sense for other implementions of this interface - tells users
	 * where the data is going
	 */
	@Override
	public String getOutputDestination() {

		return "Held in Memory";
	}

	public OfficeDocument getProcessedDocument() {
		return processedDoc;
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
	 * Allows us to set a Logger that will flush to an Document such as Spreadheet
	 * 
	 * @param spreadSheetLogger
	 */
	@Override
	public void setDocumentLogger(ILogger spreadSheetLogger) {
		this.docLogger = spreadSheetLogger;

	}

	protected void setProcessedDocument(OfficeDocument processedWorkbook) {
		this.processedDoc = processedWorkbook;
	}

	@Override
	public void setUpdates(OfficeDocument fileToProcess, RangeList ranges) throws IOException {
		this.processedDoc = fileToProcess;
		if (fileToProcess.isSpreadsheet()) {
			SpreadSheetConvertor.updateRedRangeintoPoiExcel(fileToProcess.getOriginalAsPoiWorkbook(), ranges);
		}
	}

}
