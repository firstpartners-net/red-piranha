package net.firstpartners.core.excel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.drools.loader.RuleConfig;

public class ExcelInputStrategyTest {

	// Logger
	private static final Logger log = LogManager.getLogger(ExcelInputStrategyTest.class.getName());

	/**
	 * Just check that the rules can run, throws no exception
	 */
	@Test
	public final void testXlsCallRulesFromFile() throws Exception {

		RuleConfig ruleSource = new RuleConfig();
		ruleSource.setRulesLocation(TestConstants.RULES_FILES);
		
		log.debug("rule source created");
		
		RuleRunner runner =RuleRunnerFactory.getRuleRunner(TestConstants.XLS_DATA_FILE,ruleSource,"some-dummy.xls");
		assertTrue (runner.getDocumenttOutputStrategy() instanceof ExcelOutputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setOutputStrategy(outputStrategy);

		runner.callRules();
		assertNotNull(outputStrategy.getProcessedDocument());

	}

	@Test
	/**
	 * Just check that the rules can run, throws no exception
	 */
	public final void testXlsXCallRulesFromFile() throws Exception {
		
		RuleConfig ruleSource = new RuleConfig();
		ruleSource.setRulesLocation(TestConstants.RULES_FILES);

		RuleRunner runner =RuleRunnerFactory.getRuleRunner(TestConstants.XLSX_DATA_FILE,ruleSource,"some-dummy.xls");
		assertTrue (runner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setOutputStrategy(outputStrategy);

		runner.callRules();
		assertNotNull(outputStrategy.getProcessedDocument());

	}

	
}
