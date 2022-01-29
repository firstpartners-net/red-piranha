package net.firstpartners.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.TestConstants;
import net.firstpartners.data.RangeList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConfigTest {

	// handle for our config
	@Autowired
	Config myConfig;
	public final void testReadPreviousData() throws IOException, ClassNotFoundException {
		
		FileInputStream fileIn = new FileInputStream(TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		RangeList redData = (RangeList) in.readObject();
		assertNotNull(redData);
		in.close();
		fileIn.close();
	}
	
	
	@Test
	public final void testSampleBaseDirNotNullOrEmpty() {
		String sampleBaseDir =myConfig.getSampleBaseDirDefault();
		assertNotNull(sampleBaseDir);

		String sampleBaseDirAlternate =myConfig.getSampleBaseDirAlternate();
		assertNotNull(sampleBaseDirAlternate);

		
	} 

	
	@Test
	public final void testToString() {
		
		String toS = myConfig.toString();
		assertEquals(-1,toS.indexOf("inputFileName=<null>"));
	}

	
}
