package net.firstpartners.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * Bean to hold Configuration from the specified property file
 * 
 * @author PBrowne
 *
 */
@Component
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
@ManagedResource
public class Config {

	
	// Constant to denote empty values
	public final static String EMPTY = "empty";

	// Properties autowired by spring
	@Value("${FILE_INPUT}")
	private String inputFileName;

	@Autowired
	@Value("${FILE_OUTPUT}")
	private String outputFileName;

	@Autowired
	@Value("${LOG_FILE_NAME}")
	private String forcedLogFileName;

	@Autowired
	@Value("${DSL ?:empty}")
	private String dslName;

	@Autowired
	@Value("${RULE1}")
	private String rule1;

	@Autowired
	@Value("${RULE2 ?:empty}")
	private String rule2;

	@Autowired
	@Value("${RULE3 ?:empty}")
	private String rule3;

	@Autowired
	@Value("${SHOW_FULL_RULE_ENGINE_LOGS}")
	private boolean showFullRuleEngineLogs;

	public boolean getShowFullRuleEngineLogs() {
		return showFullRuleEngineLogs;
	}

	public void setShowFullRuleEngineLogs(boolean showFullRuleEngineLogs) {
		this.showFullRuleEngineLogs = showFullRuleEngineLogs;
	}

	public String getRule1() {
		return rule1;
	}

	public void setRule1(String rule1) {
		this.rule1 = rule1;
	}

	public String getRule2() {
		return rule2;
	}

	public void setRule2(String rule2) {
		this.rule2 = rule2;
	}

	public String getRule3() {
		return rule3;
	}

	public void setRule3(String rule3) {
		this.rule3 = rule3;
	}


	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public String getDslName() {

		return dslName;
	}

	public void setDslName(String dslName) {
		this.dslName = dslName;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	

}
