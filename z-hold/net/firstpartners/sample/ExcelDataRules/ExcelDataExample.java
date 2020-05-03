package net.firstpartners.sample.ExcelDataRules;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.core.drools.loader.FileRuleLoader;
import net.firstpartners.core.drools.loader.IRuleLoaderStrategy;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.drools.RuleRunnerFactoryTest;
import net.firstpartners.core.drools.loader.RuleDTO;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.core.excel.ExcelOutputStrategy;

/**
 * Sample showing how we can read and manipulate data from excel
 * Read Ranges from Excel, Convert to a format that rules can use
 *
 * Based on Sample from Apache POI
 *
 * @author paulbrowne
 *
 */
public class ExcelDataExample {

	//Logging
	private static final Logger log = RpLogger.getLogger(ExcelDataExample.class.getName());

	private static final String EXCEL_DATA_FILE = "war/sampleresources/ExcelDataRules/chocolate-data.xls";


	// the name of the sheet the we log files to
	private static final String EXCEL_LOG_WORKSHEET_NAME = "log";

	//The rule files that we are using
	private static final String[] RULES_FILES = new String[] {
	"war/sampleresources/ExcelDataRules/log-then-modify-rules.drl"};

	/**
	 * Read an excel file and spit out what we find.
	 *
	 * @param args
	 *            Expect one argument that is the file to read.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		log.debug("Starting Sample");
		ExcelDataExample thisSample = new ExcelDataExample();
		thisSample.runExcelDataExample();
	}

	/**
	 * The Actual Test
	 * @return
	 * @throws IOException
	 * @throws DroolsParserException
	 * @throws ClassNotFoundException
	 * @throws InvalidFormatException 
	 */
	public void runExcelDataExample() throws IOException, DroolsParserException, ClassNotFoundException, InvalidFormatException{

		//Handle to the Spreadsheet Rule Runner and Rule file loader
		IRuleLoader ruleLoader = new FileRuleLoader();
		RuleRunner ruleRunner = RuleRunnerFactory.getRuleRunner(ruleLoader,EXCEL_DATA_FILE);
		

		//Start Integrate in new RuleRunner
		//Identify where the rules are stored
		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(RULES_FILES);


		//Get the URL
		File excelDataFile = new File(EXCEL_DATA_FILE);

		//Call the rule engine passing in the excel data file, the rules we want to use, and name of the spreadsheet that we log rules to
		ruleRunner.callRules(excelDataFile,ruleSource, EXCEL_LOG_WORKSHEET_NAME);



	}

}
