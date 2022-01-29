package net.firstpartners.core.drools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.excel.ExcelInputStrategy;
import net.firstpartners.core.excel.ExcelOutputStrategy;
import net.firstpartners.core.file.CSVOutputStrategy;
import net.firstpartners.core.file.PDFOutputStrategy;
import net.firstpartners.core.json.JsonInputStrategy;
import net.firstpartners.core.json.JsonOutputStrategy;
import net.firstpartners.core.word.WordInputStrategy;
import net.firstpartners.core.word.WordXInputStrategy;
import net.firstpartners.data.RedModel;

public class RuleRunnerFactoryTest {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void testGenericXLSFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		RedModel testModel = new RedModel("some-in-name.xls", "filename", "some-dummy-out.xls");

		
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);

	}

	@Test
	public void testGenericXLSXFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		RedModel testModel = new RedModel("some-in-name.xlsx", "filename", "some-dummy-out.xlsx");
		
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);

	}

	@Test
	public void testGenericDocFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		RedModel testModel = new RedModel("some-in-name.doc", "filename", "some-dummy-out.csv");
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof WordInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof CSVOutputStrategy);

	}

	@Test
	public void testGenericDocXFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		RedModel testModel = new RedModel("some-in-name.docx", "filename", "some-dummy-out.pdf");
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof WordXInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof PDFOutputStrategy);

	}

	
	@Test
	public void testCsvOutAndIn() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		RedModel testModel = new RedModel("some-in-name.doc", "filename", "some-dummy-out.csv");
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof WordInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof CSVOutputStrategy);

	}
	
	@Test
	public void testGenericFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		
		try {
			RedModel testModel = new RedModel("some-name-that-should-match-to-nothing", "filename",
					"another-name-to-match-to-nothing");
			RuleRunnerFactory.getRuleRunner(testModel);
			
			fail("Expected exception not thrown");
		} catch (IllegalArgumentException iae) {
			//ok to ignore we expected it
		}
		
	

	}

	@Test
	public void testFileFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		
		RedModel testModel = new RedModel("generic.xls", "someFile",
				"generic.xls");
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);
																									// is stored

	}

	@Test
	public void testUrlFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		RedModel testModel = new RedModel("some-gernic.xls",
				"http-something-else", "some-gernic.xls");
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);

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
		RedModel testModel = new RedModel("some-in-name.xlsx", "filename", "some-dummy-out.xlsx");
		RuleRunnerFactory.getRuleRunner(testModel);
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
		
		RedModel testModel = new RedModel("generic.json", "someFile",
				"generic.json");
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof JsonInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof JsonOutputStrategy);
																									// is stored

	}


}
