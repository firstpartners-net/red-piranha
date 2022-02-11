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
 *
 */
@Component
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
@ManagedResource
public class Config {

	@Autowired
	@Value("${sample.Base.Directory.Alternate}")
	private String sampleBaseDirAlternate="";

	@Autowired
	@Value("${sample.Base.Directory.Default}")
	private String sampleBaseDirDefault="";
	
	@Autowired
	@Value("${SHOW_FULL_RULE_ENGINE_LOGS}")
	private boolean showFullRuleEngineLogs = false;

	public String getSampleBaseDirAlternate() {
		return sampleBaseDirAlternate;
	}

	public String getSampleBaseDirDefault() {
		return sampleBaseDirDefault;
	}

	public boolean getShowFullRuleEngineLogs() {
		return showFullRuleEngineLogs;
	}

	public void setSampleBaseDirAlternate(String sampleBaseDirAlternate) {
		this.sampleBaseDirAlternate = sampleBaseDirAlternate;
	}

	public void setSampleBaseDirDefault(String sampleBaseDirDefault) {
		this.sampleBaseDirDefault = sampleBaseDirDefault;
	}

	public void setShowFullRuleEngineLogs(boolean showFullRuleEngineLogs) {
		this.showFullRuleEngineLogs = showFullRuleEngineLogs;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
