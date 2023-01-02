package net.firstpartners.core.drools;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

import org.drools.drl.parser.DroolsParserException;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.internal.logger.KnowledgeRuntimeLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.Config;
import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.log.IStatusUpdate;
import net.firstpartners.data.Cell;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeList;

/**
 * Call JBoss Drools (Rules Engine) passing in Document data as Java Objects
 *
 * This class uses an IDocumentStrategy Object to handle different types
 *
 * @author paulf
 * @version $Id: $Id
 */
public abstract class AbstractRunner implements IRunner {

	// Application Config - if passed in
	protected Config appConfig;

	// Handle to the Strategy Class for specific incoming document (Excel, Word etc
	// tasks)
	// Setup by RunnerFactory
	protected IDocumentInStrategy inputStrategy = null;

	// Handle to the logger
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	// Handle to the strategy Class to write out the document
	// Setup by RunnerFactory
	protected IDocumentOutStrategy outputStrategy = null;


	/**
	 * The strategy we use for dealing with incoming documents
	 *
	 * @return the strategy object
	 */
	public IDocumentInStrategy getDocumentInputStrategy() {
		return inputStrategy;
	}

	/**
	 * Handle to the the Strategy Delegate we use for outputting
	 *
	 * @return the strategy object
	 */
	public IDocumentOutStrategy getDocumentOutputStrategy() {
		return outputStrategy;
	}

	/**
	 * <p>
	 * Setter for the field <code>outputStrategy</code>.
	 * </p>
	 *
	 * @param newStrategy a {@link net.firstpartners.core.IDocumentOutStrategy}
	 *                    object
	 */
	public void setDocumentOutputStrategy(IDocumentOutStrategy newStrategy) {
		this.outputStrategy = newStrategy;
	}

	/**
	 * Call the Decision Engine - data input / output has already been set during
	 * the
	 * creation of this class
	 *
	 * @return our Model with all the information so we can display back to the user
	 * @throws java.lang.Exception
	 * @param ruleModel a {@link net.firstpartners.core.RedModel} object
	 */
	public RedModel callRules(RedModel ruleModel)
			throws RPException {

		// Convert the cell and log if we have a handle
		ruleModel.addUIInfoMessage("Opening Input :" + this.inputStrategy.getInputName());
		RangeList ranges = inputStrategy.getJavaBeansFromSource();

		// Set the Modified flag on the cells
		if (ranges != null) {
			ranges.cascadeResetIsModifiedFlag();
		}

		ruleModel.setPreRulesSnapShot(ranges);
		ruleModel.setUIProgressStatus(10);

		// Add the document contents as facts into the working Memory
		ruleModel.addUIInfoMessage("Adding Excel Cells as facts into Rule Engine Memory");

		if (ranges != null) {
			ruleModel.addFacts(ranges.getAllCellsInAllRanges());
		} else {
			assert ranges != null : "No Data (Ranges =null) was passed in, this is unlikely to be what you want";
		}

		ruleModel.setUIProgressStatus(30);

		// Load and fire our rules files against the data
		Collection<Cell> newFacts = runModel(ruleModel);

		// update the progress bar
		ruleModel.setUIProgressStatus(60);

		// Make a note of any new facts added
		ruleModel.addUIInfoMessage("Collecting New Cells and put them into Excel");
		Range newRange = new Range("New Facts");
		newRange.put(newFacts);
		ranges.add(newRange);
		ruleModel.setUIProgressStatus(80);

		// update a copy of the original document (to be saved as copy) with the result
		// of our rules
		log.debug("RunRules - object " + inputStrategy.getOriginalDocument());
		outputStrategy.setUpdates(inputStrategy.getOriginalDocument(), ranges);

		ruleModel.addUIInfoMessage("Write to Output file:" + outputStrategy.getOutputDestination());
		ruleModel.setUIProgressStatus(90);

		// update our post rules snapshot
		ruleModel.setPostRulesSnapShot(ranges);

		// make sure both get written (to disk?)
		outputStrategy.processOutput();
		ruleModel.setUIProgressStatus(100);

		return ruleModel;

	}


}
