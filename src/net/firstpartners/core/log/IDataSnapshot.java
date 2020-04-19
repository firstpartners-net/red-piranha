package net.firstpartners.core.log;

/**
 * Marks as class as being able to display a pre and post rules snapshot of Data
 * @author PBrowne
 *
 */
public interface IDataSnapshot {

	/**
	 * Allows us to Log to the GUI a snapshot pre rules
	 * 
	 * @param message
	 */
	void showPreRulesSnapShot(Object dataToSnapshotToUser);

	/**
	 * Allows us to Log to the GUI a snapshot pre rules
	 * 
	 * @param message
	 */
	void showPostRulesSnapShot(Object dataToSnapshotToUser);

}