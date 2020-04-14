package net.firstpartners.core.drools;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.drools.data.RuleSource;

public class SpreadsheetRunRunnerDataInExcelTest {

	// Logger
	private static final Logger log = Logger.getLogger(SpreadsheetRunRunnerDataInExcelTest.class.getName());

	// Handle to common utility file
	private final SpreadSheetRuleRunner commonUtils = new SpreadSheetRuleRunner(new URLRuleLoader());


	@Test
	public final void testCompileRunDrlFromUrl() throws Exception {

		// Get the URL
		URL url = new URL(TestConstants.XLS_DATA_FILE_AS_URL);
		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(TestConstants.RULES_FILES);

		HSSFWorkbook wb;
		wb = commonUtils.callRules(url, ruleSource, TestConstants.EXCEL_LOG_WORKSHEET_NAME);

	}

	@Test
	/**
	 * Just check that the rules can run, throws no exception
	 */
	public final void testCallRules() throws Exception {

		// Check where we are
		File whereAmI = new File(".");
		log.info("Default file location:" + whereAmI.getAbsolutePath());

		// Get the URL
		URL url = new URL(TestConstants.XLS_DATA_FILE);

		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(TestConstants.RULES_FILES);

		HSSFWorkbook wb;
		wb = commonUtils.callRules(url, ruleSource, TestConstants.EXCEL_LOG_WORKSHEET_NAME);

	}

}
