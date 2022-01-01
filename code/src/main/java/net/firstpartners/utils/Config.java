package net.firstpartners.utils;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import net.firstpartners.core.drools.loader.RuleConfig;

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
	
	//Logger if needed
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
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
	@Value("${DSL}")
	private String dslName;
	
	@Autowired
	@Value("${RULE1}")
	private String rule1;
	
	@Autowired
	@Value("${RULE2 ?: 'empty'}")
	private String rule2;
	
	@Autowired
	@Value("${RULE3 ?: 'empty'}")
	private String rule3;
	
	@Autowired
	@Value("${EXCEL_LOG_SHEET_NAME}")
	private String excelLogSheetName;
	

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


	public String getExcelLogSheetName() {
		return excelLogSheetName;
	}


	public void setExcelLogSheetName(String excelLogSheetName) {
		this.excelLogSheetName = excelLogSheetName;
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


	/**
	 * Get the rule file names from the command line
	 * 
	 * @return a rule source object containing the drl file locations, and all the other relevant information
	 * 
	 */
	public RuleConfig getRuleConfig() {

		ArrayList<String> ruleFileLocations = new ArrayList<String>();
		if (rule1!=null) {
			ruleFileLocations.add(rule1);
		}
		
		if (rule2!=null) {
			ruleFileLocations.add(rule2);
		}
		
		if (rule3!=null) {
			ruleFileLocations.add(rule3);
		}
		

		// Set this as a RuleSource Object
		String[] tmpArr = new String[ruleFileLocations.size()];
		RuleConfig ruleSource = new RuleConfig();
		ruleSource.setRulesLocation(ruleFileLocations.toArray(tmpArr));

		//update other config
		ruleSource.setDslFileLocation(getDslName());
		
		return ruleSource;
	}
	

	/**
	 * Validate the required Config file
	 */
	void validateConfig() {

		// Check for required Keys
			assert rule1 != null : "Please make sure the config file contains a value for Rule 1";
			assert inputFileName != null : "Please make sure the config file contains a value for the Input file";
			assert outputFileName != null : "Please make sure the config file contains a value for the Output file";


		if (getInputFileName().equalsIgnoreCase(getOutputFileName())) {
			log.warn("Stopping - Input and output files should not be the same");
			throw new IllegalArgumentException("Input and output files should not be the same - we don't want to overwrite our original data");
		}
	}

	
	public String toString() {
		  return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
