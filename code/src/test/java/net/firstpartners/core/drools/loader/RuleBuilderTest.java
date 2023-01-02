package net.firstpartners.core.drools.loader;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.file.Utils;

class RuleBuilderTest {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	void testDefaultBuild() throws Exception, RPException {

		RedModel myModel = new RedModel();
		Config testConfig = new Config();
		
		myModel.setRuleFileLocation(TestConstants.RULES_FILE);

		KieBuilder myBuilder = new RuleBuilder().loadRules(myModel,testConfig);
		KieModule myModule = myBuilder.getKieModule();

		assertNotNull(myModule);
	}

	@Test
	public final void testCompileRules() throws Exception, RPException {

		log.debug("Starting to compile rules");

		RedModel rulesToCompile = new RedModel();
		rulesToCompile.setRuleFileLocation(TestConstants.RULES_FILE);
		
		Config testConfig = new Config();

		KieBuilder myBuilder = new RuleBuilder().loadRules(rulesToCompile,testConfig);
		KieModule myModule = myBuilder.getKieModule();

		assertNotNull(myModule);
		Utils.deleteOutputFileIfExists(TestConstants.KNOWLEDGE_BASE_FILE_TMP);

	}



}
