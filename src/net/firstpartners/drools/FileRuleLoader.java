package net.firstpartners.drools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.logging.Logger;

/**
 * Load rules, assume that resources are
 * 
 * @author paul
 * 
 */
public class FileRuleLoader extends AbstractRuleLoader implements IRuleLoader {


	private static final Logger log = Logger.getLogger(FileRuleLoader.class
			.getName());

	public FileRuleLoader() {
	}



	/**
	 * Get a file given a file name
	 * 
	 * @param fileResource
	 * @return
	 * @throws IOException
	 */
	@Override
	byte[] getFile(String fileResource) throws IOException {

		String line;
		StringBuffer cache = new StringBuffer();
		byte[] returnBytes = null;

		BufferedReader reader = new BufferedReader(getReader(fileResource));

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
	Reader getReader(String fileResource) throws IOException {

		File baseFile= new File(".");
		log.info("baseFile:"+baseFile.getAbsolutePath());

		File lookForFile = new File (fileResource);
		log.info("Looking for File:"+lookForFile);
		log.info("lookForFile.exists():"+lookForFile.exists());
		log.info("lookForFile.isAbsolute():"+lookForFile.isAbsolute());
		log.info("lookForFile.getParent():"+lookForFile.getParent());
		log.info("lookForFile.getAbsolutePath():"+lookForFile.getAbsolutePath());

		java.io.FileReader fr = new java.io.FileReader(lookForFile);
		return fr;

	}



	@Override
	InputStream getInputStream(String resource) throws IOException {

		return new FileInputStream(resource);
	}





}
