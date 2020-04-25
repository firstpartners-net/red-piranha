package net.firstpartners.core.drools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import org.apache.log4j.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.drools.compiler.compiler.DroolsParserException;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.drools.data.RuleSource;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.core.log.SpreadSheetLogger;
import net.firstpartners.core.spreadsheet.Range;
import net.firstpartners.core.spreadsheet.RangeConvertor;
import net.firstpartners.core.spreadsheet.RangeHolder;
import net.firstpartners.core.spreadsheet.SpreadSheetOutputter;

/**
 * Sample showing how we can read both data and rules from Excel
 *
 * Read Ranges from Excel, Convert to a format that rules can use
 *
 * Based on Sample from Apache POI
 *
 * @author paulbrowne
 *
 */
public class RulesWrittenInExcelRuleRunnerTest {

	//Logging
		private static final Logger log = RpLogger.getLogger(RulesWrittenInExcelRuleRunnerTest.class.getName());



	@Test
	public void testRunRulesWithXlsFileInput() throws IOException, DroolsParserException, ClassNotFoundException, InvalidFormatException{

		FileRuleLoader ruleLoader = new FileRuleLoader();
		InputStream inputFromExcel = ruleLoader.getInputStream(TestConstants.XLS_DATA_FILE);

		// Open our Excel file using Apache Poi
		// This method searches for our file in a number of places on disk

		if (null == inputFromExcel) {
			throw new FileNotFoundException("Cannot find file:"
					+ TestConstants.XLS_DATA_FILE);
		} else {
			log.info("found file:" + TestConstants.XLS_DATA_FILE);
		}

		// Convert this into a (POI) Workbook
		Workbook wb=WorkbookFactory.create(inputFromExcel);

		// Convert the cell
		RangeHolder ranges = RangeConvertor.convertNamesFromPoiWorkbookIntoRedRange(wb);
		HashMap<String, Object> globals = new HashMap<String, Object>();

		// Create a new Excel Logging object
		SpreadSheetLogger excelLogger = new SpreadSheetLogger();

		// Log the cell contents
		log.debug("============ Excel Cell Contents In =========");
		for (Range r : ranges) {
			log.debug(r.toString());
		}

		//Setup our parameters
		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(TestConstants.RULES_FILES_EXCEL);
		ruleSource.setFacts(ranges.getAllRangesAndCells());
		ruleSource.setGlobals(globals);

		// Load and fire our rules files against the data
		new RuleRunner(new FileRuleLoader()).runStatelessRules(ruleSource,excelLogger);

		// Log the cell contents
		log.debug("============ Excel Cell Contents Out =========");
		for (Range r : ranges) {
			log.debug(r.toString());
		}

		// update the excel spreadsheet with the result of our rules
		RangeConvertor.updateRedRangeintoPoiExcel(wb, ranges);

		// update the excel spreadsheet with our log file
		excelLogger.flush(wb, TestConstants.EXCEL_LOG_WORKSHEET_NAME);

		// Write out modified Excel sheet
		SpreadSheetOutputter.outputToFile(wb, TestConstants.EXCEL_OUTPUT_FILE);

		// Close our input work book
		inputFromExcel.close();

		// complete
		log.info("Finished");

	}

}
