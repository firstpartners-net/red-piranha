package net.firstpartners.core.excel;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import groovy.util.ResourceException;
import groovy.util.ScriptException;

import org.slf4j.Logger;

import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;

import net.firstpartners.core.Config;
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

	public SpreadSheetConvertor(Config appConfig){
		this.appConfig=appConfig;
	}

	/**
	 * Update an excel file with our new values uses the original sheet and original
	 * cell reference within the red cell to copy backk
	 *
	 * @param wb a {@link org.apache.poi.ss.usermodel.Workbook} object
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

				CellReference cellReference = new CellReference(thisRedCell.getOriginalCellRow(),thisRedCell.getOriginalCellRow());
				
				
				Row row = SheetConvertor.getOrCreateRow(thisSheet, cellReference);

				org.apache.poi.ss.usermodel.Cell excelCell = SheetConvertor.getOrCreateCell(row, cellReference);

				// update the values into the cell
				CellConvertor.convertRedCellToPoiCell(wb, excelCell, thisRedCell);

			}

		}

	}

	// handle for our config
	@Autowired
	Config appConfig;

	public Config getAppConfig() {
		return appConfig;
	}

		public void setAppConfig(Config appConfig) {
			this.appConfig = appConfig;
		}

	/**
 * Read an excel file and return what we find as a set of simple JavaBeans
 *
 * @param wb - Apache Poi workbook to convert
 * @return RangeHolder - can be empty if no names are definded
 * @throws java.io.IOException
	 * @throws ScriptException
	 * @throws ResourceException
 */
public RangeList convertNamesFromPoiWorkbookIntoRedRange(org.apache.poi.ss.usermodel.Workbook wb) throws IOException, ResourceException, ScriptException{
	return convertNamesFromPoiWorkbookIntoRedRange(null,null,wb);
}

	/**
	 * Read an excel file and return what we find as a set of simple JavaBeans
	 *
	 * @param baseDir of this dataset
	 * @param wb - Apache Poi workbook to convert
	 * @return RangeHolder - can be empty if no names are definded
	 * @throws java.io.IOException
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public RangeList convertNamesFromPoiWorkbookIntoRedRange(String baseDir, String preprocessScript,org.apache.poi.ss.usermodel.Workbook wb)
			throws IOException, ResourceException, ScriptException {

		
		//check incoming values
		assert wb!=null : "Incoming workboook should not be null";

		
		// hold all the named ranges from our sheet
		RangeList returnValues = new RangeList();

		// retrieve the named range 
		List<? extends Name> namedRanges = wb.getAllNames();		
		if (namedRanges == null) {
			log.info("No Named Ranges in workbook- skipping");
			throw new IOException("No Named Ranges in workbook- skipping");
			//return returnValues;
		}

		// Setup loop through named ranges
		org.apache.poi.ss.usermodel.Name aNamedRange = null;
		ListIterator<? extends Name> loopList = namedRanges.listIterator();

		while (loopList.hasNext()) {

			aNamedRange = loopList.next(); // wb.getNameAt(namedRangeIdx);

			// Reset to empty array - so we're covered even if an exception is thrown
			AreaReference aRef[] = new AreaReference[0];

			try {
				aRef = AreaReference.generateContiguous(wb.getSpreadsheetVersion(), aNamedRange.getRefersToFormula());

				//only proceed if we can get an actual handle to this
				// retrieve the cells at the named range
				log.debug("Processing named range:" + aNamedRange.getNameName());

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
					//try {
						r = sheet.getRow(thisCellRef.getRow()); // ISSUE HERE
					//} catch (NullPointerException npe) {
					//	log.error("Excel Read error on Cell:" + thisCellRef, npe);
					//}

					org.apache.poi.ss.usermodel.Cell thisExcelCell = null;

					if (r != null) {

						// extract the cell contents based on cell type etc.
						thisExcelCell = r.getCell(thisCellRef.getCol());

					}

					// Create our JavaBean representing the cell
					String cellHandle = redRange.getUniqueCellName(thisCellinRange);
					assert cellHandle != null;

					net.firstpartners.data.Cell redCell = CellConvertor.convertPoiCellToRedCell(cellHandle, thisExcelCell);

					// log.debug("Converted Cell:" + redCell);
					log.debug("Converted Number:" + thisCellinRange + " of " + cellArray.size());

					// Add the list of cells to a range
					redRange.put(cellHandle, redCell);

					//save for use later
					returnValues.add(redRange);

				}


			} catch (IllegalArgumentException iae) {

				// It is possible that a named range exists in excel but, the actual cell as
				// been deleted
				log.debug("Ignoring invalid range ref:" + iae);
			}

				

			
		}

		return returnValues;

	}

}
