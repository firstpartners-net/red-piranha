package net.firstpartners.core.excel;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.firstpartners.core.script.PreProcessor;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeList;

/**
 * Read Ranges from Excel, Convert to a format (JavaBeans) that rules find
 * easier to use.
 *
 * Based on Sample from Apache POI
 *
 * @author paulbrowne
 * @version $Id: $Id
 */
public class SpreadSheetConvertor {

	// Logging
	private static final Logger log = LoggerFactory.getLogger(SpreadSheetConvertor.class);

	// Handle to the preprocessor
	static PreProcessor preProcess = null;

	/**
	 * Update an excel file with our new values uses the original sheet and original
	 * cell reference within the red cell to copy backk
	 *
	 * @param wb            a {@link org.apache.poi.ss.usermodel.Workbook} object
	 * @param updatedValues a {@link net.firstpartners.data.RangeList} object
	 * @throws java.io.IOException
	 */
	public static void updateRedRangeintoPoiExcel(Workbook wb, RangeList updatedValues) throws IOException {

		// Get all the Cells that we have been keeping track of
		Map<String, net.firstpartners.data.Cell> allCells = updatedValues.getAllCellsWithNames();

		// Loop through the cells
		Iterator<net.firstpartners.data.Cell> redCells = allCells.values().iterator();
		while (redCells.hasNext()) {

			net.firstpartners.data.Cell thisRedCell = redCells.next();
			String orignalSheetRef = thisRedCell.getOriginalTableReference();
			String originalPoiRef = thisRedCell.getOriginalCellReference();

			if (originalPoiRef == null || orignalSheetRef == null) {
				log.debug("Cells has no ref to original sheet or cell - ignoring:" + thisRedCell);
			} else {

				// Get a handle to the Excel cell at Sheet / reference
				org.apache.poi.ss.usermodel.Sheet thisSheet = SheetConvertor.getOrCreateSheet(wb, thisRedCell);

				CellReference cellReference = new CellReference(thisRedCell.getOriginalCellRow(),
						thisRedCell.getOriginalCellRow());

				Row row = SheetConvertor.getOrCreateRow(thisSheet, cellReference);

				org.apache.poi.ss.usermodel.Cell excelCell = SheetConvertor.getOrCreateCell(row, cellReference);

				// update the values into the cell
				CellConvertor.convertRedCellToPoiCell(wb, excelCell, thisRedCell);

			}

		}

	}

	/**
	 * Read an excel file and return what we find as a set of simple JavaBeans
	 *
	 * @param wb - Apache Poi workbook to convert
	 * @return RangeList - a list of Red Ranges (RP Formal) contain one or more Red
	 *         Cells (RP Format)
	 * @throws java.io.IOException
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public static RangeList convertNamesFromPoiWorkbookIntoRedRange(org.apache.poi.ss.usermodel.Workbook poiWorkbook)
			throws IOException, ResourceException, ScriptException {

		// Note in this code we prefix variables as follows
		// poiXXX objects using the Apache POI Structure
		// redXXX objects using our our (Red Piranha) JavaBeans

		// check incoming values
		assert poiWorkbook != null : "Incoming poi workbook should not be null";

		// hold all the named ranges from our sheet
		RangeList redReturnList = new RangeList();

		// retrieve the named range
		List<? extends Name> poiNamedRanges = poiWorkbook.getAllNames();
		if (poiNamedRanges == null) {

			log.info("No Named Ranges in poi workbook- returning empty list");
			return redReturnList;

		}

		// Setup loop through named ranges
		org.apache.poi.ss.usermodel.Name thisPoiNamedRange = null;
		ListIterator<? extends Name> namedRangeList = poiNamedRanges.listIterator();

		// start our loop through the named ranges
		while (namedRangeList.hasNext()) {

			// Get the next named range
			thisPoiNamedRange = namedRangeList.next();
			log.debug("Converting poi->Red named range:" + thisPoiNamedRange.getNameName() + " refers to:"
					+ thisPoiNamedRange.getRefersToFormula());

			// The poiCell refs we will convert to Red Cells
			CellReference[] crefs = new CellReference[0];

			// check for non-contiguous aresa
			if (!AreaReference.isContiguous(thisPoiNamedRange.getRefersToFormula())) {

				// Get area refnereces
				AreaReference[] aref = AreaReference.generateContiguous(SpreadsheetVersion.EXCEL2007,
						thisPoiNamedRange.getRefersToFormula());

				// Convert all of these to a single array of cells
				for (int i = 1; i < aref.length; i++) {

					// add the poi cells from this part of the referenced area to our poi cell lsit
					crefs = ArrayUtils.addAll(crefs, aref[i].getAllReferencedCells());

				}

				log.debug("Will process:" + crefs.length + " referenced cells (non contig)");

			} else {

				// get the singel set of Cells[] that are behind this poi Name
				AreaReference aref = new AreaReference(thisPoiNamedRange.getRefersToFormula(),
						SpreadsheetVersion.EXCEL2007);
				crefs = aref.getAllReferencedCells();
				log.debug("Will process:" + crefs.length + " referenced cells (contig)");
			}

			// only proceed if we can get an actual handle to this
			// retrieve the cells at the named range

			// A Red Range that we will put the new cells *for this name* into
			net.firstpartners.data.Range redRangeForThisName = new Range(thisPoiNamedRange.getNameName());

			// setup loop
			Sheet currentPoiSheet;
			Row currentPoiRow;
			org.apache.poi.ss.usermodel.Cell currentPoiCell;
			String redCellHandle;

			// loop through all cells behind this name
			for (int thisCellCounter = 0; thisCellCounter < crefs.length; thisCellCounter++) {

				// Get a handle the to current cell in the range
				currentPoiSheet = poiWorkbook.getSheet(crefs[thisCellCounter].getSheetName());
				currentPoiRow = currentPoiSheet.getRow(crefs[thisCellCounter].getRow());

				if (currentPoiRow == null) {
					log.error("POI Row is null - unable to convert:" + crefs[thisCellCounter]);
				} else {

					currentPoiCell = currentPoiRow.getCell(crefs[thisCellCounter].getCol());

					// extract the cell contents based on cell type etc. and create our (red)Cell /
					// Javabean
					redCellHandle = redRangeForThisName.getUniqueCellName(thisCellCounter);
					assert redCellHandle != null;

					net.firstpartners.data.Cell redCell = CellConvertor.convertPoiCellToRedCell(redCellHandle,
							currentPoiCell);

					log.debug("Converted to Red Cell:" + redCell);

					// Add the list of cells to a RedRange Group
					redRangeForThisName.put(redCellHandle, redCell);
				}

			}

			// save to the full list tthat weill be returned later
			redReturnList.add(redRangeForThisName);

		}

		return redReturnList;

		// ## move this into a tighter catch loop
		// } catch (IllegalArgumentException iae) {

		// // It is possible that a named range exists in excel but, the actual cell as
		// // been deleted
		// log.debug("Ignoring invalid Excel range ref:", iae);
		// }

	}


}
