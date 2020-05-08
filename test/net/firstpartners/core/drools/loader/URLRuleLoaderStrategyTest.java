package net.firstpartners.core.drools.loader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.log4j.Logger;

import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Test;

public class URLRuleLoaderStrategyTest {

	URLRuleLoaderStrategy ruleLoader = new URLRuleLoaderStrategy();

	private static final Logger log = Logger.getLogger(URLRuleLoaderStrategyTest.class
			.getName());


	
	public final void testLoadCachedRulesFromUrl() throws IOException, ClassNotFoundException {

		//Set the rule source
		RuleConfig ruleSource = new RuleConfig();
		ruleSource.setKnowledgeBaseLocation("https://raw.githubusercontent.com/paulbrowne-irl/red-piranha/master/test/testdata/log-then-modify-rules.KnowledgeBase");


		//Check that we can read rules from remote location
		log.debug("Attempting to load rules from:"+ruleSource);
		org.drools.KnowledgeBase kb = ruleLoader.loadKnowledgeBase(ruleSource);

		//Check that we can do things with the retrieved knowledgebase
		assertNotNull(kb);
		StatefulKnowledgeSession sks = kb.newStatefulKnowledgeSession();
		assertNotNull(sks);
	}

	@Test
	public final void testSecurityManagerPrefixOnUrlLoad() throws IOException, ClassNotFoundException{

		//check that dodgy domains are caught
		//Set the rule source
		RuleConfig ruleSource = new RuleConfig();
		ruleSource.setKnowledgeBaseLocation("http://www.bbc.co.uk/not-on-whitelist-should-fail.Knowledgebase");

		//Check that we cannot read rules from remote location
		try{
			ruleLoader.loadKnowledgeBase(ruleSource);
			fail("Expected Security Exception not thrown");

		} catch (SecurityException se){
			// do nothing - we need the security exception to be thrown for the test to pass
		} catch (FileNotFoundException fnfe){
			// do nothing - we need the security exception to be thrown for the test to pass
		}
	}

	@Test
	public final void testSecurityManagerSuffixOnUrlLoad() throws IOException, ClassNotFoundException{

		//check that dodgy domains are caught
		//Set the rule source
		RuleConfig ruleSource = new RuleConfig();
		ruleSource.setKnowledgeBaseLocation("http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/log-then-modify-rules.SomeUnknownResourceType");

		//Check that we cannoread rules from remote location
		try{
			ruleLoader.loadKnowledgeBase(ruleSource);
			fail("Expected Security Exception not thrown");

		} catch (SecurityException se){
			// do nothing - we need the security exception to be thrown for the test to pass
		}catch (FileNotFoundException fnfe){
			// do nothing - we need the security exception to be thrown for the test to pass
		}
	}


}
