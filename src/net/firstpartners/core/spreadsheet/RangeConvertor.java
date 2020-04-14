package net.firstpartners.core.spreadsheet;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;

import net.firstpartners.core.log.RpLogger;

/**
 * Read Ranges from Excel, Convert to a format that rules find easier use
 *
 * Based on Sample from Apache POI
 *
 * @author paulbrowne
 *
 */
public class RangeConvertor {

	private static final Logger log = RpLogger.getLogger(RangeConvertor.class.getName());

	/**
	 * Read an excel file and spit out what we find.
	 *
	 * @param args Expect one argument that is the file to read.
	 * @throws IOException When there is an error processing the file.
	 */
	public static RangeHolder convertPoiWorkbookIntoRedRange(org.apache.poi.ss.usermodel.Workbook wb) throws IOException {

		RangeHolder returnValues = new RangeHolder();

		// retrieve the named range
		int numberOfNames = wb.getNumberOfNames();

		// Get all the named ranges in our spreadsheet
		for (int namedRangeIdx = 0; namedRangeIdx < numberOfNames; namedRangeIdx++) {
			org.apache.poi.ss.usermodel.Name aNamedRange = wb.getNameAt(namedRangeIdx);

			// retrieve the cell at the named range and test its contents

			AreaReference aref = new AreaReference(aNamedRange.getRefersToFormula()); //was getReference
			org.apache.poi.ss.util.CellReference[] crefs = aref.getAllReferencedCells();

			// A Range that we will put the new cells into
			Range redRange = new Range(aNamedRange.getNameName());

			for (int thisCellinRange = 0; thisCellinRange < crefs.length; thisCellinRange++) {
				Sheet sheet = wb.getSheet(crefs[thisCellinRange].getSheetName());
				Row r = sheet.getRow(crefs[thisCellinRange].getRow());

				org.apache.poi.ss.usermodel.Cell thisExcelCell = null;
				if (r != null) {
					thisExcelCell = r.getCell(crefs[thisCellinRange].getCol());
					// extract the cell contents based on cell type etc.
				}

				// Create our JavaBean representing the cell
				String cellHandle = redRange.getUniqueCellName(thisCellinRange);
				net.firstpartners.core.spreadsheet.Cell redCell = CellConvertor.convertPoiCellToRedCell(cellHandle,
						thisExcelCell);

				// Give the cell information about who is holding it
				// and that it should pass on property change events to it
				redCell.setHoldingRange(redRange);
				redCell.addPropertyChangeListener(redRange);

				// Add the list of cells to a range
				redRange.put(cellHandle, redCell);

			}

			returnValues.add(redRange);
		}

		return returnValues;

	}

	/**
	 * Update an excel file with our new values
	 *
	 */
	public static void updateRedRangeintoPoiExcel(Workbook wb, RangeHolder updatedValues) throws IOException {

		// retrieve the named range
		int numberOfNames = wb.getNumberOfNames();

		// Get all names of *all* the cells in *all* the ranges
		Map<String, net.firstpartners.core.spreadsheet.Cell> allCells = updatedValues.getAllCells();

		for (int namedCellIdx = 0; namedCellIdx < numberOfNames; namedCellIdx++) {
			org.apache.poi.ss.usermodel.Name aNamedCell = wb.getNameAt(namedCellIdx);

			// retrieve the cell at the named range and test its contents
			AreaReference aref = new AreaReference(aNamedCell.getRefersToFormula()); //was getReference
			CellReference[] crefs = aref.getAllReferencedCells();

			for (int thisCellinRange = 0; thisCellinRange < crefs.length; thisCellinRange++) {
				org.apache.poi.ss.usermodel.Sheet sheet = wb.getSheet(crefs[thisCellinRange].getSheetName());

				org.apache.poi.ss.usermodel.Row r = sheet.getRow(crefs[thisCellinRange].getRow());

				// Get the cell that is referred to
				org.apache.poi.ss.usermodel.Cell excelCell = null;
				if (r != null) {
					excelCell = r.getCell(crefs[thisCellinRange].getCol());

					// Check that the range name is on our list
					String cellHandle = Range.getUniqueCellName(aNamedCell.getNameName(), thisCellinRange);

					if (allCells.containsKey(cellHandle)) {
						CellConvertor.convertRedCellToPoiCell(wb, excelCell, allCells.get(cellHandle));

					} else {
						log.finest("Name not found in facts:" + cellHandle);
					}

				} else {
					log.info("Null");
				}

			}
		}

	}

}
