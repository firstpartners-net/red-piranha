package net.firstpartners.core.log;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * An Adaptor that allows us and hold Logs in Memory While Useful for Testing ,
 * the main aim is to hold the information long enough to display back to the
 * user on a web page
 * 
 * @author paulbrowne
 *
 */
public class BufferStatusUpdate extends AbstractStatusUpdate implements IStatusUpdate {

	// We also allow this to be configured to log to memory
	final StringBuffer buffer = new StringBuffer();

	IStatusUpdate nestedLogger = null;
	

	private String postRulesSnapShotAsJson = "";

	private String preRulesSnapShotAsJson = "";

	//Hold at class level to save time
	ObjectWriter writer;
	
	/**
	 * Default constructor
	 */
	public BufferStatusUpdate() {

		ObjectMapper objectMapper = new ObjectMapper();
		writer = objectMapper.writer(new DefaultPrettyPrinter());
		
	}

	public BufferStatusUpdate(IStatusUpdate nestedLogger) {
		this.nestedLogger = nestedLogger;
	}

	public void debug(String output) {
		buffer.append("DEBUG:" + output + "\n");

		if (nestedLogger != null) {
			nestedLogger.debug(output);
		}
	}

	public void exception(String output, Throwable t) {
		buffer.append("EXCEPTION:" + output + "\n");
		buffer.append(t.fillInStackTrace());

		if (nestedLogger != null) {
			nestedLogger.exception(output, t);
		}
	}

	public String getContents() {
		return buffer.toString();
	}

	public IStatusUpdate getNestedLogger() {
		return nestedLogger;
	}

	public String getPostRulesSnapShotAsJson() {
		return postRulesSnapShotAsJson;
	}

	public String getPreRulesSnapShotAsJson() {
		return preRulesSnapShotAsJson;
	}

	public void info(String output) {
		buffer.append("INFO:" + output + "\n");

		if (nestedLogger != null) {
			nestedLogger.info(output);
		}
	}

	public void setNestedLogger(IStatusUpdate nestedLogger) {
		this.nestedLogger = nestedLogger;
	}

	/**
	 * Allows us to notify the user of a snapshot post rules
	 * @param message
	 */
	public void setPostRulesSnapShot(Object dataToSnapshotToUser) {
		
		// We want to keep a snapshot in JSON (as the object tree will be updated later
		// by the rule engine
		
		
		try {
			this.postRulesSnapShotAsJson = writer.writeValueAsString(dataToSnapshotToUser);
		} catch (JsonProcessingException e) {
			this.exception("", e);
		
		}

		// Also log (if set) in the super class
		super.setPostRulesSnapShot(dataToSnapshotToUser.toString());
		
	}
	
	/**
	 * Allows us to notify the user of a snapshot post rules
	 * 
	 * @param message
	 */
	@Override
	public void setPreRulesSnapShot(Object dataToSnapshotToUser) {

		// We want to keep a snapshot in JSON (as the object tree will be updated later
		// by the rule engine
		try {
			this.preRulesSnapShotAsJson = writer.writeValueAsString(dataToSnapshotToUser);
		} catch (JsonProcessingException e) {
			this.exception("", e);
		
		}

		// Also log (if set) in the super class
		super.setPreRulesSnapShot(dataToSnapshotToUser.toString());
	}
}
