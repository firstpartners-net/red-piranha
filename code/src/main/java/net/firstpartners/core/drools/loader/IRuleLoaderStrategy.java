package net.firstpartners.core.drools.loader;

import java.io.IOException;

import org.drools.compiler.compiler.DroolsParserException;
import org.kie.api.builder.KieBuilder;

import net.firstpartners.data.RedModel;

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
	KieBuilder loadRules(RedModel ruleSource) throws DroolsParserException, IOException, ClassNotFoundException;



}