package net.firstpartners.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;
import net.firstpartners.data.RangeList;

/**
 * Test to call the main method
 * 
 * @author PBrowne
 *
 */
public class RedControllerTest {

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	public final void testCallMain() throws Throwable {

		// Check where we are
		File whereAmI = new File(".");
		log.debug("Default file location:" + whereAmI.getAbsolutePath());

		// Call test

	}


	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static RangeList getTestDataFromExcel() throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		RangeList redData = null;
		fileIn = new FileInputStream(TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
		in = new ObjectInputStream(fileIn);
		redData = (RangeList) in.readObject();
		fileIn.close();
		return redData;
	}

	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static RangeList getTestDataFromWord() throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream fileIn;
		ObjectInputStream in;
		RangeList redWordData = null;
		fileIn = new FileInputStream(TestConstants.SAVED_WORD_RANGEHOLDER_DATA);
		in = new ObjectInputStream(fileIn);
		redWordData = (RangeList) in.readObject();
		fileIn.close();
		return redWordData;
	}
}
