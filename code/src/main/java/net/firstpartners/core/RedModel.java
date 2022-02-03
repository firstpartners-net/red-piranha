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
 *
 *
 */
public class RedModel implements IStatusUpdate {

	private String currentStatus;

	// internal data
	private String dslFileLocation;

	private Collection<Cell> facts = new ArrayList<>();

	private HashMap<String, Cell> globals = new HashMap<>();

	private String inputFileLocation;

	private String knowledgeBaseLocation;

	// Hold messages that we want to pass back to the User
	final StringBuffer messageUIBuffer = new StringBuffer();

	private String outputFileLocation;

	private String postRulesSnapShotAsJson = "";

	private String preRulesSnapShotAsJson = "";

	private String ruleFileLocation;

	private String ruleFlowFileUrl;

	private int uIProgressStatus;

	/**
	 * Default constructor - needed so this remains a bean
	 */
	public RedModel() {
	}

	/**
	 * Convenience constructor - most commonly used use case
	 * 
	 * @param inputfileLocation
	 * @param ruleFileLocation
	 * @param outputFileLocation
	 */
	public RedModel(String inputFileLocation, String ruleFileLocation, String outputFileLocation) {

		this();

		this.inputFileLocation = inputFileLocation;
		this.ruleFileLocation = ruleFileLocation;
		this.outputFileLocation = outputFileLocation;

	}

	public void addFact(Cell fact) {

		if (facts == null) {
			facts = new ArrayList<>();
		}

		facts.add(fact);
	}

	public void addFacts(Collection<Cell> facts) {

		if (this.facts == null) {
			this.facts = new ArrayList<>();
		}

		this.facts.addAll(facts);
	}

	public void addGlobal(String globalName, Cell global) {

		if (globals == null) {
			globals = new HashMap<>();
		}
		globals.put(globalName, global);
	}

	public void addGlobals(HashMap<String, Cell> globals) {

		if (this.globals == null) {
			this.globals = new HashMap<>();
		}
		this.globals.putAll(globals);
	}

	@Override
	public void addUIDebugMessage(String output) {
		messageUIBuffer.append("DEBUG:" + output + "\n");

	}

	@Override
	public void addUIExceptionMessage(String output, Throwable t) {
		messageUIBuffer.append("EXCEPTION:" + output + "\n");
		messageUIBuffer.append(t.fillInStackTrace());

	}

	@Override
	public void addUIInfoMessage(String output) {
		messageUIBuffer.append("INFO:" + output + "\n");

	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public String getDslFileLocation() {
		return dslFileLocation;
	}

	public Collection<Cell> getFacts() {
		return facts;
	}

	public HashMap<String, Cell> getGlobals() {
		return globals;
	}

	public String getInputFileLocation() {
		return inputFileLocation;
	}

	public String getKnowledgeBaseLocation() {
		return knowledgeBaseLocation;
	}

	public String getOutputFileLocation() {
		return outputFileLocation;
	}

	public String getPostRulesSnapShotAsJson() {
		return postRulesSnapShotAsJson;
	}

	public String getPreRulesSnapShotAsJson() {
		return preRulesSnapShotAsJson;
	}

	public String getRuleFileLocation() {
		return ruleFileLocation;
	}

	public String getRuleFlowFileUrl() {
		return ruleFlowFileUrl;
	}

	/**
	 * Convenience method - some code expects an array
	 * 
	 * @return
	 */
	public String[] getRulesFilesLocations() {

		String[] returnValues = new String[1];
		returnValues[0] = ruleFileLocation;
		return returnValues;

	}

	public int getUIProgressStatus() {
		return uIProgressStatus;
	}

	public String getUserMessageContents() {
		return messageUIBuffer.toString();
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public void setDslFileLocation(String dslFileLocation) {
		this.dslFileLocation = dslFileLocation;
	}

	public void setFacts(Cell[] facts) {
		List<Cell> factsCollection = Arrays.asList(facts);
		setFacts(factsCollection);

	}

	public void setFacts(Collection<Cell> facts) {
		this.facts = facts;
	}

	public void setGlobals(HashMap<String, Cell> globals) {
		this.globals = globals;
	}

	public void setInputFileLocation(String inputFileLocation) {
		this.inputFileLocation = inputFileLocation;
	}

	public void setKnowledgeBaseLocation(String knowledgeBaseLocation) {
		this.knowledgeBaseLocation = knowledgeBaseLocation;
	}

	public void setOutputFileLocation(String outputFileLocation) {
		this.outputFileLocation = outputFileLocation;
	}

	/**
	 * Allows us to notify the user of a snapshot post rules
	 * 
	 * @param message
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
			this.addUIExceptionMessage("", e);

		}

	}

	/**
	 * Allows us to notify the user of a snapshot post rules
	 *
	 * @param message
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
			this.addUIExceptionMessage("", e);

		}

	}

	public void setRuleFileLocation(String ruleFileLocation) {
		this.ruleFileLocation = ruleFileLocation;
	}

	public void setRuleFlowFileUrl(String ruleFlowFileUrl) {
		this.ruleFlowFileUrl = ruleFlowFileUrl;
	}

	/**
	 * Notifies the user of percentage progress made
	 * 
	 * @param percentProgressMade 0 to 100
	 */
	public void setUIProgressStatus(int uIProgressStatus) {
		this.uIProgressStatus = uIProgressStatus;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
