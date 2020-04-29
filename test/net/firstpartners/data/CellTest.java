package net.firstpartners.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class CellTest {

	@Test
	public final void testCellEquality() {
		
		Cell myCell1 = new Cell("Key1","Value1");
		Cell myCellSameValues = new Cell("Key1","Value1");
		assertEquals(myCell1,myCellSameValues);
		
		
		Cell myCell2 = new Cell ("Key1","Value2");
		assertNotEquals(myCell1,myCell2);
		
	}
	
	@Test
	public final void testPropertyChangeListener() {
		
		
		// note when fixing - is there an annotation
		fail("test not yet implemented");
	}

}

