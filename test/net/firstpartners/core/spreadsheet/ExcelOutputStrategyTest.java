package net.firstpartners.core.spreadsheet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import net.firstpartners.TestConstants;

public class ExcelOutputStrategyTest {

	@Test
	public final void testOutputExcelThenDelete() throws IOException {
		
		ExcelOutputStrategy fileUtils = new ExcelOutputStrategy(TestConstants.EXCEL_TMP_FILE);
		
		
		Workbook wb = WorkbookFactory.create(true); // create new boolean
		fileUtils.processOutput(wb);
		
		//check that this exists
		File f = new File(TestConstants.EXCEL_TMP_FILE);
		assertTrue ("Cannot find file that should exist", f.exists() );
		f =null; //avoid any interference in the next step
		
		//Make sure we can delete file
		fileUtils.deleteOutputFileIfExists(); //object remembers file name from earlier
		f = new File(TestConstants.EXCEL_TMP_FILE);
		assertFalse ("Found file that should exist", f.exists() );
		
		
	}

}

