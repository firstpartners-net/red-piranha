package net.firstpartners.ui;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.log.EmptyLogger;
import net.firstpartners.core.log.GiveLogFeedback;
import net.firstpartners.core.log.IGiveFeedbackToUsers;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.ui.utils.Config;

/**
 * Test to call the main method
 * 
 * @author PBrowne
 *
 */
public class StartTest {

	// Logger
	private static final Logger log = Logger.getLogger(StartTest.class.getName());

	// Test file locations
	private static final String[] args = new String[] {};

	@Test
	public final void testCallMainDefaultArgs() throws Throwable {

		// Call test
		Start.main(args);

	}

	@Test
	public final void testCallRunRulesWordDocs() throws Throwable {

		// Mock up logging objects

		ILogger playerAsLogger = new EmptyLogger();
		IGiveFeedbackToUsers userUpdates = new GiveLogFeedback();

		// Mock up Config - these are system (or JVM/Thread wide)
		Properties p = new Properties();
		p.setProperty(Config.FILE_INPUT, TestConstants.WORD_DATA_FILE);
		p.setProperty(Config.FILE_OUTPUT, TestConstants.CSV_TMP_FILE);
		p.setProperty(Config.RULE1, TestConstants.RULE_FILE_FOR_WORD);
		Config.setProperties(p);
		

		// Make sure the config changes stick
		assertEquals(Config.getInputFileName(), TestConstants.WORD_DATA_FILE);
		log.debug("Successfully configure for word test");
		
		// Call test
		Start.runRules(playerAsLogger, userUpdates);

	}
	
	@Test
	public final void testCallRunRulesExcelInWordDocOut() throws Throwable {

		// Mock up logging objects

		ILogger playerAsLogger = new EmptyLogger();
		IGiveFeedbackToUsers userUpdates = new GiveLogFeedback();

		// Mock up Config - these are system (or JVM/Thread wide)
		Properties p = new Properties();
		p.setProperty(Config.FILE_INPUT, TestConstants.XLSX_DATA_FILE);
		p.setProperty(Config.FILE_OUTPUT, TestConstants.CSV_TMP_FILE);
		p.setProperty(Config.RULE1, TestConstants.RULE_FILE_FOR_WORD);  // these shouldn't fire, but we're more interested in teh converstion
		Config.setProperties(p);
		

		// Make sure the config changes stick
		assertEquals(Config.getInputFileName(), TestConstants.XLSX_DATA_FILE);
		log.debug("Successfully configure for excel in word out test");
		
		// Call test
		Start.runRules(playerAsLogger, userUpdates);

	}


}
