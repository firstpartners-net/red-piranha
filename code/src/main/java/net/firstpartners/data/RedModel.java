package net.firstpartners.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cargo object to hold information about the rules and facts (Excel Data) we are working with
 * @author paul
 *
 */
public class RedModel {
	
	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//internal data
	private String dslFileLocation;

	private Collection<Cell> facts = new ArrayList<Cell>();

	private HashMap<String, Cell> globals = new HashMap<String, Cell>();

	private String knowledgeBaseLocation;

	private String ruleFlowFileUrl;

	private ArrayList<String> rulesLocation = new ArrayList<String>();

	private Config config;

	public RedModel() {}
	
	public RedModel(Config config) {
		this.config = config;
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

	public String getKnowledgeBaseLocation() {
		return knowledgeBaseLocation;
	}

	public String getRuleFlowFileUrl() {
		return ruleFlowFileUrl;
	}

	public String[] getRulesLocation() {
		log.debug("ruleslocation:"+rulesLocation.getClass());
		return (String[]) rulesLocation.toArray(new String[rulesLocation.size()]);
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

	public void setKnowledgeBaseLocation(String knowledgeBaseLocation) {
		this.knowledgeBaseLocation = knowledgeBaseLocation;
	}

	public void setRuleFlowFileUrl(String ruleFlowFileUrl) {
		this.ruleFlowFileUrl = ruleFlowFileUrl;
	}

	/**
	 * Asd rule location
	 * Takes string instead of array
	 * @param ruleLocation
	 */
	public void addRuleLocation(String ruleLocation) {

		this.rulesLocation.add(ruleLocation);
	}
	
	public void addRuleLocation(String[] ruleLocation) {

		if(ruleLocation!=null) {
			for (int counter=0; counter <ruleLocation.length; counter++) {
				addRuleLocation(ruleLocation[counter]);
			}
		}
		
	}
	
	public Config getConfig() {
		return this.config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}






}
