package net.firstpartners.core.security;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import net.firstpartners.TestConstants;

public class RedSecurityManagerTest {

	RedSecurityManager redSecurity = new RedSecurityManager(TestConstants.PROPERTY_FILE_OF_URL_PREFIXES, TestConstants.PROPERTY_FILE_OF_RESOURCE_SUFFIXES);
	
	//Handle to class under Test
	RedSecurityManager testManager = null;

	@Test
	public final void testPropertiesLoad() throws IOException{


		//Check that we have loaded the list of OK URL types
		assertNotNull(redSecurity.getUrlPrefixes());
		assertTrue(redSecurity.getUrlPrefixes().size()>0);

		//Check that we have loaded the list of OK package types
		assertNotNull(redSecurity.getResourceSuffixes());
		assertTrue(redSecurity.getResourceSuffixes().size()>0);

	}

	@Test
	public final void testcheckResourcePermitted() throws SecurityException, IOException{

		//These should pass
		redSecurity.checkUrl("http://localhost:.KnowledgeBase");
		redSecurity.checkUrl("http://localhost:.xls");

		redSecurity.checkUrl("http://localhost:blahblahblah.KnowledgeBase");
		redSecurity.checkUrl("http://localhost:blahblahblah.xls");

	}

	@Test
	public final void testcheckResourceNotPermitted() throws IOException{

		try {

			redSecurity.checkUrl("http://www.bbc.co.uk");
			fail("Previous Call should not have passed");

		} catch (SecurityException se){

		}

	}



	@Test
	public final void testCheckUrlLocationOk() throws SecurityException, IOException{

		//These should all pass
		redSecurity.checkUrl("http://localhost/somefile.xls");
		redSecurity.checkUrl("http://red-piranha.appspot.com/somefile.xls");
		redSecurity.checkUrl("http://red-piranha.googlecode.com/somefile.xls");


	}

	@Test
	public final void testCheckUrlLocationFail() throws IOException{

		try {

			redSecurity.checkUrl("http://red-piranha.appspot.com/somerules.ThisIsNotAnAllowedResource");
			fail("Previous Call should not have passed");

		} catch (SecurityException se){

		}

	}

}
