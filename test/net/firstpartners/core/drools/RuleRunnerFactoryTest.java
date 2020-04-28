package net.firstpartners.core.drools;

import static org.junit.Assert.*;

import org.junit.Test;

import net.firstpartners.core.drools.loader.FileRuleLoader;
import net.firstpartners.core.drools.loader.URLRuleLoaderStrategy;
import net.firstpartners.core.excel.ExcelInputStrategy;
import net.firstpartners.core.excel.ExcelOutputStrategy;
import net.firstpartners.core.word.WordInputStrategy;
import net.firstpartners.core.word.WordOutputStrategy;

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
	public void testGenericXLSXFactory() {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-in-name.xlsx","filename","some-dummy-out.xlsx");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof ExcelOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof FileRuleLoader);
		
		
	}
	
	@Test
	public void testGenericDocFactory() {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-in-name.doc","filename","some-dummy-out.doc");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof WordInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof WordOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof FileRuleLoader);
		
	}
	
	@Test
	public void testGenericDocXFactory() {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-in-name.docx","filename","some-dummy-out.docx");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof WordInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof WordOutputStrategy);
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
		assertTrue(myRunner.getRuleLoader() instanceof URLRuleLoaderStrategy);
		
		
	}



}
