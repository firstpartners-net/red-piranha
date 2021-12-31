package net.firstpartners.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An Adaptor that allows us to log to the consoles
 *
 */
public class ConsoleLogger implements ILogger {

	
	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	
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
