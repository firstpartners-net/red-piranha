package net.firstpartners.core.drools.loader;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.logging.Logger;

import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Test;

import net.firstpartners.TestConstants;

public class FileRuleLoaderTest {

	//Class Under Test
	FileRuleLoader ruleLoader = new FileRuleLoader();

	private static final Logger log = Logger.getLogger(FileRuleLoaderTest.class
			.getName());

	@Test
	public final void testLoadCachedRules() throws IOException, ClassNotFoundException {

		log.fine("starting test");
		
		//Set the rule source
		RuleSource ruleSource = new RuleSource();
		ruleSource.setKnowledgeBaseLocation(TestConstants.KNOWLEDGE_BASE_FILE);
		log.fine("will try to load:"+ruleSource.getKnowledgeBaseLocation());
		
		
		
		// do the call
		org.drools.KnowledgeBase kb = ruleLoader.loadKnowledgeBase(ruleSource);
		assertNotNull(kb);
		StatefulKnowledgeSession sks = kb.newStatefulKnowledgeSession();
		assertNotNull(sks);

	}
	

	

}
