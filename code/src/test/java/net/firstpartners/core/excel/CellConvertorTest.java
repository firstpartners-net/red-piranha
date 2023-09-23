package net.firstpartners.core.excel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;
import net.firstpartners.data.Cell;
import net.firstpartners.data.RangeList;

public class CellConvertorTest {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private static RangeList redData = null;
	private static Workbook excelData = null;

	@Before
	public void beforeClass() throws IOException, ClassNotFoundException {

		FileInputStream fileIn = new FileInputStream(TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		redData = (RangeList) in.readObject();
		in.close();
		fileIn.close();
	}

	@Test
	public final void testConvertRedCellToPoiCell() throws IOException {
		assertNotNull(redData);

		int counter =1;

		Workbook wb = WorkbookFactory.create(true); // create new boolean

		// loop through and check ranges
		Map<String, Cell> map = redData.getAllCellsWithNames();
		for (Map.Entry<String, Cell> entry : map.entrySet()) {

			log.debug("Counter:"+counter+" ========");
			counter++;
			if(counter>15) {break;}

			log.debug(entry.getKey() + ":" + entry.getValue());

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
			String tmp = thisRedCell.getValueAsText();
			String poiValue= poiCell.getStringCellValue();

			// Excel stores boolean in a different way
			if (!("[false]".contains(tmp))) {
				assertEquals(tmp, poiValue);
			}

		}

		// Save the Excel data - we will need it later
		excelData = wb;
		subTestConvertPoiCellToRedCell();
	}

	public final void subTestConvertPoiCellToRedCell() {
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
