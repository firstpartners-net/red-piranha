package net.firstpartners.core.document;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.word.DocumentConvertor;

class DocumentConvertorTest {

	@Test
	void testConvertDocx() throws InvalidFormatException, IOException {
		DocumentConvertor.convertTables(TestConstants.WORDX_DATA_FILE);
		DocumentConvertor.convertParas(TestConstants.WORDX_DATA_FILE);
	}
	
	@Test
	void testConvertDoc() throws InvalidFormatException, IOException {
		DocumentConvertor.convertTables(TestConstants.WORD_DATA_FILE);
		DocumentConvertor.convertParas(TestConstants.WORD_DATA_FILE);
	}


}
