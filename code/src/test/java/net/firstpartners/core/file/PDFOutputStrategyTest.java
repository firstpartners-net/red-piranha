package net.firstpartners.core.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.TestConstants;
import net.firstpartners.data.RangeList;
import net.firstpartners.ui.RedControllerTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PDFOutputStrategyTest {


	@Test
	public final void testOutputPdfThenDelete() throws IOException, InvalidFormatException, ClassNotFoundException {


		PDFOutputStrategy pdfOut = new PDFOutputStrategy(TestConstants.PDF_TMP_FILE);
		RangeList TestData=RedControllerTest.getTestDataFromExcel();
		pdfOut.setUpdates(null, TestData);

		// Process the output
		pdfOut.processOutput();

		// check that this exists
		File f = new File(TestConstants.PDF_TMP_FILE);
		assertTrue("Cannot find file that should exist", f.exists());
		f = null; // avoid any interference in the next step

		// Make sure we can delete file
		Utils.deleteOutputFileIfExists(TestConstants.PDF_TMP_FILE); // object remembers file name from earlier
		f = new File(TestConstants.PDF_TMP_FILE);
		assertFalse("Found file that should exist", f.exists());

	}

}
