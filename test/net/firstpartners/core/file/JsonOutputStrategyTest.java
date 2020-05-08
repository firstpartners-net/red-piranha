package net.firstpartners.core.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.data.RangeList;
import net.firstpartners.ui.RedGuiTest;

public class JsonOutputStrategyTest {

	@Test
	public final void testOutputJsoThenDelete() throws IOException, ClassNotFoundException, InvalidFormatException {
		
		RangeList TestData = RedGuiTest.getTestDataFromWord();
	
		
		JsonOutputStrategy jsonOut = new JsonOutputStrategy(TestConstants.JSON_TMP_FILE);

		// test the class
		jsonOut.setUpdates(null, TestData);
		jsonOut.processOutput();

		
		//check that this exists
		File f = new File(TestConstants.JSON_TMP_FILE);
		assertTrue ("Cannot find file that should exist", f.exists() );
		f =null; //avoid any interference in the next step
		
		//Make sure we can delete file
//		Utils.deleteOutputFileIfExists(TestConstants.JSON_TMP_FILE); //object remembers file name from earlier
//		f = new File(TestConstants.JSON_TMP_FILE);
//		assertFalse ("Found file that should exist", f.exists() );
		
		
	}

	

}
