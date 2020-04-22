package net.firstpartners.core.spreadsheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import net.firstpartners.core.log.RpLogger;

/**
 * Holder of all multiple Ranges from a spreadshet
 * 
 * @author paul
 *
 */
public class RangeHolder implements List<Range>, Serializable {

	private static final Logger log = RpLogger.getLogger(RangeHolder.class.getName());

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
	 * Returns all Ranges , and the cells within those ranges As a flattened
	 * collection
	 * 
	 * @return
	 */
	public Collection<Cell> getAllRangesAndCells() {

		Collection<Cell> returnValues = new ArrayList<Cell>();

		for (Range range : allRanges) {

			// Add the range to the flattened collection
			//returnValues.add(range);

			// Add the cells within the range

			for (net.firstpartners.core.spreadsheet.Cell cell : range.values()) {
				returnValues.add(cell);
			}

		}

		return returnValues;

	}
	/**
	 * toString method, lists all the cells we hold
	 */
	
	@Override
	public String toString() {
		StringBuilder returnText = new StringBuilder(ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE));
		
		getAllRangesAndCells().forEach((cell) -> {
			returnText.append("  "+cell+"\n");
		});
		
		return returnText.toString();
	   
	}

	/**
	 * Returns a map of Cells, with the unique handle we've associate with them
	 * 
	 * @return
	 */
	public Map<String, net.firstpartners.core.spreadsheet.Cell> getAllCells() {

		HashMap<String, Cell> returnValues = new HashMap<String, Cell>();
		log.info("combining all cells in all ranges, returning as Hashmap");

		for (Range range : allRanges) {

			for (net.firstpartners.core.spreadsheet.Cell cell : range.values()) {
				if (cell.getCellName() != null) {
					returnValues.put(cell.getCellName(), cell);
				}

			}

		}

		return returnValues;

	}

}
