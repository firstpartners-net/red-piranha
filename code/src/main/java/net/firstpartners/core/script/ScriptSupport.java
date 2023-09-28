package net.firstpartners.core.script;

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
	 * Loop through a table in Excel, naming the ranges individually within tehm
	 * @param baseName - foundation we will generate names from e.g. a baseName of "Cash" might generate "Cash-In-FY22"
	 * @param sheetName - withing the workbook, where we want to set the workbook
	 * @param mainTableRef - the full table, in Excel notation *including* the Header and column rows
	 * @param numberOfHeaderRows - can be 0 (if none), 1 (e.g. years) or 2 or more (in which we will collate teh date)
	 * @param int numberOfColumnRows 
	*/
	public void nameTable (String baseName, String sheetName, String mainTableRef ,int numberOfHeaderRows, int numberOfColumnRows )  {

		
		//get handle to block of cells at maintable ref
		String formula = sheetName+"!"+mainTableRef; 	// should give us same as in Excel e.g. Accounts!B14:I14
		log.debug("Looking for Table Formula:"+formula);
		AreaReference aref = new AreaReference(formula,SpreadsheetVersion.EXCEL2007);

		//Setup our loop
		Sheet s = wb.getSheet(sheetName);
		CellReference[] crefs = aref.getAllReferencedCells();

		for (int i=0; i<crefs.length; i++) {

			Row r = s.getRow(crefs[i].getRow());
			Cell c = r.getCell(crefs[i].getCol());

			log.debug(crefs[i].formatAsString()+" Row:"+r+" Col:"+c.toString());

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
