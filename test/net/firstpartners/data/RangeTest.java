package net.firstpartners.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;

import net.firstpartners.core.log.RpLogger;


public class RangeTest  {


	private Logger log = RpLogger.getLogger(Range.class.getName());

	
	@Test
	public void testCellSearch() throws Exception {

		log.debug("starting test");
		
		//Create the test object
		Range testRange = new Range();
		
		//Check that we can the name of the cell in the same range
		assertEquals("range_3",testRange.getCellName("range_12", 3));
		
		//Check a name that contains multiple underscores
		assertEquals("range_double_barrell_3",testRange.getCellName("range_double_barrell_12", 3));
		
	}
	
	@Test
	public void testRangeContains(){
		
		//Create the test object
		Range testRange = new Range();
		
		// add some test cells
		Cell testCellOne = new Cell();
		testCellOne.setValue("cellValue1");
		
		Cell testCellTwo = new Cell();
		testCellTwo.setValue("cellValue2");
		
		testRange.put("name1",testCellOne);
		testRange.put("name2",testCellTwo);
		
		assertTrue(testRange.getRangeContainsValue("cellValue1"));
		assertFalse(testRange.getRangeContainsValue("cellValue3")); // we don't add this , should not exist
		
		
	}
	
	@Test
	public final void testPropertyChangeListener() {
		
		
		// note when fixing - is there an annotation
		fail("test not yet implemented");
	}
	
	

}

