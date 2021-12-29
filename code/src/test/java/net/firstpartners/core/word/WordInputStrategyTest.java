package net.firstpartners.core.word;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.drools.loader.RuleConfig;
import net.firstpartners.core.file.PDFOutputStrategy;

public class WordInputStrategyTest {

	// Logger
	private static final Logger log = Logger.getLogger(WordInputStrategyTest.class.getName());

	/**
	 * Just check that the rules can run, throws no exception
	 */
	@Test
	public final void testdocCallRulesFromFile() throws Exception {

		RuleConfig ruleSource = new RuleConfig();
		ruleSource.setRulesLocation(TestConstants.RULES_FILES);
		
		log.debug("rule source created");
		
		RuleRunner runner =RuleRunnerFactory.getRuleRunner(TestConstants.WORD_DATA_FILE,ruleSource,"some-dummy.pdf");
		assertTrue (runner.getDocumenttOutputStrategy() instanceof PDFOutputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setOutputStrategy(outputStrategy);

		runner.callRules();
		assertNotNull(outputStrategy.getProcessedDocument());

	}


	
}
