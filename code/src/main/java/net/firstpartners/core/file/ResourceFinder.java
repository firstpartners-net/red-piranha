package net.firstpartners.core.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import net.firstpartners.core.Config;
import net.firstpartners.core.drools.ClassAndLocation;

/**
 * Find and load resources
 *
 * @author paulf
 * @version $Id: $Id
 */
@Component
public class ResourceFinder {

	// Handle to the logger
	private static Logger log = LoggerFactory.getLogger(ResourceFinder.class);

	//handle to our config
	@Autowired
	private Config appConfig0;
	private static Config appConfig; // done this way to allow for post constrcut


	/**
	 * Called by Spring post construction
	 */
	@PostConstruct     
  	private void initStaticConfig () {
		
     	appConfig = this.appConfig0;
		//log.debug("Setting post construct app config to:"+appConfig.toString());
  	}

	/**
	 * Allows setting of config (overrides) eg. for testing
	 * @param Config inconfig to replace existing
	 */
	public static void setConfig(Config inConfig){
		appConfig = inConfig;
	}

	/**
	 * Mostly Spring will well the app config on the components
	 * This method is here as an interim refactoring method
	 */
	public static Config getConfig(){
		return appConfig;
	}


	/**
	 * Try to load the file at resource name , in this order 1) in working directory
	 * 2) in directory as specified in Config.sampleBaseDirDefault + ResourceName 3)
	 * 3) in directory as specified in Config.sampleBaseDirAlternate + ResourceName
	 *
	 * Functions the same as the overloaded method, but with an empty baseDirectory
	 *
	 * @param location a {@link ClassAndLocation} , possibly containing our location
	 * @return a {@link java.io.File} object
	 * @throws IOException
	 */
	public static File getFileResourceUsingConfig(ClassAndLocation location)  throws IOException {

		if(location.fileLocation!=null){
			log.debug("Using cached handle to location:"+location.fileLocation);
			return location.fileLocation;
		}

		//if not pre set, try finding
		log.debug("Searching for file using string filename:"+location.locationText);

		return getFileResourceUsingConfig("", location.locationText);
	}

	/**
	 * Try to load the file at resource name , in this order 1) in working directory
	 * 2) in directory as specified in Config.sampleBaseDirDefault + ResourceName 3)
	 * 3) in directory as specified in Config.sampleBaseDirAlternate + ResourceName
	 *
	 * Functions the same as the overloaded method, but with an empty baseDirectory
	 *
	 * @param resourceName a {@link java.lang.String} object
	 * @return a {@link java.io.File} object
	 * @throws IOException
	 */
	public static File getFileResourceUsingConfig(String resourceName ) throws IOException {


		//Check incoming params
		assert appConfig!=null: "Config should not be null";

		return getFileResourceUsingConfig("", resourceName);
	}

	/**
	 * Try to load the file at resource name , in this order 1) in working directory
	 * 2) in directory as specified in Config.sampleBaseDirDefault + ResourceName 3)
	 * 3) in directory as specified in Config.sampleBaseDirAlternate + ResourceName
	 * 
	 * @param baseDir      that will get pre-pended to the file names we are looking
	 *                     for
	 * @param resourceName a {@link java.lang.String} object
	 * @return a {@link java.io.File} object
	 * @throws IOException
	 */
	public static File getFileResourceUsingConfig(String baseDir, String resourceName)
			throws IOException {


		//Check incoming params
		assert appConfig!=null: "Config should not be null";
		
		String seekName = resourceName;

		if (baseDir != null && baseDir != "") {
			seekName = baseDir + resourceName;

		}

		log.debug("Seeking:" + resourceName);
		log.debug("App Base directory:"+new File("").getAbsolutePath());

		File bareFile = new File(seekName);

		//File workingDir = new File(".");
		if (bareFile.exists()) {
			log.debug("1 File- Found file via App BaseDirectory:"+bareFile);
			return bareFile;
		} else {
			log.debug("1 File- App Base Directory - No resource:" + bareFile
					+ " attempting another approach?");
		}


		String defaultDir = appConfig.getSampleBaseDirDefault();
		File defaultFile = new File(defaultDir + seekName);
		if (defaultFile.exists()) {
			log.debug("2 File - Found Resource:" + defaultFile);
			return defaultFile;
		} else {
			log.debug("2 File- No resource in default location:" + defaultFile.getAbsolutePath()
					+ " attempting another approach");
		}

		String alternateDir = appConfig.getSampleBaseDirAlternate();
		File alternateFile = new File(alternateDir + seekName);
		//log.debug("isFile:"+alternateFile.isFile());
		if (alternateFile.exists()) {
			log.debug("3 File - Found Resource:" + alternateFile);
			return alternateFile;
		} else {
			log.debug("3 File - No resource in alternate dir:" + alternateFile+":end");

		}

		File fullFileDir = new File(resourceName);
		if (fullFileDir.exists()) {
			log.debug("4 File - Found via Full File Path:"+fullFileDir);
			return fullFileDir;
		} else {
			log.debug("4 File - No resource in full file path: " + fullFileDir.getAbsolutePath());
		}

		
		Resource springResource = new ClassPathResource(seekName);
		File springFile = springResource.getFile();
		if (springFile.exists()) {
			log.debug("5 File - Found via Spring Classloader:"+springFile);
			return springFile;
		} else {
			log.debug("5 File - No resource in Spring Loader file path: " + springFile.getAbsolutePath());
		}






		throw new FileNotFoundException("Cannot find:" + seekName + " in any of 4 locations - default , "
					+ defaultDir + " , " + alternateDir+" or full file path "+fullFileDir); 

	}

