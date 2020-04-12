package net.firstpartners.ui;

import static org.junit.Assert.*;

import java.io.File;
import java.util.logging.Logger;

import org.junit.Test;

public class RedServletTest {

	//Logger
	private static final Logger log = Logger.getLogger(RedServletTest.class.getName());
	
	//Test file locations
	private static final String EXCEL_DATA_FILE = "file:///home/paul/projects/red-piranha/war/chocolate-data.xls";
	private static final String[] RULES_FILES = new String[] { "file:///home/paul/projects/red-piranha/war/simple-rules.drl" };

	
	
	@Test
	public final void testCallRules() throws Exception{
		
		//Check where we are
		File whereAmI = new File(".");
		log.info("Default file location:"+whereAmI.getAbsolutePath());
				
	}

}
