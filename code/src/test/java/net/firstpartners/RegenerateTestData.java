package net.firstpartners;

import net.firstpartners.core.excel.SpreadSheetConvertorTest;
import net.firstpartners.core.word.DocumentConvertorTest;
import net.firstpartners.core.word.DocumentXConvertorTest;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Testing utility to generate test Data
 * Mainly this is Serialized Excel and Word Docs that we use later in unit testing
 * e.g. converting Excel into our Java Model
 * 
 * This can help resolve test failures (e.g. due to different versions of Java), or when we update the Cell and Range classes
 * 
 * To Use - simply call the main method in the IDE of your choice
 * @author paulf
 *
 */
public class RegenerateTestData {

	//Logging
	private static final Logger log = LoggerFactory.getLogger(RegenerateTestData.class);


	/**
	 * Call the main methods to regen test data
	 */
	public static void main (String [] args) throws Exception
	{

		//check our current working directory in Java
		String currentDirectory = System.getProperty("user.dir");
		log.info("Current Directory:"+currentDirectory);

		//Default args to pass into Regen methods
		String[] subArgs = {""};  

		//make sure we are in the 'code' directory so paths work
		if(currentDirectory.lastIndexOf("code")<0){

			log.info("Requesting move to code directory:");
			subArgs[0]="code/";
		}
		
		SpreadSheetConvertorTest.main(subArgs);
		DocumentConvertorTest.main(subArgs);
		DocumentXConvertorTest.main(subArgs);
		
	}
	
}
