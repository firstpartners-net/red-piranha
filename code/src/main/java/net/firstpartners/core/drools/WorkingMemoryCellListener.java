package net.firstpartners.core.drools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.drools.event.rule.ObjectInsertedEvent;
import org.drools.event.rule.ObjectRetractedEvent;
import org.drools.event.rule.ObjectUpdatedEvent;
import org.drools.event.rule.WorkingMemoryEventListener;

import net.firstpartners.core.log.RpLogger;
import net.firstpartners.data.Cell;

/**
 * Observes working memory of drools to see if any new facts are created
 * 
 * @author PBrowne
 *
 */
public class WorkingMemoryCellListener implements WorkingMemoryEventListener {

	// Logger
	private static final Logger log = RpLogger.getLogger(WorkingMemoryCellListener.class.getName());

	ArrayList<Cell> newCells = new ArrayList<Cell>();

	/**
	 * We need to know about new Objects inserted in working memory
	 * So if new facts (Cells) are created, we can capture them on exit
	 */
	@Override
	public void objectInserted(ObjectInsertedEvent event) {

		if (event.getObject() instanceof Cell) {

			newCells.add((Cell) event.getObject());
	//		log.debug("Object Inserted:" + event.getObject());

		}

	}

	/**
	 * We get notified of these events, but we don't really care about updates
	 */
	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		// log.debug("Object updating:"+event.getObject());

	}

	@Override
	public void objectRetracted(ObjectRetractedEvent event) {
		newCells.remove(event.getOldObject());
		//log.debug("Object retracted:" + event.getOldObject());

	}

	public List<Cell> getNewCells() {
		return newCells;

	}

	/**
	 * Compare the list of facts we are given
	 * and return any new ones that we know about
	 * @param facts
	 * @return 
	 */
	public List<Cell> getNewCells(Collection<Cell> facts) {
		
		ArrayList<Cell> returnList = new ArrayList<Cell>();
		
		for (Cell cell : newCells) {
			
			if(facts.contains(cell)) {
			//	log.debug("already know about:"+cell);
			} else {
				log.debug("adding new fact:"+cell);
				returnList.add(cell);
			}
		}
		
		return returnList;
	}

}
