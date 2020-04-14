package net.firstpartners.ui;

import java.io.File;
import java.util.logging.Logger;

import org.junit.Test;

public class RedServletTest {

	//Logger
	private static final Logger log = Logger.getLogger(RedServletTest.class.getName());
	
	
	@Test
	public final void testCallRules() throws Exception{
		
		//Check where we are
		File whereAmI = new File(".");
		log.info("Default file location:"+whereAmI.getAbsolutePath());
		// TODO fail("Not yet implemented"); 
				
	}

}
