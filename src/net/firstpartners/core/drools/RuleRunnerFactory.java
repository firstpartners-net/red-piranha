package net.firstpartners.core.drools;

import net.firstpartners.core.drools.loader.FileRuleLoader;
import net.firstpartners.core.drools.loader.IRuleLoader;
import net.firstpartners.core.drools.loader.RuleSource;
import net.firstpartners.core.drools.loader.URLRuleLoader;
import net.firstpartners.core.excel.ExcelInputStrategy;
import net.firstpartners.core.excel.ExcelOutputStrategy;

/**
 * Return an instance of RuleRunner, Appropriately configured with the various
 * IDocumentStrategy etc to handle the current document request
 * 
 * @author PBrowne
 *
 */
public class RuleRunnerFactory {

	/**
	 * Private Constructor, part of singleton Method
	 */
	private RuleRunnerFactory() {
	}
	
	/**
	 * Convenience constructor, state rules as single string
	 * @param inputFileName
	 * @param mySourceAsString
	 * @param outputFileName
	 * @return
	 */
	public static RuleRunner getRuleRunner(String inputFileName, String mySourceAsString, String outputFileName) {
		
		RuleSource mySource = new RuleSource();
		mySource.setRulesLocation(mySourceAsString);
		
		return getRuleRunner(inputFileName,mySource,outputFileName);
	}

	/**
	 * Create a properly configured RuleRunner for the Input / Output file types we
	 * are passing
	 * 
	 * @param outputFileName
	 * @return
	 */
	public static RuleRunner getRuleRunner(String inputFileName, RuleSource mySource, String outputFileName) {
		
		//Make sure we get the right type of loader
		IRuleLoader myLoader = getRuleLoader(mySource.getRulesLocation()[0]);
		
		return new RuleRunner(new ExcelInputStrategy(inputFileName), myLoader,
				new ExcelOutputStrategy(outputFileName));
	}

	/**
	 * Get a handle to the rule loader we will be using, based on the ruleLocation
	 * @param ruleLocation
	 * @return
	 */
	public static IRuleLoader getRuleLoader(String ruleLocation) {

		IRuleLoader ruleLoader;

		if (ruleLocation.startsWith("http")) {
			ruleLoader = new URLRuleLoader();
		} else {
			ruleLoader = new FileRuleLoader(ruleLocation);
		}

		// Default - url rule loader
		return ruleLoader;
	}

}
