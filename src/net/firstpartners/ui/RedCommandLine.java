package net.firstpartners.ui;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.RedConstants;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.drools.loader.RuleSource;
import net.firstpartners.core.log.IGiveFeedbackToUsers;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.ui.utils.Config;

/**
 * Main Entry Point for Red-Piranha. Looks for red-piranha.config to decide what
 * mode it should run in
 * 
 * By default, loads data from excel, executes business rules against it, saves
 * into another Excel file.
 * 
 * @author PBrowne
 *
 */
public class RedCommandLine {

	private static final Logger log = RpLogger.getLogger(RedCommandLine.class.getName());


	/**
	 * Usage from command line java -jar [jarName.jar] all args are ignored - we
	 * load from red-piranha.config
	 *
	 * @param ignoredArgs - not used
	 * @throws IOException
	 * @throws DroolsParserException
	 */
	public static void main(String[] ignoredArgs) throws Exception {

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
	 * @param excelFile
	 * @param outputFileName
	 * @param ruleFiles
	 * @param player
	 * @throws IllegalArgumentException
	 */
	static void runRules(ILogger playerAsLogger, IGiveFeedbackToUsers userUpdates) throws IllegalArgumentException {

		
		// Get the params
		String inputFileName = Config.getInputFileName();
		String outputFileName = Config.getOutputFileName();
		RuleSource ruleFiles = Config.getRuleFiles();

		// Handle to common utility file
		//The Factory auto-generates the input and output strategy based on the filenames
		RuleRunner runner = RuleRunnerFactory.getRuleRunner(outputFileName);
		
		try {
			// Open the input file as a stream
			playerAsLogger.info("Opening Input file:" + inputFileName);
			FileInputStream inputStream = new FileInputStream(inputFileName);

			// Call the rules using this Excel datafile
			playerAsLogger.info("Running Rules:" + ruleFiles);
			runner.callRules(inputStream, ruleFiles, RedConstants.EXCEL_LOG_WORKSHEET_NAME,
					userUpdates, playerAsLogger);

			
			playerAsLogger.info("Complete");

		} catch (Throwable t) {
			playerAsLogger.exception("Uncaught Exception", t);
			userUpdates.notifyExceptionOccured();

		}

	}

}
