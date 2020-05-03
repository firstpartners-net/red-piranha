package net.firstpartners.sample.EventRules;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.drools.compiler.compiler.DroolsParserException;
import org.junit.Test;

public class EventRulesExampleTest {

	@Test
	public final void testRunEventRulesExample() throws DroolsParserException, IOException, ClassNotFoundException {

		EventRulesExample thisSample = new EventRulesExample();
		CustomerOrder co = thisSample.runEventRulesExample();

		assertNotNull(co);

		
	}

}
