package net.firstpartners.drools;

import java.io.IOException;
import java.util.logging.Logger;

import net.firstpartners.drools.data.RuleSource;

import org.junit.Test;

public class FileRuleLoaderTest {

	//Class Under Test
	FileRuleLoader ruleLoader = new FileRuleLoader();

	private static final Logger log = Logger.getLogger(FileRuleLoaderTest.class
			.getName());

	@Test
	public final void testLoadCachedRules() throws IOException, ClassNotFoundException {

		//Set the rule source
		RuleSource ruleSource = new RuleSource();
		ruleSource.setKnowledgeBaseLocation("war/spreadsheet/log-then-modify-rules.KnowledgeBase");

		//do the call
		ruleLoader.loadKnowledgeBase(ruleSource);

	}

}
