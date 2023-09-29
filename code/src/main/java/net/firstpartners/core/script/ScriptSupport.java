package net.firstpartners.core.script;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.firstpartners.core.excel.CellConvertor;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;

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
	private Workbook wb = null;

	// reusable cell and date formatter
	DataFormatter df = new DataFormatter();

	/**
	 * Constructor
	 * 
	 * @param xlWorkbook that we
	 */
	public ScriptSupport(Workbook xlWorkbook) {
		this.wb = xlWorkbook;
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

		nameTable(baseName, sheetName, mainTableRef, 1, 1);

	}

	/**
	 * Loop through a table in Excel, naming the ranges individually within them
	 * Currently marked "Private" as only implemented / tested to be called with one
	 * header row / col
	 * 
	 * @param baseName           - foundation we will generate names from e.g. a
	 *                           baseName of "Cash" might generate "Cash-In-FY22"
	 * @param sheetName          - withing the workbook, where we want to set the
	 *                           workbook
	 * @param mainTableRef       - the full table, in Excel notation *including* the
	 *                           Header and column rows
	 * @param numberOfHeaderRows - can be 0 (if none), 1 (e.g. years) or 2 or more
	 *                           (in which we will collate teh date)
	 * @param int                numberOfColumnRows
	 */
	private void nameTable(String baseName, String sheetName, String mainTableRef, int numberOfHeaderRows,
			int numberOfColumnLabels) {

		// get handle to block of cells at maintable ref
		String formula = sheetName + "!" + mainTableRef; // should give us same as in Excel e.g. Accounts!B14:I14
		log.debug("Looking for Table Formula:" + formula);
		AreaReference aref = new AreaReference(formula, SpreadsheetVersion.EXCEL2007);

		// Setup our loop
		Sheet s = wb.getSheet(sheetName);
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
			poiRow = s.getRow(crefs[i].getRow());
			poiCell = poiRow.getCell(crefs[i].getCol());

			// Get the current cells value use our main convertor
			// for this use case, we just want it as a string.
			currentCellText = getCellAsStringForceDateConversion(poiCell);
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
					currentCellText = "Col" + col + 1;
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
					currentCellText = "Row" + col + 1;
					// log.debug("Updated blank col");
				}

				colNames.put("" + row, currentCellText);
			}

			if (!colLabel && !rowLabel) {
				// we are in main body of table labels(s)
				log.debug(" Row:" + row + " Col:" + col + " " + Arrays.toString(crefs[i].getCellRefParts())
						+ " table body value:" + currentCellText);

				// Calculate namedRangeName based on header and col
				// remember it is a table, so the col names will differ by row (and vice versa)
				String refName = baseName + "_" + colNames.get("" + row) + "_" + headerNames.get("" + col);

				// tidy to remove spaces etc as Excel won't allow them in named range
				refName = refName.replaceAll("/", "_");
				refName = refName.replaceAll("-", "_minus_");
				refName = refName.replaceAll("\\+", "_plus_");
				refName = refName.replaceAll("[^A-Za-z0-9_.]", "");

				// name this cell
				log.debug("Would name cell:" + refName + " sheetname:" + sheetName + " excelref:"
						+ crefs[i].formatAsR1C1String());
				nameSingleCell(refName, sheetName, crefs[i].formatAsR1C1String(false));

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

		String formula = sheetName + "!" + cellRef; // should give us same as in Excel e.g. Accounts!B14:I14

		try {
			Name newXlNamedRange = wb.createName();
			newXlNamedRange.setNameName(baseName);
			newXlNamedRange.setRefersToFormula(formula);
			log.debug("Added Name:" + baseName + " formula:" + formula);
		} catch (IllegalArgumentException iae) {
			log.info("Ignoring Duplicate or invalid Name:" + baseName + " formula:" + formula);

		}

	}

	/**
	 * Convert Cell to the String
	 * for this use case, we just want it as a string.
	 * e.g. for headers like "20/12/21" we want it as that value, not string
	 * Note that numbers will often be treated as dates - since these are more
	 * likely to be headers
	 * 
	 * @param poiCell
	 * @return
	 */
	public String getCellAsStringForceDateConversion(Cell poiCell) {

		// Convert our cell differently depending if is a date or note
		// log.debug("Cell Type:"+(poiCell.getCellType()));

		String simpleConversion = "" + CellConvertor.getCellContents(poiCell);
		// log.debug("Simple Conversion:"+simpleConversion);

		CellType type = poiCell.getCellType();

		if (type == CellType.NUMERIC
				|| (poiCell.getCellType() == CellType.FORMULA) && (DateUtil.isCellDateFormatted(poiCell))) {

			// convert using null checks
			if(simpleConversion!=null){
				Date javaDate = DateUtil.getJavaDate(Double.parseDouble(simpleConversion));
				if(javaDate!=null){
					return new SimpleDateFormat("dd/MM/yy").format(javaDate);
				}
			}

		}

		// return default conversion if we get this far
		return simpleConversion;

		//

	}
}
