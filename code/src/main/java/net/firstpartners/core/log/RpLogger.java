package net.firstpartners.core.log;

import java.io.IOException;

import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.FileAppender.Builder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.firstpartners.ui.Start;

/**
 * Extend logging so that we can control logging in an 	exe environment where we
 * may not have full control of config
 * 
 * @author PBrowne
 */
public class RpLogger extends LogManager {

	// the handle to the log file
	private static FileAppender fileHandler = null;

//	/**
//	 * @param name of this Logger
//	 */
//	public RpLogger(String name) {
//
//	}

	/**
	 * Get a handle to a logger, with our additions (if set)
	 * 
	 * @param name
	 * @return java.util.logging.Logger - but configured to output in exe
	 *         environment
	 */
	public static Logger getLogger(String name) {

		Logger log = LogManager.getLogger(name);
		

//		if (fileHandler != null) {
//			log.addAppender(fileHandler);
//			log.
//		}

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
			
		
			
			FileAppender.Builder b = FileAppender.newBuilder();
			b.withFileName(logFileName);
			b.withAppend(true);
			b.setName("fileLogger");
			b.build();
			fileHandler = b.build();
			
			//Log 
			Logger thisLog = getLogger(Start.class.getName());
			thisLog.debug("Forced Log to:"+logFileName);
		}

	}

}
