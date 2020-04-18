package net.firstpartners.core.spreadsheet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(Alphanumeric.class)
public class CellConvertorTest {

	private static RangeHolder redData=null;
	private static RangeHolder testRangeData = null;

	@Before
	public void beforeClass() throws IOException, ClassNotFoundException {

		FileInputStream fileIn = new FileInputStream(RangeConvertorTest.SAVED_RANGEHOLDER_DATA);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		testRangeData = (RangeHolder) in.readObject();
		in.close();
		fileIn.close();
	}

	@Test
	public final void testConvertPoiCellToRedCell() {
		assertNotNull(testRangeData);

		// loop through and chesck ranges
		Map<String, Cell> map = testRangeData.getAllCells();
		for (Map.Entry<String, Cell> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
			assertNotNull(entry.getKey());
			assertNotNull(entry.getValue());

		}
		
		redData = testRangeData;
	}

	@Test
	public final void testConvertRedCellToPoiCell() {
		assertNotNull(redData);
	}

}
