package net.firstpartners.ui.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import net.firstpartners.core.drools.loader.RuleConfig;
import net.firstpartners.core.log.RpLogger;

/**
 * Read the System configuration from the specified property file
 * 
 * @author PBrowne
 *
 */
public class Config {

	
	
	// the name of the sheet the we log files to
	public static final String EXCEL_LOG_WORKSHEET_NAME = "log";
	
	//Default name for the config file
	public static final String RED_PIRANHA_CONFIG = "red-piranha.config";
	
	public static final String RULE1 = "RULE1";
	private static final String RULE2 = "RULE2";
	private static final String RULE3 = "RULE3";
	
	public static final String DRL="DSL";
	
	public static final String GUI_MODE="GUI_MODE"; // allowed values, empty, CLOSE_ON_COMPLETION
	
	// names of params to read from properties files - keep these internal except for testing
	public static final String FILE_INPUT = "FILE_INPUT";
	public static final String FILE_OUTPUT = "FILE_OUTPUT";
	private static final Logger log = RpLogger.getLogger(Config.class.getName());
	private static final String LOG_FILE_NAME = "LOG_FILE_NAME";

	// Simple Cache
	private static Properties prop = null;

	// Keys that must be present in the file
	public static String[] requiredConfigKeys = { FILE_INPUT, FILE_OUTPUT, RULE1 };

	/**
	 * If set, return the name of the Log file
	 * 
	 * @return filename , or empty / null if not present
	 */
	public static String getForcedLogFileName() {
		return readConfig().getProperty(Config.LOG_FILE_NAME, "");
	}
	
	/**
	 * If set, return the name of the GuiMode file
	 * 
	 * @return filename , or empty / null if not present
	 */
	public static String getGuiMode() {
		return readConfig().getProperty(Config.GUI_MODE, "");
	}

	/**
	 * If set, return the name of the Log file
	 * 
	 * @return filename , or empty / null if not present
	 */
	public static String getInputFileName() {
		return readConfig().getProperty(Config.FILE_INPUT, "");
	}

	/**
	 * If set, return the name of the Log file
	 * 
	 * @return filename , or empty / null if not present
	 */
	public static String getOutputFileName() {
		return readConfig().getProperty(Config.FILE_OUTPUT, "");
	}

	/**
	 * Get the rule file names from the command line
	 * 
	 * @return a rule source object containing the drl file locations, and all the other relevant information
	 * 
	 */
	public static RuleConfig getRuleConfig() {

		Properties prop = readConfig();

		ArrayList<String> ruleFileLocations = new ArrayList<String>();
		if (prop.getProperty(RULE1) != null) {
			ruleFileLocations.add(prop.getProperty(RULE1));
		}
		if (prop.getProperty(RULE2) != null) {
			ruleFileLocations.add(prop.getProperty(RULE2));
		}
		if (prop.getProperty(RULE3) != null) {
			ruleFileLocations.add(prop.getProperty(RULE3));
		}

		// Set this as a RuleSource Object
		String[] tmpArr = new String[ruleFileLocations.size()];
		RuleConfig ruleSource = new RuleConfig();
		ruleSource.setRulesLocation(ruleFileLocations.toArray(tmpArr));

		//update other config
		ruleSource.setDslFileLocation(getDslName());
		
		return ruleSource;
	}

	/**
	 * Read the configuration file using default of @see
	 * RedConstants.RED_PIRANHA_CONFIG
	 * 
	 * @return
	 */
	public static Properties readConfig() {
		return readConfig(RED_PIRANHA_CONFIG);
	}

	/**
	 * Read the configuration file
	 * 
	 * @param configFileName allows us to set the config file location
	 * @return
	 */
	public static Properties readConfig(String configFileName) {

		if (prop == null) {

			log.debug("loading config");
			prop = new Properties();

			String fileName = configFileName;
			InputStream is = null;

			try {
				is = new FileInputStream(fileName);
				log.debug("input stream is not null:"+is==null);
			} catch (FileNotFoundException ex) {
				log.error(ex.getStackTrace().toString());
			}

			try {
				prop.load(is);
				validateConfig();
			} catch (IOException ex) {
				log.error(ex.getStackTrace().toString());
			}
			log.info("Using Configuration file:" + prop.toString());

			return prop;

		} else {
			log.debug("using previously loaded config");

			return prop;
		}

	}

	/**
	 * Reset the properties - will be reload (as if new) on next read. No value
	 * checking
	 */
	static void reset() {
		prop = null;
	}

	/**
	 * We can override the properties (e.g. for testing)
	 * 
	 * @param p - the properties we want to use - cannot be null
	 */
	public static void setProperties(Properties p) {

		assert p != null : "Properties file cannot be reset to null";

		prop = p;
		validateConfig();
	}

	/**
	 * Validate the required Config file
	 */
	static void validateConfig() {

		for (int a = 0; a < requiredConfigKeys.length; a++) {
			assert prop.get(requiredConfigKeys[a]) != null : "Please make sure the config file contains a value for " + requiredConfigKeys[a];

		}

		if (getInputFileName().equalsIgnoreCase(getOutputFileName())) {
			log.warn("Stopping - Input and output files should not be the same");
			throw new IllegalArgumentException("Input and output files should not be the same - we don't want to overwrite our original data");
		}
	}

	public static String getDslName() {
		return readConfig().getProperty(Config.DRL, "");
	}

}
