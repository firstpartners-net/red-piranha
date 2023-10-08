package net.firstpartners.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import net.firstpartners.core.log.IStatusUpdate;
import net.firstpartners.data.Cell;

/**
 * Cargo object to hold information about the rules and facts (Excel Data) we
 * are working with and the results returned by the Rule Engine
 *
 * Implements IStatusUpdate to passback information to the user
 *
 * @author paul
 * @version $Id: $Id
 */
public class RedModel implements IStatusUpdate {

	private String dslFileLocation;

	private List<Cell> facts = new ArrayList<>();

	private HashMap<String, Cell> globals = new HashMap<>();

	private String subDirectory;

	private String inputFileLocation;

	private String knowledgeBaseLocation;

	// Hold messages that we want to pass back to the User
	final List<String>  messagesUI = new ArrayList<String>();

	private String outputFileLocation;

	private String postRulesSnapShotAsJson = "";

	private String preRulesSnapShotAsJson = "";

	private String ruleFileLocation;

	private String ruleFlowFileUrl;

	private String UICurrentStatus;

	/**
	 * Default constructor - needed so this remains a bean
	 */
	public RedModel() {
	}

	/**
	 * Convenience constructor - most commonly used use case
	 * @param subDirectory - the folder there all the sub files are located
	 * @param ruleFileLocation a {@link java.lang.String} object
	 * @param outputFileLocation a {@link java.lang.String} object
	 * @param inputFileLocation a {@link java.lang.String} object
	 */
	public RedModel(String inputFileLocation, String ruleFileLocation, String outputFileLocation) {

		this("",inputFileLocation,ruleFileLocation,outputFileLocation);

	}

	/**
	 * Convenience constructor - most commonly used use case
	 * @param subDirectory - the folder there all the sub files are located
	 * @param ruleFileLocation a {@link java.lang.String} object
	 * @param outputFileLocation a {@link java.lang.String} object
	 * @param inputFileLocation a {@link java.lang.String} object
	 */
	public RedModel(String subDirectory, String inputFileLocation, String ruleFileLocation, String outputFileLocation) {

		this();

		this.subDirectory = subDirectory;
		this.inputFileLocation = inputFileLocation;
		this.ruleFileLocation = ruleFileLocation;
		this.outputFileLocation = outputFileLocation;

	}

	/**
	 * <p>addFact.</p>
	 *
	 * @param fact a {@link net.firstpartners.data.Cell} object
	 */
	public void addFact(Cell fact) {

		if (facts == null) {
			facts = new ArrayList<>();
		}

		facts.add(fact);
	}

	/**
	 * <p>addFacts.</p>
	 *
	 * @param facts a {@link java.util.Collection} object
	 */
	public void addFacts(Collection<Cell> facts) {

		if (this.facts == null) {
			this.facts = new ArrayList<>();
		}

		this.facts.addAll(facts);
	}

	/**
	 * <p>addGlobal.</p>
	 *
	 * @param globalName a {@link java.lang.String} object
	 * @param global a {@link net.firstpartners.data.Cell} object
	 */
	public void addGlobal(String globalName, Cell global) {

		if (globals == null) {
			globals = new HashMap<>();
		}
		globals.put(globalName, global);
	}

	/**
	 * <p>addGlobals.</p>
	 *
	 * @param globals a {@link java.util.HashMap} object
	 */
	public void addGlobals(HashMap<String, Cell> globals) {

		if (this.globals == null) {
			this.globals = new HashMap<>();
		}
		this.globals.putAll(globals);
	}

	/** {@inheritDoc} */
	@Override
	public void addUIDebugMessage(String output) {
		messagesUI.add("DEBUG:" + output + "\n");

	}

		/** {@inheritDoc} */
	@Override
	public void debug(String output) {
		addUIDebugMessage(output);

	}

	/** {@inheritDoc} */
	@Override
	public void addUIWarnMessage(String output, Throwable t) {
		messagesUI.add("WARN:" + output + "\n");
		if(t!=null) {
			messagesUI.add(t.getMessage());	
		}
	}

