package net.firstpartners.core.drools;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.junit.Test;

import net.firstpartners.core.drools.loader.FileRuleLoader;
import net.firstpartners.core.drools.loader.URLRuleLoaderStrategy;
import net.firstpartners.core.excel.ExcelInputStrategy;
import net.firstpartners.core.excel.ExcelOutputStrategy;
import net.firstpartners.core.file.CSVOutputStrategy;
import net.firstpartners.core.file.JsonOutputStrategy;
import net.firstpartners.core.file.PDFOutputStrategy;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.core.word.WordInputStrategy;
import net.firstpartners.core.word.WordXInputStrategy;

public class RuleRunnerFactoryTest {

	// Handle to the logger
	private static final Logger log = RpLogger.getLogger(RuleRunnerFactoryTest.class.getName());

	@Test
	public void testGenericXLSFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-in-name.xls", "filename", "some-dummy-out.xls");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof ExcelOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof FileRuleLoader);

	}

	@Test
	public void testGenericXLSXFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-in-name.xlsx", "filename", "some-dummy-out.xlsx");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof ExcelOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof FileRuleLoader);

	}

	@Test
	public void testGenericDocFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-in-name.doc", "filename", "some-dummy-out.csv");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof WordInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof CSVOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof FileRuleLoader);

	}

	@Test
	public void testGenericDocXFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-in-name.docx", "filename", "some-dummy-out.pdf");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof WordXInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof PDFOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof FileRuleLoader);

	}

	
	@Test
	public void testCsvOutAndIn() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-in-name.doc", "filename", "some-dummy-out.csv");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof WordInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof CSVOutputStrategy);

	}
	
	@Test
	public void testGenericFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		
		try {
			RuleRunnerFactory.getRuleRunner("some-name-that-should-match-to-nothing", "filename",
					"another-name-to-match-to-nothing");
			
			fail("Expected exception not thrown");
		} catch (IllegalArgumentException iae) {
			//ok to ignore we expected it
		}
		
	

	}

	@Test
	public void testFileFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("generic.xls", "someFile",
				"generic.xls");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof ExcelOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof FileRuleLoader);
		assertNotNull(myRunner.getRuleLoader().getRuleSource());
		assertEquals(myRunner.getRuleLoader().getRuleSource().getRulesLocation()[0], "someFile"); // check that someFile
																									// is stored

	}

	@Test
	public void testUrlFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("some-gernic.xls",
				"http-something-else", "some-gernic.xls");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof ExcelOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof URLRuleLoaderStrategy);

	}

	@Test
	public void testBuildSuffixReferenceTables() throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		assertNotNull(RuleRunnerFactory.inputSuffixMaps);
		assertNotNull(RuleRunnerFactory.outputSuffixMaps);

		RuleRunnerFactory.resetReferenceTables();
		assertNull(RuleRunnerFactory.inputSuffixMaps);
		assertNull(RuleRunnerFactory.outputSuffixMaps);

		// Now force the rebuild
		RuleRunnerFactory.getRuleRunner("some-in-name.xlsx", "filename", "some-dummy-out.xlsx");
		assertNotNull(RuleRunnerFactory.inputSuffixMaps);
		assertNotNull(RuleRunnerFactory.outputSuffixMaps);

		assertTrue(RuleRunnerFactory.inputSuffixMaps.size() > 3);
		assertTrue(RuleRunnerFactory.outputSuffixMaps.size() > 3);

	}

	@Test
	public void testInputMappings() {

		Object tmpObject = RuleRunnerFactory.getInputMapping("something.xlsx");
		log.debug("InputMapping:"+tmpObject);
		assertTrue(RuleRunnerFactory.getInputMapping("something.xlsx") == ExcelInputStrategy.class);
	}
	
	@Test
	public void testJsonFileFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner("generic.xls", "someFile",
				"generic.json");
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumenttOutputStrategy() instanceof JsonOutputStrategy);
		assertTrue(myRunner.getRuleLoader() instanceof FileRuleLoader);
		assertNotNull(myRunner.getRuleLoader().getRuleSource());
		assertEquals(myRunner.getRuleLoader().getRuleSource().getRulesLocation()[0], "someFile"); // check that someFile
																									// is stored

	}


}
