package net.firstpartners.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import net.firstpartners.drools.data.RuleSource;

/**
 * Simple gatekeeper to ensure we only load resources from known safe locations
 * 
 * @author paul
 * 
 */
public class RedSecurityManager {

	private static final Logger log = Logger.getLogger(RedSecurityManager.class
			.getName());

	public static String PROPERTY_FILE_OF_URL_PREFIXES = "net/firstpartners/security/UrlWhiteList.properties";
	public static String PROPERTY_FILE_OF_RESOURCE_SUFFIXES = "net/firstpartners/security/ResourceWhiteList.properties";

	private static Map resourceSuffixes;

	private static Map urlPrefixes;

	/**
	 * Checks if the Url is Ok or not
	 * 
	 * @param url
	 * @throws IOException
	 */
	public static void checkUrl(String url) throws SecurityException,
	IOException {

		if((url==null)||(url.equals(""))){
			throw new SecurityException("Url to check should not be null or empty");
		}

		checkUrlPrefix(url);
		//	checkResourceSuffix(url);

	}

	private static void checkUrlPrefix(String url) throws SecurityException,
	IOException {

		Object thisKey;
		Map urlPreFix = getUrlPrefixes();
		Iterator loopList = urlPreFix.keySet().iterator();

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

	private static void checkResourceSuffix(String url)
	throws SecurityException, IOException {

		Object thisKey;
		Map suffix = getResourceSuffixes();
		Iterator loopList = suffix.keySet().iterator();

		while (loopList.hasNext()) {
			thisKey = loopList.next();
			log.fine("Comparing Key:" + thisKey + " to:" + url);

			if (url.endsWith(thisKey.toString())) {

				// We have found a match
				return;
			}

		}

		// If we get to this point, we have found no matches
		throw new SecurityException("Suffix (resourceType) not on Whitelist:"
				+ url);

	}

	public static Map getResourceSuffixes() throws IOException {

		if (resourceSuffixes == null) {
			resourceSuffixes = readResourceSuffixes();
		}
		return resourceSuffixes;
	}

	public static Map getUrlPrefixes() throws IOException {

		// check and load propers
		if (urlPrefixes == null) {
			urlPrefixes = readUrlPrefixes();
		}

		return urlPrefixes;
	}

	static Map readResourceSuffixes() throws IOException {

		// Read properties file.
		InputStream resourceAsStream = RedSecurityManager.class
		.getClassLoader().getResourceAsStream(
				PROPERTY_FILE_OF_RESOURCE_SUFFIXES);

		Properties properties = new Properties();
		properties.load(resourceAsStream);

		return properties;
	}

	static Map readUrlPrefixes() throws IOException {

		// Read properties file.
		InputStream resourceAsStream = RedSecurityManager.class
		.getClassLoader().getResourceAsStream(
				PROPERTY_FILE_OF_URL_PREFIXES);

		Properties properties = new Properties();
		properties.load(resourceAsStream);

		return properties;
	}

	public static void checkUrl(RuleSource ruleSource) throws SecurityException, IOException {

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

	private static void checkUrl(String[] rulesLocation) throws SecurityException, IOException {

		if(rulesLocation!=null){
			for (String element : rulesLocation) {
				checkUrl(element);
			}
		}

	}

}
