package net.firstpartners.sample.multiple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.logging.Logger;

import org.drools.compiler.compiler.DroolsParserException;
import org.junit.Test;

import net.firstpartners.core.log.RpLogger;


/**
 * Turns the Multiple Rules Example into a JunitTest
 * @author paul
 *
 */
public class MultipleRulesExampleTest {

	
	// Logging
	private static final Logger log = RpLogger.getLogger(MultipleRulesExampleTest.class.getName());

	@Test
	public final void testRunMultipleRulesExample() throws DroolsParserException, IOException, ClassNotFoundException {

		log.info("Calling example");
		
		//Run the Rules Example, almost as if from the command line
		MultipleRulesExample thisSample = new MultipleRulesExample();
		CustomerOrder co =thisSample.runMultipleRulesExample();


		//Check the final co object to ensure that the rules have run as we expected
		assertNotNull(co);
		assertEquals(co.getInitialBalance(),2000);
		assertEquals(co.getCurrentBalance(),0);

		assertNotNull(co.getShipments());
		assertEquals(co.getShipments().size(),10);


	}

}
