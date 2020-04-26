package net.firstpartners.core;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Strategy Class for Output of Documents to memory
 * Doesn't actually output anything, but holds data so we can unit test.
 * @author paul
 *
 */
public class MemoryOutputStrategy implements IDocumentOutStrategy {

	
	
	//Name of the outputfile
	private Workbook processedWorkbook =null;


		
	protected Workbook getProcessedWorkbook() {
		return processedWorkbook;
	}



	protected void setProcessedWorkbook(Workbook processedWorkbook) {
		this.processedWorkbook = processedWorkbook;
	}



	/**
	 * Hold the output file for later testing
	 * @param fileToProcess
	 * @param outputFileName
	 * @throws IOException
	 */
	public void processOutput(Workbook fileToProcess) throws IOException {
		
		this.processedWorkbook = fileToProcess;
	}


	/**
	 * Makes more sense for other implementions of this interface - tells users where the data is going
	 */
	@Override
	public String getOutputDestination() {
		
		return "Held in Memory";
	}


	/** 
	 * Get the last outputted workbooks
	 * @return
	 */
	public Object getWorkbook() {
		
		return processedWorkbook;
	}

}
