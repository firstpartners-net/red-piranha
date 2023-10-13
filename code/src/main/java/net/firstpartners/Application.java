package net.firstpartners;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import net.firstpartners.core.Config;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.IRunner;
import net.firstpartners.core.drools.RunnerFactory;
import net.firstpartners.core.json.SampleData;
import net.firstpartners.core.json.SampleDataLoader;
import net.firstpartners.ui.RedController;

/**
 * Main class called by Spring on Command line to start Application.
 * 
 * Note that it will automatically boot the webserver <b>AND</b>
 * Check for parameters passed via teh command line to execute a sample straight
 * away
 * 
 * @see examples.json for a list of the pre-configured examples
 * @apiNote mvn spring-boot:run -Dspring-boot.run.arguments=--examples=5
 *
 * @author paulf
 * @version $Id: $Id
 */
@SpringBootApplication
public class Application implements ApplicationRunner {

	public final static String EXAMPLE_OPTION = "examples";

	@Autowired
	Config myConfig;

	@Autowired
    private ApplicationContext appContext;

	// Logger
	private static Logger log = LoggerFactory.getLogger(Application.class);

	/**
	 * <p>
	 * main.
	 * </p>
	 *
	 * @param commandLineArgs an array of {@link java.lang.String} objects
	 */
	public static void main(String[] commandLineArgs) {

		// Allow the main Spring Application to configure itself.

        SpringApplicationBuilder app = new SpringApplicationBuilder(Application.class);
        if (commandLineArgs.length == 0) { // This can be any condition you want
            app.web(WebApplicationType.SERVLET);
			log.info("Running As Web");
        } else {
            app.web(WebApplicationType.NONE);
			log.info("running as Command Line only");
        }
        app.run(commandLineArgs);

		//was
		//SpringApplication.run(Application.class, commandLineArgs);
	}

	/**
	 * When ready, the Spring Framework will call this method
	 * It allows us to check command line parameters and execute a preconfigured
	 * example
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {

		// String [] sourceargs= args.getSourceArgs();
		// Set<String> options = args.getOptionNames();
		// String joined = String.join(",", options);

		// log.debug("OPTIONS ..... "+joined);
		// log.debug("SOURCEARGS ..... "+Arrays.toString(sourceargs));

		if (args.getOptionNames().contains(EXAMPLE_OPTION)) {

			log.info("Command line request to execute sample:" + args.getOptionValues(EXAMPLE_OPTION).get(0));
			sampleRunner(args.getOptionValues(EXAMPLE_OPTION).get(0));

			log.info("Sample finished, exiting application");
			SpringApplication.exit(appContext, () -> 0);


		} else {
			log.debug("No example requested at command line - waiting for web input");
		}

	}

	/**
	 * Pass in single number to run single example
	 * 
	 * @param sampleToRun
	 * @throws Exception
	 * @throws RPException
	 */
	private void sampleRunner(String sampleToRun) {

		//Surround by try block as we want to keep server running if if the example fails
		try {

			int sampleAsNumber= Integer.valueOf(sampleToRun);			

			List<SampleData> examples = SampleDataLoader.loadSampleInformation(SampleDataLoader.SAMPLE_INFO_IN_JSON);
			SampleData thisExample = examples.get(sampleAsNumber - 1); // adjust for 0 based sampels

			RedModel sampleModel = new RedModel();

			//Configure it using our example info
			sampleModel.setInputFileLocation(thisExample.getInputFileLocation());
			sampleModel.setRuleFileLocation(thisExample.getRuleFileLocation());
			sampleModel.setOutputFileLocation(thisExample.getOutputFileLocation());
			sampleModel.setDslFileLocation(thisExample.getDslFileLocation());
			sampleModel.setSubDirectory(thisExample.getSubDirectory());


			log.info("Running Sample"+sampleAsNumber+":\n" + sampleModel);
			IRunner runner = RunnerFactory.getRuleRunner(sampleModel);

			// Update our output strategy with additional info we want it to include in the
			HashMap<String, String> additionalOutputs = RedController.getAdditionalOutputs(sampleModel);
			runner.setAdditionalOutputData(additionalOutputs);
			

			runner.callRulesLoop(sampleModel);

			//flush logs
			flushUserLogs(sampleModel.getUserMessageContents());
			

		} catch (Throwable t) {

			log.warn("Error when trying to run inital sample:",t);
			log.info("Web Server remains available");
			
		}

	}

		/*
	 * Helper method - persist any user friendly messages to disk
	 * 
	 * @param userMessageContents
	 */
	public static void flushUserLogs(List<String> userMessageContents) {

		log.info("===== User Messages =====");
		// Logger userLogger = LoggerFactory.getLogger(Config.USER_LOG);

		for (String message : userMessageContents) {

			// userLogger.info(message); // log to special userlog
			log.info(message); // useful to have at end our normal log as well

		}

		//log.debug("Saved User Messages to:" + Config.USER_LOG);
	}
}
