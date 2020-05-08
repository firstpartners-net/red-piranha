/**
 *
 */
package net.firstpartners.core.drools;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.drools.compiler.compiler.DroolsParserException;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.drools.loader.RuleConfig;
import net.firstpartners.core.file.Utils;

/**
 * @author paul
 *
 */
public class PreCompileRuleBuilderTest {

	private static final String EXAMPLES_DOMAIN_SPECIFIC_LANGUAGE_LOG_RULES_DSLR = "examples/domain-specific-language/log-rules.dslr";

	private static final String EXAMPLES_DOMAIN_SPECIFIC_LANGUAGE_CELL_LOGGING_DSL = "examples/domain-specific-language/cell-logging.dsl";

	// Class under test
	private final PreCompileRuleBuilder preCompileRuleBuilder = new PreCompileRuleBuilder();

	// Handle to Logger
	private static final Logger log = Logger.getLogger(PreCompileRuleBuilderTest.class.getName());

	@Test
	public final void testpreCompileRules() throws ClassNotFoundException, DroolsParserException, IOException {

		log.debug("Starting to compile rules");

		RuleConfig rulesToCompile = new RuleConfig();
		rulesToCompile.setRulesLocation(TestConstants.RULES_FILES[0]);
	
		preCompileRuleBuilder.compileRule(rulesToCompile, TestConstants.KNOWLEDGE_BASE_FILE_TMP);

		// check that this exists
		File f = new File(TestConstants.KNOWLEDGE_BASE_FILE_TMP);
		assertTrue("Cannot find file that should exist", f.exists());
		f = null; // avoid any interference in the next step

		Utils.deleteOutputFileIfExists(TestConstants.KNOWLEDGE_BASE_FILE_TMP);

	}

	@Test
	public final void testpreCompileDomainLanguageRules() throws ClassNotFoundException, DroolsParserException, IOException {

		log.debug("Starting to compile rules");

		// String rulesToCompile = "examples/private/sef-copy-proposal-data.dslr";

		RuleConfig rulesToCompile = new RuleConfig();
		rulesToCompile.setRulesLocation(EXAMPLES_DOMAIN_SPECIFIC_LANGUAGE_LOG_RULES_DSLR);
		rulesToCompile.setDslFileLocation(EXAMPLES_DOMAIN_SPECIFIC_LANGUAGE_CELL_LOGGING_DSL);


		preCompileRuleBuilder.compileRule(rulesToCompile, TestConstants.KNOWLEDGE_BASE_FILE_TMP);

		// check that this exists
		File f = new File(TestConstants.KNOWLEDGE_BASE_FILE_TMP);
		assertTrue("Cannot find file that should exist", f.exists());
		f = null; // avoid any interference in the next step

		Utils.deleteOutputFileIfExists(TestConstants.KNOWLEDGE_BASE_FILE_TMP);

	}


}
