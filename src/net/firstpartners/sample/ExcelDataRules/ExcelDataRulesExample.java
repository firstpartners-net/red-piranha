package net.firstpartners.sample.ExcelDataRules;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.core.drools.FileRuleLoader;
import net.firstpartners.core.drools.IRuleLoader;
import net.firstpartners.core.drools.SpreadSheetRuleRunner;
import net.firstpartners.core.drools.data.RuleSource;
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
public class ExcelDataRulesExample {

	private static Log log = LogFactory.getLog(ExcelDataRulesExample.class);

	private static final String EXCEL_DATA_FILE = "war/sampleresources/ExcelDataRules/chocolate-data.xls";


	// the name of the sheet the we log files to
	private static final String EXCEL_LOG_WORKSHEET_NAME = "log";

	private static final String[] RULES_FILES = new String[] { "war/sampleresources/ExcelDataRules/log-rules.drl",
			"war/sampleresources/ExcelDataRules/TradingRules.xls" };

	/**
	 * Read an excel file and spit out what we find.
	 *
	 * @param args Expect one argument that is the file to read.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		ExcelDataRulesExample thisSample = new ExcelDataRulesExample();
		thisSample.runExcelDataRulesExample();
	}

	public Workbook runExcelDataRulesExample()
			throws IOException, DroolsParserException, ClassNotFoundException, InvalidFormatException {

		// Handle to the Spreadsheet Rule Runner and Rule file loader
		IRuleLoader ruleLoader = new FileRuleLoader();
		SpreadSheetRuleRunner ruleRunner = new SpreadSheetRuleRunner(ruleLoader);

		// Start Integrate in new RuleRunner
		// Identify where the rules are stored
		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(RULES_FILES);

		// Get the URL
		File excelDataFile = new File(EXCEL_DATA_FILE);

		// Call the rule engine passing in the excel data file, the rules we want to
		// use, and name of the spreadsheet that we log rules to
		log.info("Calling example");
		Workbook wb = ruleRunner.callRules(excelDataFile, ruleSource, EXCEL_LOG_WORKSHEET_NAME);

		// Output the workbook as a file,
		SpreadSheetOutputter.outputToFile(wb, EXCEL_LOG_WORKSHEET_NAME);

		// Return the same workbook to the calling method
		return wb;

	}

}
