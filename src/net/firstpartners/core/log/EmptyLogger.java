package net.firstpartners.core.log;

/**
 * A Logging Adaptor that does Nothing 
 * But avoids Nullpointers in the rulebase
 *
 */
public class EmptyLogger implements ILogger {


	
	public EmptyLogger(){
		
	}
	
	public void debug(String output){
	}
	
	public void info(String output){
	}
	
	public void exception(String output, Throwable t){
	}


}
