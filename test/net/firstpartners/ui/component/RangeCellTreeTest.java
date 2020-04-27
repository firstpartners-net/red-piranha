package net.firstpartners.ui.component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.apache.log4j.Logger;

import org.junit.Before;
import org.junit.Test;

import net.firstpartners.core.excel.RangeConvertorTest;
import net.firstpartners.data.RangeHolder;

/**
 * Test to call the main method
 * 
 * @author PBrowne
 *
 */
public class RangeCellTreeTest {

	// Logger
	private static final Logger log = Logger.getLogger(RangeCellTreeTest.class.getName());

	private static RangeHolder redData = null;

	@Before
	public void beforeClass() throws IOException, ClassNotFoundException {

		FileInputStream fileIn = new FileInputStream(RangeConvertorTest.SAVED_RANGEHOLDER_DATA);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		redData = (RangeHolder) in.readObject();
		in.close();
		fileIn.close();
	}

	@Test
	public final void testCreateGUI() throws Throwable {
		log.info("Test create GUI");
		RangeCellTree tree = new RangeCellTree();
		tree.setDataModel(redData);

	}

}
