package net.firstpartners.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Marks as class as being able to display a pre and post rules snapshot of Data as feedback to users.
 * Often this is used as to update the status to the user.
 * 
 * It differs from a log as we are more interested in the last message
 * 
 * @author PBrowne
 *
 */
public abstract class AbstractStatusUpdate implements IStatusUpdate {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private String currentStatus="";
	
	
	

	public String getCurrentStatus() {
		return currentStatus;
	}


	public void updateCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}


	/**
	 * Allows us to notify the user of a snapshot post rules
	 * @param message
	 */
	public void setPreRulesSnapShot(Object dataToSnapshotToUser) {
		log.info(dataToSnapshotToUser.toString());
	}
	

	/**
	 * Allows us to notify the user of a snapshot post rules
	 * @param message
	 */
	public void setPostRulesSnapShot(Object dataToSnapshotToUser) {
		log.info(dataToSnapshotToUser.toString());
	}
	
	/**
	 * Notifies the user of percentage progress made
	 * @param percentProgressMade 0 to 100
	 */
	public void notifyProgress(int percentProgressMade) {
		
		assert percentProgressMade>=0;
		String str = "I";
		String repeated = str.repeat(percentProgressMade);
		
		log.info(repeated);
	}
	
	/**
	 * Notifies us if we want Provide a visual notification that an Exception has occured
	 */
	public void notifyExceptionOccured() {
		log.warn("EXCEPTION!");
	}
	
	

}