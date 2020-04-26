package net.firstpartners.core.spreadsheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.log4j.Logger;

import org.apache.poi.ss.usermodel.Workbook;

import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeHolder;

/**
 * Strategy class of output of Excel Document
 * @author paul
 *
 */
public class ExcelOutputStrategy implements IDocumentOutStrategy {

	// Logger
	private static final Logger log = RpLogger.getLogger(ExcelOutputStrategy.class.getName());
	
	//Name of the outputfile
	private String outputFileName =null;
	
	/**
	 * Constructor - takes the name of the file we intend outputting to	
	 * @param outputFileName
	 */
	public ExcelOutputStrategy(String outputFileName) {
		this.outputFileName = outputFileName;
	}


	/**
	 * Delete the output file if it already exists
	 *
	 * @param outputFile
	 */
	public void deleteOutputFileIfExists() {

		File outputFile = new File(outputFileName);
		if (outputFile.exists()) {
			outputFile.delete();
		}

	}
	

	public String getOutputFileName() {
		return outputFileName;
	}

	/**
	 * Outputs Red-Piranha's own internal format to a Logging Console
	 * @param ranges
	 */
	void outputToConsole(RangeHolder ranges ){
		for (Range r : ranges) {
			log.info(r.toString());
		}
	}

	/**
	 * Outputs an Apache POI Workbook to a Logging Console
	 * @param wb
	 * @throws IOException
	 */
	void outputToConsole(Workbook wb) throws IOException{

		RangeHolder ranges = RangeConvertor.convertNamesFromPoiWorkbookIntoRedRange(wb);
		outputToConsole(ranges);

	}

	/**
	 * Outputs an Apache POI Workbook to a file
	 * @param wb - Apache POI Workbook (excel)
	 * @param fileName
	 * @throws IOException
	 */
	void outputToFile(Workbook wb) throws IOException{

		// Write out modified Excel sheet
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(
					this.outputFileName);
			outputToStream(wb,fileOutputStream);
			fileOutputStream.close();
		} catch (java.security.AccessControlException ace){
			//Unable to output file (e.g. as in Google App Engine) - drop back and log via console instead
			log.error("Unable to output to file - logging to console instead");
			outputToConsole(wb);
		}

	}

	
	/**
	 * Outputs an Apache POI Workbook to a Stream (e.g Servlet response)
	 * @param wb
	 * @param stream
	 * @throws IOException
	 */
	public void outputToStream(Workbook wb,OutputStream stream) throws IOException{

		wb.write(stream); 
	}
	
	/**
	 * Process the output from the system
	 * @param fileToProcess
	 * @param outputFileName
	 * @throws IOException
	 */
	public void processOutput(Workbook fileToProcess) throws IOException {
		
		// delete the outputFile if it exists
		deleteOutputFileIfExists();

		// Open the outputfile as a stream
		outputToFile(fileToProcess);

	}

	/**
	 * String representing where our output is going to
	 */
	@Override
	public String getOutputDestination() {
		return "File:"+outputFileName;
	}

}
