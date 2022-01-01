package net.firstpartners.core.drools.loader;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.RedModelFactory;
import net.firstpartners.data.Config;
import net.firstpartners.data.RedModel;

public class FileRuleLoaderStrategyTest {



	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	public final void testLoadCachedRules() throws IOException, ClassNotFoundException {

		
		
		log.debug("starting test");
		
		//Set the rule source
		RedModel ruleSource = RedModelFactory.getFreshRedModelUsingConfiguration(new Config());
		ruleSource.setKnowledgeBaseLocation(TestConstants.KNOWLEDGE_BASE_FILE);
		log.debug("will try to load:"+ruleSource.getKnowledgeBaseLocation());
		
		// do the call
		//Class Under Test
		FileRuleLoader ruleLoader = new FileRuleLoader(ruleSource);
		org.drools.KnowledgeBase kb = ruleLoader.loadKnowledgeBase(ruleSource);
		assertNotNull(kb);
		StatefulKnowledgeSession sks = kb.newStatefulKnowledgeSession();
		assertNotNull(sks);

	}
	

	

}
