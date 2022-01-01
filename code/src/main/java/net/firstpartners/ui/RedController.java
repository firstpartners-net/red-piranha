package net.firstpartners.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.drools.loader.RuleConfig;
import net.firstpartners.core.log.BufferLogger;
import net.firstpartners.core.log.GiveLogFeedback;
import net.firstpartners.core.log.IGiveFeedbackToUsers;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.utils.Config;

/**
 * Class that Spring will delegate most web (not rest) requests to in order to
 * decide what to do next
 * 
 * @return
 */
@Controller
public class RedController {

	// handle for our config
	@Autowired
	Config appConfig;

	@Autowired
	private ApplicationContext context;

	// Logger if needed
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/zzz-some-made-up-method")
	public String index() {
		return "Greetings from Spring Boot!";
	}

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
		model.addAttribute("configBean", appConfig);

		// this just means render index.html from static/ area
		return "index";
	}

	@PostMapping("/updateConfig")
	public String submitForm(Model model, @ModelAttribute("configBean") Config configBean) {

		// Make sure our Global Config gets updated with incoming values
		appConfig.setInputFileName(configBean.getInputFileName());
		appConfig.setRule1(configBean.getRule1());
		appConfig.setOutputFileName(configBean.getOutputFileName());

		// update the web values with system value (in case we missed any)
		model.addAttribute("configBean", appConfig);

		return "index";
	}

	@PostMapping("/runRules")
	public String runRules(Model model, @ModelAttribute("configBean") Config configBean) {

		// Make sure our Global Config gets updated with incoming values
		// Get the params
		String inputFileName = appConfig.getInputFileName();
		String outputFileName = appConfig.getOutputFileName();
		RuleConfig ruleConfig = appConfig.getRuleConfig();

		log.debug("DSL?" + ruleConfig.getDslFileLocation());
		
		//Create objects to gather feedback
		BufferLogger userLogger = new BufferLogger();
		IGiveFeedbackToUsers userUpdates = new GiveLogFeedback();

		try {

			// The Factory auto-generates the input and output strategy based on the filenames
			RuleRunner runner = RuleRunnerFactory.getRuleRunner(inputFileName, ruleConfig, outputFileName);
			
			// Call the rules using this datafile
			userLogger.info("Running Rules:" + ruleConfig);
			runner.callRules(userUpdates, userLogger);
			userLogger.info("Complete");

		} catch (Throwable t) {
			userLogger.exception("Uncaught Exception", t);
			userUpdates.notifyExceptionOccured();

		}

		//make our full log objects available in the session
		
		
		// update the web values with the values coming back
		model.addAttribute("updateMessage", "Some progress message...");
		model.addAttribute("inputFileContent", "incoming values...");
		model.addAttribute("ruleFileContent", userLogger.getContents());
		model.addAttribute("outputFileContent", "output log...");

		// make the config we used available as well
		model.addAttribute("configBean", appConfig);

		return "index";
	}

}