	/** {@inheritDoc} */
	@Override
	public void addUIInfoMessage(String output) {
		messagesUI.add("INFO:" + output + "\n");

	}

	/** {@inheritDoc} */
	@Override
	public void info(String output) {
		addUIInfoMessage("INFO:" + output + "\n");

	}

	/**
	 * <p>Getter for the field <code>dslFileLocation</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getDslFileLocation() {
		return dslFileLocation;
	}

	/**
	 * <p>Getter for the field <code>facts</code>.</p>
	 *
	 * @return a {@link java.util.Collection} object
	 */
	public Collection<Cell> getFacts() {
		return facts;
	}

	/**
	 * Covenience method to get facts as a list
	 * @return
	 */
	public List<Cell> getFactsAsList() {
		return facts;
	}


	/**
	 * <p>Getter for the field <code>globals</code>.</p>
	 *
	 * @return a {@link java.util.HashMap} object
	 */
	public HashMap<String, Cell> getGlobals() {
		return globals;
	}

	/**
	 * <p>Getter for the field <code>inputFileLocation</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getInputFileLocation() {
		return inputFileLocation;
	}

	/**
	 * <p>Getter for the field <code>subDirectory</code>.</p>
	 * Modified to make it null safe (will return a blank string)
	 * @return a {@link java.lang.String} object
	 */
	public String getSubDirectory() {

		if(this.subDirectory==null){
			return "";
		}

		return this.subDirectory;
	}


	/**
	 * <p>Getter for the field <code>knowledgeBaseLocation</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getknowledgeBaseLocation() {
		return knowledgeBaseLocation;
	}

	/**
	 * <p>Getter for the field <code>outputFileLocation</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getOutputFileLocation() {
		return outputFileLocation;
	}

	/**
	 * <p>Getter for the field <code>postRulesSnapShotAsJson</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getPostRulesSnapShotAsJson() {
		return postRulesSnapShotAsJson;
	}

	/**
	 * <p>Getter for the field <code>preRulesSnapShotAsJson</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getPreRulesSnapShotAsJson() {
		return preRulesSnapShotAsJson;
	}

	/**
	 * <p>Getter for the field <code>ruleFileLocation</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getRuleFileLocation() {
		return ruleFileLocation;
	}

	/**
	 * <p>Getter for the field <code>ruleFlowFileUrl</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getRuleFlowFileUrl() {
		return ruleFlowFileUrl;
	}

	/**
	 * Convenience method - some code expects an array
	 *
	 * @return an array of {@link java.lang.String} objects
	 */
	public String[] getRulesFilesLocations() {

		String[] returnValues = new String[1];
		returnValues[0] = ruleFileLocation;
		return returnValues;

	}

	/**
	 * <p>getUICurrentStatus.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getUICurrentStatus() {
		return UICurrentStatus;
	}


	/**
	 * <p>getUserMessageContents.</p>
	 *
	 * @return a {@link java.util.List} object
	 */
	public List<String> getUserMessageContents() {
		return messagesUI;
	}

	/**
	 * <p>Setter for the field <code>dslFileLocation</code>.</p>
	 *
	 * @param dslFileLocation a {@link java.lang.String} object
	 */
	public void setDslFileLocation(String dslFileLocation) {
		this.dslFileLocation = dslFileLocation;
	}

	/**
	 * <p>Setter for the field <code>facts</code>.</p>
	 *
	 * @param facts an array of {@link net.firstpartners.data.Cell} objects
	 */
	public void setFacts(Cell[] facts) {
		List<Cell> factsCollection = Arrays.asList(facts);
		setFacts(factsCollection);

	}

	/**
	 * <p>Setter for the field <code>facts</code>.</p>
	 *
	 * @param facts a {@link java.util.Collection} object
	 */
	public void setFacts(Collection<Cell> facts) {
		this.facts = new ArrayList<Cell>(facts);
	}

