package net.firstpartners.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.firstpartners.drools.FileRuleLoader;
import net.firstpartners.sample.ExcelDataRules.ExcelDataRulesExample;

import java.util.logging.Logger;

/**
 * A class for configuring the RulesPlayer
 * 
 * @author paul
 * 
 */
public class SettingsLoader {

	// Where we can attempt to find the properties ifles
	private final String PROPERTIES_FILE_LOCAL = "RulePlayer.properties";
	private final String PROPERTIES_FILE_DEV = "../red-piranha/war/ruleplayer/lib/RulePlayer.properties";

	// Handle to logger
	private static final Logger log = Logger.getLogger(SettingsLoader.class
			.getName());

	/**
	 * Load the settings for the rulePlayer
	 * 
	 * @param player
	 * @throws IOException
	 */
	public void load(RulePlayerConfigurable configurablePlayer) {

		try {
			// Try to Load the file
			FileInputStream input = getInputStream(PROPERTIES_FILE_DEV);
			if (input == null) {

				log.info("Can't find properties at " + PROPERTIES_FILE_DEV
						+ " attempting alternative");

				// attempt alternative
				input = getInputStream(PROPERTIES_FILE_LOCAL);

			}

			// If still null
			if (input == null) {

				log.info("Can't find properties at " + PROPERTIES_FILE_LOCAL
						+ " - using defaults");
			} else {

				Properties myProperties = new Properties();
				myProperties.load(input);

				// set the relevant properties
				configurablePlayer.setRuleFile(myProperties
						.getProperty("RuleFile"));
				configurablePlayer.setGoogleUser(myProperties
						.getProperty("GoogleUser"));
				configurablePlayer.setProxyHost(myProperties
						.getProperty("ProxyHost"));
				configurablePlayer.setProxyPort(myProperties
						.getProperty("ProxyPort"));

			}
		} catch (IOException ioe) {
			log.info("Recovering from error:"+ioe.getMessage()+" by using default propery settings");
		}
		
	}

	/**
	 * Try to open an inputStream from a file
	 * 
	 * @param location
	 * @return
	 * @throws FileNotFoundException
	 */
	private FileInputStream getInputStream(String location) {

		File localFile = new File(location);
		if (!localFile.exists()) {
			return null;
		}

		FileInputStream returnStream = null;

		try {
			returnStream = new FileInputStream(localFile);
		} catch (FileNotFoundException e) {
			log
					.info("File InputStream Error when opening properties file - defaulting to generics");
		}

		return returnStream;
	}

}
