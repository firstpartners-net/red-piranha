package net.firstpartners.drools.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Cargo object to hold information about the type of rules that we are working with
 * @author paul
 *
 */
public class RuleSource {

	public String getKnowledgeBaseLocation() {
		return knowledgeBaseLocation;
	}

	public void setKnowledgeBaseLocation(String knowledgeBaseLocation) {
		this.knowledgeBaseLocation = knowledgeBaseLocation;
	}

	public String[] getRulesLocation() {
		return rulesLocation;
	}

	public void setRulesLocation(String[] rulesLocation) {
		this.rulesLocation = rulesLocation;
	}

	/**
	 * Convenience method for setting rule location
	 * Takes string instead of array
	 * @param ruleLocation
	 */
	public void setRulesLocation(String ruleLocation) {

		String[] tmpArray = new String[1];
		tmpArray[0] = ruleLocation;
		rulesLocation = tmpArray;
	}


	public String getDslFileLocation() {
		return dslFileLocation;
	}

	public void setDslFileLocation(String dslFileLocation) {
		this.dslFileLocation = dslFileLocation;
	}

	public Collection<Object> getFacts() {
		return facts;
	}

	public void addFact(Object fact) {

		if(facts ==null){
			facts = new ArrayList<Object>();
		}

		facts.add(fact);
	}

	public void addFacts(Collection<Object> facts) {

		if(this.facts ==null){
			this.facts = new ArrayList<Object>();
		}

		this.facts.addAll(facts);
	}

	public void setFacts(Collection<Object> facts) {
		this.facts = facts;
	}


	public void setFacts(Object[] facts) {
		List<Object> factsCollection = Arrays.asList(facts);
		setFacts(factsCollection);

	}

	public HashMap<String, Object> getGlobals() {
		return globals;
	}

	public void addGlobal(String globalName, Object global) {

		if(globals==null){
			globals = new HashMap<String, Object> ();
		}
		globals.put(globalName,global);
	}

	public void addGlobals(HashMap<String, Object> globals) {

		if(this.globals==null){
			this.globals = new HashMap<String, Object> ();
		}
		this.globals.putAll(globals);
	}

	public void setGlobals(HashMap<String, Object> globals) {
		this.globals = globals;
	}


	public String getRuleFlowFileUrl() {
		return ruleFlowFileUrl;
	}

	public void setRuleFlowFileUrl(String ruleFlowFileUrl) {
		this.ruleFlowFileUrl = ruleFlowFileUrl;
	}

	private String knowledgeBaseLocation;

	private String[] rulesLocation;

	private String dslFileLocation;

	private Collection<Object> facts = new ArrayList<Object>();

	private HashMap<String, Object> globals = new HashMap<String, Object> ();

	private String ruleFlowFileUrl;






}
