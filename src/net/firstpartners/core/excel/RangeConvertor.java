package net.firstpartners.core.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;

import net.firstpartners.core.log.RpLogger;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeList;

/**
 * Read Ranges from Excel, Convert to a format (JavaBeans) that rules find
 * easier to use.
 *
 * Based on Sample from Apache POI
 *
 * @author paulbrowne
 *
 */
public class RangeConvertor {

	// Logging
	private static final Logger log = RpLogger.getLogger(RangeConvertor.class.getName());

	/**
	 * Read an excel file and return what we find as a set of simple JavaBeans
	 * 
	 * @param wb - Apache Poi workbook to convert
	 * @return RangeHolder - can be empty if no names are definded
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static RangeList convertNamesFromPoiWorkbookIntoRedRange(org.apache.poi.ss.usermodel.Workbook wb)
			throws IOException {

		// hold all the named ranges from our sheet
		RangeList returnValues = new RangeList();

		// retrieve the named range - Iterator not available
		List<? extends Name> namedRanges = wb.getAllNames();
		if (namedRanges == null) {
			log.info("No Named Ranges in workbook- skipping");
			return returnValues;
		}

		// Setup loop through named ranges
		int namedRangeIdx = -1;
		namedRangeIdx++;

		org.apache.poi.ss.usermodel.Name aNamedRange = null;
		ListIterator<? extends Name> loopList = namedRanges.listIterator();

		while (loopList.hasNext()) {
			namedRangeIdx++;

			aNamedRange = loopList.next(); // wb.getNameAt(namedRangeIdx);

			// retrieve the cells at the named range
			log.info("Processing named range:" + aNamedRange.getNameName());

			// Reset to empty array - so we're covered even if an exception is through
			AreaReference aRef[] = new AreaReference[0];

			try {
				aRef = AreaReference.generateContiguous(wb.getSpreadsheetVersion(), aNamedRange.getRefersToFormula());
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
			Row r = null;

			while (loop.hasNext()) {

				CellReference thisCellRef = loop.next();
				thisCellinRange++;

				Sheet sheet = wb.getSheet(thisCellRef.getSheetName());
				try {
					r = sheet.getRow(thisCellRef.getRow()); // ISSUE HERE
				} catch (NullPointerException npe) {
					log.error("Excel Read error on Cell:" + thisCellRef, npe);

				}

				org.apache.poi.ss.usermodel.Cell thisExcelCell = null;

				if (r != null) {

					// extract the cell contents based on cell type etc.
					thisExcelCell = r.getCell(thisCellRef.getCol());

				}

				// Create our JavaBean representing the cell
				String cellHandle = redRange.getUniqueCellName(thisCellinRange);
				assert cellHandle != null;

				net.firstpartners.data.Cell redCell = CellConvertor.convertPoiCellToRedCell(cellHandle, thisExcelCell);

				// Give the cell information about who is holding it
				// and that it should pass on property change events to it
				redCell.setHoldingRange(redRange);
				redCell.addPropertyChangeListener(redRange);

				// log.info("Converted Cell:" + redCell);
				log.info("Converted Number:" + thisCellinRange + " of " + cellArray.size());

				// Add the list of cells to a range
				redRange.put(cellHandle, redCell);

			}

			returnValues.add(redRange);
		}

		return returnValues;

	}

	/**
	 * Update an excel file with our new values uses the original sheet and original
	 * cell reference within the red cell to copy backk
	 * 
	 * @param wb
	 * @param updatedValues
	 * @throws IOException
	 */
	public static void updateRedRangeintoPoiExcel(Workbook wb, RangeList updatedValues) throws IOException {

		// Get all the Cells that we have been keeping track of
		Map<String, net.firstpartners.data.Cell> allCells = updatedValues.getAllCells();

		// Loop through the cells
		Iterator<net.firstpartners.data.Cell> redCells = allCells.values().iterator();
		while (redCells.hasNext()) {

			net.firstpartners.data.Cell thisRedCell = redCells.next();
			String orignalSheetRef = thisRedCell.getOriginalSheetReference();
			String originalPoiRef = thisRedCell.getOriginalCellReference();

			if (originalPoiRef == null || orignalSheetRef == null) {
				log.info("Cells has no ref to original sheet or cell - ignoring:" + thisRedCell);
			} else {

				// Get a handle to the Excel cell at Sheet / reference
				org.apache.poi.ss.usermodel.Sheet thisSheet = SheetConvertor.getOrCreateSheet(wb, thisRedCell);

				CellReference cellReference = new CellReference(thisRedCell.getOriginalCellReference());
				Row row = SheetConvertor.getOrCreateRow(thisSheet, cellReference);

				org.apache.poi.ss.usermodel.Cell excelCell = SheetConvertor.getOrCreateCell(row, cellReference);

				// update the values into the cell
				CellConvertor.convertRedCellToPoiCell(wb, excelCell, thisRedCell);

			}

		}

	}

}
