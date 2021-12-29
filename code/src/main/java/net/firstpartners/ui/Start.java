package net.firstpartners.ui;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.drools.compiler.compiler.DroolsParserException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.drools.loader.RuleConfig;
import net.firstpartners.core.log.IGiveFeedbackToUsers;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.ui.utils.Config;

/**
 * <p>Main Entry Point for Red-Piranha. Looks for red-piranha.config to decide what
 * mode it should run in.
 * </p><p>
 * Main use case, loads data from excel (or word doc), executes business rules against it, saves
 * into another Excel file (or similar data format).
 * </p><p>
 * There is a check to ensure the input file is not the same as the output file.
 * </p><p>
 * @author PBrowne
 *
 */
@SpringBootApplication
public class Start {

	private static final Logger log = RpLogger.getLogger(Start.class.getName());


	/**
	 * TODO - update javadoc
	 * <p>Usage from command line java -jar [jarName.jar] [optional.config]</p>
	 * <p>If no optional.config is specified, it is loaded from red-piranha.config instead. This is expected to be in the current directory.
	 * </p>
	 * @param args[0] the name of the config file - defaults to @see RedConstants.RED_PIRANHA_CONFIG
	 * @throws IOException
	 * @throws DroolsParserException
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(Start.class, args);
	}
	
	public static void oldmain(String[] args) throws Exception {

		//Check if we need to override the configuration
		if(args.length>=1) {
			Config.readConfig(args[0]);
		} else {
			Config.readConfig();
		}
		
		// Check and force logging
		String logFileName = Config.getForcedLogFileName();
		RpLogger.checkForceLogToFile(logFileName);
		
		
		// Open the GUI
		log.debug("Opening GUI");
		RedGui player = new RedGui();
		Runnable readRun = new Thread(player);
		readRun.run();
		Thread.sleep(100L); // pause this thread to give the GUI time to display

		runRules(player, player);

	}

	/**
	 * Run the Rules - word in , word out
	 * @param playerAsLogger - for logging to GUI
	 * @param userUpdates - for progress feedback to GUI
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	static void runRules(ILogger playerAsLogger, IGiveFeedbackToUsers userUpdates) throws IllegalArgumentException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException {

		
		// Get the params
		String inputFileName = Config.getInputFileName();
		String outputFileName = Config.getOutputFileName();
		RuleConfig ruleConfig = Config.getRuleConfig();

		
		log.debug("DSL?"+ruleConfig.getDslFileLocation());
		
		// Handle to common utility file
		//The Factory auto-generates the input and output strategy based on the filenames
		RuleRunner runner = RuleRunnerFactory.getRuleRunner(inputFileName,ruleConfig,outputFileName);
		
		
		
		try {

			// Call the rules using this datafile
			playerAsLogger.info("Running Rules:" + ruleConfig);
			runner.callRules( userUpdates, playerAsLogger);
			playerAsLogger.info("Complete");

		} catch (Throwable t) {
			playerAsLogger.exception("Uncaught Exception", t);
			userUpdates.notifyExceptionOccured();

		}

	}
	
	

}
