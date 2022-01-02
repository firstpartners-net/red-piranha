package net.firstpartners.core.drools.loader;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.drools.compiler.compiler.DroolsParserException;
import org.junit.jupiter.api.Test;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;
import net.firstpartners.core.RedModelFactory;
import net.firstpartners.core.file.Utils;
import net.firstpartners.data.Config;
import net.firstpartners.data.RedModel;

class RedRuleBuilderTest {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	void test() {

		RedModel myModel = RedModelFactory.getFreshRedModelUsingConfiguration(new Config());
		myModel.addRuleLocation(TestConstants.RULES_FILES);

		KieBuilder myBuilder = new RedRuleBuilder().loadRules(myModel);
		KieModule myModule = myBuilder.getKieModule();

		assertNotNull(myModule);
	}

	@Test
	public final void testCompileRules() throws ClassNotFoundException, DroolsParserException, IOException {

		log.debug("Starting to compile rules");

		RedModel rulesToCompile = RedModelFactory.getFreshRedModelUsingConfiguration(new Config());
		rulesToCompile.addRuleLocation(TestConstants.RULES_FILES[0]);

		KieBuilder myBuilder = new RedRuleBuilder().loadRules(rulesToCompile);
		KieModule myModule = myBuilder.getKieModule();

		assertNotNull(myModule);
		Utils.deleteOutputFileIfExists(TestConstants.KNOWLEDGE_BASE_FILE_TMP);

	}

	@Test
	public final void testCompileDomainLanguageRules()
			throws ClassNotFoundException, DroolsParserException, IOException {

		log.debug("Starting to compile rules");

		// String rulesToCompile = "examples/private/sef-copy-proposal-data.dslr";

		RedModel rulesToCompile = RedModelFactory.getFreshRedModelUsingConfiguration(new Config());
		rulesToCompile.addRuleLocation(TestConstants.EXAMPLES_DOMAIN_SPECIFIC_LANGUAGE_LOG_RULES_DSLR);
		rulesToCompile.setDslFileLocation(TestConstants.EXAMPLES_DOMAIN_SPECIFIC_LANGUAGE_CELL_LOGGING_DSL);

		KieBuilder myBuilder = new RedRuleBuilder().loadRules(rulesToCompile);
		KieModule myModule = myBuilder.getKieModule();

		assertNotNull(myModule);
		Utils.deleteOutputFileIfExists(TestConstants.KNOWLEDGE_BASE_FILE_TMP);

	}
}
