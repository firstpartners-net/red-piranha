package net.firstpartners.ui.utils;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.log4j.Logger;

import net.firstpartners.RedConstants;
import net.firstpartners.core.drools.loader.RuleSource;
import net.firstpartners.core.log.RpLogger;

/**
 * Read the System configuration from the specified property file
 * 
 * @author PBrowne
 *
 */
public class Config {

	private static final Logger log = RpLogger.getLogger(Config.class.getName());

	// names of params to read from properties files - keep these internal
	private static final String EXCEL_INPUT = "EXCEL_INPUT";
	private static final String EXCEL_OUTPUT = "EXCEL_OUTPUT";
	private static final String DRL1 = "DRL1";
	private static final String DRL2 = "DRL2";
	private static final String DRL3 = "DRL3";
	private static final String LOG_FILE_NAME = "LOG_FILE_NAME";

	//Keys that must be present in the file
	public static String[] requiredConfigKeys = { EXCEL_INPUT, EXCEL_OUTPUT, DRL1 };	
	
	//Simple Cache
	private static Properties prop = null;

	
	
	
	/**
	 * Get the rule file names from the command line
	 * 
	 * @return a rule source object containing the drl file locations
	 */
	public static RuleSource getRuleFiles() {

		Properties prop = readConfig();

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
	 * We can override the properites (e.g. for testign)
	 * @param p - the properties we want to use
	 */
	static void setConfig(Properties p) {
		
		assert p!=null : "Properties file cannot be reset to null";
		
		prop =p;
		validateConfig();
	}
	
	/**
	 * Validate the required Config file
	 */
	static void validateConfig() {
		
		
		for (int a=0; a <requiredConfigKeys.length;a++) {
			assert prop.get(requiredConfigKeys[a])!=null : "Please make sure the config file "+RedConstants.RED_PIRANHA_CONFIG+ " contains a value for "+requiredConfigKeys[a];
			
		}
		
		if (getExcelInputFile().equalsIgnoreCase(getExcelOutputFile())) {
			log.warn("Stopping - Input and output files should not be the same");
			throw new IllegalArgumentException("Input and output files should not be the same");
		}
	}
	
	/**
	 * Reset the properties - will be reload (as if new) on next read. No value checking
	 */
	static void reset() {
		prop =null;
	}
	
	/**
	 * Read the configuration file
	 * 
	 * @return
	 */
	static Properties readConfig() {

		if (prop == null) {

			log.debug("loading config");
			prop = new Properties();

			String fileName = RedConstants.RED_PIRANHA_CONFIG;
			InputStream is = null;

			try {
				is = new FileInputStream(fileName);
			} catch (FileNotFoundException ex) {
				log.error(ex.getStackTrace().toString());
			}

			try {
				prop.load(is);
				validateConfig();
			} catch (IOException ex) {
				log.error(ex.getStackTrace().toString());
			}
			log.info("Properties file:" + prop.toString());

			
			
			return prop;

		} else {
			log.debug("using previously loaded config");

			return prop;
		}

	}

	/**
	 * If set, return the name of the Log file
	 * 
	 * @return filename , or empty / null if not present
	 */
	public static String getForcedLogFileName() {
		return readConfig().getProperty(Config.LOG_FILE_NAME, "");
	}

	/**
	 * If set, return the name of the Log file
	 * 
	 * @return filename , or empty / null if not present
	 */
	public static String getExcelInputFile() {
		return readConfig().getProperty(Config.EXCEL_INPUT, "");
	}

	/**
	 * If set, return the name of the Log file
	 * 
	 * @return filename , or empty / null if not present
	 */
	public static String getExcelOutputFile() {
		return readConfig().getProperty(Config.EXCEL_OUTPUT, "");
	}

}
