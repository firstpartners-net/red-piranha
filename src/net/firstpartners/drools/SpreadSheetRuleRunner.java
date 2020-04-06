package net.firstpartners.drools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.drools.data.RuleSource;
import net.firstpartners.drools.log.ILogger;
import net.firstpartners.drools.log.SpreadSheetLogger;
import net.firstpartners.spreadsheet.Range;
import net.firstpartners.spreadsheet.RangeConvertor;
import net.firstpartners.spreadsheet.RangeHolder;

/**
 * Common Entry point to both Servlet and command line,
 * Unit Tests and samples for calling for rules manipulating Spreadsheet data
 */
public class SpreadSheetRuleRunner {

	//Handle to the logger
	private static final Logger log = Logger.getLogger(SpreadSheetRuleRunner.class
			.getName());

	//Handle to the Rule Runner we use
	RuleRunner ruleRunner;

	public SpreadSheetRuleRunner(IRuleLoader ruleLoader){
		ruleRunner = new RuleRunner(ruleLoader);
	}


	/**
	 *
	 * @param spreadsheetRange - Red Piranha representation of the spreadsheet format
	 * @param args
	 * @param nameOfLogSheet
	 * @return
	 * @throws DroolsParserException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public RangeHolder callRules(RangeHolder spreadsheetRange, RuleSource ruleSource,
			String nameOfLogSheet,ILogger logger) throws DroolsParserException, IOException, ClassNotFoundException {


		// Log the cell contents
		log.finer("============ Spreadsheet Cell Contents In =========");
		for (Range r : spreadsheetRange) {
			log.finer(r.toString());
		}

		//Add the Spreadsheet contents as facts
		ruleSource.addFacts(spreadsheetRange.getAllRangesAndCells());

		// Load and fire our rules files against the data
		ruleRunner.runStatelessRules(ruleSource, logger);

		// Log the cell contents
		log.finer("============ Spreadsheet Cell Contents Out =========");
		for (Range r : spreadsheetRange) {
			log.finer(r.toString());
		}


		return spreadsheetRange;
	}


	/**
	 *
	 * @param inputFromExcel
	 *            - the excel data sheet as already opened as a Java Stream
	 * @param args
	 * @param nameOfLogSheet
	 * @return
	 * @throws DroolsParserException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public HSSFWorkbook callRules(InputStream inputFromExcel, RuleSource ruleSource,
			String nameOfLogSheet) throws DroolsParserException, IOException, ClassNotFoundException {

		// Create a new Excel Logging object
		SpreadSheetLogger spreadsheetLogger = new SpreadSheetLogger();

		// Convert this into a (POI) Workbook
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inputFromExcel));

		// Convert the cell
		RangeHolder ranges = RangeConvertor.convertExcelToCells(wb);

		//Call the overloaded method to actually run the rules
		callRules(ranges,ruleSource,nameOfLogSheet,spreadsheetLogger);

		// update the excel spreadsheet with the result of our rules
		RangeConvertor.convertCellsToExcel(wb, ranges);

		// update the excel spreadsheet with our log file
		spreadsheetLogger.flush(wb, nameOfLogSheet);

		// Close our input work book
		inputFromExcel.close();

		// Return the workbook
		return wb;

	}

	/**
	 * Read an excel file and spit out what we find.
	 *
	 * Method is protected (not private) to allow for unit testing
	 *
	 * @param args
	 *            Expect one argument that is the file to read.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws DroolsParserException
	 * @throws Exception
	 * @throws Exception
	 */
	public HSSFWorkbook callRules(URL urlOfExcelDataFile, RuleSource ruleSource,
			String excelLogSheet) throws DroolsParserException, IOException, ClassNotFoundException  {

		InputStream inputFromExcel = null;

		try {
			log.info("Looking for url:" + urlOfExcelDataFile);

			inputFromExcel = urlOfExcelDataFile.openStream();

			log.info("found url:" + urlOfExcelDataFile);

		} catch (MalformedURLException e) {

			log.log(Level.SEVERE, "Malformed URL Exception Loading rules", e);
			throw e;

		} catch (IOException e) {
			log.log(Level.SEVERE, "IO Exception Loading rules", e);
			throw e;

		}

		return callRules(inputFromExcel, ruleSource, excelLogSheet);

	}

	/**
	 * Read an excel file and spit out what we find.
	 *
	 * Method is protected (not private) to allow for unit testing
	 *
	 * @param args
	 *            Expect one argument that is the file to read.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws DroolsParserException
	 * @throws Exception
	 * @throws Exception
	 */
	public HSSFWorkbook callRules(File locationOfExcelDataFile, RuleSource ruleSource,
			String excelLogSheet) throws IOException, DroolsParserException, ClassNotFoundException {

		InputStream inputFromExcel = null;

		//Sanity checks on the incoming file
		if (locationOfExcelDataFile==null){
			throw new IOException("java.io.File cannot be null");
		}

		if (!locationOfExcelDataFile.exists()){
			throw new IOException("no file at location:"+locationOfExcelDataFile.getAbsolutePath());
		}


		try {

			log.info("Looking for file:" + locationOfExcelDataFile.getAbsolutePath());
			inputFromExcel = new FileInputStream(locationOfExcelDataFile);

			log.info("found file:" + locationOfExcelDataFile);


		} catch (IOException e) {
			log.log(Level.SEVERE, "IO Exception Loading rules", e);
			throw e;

		}

		return callRules(inputFromExcel, ruleSource, excelLogSheet);

	}
}