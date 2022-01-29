package net.firstpartners.core.excel;

import java.io.File;
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
import net.firstpartners.core.log.IStatusUpdate;
import net.firstpartners.core.log.SpreadSheetStatusUpdate;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeList;

/**
 * Strategy class of output of Excel Document
 * 
 * @author paul
 *
 */
public class ExcelOutputStrategy implements IDocumentOutStrategy {

	private static final String RULES_LOG = "rules-log";

	private Config appConfig;
	
	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// Name of the outputfile
	private String outputFileName = null;

	private IStatusUpdate spreadSheetLogger;

	private Workbook workbook;

	/**
	 * Constructor - takes the name of the file we intend outputting to
	 * 
	 * @param outputFileName
	 */
	public ExcelOutputStrategy(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	/**
	 * Write out and logs we hold to the document
	 * 
	 * @param excelWorkBook
	 * @param nameOfLogSheet
	 */
	@Override
	public void flush() {
		if (this.spreadSheetLogger instanceof SpreadSheetStatusUpdate) {

			((SpreadSheetStatusUpdate) this.spreadSheetLogger).flush(workbook, RULES_LOG);

		}

	}

	/**
	 * Write out any documents we hold to anybody else interested
	 * 
	 * @param logger
	 */
	@Override
	public void flush(IStatusUpdate logger) {
		this.flush();
		if (this.spreadSheetLogger instanceof SpreadSheetStatusUpdate) {

			((SpreadSheetStatusUpdate) this.spreadSheetLogger).flush(logger);
		}

	}

	/**
	 * String representing where our output is going to
	 */
	@Override
	public String getOutputDestination() {
		return "File:" + outputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

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
	void outputToFile(Workbook wb) throws IOException {

		File outputFile = ResourceFinder.getFileResourceUsingConfig(this.outputFileName, appConfig);
		
		// Write out modified Excel sheet
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
			outputToStream(wb, fileOutputStream);
			fileOutputStream.close();
		} catch (Exception ace) {
			// Unable to output file (e.g. as in Google App Engine) - drop back and log via
			// console instead
			log.error("Unable to output to file - logging to console instead");
			outputToConsole(wb);
		}

	}

	/**
	 * Outputs an Apache POI Workbook to a Stream (e.g Servlet response)
	 * 
	 * @param wb
	 * @param stream
	 * @throws IOException
	 */
	public void outputToStream(Workbook wb, OutputStream stream) throws IOException {

		wb.write(stream);
	}

	/**
	 * Process the output from the system
	 * 
	 * @param fileToProcess
	 * @param outputFileName
	 * @throws IOException
	 */
	public void processOutput() throws IOException {

		// delete the outputFile if it exists
		Utils.deleteOutputFileIfExists(outputFileName);

		// Open the outputfile as a stream
		outputToFile(workbook);

	}

	public void setConfig(Config appConfig) {
		this.appConfig = appConfig;
	}

	/**
	 * Allows us to set a Logger that will flush to an Excel Spreadheet
	 * 
	 * @param spreadSheetLogger
	 */
	@Override
	public void setDocumentLogger(IStatusUpdate spreadSheetLogger) {
		this.spreadSheetLogger = spreadSheetLogger;

	}

	/**
	 * Update a copy of our Original Document with new data
	 * 
	 * @param ranges
	 * @throws IOException
	 */
	public void setUpdates(OfficeDocument fileToProcess, RangeList range) throws IOException {

		this.workbook = fileToProcess.getOriginalAsPoiWorkbook();
		SpreadSheetConvertor.updateRedRangeintoPoiExcel(this.workbook, range);

	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

}
