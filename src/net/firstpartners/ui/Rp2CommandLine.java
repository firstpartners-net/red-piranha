package net.firstpartners.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.core.drools.FileRuleLoader;
import net.firstpartners.core.drools.SpreadSheetRuleRunner;
import net.firstpartners.core.drools.data.RuleSource;

public class Rp2CommandLine {

	// the name of the sheet the we log files to
	private static final String EXCEL_LOG_WORKSHEET_NAME = "log";

	private static final Logger log = Logger.getLogger(Rp2CommandLine.class
			.getName());

	// Handle to common utility file
	private static final SpreadSheetRuleRunner commonUtils = new SpreadSheetRuleRunner(
			new FileRuleLoader());

	/**
	 * Usage from command line java -jar [jarName.jar]
	 * Usage: java -jar [jarName.jar] ExcelFile OutputFile rulesfile1 rulesfile2 ..
	 * As manifest setup to point to this file
	 * 
	 * If no args specificed, show Rp2Gui
	 * rulesfile2 ...
	 *
	 * @param args
	 * @throws IOException
	 * @throws DroolsParserException
	 */
	public static void main(String[] args) throws Exception {

		// check incoming args
		if ((args == null) || (args.length < 3)) {
			log
			.info("Usage: java -jar [jarName.jar] ExcelFile OutputFile rulesfile1 rulesfile2 ...");
			
			log.info("Defaulting to GUI");
			Rp2Gui.main(args);
			
			return;
		}

		//Normally the Args are
		
		// Get the params
		String excelFile = args[0];
		String outputFileName = args[1];
		RuleSource ruleArgs = convertSourceToRuleArgs(args);

		// Open the inputfile as a stream
		FileInputStream excelInput = new FileInputStream(excelFile);

		// Call the rules using this Excel datafile
		HSSFWorkbook wb = commonUtils.callRules(excelInput, ruleArgs,
				EXCEL_LOG_WORKSHEET_NAME);

		// delete the outputFile if it exists
		deleteOutputFileIfExists(outputFileName);

		// Open the outputfile as a stream
		FileOutputStream excelOutput = new FileOutputStream(outputFileName);

		wb.write(excelOutput);

	}

	/**
	 * Get the 3rd and subsequent argument passed from the command line - these
	 * are the rule file names
	 *
	 * @param commandLineArgs
	 * @return
	 */
	private static RuleSource convertSourceToRuleArgs(String[] commandLineArgs) {

		String[] ruleFileLocations = null;
		if (commandLineArgs.length > 2) {
			ruleFileLocations = new String[commandLineArgs.length - 2];
			for (int a = 0; a < ruleFileLocations.length; a++) {
				ruleFileLocations[a] = commandLineArgs[a + 2];
			}
		}

		//Set this as a RuleSource Object
		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(ruleFileLocations);

		return ruleSource;
	}

	/**
	 * Delete the output file if it already exists
	 *
	 * @param outputFile
	 */
	private static void deleteOutputFileIfExists(String outputFileName) {

		File outputFile = new File(outputFileName);
		if (outputFile.exists()) {
			outputFile.delete();
		}

	}

}
