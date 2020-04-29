package net.firstpartners.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;

import net.firstpartners.core.log.RpLogger;

/**
 * Holder of all multiple Named Ranges from a spreadsheet. Also provides
 * convenience methods to access all the Cells (avoid the intermediate step of
 * getting the Ranges). Since we also map from other sources into these classes,
 * a RangeHolder could instead contain tables from a Word Document.
 * 
 * It is designed to be a 'collection' which allows us to
 * 
 * @author paul
 *
 */
public class RangeList implements List<Range>, Serializable {

	//
	private static final Logger log = RpLogger.getLogger(RangeList.class.getName());

	/**
	 * needed for serialization
	 */
	private static final long serialVersionUID = -4130443874933187650L;
	List<Range> allRanges = new ArrayList<Range>();

	public void add(int arg0, Range arg1) {
		allRanges.add(arg0, arg1);
	}

	public boolean add(Range arg0) {
		return allRanges.add(arg0);
	}

	public boolean addAll(Collection<? extends Range> arg0) {
		return allRanges.addAll(arg0);
	}

	public boolean addAll(int arg0, Collection<? extends Range> arg1) {
		return allRanges.addAll(arg0, arg1);
	}

	/**
	 * Reset the isModified Flag to false in allRanges (and subranges) held by this
	 * RangeList
	 * 
	 * @return
	 */
	public void cascadeResetIsModifiedFlag() {

		for (Range range : allRanges) {

			range.cascadeResetIsModifiedFlag();
		}

	}

	public void clear() {
		allRanges.clear();
	}

	public boolean contains(Object arg0) {
		return allRanges.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return allRanges.containsAll(arg0);
	}

	public boolean equals(Object arg0) {
		return allRanges.equals(arg0);
	}

	public Range get(int arg0) {
		return allRanges.get(arg0);
	}

	/**
	 * Returns all Ranges , and the cells within those ranges As a flattened
	 * collection
	 * 
	 * @return
	 */
	public Collection<Cell> getAllCellsInAllRanges() {

		Collection<Cell> returnValues = new ArrayList<Cell>();

		for (Range range : allRanges) {

			// Add the cells within the range

			for (net.firstpartners.data.Cell cell : range.values()) {
				returnValues.add(cell);
			}

		}

		return returnValues;

	}

	/**
	 * Returns all Cells (held by the Ranges we hold)
	 * 
	 * @Param nameStartsWith - search criteria
	 * 
	 * @return
	 */
	public Collection<Cell> findCells(String nameStartsWith) {

		Collection<Cell> returnValues = new ArrayList<Cell>();

		for (Range range : allRanges) {

			// Add the cells within the range

			for (net.firstpartners.data.Cell cell : range.values()) {
				if ((cell.getCellName() != null) && cell.getCellName().startsWith(nameStartsWith)) {
					returnValues.add(cell);
				}

			}

		}

		return returnValues;

	}

	/**
	 * Returns all Cells (held by the Ranges we hold)
	 * @Param nameStartsWith - search criteria
	 * 
	 * @return
	 */
	public Collection<Range> findRanges(String nameStartsWith) {

		Collection<Range> returnValues = new ArrayList<Range>();

		for (Range range : allRanges) {

			//Filter to see if our Range matches the filter
			if((range.getRangeName()!=null)&&(range.getRangeName().startsWith(nameStartsWith))) {
					returnValues.add(range);
				}

		}

	return returnValues;

	}

	/**
	 * Returns a map of Cells, with the unique handle we've associate with them
	 * 
	 * @return
	 */
	public Map<String, net.firstpartners.data.Cell> getAllCellsWithNames() {

		HashMap<String, Cell> returnValues = new HashMap<String, Cell>();
		log.info("combining all cells in all ranges, returning as Hashmap");

		for (Range range : allRanges) {

			for (net.firstpartners.data.Cell cell : range.values()) {
				if (cell.getCellName() != null) {
					returnValues.put(cell.getCellName(), cell);
				}

			}

		}

		return returnValues;

	}

	public int hashCode() {
		return allRanges.hashCode();
	}

	public int indexOf(Object arg0) {
		return allRanges.indexOf(arg0);
	}

	public boolean isEmpty() {
		return allRanges.isEmpty();
	}

	public Iterator<Range> iterator() {
		return allRanges.iterator();
	}

	public int lastIndexOf(Object arg0) {
		return allRanges.lastIndexOf(arg0);
	}

	public ListIterator<Range> listIterator() {
		return allRanges.listIterator();
	}

	public ListIterator<Range> listIterator(int arg0) {
		return allRanges.listIterator(arg0);
	}

	public Range remove(int arg0) {
		return allRanges.remove(arg0);
	}

	public boolean remove(Object arg0) {
		return allRanges.remove(arg0);
	}

	public boolean removeAll(Collection<?> arg0) {
		return allRanges.removeAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0) {
		return allRanges.retainAll(arg0);
	}

	public Range set(int arg0, Range arg1) {
		return allRanges.set(arg0, arg1);
	}

	public int size() {
		return allRanges.size();
	}

	public List<Range> subList(int arg0, int arg1) {
		return allRanges.subList(arg0, arg1);
	}

	public Object[] toArray() {
		return allRanges.toArray();
	}

	public <T> T[] toArray(T[] arg0) {
		return allRanges.toArray(arg0);
	}

	/**
	 * toString method, lists all the cells we hold
	 */

	@Override
	public String toString() {

		StringBuilder returnText = new StringBuilder();
		getAllCellsInAllRanges().forEach((cell) -> {
			returnText.append(cell.toString() + "\n");
		});

		return returnText.toString();

	}

}
