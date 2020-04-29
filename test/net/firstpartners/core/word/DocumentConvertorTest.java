package net.firstpartners.core.word;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.data.RangeHolder;

public class DocumentConvertorTest {


	@Test
	public void testMain() throws InvalidFormatException, IOException {
		DocumentConvertor.convertTables(TestConstants.WORDX_DATA_FILE);
		DocumentConvertor.convertParas(TestConstants.WORDX_DATA_FILE);
	}

	
	@Test
	public void testConvertPoiToRed() throws InvalidFormatException, IOException {
		
		XWPFDocument xdoc = new XWPFDocument();
		RangeHolder results = DocumentConvertor.convertFromPoiWordIntoRedRange(xdoc);
		assertNotNull(results);
		assertTrue(results.toArray().length>0);
		
	}
	
	
	@Test
	public void testConvertRedToPoi() throws InvalidFormatException, IOException {
		XWPFDocument xdoc = new XWPFDocument();
		RangeHolder results = new RangeHolder();
		
		DocumentConvertor.updateRedRangeintoPoiWord(xdoc, results);
	}
	
	

}
