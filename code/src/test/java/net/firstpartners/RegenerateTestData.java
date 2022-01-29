package net.firstpartners;

import net.firstpartners.core.excel.SpreadSheetConvertorTest;
import net.firstpartners.core.word.DocumentConvertorTest;
import net.firstpartners.core.word.DocumentXConvertorTest;

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

	/**
	 * 
	 */
	public static void main (String [] args) throws Exception
	{
		
		SpreadSheetConvertorTest.main(null);
		DocumentConvertorTest.main(null);
		DocumentXConvertorTest.main(null);
		
	}
	
}
