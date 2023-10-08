package net.firstpartners.core.drools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.excel.ExcelInputStrategy;
import net.firstpartners.core.excel.ExcelOutputStrategy;
import net.firstpartners.core.file.CSVOutputStrategyMultiLine;
import net.firstpartners.core.file.PDFOutputStrategy;
import net.firstpartners.core.json.JsonInputStrategy;
import net.firstpartners.core.json.JsonOutputStrategy;
import net.firstpartners.core.word.WordInputStrategy;
import net.firstpartners.core.word.WordXInputStrategy;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RunnerFactoryTest {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// handle for our config
	@Autowired
	Config appConfig;
	
	@Test
	public void testGenericXLSFactory() throws RPException{
		
		RedModel testModel = new RedModel("some-in-name.xls", "filename", "some-dummy-out.xls");

		
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy().get(0) instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);

	}

	@Test
	public void testGenericXLSXFactory() throws RPException {
		
		RedModel testModel = new RedModel("some-in-name.xlsx", "filename", "some-dummy-out.xlsx");
		
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy().get(0) instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);

	}

	@Test
	public void testGenericDocFactory() throws RPException {
		
		RedModel testModel = new RedModel("some-in-name.doc", "filename", "some-dummy-out.csv");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy().get(0) instanceof WordInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof CSVOutputStrategyMultiLine);

	}

	@Test
	public void testGenericDocXFactory() throws RPException
		{
		
		RedModel testModel = new RedModel("some-in-name.docx", "filename", "some-dummy-out.pdf");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy().get(0) instanceof WordXInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof PDFOutputStrategy);

	}

	
	@Test
	public void testCsvOutAndIn() throws RPException {
		
		RedModel testModel = new RedModel("some-in-name.doc", "filename", "some-dummy-out.csv");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy().get(0) instanceof WordInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof CSVOutputStrategyMultiLine);

	}
	
	@Test
	public void testGenericFactory() throws RPException{
		
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
	public void testFileFactory() throws RPException {
		
		
		RedModel testModel = new RedModel("generic.xls", "someFile",
				"generic.xls");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy().get(0) instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);
																									// is stored

	}

		@Test
	public void testDirectoryFactoryEmpty() throws RPException {
		
		//Note that our input directory does not exist
		RedModel testModel = new RedModel("somedirectory/", "someFile",
				"generic.csv");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);

		//we should get a 0 based list of input stategies
		assertEquals("No input Strategy should be created for an empty directory", 0,myRunner.getDocumentInputStrategy().size());

			
		//quick check on the output strategy
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof CSVOutputStrategyMultiLine);
																									// is stored

	}


	@Test
	public void testDirectoryFactory() throws RPException {
		
		
		RedModel testModel = new RedModel(TestConstants.DIRECTORY_SAMPLE, "someFile",
				"generic.csv");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);

		//we should get a list , size 2, both with excel input strategies point to an excel file
		assertEquals("More than two files identified in sample", 2,myRunner.getDocumentInputStrategy().size());
		assertTrue(myRunner.getDocumentInputStrategy().get(0) instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentInputStrategy().get(1) instanceof ExcelInputStrategy);
		
		
		
		//quick check on the output strategy
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof CSVOutputStrategyMultiLine);
																									// is stored

	}


	@Test
	public void testUrlFactory() throws RPException {
		
		RedModel testModel = new RedModel("some-gernic.xls",
				"http-something-else", "some-gernic.xls");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy().get(0) instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);

	}

	@Test
	public void testBuildSuffixReferenceTables() throws RPException {

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
	public void testInputMappings() throws RPException {

		Object tmpObject = RunnerFactory.getInputMapping("","something.xlsx");
		log.debug("InputMapping:"+tmpObject);
		assertTrue(RunnerFactory.getInputMapping("","something.xlsx").get(0) == ExcelInputStrategy.class);
	}
	
	@Test
	public void testJsonFileFactory() throws RPException {
		
		RedModel testModel = new RedModel("generic.json", "someFile",
				"generic.json");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue(myRunner.getDocumentInputStrategy().get(0) instanceof JsonInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof JsonOutputStrategy);
																									// is stored

	}

	@Test
	public void testDecisionModelFactoryCreation() throws RPException{
	
		RedModel testModel = new RedModel("some-in-name.xlsx", "filename.dmn", "some-dummy-out.xlsx");	
		
		
		IRunner myRunner = RunnerFactory.getRuleRunner(testModel);
		assertNotNull(myRunner);

		//this is the key test
		assertTrue (myRunner instanceof DecisionModelRunner);

		assertTrue(myRunner.getDocumentInputStrategy().get(0) instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);




	}


	@Test
	public void testHandleDirectoryInput() throws RPException {


		RedModel testModel = new RedModel(TestConstants.DIRECTORY_SAMPLE,
				"http-something-else", "some-gernic.xls");
		RuleRunner myRunner = (RuleRunner)RunnerFactory.getRuleRunner(testModel);


		assertNotNull(myRunner);
		assertTrue (myRunner instanceof RuleRunner);
		assertTrue("We should have at least one input strategy identified",myRunner.getDocumentInputStrategy().size()>0);
		assertTrue(myRunner.getDocumentInputStrategy().get(0) instanceof ExcelInputStrategy);
		assertTrue(myRunner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);

	}


}
