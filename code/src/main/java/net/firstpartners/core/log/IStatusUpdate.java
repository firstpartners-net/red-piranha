package net.firstpartners.core.log;

/**
 * Marks a class as being one that can pass 'friendly' messages to the user
 * rather than the system level details we output to the logs.
 *
 * @author paulbrowne
 * @version $Id: $Id
 */
public interface IStatusUpdate {

	/**
	 * Log at Debug Level
	 *
	 * @param output a {@link java.lang.String} object
	 */
	public void addUIDebugMessage(String output);

	/**
	 * Log Exception
	 *
	 * @param output a {@link java.lang.String} object
	 * @param t a {@link java.lang.Throwable} object
	 */
	public void addUIWarnMessage(String output, Throwable t);

	/**
	 * Log at Info Level
	 *
	 * @param output a {@link java.lang.String} object
	 */
	public void addUIInfoMessage(String output);

	
	/**
	 * Allows us to notify the user of a snapshot post rules
	 *
	 * @param dataToSnapshotToUser a {@link java.lang.Object} object
	 */
	public void setPostRulesSnapShot(Object dataToSnapshotToUser);

	/**
	 * Allows us to notify the user of a snapshot post rules
	 *
	 * @param dataToSnapshotToUser a {@link java.lang.Object} object
	 */
	public void setPreRulesSnapShot(Object dataToSnapshotToUser);

	/**
	 * Notifies the user of percentage progress made
	 *
	 * @param percentProgressMade 0 to 100
	 */
	public void setUIProgressStatus(int percentProgressMade);
}
