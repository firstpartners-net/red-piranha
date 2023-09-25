package net.firstpartners.core.script;


import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.lang.Binding;

import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;


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

	/**
	 * Run the Specified Groovy Script
	 *
	 * @param groovyScriptName a {@link java.lang.String} name of a Groovy file to run
	 * @param xlWorkbook - an Apache POI workbook representing the file we want to preprocess
	 * @throws IOException
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public void preprocessXlWorkbook (String groovyScriptName, Workbook xlWorkbook) throws IOException, ResourceException, ScriptException {

		if(engine==null){
			engine = new GroovyScriptEngine(new String[]{ "." });
		}


		Binding binding = new Binding();
		binding.setVariable("xlWorkbook", xlWorkbook);
		binding.setVariable("arg2", "Bob");
		Object result = engine.run(groovyScriptName, binding); 
		log.info("Result:"+result);
		

	}
}
