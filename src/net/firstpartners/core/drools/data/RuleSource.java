package net.firstpartners.core.drools.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import net.firstpartners.core.spreadsheet.Cell;

/**
 * Cargo object to hold information about the type of rules that we are working with
 * @author paul
 *
 */
public class RuleSource {

	private String dslFileLocation;

	private Collection<Cell> facts = new ArrayList<Cell>();

	private HashMap<String, Cell> globals = new HashMap<String, Cell> ();

	private String knowledgeBaseLocation;

	private String ruleFlowFileUrl;


	private String[] rulesLocation;

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
		return rulesLocation;
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
	 * Convenience method for setting rule location
	 * Takes string instead of array
	 * @param ruleLocation
	 */
	public void setRulesLocation(String ruleLocation) {

		String[] tmpArray = new String[1];
		tmpArray[0] = ruleLocation;
		rulesLocation = tmpArray;
	}

	public void setRulesLocation(String[] rulesLocation) {
		this.rulesLocation = rulesLocation;
	}






}
