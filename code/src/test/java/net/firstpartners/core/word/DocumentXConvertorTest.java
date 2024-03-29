package net.firstpartners.core.word;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeList;

public class DocumentXConvertorTest {

	// Logger
	private static final Logger log = LoggerFactory.getLogger(DocumentXConvertorTest.class);

	//if we need to change the directory prefix
	private static String dirPrefix="";

	/**
	 * s Convenience method - serialize testdata for use by Cell Tasts
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static void main(String args[]) throws IOException, InvalidFormatException {

		//have we been requested to modfiy the directoyr
		dirPrefix = args[0];

		RangeList myRange = new DocumentXConvertorTest().getTestDataFromWorkbook();
		FileOutputStream fileOut = new FileOutputStream(dirPrefix+TestConstants.SAVED_WORDX_RANGEHOLDER_DATA);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(myRange);
		out.close();
		fileOut.close();
		log.debug("Serialized data is saved in:" + (dirPrefix+TestConstants.SAVED_WORDX_RANGEHOLDER_DATA));
	}

	/**
	 * This is implemented as a sub method so we can call from tests and convenience
	 * main[] method
	 * 
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	private final RangeList getTestDataFromWorkbook() throws IOException, InvalidFormatException {

		FileInputStream inputStream = new FileInputStream(dirPrefix+TestConstants.WORDX_DATA_FILE);
		XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(inputStream));
		return DocumentXConvertor.convertFromPoiWordIntoRedRange(xdoc);

	}

	@Test
	public void testConvertPoiToRed() throws InvalidFormatException, IOException {

		RangeList myRangeList = new DocumentXConvertorTest().getTestDataFromWorkbook();
		log.debug("RangeList:" + myRangeList);
		assertNotNull(myRangeList);

		// check what we think will we return
		Collection<Range> filterParaRange = myRangeList.findRangesStartingWith(DocumentXConvertor.WORD_PARAGRAPH_NAME_AS_RANGELIST);
		log.debug("FilteredRange:" + filterParaRange);
		assertNotNull(filterParaRange);
		assertEquals(9, filterParaRange.size());

		// Check for tables
		Collection<Range> filterTableRange = myRangeList.findRangesStartingWith(DocumentXConvertor.WORD_TABLE_ROW_AS_RANGELIST);
		assertNotNull(filterTableRange);
		assertEquals(2, filterTableRange.size());

	}
	


}
