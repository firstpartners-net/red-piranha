package net.firstpartners.core.word;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;

import net.firstpartners.TestConstants;

class DocumentConvertorTest {

	@Test
	void testMarket() throws InvalidFormatException, IOException {
		fail("No real tests implemented");
	}
	
	
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
