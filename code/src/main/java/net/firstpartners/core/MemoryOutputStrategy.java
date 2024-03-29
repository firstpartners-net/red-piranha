package net.firstpartners.core;

import java.io.IOException;
import java.util.Map;

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
 * @version $Id: $Id
 */
public class MemoryOutputStrategy implements IDocumentOutStrategy {

	
	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	// Name of the outputfile
	private OfficeDocument processedDoc = null;

	//sub directory e.g. for samples
	private String subDirectory;

		//Additional data we wish to output
	Map<String, String> additionalDataToInclude=null;

	//Associated Settor
	public void setAdditionalOutputData(Map<String, String> additionalData){
		this.additionalDataToInclude=additionalData;
	}


	public String getSubDirectory() {
		return subDirectory;
	}

	public void setSubDirectory(String subDirectory) {
		log.debug("Set Directory to:"+subDirectory);
		this.subDirectory = subDirectory;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Makes more sense for other implementions of this interface - tells users
	 * where the data is going
	 */
	@Override
	public String getOutputDestination() {

		return "Held in Memory";
	}


	/**
	 * <p>getProcessedDocument.</p>
	 *
	 * @return a {@link net.firstpartners.core.file.OfficeDocument} object
	 */
	public OfficeDocument getProcessedDocument() {
		return processedDoc;
	}

	/**
	 * Hold the output file for later testing
	 *
	 * @throws java.io.IOException
	 */
	public void processOutput() throws IOException {

		
	}

	/** {@inheritDoc} */
	@Override
	public void setUpdates(OfficeDocument fileToProcess, RangeList ranges) throws IOException {
		this.processedDoc = fileToProcess;
		if (fileToProcess.isSpreadsheet()) {
			SpreadSheetConvertor.updateRedRangeintoPoiExcel(fileToProcess.getOriginalAsPoiWorkbook(), ranges);
		}
	}

	/**
	 * <p>setProcessedDocument.</p>
	 *
	 * @param processedWorkbook a {@link net.firstpartners.core.file.OfficeDocument} object
	 */
	protected void setProcessedDocument(OfficeDocument processedWorkbook) {
		this.processedDoc = processedWorkbook;
	}

	@Override
	public void complete() {
		
	}


}
