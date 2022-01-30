package net.firstpartners.core.json;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Simple Bean to hold our sample data
 * @author paulf
 *
 */
public class SampleData {

	
	private int id;
	private String inputFileLocation;
	private String ruleFileLocation;
	private String outputFileLocation;
	private String dslFileLocation;
	private String descriptiontext;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInputFileLocation() {
		return inputFileLocation;
	}
	public void setInputFileLocation(String inputFileLocation) {
		this.inputFileLocation = inputFileLocation;
	}
	public String getRuleFileLocation() {
		return ruleFileLocation;
	}
	public void setRuleFileLocation(String ruleFileLocation) {
		this.ruleFileLocation = ruleFileLocation;
	}
	public String getOutputFileLocation() {
		return outputFileLocation;
	}
	public void setOutputFileLocation(String outputFileLocation) {
		this.outputFileLocation = outputFileLocation;
	}
	public String getDslFileLocation() {
		return dslFileLocation;
	}
	public void setDslFileLocation(String dslFileLocation) {
		this.dslFileLocation = dslFileLocation;
	}
	public String getDescriptiontext() {
		return descriptiontext;
	}
	public void setDescriptiontext(String descriptiontext) {
		this.descriptiontext = descriptiontext;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	
}
