package net.firstpartners.core.file;

import java.io.File;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.Config;

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
	 * @param directoryOnly - check for directory and not file
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static File getFileResourceUsingConfig(String resourceName, Config appConfig) throws FileNotFoundException {
		
		
		File bareFile = new File(resourceName);
		if (bareFile.exists()) {
			return bareFile;
		} else {
			log.debug("No resource:"+resourceName+ " attempting another approach?");
		}
		
		// We need config info to try backups
		if(appConfig==null) {
			throw new FileNotFoundException("1 Cannot find :"+resourceName+" no config provided");
		}
		
		String defaultDir = appConfig.getSampleBaseDirDefault();
		File defaultFile = new File(defaultDir+resourceName);
		if (defaultFile.exists()) {
			return defaultFile;
		} else {
			log.debug("2 No resource in default dir:"+defaultFile+ " attempting another approach");
		}
		
		
		String alternatDir = appConfig.getSampleBaseDirAlternate();
		File alternateFile = new File(alternatDir+resourceName);
		if (alternateFile.exists()) {
			return alternateFile;
		} else {
			log.debug("3 No resource in alternate dir:"+alternateFile);
			
			throw new FileNotFoundException("Cannot find after 3 attempts:"+resourceName);
		}
		
		
	}
	
	/**
	 * Try to load the file at resource name , in this order 1) in working directory
	 * 2) in directory as specified in Config.sampleBaseDirDefault + ResourceName 3)
	 * 3) in directory as specified in Config.sampleBaseDirAlternate + ResourceName
	 * 
	 * @param resourceName
	 * @param appConfig
	 * @param directoryOnly - check for directory and not file
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static String getDirectoryResourceUsingConfig(Config appConfig) throws FileNotFoundException {
		
		

		// We need config info to try backups - default to current working directory
		if(appConfig==null) {
			log.debug("Config is null - defaulting to working dir");
			return new File(".").getAbsolutePath();
		}
		
		String defaultDir = appConfig.getSampleBaseDirDefault();
		File defaultFile = new File(defaultDir);
		if (defaultFile.exists()) {
			return defaultDir;
		} else {
			log.debug("1 No resource in default dir:"+defaultFile+ " attempting another approach");
		}
		
		
		String alternateDir = appConfig.getSampleBaseDirAlternate();
		File alternateFile = new File(alternateDir);
		if (alternateFile.exists()) {
			return alternateDir;
		} else {
			log.debug("2 No resource in alternate dir:"+alternateDir);	
		}
		
		log.debug("BackupPlan- defaulting to working dir");
		return new File(".").getAbsolutePath();
			
		
	}


}
