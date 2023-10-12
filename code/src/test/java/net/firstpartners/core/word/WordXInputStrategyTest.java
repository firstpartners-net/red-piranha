package net.firstpartners.core.word;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RunnerFactory;
import net.firstpartners.core.file.PDFOutputStrategy;

public class WordXInputStrategyTest {

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Just check that the rules can run, throws no exception
	 * @throws RPException
	 */
	@Test
	public final void testdoxcCallRulesFromFile() throws Exception, RPException {
	

		RedModel redModel = new RedModel(TestConstants.WORDX_DATA_FILE, TestConstants.SIMPLE_LOG_MODIFY_RULES_FILE,
				"some-dummy.pdf");
		

		log.debug("rule source created");

		RuleRunner runner = (RuleRunner)RunnerFactory.getRuleRunner(redModel);
		assertTrue(runner.getDocumentOutputStrategy() instanceof PDFOutputStrategy);

		// set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setDocumentOutputStrategy(outputStrategy);

		runner.callRulesLoop(redModel);
		assertNotNull(outputStrategy.getProcessedDocument());

	}


}
