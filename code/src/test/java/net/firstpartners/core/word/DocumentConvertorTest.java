package net.firstpartners.core.word;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeList;

public class DocumentConvertorTest {

	// Logger
	private static final Logger log = LoggerFactory.getLogger(DocumentConvertorTest.class);

	/**
	 * s Convenience method - serialize test data for use by Cell Tests
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static void main(String args[]) throws IOException, InvalidFormatException {

		RangeList myRange = new DocumentConvertorTest().getTestDataFromWorkbook();
		FileOutputStream fileOut = new FileOutputStream(TestConstants.SAVED_WORD_RANGEHOLDER_DATA);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(myRange);
		out.close();
		fileOut.close();
		log.debug("Serialized data is saved in:" + (TestConstants.SAVED_WORD_RANGEHOLDER_DATA));
	}

	/**
	 * This is implemented as a sub method so we can call from tests and convenience
	 * main[] method
	 * 
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	final RangeList getTestDataFromWorkbook() throws IOException, InvalidFormatException{

		InputStream fis = new FileInputStream(TestConstants.WORD_DATA_FILE);
		POIFSFileSystem fs = new POIFSFileSystem(fis);
		HWPFDocument xdoc = new HWPFDocument(fs);

		return DocumentConvertor.convertFromPoiWordIntoRedRange(xdoc);

	}

	@Test
	public void testConvertPoiToRed() throws IOException, InvalidFormatException {

		RangeList myRangeList = new DocumentConvertorTest().getTestDataFromWorkbook();
		log.debug("RangeList:" + myRangeList);
		assertNotNull(myRangeList);

		// check what we think will we return
		Collection<Range> filterParaRange = myRangeList.findRangesStartingWith(DocumentXConvertor.WORD_PARAGRAPH_NAME_AS_RANGELIST);
		log.debug("FilteredRange:" + filterParaRange);
		assertNotNull(filterParaRange);
		assertEquals(75, filterParaRange.size());

		// Check for tables
		Collection<Range> filterTableRange = myRangeList.findRangesStartingWith(DocumentXConvertor.WORD_TABLE_ROW_AS_RANGELIST);
		assertNotNull(filterTableRange);
		assertEquals(13, filterTableRange.size());

	}
	

}
