package net.firstpartners.core.excel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;
import net.firstpartners.core.file.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExcelOutputStrategyTest {

	// handle for our config
	@Autowired
	Config appConfig;

	@Test
	public final void testOutputExcelThenDelete() throws IOException {

		Workbook wb = WorkbookFactory.create(true); // create new boolean

		ExcelOutputStrategy excelOut = new ExcelOutputStrategy(TestConstants.EXCEL_TMP_FILE);
		excelOut.setWorkbook(wb);
	//	excelOut.appConfig = this.appConfig;

		// Process the output
		excelOut.processOutput();

		// check that this exists
		File f = new File(TestConstants.EXCEL_TMP_FILE);
		assertTrue("Cannot find file that should exist in:"+TestConstants.EXCEL_TMP_FILE, f.exists());
		f = null; // avoid any interference in the next step

		// Make sure we can delete file
		Utils.deleteOutputFileIfExists(TestConstants.EXCEL_TMP_FILE); // object remembers file name from earlier
		f = new File(TestConstants.EXCEL_TMP_FILE);
		assertFalse("Found file that should not exist:"+TestConstants.EXCEL_TMP_FILE, f.exists());

	}

}
