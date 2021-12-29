package net.firstpartners.ui.utils;


import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.firstpartners.TestConstants;
import net.firstpartners.core.drools.loader.RuleConfig;
import net.firstpartners.data.RangeList;

public class ConfigTest {

	//handle for our config
	@Autowired
	Config appConfig;
	
	// Logger
		private static final Logger log = Logger.getLogger(ConfigTest.class.getName());
		@SuppressWarnings("unused")
		private static RangeList redData=null;

		
		@Before
		public void beforeClass() throws IOException, ClassNotFoundException {

			FileInputStream fileIn = new FileInputStream(TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			redData = (RangeList) in.readObject();
			in.close();
			fileIn.close();
		}


		
		@Test
		public final void testReadLogFileName() {
			
			String testLog = appConfig.getForcedLogFileName();
			Assert.assertNotNull(testLog);
			assertTrue(testLog!="");
			log.debug("test run");
			
		}
		
		@Test
		public final void testReadDslName() {
			
			String testDsl = appConfig.getDslName();
			Assert.assertNotNull(testDsl);
			assertTrue(testDsl!="");
			
		}
		@Test
		public final void testReadRulesFiles() {
			RuleConfig myRuleDrl = appConfig.getRuleConfig();
			Assert.assertNotNull(myRuleDrl);
			Assert.assertEquals(myRuleDrl.getRulesLocation().length,1);
			
		}
		
		@Test
		public final void testExcelIn() {
			
			String xlIn = appConfig.getInputFileName();
			Assert.assertNotNull(xlIn);
			assertTrue(xlIn!="");
			
		}
		
		@Test
		public final void testExcelOut() {
			
			String xlOut = appConfig.getOutputFileName();
			Assert.assertNotNull(xlOut);
			assertTrue(xlOut!="");
			
		}

}
