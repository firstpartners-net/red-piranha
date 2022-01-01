package net.firstpartners.core;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.data.Config;
import net.firstpartners.data.RedModel;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class RedModelFactoryTest {

	// Logger if needed
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// handle for our config
	@Autowired
	Config appConfig;

	
	@Test
	public final void testAutoConfig() {
		assertTrue(appConfig.getOutputFileName()!=null);
		log.info("Output file name"+appConfig.getOutputFileName());
		assertTrue(appConfig.getOutputFileName().indexOf("FILE_OUTPUT")!=0);
	}
	
	@Test
	public final void testReadRulesFiles() {

		RedModel myModel = RedModelFactory.getFreshRedModelUsingConfiguration(appConfig);

		Assert.assertNotNull(myModel);
		Assert.assertEquals(myModel.getRulesLocation().length, 1);

	}
	
}
