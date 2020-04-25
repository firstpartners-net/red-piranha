package net.firstpartners.sample.DslRuleflow;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import net.firstpartners.core.drools.FileRuleLoader;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.loader.RuleSource;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.core.log.SpreadSheetLogger;
import net.firstpartners.core.spreadsheet.RangeConvertor;
import net.firstpartners.core.spreadsheet.RangeHolder;
import net.firstpartners.core.spreadsheet.SpreadSheetOutputter;


/**
 * Sample showing how we can read and manipulate data from excel
 * Read Ranges from Excel, Convert to a format that rules can use
 * 
 * Based on Sample from Apache POI
 * 
 * @author paulbrowne
 * 
 */
public class NoRuleflowExample {

	// Logging
	private static final Logger log = RpLogger.getLogger(NoRuleflowExample.class.getName());

	private static final String EXCEL_DATA_FILE = "chocolate-data.xls";

	private static final String EXCEL_OUTPUT_FILE = "chocolate-output.xls";

	// the name of the sheet the we log files to
	private static final String EXCEL_LOG_WORKSHEET_NAME = "log";

	private static final String[] RULES_FILES = new String[] {
	"ruleflow-rules.drl"};




	/**
	 * Read an excel file and spit out what we find.
	 * 
	 * @param args
	 *            Expect one argument that is the file to read.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// Open our Excel file using Apache Poi
		// This method searches for our file in a number of places on disk
		InputStream inputFromExcel = RuleflowExample.class
		.getClassLoader().getResourceAsStream(EXCEL_DATA_FILE);

		if (null == inputFromExcel) {
			throw new FileNotFoundException("Cannot find file:"
					+ EXCEL_DATA_FILE);
		} else {
			log.info("found file:" + EXCEL_DATA_FILE);
		}

		// Convert this into a (POI) Workbook
		Workbook wb=WorkbookFactory.create(inputFromExcel);

		// Convert the cell
		RangeHolder ranges = RangeConvertor.convertNamesFromPoiWorkbookIntoRedRange(wb);
		HashMap<String, Object> globals = new HashMap<String, Object>();

		// Create a new Excel Logging object
		SpreadSheetLogger excelLogger = new SpreadSheetLogger();

		// Set Paramaters
		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(RULES_FILES);
		ruleSource.setFacts(ranges.getAllRangesAndCells());
		ruleSource.setGlobals(globals);


		// Load and fire our rules files against the data
		new RuleRunner(new FileRuleLoader()).runStatelessRules(ruleSource,excelLogger);


		// update the excel spreadsheet with the result of our rules
		RangeConvertor.updateRedRangeintoPoiExcel(wb, ranges);

		// update the excel spreadsheet with our log file
		excelLogger.flush(wb, EXCEL_LOG_WORKSHEET_NAME);

		// Write out modified Excel sheet
		SpreadSheetOutputter.outputToFile(wb, EXCEL_OUTPUT_FILE);

		// Close our input work book
		inputFromExcel.close();

		// complete
		log.info("Finished");
	}

}
