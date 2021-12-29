package net.firstpartners.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.firstpartners.TestConstants;
import net.firstpartners.core.log.EmptyLogger;
import net.firstpartners.core.log.GiveLogFeedback;
import net.firstpartners.core.log.IGiveFeedbackToUsers;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.ui.utils.Config;

/**
 * Test to call the main method
 * @TODO investigate more options from : https://www.baeldung.com/spring-tests-override-properties
 * @author PBrowne
 *
 */
public class StartTest {

	//handle for our config
	@Autowired
	Config appConfig;
	
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

		fail("Test needs implemented for new configuration");
		
		// Mock up logging objects

		ILogger playerAsLogger = new EmptyLogger();
		IGiveFeedbackToUsers userUpdates = new GiveLogFeedback();

		// Mock up Config - these are system (or JVM/Thread wide)
		Properties p = new Properties();
		p.setProperty(Config.FILE_INPUT, TestConstants.WORD_DATA_FILE);
		p.setProperty(Config.FILE_OUTPUT, TestConstants.CSV_TMP_FILE);
		p.setProperty(Config.RULE1, TestConstants.RULE_FILE_FOR_WORD);
		//appConfig.setProperties(p);
		

		// Make sure the config changes stick
		assertEquals(appConfig.getInputFileName(), TestConstants.WORD_DATA_FILE);
		log.debug("Successfully configure for word test");
		
		// Call test
		//Start.runRules(playerAsLogger, userUpdates);

	}
	
	@Test
	public final void testCallRunRulesExcelInWordDocOut() throws Throwable {

		fail("Test needs implemented for new configuration");
		
		
		// Mock up logging objects

		ILogger playerAsLogger = new EmptyLogger();
		IGiveFeedbackToUsers userUpdates = new GiveLogFeedback();

		// Mock up Config - these are system (or JVM/Thread wide)
		Properties p = new Properties();
		p.setProperty(Config.FILE_INPUT, TestConstants.XLSX_DATA_FILE);
		p.setProperty(Config.FILE_OUTPUT, TestConstants.CSV_TMP_FILE);
		p.setProperty(Config.RULE1, TestConstants.RULE_FILE_FOR_WORD);  // these shouldn't fire, but we're more interested in teh converstion
		//appConfig.setProperties(p);
		

		// Make sure the config changes stick
		assertEquals(appConfig.getInputFileName(), TestConstants.XLSX_DATA_FILE);
		log.debug("Successfully configure for excel in word out test");
		
		// Call test
		//Start.runRules(playerAsLogger, userUpdates);

	}


}
