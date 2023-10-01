package net.firstpartners.core.word;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RunnerFactory;
import net.firstpartners.core.file.PDFOutputStrategy;

public class WordInputStrategyTest {


	/**
	 * Just check that the rules can run, throws no exception
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	@Test
	public final void testdocCallRulesFromFile() throws RPException, ResourceException, ScriptException {

		RedModel redModel = new RedModel(TestConstants.WORD_DATA_FILE,TestConstants.RULE_FILE_FOR_WORD,"some-dummy.pdf");
		
		RuleRunner runner =(RuleRunner)RunnerFactory.getRuleRunner(redModel);
		assertTrue (runner.getDocumentOutputStrategy() instanceof PDFOutputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setDocumentOutputStrategy(outputStrategy);

		runner.callRules(redModel);
		assertNotNull(outputStrategy.getProcessedDocument());

	}


	
}
