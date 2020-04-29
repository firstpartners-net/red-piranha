package net.firstpartners.data;

import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;

import net.firstpartners.core.log.RpLogger;


public class RangeListTest  {


	private Logger log = RpLogger.getLogger(Range.class.getName());

	
	@Test
	public void testIfNullBreaks() throws Exception {

		RangeList myList = new RangeList();
		myList.getAllRangesAndCells();
		myList.getAllCells();
		myList.get(0);
		log.info("null test succesfully passed");
		
	}
	
	
	@Test
	public final void testPropertyChangeListener() {
		
		
		// note when fixing - is there an annotation
		fail("test not yet implemented");
	}
	
	

}

