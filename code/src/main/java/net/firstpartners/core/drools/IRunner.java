package net.firstpartners.core.drools;

import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;

/**
 * Marks a class as being able to execute a KIE Knowledge Model e.g. Rules or a Decision
 * Model
 */
public interface IRunner {
    public RedModel callRules(RedModel ruleModel) throws RPException, ResourceException, ScriptException;

    public void setDocumentOutputStrategy(IDocumentOutStrategy newStrategy);

    public IDocumentInStrategy getDocumentInputStrategy();

    public IDocumentOutStrategy getDocumentOutputStrategy();
}
