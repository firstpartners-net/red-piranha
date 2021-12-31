package net.firstpartners.ui.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.TestConstants;
import net.firstpartners.core.drools.loader.RuleConfig;
import net.firstpartners.data.RangeList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConfigTest {

	// handle for our config
	@Autowired
	Config myConfig;

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());


	public final void testReadPreviousData() throws IOException, ClassNotFoundException {
		
		FileInputStream fileIn = new FileInputStream(TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		RangeList redData = (RangeList) in.readObject();
		assertNotNull(redData);
		in.close();
		fileIn.close();
	}
	
	@Test
	public final void testConfigNotNullOrEmpty() {
		assertTrue(myConfig!=null);
		assertTrue(myConfig.getDslName()!=null);
		assertTrue(myConfig.getForcedLogFileName()!=null);
		assertTrue(myConfig.getInputFileName()!=null);
		
	} 
	
	@Test
	public final void testAutoConfig() {
		assertTrue(myConfig.getOutputFileName()!=null);
		log.info("Output file name"+myConfig.getOutputFileName());
		assertTrue(myConfig.getOutputFileName().indexOf("FILE_OUTPUT")==0);
	}
	
	
	@Test
	public final void testCanOverwriteConfig() {
		
		myConfig.setInputFileName("yada-yada");
		assertEquals(myConfig.getInputFileName(),"yada-yada");
		
		myConfig.setOutputFileName("yada-yada2");
		assertEquals(myConfig.getOutputFileName(),"yada-yada2");
	}
	
	
	@Test
	public final void testReadLogFileName() {

		String testLog = myConfig.getForcedLogFileName();
		Assert.assertNotNull(testLog);
		assertTrue(testLog != "");
		log.debug("test run");

	}

	@Test
	public final void testReadDslName() {

		String testDsl = myConfig.getDslName();
		Assert.assertNotNull(testDsl);
		assertTrue(testDsl != "");

	}

	@Test
	public final void testReadRulesFiles() {
		RuleConfig myRuleDrl = myConfig.getRuleConfig();
		Assert.assertNotNull(myRuleDrl);
		Assert.assertEquals(myRuleDrl.getRulesLocation().length, 1);

	}

	@Test
	public final void testExcelIn() {

		String xlIn = myConfig.getInputFileName();
		Assert.assertNotNull(xlIn);
		assertTrue(xlIn != "");

	}

	@Test
	public final void testExcelOut() {

		String xlOut = myConfig.getOutputFileName();
		Assert.assertNotNull(xlOut);
		assertTrue(xlOut != "");

	}
	
	@Test
	public final void testToString() {
		
		String toS = myConfig.toString();
		assertEquals(-1,toS.indexOf("inputFileName=<null>"));
	}

}
