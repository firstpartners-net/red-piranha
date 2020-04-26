package net.firstpartners.core.spreadsheet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.drools.loader.RuleSource;

public class ExcelInputStrategyTest {

	// Logger
	private static final Logger log = Logger.getLogger(ExcelInputStrategyTest.class.getName());

	/**
	 * Just check that the rules can run, throws no exception
	 */
	@Test
	public final void testXlsCallRulesFromFile() throws Exception {

		RuleRunner runner =RuleRunnerFactory.getRuleRunner(TestConstants.XLS_DATA_FILE);
		assertTrue (runner.getOutputStrategy() instanceof ExcelOutputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setOutputStrategy(outputStrategy);

		// Check where we are
		File whereAmI = new File(".");
		log.info("Default file location:" + whereAmI.getAbsolutePath());

		// Get the XLS DAta file

		FileInputStream excelInput = new FileInputStream(TestConstants.XLS_DATA_FILE);

		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(TestConstants.RULES_FILES);

		runner.callRules(excelInput, ruleSource, TestConstants.EXCEL_LOG_WORKSHEET_NAME);
		assertNotNull(outputStrategy.getWorkbook());

	}

	@Test
	/**
	 * Just check that the rules can run, throws no exception
	 */
	public final void testXlsXCallRulesFromFile() throws Exception {

		RuleRunner runner =RuleRunnerFactory.getRuleRunner(TestConstants.XLSX_DATA_FILE);
		assertTrue (runner.getOutputStrategy() instanceof ExcelOutputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setOutputStrategy(outputStrategy);

		// Check where we are
		File whereAmI = new File(".");
		log.info("Default file location:" + whereAmI.getAbsolutePath());

		// Get the XLS DAta file

		FileInputStream excelInput = new FileInputStream(TestConstants.XLSX_DATA_FILE);

		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(TestConstants.RULES_FILES);

		runner.callRules(excelInput, ruleSource, TestConstants.EXCEL_LOG_WORKSHEET_NAME);
		assertNotNull(outputStrategy.getWorkbook());

	}

	
}
