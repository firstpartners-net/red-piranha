package net.firstpartners.sample.EventRules;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import net.firstpartners.sample.EventRules.CustomerOrder;
import net.firstpartners.sample.EventRules.EventRulesExample;

import org.drools.compiler.DroolsParserException;
import org.junit.Test;

public class EventRulesExampleTest {

	@Test
	public final void testRunEventRulesExample() throws DroolsParserException, IOException, ClassNotFoundException {

		EventRulesExample thisSample = new EventRulesExample();
		CustomerOrder co = thisSample.runEventRulesExample();

		assertNotNull(co);

		fail("Assertions Not yet implemented"); // TODO
	}

}
