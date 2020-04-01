package net.firstpartners.drools;

import java.io.IOException;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.marshalling.impl.ProtobufMessages.KnowledgeBase;

import net.firstpartners.drools.data.RuleSource;

public interface IRuleLoader {

	KnowledgeBase loadRules(RuleSource ruleSource) throws DroolsParserException, IOException, ClassNotFoundException;


}