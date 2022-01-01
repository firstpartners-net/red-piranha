package net.firstpartners.core.excel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;
import net.firstpartners.data.Cell;
import net.firstpartners.data.RangeList;

public class SpreadSheetConvertorTest {

	private Workbook wb;

	//Logging
	private static final Logger log = LoggerFactory.getLogger(SpreadSheetConvertorTest.class);

	
	
	/** 
	 * Convenience method - serialize testdata for use by Cell Tasts
	 * @param args
	 * @throws IOException 
	 */
	public static void main (String args[]) throws IOException {
		
		RangeList myRange = new SpreadSheetConvertorTest().getTestDataFromWorkbook();
		FileOutputStream fileOut =new FileOutputStream(TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(myRange);
        out.close();
        fileOut.close();
        log.debug("Serialized data is saved in:"+ TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
	}

	/**
	 * This is implemented as a sub method so we can call from tests and convenience main[] method
	 * @throws IOException 
	 */
	private final RangeList getTestDataFromWorkbook() throws IOException {
	
		FileInputStream inputStream = new FileInputStream(TestConstants.XLSX_DATA_FILE);
		wb = WorkbookFactory.create(inputStream);
		return SpreadSheetConvertor.convertNamesFromPoiWorkbookIntoRedRange(wb);
		
	}
	
	@Test
	public final void testRangeConversation() throws IOException {
		
		RangeList myRange = getTestDataFromWorkbook();
		assertNotNull(wb);
		
		// Convert over and back again
		assertNotNull(myRange);
		assertEquals(myRange.getAllCellsWithNames().size(), 132);

		// loop through and checck rnages
		Map<String, Cell> map = myRange.getAllCellsWithNames();
		for (Map.Entry<String, Cell> entry : map.entrySet()) {
	        System.out.println(entry.getKey() + ":" + entry.getValue());
	        assertNotNull(entry.getKey());
	        assertNotNull(entry.getValue());
	        
	    }
			
		SpreadSheetConvertor.updateRedRangeintoPoiExcel(wb, myRange);

	}

}
