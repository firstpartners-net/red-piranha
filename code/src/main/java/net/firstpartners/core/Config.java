package net.firstpartners.core;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Bean to hold Configuration from the specified property file
 *
 * @author PBrowne
 * @version $Id: $Id
 */
@Component
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
@ManagedResource
public class Config {


	//Constants
	//@TODO - consider moving to config
	//The string we use to "glue names together"
	//needs to be alphanumeric (as it will becom and Excel name, yet easy to split later)
	public static final String COMBO="_III_";

	//Constants - these will be the headings we use when outputting additional data
	public static final String ADDITIONALDATA_SOURCE ="Source";
	public static final String ADDITIONALDATA_DATE ="Extract Date";

	//How we mark cells with potential errors
    public static final String ERROR_CELL_MARKER = "error";

	//a user friendly log (containing main messages)
	public static final String USER_LOG = "SOME_USER_LOG";

	@Autowired
	@Value("${sample.Base.Directory.Alternate}")
	private String sampleBaseDirAlternate = "";

	@Autowired
	@Value("${sample.Base.Directory.Default}")
	private String sampleBaseDirDefault = "";

	@Autowired
	@Value("${SHOW_FULL_RULE_ENGINE_LOGS}")
	private boolean showFullRuleEngineLogs = false;

	@Autowired
	@Value("${script.preprocess}")
	private String preprocessScript = "";

	/**
	 * <p>
	 * Setter for the field <code>sampleBaseDirAlternate</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public void setPreprocessScript(String preProcessScript) {
		this.preprocessScript = preProcessScript;
	}

	/**
	 * <p>
	 * Getter for the field <code>sampleBaseDirAlternate</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getPreprocessScript() {
		return preprocessScript;
	}

	/**
	 * <p>
	 * Getter for the field <code>sampleBaseDirAlternate</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getSampleBaseDirAlternate() {
		return sampleBaseDirAlternate;
	}

	/**
	 * <p>
	 * Getter for the field <code>sampleBaseDirDefault</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getSampleBaseDirDefault() {
		return sampleBaseDirDefault;
	}

	/**
	 * <p>
	 * Getter for the field <code>showFullRuleEngineLogs</code>.
	 * </p>
	 *
	 * @return a boolean
	 */
	public boolean getShowFullRuleEngineLogs() {
		return showFullRuleEngineLogs;
	}

	/**
	 * <p>
	 * Setter for the field <code>sampleBaseDirAlternate</code>.
	 * </p>
	 *
	 * @param sampleBaseDirAlternate a {@link java.lang.String} object
	 */
	public void setSampleBaseDirAlternate(String sampleBaseDirAlternate) {
		this.sampleBaseDirAlternate = sampleBaseDirAlternate;
	}

	/**
	 * <p>
	 * Setter for the field <code>sampleBaseDirDefault</code>.
	 * </p>
	 *
	 * @param sampleBaseDirDefault a {@link java.lang.String} object
	 */
	public void setSampleBaseDirDefault(String sampleBaseDirDefault) {
		this.sampleBaseDirDefault = sampleBaseDirDefault;
	}

	/**
	 * <p>
	 * Setter for the field <code>showFullRuleEngineLogs</code>.
	 * </p>
	 *
	 * @param showFullRuleEngineLogs a boolean
	 */
	public void setShowFullRuleEngineLogs(boolean showFullRuleEngineLogs) {
		this.showFullRuleEngineLogs = showFullRuleEngineLogs;
	}

	/**
	 * <p>
	 * toString.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
