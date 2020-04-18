package net.firstpartners.core.spreadsheet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Before;
import org.junit.Test;

import net.firstpartners.TestConstants;

public class RangeConvertorTest {

	private Workbook wb;

	@Before
	public void beforeClass() {
		
		 URL localurl = ClassLoader.getSystemResource("logging.properties");
		 System.out.println("URL:"+localurl);
		 System.setProperty("java.util.logging.config.file",localurl.getPath());	
	}

	@Before
	public final void setup() throws InvalidFormatException, IOException {

		FileInputStream inputStream = new FileInputStream(TestConstants.XLSX_DATA_FILE);
		wb = WorkbookFactory.create(inputStream);
	}

	@Test
	public final void testRangeConversation() throws IOException {
		assertNotNull(wb);

		// Convert over and back again
		RangeHolder myRange = RangeConvertor.convertNamesFromPoiWorkbookIntoRedRange(wb);
		assertNotNull(myRange);
		assertEquals(myRange.getAllCells().size(), 133);

		RangeConvertor.updateRedRangeintoPoiExcel(wb, myRange);

	}

}
