package net.firstpartners.drools;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.logging.Logger;

import net.firstpartners.drools.data.RuleSource;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Test;

public class URLRuleLoaderTest {

	URLRuleLoader ruleLoader = new URLRuleLoader();

	private static final Logger log = Logger.getLogger(URLRuleLoaderTest.class
			.getName());



	@Test
	public final void testLoadCachedRulesFromUrl() throws IOException, ClassNotFoundException {

		//Set the rule source
		RuleSource ruleSource = new RuleSource();
		ruleSource.setKnowledgeBaseLocation("http://red-piranha.appspot.com/spreadsheet/log-then-modify-rules.KnowledgeBase");


		//Check that we can read rules from remote location
		KnowledgeBase kb = ruleLoader.loadKnowledgeBase(ruleSource);

		//Check that we can do things with the retrieved knowledgebase
		assertNotNull(kb);

		StatefulKnowledgeSession sks = kb.newStatefulKnowledgeSession();
	}

}
