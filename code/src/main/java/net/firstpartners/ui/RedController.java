package net.firstpartners.ui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.firstpartners.core.Config;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.IRunner;
import net.firstpartners.core.drools.RunnerFactory;
import net.firstpartners.core.json.SampleData;
import net.firstpartners.core.json.SampleDataLoader;

/**
 * Class that Spring will delegate most web requests to in order to decide what
 * to do next
 *
 * @author paulf
 * @version $Id: $Id
 */
@Controller
public class RedController {

	private static final String RED_MODEL = "redModel";
	private static final String SAMPLE_INFO = "samples";

	// handle for our config
	@Autowired
	Config appConfig;

	// Logger if needed
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Original Request - setup and display the main page
	 *
	 * @param model a {@link org.springframework.ui.Model} object
	 * @return a {@link java.lang.String} object
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {

		// this attribute will be available in the view index.html as a thymeleaf
		// variable
		log.debug("\nCreating new Red Model - replacing any previous settings with incoming values\n");
		model.addAttribute(RED_MODEL, new RedModel());

		// (re)load sample information
		try {
			List<SampleData> sampleData = SampleDataLoader.loadSampleInformation(SampleDataLoader.SAMPLE_INFO_IN_JSON);
			model.addAttribute(SAMPLE_INFO, sampleData);

		} catch (IOException e) {
			log.warn("Error loading Sample information " + e.getMessage());
		}

		model.addAttribute("updateMessage", "Please hit the 'Run Rules' or 'Run Sample' button");

		// this just means render index.html from static/ area
		return "index";
	}

	/**
	 * <p>
	 * runRules.
	 * </p>
	 *
	 * @param model    a {@link org.springframework.ui.Model} object
	 * @param redModel a {@link net.firstpartners.core.RedModel} object
	 * @return a {@link java.lang.String} object
	 */
	@RequestMapping(value = "/runRules")
	public String runRules(Model model, @ModelAttribute(RED_MODEL) RedModel redModel) {

		log.debug("subDirectory:" + redModel.getSubDirectory());
		log.debug("inputFileLocation:" + redModel.getInputFileLocation());
		log.debug("DSL?" + redModel.getDslFileLocation());
		log.debug("DRL?" + redModel.getRuleFileLocation());
		log.debug("Output?" + redModel.getOutputFileLocation());

		// Just in Case Status message that we will update as we progress
		redModel.setUICurrentStatus("Something has gone wrong, please check the messages below and in the logs.");

		// The Factory auto-generates the input and output strategy based on the
		// filenames
		IRunner runner = null;
		try {
			runner = RunnerFactory.getRuleRunner(redModel);
		} catch (RPException e) {
			model.addAttribute("displayException", e);
			redModel.setUICurrentStatus("Error creating input and output strategies");
		}

		// if we have a runner
		if (runner != null) {

			// Update our output strategy with additional info we want it to include in the
			// file
			HashMap<String, String> additionalOutputs = getAdditionalOutputs(redModel);
			runner.setAdditionalOutputData(additionalOutputs);

			// Call the rules using this datafile
			//////////////////////////////////////////////////////////
			redModel.addUIInfoMessage("Running Document Loop");
			runner.callRulesLoop(redModel);
			redModel.addUIInfoMessage("Document Loop Complete");
			//////////////////////////////////////////////////////////

			// update our main status message
			redModel.setUICurrentStatus("Rules Complete");
		}

		// Flush the user friendly logs to disk as well
		// these will still also be displayed on the webpage
		flushUserLogsToDisk(redModel.getUserMessageContents());

		// update the web values with the values coming back
		model.addAttribute("updateMessage", redModel.getUICurrentStatus());
		model.addAttribute("inputFacts", redModel.getPreRulesSnapShotAsJson());
		model.addAttribute("ruleFileMessages", redModel.getUserMessageContents());
		model.addAttribute("outputFacts", redModel.getPostRulesSnapShotAsJson());

		// Make sure any exceptions are communicated to the web
		if (redModel.getLastKnownException() != null) {
			model.addAttribute("displayException", redModel.getLastKnownException());
		}

		// make the config we used available as well
		model.addAttribute(RED_MODEL, redModel);

		// end of processing
		log.debug("##############################");
		log.debug("Documents Processing Complete");
		log.debug("##############################");

		// set the html page we want to display
		return "index";
	}

	/**
	 * Helper methods
	 * 
	 * @return
	 */
	private HashMap<String, String> getAdditionalOutputs(RedModel redModel) {

		HashMap<String, String> additionalOutputs = new HashMap<String, String>();

		// set the output date
		additionalOutputs.put(Config.ADDITIONALDATA_DATE, LocalDateTime.now().toString());

		// Note - output file set later in process

		return additionalOutputs;

	}

	/*
	 * Helper method - persist any user friendly messages to disk
	 * 
	 * @param userMessageContents
	 */
	private void flushUserLogsToDisk(List<String> userMessageContents) {


	//	Logger userLogger = LoggerFactory.getLogger(Config.USER_LOG);

		for (String message : userMessageContents) {
			
			//userLogger.info(message); // log to special userlog
			log.info(message); // useful to have at end our normal log as well

		}

		log.debug("Saved User Messages to:" + Config.USER_LOG);
	}

}
