package net.firstpartners.drools;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.logging.Logger;

import net.firstpartners.drools.data.RuleSource;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
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
		ruleSource.setKnowledgeBaseLocation("war/sampleresources/SpreadSheetServlet/log-then-modify-rules.KnowledgeBase");

		//do the call
		KnowledgeBase kb = ruleLoader.loadKnowledgeBase(ruleSource);
		assertNotNull(kb);
		StatefulKnowledgeSession sks = kb.newStatefulKnowledgeSession();

	}

}
