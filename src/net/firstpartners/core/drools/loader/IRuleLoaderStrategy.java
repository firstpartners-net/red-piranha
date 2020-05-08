package net.firstpartners.core.drools.loader;

import java.io.IOException;

import org.drools.compiler.compiler.DroolsParserException;

/**
 * Marks a class as being able to load rules on behalf of the system
 * @author PBrowne
 *
 */
public interface IRuleLoaderStrategy {

	/**
	 * loads the rules, using the source provided
	 * @param ruleSource
	 * @return
	 * @throws DroolsParserException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	org.drools.KnowledgeBase loadRules(RuleConfig ruleSource) throws DroolsParserException, IOException, ClassNotFoundException;
	
	/**
	 * gets the original rule source
	 * @return
	 */
	RuleConfig getRuleSource();


}