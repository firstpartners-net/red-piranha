package net.firstpartners.core.drools.loader;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;
import net.firstpartners.core.RedModelFactory;
import net.firstpartners.data.Config;
import net.firstpartners.data.RedModel;

class RedRuleBuilderTest {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Test
	void test() {
		
		RedModel myModel = RedModelFactory.getFreshRedModelUsingConfiguration(new Config());
		myModel.addRuleLocation(TestConstants.RULES_FILES);
		
		KieBuilder myBuilder = new RedRuleBuilder().getRulesFromDisk(myModel);
		KieModule myModule = myBuilder.getKieModule();
		
		assertNotNull(myModule);
	}

}
