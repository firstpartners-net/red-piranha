package net.firstpartners.core.script;

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

		File xlFile = ResourceFinder.getFileResourceUsingConfig(TestConstants.COMPLEX_EXCEL, appConfig);

		InputStream inputAsStream = new FileInputStream(xlFile);
		Workbook excelWorkBook = WorkbookFactory.create(inputAsStream);

		Workbook returnBook = processor.preprocessXlWorkbook("", TestConstants.SIMPLE_GROOVY, excelWorkBook);

		// retrieve the named range - Iterator not available
		List<? extends Name> namedRanges = returnBook.getAllNames();

		// Setup loop through named ranges
		int namedRangeIdx = -1;
		namedRangeIdx++;

		org.apache.poi.ss.usermodel.Name aNamedRange = null;
		ListIterator<? extends Name> loopList = namedRanges.listIterator();

		while (loopList.hasNext()) {
			namedRangeIdx++;

			aNamedRange = loopList.next(); // wb.getNameAt(namedRangeIdx);

			// retrieve the cells at the named range
			log.debug("Processing named range:" + aNamedRange.getNameName());
		}

		log.debug("test complete");

	}

}
