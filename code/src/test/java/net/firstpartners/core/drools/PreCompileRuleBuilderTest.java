/**
 *
 */
package net.firstpartners.core.drools;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.drools.compiler.compiler.DroolsParserException;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import net.firstpartners.TestConstants;
import net.firstpartners.core.RedModelFactory;
import net.firstpartners.core.file.Utils;
import net.firstpartners.data.Config;
import net.firstpartners.data.RedModel;

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
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Test
	public final void testpreCompileRules() throws ClassNotFoundException, DroolsParserException, IOException {

		log.debug("Starting to compile rules");

		RedModel rulesToCompile = RedModelFactory.getFreshRedModelUsingConfiguration(new Config());
		rulesToCompile.addRuleLocation(TestConstants.RULES_FILES[0]);
	
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

		RedModel rulesToCompile = RedModelFactory.getFreshRedModelUsingConfiguration(new Config());
		rulesToCompile.addRuleLocation(EXAMPLES_DOMAIN_SPECIFIC_LANGUAGE_LOG_RULES_DSLR);
		rulesToCompile.setDslFileLocation(EXAMPLES_DOMAIN_SPECIFIC_LANGUAGE_CELL_LOGGING_DSL);


		preCompileRuleBuilder.compileRule(rulesToCompile, TestConstants.KNOWLEDGE_BASE_FILE_TMP);

		// check that this exists
		File f = new File(TestConstants.KNOWLEDGE_BASE_FILE_TMP);
		assertTrue("Cannot find file that should exist", f.exists());
		f = null; // avoid any interference in the next step

		Utils.deleteOutputFileIfExists(TestConstants.KNOWLEDGE_BASE_FILE_TMP);

	}


}
