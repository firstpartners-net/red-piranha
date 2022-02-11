/**
 * 
 */
package net.firstpartners.core.json;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;

/**
 * @author paulf
 *
 */
class JsonInputStrategyTest {

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

	@Test
	/**
	 * Just check that the rules can run, throws no exception
	 */
	public final void testThereAndBackAgain() throws Exception {

		RedModel redModel = new RedModel(TestConstants.JSON_SERIAL_FILE, TestConstants.RULES_FILE, "some-dummy.xls");

		RuleRunner runner = RuleRunnerFactory.getRuleRunner(redModel);
		assertTrue(runner.getDocumentInputStrategy() instanceof JsonInputStrategy);

		// set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setOutputStrategy(outputStrategy);

		runner.callRules(redModel);
		assertNotNull(outputStrategy.getProcessedDocument());

	}

}
