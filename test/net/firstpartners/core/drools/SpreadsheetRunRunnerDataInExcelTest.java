package net.firstpartners.core.drools;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import org.apache.log4j.Logger;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.drools.loader.FileRuleLoader;
import net.firstpartners.core.drools.loader.RuleSource;

public class SpreadsheetRunRunnerDataInExcelTest {

	// Logger
	private static final Logger log = Logger.getLogger(SpreadsheetRunRunnerDataInExcelTest.class.getName());

	/**
	 * Just check that the rules can run, throws no exception
	 */
	@Test
	public final void testXlsCallRulesFromFile() throws Exception {

		SpreadSheetRuleRunner commonUtils = new SpreadSheetRuleRunner(new FileRuleLoader());

		// Check where we are
		File whereAmI = new File(".");
		log.info("Default file location:" + whereAmI.getAbsolutePath());

		// Get the XLS DAta file

		FileInputStream excelInput = new FileInputStream(TestConstants.XLS_DATA_FILE);

		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(TestConstants.RULES_FILES);

		Workbook wb = commonUtils.callRules(excelInput, ruleSource, TestConstants.EXCEL_LOG_WORKSHEET_NAME);
		assertNotNull(wb);

	}

	@Test
	/**
	 * Just check that the rules can run, throws no exception
	 */
	public final void testXlsXCallRulesFromFile() throws Exception {

		SpreadSheetRuleRunner commonUtils = new SpreadSheetRuleRunner(new FileRuleLoader());

		// Check where we are
		File whereAmI = new File(".");
		log.info("Default file location:" + whereAmI.getAbsolutePath());

		// Get the XLSX Datafile
		FileInputStream excelInput = new FileInputStream(TestConstants.XLSX_DATA_FILE);

		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(TestConstants.RULES_FILES);

		Workbook wb = commonUtils.callRules(excelInput, ruleSource, TestConstants.EXCEL_LOG_WORKSHEET_NAME);
		assertNotNull(wb);

	}

	
}
