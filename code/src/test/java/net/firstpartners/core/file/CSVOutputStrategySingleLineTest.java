package net.firstpartners.core.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;
import net.firstpartners.data.RangeList;
import net.firstpartners.ui.RedControllerTest; 

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CSVOutputStrategySingleLineTest {

	// handle for our config
	@Autowired
	Config appConfig;

	// Logger
	private static Logger log = LoggerFactory.getLogger(CSVOutputStrategySingleLineTest.class);

	@Test
	public final void testAppendToCSV() throws IOException, InvalidFormatException, ClassNotFoundException {

		RangeList TestData = RedControllerTest.getTestDataFromWord();

		CSVOutputStrategySingleLine csvOut = new CSVOutputStrategySingleLine(TestConstants.CSV_APPEND_FILE);
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

	
		CSVOutputStrategySingleLine csvOut = new CSVOutputStrategySingleLine(TestConstants.CSV_APPEND_FILE);

		List<String> headers = csvOut.getHeadersFromFile();
		

		assertEquals(headers.get(0), "Input");
		assertEquals(headers.get(1), "Runtime");
		assertEquals(headers.get(2), "Name");
		assertEquals(headers.get(3), "Value");


		log.debug("completed checking headers");

	}

	@Test
	public final void testNoPreExistingCsvFile() throws InvalidFormatException, IOException {

		try {

			CSVOutputStrategySingleLine testObject = new CSVOutputStrategySingleLine("some-file-that-should-never-never-exist.csv");
			testObject.processOutput();

			fail("expected exception never thrown");

		} catch (java.io.FileNotFoundException iae) {
			// ok to ignore as we expect it
		}

	}

	
}
