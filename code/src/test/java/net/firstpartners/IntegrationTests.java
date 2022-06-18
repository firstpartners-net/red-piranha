package net.firstpartners;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import net.firstpartners.core.Config;
import net.firstpartners.core.MemoryOutputStrategy;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.RuleRunner;
import net.firstpartners.core.drools.RuleRunnerFactory;
import net.firstpartners.core.json.SampleData;
import net.firstpartners.core.json.SampleDataLoader;

/**
 * Test Harness to run through all the Red Piranha examples - make sure that
 * they continue working
 * 
 * @author paulf
 *
 */
@SpringBootTest
class IntegrationTests {

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
	void testSpecificExample() throws Exception {
		sampleTestRunner(1);
	}


 	@Test
	void testAllSamples() throws Exception {
		sampleTestRunner(-1);
	}

	/**
	 * Pass in Integer = null or -1 to run all tests
	 * Or single number to run single test
	 * @param sampleToRun
	 * @throws Exception
	 */
	private void sampleTestRunner(int sampleToRun) throws Exception{

		List<SampleData> examples = SampleDataLoader.loadSampleInformation(SampleDataLoader.SAMPLE_INFO_IN_JSON,
		appConfig);

		//counter
		Integer counter =0;
		log.info("Sample to run:"+sampleToRun);


		for (SampleData thisExample : examples) {

			//check if we're running in single test mode
			if(sampleToRun>0){
				counter++;
				if(counter != sampleToRun){
					log.info("Running in single sample mode - skip sample:"+counter);
					continue; //skips this iteration of the loop
				} else {
					log.info("Running single sample:"+counter);
				}

			}

			RedModel testModel = new RedModel();
			testModel.setInputFileLocation(thisExample.getInputFileLocation());
			testModel.setRuleFileLocation(thisExample.getRuleFileLocation());
			testModel.setOutputFileLocation(thisExample.getOutputFileLocation());
			testModel.setDslFileLocation(thisExample.getDslFileLocation());

			log.debug("Running:\n" + thisExample);
			RuleRunner runner = RuleRunnerFactory.getRuleRunner(testModel, appConfig);
			
			// set out OutputStrategy so we can test the output later
			// this overrides the normal output
			MemoryOutputStrategy outputStrategy = new MemoryOutputStrategy();
			runner.setOutputStrategy(outputStrategy);

			runner.callRules(testModel);

			log.debug("\n=======\n");
			log.debug(testModel.toString());
			
			
			//check not blowing up and that we have
			assertNotNull(outputStrategy.getProcessedDocument());
			
		}


	}

}
