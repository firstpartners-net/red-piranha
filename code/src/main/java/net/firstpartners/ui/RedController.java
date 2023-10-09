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
import net.firstpartners.core.IDocumentOutStrategy;
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
		log.info("\nCreating new Red Model - replacing any previous settings with incoming values\n");
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
	 * <p>runRules.</p>
	 *
	 * @param model a {@link org.springframework.ui.Model} object
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
		
		try {

			// Just in Case Status message that we will update as we progress
			redModel.setUICurrentStatus("Something has gone wrong, please check the messages below and in the logs.");

			// The Factory auto-generates the input and output strategy based on the
			// filenames
			IRunner runner = RunnerFactory.getRuleRunner(redModel);

		
			//Update our output strategy with additional info we want it to include in the file
			HashMap<String,String> additionalOutputs = getAdditionalOutputs(redModel);
			runner.setAdditionalOutputData(additionalOutputs);


			// Call the rules using this datafile
			/////////////////////////////////////
			redModel.addUIInfoMessage("Running Rules:");
			runner.callRules(redModel);
			redModel.addUIInfoMessage("Rules Completed");
			////////////////////////////////////////////////////

			// update our main status message
			redModel.setUICurrentStatus("Rules Complete");

		} catch (Throwable t) {

			// We try to show as much details as possible (in the logs) without scaring the
			// user
			log.error("Full details of error", t);
			redModel.addUIWarnMessage("Uncaught Exception", t);
			model.addAttribute("displayException", t);

		}


		// update the web values with the values coming back
		model.addAttribute("updateMessage", redModel.getUICurrentStatus());
		model.addAttribute("inputFacts", redModel.getPreRulesSnapShotAsJson());
		model.addAttribute("ruleFileMessages", redModel.getUserMessageContents());
		model.addAttribute("outputFacts", redModel.getPostRulesSnapShotAsJson());

		// make the config we used available as well
		model.addAttribute(RED_MODEL, redModel);

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
		//get teh single intput file location
		additionalOutputs.put(IDocumentOutStrategy.ADDITIONALDATA_SOURCE, redModel.getInputFileLocation());

		//set the output date
		additionalOutputs.put(IDocumentOutStrategy.ADDITIONALDATA_DATE, LocalDateTime.now().toString());

		return additionalOutputs;

	}

}
