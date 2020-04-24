/**
 * 
 */
package net.firstpartners.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.RedConstants;
import net.firstpartners.core.drools.PreCompileRuleBuilder;
import net.firstpartners.core.drools.data.RuleSource;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;
/**
 * Set of common functions used by GUI / Command Line / Servlet
 * 
 * @author PBrowne
 *
 */
public class UiUtils {

	private static final Logger log = RpLogger.getLogger(UiUtils.class.getName());

	// names of params to read from properties files
	public static final String EXCEL_INPUT = "EXCEL_INPUT";
	public static final String EXCEL_OUTPUT = "EXCEL_OUTPUT";
	static final String DRL1 = "DRL1";
	static final String DRL2 = "DRL2";
	static final String DRL3 = "DRL3";
	static final String LOG_FILE_NAME = "LOG_FILE_NAME";

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

	/**
	 * Compile the rules using the values that we have been passed
	 */
	static void compileRules(ILogger gui, String ruleFile) {

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
