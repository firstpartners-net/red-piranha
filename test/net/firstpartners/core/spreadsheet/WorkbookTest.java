package net.firstpartners.core.spreadsheet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;


public class WorkbookTest  {


	private Log log = LogFactory.getLog(WorkbookTest.class);

	
	@Test
	public void testBasicWorkbook() throws Exception {

		//Create the test object
		Workbook myWorkbook = new Workbook();
		assertNotNull(myWorkbook);
		
		
	}
}

