package net.firstpartners.ui;


import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;

import net.firstpartners.ui.RedCommandLine;

import org.junit.Test;

public class RedCommandLineTest {

	//Logger
	private static final Logger log = Logger.getLogger(RedCommandLineTest.class.getName());

	//Test file locations
	private static final String[] args = new String[] {"war/sampleresources/ExcelDataRules/chocolate-data.xls",
		"war/sampleresources/ExcelDataRules/output.xls",
	"war/sampleresources/ExcelDataRules/log-then-modify-rules.drl" };


	@Test
	public final void testCallMain() throws Throwable{

		//Check where we are
		File whereAmI = new File(".");
		log.info("Default file location:"+whereAmI.getAbsolutePath());


		//Call test
		try{
			RedCommandLine.main(args);
		} catch (Throwable t){
			t.printStackTrace();
			throw t;
		}


	}
	
	
}
