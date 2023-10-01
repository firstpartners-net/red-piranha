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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;
import net.firstpartners.core.file.ResourceFinder;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
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
		PreProcessor processor = new PreProcessor(appConfig);

		//Handle to test data
		File xlFile = ResourceFinder.getFileResourceUsingConfig(TestConstants.COMPLEX_EXCEL, appConfig);
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
		boolean Info_YearEnd_BaseYear_minus_2 = false;
		boolean Info_TotalIrishLabourCosts000s_BaseYear_plus_5 = false;


		while (loopList.hasNext()) {

			aNamedRange = loopList.next(); // wb.getNameAt(namedRangeIdx);
			namedRange = aNamedRange.getNameName();
			
			//check that there are no nulls in range names
			assertFalse("Range Name "+namedRange+ " should not contain null",namedRange.contains("null"));

			//note if we have found specific sample ranges
			if(namedRange.equals("Info_YearEnd_BaseYear_minus_2")){Info_YearEnd_BaseYear_minus_2=true;}
			if(namedRange.equals("Info_TotalIrishLabourCosts000s_BaseYear_plus_5")){Info_TotalIrishLabourCosts000s_BaseYear_plus_5=true;}

			// retrieve the cells at the named range
			log.debug("Processing named range:" + aNamedRange.getNameName());
		}

		assertTrue("Did not find expected sample range:Info_YearEnd_BaseYear_minus_2",Info_YearEnd_BaseYear_minus_2);
		assertTrue("Did not find expected sample range:Info_TotalIrishLabourCosts000s_BaseYear_plus_5",Info_TotalIrishLabourCosts000s_BaseYear_plus_5);
		//


		log.debug("test complete");

	}


	
}
