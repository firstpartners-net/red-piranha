package net.firstpartners.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.core.drools.FileRuleLoader;
import net.firstpartners.core.drools.PreCompileRuleBuilder;
import net.firstpartners.core.drools.SpreadSheetRuleRunner;
import net.firstpartners.core.drools.data.RuleSource;
import net.firstpartners.core.drools.log.ILogger;

/**
 * Main Entry Point for Red-Piranha
 * 
 * @author PBrowne
 *
 */
public class Rp2CommandLine {

	// Location of config file
	private static final String RED_PIRANHA_CONFIG = "red-piranha.config";
	private static final String RED_PIRANHA_LOG = "red-piranha.log";

	// names of params to read from properties files
	static final String EXCEL_INPUT = "EXCEL_INPUT";
	static final String EXCEL_OUTPUT = "EXCEL_OUTPUT";
	static final String DRL1 = "DRL1";
	static final String DRL2 = "DRL2";
	static final String DRL3 = "DRL3";
	static final String LOG_FILE_NAME = "LOG_FILE_NAME";

	// the name of the sheet the we log files to
	private static final String EXCEL_LOG_WORKSHEET_NAME = "log";

	private static final Logger log = Logger.getLogger(Rp2CommandLine.class.getName());

	// Handle to common utility file
	private static final SpreadSheetRuleRunner commonUtils = new SpreadSheetRuleRunner(new FileRuleLoader());

	/**
	 * Usage from command line java -jar [jarName.jar] all args are ignored - we
	 * load from config rulesfile2 ...
	 *
	 * @param args - not used
	 * @throws IOException
	 * @throws DroolsParserException
	 */
	public static void main(String[] inforedArgs) throws Exception {

		// read the properties file
		Properties prop = readConfig();

		// Check logging
		checkForceLogging(prop);

		// Get the params
		String excelFile = prop.getProperty(EXCEL_INPUT);
		String outputFileName = prop.getProperty(EXCEL_OUTPUT);
		RuleSource ruleFiles = convertSourceToRuleArgs(prop);

		// Open the GUI
		Rp2Gui player = new Rp2Gui();
		Runnable readRun = new Thread(player);
		readRun.run();
		// player.compileRules();
		// Sanity check

		if (excelFile.equalsIgnoreCase(outputFileName)) {
			player.info("Stopping - Input and output files should not be the same");
			throw new IllegalArgumentException("Input and output files should not be the same");
		} else {

			try {
				// Open the inputfile as a stream
				player.info("Opening Excel Input file:" + excelFile);
				FileInputStream excelInput = new FileInputStream(excelFile);

				// Call the rules using this Excel datafile
				player.info("Running Rules:" + ruleFiles);
				HSSFWorkbook wb = commonUtils.callRules(excelInput, ruleFiles, EXCEL_LOG_WORKSHEET_NAME);

				// delete the outputFile if it exists
				deleteOutputFileIfExists(outputFileName);

				// Open the outputfile as a stream
				player.info("Opening Excel Output file:" + outputFileName);
				FileOutputStream excelOutput = new FileOutputStream(outputFileName);

				wb.write(excelOutput);
			} catch (Throwable t) {
				player.exception("Uncaught Exception", t);
			}
		}

	}

	/**
	 * Get the rule file names from the command line
	 *
	 * @param Properties to convert
	 * @return
	 */
	static RuleSource convertSourceToRuleArgs(Properties prop) {

		ArrayList<String> ruleFileLocations = new ArrayList<String>();
		if (prop.getProperty(DRL1) != null) {
			ruleFileLocations.add(prop.getProperty(DRL1));
		}
		if (prop.getProperty(DRL2) != null) {
			ruleFileLocations.add(prop.getProperty(DRL2));
		}
		if (prop.getProperty(DRL3) != null) {
			ruleFileLocations.add(prop.getProperty(DRL3));
		}

		// Set this as a RuleSource Object
		String[] tmpArr = new String[ruleFileLocations.size()];
		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(ruleFileLocations.toArray(tmpArr));

		return ruleSource;
	}

	/**
	 * Delete the output file if it already exists
	 *
	 * @param outputFile
	 */
	static void deleteOutputFileIfExists(String outputFileName) {

		File outputFile = new File(outputFileName);
		if (outputFile.exists()) {
			outputFile.delete();
		}

	}

	/**
	 * Read the configuration file
	 * 
	 * @return
	 */
	static Properties readConfig() {

		Properties prop = new Properties();
		String fileName = RED_PIRANHA_CONFIG;
		InputStream is = null;

		try {
			is = new FileInputStream(fileName);
		} catch (FileNotFoundException ex) {
			log.warning(ex.getStackTrace().toString());
		}

		try {
			prop.load(is);
		} catch (IOException ex) {
			log.warning(ex.getStackTrace().toString());
		}
		log.info(prop.toString());

		return prop;

	}

	/**
	 * Check if we need to force turn of of logging
	 * 
	 * @param prop - looking for FORCE_LOGGING key
	 * @return true - if
	 * @throws IOException
	 * @throws SecurityException
	 */
	static void checkForceLogging(Properties prop) throws SecurityException, IOException {

		Object logFileName = prop.get(LOG_FILE_NAME);

		if (logFileName != null) {

			log.warning("RP Config Forcing Logging to:" + logFileName);

			LogManager logMan = LogManager.getLogManager();

			log.setLevel(Level.FINE);
			FileHandler fileHandler = new FileHandler(logFileName.toString());
			fileHandler.setFormatter(new SimpleFormatter());

			log.addHandler(fileHandler);

			log.info("Added forced logging");

		}
	}

	/**
	 * Compile the rules using the values that we have been passed
	 */
	void compileRules(ILogger gui, String ruleFile) {

		if (ruleFile == null) {
			gui.info("Please set 'rule' as a param pointing at the rule file you wish to compile ");
		} else {
			gui.info("Compiling Rules...\n");
			gui.info("Using file:" + ruleFile + "\n");
			PreCompileRuleBuilder rulebuilder = new PreCompileRuleBuilder();

			try {
				rulebuilder.cacheRule(ruleFile, (String) null);
			} catch (DroolsParserException ex) {
				gui.exception("DroolsParserException:" + ex + "\n", ex);
			} catch (IOException ex) {
				gui.exception("IOException:" + ex + "\n", ex);
			} catch (ClassNotFoundException ex) {
				gui.exception("ClassNotFoundException:" + ex + "\n", ex);
			}

			gui.info("Compiling complete\n");
		}

	}
}
