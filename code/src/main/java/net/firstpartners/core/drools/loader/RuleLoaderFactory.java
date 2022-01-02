package net.firstpartners.core.drools.loader;

import net.firstpartners.data.RedModel;

public class RuleLoaderFactory {

	/**
	 * Get a handle to the rule loader we will be using, based on the ruleLocation
	 * 
	 * @param ruleLocation
	 * @return
	 */
	public static IRuleLoaderStrategy getRuleLoader(RedModel ruleLocation) {

		IRuleLoaderStrategy ruleLoader = new RedRuleBuilder();
//		String firstRuleLocation = ruleLocation.getRulesLocation()[0].toLowerCase();
//		
//
//		if (firstRuleLocation.startsWith("http")) {
//			if (true) {throw new RuntimeException("http loading not implemented yet");}
//			ruleLoader = new URLRuleLoaderStrategy();
//		} else {
//			ruleLoader = new FileRuleLoader(ruleLocation);
//		}

		// Default - url rule loader
		return ruleLoader;
	}
	
}
