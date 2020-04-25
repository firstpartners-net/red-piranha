/**
 * 
 */
package net.firstpartners.ui.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import net.firstpartners.RedConstants;
import net.firstpartners.core.drools.config.RuleSource;
import net.firstpartners.core.log.RpLogger;
/**
 * Set of common functions used by GUI / Command Line / Servlet
 * 
 * @author PBrowne
 *
 */
public class Config {

	private static final Logger log = RpLogger.getLogger(Config.class.getName());

	// names of params to read from properties files
	public static final String EXCEL_INPUT = "EXCEL_INPUT";
	public static final String EXCEL_OUTPUT = "EXCEL_OUTPUT";
	static final String DRL1 = "DRL1";
	static final String DRL2 = "DRL2";
	static final String DRL3 = "DRL3";
	public static final String LOG_FILE_NAME = "LOG_FILE_NAME";

	/**
	 * Get the rule file names from the command line
	 *
	 * @param Properties to convert
	 * @return
	 */
	public static RuleSource getRuleFiles(Properties prop) {

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
	 * Read the configuration file
	 * 
	 * @return
	 */
	public static Properties readConfig() {

		Properties prop = new Properties();
		String fileName = RedConstants.RED_PIRANHA_CONFIG;
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




}
