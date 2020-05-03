package net.firstpartners.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Test;

public class CellTest implements PropertyChangeListener {

	PropertyChangeEvent holdEvent=null;
	
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
		
		Cell myCell1 = new Cell("Key1","Value1");
		myCell1.addPropertyChangeListener(this);
		
		//reset the event
		holdEvent =null;
		//something should fire
		
		assertNull(holdEvent);
		myCell1.setCellName("newName");
		
		assertNotNull(holdEvent);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.holdEvent=evt;
	}
	
	@Test
	public void testTrueBoolean() {
		Cell myTrueCell = new Cell();
		myTrueCell.setValue("true");
		assertTrue(myTrueCell.getValueAsBoolean());
		assertEquals(myTrueCell.getValue(),"true");
		assertNull(myTrueCell.getValueAsLong());
		
	}
	
	@Test
	public void testFalseBoolean() {
		Cell myFalseCell = new Cell();
		myFalseCell.setValue("false");
		assertFalse(myFalseCell.getValueAsBoolean());
		assertEquals(myFalseCell.getValue(),"false");
		assertNull(myFalseCell.getValueAsLong());
		
	}
	
	@Test
	public void testNotBoolean() {
		Cell myFalseCell = new Cell();
		myFalseCell.setValue(1);
		assertNull(myFalseCell.getValueAsBoolean());
		assertEquals(myFalseCell.getValue(),1);
		
	}

}

