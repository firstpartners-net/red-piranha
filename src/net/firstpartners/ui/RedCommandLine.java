package net.firstpartners.ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Workbook;
import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.RedConstants;
import net.firstpartners.core.drools.FileRuleLoader;
import net.firstpartners.core.drools.SpreadSheetRuleRunner;
import net.firstpartners.core.drools.data.RuleSource;
import net.firstpartners.core.log.IGiveFeedbackToUsers;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;

/**
 * Main Entry Point for Red-Piranha
 * Looks for red-piranha.config to decide what mode it should run in
 * @author PBrowne
 *
 */
public class RedCommandLine {


	private static final Logger log = RpLogger.getLogger(RedCommandLine.class.getName());

	// Handle to common utility file
	private static final SpreadSheetRuleRunner commonUtils = new SpreadSheetRuleRunner(new FileRuleLoader());

	/**
	 * Usage from command line java -jar [jarName.jar] all args are ignored - we
	 * load from config rulesfile2 ...
	 *
	 * @param ignoredArgs - not used
	 * @throws IOException
	 * @throws DroolsParserException
	 */
	public static void main(String[] ignoredArgs) throws Exception {

		// read the properties file
		Properties prop = UiUtils.readConfig();

		// Check and force logging
		Object logFileName = prop.get(UiUtils.LOG_FILE_NAME);
		RpLogger.checkForceLogToFile(logFileName);

		// Open the GUI
		log.fine("Opening GUI");
		RedGui player = new RedGui();
		player.setGUIProperties(prop);
		Runnable readRun = new Thread(player);
		readRun.run();
		Thread.sleep(100L); // pause this thread to give the GUI time to display


		runRules(prop, player,player);

	}

	/**
	 * @param excelFile
	 * @param outputFileName
	 * @param ruleFiles
	 * @param player
	 * @throws IllegalArgumentException
	 */
	static void runRules(Properties prop, ILogger playerAsLogger,IGiveFeedbackToUsers userUpdates)
			throws IllegalArgumentException {
		
		
		// Get the params
		String excelFile = prop.getProperty(UiUtils.EXCEL_INPUT);
		String outputFileName = prop.getProperty(UiUtils.EXCEL_OUTPUT);
		RuleSource ruleFiles = UiUtils.getRuleFiles(prop);

		
		if (excelFile.equalsIgnoreCase(outputFileName)) {
			playerAsLogger.info("Stopping - Input and output files should not be the same");
			throw new IllegalArgumentException("Input and output files should not be the same");
		} else {

			try {
				// Open the inputfile as a stream
				playerAsLogger.info("Opening Excel Input file:" + excelFile);
				FileInputStream excelInput = new FileInputStream(excelFile);
				
				// Call the rules using this Excel datafile
				playerAsLogger.info("Running Rules:" + ruleFiles);
				Workbook wb = commonUtils.callRules(excelInput, ruleFiles, RedConstants.EXCEL_LOG_WORKSHEET_NAME,userUpdates);

				// delete the outputFile if it exists
				UiUtils.deleteOutputFileIfExists(outputFileName);

				// Open the outputfile as a stream
				playerAsLogger.info("Opening Excel Output file:" + outputFileName);
				FileOutputStream excelOutput = new FileOutputStream(outputFileName);

				wb.write(excelOutput);

				playerAsLogger.info("Complete");

			} catch (Throwable t) {
				playerAsLogger.exception("Uncaught Exception", t);
				
			}
		}
	}


}
