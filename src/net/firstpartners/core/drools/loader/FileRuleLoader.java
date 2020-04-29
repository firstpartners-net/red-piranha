package net.firstpartners.core.drools.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.apache.log4j.Logger;

import net.firstpartners.core.log.RpLogger;

/**
 * Load rules, assume that resources are available on the local file system
 * 
 * @author paul
 * 
 */
public class FileRuleLoader extends AbstractRuleLoaderStrategy implements IRuleLoaderStrategy {


	private static final Logger log = RpLogger.getLogger(FileRuleLoader.class
			.getName());
	
	private RuleDTO ruleSource;

	/**
	 * 
	 * @param ruleSource
	 */
	public FileRuleLoader(RuleDTO ruleSource) {
		this.ruleSource = ruleSource;
	}
	
	/** 
	 * Constructor - convenience - takes single ruleLocation
	 * @param ruleLocation
	 */
	public FileRuleLoader(String ruleLocation) {
		this.ruleSource = new RuleDTO();
		this.ruleSource.setRulesLocation(ruleLocation);
	}




	/**
	 * Get a file given a file name (inc directories from current working directory)
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
		log.debug("Read file contents/n =========");
		log.debug(cache.toString());
		log.debug(" ========= /n end file contents");
		returnBytes = reader.toString().getBytes();
		log.debug("end getFileFromUrl");
		return returnBytes;


	}



	/**
	 * Get an Input Stream suitable for this class
	 */
	@Override
	InputStream getInputStream(String resource) throws IOException {

		return new FileInputStream(resource);
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
		log.debug("baseFile:"+baseFile.getAbsolutePath());

		File lookForFile = new File (fileResource);
		log.debug("Looking for File:"+lookForFile);
		log.debug("lookForFile.exists():"+lookForFile.exists());
		log.debug("lookForFile.isAbsolute():"+lookForFile.isAbsolute());
		log.debug("lookForFile.getParent():"+lookForFile.getParent());
		log.debug("lookForFile.getAbsolutePath():"+lookForFile.getAbsolutePath());

		java.io.FileReader fr = new java.io.FileReader(lookForFile);
		return fr;

	}

	/**
	 * gets the original rule source
	 * @return
	 */
	public RuleDTO getRuleSource() {
		return ruleSource;
	}

	

	public void setRuleSource(RuleDTO ruleSource) {
		this.ruleSource = ruleSource;
	}





}
