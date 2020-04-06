/**
 *
 * (c) Paul Browne, FirstPartners.net
 * Contains sample code from FIT and Drools
 */
package net.firstpartners.sample.multiple;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.drools.FileRuleLoader;
import net.firstpartners.drools.RuleRunner;
import net.firstpartners.drools.data.RuleSource;
import net.firstpartners.drools.log.ConsoleLogger;


/**
 * This is our Chocolate Shipping example
 * It aims to show
 * a) Running our rules from Java
 * b) Multiple Rules Interacting
 * @author paulbrowne
 *
 */
public class MultipleRulesExample {

	private static Log log = LogFactory.getLog(MultipleRulesExample.class);

	private static final String NEXT_AVAILABLE_SHIPMENT_DATE = "nextAvailableShipmentDate";

	private static final String[] RULES_FILES= new String[]{"war/sampleresources/multiple/shipping-rules.drl"};

	/**
	 * Main Method, allows file to be run from the command line
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		MultipleRulesExample thisSample = new MultipleRulesExample();
		thisSample.runMultipleRulesExample();
	}


	/**
	 * The Actual Example. Shows Multiple Rules Firing in Java
	 * @return
	 * @throws DroolsParserException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public  CustomerOrder runMultipleRulesExample() throws DroolsParserException, IOException, ClassNotFoundException{

		//Initial order
		CustomerOrder candyBarOrder = new CustomerOrder(2000);

		HashMap<String,Object> startDate = new HashMap<String,Object>();
		startDate.put(NEXT_AVAILABLE_SHIPMENT_DATE, new OoompaLoompaDate(2009,02,03));

		//Holidays
		OoompaLoompaDate holiday2= new OoompaLoompaDate(2009,2,10);
		OoompaLoompaDate holiday1= new OoompaLoompaDate(2009,3,17);

		log.info("===== Setup =====");
		log.info(candyBarOrder);

		//Call the rule engine
		log.info("==================== Calling Rule Runner ====================");

		Object[] facts = new Object[3];
		facts[0]=candyBarOrder;
		facts[1]=holiday1;
		facts[2]=holiday2;


		//Set these into a new RuleSource object
		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(RULES_FILES);
		ruleSource.setFacts(facts);
		ruleSource.setGlobals(startDate);

		// A lot of the running rules uses the same code. The RuleRunner (code in this project)
		// keeps this code in one place. It needs to know
		// - the name(s) of the files containing our rules
		// - the fact object(s) containing the information to be passed in and out of our rules
		// - a list of global values


		new RuleRunner(new FileRuleLoader()).runStatelessRules( ruleSource , new ConsoleLogger());

		//Look at the results
		log.info("======= Results - shipping schedule =======");
		log.info(candyBarOrder);

		return candyBarOrder;
	}
}
