package net.firstpartners.core.file;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.data.RangeList;
import net.firstpartners.ui.RedGuiTest;

public class CSVOutputStrategyTest {

	@Test
	public final void testOutputCSVThenDelete() throws IOException, InvalidFormatException, ClassNotFoundException {


		CSVOutputStrategy csvOut = new CSVOutputStrategy(TestConstants.CSV_TMP_FILE);
		RangeList TestData=RedGuiTest.getTestDataFromWord();
		csvOut.setUpdates(null, TestData);

		// Process the output
		csvOut.processOutput();

		// check that this exists
		File f = new File(TestConstants.CSV_TMP_FILE);
		assertTrue("Cannot find file that should exist", f.exists());
		f = null; // avoid any interference in the next step

//		// Make sure we can delete file
//		Utils.deleteOutputFileIfExists(TestConstants.CSV_TMP_FILE); // object remembers file name from earlier
//		f = new File(TestConstants.CSV_TMP_FILE);
//		assertFalse("Found file that should exist", f.exists());

	}

	
	@Test
	public final void testGetJavaBeansFromSource() throws EncryptedDocumentException, IOException {
		
		RangeList readData = new CSVOutputStrategy(TestConstants.CSV_TMP_FILE).getJavaBeansFromSource();
		assertNotNull(readData);
		fail("write some more checks");
	}

	
}
