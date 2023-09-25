package net.firstpartners.core.script;


import java.io.IOException;

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
	 * @throws IOException
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public void runGroovyScript (String groovyScriptName) throws IOException, ResourceException, ScriptException {

		if(engine==null){
			engine = new GroovyScriptEngine(new String[]{ "." });
		}


		Binding binding = new Binding();
		//binding.setVariable("arg1", "Mr.");
		//binding.setVariable("arg2", "Bob");
		Object result = engine.run(groovyScriptName, binding); 
		log.info("Result:"+result);
		

	}
}
