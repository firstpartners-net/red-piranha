/**
 * 
 */
package net.firstpartners.core.json;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RunnerFactory;
import net.firstpartners.core.excel.SpreadSheetConvertorTest;

import net.firstpartners.data.RangeList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author paulf
 *
 */
public class JsonInputStrategyTest {

	//if we need to change the directory prefix
	private static String dirPrefix="";

	//Logging
	private static final Logger log = LoggerFactory.getLogger(JsonInputStrategyTest.class);

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}


	/**
	 * Just check that the rules can run, throws no exception
	 * @throws RPException
	 */
	public final void testJSONInOut() throws Exception, RPException {

		
		// was 
		RedModel redModel = new RedModel(TestConstants.JSON_SERIAL_FILE, TestConstants.RULES_FILE, "some-dummy.xls");

		RuleRunner runner = (RuleRunner)RunnerFactory.getRuleRunner(redModel);
		assertTrue(runner.getDocumentInputStrategy() instanceof JsonInputStrategy);

		// set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setDocumentOutputStrategy(outputStrategy);

		runner.callRules(redModel);
		assertNotNull(outputStrategy.getProcessedDocument());

	}

		
	/** 
	 * Convenience method - serialize testdata for use by Cell Tasts
	 * @param args
	 * @throws IOException 
	 * @throws InvalidFormatException
	 */
	public static void main (String args[]) throws IOException, InvalidFormatException {
		
		//have we been requested to modfiy the directoyr
		dirPrefix = args[0];

		RangeList myRange = new SpreadSheetConvertorTest().getTestDataFromWorkbook();

		JsonOutputStrategy jsonOut = new JsonOutputStrategy(dirPrefix+TestConstants.JSON_SERIAL_FILE);

		// test the class
		jsonOut.setUpdates(null, myRange);
		jsonOut.processOutput();
		log.debug("Serialized data is saved in:"+ dirPrefix+TestConstants.JSON_SERIAL_FILE);
	}

}
