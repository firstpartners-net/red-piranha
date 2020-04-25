package net.firstpartners.ui;


import java.io.File;
import org.apache.log4j.Logger;

import org.junit.Test;

/**
 * Test to call the main method
 * 
 * @author PBrowne
 *
 */
public class RedCommandLineTest {

	//Logger
	private static final Logger log = Logger.getLogger(RedCommandLineTest.class.getName());

	//Test file locations
	private static final String[] args = new String[] { };


	@Test
	public final void testCallMain() throws Throwable{

		//Check where we are
		File whereAmI = new File(".");
		log.info("Default file location:"+whereAmI.getAbsolutePath());


		//Call test
		RedCommandLine.main(args);
		

	}
	
	
}
