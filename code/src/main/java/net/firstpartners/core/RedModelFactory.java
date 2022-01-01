package net.firstpartners.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.firstpartners.data.Config;
import net.firstpartners.data.RedModel;

/**
 * Generate Model Object, using latest configuration
 * @author paulf
 *
 */
@Component
public class RedModelFactory {
	
	// handle for our config
	@Autowired
	Config appConfig;
	
	// Logger
	private static Logger log = LoggerFactory.getLogger(RedModelFactory.class);
	
	/**
	 * Get a fresh Model (rule source, outputs etc) based on current config
	 * 
	 * @return a Red Model source object containing the drl
	 */
	public static RedModel getFreshRedModelUsingConfiguration(Config thisConfig) {

		//new model we are building
		RedModel freshModel=  new RedModel(thisConfig);
		
		
		if (thisConfig.getRule1() != null && thisConfig.getRule1() != Config.EMPTY) {
			freshModel.addRuleLocation(thisConfig.getRule1());
		}
		
		if (thisConfig.getRule2() != null && thisConfig.getRule2() != Config.EMPTY) {
			freshModel.addRuleLocation(thisConfig.getRule2());
		}

		if (thisConfig.getRule3() != null && thisConfig.getRule3() != Config.EMPTY) {
			freshModel.addRuleLocation(thisConfig.getRule3());
		}

		//Validate this model
		validateModel (freshModel);
		
		return freshModel;
	}

	/**
	 * Validate the required Config file
	 */
	static void validateModel(RedModel modelToValidate) {

		// Check for required Keys
		assert modelToValidate.getRulesLocation() != null : "Please make sure the config file contains a value for Rule 1";
		assert modelToValidate.getRulesLocation().length >0 : "Please make sure the config file contains at least one rules file";
		
		assert modelToValidate.getConfig().getInputFileName() != null : "Please make sure the config file contains a value for the Input file";
		assert modelToValidate.getConfig().getOutputFileName() != null : "Please make sure the config file contains a value for the Output file";

		if (modelToValidate.getConfig().getInputFileName().equalsIgnoreCase(modelToValidate.getConfig().getOutputFileName())) {
			log.warn("Stopping - Input and output files should not be the same");
			throw new IllegalArgumentException(
					"Input and output files should not be the same - we don't want to overwrite our original data");
		}
	}

}
