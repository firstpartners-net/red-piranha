package net.firstpartners.core.excel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RunnerFactory;
import net.firstpartners.core.json.JsonOutputStrategy;
import net.firstpartners.data.RangeList;


public class ExcelInputStrategyTest {

	// Logger
	private static Logger log = LoggerFactory.getLogger(ExcelInputStrategyTest.class);
	
	/**
	 * Just check that the rules can run, throws no exception
	 * @throws RPException
	 */
	@Test
	public final void testXlsCallRulesFromFile() throws Exception, RPException {

		RedModel redModel = new RedModel(TestConstants.XLS_DATA_FILE,TestConstants.RULES_FILE,"some-dummy.xls");
			
		log.debug("rule source created");
		
		RuleRunner runner =(RuleRunner)RunnerFactory.getRuleRunner(redModel);
		assertTrue (runner.getDocumentOutputStrategy() instanceof ExcelOutputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setDocumentOutputStrategy(outputStrategy);
 
		runner.callRules(redModel);
		assertNotNull(outputStrategy.getProcessedDocument());

	}

	@Test
	/**
	 * Just check that the rules can run, throws no exception
	 */
	public final void testXlsXCallRulesFromFile() throws Exception, RPException {
		
		RedModel redModel = new RedModel(TestConstants.XLSX_DATA_FILE,TestConstants.RULES_FILE,"some-dummy.xls");

		RuleRunner runner =(RuleRunner)RunnerFactory.getRuleRunner(redModel);
		assertTrue (runner.getDocumentInputStrategy() instanceof ExcelInputStrategy);
		
		//set out OutputStrategy so we can test the output later
		MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
		runner.setDocumentOutputStrategy(outputStrategy);

		runner.callRules(redModel);
		assertNotNull(outputStrategy.getProcessedDocument());

	}


		/**
		 * Helper method to regeneate test data (JSON from Complet excel)
		 * @param args
		 * @throws IOException
		 * @throws InvalidFormatException
		 * @throws ResourceException
		 * @throws ScriptException
		 */
		public static void main (String args[]) throws IOException, InvalidFormatException, ResourceException, ScriptException {
		
		//have we been requested to modfiy the directoy - used later
		String dirPrefix = "code";
		if(args!=null && args.length>0) {
			dirPrefix= args[0];
		}
		
		//mockup the configuration we need
		Config testConfig = new Config();
		testConfig.setPreprocessScript(dirPrefix+"/"+TestConstants.GROOVY_PREPROCESS);
		
		//Handle to our Excel convertor and set config
		ExcelInputStrategy xlInput = new ExcelInputStrategy(dirPrefix+"/"+TestConstants.COMPLEX_EXCEL);
		xlInput.setConfig((testConfig));

		//call and get the data
		RangeList myRange = xlInput.getJavaBeansFromSource();

		JsonOutputStrategy jsonOut = new JsonOutputStrategy(dirPrefix+"/"+TestConstants.JSON_SERIAL_FILE_COMPLEX);

		// test the class
		jsonOut.setUpdates(null, myRange);
		jsonOut.processOutput();
		log.debug("Serialized data is saved in:"+ dirPrefix+"/"+TestConstants.JSON_SERIAL_FILE_COMPLEX);
	}

	
}
