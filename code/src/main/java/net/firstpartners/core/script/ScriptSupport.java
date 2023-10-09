package net.firstpartners.core.script;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
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
import net.firstpartners.core.excel.CellConvertor;

/**
 * Utilities for supporting Groovy Scripts in injecting names into the sheet
 * Everything that is done is in this class could be done in the Groovy Scripts
 * But having it packaged in a utility makes them easier to use.
 *
 * @author PBrowne
 * @version $Id: $Id
 */
public class ScriptSupport {

	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// Handle to the workbook we will be operating on
	Workbook wb = null;

	// reusable cell and date formatter
	DataFormatter df = new DataFormatter();

	// flag have we already removed spaces
	private boolean spacesRemoved = false;

	/**
	 * Constructor
	 * 
	 * @param xlWorkbook that we
	 */
	public ScriptSupport(Workbook xlWorkbook) {

		// update to remove spaces
		this.wb = xlWorkbook;

	}

	/**
	 * Internal Method - remove spaces from sheet names
	 * Marked final - as called by constructor
	 * 
	 * @param wb        - workbook to work on
	 * @param sheetName to find
	 * @return
	 */
	public void removeSpacesSheetNames() {

		// check if we've already done this
		if (this.spacesRemoved) {
			return;
		}

		for (int i = 0; i < this.wb.getNumberOfSheets(); i++) {
			Sheet sheet = this.wb.getSheetAt(i);
			String tmpSheetName = sheet.getSheetName();

			// remove spaces from sheet name
			tmpSheetName = tmpSheetName.replaceAll("\\s", "");

			// rename sheet to match this
			this.wb.setSheetName(i, tmpSheetName);
			log.debug("Updated sheet name:" + tmpSheetName);
		}

		this.spacesRemoved = true;

	}

	/**
	 * Loop through a table in Excel, naming the rangess individually within thems.
	 * Calls the overloaded method and will
	 * use the first row and first (left hand) column as names
	 * 
	 * @param baseName     - foundation we will generate names from e.g. a baseName
	 *                     of "Cash" might generate "Cash-In-FY22"
	 * @param sheetName    - withing the workbook, where we want to set the workbook
	 * @param mainTableRef - the full table, in Excel notation *including* the
	 *                     Header and column rows
	 * @throwpreprocessXlWorkbooks IOException
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public void nameTable(String baseName, String sheetName, String mainTableRef) {

		// method level constants
		int numberOfHeaderRows = 1;
		int numberOfColumnLabels = 1;

		// ensure our sheets have no spaces in them
		removeSpacesSheetNames();

		// get handle to block of cells at maintable ref
		sheetName = sheetName.replaceAll("\\s", ""); // we ignore spaces
		String formula = sheetName + "!" + mainTableRef; // should give us same as in Excel e.g. Accounts!B14:I14

		log.debug("Looking for Table Formula:" + formula);
		AreaReference aref = new AreaReference(formula, SpreadsheetVersion.EXCEL2007);

		// Setup our loop
		Sheet s = getSafeSheet(sheetName);
		CellReference[] crefs = aref.getAllReferencedCells();
		String currentCellText = "";
		Row poiRow = null;
		Cell poiCell = null;

		// Hashmaps Column and header names - since our table might start at col 3, cell
		// 3
		// we need to hold the col name as "3" rather than relying on the 0 based index
		// of an array
		HashMap<String, String> headerNames = new HashMap<String, String>();
		HashMap<String, String> colNames = new HashMap<String, String>();

		// set first row based on first cell (assume it is at top left of block)
		int firstRow = crefs[0].getRow();
		int firstCol = crefs[0].getCol();
		for (int i = 0; i < crefs.length; i++) {

			// reset header flags
			boolean rowLabel = false;
			boolean colLabel = false;

			// Get Cell from Spreadhseet
			assert s != null : "sheet should not be null";
			poiRow = s.getRow(crefs[i].getRow());
			if(poiRow!=null){
				poiCell = poiRow.getCell(crefs[i].getCol());
			}
			
			// Get the current cells value use our main convertor
			// for this use case, we just want it as a string.
			currentCellText = CellConvertor.getCellAsStringForceDateConversion(poiCell);
			// log.debug("CurrentCellText:"+currentCellText+"
			// length"+currentCellText.length());

			// Make a note of how many cells / rows deep in the table we are
			int row = crefs[i].getRow();
			int col = crefs[i].getCol();

			// check for header row
			if (row < firstRow + numberOfHeaderRows) {
				rowLabel = true;
			} else {
				rowLabel = false;
			}

			// check if this contains col lables
			if (col < firstCol + numberOfColumnLabels) {
				colLabel = true;
			} else {
				colLabel = false;
			}

			// decide how we handle this cell

			if (rowLabel && colLabel) {
				// we are in top left corner 'dead space'
				log.debug(" Row:" + row + " Col:" + col + " " + Arrays.toString(crefs[i].getCellRefParts())
						+ " dead space" + currentCellText);
				// ignore
			}

			if (!colLabel && rowLabel) {
				// we are in top row(s)
				log.debug(" Row:" + row + " Col:" + col + " " + Arrays.toString(crefs[i].getCellRefParts())
						+ " row header:" + currentCellText);

				// fill in for blank names
				if (currentCellText.length() == 0) {
					currentCellText = "Col" + row + 1;
					// log.debug("Updated blank row");
				}

				headerNames.put("" + col, currentCellText);

			}

			if (colLabel && !rowLabel) {
				// we are in col labels(s)
				log.debug(" Row:" + row + " Col:" + col + " " + Arrays.toString(crefs[i].getCellRefParts())
						+ " col labels:" + currentCellText);

				// fill in for blank names
				if (currentCellText.length() == 0) {
					currentCellText = "Row" + row + 1;
					// log.debug("Updated blank col");
				}

				colNames.put("" + row, currentCellText);
			}

			if (!colLabel && !rowLabel) {
				// we are in main body of table labels(s)
				// log.debug(" Row:" + row + " Col:" + col + " " +
				// Arrays.toString(crefs[i].getCellRefParts())
				// + " table body value:" + currentCellText);

				// Calculate namedRangeName based on header and col
				// remember it is a table, so the col names will differ by row (and vice versa)
				String refName = baseName + "_" + colNames.get("" + row) + "_" + headerNames.get("" + col);

				// tidy to remove spaces etc as Excel won't allow them in named range
				refName = refName.replaceAll("y/e", "ye");
				refName = refName.replaceAll("/", "_");
				refName = refName.replaceAll("-", "_minus_");
				refName = refName.replaceAll("\\+", "_plus_");
				refName = refName.replaceAll("[^A-Za-z0-9_.]", "");

				// name this cell
				// log.debug("Would name cell:" + refName + " sheetname:" + sheetName + "
				// excelref:"
				// + crefs[i].formatAsString());
				nameSingleCell(refName, sheetName, crefs[i].formatAsString(false));
			}

		}

		// log.debug("HeaderNames size:"+headerNames.size()+" values
		// "+Arrays.toString(headerNames.entrySet().toArray()));
		// log.debug("ColNames size:"+colNames.size()+" values
		// "+Arrays.toString(colNames.entrySet().toArray()));

	}

	/**
	 * Loop through a table in Excel, naming the rangess individually within tehm
	 *
	 * @param sheetName          - withing the workbook, where we want to set the
	 *                           workbook
	 * @param baseName           - in this case will be the full name used
	 * @param mainTableRef       - the full table, in Excel notation *including* the
	 *                           Header and column rows
	 * @param numberOfHeaderRows - can be 0 (if none), 1 (e.g. years) or 2 or more
	 *                           (in which we will collate teh date)
	 * @param int                numberOfColumnRows
	 */
	public void nameSingleCell(String baseName, String sheetName, String cellRef) {

		// ensure our sheets have no spaces in them, nor in teh incoming sheet name
		removeSpacesSheetNames();
		sheetName = sheetName.replaceAll("\\s", "");

		String formula = sheetName + "!" + cellRef; // should give us same as in Excel e.g. Accounts!B14:I14

		// check if the name already exists
		Name existingName = wb.getName(baseName);
		if (existingName != null) {
			log.info("Ignoring existing name:" + baseName + " refers to:" + existingName.getRefersToFormula()
					+ " duplicate new ref:" + cellRef);
		} else {

			// try to add it
			Name newXlNamedRange = wb.createName();
			newXlNamedRange.setNameName(baseName);
			newXlNamedRange.setRefersToFormula(formula);
			log.debug("Added Name:" + baseName + " formula:" + formula);

		}

	}


