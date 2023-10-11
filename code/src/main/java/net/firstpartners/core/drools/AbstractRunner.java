package net.firstpartners.core.drools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.firstpartners.core.Config;
import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.data.Cell;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeList;

/**
 * Call JBoss Drools (Rules Engine) passing in Document data as Java Objects
 *
 * This class uses an IDocumentStrategy Object to handle different types of
 * input and output.
 *
 * @author paulf
 * @version $Id: $Id
 */
public abstract class AbstractRunner implements IRunner {


	// Handle to the Strategy Class for specific incoming document (Excel, Word etc
	// tasks)
	// Setup by RunnerFactory
	protected List<IDocumentInStrategy> inputStrategy = new ArrayList<IDocumentInStrategy>();

	// Handle to the logger
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	// Handle to the strategy Class to write out the document
	// Setup by RunnerFactory
	protected IDocumentOutStrategy outputStrategy = null;

	//Additional data that we wish to be passed to the output
	Map<String,String> additionalData = new HashMap<String,String>();

	//getts and setters
	public Map<String, String> getAdditionalOutputData() {
		return additionalData;
	}

	public void setAdditionalOutputData(Map<String, String> additionalData) {
		this.additionalData = additionalData;
	}


	/**
	 * The strategy we use for dealing with incoming documents
	 *
	 * @return the strategy object
	 */
	public List<IDocumentInStrategy> getDocumentInputStrategy() {
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
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public RedModel callRules(RedModel ruleModel)
			throws RPException {
		//loop over our input file(s)
		for(IDocumentInStrategy thisDocumentSource : this.inputStrategy)
		{  


			// Convert the cell and log if we have a handle
			log.debug("=======================================================");
			log.debug("\n\n");
			log.info("Opening Input:"+thisDocumentSource.getInputDetails());
			log.debug("\n\n");
			log.debug("=======================================================");



			ruleModel.addUIInfoMessage("Opening Input :" + thisDocumentSource.getInputDetails());

			RangeList ranges;
			try {
				ranges = thisDocumentSource.getJavaBeansFromSource();
			} catch (EncryptedDocumentException e) {
				log.warn("error",e);
				throw new RPException("EncryptedDocumentException when opening Input", e);
			} catch (ResourceException e) {
				log.warn("error",e);
				throw new RPException("ResourceException when opening Input", e);
			} catch (ScriptException e) {
				log.warn("error",e);
				throw new RPException("ScriptException when opening Input", e);
			} catch (InvalidFormatException e) {
				log.warn("error",e);
				throw new RPException("InvalidFormatException when opening Input", e);
			} catch (IOException e) {
				log.warn("error",e);
				throw new RPException("IOException when opening Input", e);
			}

			// Set the Modified flag on the cells
			if (ranges != null) {
				ranges.cascadeResetIsModifiedFlag();
			}

			// Update / snapshot our progress
			ruleModel.setPreRulesSnapShot(ranges);

			// Add the document contents as facts into the working Memory
			ruleModel.addUIInfoMessage("Adding Excel Cells as facts into Rule Engine Memory");

			if (ranges != null) {
				ruleModel.addFacts(ranges.getAllCellsInAllRanges());
			} else {
				throw new RPException("No Data (Ranges =null) was passed in, this is unlikely to be what you want");
			}

			// Load and fire our rules files against the data
			Collection<Cell> newFacts = runModel(ruleModel);

			// Make a note of any new facts added
			ruleModel.addUIInfoMessage("Collecting New Cells and put them into Excel");
			Range newRange = new Range("New Facts");
			newRange.put(newFacts);
			ranges.add(newRange);

			// update a copy of the original document (to be saved as copy) with the result
			// of our rules
			try {
				outputStrategy.setUpdates(thisDocumentSource.getOriginalDocument(), ranges);
			} catch (IOException e) {
				throw new RPException("Error when updating document", e);
			}

			ruleModel.addUIInfoMessage("Write to Output file:" + outputStrategy.getOutputDestination());

			// update our post rules snapshot
			ruleModel.setPostRulesSnapShot(ranges);

			//update any additional output data
			ClassAndLocation cAndL =thisDocumentSource.getInputDetails();
			String updateSource =cAndL.locationText;// default
			if(cAndL!=null&&cAndL.fileLocation!=null){
				//Better to use actual file location
				updateSource= cAndL.fileLocation.getName();
			}
			log.debug("Updated Source in Output to:"+updateSource);

			additionalData.put(Config.ADDITIONALDATA_SOURCE,updateSource); // overwrite any previous source
			outputStrategy.setAdditionalOutputData(additionalData);

			// make sure both get written (to disk?)
			try {
				outputStrategy.processOutput();
			} catch (InvalidFormatException | IOException e) {
				throw new RPException("Error when writing out document", e);
			}
			log.debug("==========================");
			log.info("Current Document Complete");
			log.debug("==========================");

		} // end loop over document

		return ruleModel;

	}


	/**
	 * Abstract Method to be implemented by subclasses
	 * 
	 * @param model
	 * @return
	 */
	abstract Collection<Cell> runModel(RedModel model) throws RPException;

}
