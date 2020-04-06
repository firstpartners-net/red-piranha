package net.firstpartners.drools;

import java.io.IOException;

import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.drools.data.RuleSource;

public interface IRuleLoader {

	org.drools.KnowledgeBase loadRules(RuleSource ruleSource) throws DroolsParserException, IOException, ClassNotFoundException;


}