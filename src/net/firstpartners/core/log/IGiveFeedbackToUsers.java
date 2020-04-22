package net.firstpartners.core.log;

/**
 * Marks as class as being able to display a pre and post rules snapshot of Data as feedback to users
 * @author PBrowne
 *
 */
public interface IGiveFeedbackToUsers {

	/**
	 * Allows us to notify the user of a snapshot post rules
	 * @param message
	 */
	void showPreRulesSnapShot(String dataToSnapshotToUser);

	/**
	 * Allows us to notify the user of a snapshot post rules
	 * @param message
	 */
	void showPostRulesSnapShot(String dataToSnapshotToUser);
	
	/**
	 * Notifies the user of percentage progress made
	 * @param percentProgressMade 0 to 100
	 */
	public void notifyProgress(int percentProgressMade);
	
	/**
	 * Notifies us if we want Provide a visual notification that an Exception has occured
	 */
	public void notifyExceptionOccured();

	

}