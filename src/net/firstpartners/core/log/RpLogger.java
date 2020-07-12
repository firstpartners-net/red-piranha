package net.firstpartners.core.log;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import net.firstpartners.ui.Start;

/**
 * Extend logging so that we can control logging in an exe environment where we
 * may not have full control of config
 * 
 * @author PBrowne
 */
public class RpLogger extends Logger {

	// the handle to the log file
	 private static FileAppender fileHandler = null;

	/**
	 * @param name of this Logger
	 */
	public RpLogger(String name) {

		super(name);
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
			log.addAppender(fileHandler);
		}

		return log;

	}

	/**
	 * Check if we need to force turn of of logging
	 * 
	 * @param logFileName
	 * @throws SecurityException
	 * @throws IOException
	 */
	public static void checkForceLogToFile(String logFileName) throws SecurityException, IOException {

		if (logFileName != null & logFileName !="") {
			fileHandler = new FileAppender();
			fileHandler.setName("FileLogger");
			fileHandler.setFile(logFileName);
			fileHandler.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
			fileHandler.setThreshold(Level.DEBUG);
			fileHandler.setAppend(true);
			fileHandler.activateOptions();

			// add appender to any Logger (here is root)
			Logger.getRootLogger().addAppender(fileHandler);
			
			//Log 
			Logger thisLog = getLogger(Start.class.getName());
			thisLog.debug("Forced Log to:"+logFileName);
		}

	}

}
