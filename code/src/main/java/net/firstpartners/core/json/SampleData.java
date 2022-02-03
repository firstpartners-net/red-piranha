package net.firstpartners.core.json;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Simple Bean to hold our sample data
 * 
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
	private String testingResult;

	public String getTestingResult() {
		return testingResult;
	}

	public void setTestingResult(String testingResult) {
		this.testingResult = testingResult;
	}

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

	/**
	 * Convenience method to generate the URL Params if we run this sample
	 * @throws UnsupportedEncodingException 
	 * 
	 * @returns the info held in this bean as a params e.g.
	 *          ?param1=value&param2=value2 etc
	 */
	public String getSampleUrlParams() throws UnsupportedEncodingException {
		StringBuffer returnValue = new StringBuffer();
		
		String enc = StandardCharsets.UTF_8.toString();

		returnValue.append("?inputFileLocation=" + URLEncoder.encode(this.inputFileLocation, enc));
		returnValue.append("&ruleFileLocation=" + URLEncoder.encode(this.ruleFileLocation, enc));
		returnValue.append("&outputFileLocation=" + URLEncoder.encode(this.outputFileLocation, enc));

		if ((this.dslFileLocation != null) && (!this.dslFileLocation.equals(""))) {
			returnValue.append("&dslFileLocation=" + URLEncoder.encode(this.dslFileLocation, enc));
		}

		return returnValue.toString();
	}

}
