package net.firstpartners.core.word;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.file.PDFOutputStrategy;
import net.firstpartners.core.log.EmptyStatusUpdate;
import net.firstpartners.data.RedModel;

public class WordInputStrategyTest {


	/**
	 * Just check that the rules can run, throws no exception
	 */
	@Test
	public final void testdocCallRulesFromFile() throws Exception {

		RedModel redModel = new RedModel(TestConstants.WORD_DATA_FILE,TestConstants.RULE_FILE_FOR_WORD,"some-dummy.pdf");
		
		RuleRunner runner =RuleRunnerFactory.getRuleRunner(redModel);
		assertTrue (runner.getDocumentOutputStrategy() instanceof PDFOutputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setOutputStrategy(outputStrategy);

		runner.callRules(new EmptyStatusUpdate(),redModel);
		assertNotNull(outputStrategy.getProcessedDocument());

	}


	
}
