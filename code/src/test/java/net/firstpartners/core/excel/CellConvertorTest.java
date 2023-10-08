package net.firstpartners.core.excel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	public final void testConvertRedCellToPoiCell() throws IOException, ClassNotFoundException {
		

		FileInputStream fileIn = new FileInputStream(TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		RangeList redData = (RangeList) in.readObject();

		in.close();
		fileIn.close();
		
		assertNotNull(redData);

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
			
			log.info("Tmp Cell Row,Col:" + tmpCellRow+","+tmpCellCol);

			//CellReference cellReference = new CellReference(tmpCellRef);
			CellReference cellReference = new CellReference(tmpCellRow,tmpCellCol);
			
			Row row = SheetConvertor.getOrCreateRow(thisSheet, cellReference);

			log.info("found Row:" + row);
			org.apache.poi.ss.usermodel.Cell poiCell = SheetConvertor.getOrCreateCell(row, cellReference);

			assertNotNull(thisRedCell);
			log.info("redCell:"+thisRedCell);

			CellConvertor.convertRedCellToPoiCell(wb, poiCell, thisRedCell);
			
			assertNotNull(poiCell);
			log.info("poiCell:"+poiCell);
			
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

		// Save the Excel data - we will need it later
		//excelData = wb;

		// try out the subtest
		subTestConvertPoiCellToRedCell(wb);
	}

	public final void subTestConvertPoiCellToRedCell(Workbook excelData) {
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
				fail("additional tests that data has been copied over etc");

			}

		}

	}

}
