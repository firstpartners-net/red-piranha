package net.firstpartners.sample.multiple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import net.firstpartners.sample.multiple.CustomerOrder;
import net.firstpartners.sample.multiple.MultipleRulesExample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.compiler.DroolsParserException;
import org.junit.Test;


/**
 * Turns the Multiple Rules Example into a JunitTest
 * @author paul
 *
 */
public class MultipleRulesExampleTest {

	private static Log log = LogFactory.getLog(MultipleRulesExampleTest.class);

	@Test
	public final void testRunMultipleRulesExample() throws DroolsParserException, IOException, ClassNotFoundException {

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
