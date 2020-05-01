package net.firstpartners.core.word;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;

import net.firstpartners.TestConstants;

public class WordOutputStrategyTest {

	@Test
	public final void testOutputWordThenDelete() throws IOException, InvalidFormatException {

		XWPFDocument xdoc = new XWPFDocument();

		WordOutputStrategy wordOut = new WordOutputStrategy(TestConstants.WORD_TMP_FILE);
		wordOut.setWorkbook(xdoc);

		// Process the output
		wordOut.processOutput();

		// check that this exists
		File f = new File(TestConstants.WORD_TMP_FILE);
		assertTrue("Cannot find file that should exist", f.exists());
		f = null; // avoid any interference in the next step

		// Make sure we can delete file
		wordOut.deleteOutputFileIfExists(); // object remembers file name from earlier
		f = new File(TestConstants.WORD_TMP_FILE);
		assertFalse("Found file that should exist", f.exists());

	}

}
