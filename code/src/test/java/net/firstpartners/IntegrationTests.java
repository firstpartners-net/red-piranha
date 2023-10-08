package net.firstpartners;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.core.Config;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.IRunner;
import net.firstpartners.core.drools.RunnerFactory;
import net.firstpartners.core.json.SampleData;
import net.firstpartners.core.json.SampleDataLoader;

/**
 * Test Harness to run through all the Red Piranha examples - make sure that
 * they continue working
 * 
 * @author paulf
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IntegrationTests {

	// handle for our config
	@Autowired
	Config appConfig;

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
	/**
	 * Useful if we want to unit test a specfici example
	 * @throws Exception
	 */
	void testSpecificExample() throws Exception, RPException {
		sampleTestRunner(5);
	}


 	@Test
	void testAllSamples() throws Exception, RPException {
		sampleTestRunner(-1);
	}

	/**
	 * Pass in Integer = null or -1 to run all tests
	 * Or single number to run single test
	 * @param sampleToRun
	 * @throws Exception
	 * @throws RPException
	 */
	private void sampleTestRunner(int sampleToRun) throws Exception, RPException{

		List<SampleData> examples = SampleDataLoader.loadSampleInformation(SampleDataLoader.SAMPLE_INFO_IN_JSON);

		//counter
		if(sampleToRun<0){
			log.info("running all integrations samples");
		} else {
			log.info("running single integration sample:"+sampleToRun);
		}
		


		for (SampleData thisExample : examples) {

			//check if we're running in single test mode
			if(sampleToRun>0){
				
				if(sampleToRun != thisExample.getId()){
					log.info("Running in single sample mode - skip sample:"+thisExample.getId());
					continue; //skips this iteration of the loop
				} else {
					log.info("Running single sample:"+sampleToRun);
				}

			}

			RedModel testModel = new RedModel();
			testModel.setInputFileLocation(thisExample.getInputFileLocation());
			testModel.setRuleFileLocation(thisExample.getRuleFileLocation());
			testModel.setOutputFileLocation(thisExample.getOutputFileLocation());
			testModel.setDslFileLocation(thisExample.getDslFileLocation());
			testModel.setSubDirectory(thisExample.getSubDirectory());

			log.debug("Running:\n" + testModel);

			IRunner runner = RunnerFactory.getRuleRunner(testModel);
			
			// set out OutputStrategy so we can test the output later
			// this overrides the normal output
			log.info("Overriding output strategy - now will use MemoryOutputStrategy");
			MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
			runner.setDocumentOutputStrategy(outputStrategy);

			runner.callRules(testModel);

			log.debug("\n=======\n");
			//log.debug(testModel.toString());
			
			
			//check not blowing up and that we have a value back
			assertNotNull(outputStrategy.getProcessedDocument());
			
		}


	}

}
