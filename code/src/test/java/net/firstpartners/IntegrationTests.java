package net.firstpartners;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.drools.compiler.compiler.DroolsParserException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

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
	void testAllSamples() throws StreamReadException, DatabindException, IOException, NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException, InvalidFormatException, DroolsParserException {

		List<SampleData> examples = SampleDataLoader.loadSampleInformation(SampleDataLoader.SAMPLE_INFO_IN_JSON,
				appConfig);

		for (SampleData thisExample : examples) {

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
