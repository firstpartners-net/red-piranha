package net.firstpartners.core.file;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.TestConstants;
import net.firstpartners.core.Config;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ResourceFinderTest {

	
	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());

		// handle for our config
	@Autowired
	Config appConfig;
	
	
	@Test
	public final void testFindMultiInFromDir() throws IOException {

		List<File> foundFiles = ResourceFinder.getDirectoryFilesUsingConfig(TestConstants.DIRECTORY_SAMPLE);

		log.debug("Number of files found:"+foundFiles.size());
		assertEquals("Number of files in sample Directory five should be 8",8,foundFiles.size());
		
	}

}

