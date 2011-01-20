package net.firstpartners.drools;

import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import net.firstpartners.drools.SpreadSheetRuleRunner;
import net.firstpartners.drools.URLRuleLoader;
import net.firstpartners.drools.data.RuleSource;
import net.firstpartners.sample.SpreadsheetServlet.SpreadsheetServletSample;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

public class SpreadsheetRuleRunnerTest {

	//Logger
	private static final Logger log = Logger.getLogger(SpreadsheetRuleRunnerTest.class.getName());

	//Handle to common utility file
	private final  SpreadSheetRuleRunner commonUtils = new SpreadSheetRuleRunner(new URLRuleLoader());


	@Test
	public final void testRangeHolderCallRules(){

		fail("Need to construct RangeHolders, check results");

	}

	@Test
	public final void testCompileRunDrlFromUrl() throws Exception{


		//Get the URL
		URL url = new URL(SpreadsheetServletSample.EXCEL_DATA_FILE);
		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(SpreadsheetServletSample.RULES_FILES);

		HSSFWorkbook wb;
		wb = commonUtils.callRules(url,ruleSource, SpreadsheetServletSample.getExcelLogWorksheetName());




	}

	@Test
	/**
	 * Just check that the rules can run, throws no exception
	 */
	public final void testCallRules() throws Exception{

		//Check where we are
		File whereAmI = new File(".");
		log.info("Default file location:"+whereAmI.getAbsolutePath());

		//Get the URL
		URL url = new URL(SpreadsheetServletSample.EXCEL_DATA_FILE);

		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(SpreadsheetServletSample.RULES_FILES);


		HSSFWorkbook wb;
		wb = commonUtils.callRules(url,ruleSource, SpreadsheetServletSample.getExcelLogWorksheetName());

	}



}
