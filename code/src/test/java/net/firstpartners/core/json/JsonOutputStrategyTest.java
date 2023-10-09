package net.firstpartners.core.json;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;
import net.firstpartners.core.file.ResourceFinder;
import net.firstpartners.data.RangeList;
import net.firstpartners.ui.RedControllerTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JsonOutputStrategyTest {

	// handle for our config
	@Autowired
	Config appConfig;

	@Test
	public final void testOutputJsonThenDelete() throws IOException, ClassNotFoundException, InvalidFormatException {

		//make sure we've been wired up correctly
		assert appConfig!=null;

		// Make sure we can delete file
		ResourceFinder.deleteOutputFileIfExists(TestConstants.JSON_TMP_FILE); // object remembers file name from earlier
		//File f = ResourceFinder.getFileResourceUsingConfig(TestConstants.JSON_TMP_FILE);
		//assertFalse("Found file that should exist", f.exists());

		// Now get and output our test data
		RangeList TestData = RedControllerTest.getTestDataFromExcel();
		JsonOutputStrategy jsonOut = new JsonOutputStrategy(TestConstants.JSON_TMP_FILE);

		// test the class
		jsonOut.setUpdates(null, TestData);
		jsonOut.processOutput();

		// check that this exists
		File newFile = ResourceFinder.getFileResource(TestConstants.JSON_TMP_FILE);
		assertTrue("Cannot find file that should exist", newFile.exists());

	}

}
