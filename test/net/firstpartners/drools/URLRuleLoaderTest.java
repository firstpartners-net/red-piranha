package net.firstpartners.drools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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
		ruleSource.setKnowledgeBaseLocation("http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/log-then-modify-rules.KnowledgeBase");


		//Check that we can read rules from remote location
		KnowledgeBase kb = ruleLoader.loadKnowledgeBase(ruleSource);

		//Check that we can do things with the retrieved knowledgebase
		assertNotNull(kb);
		StatefulKnowledgeSession sks = kb.newStatefulKnowledgeSession();
	}

	@Test
	public final void testSecurityManagerPrefixOnUrlLoad() throws IOException, ClassNotFoundException{

		//check that dodgy domains are caught
		//Set the rule source
		RuleSource ruleSource = new RuleSource();
		ruleSource.setKnowledgeBaseLocation("http://www.bbc.co.uk/not-on-whitelist-should-fail.Knowledgebase");

		//Check that we cannot read rules from remote location
		try{
			KnowledgeBase kb = ruleLoader.loadKnowledgeBase(ruleSource);
			fail("Expected Security Exception not thrown");

		} catch (SecurityException se){
			// do nothing - we need the security exception to be thrown for the test to pass
		}
	}

	@Test
	public final void testSecurityManagerSuffixOnUrlLoad() throws IOException, ClassNotFoundException{

		//check that dodgy domains are caught
		//Set the rule source
		RuleSource ruleSource = new RuleSource();
		ruleSource.setKnowledgeBaseLocation("http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/log-then-modify-rules.SomeUnknownResourceType");

		//Check that we cannoread rules from remote location
		try{
			KnowledgeBase kb = ruleLoader.loadKnowledgeBase(ruleSource);
			fail("Expected Security Exception not thrown");

		} catch (SecurityException se){
			// do nothing - we need the security exception to be thrown for the test to pass
		}
	}


}
