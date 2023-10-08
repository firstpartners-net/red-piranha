/**
 * 
 */
package net.firstpartners.core.file;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RunnerFactory;
import net.firstpartners.core.excel.SpreadSheetConvertorTest;
import net.firstpartners.data.RangeList;

/**
 * @author paulf
 *
 */
public class DirectoryInputStrategyTest {

	//if we need to change the directory prefix
	private static String dirPrefix="";

	//Logging
	private static final Logger log = LoggerFactory.getLogger(DirectoryInputStrategyTest.class);

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
	@Test
	public final void testDirectoryIn() throws Exception, RPException {

		
		// was 
		RedModel redModel = new RedModel(TestConstants.DIRECTORY_SAMPLE, TestConstants.SIMPLE_LOG_MODIFY_RULES_FILE, "some-dummy.xls");

		RuleRunner runner = (RuleRunner)RunnerFactory.getRuleRunner(redModel);
		assertTrue(runner.getDocumentInputStrategy() instanceof DirectoryInputStrategy);

		// set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setDocumentOutputStrategy(outputStrategy);

		fail("Test not fully implmeneted yet");

	
	}

}
