package net.firstpartners.core.file;

import java.io.File;

/**
 * Utilities for dealing with files
 * @author PBrowne
 *
 */
public class Utils {


	/**
	 * Delete the output file if it already exists
	 * @param fileToDelete
	 */
	public static void deleteOutputFileIfExists(String fileToDelete) {

		File fileOfInterest = new File(fileToDelete);
		if (fileOfInterest.exists()) {
			fileOfInterest.delete();
		}

	}
}
