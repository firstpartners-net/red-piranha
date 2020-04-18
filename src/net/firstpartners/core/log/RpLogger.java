/**
 * 
 */
package net.firstpartners.core.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Extender logging so that we can control logging in an exe environment
 * 
 * @author PBrowne
 */
public class RpLogger extends Logger {

	// the name of the log file
	private static FileHandler fileHandler = null;

	/**
	 * @param name
	 * @param resourceBundleName
	 */
	public RpLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
	}

	/**
	 * Get a handle to a logger, with our additions (if set)
	 * 
	 * @param name
	 * @return java.util.logging.Logger - but configured to output in exe
	 *         environment
	 */
	public static Logger getLogger(String name) {

		Logger log = Logger.getLogger(name);

		if (fileHandler != null) {

			log.setLevel(Level.FINE);
			fileHandler.setFormatter(new SimpleFormatter());
			log.addHandler(fileHandler);
		}

		return log;

	}
	

	/**
	 * Check if we need to force turn of of logging
	 * 
	 * @param prop - looking for FORCE_LOGGING key
	 * @return true - if
	 * @throws IOException
	 * @throws SecurityException
	 */
	public static void checkForceLogToFile(Object logFileName) throws SecurityException, IOException {

		if (logFileName != null) {
			fileHandler = new FileHandler(logFileName.toString(), true);
			Logger tmpLog = getLogger(RpLogger.class.getName());
			tmpLog.warning("RP Config Forcing Logging to:" + logFileName);

		}

	}


	
	
}
