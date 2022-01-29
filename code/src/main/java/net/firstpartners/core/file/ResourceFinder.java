package net.firstpartners.core.file;

import java.io.File;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.data.Config;

/**
 * Find and load resources
 * @author paulf
 *
 */
public class ResourceFinder {
	
	
	// Handle to the logger
	private static Logger log = LoggerFactory.getLogger(ResourceFinder.class);


	/**
	 * Try to load the file at resource name , in this order 1) in working directory
	 * 2) in directory as specified in Config.sampleBaseDirDefault + ResourceName 3)
	 * 3) in directory as specified in Config.sampleBaseDirAlternate + ResourceName
	 * 
	 * @param resourceName
	 * @param appConfig
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static File getResourceUsingConfig(String resourceName, Config appConfig) throws FileNotFoundException {
		
		File bareFile = new File(resourceName);
		if (bareFile.exists()) {
			return bareFile;
		} else {
			log.debug("No resource:"+resourceName+ " attempting another approach");
		}
		
		String defaultDir = appConfig.getSampleBaseDirDefault();
		File defaultFile = new File(defaultDir+resourceName);
		if (defaultFile.exists()) {
			return defaultFile;
		} else {
			log.debug("No resource in default dir:"+defaultFile+ " attempting another approach");
		}
		
		
		String alternatDir = appConfig.getSampleBaseDirAlternate();
		File alternateFile = new File(alternatDir+resourceName);
		if (alternateFile.exists()) {
			return alternateFile;
		} else {
			log.debug("No resource in alternate dir:"+alternateFile);
			
			throw new FileNotFoundException("Cannot find after 3 attempts:"+resourceName);
		}
		
		
	}


}
