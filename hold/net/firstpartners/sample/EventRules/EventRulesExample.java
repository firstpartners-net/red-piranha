/**
 *
 * (c) Paul Browne, FirstPartners.net
 */
package net.firstpartners.sample.EventRules;

import java.io.IOException;
import java.util.logging.Logger;

import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.core.drools.FileRuleLoader;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.config.RuleSource;
import net.firstpartners.core.log.ConsoleLogger;
import net.firstpartners.core.log.RpLogger;


/**
 * This is our Chocolate Shipping example
 * It aims to show
 * a) Running our rules from Java
 * b) Multiple Rules Interacting
 * @author paulbrowne
 *
 */
public class EventRulesExample {

	//Logging
	private static final Logger log = RpLogger.getLogger(EventRulesExample.class.getName());

	private static final String NEXT_AVAILABLE_SHIPMENT_DATE = "nextAvailableShipmentDate";

	private static final String[] RULES_FILES= new String[]{"war/sampleresources/EventRules/shipping-rules.drl"};

	public static void main(String[] args) throws Exception {

		EventRulesExample thisSample = new EventRulesExample();
		thisSample.runEventRulesExample();

	}

	public CustomerOrder runEventRulesExample() throws DroolsParserException, IOException, ClassNotFoundException{



		//Initial order
		CustomerOrder candyBarOrder = new CustomerOrder(2000);

		//Holidays
		OoompaLoompaDate holiday2= new OoompaLoompaDate(2009,2,10);
		OoompaLoompaDate holiday1= new OoompaLoompaDate(2009,3,17);


		//Set these into a new RuleSource object
		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(RULES_FILES);

		//Add the globals
		ruleSource.addGlobal(NEXT_AVAILABLE_SHIPMENT_DATE, new Cell( new OoompaLoompaDate(2009,02,03)));

		//Add the facts
		ruleSource.addFact(candyBarOrder);
		ruleSource.addFact(holiday1);
		ruleSource.addFact(holiday2);

		// A lot of the running rules uses the same code. The RuleRunner (code in this project)
		// keeps this code in one place. It needs to know
		// - the name(s) of the files containing our rules
		// - the fact object(s) containing the information to be passed in and out of our rules
		// - a list of global values


		new RuleRunner(new FileRuleLoader()).runStatelessRules( ruleSource , new ConsoleLogger());

		//Look at the results
		log.info("======= Results - shipping schedule =======");
		log.info(candyBarOrder.toString());

		return candyBarOrder;






	}
}
