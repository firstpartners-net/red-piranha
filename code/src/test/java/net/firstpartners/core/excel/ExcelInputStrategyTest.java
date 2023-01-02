package net.firstpartners.core.excel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RunnerFactory;


public class ExcelInputStrategyTest {

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Just check that the rules can run, throws no exception
	 * @throws RPException
	 */
	@Test
	public final void testXlsCallRulesFromFile() throws Exception, RPException {

		RedModel redModel = new RedModel(TestConstants.XLS_DATA_FILE,TestConstants.RULES_FILE,"some-dummy.xls");
			
		log.debug("rule source created");
		
		RuleRunner runner =(RuleRunner)RunnerFactory.getRuleRunner(redModel);
		assertTrue (runner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setDocumentOutputStrategy(outputStrategy);
 
		runner.callRules(redModel);
		assertNotNull(outputStrategy.getProcessedDocument());

	}

	@Test
	/**
	 * Just check that the rules can run, throws no exception
	 */
	public final void testXlsXCallRulesFromFile() throws Exception, RPException {
		
		RedModel redModel = new RedModel(TestConstants.XLSX_DATA_FILE,TestConstants.RULES_FILE,"some-dummy.xls");

		RuleRunner runner =(RuleRunner)RunnerFactory.getRuleRunner(redModel);
		assertTrue (runner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setDocumentOutputStrategy(outputStrategy);

		runner.callRules(redModel);
		assertNotNull(outputStrategy.getProcessedDocument());

	}

	
}
