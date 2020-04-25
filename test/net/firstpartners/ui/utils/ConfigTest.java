package net.firstpartners.ui.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import net.firstpartners.RedConstants;
import net.firstpartners.core.drools.loader.RuleSource;
import net.firstpartners.core.spreadsheet.RangeConvertorTest;
import net.firstpartners.data.RangeHolder;

public class ConfigTest {

	
	// Logger
	private static final Logger log = Logger.getLogger(ConfigTest.class.getName());
	@SuppressWarnings("unused")
	private static RangeHolder redData=null;

	
	@Before
	public void beforeClass() throws IOException, ClassNotFoundException {

		FileInputStream fileIn = new FileInputStream(RangeConvertorTest.SAVED_RANGEHOLDER_DATA);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		redData = (RangeHolder) in.readObject();
		in.close();
		fileIn.close();
	}

	@Test
	public final void testReadConfig() {
		
		Properties prop = Config.readConfig();
		log.debug(prop.toString());

	}
	
	@Test
	public final void testReadLogFileName() {
		
		Config.reset(); // so we know the start state
		String testLog = Config.getForcedLogFileName();
		assertNotNull(testLog);
		assertNotEquals(testLog,"");
		
	}
	
	@Test
	public final void testReadRulesFiles() {
		RuleSource myRuleDrl = Config.getRuleFiles();
		assertNotNull(myRuleDrl);
		assertEquals(myRuleDrl.getRulesLocation().length,1);
		
	}
	
	@Test
	public final void testExcelIn() {
		
		String xlIn = Config.getExcelInputFile();
		assertNotNull(xlIn);
		assertNotEquals(xlIn,"");
		
	}
	
	@Test
	public final void testExcelOut() {
		
		String xlOut = Config.getExcelOutputFile();
		assertNotNull(xlOut);
		assertNotEquals(xlOut,"");
		
	}
	
	@Test
	public final void testNullProperties() {
		
		
		try {
			Config.setConfig(null);
			fail("Expected assertion error on null properties not thrown");
		} catch (AssertionError e) {
			//ignore - this is mean to happen
		}

		
	}
	
	@Test
	public final void testKeyValidation() {
		
		try {
			Config.setConfig(new Properties());
			fail("Expected assertion error on missing Excel Input value not thrown");
		} catch (AssertionError e) {
			//ignore - this is mean to happen
		}
		

	}
	
	@Test
	public final void testDuplicationValidation() {
		

		//set this up to fail
		Properties dup = new Properties();
		for (int a=0; a <Config.requiredConfigKeys.length;a++) {
			dup.setProperty(Config.requiredConfigKeys[a], "dup-value");	
		}
		
		try {
			Config.setConfig(dup);
			fail("Expected assertion error on duplicate input values not thrown");
		} catch (IllegalArgumentException e) {
			//ignore - this is mean to happen
		}
		
	}


}
