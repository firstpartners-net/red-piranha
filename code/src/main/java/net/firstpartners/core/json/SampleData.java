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
 * @version $Id: $Id
 */
public class SampleData {

	private int id;
	private String baseDirectory;
	private String inputFileLocation;
	private String ruleFileLocation;
	private String outputFileLocation;
	private String dslFileLocation;
	private String sampleDescription;
	private String testingResult;



	/**
	 * <p>Getter for the field <code>id</code>.</p>
	 *
	 * @return a int
	 */
	public int getId() {
		return id;
	}

	/**
	 * <p>Setter for the field <code>id</code>.</p>
	 *
	 * @param id a int
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * <p>Getter for the field <code>baseDirectory</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getBaseDirectory() {
		return baseDirectory;
	}

	/**
	 * <p>Setter for the field <code>baseDirectory</code>.</p>
	 *
	 * @param descriptiontext a {@link java.lang.String} object
	 */
	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
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
	 * <p>Setter for the field <code>inputFileLocation</code>.</p>
	 *
	 * @param inputFileLocation a {@link java.lang.String} object
	 */
	public void setInputFileLocation(String inputFileLocation) {
		this.inputFileLocation = inputFileLocation;
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
	 * <p>Setter for the field <code>ruleFileLocation</code>.</p>
	 *
	 * @param ruleFileLocation a {@link java.lang.String} object
	 */
	public void setRuleFileLocation(String ruleFileLocation) {
		this.ruleFileLocation = ruleFileLocation;
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
	 * <p>Setter for the field <code>outputFileLocation</code>.</p>
	 *
	 * @param outputFileLocation a {@link java.lang.String} object
	 */
	public void setOutputFileLocation(String outputFileLocation) {
		this.outputFileLocation = outputFileLocation;
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
	 * <p>Getter for the field <code>sampleDescription</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getSampleDescription() {
		return sampleDescription;
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
	 * <p>Setter for the field <code>sampleDescription</code>.</p>
	 *
	 * @param sampleDescription a {@link java.lang.String} object
	 */
	public void setSampleDescription(String sampleDescription) {
		this.sampleDescription = sampleDescription;
	}


	/**
	 * <p>Getter for the field <code>testingResult</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getTestingResult() {
		return testingResult;
	}

	/**
	 * <p>Setter for the field <code>testingResult</code>.</p>
	 *
	 * @param testingResult a {@link java.lang.String} object
	 */
	public void setTestingResult(String testingResult) {
		this.testingResult = testingResult;
	}

	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * Convenience method to generate the URL Params if we run this sample
	 *
	 * @throws java.io.UnsupportedEncodingException
	 * @returns the info held in this bean as a params e.g.
	 *          ?param1=value&param2=value2 etc
	 * @return a {@link java.lang.String} object
	 */
	public String getSampleUrlParams() throws UnsupportedEncodingException {
		StringBuffer returnValue = new StringBuffer();
		
		String enc = StandardCharsets.UTF_8.toString();
		returnValue.append("?baseDirectory=" + URLEncoder.encode(this.baseDirectory, enc));
		returnValue.append("&inputFileLocation=" + URLEncoder.encode(this.inputFileLocation, enc));
		returnValue.append("&ruleFileLocation=" + URLEncoder.encode(this.ruleFileLocation, enc));
		returnValue.append("&outputFileLocation=" + URLEncoder.encode(this.outputFileLocation, enc));

		if ((this.dslFileLocation != null) && (!this.dslFileLocation.equals(""))) {
			returnValue.append("&dslFileLocation=" + URLEncoder.encode(this.dslFileLocation, enc));
		}

		return returnValue.toString();
	}

}
