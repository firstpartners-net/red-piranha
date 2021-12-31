package net.firstpartners.ui.component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.Before;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.data.RangeList;

/**
 * Test to call the main method
 * 
 * @author PBrowne
 *
 */
public class RangeCellTreeTest {

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private static RangeList redData = null;

	@Before
	public void beforeClass() throws IOException, ClassNotFoundException {

		FileInputStream fileIn = new FileInputStream(TestConstants.SAVED_EXCEL_RANGEHOLDER_DATA);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		redData = (RangeList) in.readObject();
		in.close();
		fileIn.close();
	}

	@Test
	public final void testCreateGUI() throws Throwable {
		log.debug("Test create GUI");
		RangeCellTree tree = new RangeCellTree();
		tree.setDataModel(redData);

	}

}
