package net.firstpartners.data;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
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
	//public final static String EMPTY = "empty";

	// Properties autowired by spring
	//@Autowired
	//@Value("${LOG_FILE_NAME}")
	//private String forcedLogFileName;

/*	
	@Value("${FILE_INPUT}")
	private String inputFileName;

	@Autowired
	@Value("${FILE_OUTPUT}")
	private String outputFileName;

	
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
*/
	//@Autowired
	//@Value("${SHOW_FULL_RULE_ENGINE_LOGS}")
	private boolean showFullRuleEngineLogs;

	@Autowired
	//@Value("${SHOW_FULL_RULE_ENGINE_LOGS}")
	private List<String> sampleBaseDir;
	
	public List<String> getSampleBaseDir() {
		return sampleBaseDir;
	}

	public void setSampleBaseDir(List<String> sampleBaseDir) {
		this.sampleBaseDir = sampleBaseDir;
	}

	public boolean getShowFullRuleEngineLogs() {
		return showFullRuleEngineLogs;
	}

	public void setShowFullRuleEngineLogs(boolean showFullRuleEngineLogs) {
		this.showFullRuleEngineLogs = showFullRuleEngineLogs;
	}

	
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	

}
