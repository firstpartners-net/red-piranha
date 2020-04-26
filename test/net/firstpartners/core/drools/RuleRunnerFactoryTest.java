package net.firstpartners.core.drools;

import static org.junit.Assert.*;

import org.junit.Test;

import net.firstpartners.core.drools.loader.FileRuleLoader;
import net.firstpartners.core.spreadsheet.ExcelInputStrategy;
import net.firstpartners.core.spreadsheet.ExcelOutputStrategy;

public class RuleRunnerFactoryTest {

	@Test
	public void testGenericXLSFactory() {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-in-name.xls","some-dummy-out.xls");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.geDocumenttOutputStrategy() instanceof ExcelOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof FileRuleLoader);
		
		
		
	}
	
	@Test
	public void testGenericFactory() {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-name-that-should-match-to-nothing","another-name-to-match-to-nothing");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.geDocumenttOutputStrategy() instanceof ExcelOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof FileRuleLoader);
		
		
	}


}
