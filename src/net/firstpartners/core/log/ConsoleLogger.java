package net.firstpartners.core.log;

import org.apache.log4j.Logger;

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
		log.debug(output);
	}
	
	public void info(String output){
		log.debug(output);
	}
	
	public void exception(String output, Throwable t){
		log.error(output,t);
		
	}


}
