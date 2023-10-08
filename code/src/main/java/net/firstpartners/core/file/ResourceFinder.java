package net.firstpartners.core.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import net.firstpartners.core.Config;

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


	@PostConstruct     
  	  private void initStaticConfig () {
		log.debug("Setting post construct app config");
     	appConfig = this.appConfig0;
  	}
	/**
	 * Try to load the file at resource name , in this order 1) in working directory
	 * 2) in directory as specified in Config.sampleBaseDirDefault + ResourceName 3)
	 * 3) in directory as specified in Config.sampleBaseDirAlternate + ResourceName
	 *
	 * Functions the same as the overloaded method, but with an empty baseDirectory
	 *
	 * @param resourceName a {@link java.lang.String} object
	 * @param appConfig    a {@link net.firstpartners.core.Config} object
	 * @throws java.io.FileNotFoundException
	 * @return a {@link java.io.File} object
	 */
	public static File getFileResourceUsingConfig(String resourceName, Config appConfig) throws FileNotFoundException {

		//Check incoming params
		assert appConfig!=null: "Config should not be null";

		return getFileResourceUsingConfig("", resourceName, appConfig);
	}

	/**
	 * Try to load the file at resource name , in this order 1) in working directory
	 * 2) in directory as specified in Config.sampleBaseDirDefault + ResourceName 3)
	 * 3) in directory as specified in Config.sampleBaseDirAlternate + ResourceName
	 * 
	 * @param baseDir      that will get pre-pended to the file names we are looking
	 *                     for
	 * @param resourceName a {@link java.lang.String} object
	 * @param appConfig    a {@link net.firstpartners.core.Config} object
	 * @throws java.io.FileNotFoundException
	 * @return a {@link java.io.File} object
	 */
	public static File getFileResourceUsingConfig(String baseDir, String resourceName, Config appConfig)
			throws FileNotFoundException {

		//Check incoming params
		assert appConfig!=null: "Config should not be null";
		
		String seekName = resourceName;

		if (baseDir != null && baseDir != "") {
			seekName = baseDir + resourceName;

		}

		log.debug("Seeking:" + resourceName);

		File bareFile = new File(seekName);

		File workingDir = new File(".");
		if (bareFile.exists()) {
			return bareFile;
		} else {
			log.debug("1 No resource:" + seekName + " found in " + workingDir.getAbsolutePath()
					+ " attempting another approach?");
		}

		// We need config info to try backups
		if (appConfig == null) {
			throw new FileNotFoundException(
					"1 Cannot find :" + seekName + " and no config provided to try other locations");
		}

		String defaultDir = appConfig.getSampleBaseDirDefault();
		File defaultFile = new File(defaultDir + seekName);
		if (defaultFile.exists()) {
			log.debug("2 Found Resource:" + defaultFile);
			return defaultFile;
		} else {
			log.debug("2 No resource in default location:" + defaultFile.getAbsolutePath()
					+ " attempting another approach");
		}

		String alternateDir = appConfig.getSampleBaseDirAlternate();
		File alternateFile = new File(alternateDir + seekName);
		if (alternateFile.exists()) {
			log.debug("2 Found Resource:" + alternateFile);
			return alternateFile;
		} else {
			log.debug("3 No resource in alternate dir:" + alternateFile);

			throw new FileNotFoundException("Cannot find:" + seekName + " in any of 3 locations - default , "
					+ defaultDir + " , " + alternateDir);
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

		//Check incoming params
		assert appConfig!=null: "Config should not be null";

		String directoryName = appConfig.getSampleBaseDirDefault();

		return getDirectoryResourceUsingConfig(appConfig,directoryName);

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
	public static String getDirectoryResourceUsingConfig(Config appConfig, String directoryName) {

		//Check incoming params
		assert appConfig!=null: "Config should not be null";

		// We need config info to try backups - default to current working directory
		if (appConfig == null) {
			log.debug("Config is null - defaulting to working dir");

			String workingDir = new File(".").getAbsolutePath();

			// handle bug of . being appended to this path name
			if (workingDir.endsWith(".")) {
				workingDir = workingDir.replaceAll(".", "");
			}

			return workingDir;

		} else {
			log.debug("AppConfig is not null");
		}

		// String defaultDir =
		File defaultFile = new File(directoryName);
		if (defaultFile.exists()) {
			return directoryName;
		} else {
			log.debug("1 No resource in default dir:" + defaultFile + " attempting another approach");
		}

		String alternateDir = appConfig.getSampleBaseDirAlternate();
		File alternateFile = new File(alternateDir);
		if (alternateFile.exists()) {
			log.debug("Using Alternate Dir:" + alternateDir);
			return alternateDir;
		} else {
			log.debug("2 No resource in alternate dir:" + alternateDir);
		}

		log.debug("3 BackupPlan- defaulting to base dir");
		return new File(".").getAbsolutePath();

	}

	/**
	 * Get the list of files in a directory
	 * 
	 * @param configuration   - to help us locate the directory
	 * @param directoryToFind
	 * @return
	 */
	public static List<File> getFilesInDirUsingConfig(Config appConfig, String directoryToFind) {

		log.debug("Looking for Directory:" + directoryToFind);

		List<File> foundFiles = new ArrayList<File>();

		// Try to locate using config
		String foundDir = getDirectoryResourceUsingConfig(appConfig, directoryToFind);

		if (foundDir == null || foundDir.equals("")) {

			// attempt to use direct pass in value
			foundDir = directoryToFind; // revert to previous value
			log.debug("Reverting to Original Directory:" + foundDir);

		} else {
			log.debug("Looking for Modified Directory:" + foundDir);
		}

		// Creating a File object for directory
		File directoryPath = new File(foundDir);
		log.debug("Found Dir:" + directoryPath);

		// List of all files and directories
		File[] filesList = directoryPath.listFiles();
		if (filesList != null) {

			log.debug("Number of files found:" + filesList.length);

			// for (int i = 0; i < filesList.length; i++) {
			// log.debug(filesList[i].getAbsolutePath());
			// }

			// adding elements of array to arrayList.

			Collections.addAll(foundFiles, filesList);
		} else {
			log.info("No files found in directory");
		}

		return foundFiles;
	}


}