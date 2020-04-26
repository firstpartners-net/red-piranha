package net.firstpartners.core.drools;

import static org.junit.Assert.*;

import org.junit.Test;

import net.firstpartners.core.spreadsheet.ExcelInputStrategy;

public class RuleRunnerFactoryTest {

	@Test
	public void testGenericXLSFactory() {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-name.xls");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentOutStrategy() instanceof ExcelInputStrategy);
		
		assertNotNull(myRunner.getOutputStrategy());
		
		
	}
	
	@Test
	public void testGenericFactory() {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-name-that-should-match-to-nothing");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentOutStrategy() instanceof ExcelInputStrategy);
		
		assertNotNull(myRunner.getOutputStrategy());
		
		
	}


}
