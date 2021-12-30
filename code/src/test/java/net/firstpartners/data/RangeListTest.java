package net.firstpartners.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.Logger;
import org.junit.Test;

import net.firstpartners.core.log.RpLogger;

public class RangeListTest  {

	private Logger log = RpLogger.getLogger(Range.class.getName());

	@Test
	public void testIfNullBreaks() throws Exception {

		RangeList myList = new RangeList();
		myList.getAllCellsInAllRanges();
		myList.getAllCellsWithNames();
		log.debug("null test succesfully passed");

	}
	
	@Test
	public void testCascadingRest() throws Exception {

		RangeList myList = new RangeList();
		
		//create modify and add range
		Range myRange = new Range();
		myRange.setRangeName("new name");
		myList.add(myRange);
		
		//Create and add cell
		Cell myCell = new Cell();
		myRange.put("cell-ref",myCell);
		
		assertFalse (myCell.isModified());
		myCell.setName("somenewname");
		assertTrue(myCell.isModified());
		
		//now do the reset
		myList.cascadeResetIsModifiedFlag();
		assertFalse (myCell.isModified());
		

	}
	
}
