package net.firstpartners.core.log;

/**
 * Marks a class as being one that can pass 'friendly' messages to the user 
 * rather than the system level details we output to the logs.
 * 
 * @author paulbrowne
 *
 */
public interface IStatusUpdate {

	/**
	 * Log at Debug Level
	 * 
	 * @param output
	 */
	public void debug(String output);

	/**
	 * Log at Info Level
	 * 
	 * @param output
	 */
	public void info(String output);

	/**
	 * Log Exception
	 * 
	 * @param output
	 * @param t
	 */
	public void exception(String output, Throwable t);

	/**
	 * Allows us to notify the user of a snapshot post rules
	 * 
	 * @param message
	 */
	public void setPreRulesSnapShot(Object dataToSnapshotToUser);

	/**
	 * Allows us to notify the user of a snapshot post rules
	 * 
	 * @param message
	 */
	public void setPostRulesSnapShot(Object dataToSnapshotToUser);

	/**
	 * Notifies the user of percentage progress made
	 * 
	 * @param percentProgressMade 0 to 100
	 */
	public void notifyProgress(int percentProgressMade);
}
