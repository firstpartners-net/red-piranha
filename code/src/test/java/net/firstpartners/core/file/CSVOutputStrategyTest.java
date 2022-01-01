package net.firstpartners.core.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.data.RangeList;
import net.firstpartners.ui.RedControllerTest;

public class CSVOutputStrategyTest {

	@Test
	public final void testAppendToCSV() throws IOException, InvalidFormatException, ClassNotFoundException {

		RangeList TestData = RedControllerTest.getTestDataFromWord();

		CSVOutputStrategy csvOut = new CSVOutputStrategy(TestConstants.CSV_APPEND_FILE);
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

		CSVOutputStrategy csvOut = new CSVOutputStrategy(TestConstants.CSV_APPEND_FILE);

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

			CSVOutputStrategy testObject = new CSVOutputStrategy("some-file-that-should-never-never-exist.csv");
			testObject.processOutput();

			fail("expected exception never thrown");

		} catch (IllegalArgumentException iae) {
			// ok to ignore as we expect it
		}

	}

}
