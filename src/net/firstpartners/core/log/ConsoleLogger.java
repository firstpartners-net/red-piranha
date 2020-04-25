package net.firstpartners.core.log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An Adaptor that allows us to log to the consoles
 *
 */
public class ConsoleLogger implements ILogger {

	
	// Logger
	private static final Logger log = RpLogger.getLogger(ConsoleLogger.class.getName());

	
	public ConsoleLogger(){
		
	}
	
	public void debug(String output){
		log.finest(output);
	}
	
	public void info(String output){
		log.fine(output);
	}
	
	public void exception(String output, Throwable t){
		log.log(Level.SEVERE, output,t);
	}


}
