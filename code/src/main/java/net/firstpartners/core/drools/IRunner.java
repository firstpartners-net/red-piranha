package net.firstpartners.core.drools;

import java.util.List;
import java.util.Map;

import groovy.util.ResourceException;
import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;

/**
 * Marks a class as being able to execute a KIE Knowledge Model e.g. Rules or a Decision
 * Model
 */
public interface IRunner {

    /**
     * Call the rule engine, using data from the inputstrategy(s), and save the results using the output strategy.
     * @param ruleModel
     * @return
     * @throws RPException
     * @throws ResourceException
     * @throws ScriptException
     */
    public RedModel callRulesLoop(RedModel ruleModel); //throws RPException;

    /**
     * Allows us to replace previous output stategies (e.g. for testing)
     * @param newStrategy
     */
    public void setDocumentOutputStrategy(IDocumentOutStrategy newStrategy);

    /*
     * Get the (List of) InputStategy to read our data
     */
    public List<IDocumentInStrategy> getDocumentInputStrategy();

    /**
     * Get the OutputStrategy that will save our data.
     * @return
     */
    public IDocumentOutStrategy getDocumentOutputStrategy();

    /**
     * Additional Data that we'd like to output alongside our data (e.g. sourcefilename or date)
     */
    public void setAdditionalOutputData(Map<String,String> additionalDataToInclude);

}