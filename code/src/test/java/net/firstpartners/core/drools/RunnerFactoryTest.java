package net.firstpartners.core.drools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.RedModel;
import net.firstpartners.core.excel.ExcelInputStrategy;
import net.firstpartners.core.excel.ExcelOutputStrategy;
import net.firstpartners.core.file.CSVOutputStrategy;
import net.firstpartners.core.file.PDFOutputStrategy;
import net.firstpartners.core.json.JsonInputStrategy;
import net.firstpartners.core.json.JsonOutputStrategy;
import net.firstpartners.core.word.WordInputStrategy;
import net.firstpartners.core.word.WordXInputStrategy;

public class RunnerFactoryTest {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void testGenericXLSFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		RedModel testModel = new RedModel("some-in-name.xls", "filename", "some-dummy-out.xls");

		
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);

	}

	@Test
	public void testGenericXLSXFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		RedModel testModel = new RedModel("some-in-name.xlsx", "filename", "some-dummy-out.xlsx");
		
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);

	}

	@Test
	public void testGenericDocFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		RedModel testModel = new RedModel("some-in-name.doc", "filename", "some-dummy-out.csv");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof WordInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof CSVOutputStrategy);

	}

	@Test
	public void testGenericDocXFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		RedModel testModel = new RedModel("some-in-name.docx", "filename", "some-dummy-out.pdf");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof WordXInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof PDFOutputStrategy);

	}

	
	@Test
	public void testCsvOutAndIn() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		RedModel testModel = new RedModel("some-in-name.doc", "filename", "some-dummy-out.csv");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof WordInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof CSVOutputStrategy);

	}
	
	@Test
	public void testGenericFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		
		try {
			RedModel testModel = new RedModel("some-name-that-should-match-to-nothing", "filename",
					"another-name-to-match-to-nothing");
			RunnerFactory.getRuleRunner(testModel);
			
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
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);
																									// is stored

	}

	@Test
	public void testUrlFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		RedModel testModel = new RedModel("some-gernic.xls",
				"http-something-else", "some-gernic.xls");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);

	}

	@Test
	public void testBuildSuffixReferenceTables() throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		assertNotNull(RunnerFactory.inputSuffixMaps);
		assertNotNull(RunnerFactory.outputSuffixMaps);

		RunnerFactory.resetReferenceTables();
		assertNull(RunnerFactory.inputSuffixMaps);
		assertNull(RunnerFactory.outputSuffixMaps);

		// Now force the rebuild
		RedModel testModel = new RedModel("some-in-name.xlsx", "filename", "some-dummy-out.xlsx");
		RunnerFactory.getRuleRunner(testModel);
		assertNotNull(RunnerFactory.inputSuffixMaps);
		assertNotNull(RunnerFactory.outputSuffixMaps);

		assertTrue(RunnerFactory.inputSuffixMaps.size() > 3);
		assertTrue(RunnerFactory.outputSuffixMaps.size() > 3);

	}

	@Test
	public void testInputMappings() {

		Object tmpObject = RunnerFactory.getInputMapping("something.xlsx");
		log.debug("InputMapping:"+tmpObject);
		assertTrue(RunnerFactory.getInputMapping("something.xlsx") == ExcelInputStrategy.class);
	}
	
	@Test
	public void testJsonFileFactory() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		RedModel testModel = new RedModel("generic.json", "someFile",
				"generic.json");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy() instanceof JsonInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof JsonOutputStrategy);
																									// is stored

	}

	@Test
	public void testDecisionModelFactoryCreation() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
	
		RedModel testModel = new RedModel("some-in-name.xlsx", "filename.dmn", "some-dummy-out.xlsx");	
		
		
		IRunner myRunner = RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);

		//this is the key test
		assertTrue (myRunner instanceof DecisionModelRunner);

		assertTrue(myRunner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);




	}


}
