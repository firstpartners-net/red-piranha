package net.firstpartners.core.json;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import net.firstpartners.core.Config;

@SpringBootTest
class SampleDataLoaderTest {
	
	private final static String SAMPLE_OUTPUT_FORMAT_FILENAME="src/test/resources/testdata/config-sample-format-rename-me.json";
	
	@Autowired
	Config myConfig;
	
	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSaveSampleConfigJson() throws StreamWriteException, DatabindException, IOException {
		
		SampleDataLoader.outputSampleFormat(SAMPLE_OUTPUT_FORMAT_FILENAME);
		
		// no assertions if this doesn't blow up!
	}
	
	/**
	 * We try loading the "real" sample information, partly as an integration test
	 * @throws StreamWriteException
	 * @throws DatabindException
	 * @throws IOException
	 */
	@Test
	void testLoadSampleConfigJson() throws StreamWriteException, DatabindException, IOException {
		
		
		//load the samples - passing in null means we only look relative to working directory
		List<SampleData> samples = SampleDataLoader.loadSampleInformation(SampleDataLoader.SAMPLE_INFO_IN_JSON,myConfig);
		
		assertNotNull(samples);
		assertFalse (samples.isEmpty());
		assertTrue(samples.size()>0);
		
		//SampleData sample1;
		
		for (SampleData thisSample : samples) {
			
			assertNotNull(thisSample);
			log.debug(thisSample.toString());
			assertNotNull(thisSample.getInputFileLocation());
			assertNotNull(thisSample.getRuleFileLocation());
			assertNotNull(thisSample.getOutputFileLocation());
			assertNotNull(thisSample.getDescriptiontext());
			
		}
		

	}

}
