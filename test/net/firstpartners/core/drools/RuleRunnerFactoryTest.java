package net.firstpartners.core.drools;

import static org.junit.Assert.*;

import org.junit.Test;

import net.firstpartners.core.drools.loader.FileRuleLoader;
import net.firstpartners.core.drools.loader.URLRuleLoader;
import net.firstpartners.core.spreadsheet.ExcelInputStrategy;
import net.firstpartners.core.spreadsheet.ExcelOutputStrategy;

public class RuleRunnerFactoryTest {

	@Test
	public void testGenericXLSFactory() {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-in-name.xls","filename","some-dummy-out.xls");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof ExcelOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof FileRuleLoader);
		
		
		
	}
	
	@Test
	public void testGenericFactory() {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-name-that-should-match-to-nothing","filename","another-name-to-match-to-nothing");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof ExcelOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof FileRuleLoader);
		
		
	}
	
	@Test
	public void testFileFactory() {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-name-that-should-match-to-nothing","someFile","another-name-to-match-to-nothing");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof ExcelOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof FileRuleLoader);
		assertNotNull(myRunner.getRuleLoader().getRuleSource());
		assertEquals(myRunner.getRuleLoader().getRuleSource().getRulesLocation()[0],"someFile"); //check that someFile is stored
		
		
	}
	
	@Test
	public void testUrlFactory() {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-name-that-should-match-to-nothing","http-something-else","another-name-to-match-to-nothing");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof ExcelOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof URLRuleLoader);
		
		
	}



}
