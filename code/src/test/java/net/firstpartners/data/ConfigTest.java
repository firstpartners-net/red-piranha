package net.firstpartners.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.TestConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConfigTest {

	// handle for our config
	@Autowired
	Config myConfig;

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	public final void testCurrentLocation() {
		
		// Check where we are
		File whereAmI = new File(".");
		log.debug("Default file location:" + whereAmI.getAbsolutePath());
	}
	
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
		assertTrue(myConfig.getInputFileName()!=null);
		
	} 

	
	
	@Test
	public final void testCanOverwriteConfig() {
		
		myConfig.setInputFileName("yada-yada");
		assertEquals(myConfig.getInputFileName(),"yada-yada");
		
		myConfig.setOutputFileName("yada-yada2");
		assertEquals(myConfig.getOutputFileName(),"yada-yada2");
	}
	

	@Test
	public final void testReadDslName() {

		String testDsl = myConfig.getDslName();
		Assert.assertNotNull(testDsl);
		assertTrue(testDsl != "");

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
