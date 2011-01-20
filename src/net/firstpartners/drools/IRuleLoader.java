package net.firstpartners.drools;

import java.io.IOException;

import net.firstpartners.drools.data.RuleSource;

import org.drools.KnowledgeBase;
import org.drools.compiler.DroolsParserException;

public interface IRuleLoader {

	KnowledgeBase loadRules(RuleSource ruleSource) throws DroolsParserException, IOException, ClassNotFoundException;


}