	/**
	 * <p>Setter for the field <code>globals</code>.</p>
	 *
	 * @param globals a {@link java.util.HashMap} object
	 */
	public void setGlobals(HashMap<String, Cell> globals) {
		this.globals = globals;
	}

		/**
	 * <p>Setter for the field <code>subDirectory</code>.</p>
	 *
	 * @param subDirectory a {@link java.lang.String} object
	 */
	public void setSubDirectory(String subDirectory) {
		this.subDirectory = subDirectory;
	}


	/**
	 * <p>Setter for the field <code>inputFileLocation</code>.</p>
	 *
	 * @param inputFileLocation a {@link java.lang.String} object
	 */
	public void setInputFileLocation(String inputFileLocation) {
		this.inputFileLocation = inputFileLocation;
	}

	/**
	 * <p>Setter for the field <code>knowledgeBaseLocation</code>.</p>
	 *
	 * @param knowledgeBaseLocation a {@link java.lang.String} object
	 */
	public void setknowledgeBaseLocation(String knowledgeBaseLocation) {
		this.knowledgeBaseLocation = knowledgeBaseLocation;
	}

	/**
	 * <p>Setter for the field <code>outputFileLocation</code>.</p>
	 *
	 * @param outputFileLocation a {@link java.lang.String} object
	 */
	public void setOutputFileLocation(String outputFileLocation) {
		this.outputFileLocation = outputFileLocation;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Allows us to notify the user of a snapshot post rules
	 */
	@Override
	public void setPostRulesSnapShot(Object dataToSnapshotToUser) {

		// We want to keep a snapshot in JSON (as the object tree will be updated later
		// by the rule engine

		ObjectMapper objectMapper = new ObjectMapper();

		// Hold at class level to save time
		ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

		try {
			this.postRulesSnapShotAsJson = writer.writeValueAsString(dataToSnapshotToUser);
		} catch (JsonProcessingException e) {
			this.addUIWarnMessage("", e);

		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * Allows us to notify the user of a snapshot post rules
	 */
	@Override
	public void setPreRulesSnapShot(Object dataToSnapshotToUser) {

		ObjectMapper objectMapper = new ObjectMapper();

		// Hold at class level to save time
		ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());

		// We want to keep a snapshot in JSON (as the object tree will be updated later
		// by the rule engine
		try {
			this.preRulesSnapShotAsJson = writer.writeValueAsString(dataToSnapshotToUser);
		} catch (JsonProcessingException e) {
			this.addUIWarnMessage("", e);

		}

	}

	/**
	 * <p>Setter for the field <code>ruleFileLocation</code>.</p>
	 *
	 * @param ruleFileLocation a {@link java.lang.String} object
	 */
	public void setRuleFileLocation(String ruleFileLocation) {
		this.ruleFileLocation = ruleFileLocation;
	}

	/**
	 * <p>Setter for the field <code>ruleFlowFileUrl</code>.</p>
	 *
	 * @param ruleFlowFileUrl a {@link java.lang.String} object
	 */
	public void setRuleFlowFileUrl(String ruleFlowFileUrl) {
		this.ruleFlowFileUrl = ruleFlowFileUrl;
	}

	/**
	 * <p>setUICurrentStatus.</p>
	 *
	 * @param currentStatus a {@link java.lang.String} object
	 */
	public void setUICurrentStatus(String currentStatus) {
		this.UICurrentStatus = currentStatus;
	}


	/** {@inheritDoc} */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * <p>addUIDebugMessage.</p>
	 *
	 * @param messages a {@link java.util.List} object
	 */
	public void addUIDebugMessage(List<String> messages) {
		this.messagesUI.addAll(messages);
		
	}

	/**
	 * <p>addUIWarnMessage.</p>
	 *
	 * @param messages a {@link java.util.List} object
	 */
	public void addUIWarnMessage(List<String> messages) {
		this.messagesUI.addAll(messages);
		
	}

}