	/**
	 * Try to load the file at resource name , in this order 1) in working directory
	 * 2) in directory as specified in Config.sampleBaseDirDefault + ResourceName 3)
	 * 3) in directory as specified in Config.sampleBaseDirAlternate + ResourceName
	 *
	 * @throws java.io.FileNotFoundException
	 * @return a {@link java.lang.String} object
	 */
	public static String getBaseDirOfAllSamples() throws FileNotFoundException {

		//empty directory should match to first or alternate base dir
		return getDirectoryUsingConfig("");

	}

	/**
	 * Try to load the file at resource name , in this order 1) in working directory
	 * 2) in directory as specified in Config.sampleBaseDirDefault + ResourceName 3)
	 * 3) in directory as specified in Config.sampleBaseDirAlternate + ResourceName
	 *
	 * @throws java.io.FileNotFoundException
	 * @return a {@link java.lang.String} object
	 */
	public static String getDirectoryUsingConfig(String directoryName) {

		
		//Check incoming params
		assert appConfig!=null: "Config should not be null";

		//attempt based on config - primary
		String primaryDirName = appConfig.getSampleBaseDirDefault()+directoryName;
		File primaryDir = new File(primaryDirName);
		if (primaryDir.exists()) {
			log.debug("1 Dir Using Primary Config Directory:" + primaryDir);
			return primaryDirName;
		} else {
			log.debug("1 No Directory in primary config location:" + primaryDirName);
		}

		//attempt based on config - alternatve
		String alternateDirName= appConfig.getSampleBaseDirAlternate()+directoryName;
		File alternateDirLocation = new File(alternateDirName);
		if (alternateDirLocation.exists()) {
			log.debug("2 Dir Using Alternate Config Directory:" + alternateDirName);
			return alternateDirName;
		} else {
			log.debug("2 No Directory in config alternate dir:" + alternateDirName);
		}

		//try to see if it is in the straight location
		File defaultDir = new File(directoryName);
		if (defaultDir.exists()) {
			log.debug("3 Dir Matched on directory location:"+directoryName);
			return directoryName;
		} else {
			log.debug("3 No Directory in location:" + directoryName + " attempting another approach");
		}

		//Default to current Dir
		log.debug("4 Dir BackupPlan- defaulting to application working dir");
		return new File("").getAbsolutePath();

	}

	/**
	 * Get the list of files in a directory
	 * 
	 * @param configuration   - to help us locate the directory
	 * @param directoryToFind
	 * @return
	 */
	public static List<File> getDirectoryFilesUsingConfig( String directoryToFind) {

		
		//Check incoming params
		assert appConfig!=null: "Config should not be null";

		//Setup return values
		List<File> foundFiles = new ArrayList<File>();

		//Now try to find the directory using this information
		log.debug("Looking for Directory:" + directoryToFind);

		
		// Try to locate using config
		String foundDir = getDirectoryUsingConfig(directoryToFind);

		if (foundDir == null || foundDir.equals("")) {
			log.debug("No directory found - returning empty list");
			return foundFiles; // empty list
		}

		// Creating a File object for directory
		File directoryPath = new File(foundDir);
		log.debug("Iterating ovoer files in Found Dir:" + directoryPath);

		// List of all files and directories
		File[] filesList = directoryPath.listFiles();
		if (filesList != null) {

			log.debug("Number of files found:" + filesList.length);

			Collections.addAll(foundFiles, filesList);
		} else {
			log.info("No files found in directory");
		}

		return foundFiles;
	}

	/**
	 * Delete the output file if it already exists
	 *
	 * @param fileToDelete a {@link java.lang.String} object
	 */
	public static void deleteOutputFileIfExists(String fileToDelete) {

		File fileOfInterest;
		try {
			fileOfInterest = getFileResourceUsingConfig(fileToDelete);

			 if (fileOfInterest.exists()) {
			fileOfInterest.delete();
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.debug("file:"+fileToDelete+" already gone -ok");
		}


	}


}