	/**
	 * Update a value in a cell in workbook in memory (not original sheet)
	 * This helps resolve naming clashes later
	 * 
	 * @param newValue  - that we will update the cell to
	 * @param sheetName - name of the sheet we wish to update
	 * @param cellRef   - in format B17 of cell we wish to update
	 */
	public void setText(String newValue, String sheetName, String cellRef) {

		sheetName = sheetName.replaceAll("\\s", ""); // we ignore spaces

		CellReference cr = new CellReference(sheetName + "!" + cellRef);

		log.debug("attempting to setText on:" + sheetName + "!" + cellRef);

		Sheet s = getSafeSheet(sheetName);
		Row row = s.getRow(cr.getRow());
		Cell cell = row.getCell(cr.getCol());

		cell.setCellValue(newValue);

	}

	/**
	 * Get a value from the workbook (as string)
	 * 
	 * @param sheetName
	 * @param cellRef   within this sheet
	 * @return value as text of this cell
	 */
	public String get(String sheetName, String cellRef) {

		CellReference cr = new CellReference(sheetName + "!" + cellRef);
		Sheet s = getSafeSheet(sheetName);

		Row row = s.getRow(cr.getRow());
		Cell cell = row.getCell(cr.getCol());

		return CellConvertor.getCellAsStringForceDateConversion(cell);
	}

	/**
	 * Remove all previous named ranges in workbook
	 * This updates Memory only and not original source file
	 */
	public void removePreviousNamedRanges() {

		// Loop over names and remove each one
		// we do it in this loop fashion to give POI a chance to update
		while (wb.getAllNames().size() > 0) {
			Name thisName = wb.getAllNames().get(0);
			// log.debug("Removing:"+thisName.getNameName());
			wb.removeName(thisName);

		}

	}

	/**
	 * Get sheet in a safe manner
	 * 
	 * @param wb2
	 * @param sheetName
	 * @return
	 */
	private Sheet getSafeSheet(String sheetName) {

		Sheet s = this.wb.getSheet(sheetName);

		if (s == null) {
			sheetName = sheetName.replaceAll("[^A-Za-z0-9_.]", "");

			log.debug("No match on:"+sheetName+" trying:"+sheetName);
			
			s = this.wb.getSheet(sheetName);
		}

		// if it's still null
		if (s == null) {

			//Log the info
			for (int i = 0; i < this.wb.getNumberOfSheets(); i++) {
				Sheet sheet = this.wb.getSheetAt(i);
				String tmpSheetName = sheet.getSheetName();
				log.debug("Noting no match - existing sheet name:" + tmpSheetName);
			}
		}

		return s;
	}

}
