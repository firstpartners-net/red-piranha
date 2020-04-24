package net.firstpartners.core.log;

/**
 * Marks a class as being one that we can log to
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
