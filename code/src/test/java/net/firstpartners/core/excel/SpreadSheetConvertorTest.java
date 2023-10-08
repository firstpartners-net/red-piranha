package net.firstpartners.core.excel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;
import net.firstpartners.data.RangeList;

public class SpreadSheetConvertorTest {

	// handle for our config
	@Autowired
	Config appConfig;

	// if we need to change the directory prefix
	private static String dirPrefix = "";

	// Logging
	private static final Logger log = LoggerFactory.getLogger(SpreadSheetConvertorTest.class);

	/**
	 * Convenience method - serialize testdata for use by Cell Tasts
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public static void main(String args[]) throws IOException, ResourceException, ScriptException {

		// have we been requested to modfiy the directoyr
		dirPrefix = args[0];

		RangeList myRange = new SpreadSheetConvertorTest().getTestDataFromWorkbook();
		FileOutputStream fileOut = new FileOutputStream(dirPrefix + TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(myRange);
		out.close();
		fileOut.close();
		log.debug("Serialized data is saved in:" + dirPrefix + TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
	}

	private Workbook wb;

	/**
	 * This is implemented as a sub method so we can call from tests and convenience
	 * main[] method
	 * 
	 * @throws IOException
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public final RangeList getTestDataFromWorkbook() throws IOException, ResourceException, ScriptException {

		String testDataLocation = dirPrefix + TestConstants.XLSX_DATA_FILE;
		log.debug("Reading test data from :" + testDataLocation);

		FileInputStream inputStream = new FileInputStream(testDataLocation);
		wb = WorkbookFactory.create(inputStream);

		return SpreadSheetConvertor.convertNamesFromPoiWorkbookIntoRedRange(wb);

	}

	@Test
	public final void testRangeConversationSimpleExcel() throws IOException, ResourceException, ScriptException {

		// Create a new Empty POI Workbook
		Workbook localWb = new HSSFWorkbook();

		RangeList myRedRangeList = getTestDataFromWorkbook();
		assertNotNull(wb);

		// Convert over and back again
		assertNotNull(myRedRangeList);
		assertEquals(myRedRangeList.getAllCellsWithNames().size(), 132);

		// loop through and checck rnages
		Map<String, net.firstpartners.data.Cell> map = myRedRangeList.getAllCellsWithNames();
		for (Map.Entry<String, net.firstpartners.data.Cell> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
			assertNotNull(entry.getKey());
			assertNotNull(entry.getValue());

		}

		SpreadSheetConvertor.updateRedRangeintoPoiExcel(localWb, myRedRangeList);

		// Loop and do something interesting with the data
		Sheet sheet = wb.getSheetAt(0);

		assertEquals(30, sheet.getPhysicalNumberOfRows());

		for (Row row : sheet) {
			for (org.apache.poi.ss.usermodel.Cell poiCell : row) {
				log.debug("Row contains cells:" + row.getPhysicalNumberOfCells() + " Cell:" + poiCell.getCellType());
				assertTrue(row.getPhysicalNumberOfCells() > 0);
			}
		}

		// only to check our format
		OutputStream fileOut = new FileOutputStream(TestConstants.XLSX_TMP_FILE);
		wb.write(fileOut);

		log.debug("Successfully wrote converted file to :" + TestConstants.XLSX_TMP_FILE);

	}



}
