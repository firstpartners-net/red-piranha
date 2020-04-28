package net.firstpartners.core.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

import net.firstpartners.core.drools.loader.RuleDTO;
import net.firstpartners.core.log.RpLogger;

/**
 * Simple gatekeeper to ensure we only load resources from known safe locations
 * 
 * @author paul
 * 
 */
public class RedSecurityManager {

	private static final Logger log = RpLogger.getLogger(RedSecurityManager.class
			.getName());

	private String propertyFileOfUrlPrexfixes;

	private String propertyFileOfResourceSuffixes;

	/**
	 * Create an instance of the manager, configure what to whitelist
	 * @param propertyFileOfUrlPrexfixes
	 * @param propertyFileOfResourceSuffixes
	 */
	public RedSecurityManager (String propertyFileOfUrlPrexfixes, String propertyFileOfResourceSuffixes ) {
		
		this.propertyFileOfUrlPrexfixes = propertyFileOfUrlPrexfixes ;
		
		this.propertyFileOfResourceSuffixes = propertyFileOfResourceSuffixes;
	}
	
	//Cache
	private static Map<?, ?> resourceSuffixes;

	//Cache
	private static Map<?, ?> urlPrefixes;

	/**
	 * Checks if the Url is Ok or not
	 * 
	 * @param url
	 * @throws IOException
	 */
	public void checkUrl(String url) throws SecurityException,
	IOException {

		if((url==null)||(url.equals(""))){
			throw new SecurityException("Url to check should not be null or empty");
		}

		checkUrlPrefix(url);
		//	checkResourceSuffix(url);

	}

	private void checkUrlPrefix(String url) throws SecurityException,
	IOException {

		Object thisKey;
		Map<?, ?> urlPreFix = getUrlPrefixes();
		Iterator<?> loopList = urlPreFix.keySet().iterator();

		while (loopList.hasNext()) {
			thisKey = loopList.next();
			if (url.indexOf(thisKey.toString()) > -1) {
				// We have found a match
				return;
			}

		}

		// If we get to this point, we have found no matches
		throw new SecurityException("Url not on Whitelist:" + url);

	}

	void checkResourceSuffix(String url)
	throws SecurityException, IOException {

		Object thisKey;
		Map<?, ?> suffix = getResourceSuffixes();
		Iterator<?> loopList = suffix.keySet().iterator();

		while (loopList.hasNext()) {
			thisKey = loopList.next();
			log.debug("Comparing Key:" + thisKey + " to:" + url);

			if (url.endsWith(thisKey.toString())) {

				// We have found a match
				return;
			}

		}

		// If we get to this point, we have found no matches
		throw new SecurityException("Suffix (resourceType) not on Whitelist:"
				+ url);

	}

	Map<?, ?> getResourceSuffixes() throws IOException {

		if (resourceSuffixes == null) {
			resourceSuffixes = readResourceSuffixes();
		}
		return resourceSuffixes;
	}

	 Map<?, ?> getUrlPrefixes() throws IOException {

		// check and load properties
		if (urlPrefixes == null) {
			urlPrefixes = readUrlPrefixes();
		}

		return urlPrefixes;
	}

	 Map<?, ?> readResourceSuffixes() throws IOException {

		// Read properties file.
		InputStream resourceAsStream = RedSecurityManager.class
		.getClassLoader().getResourceAsStream(
				this.propertyFileOfResourceSuffixes);

		Properties properties = new Properties();
		properties.load(resourceAsStream);

		return properties;
	}

	Map<?, ?> readUrlPrefixes() throws IOException {

		// Read properties file.
		InputStream resourceAsStream = RedSecurityManager.class
		.getClassLoader().getResourceAsStream(
				this.propertyFileOfUrlPrexfixes);

		Properties properties = new Properties();
		properties.load(resourceAsStream);

		return properties;
	}

	void checkUrl(RuleSource ruleSource) throws SecurityException, IOException {

		//Check all the components listed in the ruleSource - if there are not null or empty (which they are allowed to be)
		if(ruleSource.getDslFileLocation()!=null){
			checkUrl(ruleSource.getDslFileLocation());
		}

		if(ruleSource.getKnowledgeBaseLocation()!=null){
			checkUrl(ruleSource.getKnowledgeBaseLocation());
		}

		if(ruleSource.getRuleFlowFileUrl()!=null){
			checkUrl(ruleSource.getRuleFlowFileUrl());
		}

		if(ruleSource.getRulesLocation()!=null){
			checkUrl(ruleSource.getRulesLocation());
		}


	}

	void checkUrl(String[] rulesLocation) throws SecurityException, IOException {

		if(rulesLocation!=null){
			for (String element : rulesLocation) {
				checkUrl(element);
			}
		}

	}

}
