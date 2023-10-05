package net.firstpartners.core.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;
import net.firstpartners.core.RPException;
import net.firstpartners.core.json.JsonInputStrategy;
import net.firstpartners.data.RangeList;
import net.firstpartners.ui.RedControllerTest;

@SpringBootTest
public class CSVOutputStrategyMultiLineTest {

	// handle for our config
	@Autowired
	Config appConfig;

	// Logger
	private static Logger log = LoggerFactory.getLogger(CSVOutputStrategyMultiLineTest.class);

	@Test
	public final void testAppendToCSV() throws IOException, InvalidFormatException, ClassNotFoundException {

		RangeList TestData = RedControllerTest.getTestDataFromWord();

		CSVOutputStrategyMultiLine csvOut = new CSVOutputStrategyMultiLine(TestConstants.CSV_APPEND_FILE);
		int previousNumberOfCsvRows = csvOut.getNumberOfRowsInFile();

		// test the class
		csvOut.setUpdates(null, TestData);
		csvOut.processOutput();

		// check that we have added one line only
		assertEquals(previousNumberOfCsvRows + 1, csvOut.getNumberOfRowsInFile());

	}

	//


	@Test
	public final void testNoPreExistingCsvFile() throws InvalidFormatException, IOException {

		try {

			CSVOutputStrategyMultiLine testObject = new CSVOutputStrategyMultiLine(
					"some-file-that-should-never-never-exist.csv");
			testObject.processOutput();

			fail("expected exception never thrown");

		} catch (java.io.FileNotFoundException iae) {
			// ok to ignore as we expect it
		}

	}

	/**
	 * Reasd Serialised JSON info, check we can output to CSV
	 * @throws RPException
	 */
	@Test
	public final void testJsonInCsvOut() throws Exception, RPException {

		//mockup the configuration we need
		Config testConfig = new Config();

		// Delete previous output file if it exists - file should not fail if it doesn't
		try{
			File tmpOutputFile = ResourceFinder.getFileResourceUsingConfig(TestConstants.CSV_TMP_FILE_MULTI_LINE, testConfig);
			if(tmpOutputFile!=null){
				tmpOutputFile.delete();
			}
		} catch (FileNotFoundException fnfe){
			log.debug("Tmpfile "+TestConstants.CSV_TMP_FILE_MULTI_LINE+" not found, assume already deleted");
		}

		//Get our sample data
		JsonInputStrategy jsInput = new JsonInputStrategy(TestConstants.JSON_SERIAL_FILE_COMPLEX);
		jsInput.setConfig((testConfig));
		RangeList testRange = jsInput.getJavaBeansFromSource();
		assertNotNull("input data should not be empty",testRange);

		//setup our CSV Outputter
		CSVOutputStrategyMultiLine csvout = new CSVOutputStrategyMultiLine(TestConstants.CSV_TMP_FILE_MULTI_LINE);
		csvout.setConfig(testConfig);
		
		//Update our output strategy with additional info we want it to use
		HashMap<String,String> additionalOutputs = new HashMap<String,String>();
		additionalOutputs.put("Input","TestFile");
		additionalOutputs.put("Runtime",LocalDateTime.now().toString());
		csvout.setAdditionalOutputData(additionalOutputs);

		
		// pass in teh data that will be output
		csvout.setUpdates(null, testRange);

		//Do the output
		csvout.processOutput();

		log.debug("csv data is saved in:"+TestConstants.CSV_TMP_FILE_MULTI_LINE);

		fail("need to test for file");

		fail ("Need to test for main output in memory");

		fail ("Need to test for additional output");

	}

}
