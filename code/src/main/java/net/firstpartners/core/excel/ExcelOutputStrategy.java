package net.firstpartners.core.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.Config;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.core.file.ResourceFinder;
import net.firstpartners.core.file.Utils;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeList;

/**
 * Strategy class of output of Excel Document
 *
 * @author paul
 * @version $Id: $Id
 */
public class ExcelOutputStrategy implements IDocumentOutStrategy {


	Config appConfig;
	
	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// Name of the outputfile
	private String outputFileName = null;


	private Workbook workbook;

	/**
	 * Constructor - takes the name of the file we intend outputting to
	 *
	 * @param outputFileName a {@link java.lang.String} object
	 */
	public ExcelOutputStrategy(String outputFileName) {
		this.outputFileName = outputFileName;
	}


	/**
	 * {@inheritDoc}
	 *
	 * String representing where our output is going to
	 */
	@Override
	public String getOutputDestination() {
		return "File:" + outputFileName;
	}

	/**
	 * <p>Getter for the field <code>outputFileName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getOutputFileName() {
		return outputFileName;
	}

	/**
	 * <p>Getter for the field <code>workbook</code>.</p>
	 *
	 * @return a {@link org.apache.poi.ss.usermodel.Workbook} object
	 */
	public Workbook getWorkbook() {
		return workbook;
	}

	/**
	 * Outputs Red-Piranha's own internal format to a Logging Console
	 * 
	 * @param ranges
	 */
	void outputToConsole(RangeList ranges) {
		for (Range r : ranges) {
			log.debug(r.toString());
		}
	}

	/**
	 * Outputs an Apache POI Workbook to a Logging Console
	 * 
	 * @param wb
	 * @throws IOException
	 */
	void outputToConsole(Workbook wb) throws IOException {

		RangeList ranges = SpreadSheetConvertor.convertNamesFromPoiWorkbookIntoRedRange(wb);
		outputToConsole(ranges);

	}

	/**
	 * Outputs an Apache POI Workbook to a file
	 * 
	 * @param wb       - Apache POI Workbook (excel)
	 * @param fileName
	 * @throws IOException
	 */
	String outputToFile(Workbook wb) throws IOException {

		String outputFileDir = ResourceFinder.getDirectoryResourceUsingConfig(appConfig);
		
		//Construct the output file including directory
		String outputFile;
		if(outputFileDir!=""){
			outputFile = outputFileDir+"/"+outputFileName;
		} else {
			outputFile = outputFileName;
		}
		
		// Write out modified Excel sheet
		try {
			log.debug("trying to output Excel to:"+outputFile);
			FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
			outputToStream(wb, fileOutputStream);
			fileOutputStream.close();
	 	} catch (Exception ace) {
			// Unable to output file, then drop back and log via console instead
			log.error("Unable to output to file - logging to console and default dir instead",ace);
			outputToConsole(wb);
		 }
		
		 return outputFile

	}

	/**
	 * Outputs an Apache POI Workbook to a Stream (e.g Servlet response)
	 *
	 * @param wb a {@link org.apache.poi.ss.usermodel.Workbook} object
	 * @param stream a {@link java.io.OutputStream} object
	 * @throws java.io.IOException
	 */
	public void outputToStream(Workbook wb, OutputStream stream) throws IOException {

		wb.write(stream);
	}

	/**
	 * Process the output from the system
	 *
	 * @throws java.io.IOException
	 */
	public void processOutput() throws IOException {

		// delete the outputFile if it exists
		Utils.deleteOutputFileIfExists(outputFileName);

		// Open the outputfile as a stream
		outputToFile(workbook);

	}

	/** {@inheritDoc} */
	public void setConfig(Config appConfig) {
		this.appConfig = appConfig;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Update a copy of our Original Document with new data
	 */
	public void setUpdates(OfficeDocument fileToProcess, RangeList range) throws IOException {

		this.workbook = fileToProcess.getOriginalAsPoiWorkbook();
		SpreadSheetConvertor.updateRedRangeintoPoiExcel(this.workbook, range);

	}

	/**
	 * <p>Setter for the field <code>workbook</code>.</p>
	 *
	 * @param workbook a {@link org.apache.poi.ss.usermodel.Workbook} object
	 */
	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

}
