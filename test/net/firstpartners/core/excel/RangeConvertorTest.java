package net.firstpartners.core.excel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.data.Cell;
import net.firstpartners.data.RangeList;

public class RangeConvertorTest {

	private Workbook wb;

	//Logging
	private static final Logger log = RpLogger.getLogger(RangeConvertorTest.class.getName());

	//Save Range and Cell Data
	public final static String SAVED_RANGEHOLDER_DATA="test/testdata/RANGEHOLDER_RANGE_CELLS.testdata";
	
	/** 
	 * Convenience method - serialize testdata for use by Cell Tasts
	 * @param args
	 * @throws IOException 
	 */
	public static void main (String args[]) throws IOException {
		
		RangeList myRange = new RangeConvertorTest().getTestDataFromWorkbook();
		FileOutputStream fileOut =new FileOutputStream(SAVED_RANGEHOLDER_DATA);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(myRange);
        out.close();
        fileOut.close();
        log.info("Serialized data is saved in:"+ SAVED_RANGEHOLDER_DATA);
	}

	/**
	 * This is implemented as a sub method so we can call from tests and convenience main[] method
	 * @throws IOException 
	 */
	private final RangeList getTestDataFromWorkbook() throws IOException {
	
		FileInputStream inputStream = new FileInputStream(TestConstants.XLSX_DATA_FILE);
		wb = WorkbookFactory.create(inputStream);
		return RangeConvertor.convertNamesFromPoiWorkbookIntoRedRange(wb);
		
	}
	
	@Test
	public final void testRangeConversation() throws IOException {
		
		RangeList myRange = getTestDataFromWorkbook();
		assertNotNull(wb);
		
		// Convert over and back again
		assertNotNull(myRange);
		assertEquals(myRange.getAllCells().size(), 132);

		// loop through and checck rnages
		Map<String, Cell> map = myRange.getAllCells();
		for (Map.Entry<String, Cell> entry : map.entrySet()) {
	        System.out.println(entry.getKey() + ":" + entry.getValue());
	        assertNotNull(entry.getKey());
	        assertNotNull(entry.getValue());
	        
	    }
			
		RangeConvertor.updateRedRangeintoPoiExcel(wb, myRange);

	}

}
