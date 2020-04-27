package net.firstpartners.core.word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.core.log.SpreadSheetLogger;
import net.firstpartners.data.OfficeDocument;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeHolder;

/**
 * Strategy class of output of Excel Document
 * @author paul
 *
 */
public class WordOutputStrategy implements IDocumentOutStrategy {

	// Logger
	private static final Logger log = RpLogger.getLogger(WordOutputStrategy.class.getName());
	
	//Name of the outputfile
	private String outputFileName =null;
	

	private XWPFDocument wordDoc;
	
	/**
	 * Constructor - takes the name of the file we intend outputting to	
	 * @param outputFileName
	 */
	public WordOutputStrategy(String outputFileName) {
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


	/**
	 * @todo refector out this method or implement - doesn't really apply for word docs
	 */
	@Override
	public void flush() {
		
	}


	@Override
	public void flush(ILogger logger) {
		// TODO Auto-generated method stub
		
	}

	/**
	* @todo refector out this method or implement - doesn't really apply for word docs
	@Override
	public void flush(ILogger logger) {
		
	}
	

	/**
	 * String representing where our output is going to
	 */
	@Override
	public String getOutputDestination() {
		return "File:"+outputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public XWPFDocument getWordDoc() {
		return wordDoc;
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
	void outputToConsole(XWPFDocument wb) throws IOException{

		RangeHolder ranges = DocumentConvertor.convertFromPoiWordIntoRedRange(wb);
		outputToConsole(ranges);

	}

	/**
	 * Outputs an Apache POI Workbook to a file
	 * @param wb - Apache POI Workbook (excel)
	 * @param fileName
	 * @throws IOException
	 */
	void outputToFile(XWPFDocument wb) throws IOException{

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
	public void outputToStream(XWPFDocument wb,OutputStream stream) throws IOException{

		wb.write(stream); 
	}
	
	/**
	 * Process the output from the system
	 * @param fileToProcess
	 * @param outputFileName
	 * @throws IOException
	 */
	public void processOutput() throws IOException {
		
		// delete the outputFile if it exists
		deleteOutputFileIfExists();

		// Open the outputfile as a stream
		outputToFile(wordDoc);

	}

	/**
      @todo refector out this method or implement - doesn't really apply for word docs
	 */
	@Override
	public void setDocumentLogger(SpreadSheetLogger spreadSheetLogger) {
	
		
	}

	
	public void setWorkbook(XWPFDocument workbook) {
		this.wordDoc = workbook;
	}


	/**
	 * Update a copy of our Original Document with new data
	 * @param ranges
	 * @throws IOException
	 */
	public void updateCopyOfOriginalDocument(OfficeDocument fileToProcess,RangeHolder range) throws IOException {
		
		this.wordDoc =fileToProcess.getOriginalAsPoiWordDoc();
		DocumentConvertor.updateRedRangeintoPoiWord(wordDoc, range);
		
	}

}
