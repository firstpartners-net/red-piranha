package net.firstpartners.core.file;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;


public class OfficeDocumentTest {

	
	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Test
	public final void testRunEventRulesExample() throws IOException {
		Workbook wb = WorkbookFactory.create(true); // create new boolean
		
		OfficeDocument myDoc = new OfficeDocument(wb);
		assertNotNull(myDoc.getOriginalAsPoiWorkbook());
		assertTrue(myDoc.getOriginalAsPoiWorkbook() instanceof Workbook);
		assertTrue(myDoc.isSpreadsheet());
		
		//Next statement should fail (get as word doc)
		//which is the point of the entire class
		try {
			myDoc.getOriginalAsPoiWordDoc();
			fail("Excpected Conversion Error not thrown");
			
		}catch (Exception e) {
			log.debug("Expected Exception thrown");
		}
	}

}

