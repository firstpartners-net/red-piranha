package net.firstpartners.core.drools.loader;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.file.ResourceFinder;

class RuleBuilderTest {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	void testDefaultBuild() throws Exception, RPException {

		RedModel myModel = new RedModel();

		
		myModel.setRuleFileLocation(TestConstants.SIMPLE_LOG_MODIFY_RULES_FILE);

		KieBuilder myBuilder = new RuleBuilder().loadRules(myModel);
		KieModule myModule = myBuilder.getKieModule();

		assertNotNull(myModule);
	}

	@Test
	public final void testCompileRules() throws Exception, RPException {

		log.debug("Starting to compile rules");

		RedModel rulesToCompile = new RedModel();
		rulesToCompile.setRuleFileLocation(TestConstants.SIMPLE_LOG_MODIFY_RULES_FILE);
		

		KieBuilder myBuilder = new RuleBuilder().loadRules(rulesToCompile);
		KieModule myModule = myBuilder.getKieModule();

		assertNotNull(myModule);
		ResourceFinder.deleteOutputFileIfExists(TestConstants.KNOWLEDGE_BASE_FILE_TMP);

	}



}
