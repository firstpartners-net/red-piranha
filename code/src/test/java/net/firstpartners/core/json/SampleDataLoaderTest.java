package net.firstpartners.core.json;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

class SampleDataLoaderTest {
	
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
		
		SampleDataLoader.outputSampleFormat(SampleDataLoader.SAMPLE_OUTPUT_FORMAT_FILENAME);
		
		// no assertions if this doesn't blow up!
	}
	
	@Test
	void testLoadSampleConfigJson() throws StreamWriteException, DatabindException, IOException {
		
		
		//load the samples - passing in null means we only look relative to working directory
		List<SampleData> samples = SampleDataLoader.loadSampleInformation(SampleDataLoader.SAMPLE_OUTPUT_FORMAT_FILENAME,null);
		
		assertNotNull(samples);
		assertFalse (samples.isEmpty());
		assertEquals(2,samples.size());
		
		//SampleData sample1;
		
		for (SampleData sample1 : samples) {
			
			assertNotNull(sample1);
			log.debug(sample1.toString());
			assertNotNull(sample1.getInputFileLocation());
			assertNotNull(sample1.getRuleFileLocation());
			assertNotNull(sample1.getOutputFileLocation());
			assertNotNull(sample1.getDescriptiontext());
			
		}
		
		
		
		
		
		// no assertions if this doesn't blow up!
	}

	private void AssertNotNull(List<SampleData> samples) {
		// TODO Auto-generated method stub
		
	}

}
