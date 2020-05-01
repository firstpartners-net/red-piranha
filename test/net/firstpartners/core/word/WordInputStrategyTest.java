package net.firstpartners.core.word;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.drools.compiler.compiler.DroolsParserException;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.drools.loader.RuleDTO;

public class WordInputStrategyTest {

	// Logger
	private static final Logger log = Logger.getLogger(WordInputStrategyTest.class.getName());

	/**
	 * Just check that the rules can run, throws no exception
	 */
	@Test
	public final void testdoxcCallRulesFromFile() throws Exception {

		RuleDTO ruleSource = new RuleDTO();
		ruleSource.setRulesLocation(TestConstants.RULES_FILES);
		
		log.debug("rule source created");
		
		RuleRunner runner =RuleRunnerFactory.getRuleRunner(TestConstants.WORDX_DATA_FILE,ruleSource,"some-dummy.docX");
		assertTrue (runner.getDocumenttOutputStrategy() instanceof WordXOutputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setOutputStrategy(outputStrategy);

		runner.callRules();
		assertNotNull(outputStrategy.getProcessedDocument());

	}

	@Test
	/**
	 * Just check that the rules can run, throws no exception
	 */
	public final void testDocCallRulesFromFile() throws ClassNotFoundException, InvalidFormatException, DroolsParserException, IOException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		try {
			
			RuleDTO ruleSource = new RuleDTO();
			ruleSource.setRulesLocation(TestConstants.RULES_FILES);

			RuleRunner runner =RuleRunnerFactory.getRuleRunner(TestConstants.WORD_DATA_FILE,ruleSource,"some-dummy.doc");
			
			assertTrue (runner.getDocumentInputStrategy() instanceof WordXInputStrategy);
			
			//set out OutputStrategy so we can test the output later
			MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
			runner.setOutputStrategy(outputStrategy);
	
			runner.callRules();
			fail("Excpected exception when opening docx not thrown");
			
			
		} catch (OLE2NotOfficeXmlFileException ole2Exception) {
			// ignore - we expect it to call
		}
		


	}

	
}
