package net.firstpartners.ui;


import java.io.File;
import java.util.logging.Logger;

import net.firstpartners.ui.Rp2CommandLine;

import org.junit.Test;

public class Rp2CommandLineTest {

	//Logger
	private static final Logger log = Logger.getLogger(Rp2CommandLineTest.class.getName());

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
			Rp2CommandLine.main(args);
		} catch (Throwable t){
			t.printStackTrace();
			throw t;
		}


	}

}
