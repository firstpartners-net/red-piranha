package net.firstpartners.core.script;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.firstpartners.core.Config;
import net.firstpartners.core.file.ResourceFinder;


/**
 * Utilities for running the pre-processing script
 *
 * @author PBrowne
 * @version $Id: $Id
 */
public class PreProcessor {


	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());

	//Handle to the Groovy script engine
	private GroovyScriptEngine engine = null;

	// Handle to the appconfig
	private Config appConfig;

	/**
	 * Constructor
	 */
	public PreProcessor(Config appConfig){
		this.appConfig = appConfig;
	}

	/**
	 * Run the Specified Groovy Script
	 *
	 * @param groovyScriptName a {@link java.lang.String} name of a Groovy file to run
	 * @param xlWorkbook - an Apache POI workbook representing the file we want to preprocess
	 * @return 
	 * @throwpreprocessXlWorkbooks IOException
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public Workbook preprocessXlWorkbook (String baseDir, String groovyScriptName, Workbook xlWorkbook) throws IOException, ResourceException, ScriptException {

		if(engine==null){
			engine = new GroovyScriptEngine(new String[]{ "." });
		}

		//get a handle to the script - Groovy Engine needs file name
		try{
			File script = ResourceFinder.getFileResourceUsingConfig(baseDir+groovyScriptName, appConfig);
			
			String scriptPath =script.getAbsolutePath();
			log.debug("ScriptPath:"+scriptPath);

			//read the script contents
			String scriptContents = FileUtils.readFileToString(script, "utf-8");
			//log.debug("Script Contents\n"+scriptContents);

			//Pass the Workbook in 
			Binding binding = new Binding();
			binding.setVariable("xlWorkbook", xlWorkbook);
			
			//and call the script
			GroovyShell shell = new GroovyShell(binding);                       
			Object result = shell.evaluate(scriptContents); 	

			//check the script returns a workbook
			assert result instanceof org.apache.poi.ss.usermodel.Workbook : "Script should return type of Workbook";
			return (org.apache.poi.ss.usermodel.Workbook)result;
			
		} catch (FileNotFoundException fnfe){
			log.warn("Could not find preprocess Script - continueing without script");
		}

		//default - return the incoming workbook
		return xlWorkbook;
		
		

	}
}
