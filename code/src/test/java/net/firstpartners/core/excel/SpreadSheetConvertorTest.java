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

import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.firstpartners.TestConstants;
import net.firstpartners.data.Cell;
import net.firstpartners.data.RangeList;

public class SpreadSheetConvertorTest {

	//if we need to change the directory prefix
	private static String dirPrefix="";

	//Logging
	private static final Logger log = LoggerFactory.getLogger(SpreadSheetConvertorTest.class);

	/** 
	 * Convenience method - serialize testdata for use by Cell Tasts
	 * @param args
	 * @throws IOException 
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public static void main (String args[]) throws IOException, ResourceException, ScriptException {
		
		//have we been requested to modfiy the directoyr
		dirPrefix = args[0];

		RangeList myRange = new SpreadSheetConvertorTest().getTestDataFromWorkbook();
		FileOutputStream fileOut =new FileOutputStream(dirPrefix+TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(myRange);
        out.close();
        fileOut.close();
        log.debug("Serialized data is saved in:"+ dirPrefix+TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
	}

	
	
	private Workbook wb;

	/**
	 * This is implemented as a sub method so we can call from tests and convenience main[] method
	 * @throws IOException 
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public final RangeList getTestDataFromWorkbook() throws IOException, ResourceException, ScriptException {
	
		FileInputStream inputStream = new FileInputStream(dirPrefix+TestConstants.XLSX_DATA_FILE);
		wb = WorkbookFactory.create(inputStream);
		return SpreadSheetConvertor.convertNamesFromPoiWorkbookIntoRedRange(wb);
		
	}
	
	@Test
	public final void testRangeConversation() throws IOException, ResourceException, ScriptException {
		
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
