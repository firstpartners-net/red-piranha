package net.firstpartners.core.script;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;
import net.firstpartners.core.RPException;
import net.firstpartners.core.file.ResourceFinder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ScriptSupportTest {

	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// handle for our config
	@Autowired
	Config appConfig;

	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	public final void testSingleCellNaming() throws Exception {


		File xlFile = ResourceFinder.getFileResource(TestConstants.COMPLEX_EXCEL);
		InputStream inputAsStream = new FileInputStream(xlFile);
		Workbook excelWorkBook = WorkbookFactory.create(inputAsStream);

		// handle to the class under test and remove broken names
		ScriptSupport sprt = new ScriptSupport(excelWorkBook);
		sprt.removePreviousNamedRanges();

		// try out the naming
		sprt.nameSingleCell("testName", "Accounts", "A1:B2");

		// test the named ranges coming back
		Name testName = excelWorkBook.getName("testName");
		assertEquals(testName.getRefersToFormula(), "Accounts!A1:B2");

	}

	@Test
	public final void testCellStringNaming() throws Exception, RPException {

		File xlFile = ResourceFinder.getFileResource(TestConstants.COMPLEX_EXCEL);
		InputStream inputAsStream = new FileInputStream(xlFile);
		Workbook excelWorkBook = WorkbookFactory.create(inputAsStream);

		// handle to the class under test and clean ranges
		ScriptSupport sprt = new ScriptSupport(excelWorkBook);
		sprt.removePreviousNamedRanges();

		// try out the naming
		sprt.nameTable("Key Info", "Accounts", "A12:I30");

		// test the named ranges coming back
		Name testName = excelWorkBook.getName("KeyInfo_YearEnd_BaseYear_minus_2");
		assertEquals(testName.getRefersToFormula(), "Accounts!B13");

		testName = excelWorkBook.getName("KeyInfo_InvoicediscountingProjectedYearEndBalance_BaseYear_plus_2");
		assertEquals(testName.getRefersToFormula(), "Accounts!F22");

	}

	/**
	 * Test the formatter - especially useful to get dates as "31/12/20" etc
	 * 
	 * @throws Exception
	 */
	@Test
	public final void testTableNaming() throws Exception {

		File xlFile = ResourceFinder.getFileResource(TestConstants.COMPLEX_EXCEL);
		InputStream inputAsStream = new FileInputStream(xlFile);
		Workbook excelWorkBook = WorkbookFactory.create(inputAsStream);

		// handle to the class under test
		ScriptSupport sprt = new ScriptSupport(excelWorkBook);

		// Get a handle to a cell, format, check return value
		org.apache.poi.ss.usermodel.Sheet sheet = excelWorkBook.getSheet("Accounts");

		// setup a hasmhap of cells and the values we expect to extract
		HashMap<String, String> testMap = new HashMap<String, String>();
		// testMap.put("Accounts!B39","2751.0");
		testMap.put("Accounts!A14", "No. of Global Employees @ y/e (incl Irish employment)");
		testMap.put("Accounts!B34", "31/12/18");

		// Iterating HashMap through for loop
		for (Map.Entry<String, String> set : testMap.entrySet()) {

			CellReference cellReference = new CellReference(set.getKey());
			log.debug("Testing cell:" + set.getKey() + " hoping for:" + set.getValue());

			Row row = sheet.getRow(cellReference.getRow());
			Cell cell = row.getCell(cellReference.getCol());

			assertEquals(set.getValue(), sprt.getCellAsStringForceDateConversion(cell));

		}
	}

	@Test
	public final void testUpdateTableValues() throws Exception {

		File xlFile = ResourceFinder.getFileResource(TestConstants.COMPLEX_EXCEL);
		InputStream inputAsStream = new FileInputStream(xlFile);
		Workbook excelWorkBook = WorkbookFactory.create(inputAsStream);

		// handle to the class under test
		ScriptSupport sprt = new ScriptSupport(excelWorkBook);

		// try out the update of values
		sprt.setText("Overdraft_new", "Accounts", "A173");

		String tmpValue = sprt.get("Accounts", "A173");

		assertEquals("Overdraft_new",tmpValue);



	}

	@Test
	public final void testRemoveNamedRages() throws Exception {

		File xlFile = ResourceFinder.getFileResource(TestConstants.COMPLEX_EXCEL);
		InputStream inputAsStream = new FileInputStream(xlFile);
		Workbook excelWorkBook = WorkbookFactory.create(inputAsStream);

		// handle to the class under test
		ScriptSupport sprt = new ScriptSupport(excelWorkBook);

		// try out the update of values
		assertTrue("List of Named Ranges should not be empty at start",excelWorkBook.getAllNames().size()>0);
		sprt.removePreviousNamedRanges();
		assertTrue("List of Named Ranges should be empty after operation",excelWorkBook.getAllNames().size()==0);


	}

	@Test
	public final void testSheetsNameSafe() throws IOException, ResourceException, ScriptException {
		
		//Handle to test data
		File xlFile = ResourceFinder.getFileResource(TestConstants.COMPLEX_EXCEL);
		InputStream inputAsStream = new FileInputStream(xlFile);
		Workbook wb = WorkbookFactory.create(inputAsStream);

		assertNotNull(wb); 

		

		//Create teh script support and get handle to the ??updated?? wb in it
		ScriptSupport sprt = new ScriptSupport(wb);
		
		//Our test data has invalid rangenames that need removed
		sprt.removePreviousNamedRanges();

		// now try to remove sheet spaces
		sprt.removeSpacesSheetNames();
		
		wb = sprt.wb;
	

		//Check we can't find a sheet with a space in it

		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			String tmpSheetName = sheet.getSheetName();

			log.debug("Reviewing sheet name:" + tmpSheetName);
			assertTrue(!tmpSheetName.contains(" "));
	
			
		}
		

	}

}