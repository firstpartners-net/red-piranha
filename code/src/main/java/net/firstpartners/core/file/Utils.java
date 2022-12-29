package net.firstpartners.core.file;

import java.io.File;

/**
 * Utilities for dealing with files
 *
 * @author PBrowne
 * @version $Id: $Id
 */
public class Utils {


	/**
	 * Delete the output file if it already exists
	 *
	 * @param fileToDelete a {@link java.lang.String} object
	 */
	public static void deleteOutputFileIfExists(String fileToDelete) {

		File fileOfInterest = new File(fileToDelete);
		if (fileOfInterest.exists()) {
			fileOfInterest.delete();
		}

	}
}
