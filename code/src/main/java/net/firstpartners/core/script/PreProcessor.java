package net.firstpartners.core.script;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.lang.Binding;

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

	/*
	 * Convenicence method to check if preProcess file exists
	 * @param baseDir
	 * @param preprocessScript
	 */
	public boolean isPreProcessFileExists(String baseDir,String preProcessScript){

		if(baseDir==null||preProcessScript==null){
			return false;
		}

		//See if we can get a handle to this script file
		File script;
		try {
			script = ResourceFinder.getFileResourceUsingConfig(baseDir+preProcessScript, appConfig);
		} catch (FileNotFoundException e) {
			// No file?
			return false;
		}

		//Otherwirse return script
		return script.exists();

	}

	/**
	 * Run the Specified Groovy Script
	 *
	 * @param groovyScriptName a {@link java.lang.String} name of a Groovy file to run
	 * @param xlWorkbook - an Apache POI workbook representing the file we want to preprocess
	 * @throws IOException
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public Workbook preprocessXlWorkbook (String baseDir, String groovyScriptName, Workbook xlWorkbook) throws IOException, ResourceException, ScriptException {

		if(engine==null){
			engine = new GroovyScriptEngine(new String[]{ "." });
		}

		//get a handle to the script - Groovy Engine needs file name
		File script = ResourceFinder.getFileResourceUsingConfig(baseDir+groovyScriptName, appConfig);
		String scriptPath =script.getAbsolutePath();

		//Pass the Workbook in and call the script
		Binding binding = new Binding();
		binding.setVariable("xlWorkbook", xlWorkbook);
		Object result = engine.run(scriptPath, binding); 
		

		//check the script returns a workbook
		assert result instanceof org.apache.poi.ss.usermodel.Workbook : "Script should return type of Workbook";

		return (org.apache.poi.ss.usermodel.Workbook)result;
		

	}
}
