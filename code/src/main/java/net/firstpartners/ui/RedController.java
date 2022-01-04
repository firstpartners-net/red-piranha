package net.firstpartners.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.firstpartners.core.RedModelFactory;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.log.BufferStatusUpdate;
import net.firstpartners.data.Config;
import net.firstpartners.data.RedModel;

/**
 * Class that Spring will delegate most web (not rest) requests to in order to
 * decide what to do next
 * 
 * @return
 */
@Controller
public class RedController {

	private static final String CONFIG_DISPLAY = "configDisplay";


	// handle for our config
	@Autowired
	Config appConfig;


	// Logger if needed
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Original Request
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {

		
		// this attribute will be available in the view index.html as a thymeleaf
		// variable
		model.addAttribute(CONFIG_DISPLAY, appConfig);
		
		model.addAttribute("updateMessage", "Please hit the 'Run Rules' button");

		// this just means render index.html from static/ area
		return "index";
	}

	/**
	 * Method called by Spring in response to Form submission
	 * @param model
	 * @param configBean
	 * @return
	 */
	@PostMapping("/updateConfig")
	public String submitForm(Model model, @ModelAttribute(CONFIG_DISPLAY) Config configDisplay) {

		// Make sure our Global Application Config gets updated with incoming values
		appConfig.setInputFileName(configDisplay.getInputFileName());
		appConfig.setRule1(configDisplay.getRule1());
		appConfig.setDslFileName(configDisplay.getDslFileName());
		appConfig.setOutputFileName(configDisplay.getOutputFileName());

		// update the web values with system value (in case we missed any)
		model.addAttribute(CONFIG_DISPLAY, appConfig);

		return "index";
	}

	@PostMapping("/runRules")
	public String runRules(Model model, @ModelAttribute(CONFIG_DISPLAY) Config configBean) {

		// Make sure our Global Config gets updated with incoming values
		// Get the params
		String inputFileName = appConfig.getInputFileName();
		String outputFileName = appConfig.getOutputFileName();
		
		//create a validate the info we are going to use to run the rules
		RedModel redModel = RedModelFactory.getFreshRedModelUsingConfiguration(appConfig);
		RedModelFactory.validateModel(redModel);

		log.debug("DSL?" + redModel.getDslFileLocation());
		

		//Create objects to gather feedback
		BufferStatusUpdate userUpdates = new BufferStatusUpdate();
		
		// Just in Case Status message that we will update as we progress
		userUpdates.updateCurrentStatus("Something has gone wrong, please check the messages below and in the logs."); 
		

		try {

			// The Factory auto-generates the input and output strategy based on the filenames
			RuleRunner runner = RuleRunnerFactory.getRuleRunner(inputFileName, redModel, outputFileName);
			
			
			// Call the rules using this datafile
			userUpdates.info("Running Rules:" + redModel);
			runner.callRules(userUpdates,redModel);
			userUpdates.info("Complete");
			
			//update our main status message
			userUpdates.updateCurrentStatus("Rules Complete");

		} catch (Throwable t) {

			//We try to show as much details as possible (in the logs) without scaring the user
			log.error("Full details of error", t);
			userUpdates.exception("Uncaught Exception", t);
			userUpdates.notifyExceptionOccured();
			model.addAttribute("displayException", t);

		}

		//make our full log objects available in the session
		
		
		// update the web values with the values coming back
		model.addAttribute("updateMessage", userUpdates.getCurrentStatus());
		model.addAttribute("inputFacts", userUpdates.getPreRulesSnapShotAsJson());
		model.addAttribute("ruleFileMessages", userUpdates.getContents());
		model.addAttribute("outputFacts", userUpdates.getPostRulesSnapShotAsJson());

		// make the config we used available as well
		model.addAttribute(CONFIG_DISPLAY, appConfig);

		//set the html page we want to display
		return "index";
	}

}