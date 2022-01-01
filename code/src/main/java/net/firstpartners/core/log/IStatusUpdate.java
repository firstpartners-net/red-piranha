package net.firstpartners.core.log;

/**
 * Marks a class as being one that we can log to. Focus is on messages going
 * back to the user
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
	public void showPreRulesSnapShot(Object dataToSnapshotToUser);

	/**
	 * Allows us to notify the user of a snapshot post rules
	 * 
	 * @param message
	 */
	public void showPostRulesSnapShot(Object dataToSnapshotToUser);

	/**
	 * Notifies the user of percentage progress made
	 * 
	 * @param percentProgressMade 0 to 100
	 */
	public void notifyProgress(int percentProgressMade);
}
