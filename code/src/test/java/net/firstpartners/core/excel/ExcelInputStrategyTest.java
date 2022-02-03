package net.firstpartners.core.excel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;

public class ExcelInputStrategyTest {

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Just check that the rules can run, throws no exception
	 */
	@Test
	public final void testXlsCallRulesFromFile() throws Exception {

		RedModel redModel = new RedModel(TestConstants.XLS_DATA_FILE,TestConstants.RULES_FILE,"some-dummy.xls");
			
		log.debug("rule source created");
		
		RuleRunner runner =RuleRunnerFactory.getRuleRunner(redModel);
		assertTrue (runner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setOutputStrategy(outputStrategy);
 
		runner.callRules(redModel);
		assertNotNull(outputStrategy.getProcessedDocument());

	}

	@Test
	/**
	 * Just check that the rules can run, throws no exception
	 */
	public final void testXlsXCallRulesFromFile() throws Exception {
		
		RedModel redModel = new RedModel(TestConstants.XLSX_DATA_FILE,TestConstants.RULES_FILE,"some-dummy.xls");

		RuleRunner runner =RuleRunnerFactory.getRuleRunner(redModel);
		assertTrue (runner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setOutputStrategy(outputStrategy);

		runner.callRules(redModel);
		assertNotNull(outputStrategy.getProcessedDocument());

	}

	
}
