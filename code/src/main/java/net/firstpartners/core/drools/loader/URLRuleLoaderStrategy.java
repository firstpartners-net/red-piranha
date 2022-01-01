package net.firstpartners.core.drools.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Load rules from a Web URL
 * 
 * @author paul
 * 
 */
public class URLRuleLoaderStrategy extends AbstractRuleLoaderStrategy {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public URLRuleLoaderStrategy() {
	}


	/**
	 * Get a file from a String URL - takes advantage of the App engine ability
	 * to fetch a url
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	@Override
	byte[] getFile(String url) throws IOException {
		return getFileFromUrl(new URL(url));
	}

	/**
	 * Get a file from a String URL - takes advantage of the App engine ability
	 * to fetch a url
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private byte[] getFileFromUrl(URL url) throws IOException {

		String line;
		StringBuffer cache = new StringBuffer();
		byte[] returnBytes = null;

		BufferedReader reader = new BufferedReader(getReader(url));


		while ((line = reader.readLine()) != null) {
			cache.append(line);
		}
		reader.close();
		log.debug("Read file contents/n =========");
		log.debug(cache.toString());
		log.debug(" ========= /n end file contents");
		returnBytes = reader.toString().getBytes();
		log.debug("end getFileFromUrl");
		return returnBytes;
	}

	/**
	 * Get a file from a String URL - takes advantage of the App engine ability
	 * to fetch a url
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	@Override
	Reader getReader(String url) throws IOException {
		return getReader(new URL(url));
	}

	/**
	 * Get a Reader from a String URL - takes advantage of the App engine
	 * ability to fetch a url
	 * 
	 * Not file safe - you need to remember to close the reader
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private Reader getReader(URL url) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(url
				.openStream()));


		return reader;
	}


	@Override
	InputStream getInputStream(String resource) throws IOException {

		URL resourceUrl = new URL(resource);
		return resourceUrl.openStream();

	}

	/**
	 * gets the original rule source
	 * @return
	 */
	public RuleConfig getRuleSource() {
		return null;
	}




}
