package net.firstpartners.core.script;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.ListIterator;

import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;
import net.firstpartners.core.file.ResourceFinder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PreProcessorTest {

	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// handle for our config
	@Autowired
	Config appConfig;

	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	public final void testRunPreProcessor() throws Exception {

		// handle to the class under test
		PreProcessor processor = new PreProcessor();

		//Handle to test data
		File xlFile = ResourceFinder.getFile(TestConstants.COMPLEX_EXCEL);
		InputStream inputAsStream = new FileInputStream(xlFile);
		Workbook excelWorkBook = WorkbookFactory.create(inputAsStream);

		//Test out the class
		Workbook returnBook = processor.preprocessXlWorkbook("", TestConstants.GROOVY_PREPROCESS, excelWorkBook);

		// retrieve the named range - Iterator not available
		List<? extends Name> namedRanges = returnBook.getAllNames();

		// check the results
		assertNotNull(namedRanges);
	
		// Setup loop through named ranges
		org.apache.poi.ss.usermodel.Name aNamedRange = null;
		String namedRange ="";
		ListIterator<? extends Name> loopList = namedRanges.listIterator();

		//Some specific names we are looking for
		boolean Cash_III_WorkingCapitalIncrease_Decrease_III_BaseYear_minus_1_0	= false;
		boolean PL_III_OtherRDexpenditure_III_31_12_18_0 = false;


		while (loopList.hasNext()) {

			aNamedRange = loopList.next(); // wb.getNameAt(namedRangeIdx);
			namedRange = aNamedRange.getNameName();
			
			//check that there are no nulls in range names
			assertFalse("Range Name "+namedRange+ " should not contain null",namedRange.contains("null"));

			//note if we have found specific sample ranges
			//remember the III format is also in Excel
			if(namedRange.equals("Cash_III_WorkingCapitalIncrease_Decrease_III_BaseYear_minus_1")){Cash_III_WorkingCapitalIncrease_Decrease_III_BaseYear_minus_1_0=true;}
			if(namedRange.equals("PL_III_OtherRDexpenditure_III_31_12_18")){PL_III_OtherRDexpenditure_III_31_12_18_0=true;}

			// retrieve the cells at the named range
			log.debug("Processing named range:" + aNamedRange.getNameName());
		}

		assertTrue("Did not find expected sample range:Info_YearEnd_BaseYear_minus_2",Cash_III_WorkingCapitalIncrease_Decrease_III_BaseYear_minus_1_0);
		assertTrue("Did not find expected sample range:PL_III_OtherRDexpenditure_III_31_12_18",PL_III_OtherRDexpenditure_III_31_12_18_0);
		//


		log.debug("test complete");

	}


	
}
