package net.firstpartners.core.script;


import java.io.File;
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

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.ss.usermodel.*;
import java.io.*;


/**
 * Utilities for supporting Groovy Scripts in injecting names into the sheet
 * Everything that is done is in this class could be done in the Groovy Scripts
 * But having it packaged in a utility makes them easier to use.
 *
 * @author PBrowne
 * @version $Id: $Id
 */
public class SheetNames {


	// Handle to the loggers
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// Handle to the workbook we will be operating on
	private Workbook wb =null;

	/**
	 * Constructor
	 * @param xlWorkbook that we 
	 */
	public SheetNames(Workbook xlWorkbook){
		this.wb = xlWorkbook;
	}

	/**
	 * Loop through a table in Excel, naming the rangess individually within tehm
	 *
	 * @param sheetName - withing the workbook, where we want to set the workbook
	 * @param mainTableRef - the full table, in Excel notation *including* the Header and column rows
	 * @throwpreprocessXlWorkbooks IOException
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public void nameTable (String sheetName, String mainTableRef ,int numberOfHeaderRows, int numberOfColumnRows )  {

		assert 1>2 : "method not implemented yet";
		

	}
}
