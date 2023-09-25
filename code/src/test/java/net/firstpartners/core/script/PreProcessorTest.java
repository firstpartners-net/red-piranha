package net.firstpartners.core.script;


import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;


import org.slf4j.Logger;

import org.junit.Test;


public class PreProcessorTest {

	
	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// handle to the class under test
	PreProcessor processor = new PreProcessor();
	
	
	@Test
	public final void testRunPreProcessor() throws Exception {
		
		processor.runGroovyScript(TestConstants.SIMPLE_GROOVY);
		log.debug("test complete");
		
		
	}

}

