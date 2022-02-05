package net.firstpartners.ui;

import java.io.IOException;
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
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.json.SampleData;
import net.firstpartners.core.json.SampleDataLoader;

/**
 * Class that Spring will delegate most web requests to in order to decide what
 * to do next
 * 
 * @return
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
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {

		// this attribute will be available in the view index.html as a thymeleaf
		// variable
		log.info("\nCreating new Red Model - replacing any previous settings with incoming values\n");
		model.addAttribute(RED_MODEL, new RedModel());

		// (re)load sample information
		try {
			List<SampleData> sampleData = 
					SampleDataLoader.loadSampleInformation(SampleDataLoader.SAMPLE_INFO_IN_JSON,appConfig);
			model.addAttribute(SAMPLE_INFO, sampleData);
		} catch (IOException e) {
			log.warn("Error loading Sample information" + e.getMessage());
		}

		model.addAttribute("updateMessage", "Please hit the 'Run Rules' button");

		// this just means render index.html from static/ area
		return "index";
	}

	@RequestMapping(value = "/runRules")
	public String runRules(Model model, @ModelAttribute(RED_MODEL) RedModel redModel) {

		log.debug("inputFileLocation:" + redModel.getInputFileLocation());
		log.debug("DSL?" + redModel.getDslFileLocation());
		log.debug("DRL?" + redModel.getRuleFileLocation());
		log.debug("Output?" + redModel.getOutputFileLocation());


		try {

			// Just in Case Status message that we will update as we progress
			redModel.setCurrentStatus("Something has gone wrong, please check the messages below and in the logs.");

			// The Factory auto-generates the input and output strategy based on the
			// filenames
			RuleRunner runner = RuleRunnerFactory.getRuleRunner(redModel,appConfig);

			// Call the rules using this datafile
			redModel.addUIInfoMessage("Running Rules:" + redModel);
			runner.callRules(redModel);
			redModel.addUIInfoMessage("Complete");

			// update our main status message
			redModel.setCurrentStatus("Rules Complete");

		} catch (Throwable t) {

			// We try to show as much details as possible (in the logs) without scaring the
			// user
			log.error("Full details of error", t);
			redModel.addUIExceptionMessage("Uncaught Exception", t);
			model.addAttribute("displayException", t);

		}

		// make our full log objects available in the session

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

}