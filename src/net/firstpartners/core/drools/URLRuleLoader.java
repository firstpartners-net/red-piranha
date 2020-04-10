package net.firstpartners.core.drools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Load rules
 * 
 * @author paul
 * 
 */
public class URLRuleLoader extends AbstractRuleLoader {

	private static final Logger log = Logger.getLogger(URLRuleLoader.class
			.getName());

	public URLRuleLoader() {
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
		log.info("Read file contents/n =========");
		log.info(cache.toString());
		log.info(" ========= /n end file contents");
		returnBytes = reader.toString().getBytes();
		log.info("end getFileFromUrl");
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





}
