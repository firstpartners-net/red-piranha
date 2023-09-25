package net.firstpartners.core.script;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;
import net.firstpartners.core.file.ResourceFinder;

import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
public class PreProcessorTest {

	
	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// handle to the class under test
	PreProcessor processor = new PreProcessor();

	// handle for our config
	@Autowired
	Config appConfig;
	
	
	@Test
	public final void testRunPreProcessor() throws Exception {
		
		File xlFile = ResourceFinder.getFileResourceUsingConfig(TestConstants.COMPLEX_EXCEL, appConfig);

		InputStream inputAsStream = new FileInputStream(xlFile);
		Workbook excelWorkBook = WorkbookFactory.create(inputAsStream);

		processor.preprocessXlWorkbook(TestConstants.SIMPLE_GROOVY,excelWorkBook);
		log.debug("test complete");
		
		
	}

}

