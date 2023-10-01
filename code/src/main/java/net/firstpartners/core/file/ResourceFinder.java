package net.firstpartners.core.file;

import java.io.File;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.Config;

/**
 * Find and load resources
 *
 * @author paulf
 * @version $Id: $Id
 */
public class ResourceFinder {
	
	
	// Handle to the logger
	private static Logger log = LoggerFactory.getLogger(ResourceFinder.class);


	/**
	 * Try to load the file at resource name , in this order 1) in working directory
	 * 2) in directory as specified in Config.sampleBaseDirDefault + ResourceName 3)
	 * 3) in directory as specified in Config.sampleBaseDirAlternate + ResourceName
	 *
	 * Functions the same as the overloaded method, but with an empty baseDirectory
	 *
	 * @param resourceName a {@link java.lang.String} object
	 * @param appConfig a {@link net.firstpartners.core.Config} object
	 * @throws java.io.FileNotFoundException
	 * @return a {@link java.io.File} object
	 */
	public static File getFileResourceUsingConfig(String resourceName, Config appConfig) throws FileNotFoundException {
		return getFileResourceUsingConfig("", resourceName,  appConfig);
	}


	/**
	 * Try to load the file at resource name , in this order 1) in working directory
	 * 2) in directory as specified in Config.sampleBaseDirDefault + ResourceName 3)
	 * 3) in directory as specified in Config.sampleBaseDirAlternate + ResourceName
	 * @param baseDir that will get pre-pended to the file names we are looking for
	 * @param resourceName a {@link java.lang.String} object
	 * @param appConfig a {@link net.firstpartners.core.Config} object
	 * @throws java.io.FileNotFoundException
	 * @return a {@link java.io.File} object
	 */
	public static File getFileResourceUsingConfig(String baseDir,String resourceName, Config appConfig) throws FileNotFoundException {
		
		String seekName= resourceName;

		if(baseDir!=null && baseDir!=""){
			seekName = baseDir+resourceName;
			
		}

		log.debug("Seeking:"+resourceName);

		File bareFile = new File(seekName);
		
		File workingDir = new File(".");
		if (bareFile.exists()) {
			return bareFile;
		} else {
			log.debug("1 No resource:"+seekName+ " found in "+workingDir.getAbsolutePath()+" attempting another approach?");
		}
		
		// We need config info to try backups
		if(appConfig==null) {
			throw new FileNotFoundException("1 Cannot find :"+seekName+" and no config provided to try other locations");
		}
		
		
		String defaultDir = appConfig.getSampleBaseDirDefault();
		File defaultFile = new File(defaultDir+seekName);
		if (defaultFile.exists()) {
			log.debug("2 Found Resource:"+defaultFile);
			return defaultFile;
		} else {
			log.debug("2 No resource in default location:"+defaultFile.getAbsolutePath()+ " attempting another approach");
		}
		
		
		String alternateDir = appConfig.getSampleBaseDirAlternate();
		File alternateFile = new File(alternateDir+seekName);
		if (alternateFile.exists()) {
			log.debug("2 Found Resource:"+alternateFile);
			return alternateFile;
		} else {
			log.debug("3 No resource in alternate dir:"+alternateFile);
			
			throw new FileNotFoundException("Cannot find:"+seekName +" in any of 3 locations - default , "+defaultDir+" , "+alternateDir);
		}
		
		
	}
	
	/**
	 * Try to load the file at resource name , in this order 1) in working directory
	 * 2) in directory as specified in Config.sampleBaseDirDefault + ResourceName 3)
	 * 3) in directory as specified in Config.sampleBaseDirAlternate + ResourceName
	 *
	 * @param appConfig a {@link net.firstpartners.core.Config} object
	 * @throws java.io.FileNotFoundException
	 * @return a {@link java.lang.String} object
	 */
	public static String getDirectoryResourceUsingConfig(Config appConfig) throws FileNotFoundException {
		
		

		// We need config info to try backups - default to current working directory
		if(appConfig==null) {
			log.debug("Config is null - defaulting to working dir");
			
			String workingDir = new File(".").getAbsolutePath();

			//handle bug of . being appended to this path name
			if(workingDir.endsWith(".")){
				workingDir =workingDir.replaceAll(".", "");
			}

			return workingDir;


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
			log.debug("Using Alternate Dir:"+alternateDir);
			return alternateDir;
		} else {
			log.debug("2 No resource in alternate dir:"+alternateDir);	
		}
		
		log.debug("BackupPlan- defaulting to base dir");
		return new File(".").getAbsolutePath();
			
		
	}


}
