package net.firstpartners.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import net.firstpartners.data.Cell;

/**
 * Cargo object to hold information about the rules and facts (Excel Data) we are working with
 * and the results returned by the Rule Engine
 * @author paul
 *
 */
public class RedModel {
	
	
	//internal data
	private String dslFileLocation;

	private Collection<Cell> facts = new ArrayList<Cell>();

	private HashMap<String, Cell> globals = new HashMap<String, Cell>();

	private String inputFileLocation;

	private String knowledgeBaseLocation;

	private String outputFileLocation;

	private String ruleFileLocation;

	private String ruleFlowFileUrl;
	
	/**
	 * Default constructor
	 */
	public RedModel() {}
	
	/**
	 * Convenience constructor - most commonly used use case
	 * @param inputfileLocation
	 * @param ruleFileLocation
	 * @param outputFileLocation
	 */
	public RedModel(String inputFileLocation, String ruleFileLocation, String outputFileLocation) {
		
		this.inputFileLocation=inputFileLocation;
		this.ruleFileLocation = ruleFileLocation;
		this.outputFileLocation = outputFileLocation;
		
	}
	
	
	public void addFact(Cell fact) {

		if(facts ==null){
			facts = new ArrayList<Cell>();
		}

		facts.add(fact);
	}
	
	public void addFacts(Collection<Cell> facts) {

		if(this.facts ==null){
			this.facts = new ArrayList<Cell>();
		}

		this.facts.addAll(facts);
	}

	
	public void addGlobal(String globalName, Cell global) {

		if(globals==null){
			globals = new HashMap<String, Cell> ();
		}
		globals.put(globalName,global);
	}

	public void addGlobals(HashMap<String, Cell> globals) {

		if(this.globals==null){
			this.globals = new HashMap<String, Cell> ();
		}
		this.globals.putAll(globals);
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


	public String getRuleFileLocation() {
		return ruleFileLocation;
	}
	
	/**
	 * Convenience method - some code expects an array
	 * @return
	 */
	public String[] getRulesFilesLocations() {
		
		String[] returnValues = new String [1];
		returnValues[0] = ruleFileLocation;
		return returnValues;
		
	}

	public String getRuleFlowFileUrl() {
		return ruleFlowFileUrl;
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

	
	public void setRuleFileLocation(String ruleFileLocation) {
		this.ruleFileLocation = ruleFileLocation;
	}

	public void setRuleFlowFileUrl(String ruleFlowFileUrl) {
		this.ruleFlowFileUrl = ruleFlowFileUrl;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}





}
