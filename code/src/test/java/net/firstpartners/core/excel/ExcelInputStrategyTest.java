package net.firstpartners.core.excel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.RedModelFactory;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.log.EmptyStatusUpdate;
import net.firstpartners.data.Config;
import net.firstpartners.data.RedModel;

public class ExcelInputStrategyTest {

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Just check that the rules can run, throws no exception
	 */
	@Test
	public final void testXlsCallRulesFromFile() throws Exception {

		RedModel redModel = RedModelFactory.getFreshRedModelUsingConfiguration(new Config());
		redModel.addRuleLocation(TestConstants.RULES_FILES);
			
		log.debug("rule source created");
		
		RuleRunner runner =RuleRunnerFactory.getRuleRunner(TestConstants.XLS_DATA_FILE,redModel,"some-dummy.xls");
		assertTrue (runner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setOutputStrategy(outputStrategy);
 
		runner.callRules(new EmptyStatusUpdate(),redModel);
		assertNotNull(outputStrategy.getProcessedDocument());

	}

	@Test
	/**
	 * Just check that the rules can run, throws no exception
	 */
	public final void testXlsXCallRulesFromFile() throws Exception {
		
		RedModel redModel = RedModelFactory.getFreshRedModelUsingConfiguration(new Config());
		redModel.addRuleLocation(TestConstants.RULES_FILES);

		RuleRunner runner =RuleRunnerFactory.getRuleRunner(TestConstants.XLSX_DATA_FILE,redModel,"some-dummy.xls");
		assertTrue (runner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setOutputStrategy(outputStrategy);

		runner.callRules(new EmptyStatusUpdate(),redModel);
		assertNotNull(outputStrategy.getProcessedDocument());

	}

	
}
