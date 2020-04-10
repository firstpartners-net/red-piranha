package net.firstpartners.core.drools;

import java.io.IOException;

import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.core.drools.data.RuleSource;

public interface IRuleLoader {

	org.drools.KnowledgeBase loadRules(RuleSource ruleSource) throws DroolsParserException, IOException, ClassNotFoundException;


}