package net.firstpartners.core.script;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.util.ResourceException;
import groovy.util.ScriptException;

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
	private Workbook wb =null;

	/**
	 * Constructor
	 * @param xlWorkbook that we 
	 */
	public ScriptSupport(Workbook xlWorkbook){
		this.wb = xlWorkbook;
	}


	/**
	 * Loop through a table in Excel, naming the rangess individually within thems. Calls the overloaded method and will 
	 * use the first row and first (left hand) column as names
	 * @param baseName - foundation we will generate names from e.g. a baseName of "Cash" might generate "Cash-In-FY22"
	 * @param sheetName - withing the workbook, where we want to set the workbook
	 * @param mainTableRef - the full table, in Excel notation *including* the Header and column rows
	 * @throwpreprocessXlWorkbooks IOException
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public void nameTable (String baseName, String sheetName, String mainTableRef)  {

		nameTable(baseName,sheetName,mainTableRef,1,1);
		
	}


	/**
	 * Loop through a table in Excel, naming the ranges individually within them
	 * Currently marked "Private" as only implemented / tested to be called with one header row / col
	 * @param baseName - foundation we will generate names from e.g. a baseName of "Cash" might generate "Cash-In-FY22"
	 * @param sheetName - withing the workbook, where we want to set the workbook
	 * @param mainTableRef - the full table, in Excel notation *including* the Header and column rows
	 * @param numberOfHeaderRows - can be 0 (if none), 1 (e.g. years) or 2 or more (in which we will collate teh date)
	 * @param int numberOfColumnRows 
	*/
	private void nameTable (String baseName, String sheetName, String mainTableRef ,int numberOfHeaderRows, int numberOfColumnLabels )  {

		
		//get handle to block of cells at maintable ref
		String formula = sheetName+"!"+mainTableRef; 	// should give us same as in Excel e.g. Accounts!B14:I14
		log.debug("Looking for Table Formula:"+formula);
		AreaReference aref = new AreaReference(formula,SpreadsheetVersion.EXCEL2007);

		//Setup our loop
		Sheet s = wb.getSheet(sheetName);
		
		CellReference[] crefs = aref.getAllReferencedCells();

		//set first row based on first cell (assume it is at top left of block)
		int firstRow = crefs[0].getRow();
		int firstCol = crefs[0].getCol();

		//Column and header names
		ArrayList<String> headerNames = new ArrayList<String>();
		ArrayList<String> ColNames = new ArrayList<String>();

		// setup loop through cells
		boolean rowLabel=false;
		boolean colLabel=false;
		String currentCellText="";

		for (int i=0; i<crefs.length; i++) {

			//Get the current cells value
			currentCellText = ### add in string value;

			//Row r = s.getRow(crefs[i].getRow());
			//Cell c = r.getCell(crefs[i].getCol());
			int row = crefs[i].getRow();
			int col = crefs[i].getCol();

			// check for header row
			if(row< firstRow+numberOfHeaderRows){
				rowLabel=true;
			} else {
				rowLabel=false;
			}
		
			// check if this contains col lables
			if(col<firstCol+ numberOfColumnLabels){
				colLabel = true;
			} else {
				colLabel =false;
			}

			//decide how we handle this cell

			if(rowLabel&&colLabel){
				// we are in top left corner 'dead space'
				log.debug(" Row:"+row+" Col:"+col+" "+Arrays.toString(crefs[i].getCellRefParts())+" dead space");
				// ignore
			} 

			if(!colLabel&&rowLabel){
				// we are in top row(s)
				log.debug(" Row:"+row+" Col:"+col+" "+Arrays.toString(crefs[i].getCellRefParts())+" row header");
				### add to row header list;
			}

			if(colLabel&&!rowLabel){
				// we are in col labels(s)
				log.debug(" Row:"+row+" Col:"+col+" "+Arrays.toString(crefs[i].getCellRefParts())+" col labels");
				## add to col lables list;
			}

			if(!colLabel&&!rowLabel){
				// we are in main body of table labels(s)
				log.debug(" Row:"+row+" Col:"+col+" "+Arrays.toString(crefs[i].getCellRefParts())+" table body values");

				//Calculate namedRangeName based on header and col
				String refName = ### ;
					### default to row number if either of these not available;

				//calc the cell ref to pass in 
				String cellRef = ####;

				// name this cell	
				nameSingleCell (refName, sheetName, cellRef);
				

			}



		}


		//loop through rows

			// check for header

			// loop through cols

				// check for name row

				//add anme to spreadsheet

	}

		/**
	 * Loop through a table in Excel, naming the rangess individually within tehm
	 *
	 * @param sheetName - withing the workbook, where we want to set the workbook
	 * @param baseName - in this case will be the full name used
	 * @param mainTableRef - the full table, in Excel notation *including* the Header and column rows
	 * @param numberOfHeaderRows - can be 0 (if none), 1 (e.g. years) or 2 or more (in which we will collate teh date)
	 * @param int numberOfColumnRows 
	 */
	public void nameSingleCell (String baseName, String sheetName, String cellRef)  {

		String formula = sheetName+"!"+cellRef; 	// should give us same as in Excel e.g. Accounts!B14:I14

		Name newXlNamedRange = wb.createName();
		newXlNamedRange.setNameName(baseName);
		newXlNamedRange.setRefersToFormula(formula);
		
		log.debug("Added Name:"+baseName+" formula:"+formula);

	}
}
