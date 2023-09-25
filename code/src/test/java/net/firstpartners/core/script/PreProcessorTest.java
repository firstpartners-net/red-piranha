package net.firstpartners.core.script;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.slf4j.LoggerFactory;

import net.firstpartners.core.file.OfficeDocument;

import org.slf4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;


public class PreProcessorTest {

	
	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Test
	public final void testRunPreProcessor() {
		
		PreProcessor.main(null);
		
		
	}

}

