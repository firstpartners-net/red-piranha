package net.firstpartners.core.security;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

public class RedSecurityManagerTest {

	//Handle to class under Test
	RedSecurityManager testManager = null;

	@Test
	public final void testPropertiesLoad() throws IOException{


		//Check that we have loaded the list of OK URL types
		assertNotNull(RedSecurityManager.getUrlPrefixes());
		assertTrue(RedSecurityManager.getUrlPrefixes().size()>0);

		//Check that we have loaded the list of OK package types
		assertNotNull(RedSecurityManager.getResourceSuffixes());
		assertTrue(RedSecurityManager.getResourceSuffixes().size()>0);

	}

	@Test
	public final void testcheckResourcePermitted() throws SecurityException, IOException{

		//These should pass
		RedSecurityManager.checkUrl("http://localhost:.KnowledgeBase");
		RedSecurityManager.checkUrl("http://localhost:.xls");

		RedSecurityManager.checkUrl("http://localhost:blahblahblah.KnowledgeBase");
		RedSecurityManager.checkUrl("http://localhost:blahblahblah.xls");

	}

	@Test
	public final void testcheckResourceNotPermitted() throws IOException{

		try {

			RedSecurityManager.checkUrl("http://www.bbc.co.uk");
			fail("Previous Call should not have passed");

		} catch (SecurityException se){

		}

	}



	@Test
	public final void testCheckUrlLocationOk() throws SecurityException, IOException{

		//These should all pass
		RedSecurityManager.checkUrl("http://localhost/somefile.xls");
		RedSecurityManager.checkUrl("http://red-piranha.appspot.com/somefile.xls");
		RedSecurityManager.checkUrl("http://red-piranha.googlecode.com/somefile.xls");


	}

	@Test
	public final void testCheckUrlLocationFail() throws IOException{

		try {

			RedSecurityManager.checkUrl("http://red-piranha.appspot.com/somerules.ThisIsNotAnAllowedResource");
			fail("Previous Call should not have passed");

		} catch (SecurityException se){

		}

	}

}
