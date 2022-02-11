package net.firstpartners.core.json;

import static org.junit.jupiter.api.Assertions.*;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SampleDataTest {
	
	// Logger if needed
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
	void testGetSampleUrl() throws UnsupportedEncodingException {
		
		SampleData testData = new SampleData();
		testData.setDescriptiontext("blah");
		testData.setDslFileLocation("example/dslfile.dsl");
		testData.setInputFileLocation("example/inputfile.xls");
		testData.setOutputFileLocation("example/outputFile.xls");
		testData.setRuleFileLocation("example/rulefile.drl");
		
		
		String testUrl = testData.getSampleUrlParams();
		
		log.debug("generated URL:"+testUrl);
		
		
		assertNotNull (testUrl);
		assertTrue (testUrl.indexOf("http")<0,"should not encode the http part"); 
		assertTrue (testUrl.indexOf("/")<0,"url should be encoded");
		assertTrue (testUrl.indexOf("blah")<0,"description should be encoded"); 
		assertTrue (testUrl.indexOf("=&")<0,"Check for a missing value - = and & should not be beside each other");
		assertTrue (testUrl.indexOf("null")<0,"No null values should be returned");
		
	
		
		
	}
	

}
