package net.firstpartners.core.excel;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;


/**
 * A class that helps up map Excel Worksheets into Javabeans.
 *
 * In general, we operate at the Named Range level, so the function is primarily ensuring that we can convert JavaBeans back into Excel
 *
 * @author PBrowne
 * @version $Id: $Id
 */
public class SheetConvertor {

	private static final Logger log = LoggerFactory.getLogger(SheetConvertor.class);
	
	/**
	 * Get or create a cell in a row, if it is not found
	 * @param row
	 * @param cellReference
	 * @return
	 */
	static Cell getOrCreateCell(Row row, CellReference cellReference) {

		log.debug("Looking for:" + cellReference);
		Cell cell = row.getCell(cellReference.getCol());

		if(cell==null) {
			cell = row.createCell(cellReference.getCol());
		}
		
		//log.debug("found:" + cell.getStringCellValue());

		return cell;
	}

	/**
	 * Get or create a row in a sheet, if it is not found,
	 * 
	 * @param thisSheet
	 * @param cellReference
	 * @return
	 */
	static Row getOrCreateRow(Sheet thisSheet, CellReference cellReference) {

		log.debug("Looking for:" + cellReference);

		Row row = thisSheet.getRow(cellReference.getRow());

		if (row == null) {
			row = thisSheet.createRow(cellReference.getRow());
		}

		log.debug("found:" + row.getRowNum());

		return row;

	}

	/**
	 * Get or create a sheet in a workbook, if is not found.
	 * 
	 * @param wb
	 * @param thisRedCell
	 * @return
	 */
	static Sheet getOrCreateSheet(Workbook wb, net.firstpartners.data.Cell thisRedCell) {

		log.debug("trying to find sheet:" + thisRedCell.getOriginalTableReference());

		Sheet thisSheet = wb.getSheet(thisRedCell.getOriginalTableReference());

		if (thisSheet == null) {
			thisSheet = wb.createSheet(thisRedCell.getOriginalTableReference());
		}

		log.debug("Found:" + thisSheet.getSheetName());
		return thisSheet;
	}

}
