package net.firstpartners.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import net.firstpartners.core.log.RpLogger;

public class OfficeDocumentTest {

	
	// Handle to the loggers
	private static final Logger log = RpLogger.getLogger(OfficeDocumentTest.class.getName());

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

