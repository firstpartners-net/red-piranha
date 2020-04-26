package net.firstpartners.core.log;

/**
 * Marks a class as being one that we can log to. Focus is on messages going back to the user
 * 
 * @author paulbrowne
 *
 */
public interface ILogger {

	/**
	 * Log at Debug Level
	 * @param output
	 */
	public void debug(String output);

	/**
	 * Log at Info Level
	 * @param output
	 */
	public void info(String output);

	/**
	 * Log Exception
	 * @param output
	 * @param t
	 */
	public void exception(String output, Throwable t);

}
