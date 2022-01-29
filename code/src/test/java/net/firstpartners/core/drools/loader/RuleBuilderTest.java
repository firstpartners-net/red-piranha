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
import net.firstpartners.core.file.Utils;
import net.firstpartners.data.Config;
import net.firstpartners.data.RedModel;

class RuleBuilderTest {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	void testDefaultBuild() throws ClassNotFoundException, DroolsParserException, IOException {

		RedModel myModel = new RedModel();
		Config testConfig = new Config();
		
		myModel.setRuleFileLocation(TestConstants.RULES_FILE);

		KieBuilder myBuilder = new RuleBuilder().loadRules(myModel,testConfig);
		KieModule myModule = myBuilder.getKieModule();

		assertNotNull(myModule);
	}

	@Test
	public final void testCompileRules() throws ClassNotFoundException, DroolsParserException, IOException {

		log.debug("Starting to compile rules");

		RedModel rulesToCompile = new RedModel();
		rulesToCompile.setRuleFileLocation(TestConstants.RULES_FILE);
		
		Config testConfig = new Config();

		KieBuilder myBuilder = new RuleBuilder().loadRules(rulesToCompile,testConfig);
		KieModule myModule = myBuilder.getKieModule();

		assertNotNull(myModule);
		Utils.deleteOutputFileIfExists(TestConstants.KNOWLEDGE_BASE_FILE_TMP);

	}

	@Test
	public final void testCompileDomainLanguageRules()
			throws ClassNotFoundException, DroolsParserException, IOException {

		log.debug("Starting to compile rules");

		// String rulesToCompile = "examples/private/sef-copy-proposal-data.dslr";

		RedModel rulesToCompile = new RedModel();
		rulesToCompile.setRuleFileLocation(TestConstants.EXAMPLES_DOMAIN_SPECIFIC_LANGUAGE_LOG_RULES_DSLR);
		rulesToCompile.setDslFileLocation(TestConstants.EXAMPLES_DOMAIN_SPECIFIC_LANGUAGE_CELL_LOGGING_DSL);

		Config testConfig = new Config();
		
		KieBuilder myBuilder = new RuleBuilder().loadRules(rulesToCompile,testConfig);
		KieModule myModule = myBuilder.getKieModule();

		assertNotNull(myModule);
		Utils.deleteOutputFileIfExists(TestConstants.KNOWLEDGE_BASE_FILE_TMP);

	}
	
	
}
