package net.firstpartners.core.word;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.drools.loader.RuleConfig;
import net.firstpartners.core.file.PDFOutputStrategy;

public class WordXInputStrategyTest {

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Just check that the rules can run, throws no exception
	 */
	@Test
	public final void testdoxcCallRulesFromFile() throws Exception {

		RuleConfig ruleSource = new RuleConfig();
		ruleSource.setRulesLocation(TestConstants.RULES_FILES);

		log.debug("rule source created");

		RuleRunner runner = RuleRunnerFactory.getRuleRunner(TestConstants.WORDX_DATA_FILE, ruleSource,
				"some-dummy.pdf");
		assertTrue(runner.getDocumenttOutputStrategy() instanceof PDFOutputStrategy);

		// set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setOutputStrategy(outputStrategy);

		runner.callRules();
		assertNotNull(outputStrategy.getProcessedDocument());

	}


}
