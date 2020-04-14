package net.firstpartners.core.drools;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import net.firstpartners.core.drools.data.RuleSource;

public class SpreadsheetServletRuleRunnerTest {

	// Logger
	private static final Logger log = Logger.getLogger(SpreadsheetServletRuleRunnerTest.class.getName());

	// Handle to common utility file
	private final SpreadSheetRuleRunner commonUtils = new SpreadSheetRuleRunner(new URLRuleLoader());

	// Tmp rules and data file names
	public static final String EXCEL_DATA_FILE = "http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/chocolate-data.xls";
	public static final String[] RULES_FILES = new String[] {
			"http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/log-then-modify-rules.drl" };

	public static final String KNOWLEDGE_BASE_FILE = "http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/log-then-modify-rules.KnowledgeBase";

	// the name of the sheet the we log files to
	private static final String EXCEL_LOG_WORKSHEET_NAME = "log";

	@Test
	public final void testCompileRunDrlFromUrl() throws Exception {

		// Get the URL
		URL url = new URL(EXCEL_DATA_FILE);
		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(RULES_FILES);

		HSSFWorkbook wb;
		wb = commonUtils.callRules(url, ruleSource, EXCEL_LOG_WORKSHEET_NAME);

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
		URL url = new URL(EXCEL_DATA_FILE);

		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(RULES_FILES);

		HSSFWorkbook wb;
		wb = commonUtils.callRules(url, ruleSource, EXCEL_LOG_WORKSHEET_NAME);

	}

}
