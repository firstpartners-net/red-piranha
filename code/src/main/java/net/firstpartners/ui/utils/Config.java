package net.firstpartners.ui.utils;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import net.firstpartners.core.drools.loader.RuleConfig;
import net.firstpartners.core.log.RpLogger;

/**
 * Read the System configuration from the specified property file
 * 
 * @author PBrowne
 *
 */
@Component
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class Config {
	
	// Spring should make the property source available here
	@Autowired
	private Environment env;
	
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

	// Keys that must be present in the file
	public static String[] requiredConfigKeys = { FILE_INPUT, FILE_OUTPUT, RULE1 };

	/**
	 * If set, return the name of the Log file
	 * 
	 * @return filename , or empty / null if not present
	 */
	public String getForcedLogFileName() {
		
		log.info("Env is null:"+(env==null));
		return env.getProperty(Config.LOG_FILE_NAME);
		
		//return readConfig().getProperty(, "");
	}
	
	/**
	 * If set, return the name of the GuiMode file
	 * 
	 * @return filename , or empty / null if not present
	 */
	public String getGuiMode() {
		return env.getProperty(Config.GUI_MODE, "");
	}

	/**
	 * If set, return the name of the Log file
	 * 
	 * @return filename , or empty / null if not present
	 */
	public String getInputFileName() {
		return env.getProperty(Config.FILE_INPUT, "");
	}

	/**
	 * If set, return the name of the Log file
	 * 
	 * @return filename , or empty / null if not present
	 */
	public String getOutputFileName() {
		return env.getProperty(Config.FILE_OUTPUT, "");
	}

	/**
	 * Get the rule file names from the command line
	 * 
	 * @return a rule source object containing the drl file locations, and all the other relevant information
	 * 
	 */
	public RuleConfig getRuleConfig() {

		ArrayList<String> ruleFileLocations = new ArrayList<String>();
		if (env.getProperty(RULE1) != null) {
			ruleFileLocations.add(env.getProperty(RULE1));
		}
		if (env.getProperty(RULE2) != null) {
			ruleFileLocations.add(env.getProperty(RULE2));
		}
		if (env.getProperty(RULE3) != null) {
			ruleFileLocations.add(env.getProperty(RULE3));
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
	 * Validate the required Config file
	 */
	void validateConfig() {

		for (int a = 0; a < requiredConfigKeys.length; a++) {
			assert env.getProperty(requiredConfigKeys[a]) != null : "Please make sure the config file contains a value for " + requiredConfigKeys[a];

		}

		if (getInputFileName().equalsIgnoreCase(getOutputFileName())) {
			log.warn("Stopping - Input and output files should not be the same");
			throw new IllegalArgumentException("Input and output files should not be the same - we don't want to overwrite our original data");
		}
	}

	public String getDslName() {
		return env.getProperty(Config.DRL, "");
	}

}
