package net.firstpartners.core.excel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.core.file.ResourceFinder;
import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;
import net.firstpartners.data.Cell;
import net.firstpartners.data.RangeList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CellConvertorTest {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// handle for our config
	@Autowired
	Config appConfig;
	
	@Test
	public final void testRedPoiRed() throws IOException, ClassNotFoundException {
		
		
		
 		//Get our sample data
		

		FileInputStream fileIn = new FileInputStream(TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		RangeList redData = (RangeList) in.readObject();
		in.close();
		fileIn.close();
		assert redData !=null;

		//========================
		//====== POI TO RED ======
		//========================

		int counter =1;

		Workbook wb = WorkbookFactory.create(true); // create new boolean

		// loop through and check ranges
		Map<String, Cell> map = redData.getAllCellsWithNames();
		assertNotNull(map);
		assertTrue (!map.isEmpty());

		for (Map.Entry<String, Cell> entry : map.entrySet()) {

			log.debug("Counter:"+counter+" ========");
			counter++;
			if(counter>10) {break;}

			log.debug("Red Cell "+entry.getKey() + ":" + entry.getValue());

			String originalName = entry.getKey();
			Cell thisRedCell = entry.getValue();

			assertNotNull(originalName);
			assertNotNull(thisRedCell);

			// Get a handle to the Excel cell at Sheet / reference
			org.apache.poi.ss.usermodel.Sheet thisSheet = SheetConvertor.getOrCreateSheet(wb, thisRedCell);

			//String tmpCellRef = thisRedCell.getOriginalCellReference();
			int tmpCellRow = thisRedCell.getOriginalCellRow();
			int tmpCellCol = thisRedCell.getOriginalCellCol();
			
			log.debug("Tmp Cell Row,Col:" + tmpCellRow+","+tmpCellCol);

			//CellReference cellReference = new CellReference(tmpCellRef);
			CellReference cellReference = new CellReference(tmpCellRow,tmpCellCol);
			
			Row row = SheetConvertor.getOrCreateRow(thisSheet, cellReference);

			log.debug("found Row:" + row);
			org.apache.poi.ss.usermodel.Cell poiCell = SheetConvertor.getOrCreateCell(row, cellReference);

			assertNotNull(thisRedCell);
			log.debug("redCell:"+thisRedCell);

			CellConvertor.convertRedCellToPoiCell(wb, poiCell, thisRedCell);
			
			assertNotNull(poiCell);
			log.debug("poiCell:"+poiCell);
			
			//check that values have been copieed over
			String redValue = thisRedCell.getValueAsText();
			String poiValue= poiCell.getStringCellValue();

			//our test values might be boolean - so check for those
			if(poiValue.equals("")){
				poiValue =""+poiCell.getBooleanCellValue();
			}

			log.debug("RedCell:"+thisRedCell);
			log.debug("PoiCell:"+poiCell);
			

			// if we don't have a value, then pass over comparision as likely new cell
			if (redValue!="") {
				//assertEquals(redValue, poiValue);
			}

		}

		//Map data across
		Workbook excelData=wb;

		//========================
		//====== RED TO POI ======
		//========================
		assertNotNull(excelData);

		Sheet sheet = excelData.getSheetAt(0);

		// Iterate over each row in the sheet
		Iterator<?> rows = sheet.rowIterator();
		while (rows.hasNext()) {
			Row row = (Row) rows.next();
			log.debug("Row #" + row.getRowNum());

			// Iterate over each cell in the row and print out the cell's content
			Iterator<?> cells = row.cellIterator();
			while (cells.hasNext()) {
				org.apache.poi.ss.usermodel.Cell poiCell = (org.apache.poi.ss.usermodel.Cell) cells.next();
				Cell redCell = CellConvertor.convertPoiCellToRedCell("", poiCell);

				assertNotNull(redCell);
				
			}

		}

	}

		/**
	 * Test the formatter - especially useful to get dates as "31/12/20" etc
	 * 
	 * @throws Exception
	 */
	@Test
	public final void testForcedDateRenaming() throws Exception {

		File xlFile = ResourceFinder.getFile(TestConstants.COMPLEX_EXCEL);
		InputStream inputAsStream = new FileInputStream(xlFile);
		Workbook excelWorkBook = WorkbookFactory.create(inputAsStream);

		// handle to the class under test
		//ScriptSupport sprt = new ScriptSupport(excelWorkBook);

		// Get a handle to a cell, format, check return value
		org.apache.poi.ss.usermodel.Sheet sheet = excelWorkBook.getSheet("Accounts");

		// setup a hasmhap of cells and the values we expect to extract
		HashMap<String, String> testMap = new HashMap<String, String>();
		// testMap.put("Accounts!B39","2751.0");
		testMap.put("Accounts!A14", "No. of Global Employees @ y/e (incl Irish employment)");
		testMap.put("Accounts!B34", "31/12/18");

		// Iterating HashMap through for loop
		for (Map.Entry<String, String> set : testMap.entrySet()) {

			CellReference cellReference = new CellReference(set.getKey());
			log.debug("Testing cell:" + set.getKey() + " hoping for:" + set.getValue());

			Row row = sheet.getRow(cellReference.getRow());
			org.apache.poi.ss.usermodel.Cell cell = row.getCell(cellReference.getCol());

			assertEquals(set.getValue(), CellConvertor.getCellAsStringForceDateConversion(cell));

		}
	}

}
