package net.firstpartners.core.spreadsheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	 * Read an excel file and return what we find as a set of simple JavaBeans
	 * 
	 * @param wb - Apache Poi workbook to convert
	 * @return
	 * @throws IOException
	 */
	public static RangeHolder convertPoiWorkbookIntoRedRange(org.apache.poi.ss.usermodel.Workbook wb)
			throws IOException {

		RangeHolder returnValues = new RangeHolder();

		// retrieve the named range - Iterator not available
		int numberOfNames = wb.getNumberOfNames();

		// Get all the named ranges in our spreadsheet
		for (int namedRangeIdx = 0; namedRangeIdx < numberOfNames; namedRangeIdx++) {
			org.apache.poi.ss.usermodel.Name aNamedRange = wb.getNameAt(namedRangeIdx);

			// retrieve the cells at the named range
			log.log(Level.INFO, "Processing named range:" + aNamedRange.getNameName());

			AreaReference aRef[] = new AreaReference[0];

			try {
				aRef = AreaReference.generateContiguous(aNamedRange.getRefersToFormula());
			} catch (IllegalArgumentException iae) {

				// It is possible that a named range exists in excel but, the actual cell as
				// been deleted
				log.info("Ignoring invalid range ref:" + iae);
			}

			ArrayList<CellReference> cellArray = new ArrayList<CellReference>();
			for (int a = 0; a < aRef.length; a++) {

				Collections.addAll(cellArray, aRef[a].getAllReferencedCells());

			}

			// A Range that we will put the new cells into
			Range redRange = new Range(aNamedRange.getNameName());

			// Iterator to loop over POI Cells
			Iterator<CellReference> loop = cellArray.iterator();
			int thisCellinRange = 0;
			Row r =null;
			
			
			while (loop.hasNext()) {

				CellReference thisCellRef = loop.next();
				thisCellinRange++;

				Sheet sheet = wb.getSheet(thisCellRef.getSheetName());
				try {
					r = sheet.getRow(thisCellRef.getRow());
				} catch (NullPointerException npe) {
					System.out.println(npe);
					throw (npe);

				}

				org.apache.poi.ss.usermodel.Cell thisExcelCell = null;
				if (r != null) {

					// extract the cell contents based on cell type etc.
					thisExcelCell = r.getCell(thisCellRef.getCol());

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
			AreaReference aref = new AreaReference(aNamedCell.getRefersToFormula()); // was getReference
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
