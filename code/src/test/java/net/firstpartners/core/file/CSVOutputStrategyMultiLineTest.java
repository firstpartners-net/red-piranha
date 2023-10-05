package net.firstpartners.core.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
	public final void testGetMatchHeadersFromCSV() throws FileNotFoundException, ClassNotFoundException, IOException {

		RangeList testData = RedControllerTest.getTestDataFromWord();

		CSVOutputStrategyMultiLine csvOut = new CSVOutputStrategyMultiLine(TestConstants.CSV_APPEND_FILE);

		List<String> headers = csvOut.getHeadersFromFile();

		assertEquals(headers.get(0), "ABC_0");
		assertEquals(headers.get(1), "ABC_1");
		assertEquals(headers.get(2), "PARA_9_GHI");
		assertEquals(headers.get(3), "University");

		Map<String, String> valuesFromCells = csvOut.getMatchingValues(headers, testData);
		assertEquals(4, valuesFromCells.size());
		assertEquals("ABC",valuesFromCells.get("ABC_0"));
		assertEquals("DEF",valuesFromCells.get("ABC_1"));
		assertEquals("GHI",valuesFromCells.get("PARA_9_GHI"));

	}

	@Test
	public final void testNoPreExistingCsvFile() throws InvalidFormatException, IOException {

		try {

			CSVOutputStrategyMultiLine testObject = new CSVOutputStrategyMultiLine("some-file-that-should-never-never-exist.csv");
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

		// Delete previous output file if it exists
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

		//setup our CSV Outputter
		CSVOutputStrategyMultiLine csvout = new CSVOutputStrategyMultiLine(TestConstants.CSV_TMP_FILE_MULTI_LINE);
		csvout.setConfig(testConfig);


		//Do the output

		// test the class
		csvout.setUpdates(null, testRange);
		csvout.processOutput();

		log.debug("csv data is saved in:"+TestConstants.CSV_TMP_FILE_MULTI_LINE);

	}


}
