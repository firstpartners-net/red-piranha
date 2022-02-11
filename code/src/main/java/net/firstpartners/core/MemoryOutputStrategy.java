package net.firstpartners.core;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.excel.SpreadSheetConvertor;
import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.data.RangeList;

/**
 * Strategy Class for Output of Documents to memory Doesn't actually output
 * anything, but holds data so we can unit test.
 * 
 * @author paul
 *
 */
public class MemoryOutputStrategy implements IDocumentOutStrategy {

	private Config appConfig;

	
	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	// Name of the outputfile
	private OfficeDocument processedDoc = null;


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
		log.debug("Config:"+appConfig);// to clear compiliation warning
		
	}

	public void setConfig(Config appConfig) {
		this.appConfig = appConfig;
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
