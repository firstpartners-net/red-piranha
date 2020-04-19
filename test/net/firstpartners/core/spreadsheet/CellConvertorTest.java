package net.firstpartners.core.spreadsheet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;

import net.firstpartners.core.log.RpLogger;

import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(Alphanumeric.class)
public class CellConvertorTest {

	// Handle to the logger
	private static final Logger log = RpLogger.getLogger(CellConvertorTest.class.getName());

	private static RangeHolder redData = null;
	private static Workbook excelData = null;

	@Before
	public void beforeClass() throws IOException, ClassNotFoundException {

		FileInputStream fileIn = new FileInputStream(RangeConvertorTest.SAVED_RANGEHOLDER_DATA);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		redData = (RangeHolder) in.readObject();
		in.close();
		fileIn.close();
	}

	@Test
	public final void testConvertRedCellToPoiCell() throws IOException {
		assertNotNull(redData);

		Workbook wb = WorkbookFactory.create(true); // create new boolean

		// loop through and chesck ranges
		Map<String, Cell> map = redData.getAllCells();
		for (Map.Entry<String, Cell> entry : map.entrySet()) {

			log.info(entry.getKey() + ":" + entry.getValue());

			String originalName = entry.getKey();
			Cell thisRedCell = entry.getValue();

			assertNotNull(originalName);
			assertNotNull(thisRedCell);

			// Get a handle to the Excel cell at Sheet / reference
			// TODO this will likely fail since we don't have the matching 'chocolate' sheet - update code to create as needed
			org.apache.poi.ss.usermodel.Sheet thisSheet = wb.getSheet(thisRedCell.getOriginalSheetReference());
			CellReference cellReference = new CellReference(thisRedCell.getOriginalCellReference());
			Row row = thisSheet.getRow(cellReference.getRow());
			org.apache.poi.ss.usermodel.Cell poiCell = row.getCell(cellReference.getCol());

			//
			CellConvertor.convertRedCellToPoiCell(wb, poiCell, thisRedCell);
			fail("Add tests to compare before and after on each Poi Cell");
		}
		
		//Save the Excel data - we will need it later
		excelData = wb;
	}

	@Test
	public final void testConvertPoiCellToRedCell() {
		assertNotNull(excelData);
		
		 Sheet sheet = excelData.getSheetAt(0);
         
         // Iterate over each row in the sheet
         Iterator<?> rows = sheet.rowIterator(); 
         while( rows.hasNext() ) {           
             Row row = (Row) rows.next();
             log.info( "Row #" + row.getRowNum() );

             // Iterate over each cell in the row and print out the cell's content
             Iterator<?> cells = row.cellIterator();
             while( cells.hasNext() ) {
            	 org.apache.poi.ss.usermodel.Cell poiCell = (org.apache.poi.ss.usermodel.Cell) cells.next();
            	 Cell redCell = CellConvertor.convertPoiCellToRedCell("", poiCell);
                
            	 
            	 assertNotNull(redCell);
            	 fail("additional tests that data has been copied over etc");
               
                 
             }
             
         }

	}

}
