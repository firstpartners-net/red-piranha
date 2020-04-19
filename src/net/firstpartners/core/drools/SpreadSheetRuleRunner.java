package net.firstpartners.core.drools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.core.drools.data.RuleSource;
import net.firstpartners.core.log.IDataSnapshot;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.core.log.SpreadSheetLogger;
import net.firstpartners.core.spreadsheet.Range;
import net.firstpartners.core.spreadsheet.RangeConvertor;
import net.firstpartners.core.spreadsheet.RangeHolder;

/**
 * Common Entry point to both Servlet and command line,
 * Call JBoss Drools (Rules Engine) passing in Spreadsheet data as Java Objects
 */
public class SpreadSheetRuleRunner {

	//Handle to the logger
	private static final Logger log = RpLogger.getLogger(SpreadSheetRuleRunner.class
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
	 * Call the rules engine - using the Excel Data provided
	 * @param inputFromExcel
	 * @param ruleSource
	 * @param nameOfLogSheet
	 * @return
	 * @throws DroolsParserException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidFormatException
	 */
	public Workbook callRules(InputStream inputFromExcel, RuleSource ruleSource,
			String nameOfLogSheet) throws DroolsParserException, IOException, ClassNotFoundException, InvalidFormatException {
		
			return callRules(inputFromExcel,ruleSource,nameOfLogSheet,null);
	}

	/**
	 * Call the rules engine - using the Excel Data provided
	 * @param inputFromExcel
	 *            - the excel data sheet as already opened as a Java Stream
	 * @param args
	 * @param nameOfLogSheet
	 * @param userDataDisplay - if not null, we can log log items back to the user (such as pre and post excel data)
	 * @return
	 * @throws DroolsParserException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidFormatException 
	 * 
	 */
	public Workbook callRules(InputStream inputFromExcel, RuleSource ruleSource,
			String nameOfLogSheet, IDataSnapshot userDataDisplay) throws DroolsParserException, IOException, ClassNotFoundException, InvalidFormatException {

		// Create a new Excel Logging object
		SpreadSheetLogger spreadsheetLogger = new SpreadSheetLogger();

		// Convert this into a (POI) Workbook
		Workbook wb=WorkbookFactory.create(inputFromExcel);
		
		// Convert the cell and log if we have a handle
		RangeHolder ranges = RangeConvertor.convertNamesFromPoiWorkbookIntoRedRange(wb);
		if(userDataDisplay!=null) {
			userDataDisplay.showPreRulesSnapShot(ranges);
		}

		//Call the overloaded method to actually run the rules and log output if we have a handle
		callRules(ranges,ruleSource,nameOfLogSheet,spreadsheetLogger);
		if(userDataDisplay!=null) {
			userDataDisplay.showPostRulesSnapShot(ranges);
		}
		

		// update the excel spreadsheet with the result of our rules
		RangeConvertor.updateRedRangeintoPoiExcel(wb, ranges);

		// update the excel spreadsheet with our log file
		spreadsheetLogger.flush(wb, nameOfLogSheet);

		// Close our input work book
		inputFromExcel.close();

		// Return the workbook
		return wb;

	}

	/**
	 * Call Rules against and Excel data file, then return workbook 
	 * @param urlOfExcelDataFile
	 * @param ruleSource
	 * @param excelLogSheet
	 * @return
	 * @throws DroolsParserException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidFormatException 
	 */
	public Workbook callRules(URL urlOfExcelDataFile, RuleSource ruleSource,
			String excelLogSheet) throws DroolsParserException, IOException, ClassNotFoundException, InvalidFormatException  {

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
	 * Call Rules against and Excel data file, then return workbook 
	 * @param locationOfExcelDataFile
	 * @param ruleSource
	 * @param excelLogSheet
	 * @return
	 * @throws IOException
	 * @throws DroolsParserException
	 * @throws ClassNotFoundException
	 * @throws InvalidFormatException 
	 */
	public Workbook callRules(File locationOfExcelDataFile, RuleSource ruleSource,
			String excelLogSheet) throws IOException, DroolsParserException, ClassNotFoundException, InvalidFormatException {

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