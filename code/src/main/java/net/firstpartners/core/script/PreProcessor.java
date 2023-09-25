package net.firstpartners.core.script;

import groovy.util.Eval;


/**
 * Utilities for running the pre-processing script
 *
 * @author PBrowne
 * @version $Id: $Id
 */
public class PreProcessor {


	/**
	 * Delete the output file if it already exists
	 *
	 * @param fileToDelete a {@link java.lang.String} object
	 */
	public static void main (String[] args) {

		Object result = Eval.me("33*3");

		System.out.println("Hello World:"+result);

	}
}
