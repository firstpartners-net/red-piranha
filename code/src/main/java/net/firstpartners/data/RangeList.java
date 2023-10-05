package net.firstpartners.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/**
 * Holder of all multiple Named Ranges from a spreadsheet. Also provides
 * convenience methods to access all the Cells (avoid the intermediate step of
 * getting the Ranges). Since we also map from other sources into these classes,
 * a RangeList could instead contain tables from a Word Document.
 *
 * It is designed to be a 'collection' which allows us to manipulate using standard Java iterators.
 *
 * @author paul
 * @version $Id: $Id
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RangeList implements List<Range>, Serializable {


	/**
	 * needed for serialization
	 */
	private static final long serialVersionUID = -4130443874933187650L;
	List<Range> allRanges = new ArrayList<Range>();

	/**
	 * <p>add.</p>
	 *
	 * @param arg0 a int
	 * @param arg1 a {@link net.firstpartners.data.Range} object
	 */
	public void add(int arg0, Range arg1) {
		allRanges.add(arg0, arg1);
	}

	/**
	 * <p>add.</p>
	 *
	 * @param arg0 a {@link net.firstpartners.data.Range} object
	 * @return a boolean
	 */
	public boolean add(Range arg0) {
		return allRanges.add(arg0);
	}

	/** {@inheritDoc} */
	public boolean addAll(Collection<? extends Range> arg0) {
		return allRanges.addAll(arg0);
	}

	/** {@inheritDoc} */
	public boolean addAll(int arg0, Collection<? extends Range> arg1) {
		return allRanges.addAll(arg0, arg1);
	}

	/**
	 * Reset the isModified Flag to false in allRanges (and subranges) held by this
	 * RangeList
	 */
	public void cascadeResetIsModifiedFlag() {

		for (Range range : allRanges) {

			range.cascadeResetIsModifiedFlag();
		}

	}

	/**
	 * <p>clear.</p>
	 */
	public void clear() {
		allRanges.clear();
	}

	/** {@inheritDoc} */
	public boolean contains(Object arg0) {
		return allRanges.contains(arg0);
	}

	/** {@inheritDoc} */
	public boolean containsAll(Collection<?> arg0) {
		return allRanges.containsAll(arg0);
	}

	/** {@inheritDoc} */
	public boolean equals(Object arg0) {
		return allRanges.equals(arg0);
	}

	/** {@inheritDoc} */
	public Range get(int arg0) {
		return allRanges.get(arg0);
	}

	/**
	 * Returns all Ranges , and the cells within those ranges As a flattened
	 * collection
	 *
	 * @return a {@link java.util.Collection} object
	 */
	public Collection<Cell> getAllCellsInAllRanges() {

		Collection<Cell> returnValues = new ArrayList<Cell>();

		if(allRanges!=null){

			for (Range range : allRanges) {

				// Add the cells within the range

				for (net.firstpartners.data.Cell cell : range.values()) {
					returnValues.add(cell);
				}

			}

		}

		

		return returnValues;

	}
	
	/**
	 * returns the first cell with an exact match
	 *
	 * @param exactName - search criteria
	 * @return - can be null if nothing found
	 */
	public Cell findCell(String exactName) {


		for (Range range : allRanges) {

			// Add the cells within the range

			for (net.firstpartners.data.Cell cell : range.values()) {
				if ((cell.getName() != null) && cell.getName().equals(exactName)) {
					return cell;
				}

			}

		}
		
		//no match if we get this far
		return null;

	}

	/**
	 * Returns all Cells (held by the Ranges we hold)
	 *
	 * @param nameStartsWith - search criteria
	 * @return a {@link java.util.Collection} object
	 */
	public Collection<Cell> findCellsStartingWith(String nameStartsWith) {

		Collection<Cell> returnValues = new ArrayList<Cell>();

		for (Range range : allRanges) {

			// Add the cells within the range

			for (net.firstpartners.data.Cell cell : range.values()) {
				if ((cell.getName() != null) && cell.getName().startsWith(nameStartsWith)) {
					returnValues.add(cell);
				}

			}

		}
		return returnValues;

	}

	/**
	 * Returns all Cells (held by the Ranges we hold)
	 *
	 * @param nameStartsWith - search criteria
	 * @return a {@link java.util.Collection} object
	 */
	public Collection<Range> findRangesStartingWith(String nameStartsWith) {

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
	 * @return a {@link java.util.Map} object
	 */
	public Map<String, net.firstpartners.data.Cell> getAllCellsWithNames() {

		HashMap<String, Cell> returnValues = new HashMap<String, Cell>();

		for (Range range : allRanges) {

			for (net.firstpartners.data.Cell cell : range.values()) {
				if (cell.getName() != null) {
					returnValues.put(cell.getName(), cell);
				}

			}

		}

		return returnValues;

	}

	/**
	 * <p>hashCode.</p>
	 *
	 * @return a int
	 */
	public int hashCode() {
		return allRanges.hashCode();
	}

	/** {@inheritDoc} */
	public int indexOf(Object arg0) {
		return allRanges.indexOf(arg0);
	}

	/**
	 * <p>isEmpty.</p>
	 *
	 * @return a boolean
	 */
	public boolean isEmpty() {
		return allRanges.isEmpty();
	}

	/**
	 * <p>iterator.</p>
	 *
	 * @return a {@link java.util.Iterator} object
	 */
	public Iterator<Range> iterator() {
		return allRanges.iterator();
	}

	/** {@inheritDoc} */
	public int lastIndexOf(Object arg0) {
		return allRanges.lastIndexOf(arg0);
	}

	/**
	 * <p>listIterator.</p>
	 *
	 * @return a {@link java.util.ListIterator} object
	 */
	public ListIterator<Range> listIterator() {
		return allRanges.listIterator();
	}

	/** {@inheritDoc} */
	public ListIterator<Range> listIterator(int arg0) {
		return allRanges.listIterator(arg0);
	}

	/**
	 * <p>remove.</p>
	 *
	 * @param arg0 a int
	 * @return a {@link net.firstpartners.data.Range} object
	 */
	public Range remove(int arg0) {
		return allRanges.remove(arg0);
	}

	/** {@inheritDoc} */
	public boolean remove(Object arg0) {
		return allRanges.remove(arg0);
	}

	/** {@inheritDoc} */
	public boolean removeAll(Collection<?> arg0) {
		return allRanges.removeAll(arg0);
	}

	/** {@inheritDoc} */
	public boolean retainAll(Collection<?> arg0) {
		return allRanges.retainAll(arg0);
	}

	/**
	 * <p>set.</p>
	 *
	 * @param arg0 a int
	 * @param arg1 a {@link net.firstpartners.data.Range} object
	 * @return a {@link net.firstpartners.data.Range} object
	 */
	public Range set(int arg0, Range arg1) {
		return allRanges.set(arg0, arg1);
	}

	/**
	 * <p>size.</p>
	 *
	 * @return a int
	 */
	public int size() {
		return allRanges.size();
	}

	/** {@inheritDoc} */
	public List<Range> subList(int arg0, int arg1) {
		return allRanges.subList(arg0, arg1);
	}

	/**
	 * <p>toArray.</p>
	 *
	 * @return an array of {@link java.lang.Object} objects
	 */
	public Object[] toArray() {
		return allRanges.toArray();
	}

	/**
	 * <p>toArray.</p>
	 *
	 * @param arg0 an array of T[] objects
	 * @param <T> a T class
	 * @return an array of T[] objects
	 */
	public <T> T[] toArray(T[] arg0) {
		return allRanges.toArray(arg0);
	}

	/**
	 * {@inheritDoc}
	 *
	 * toString method, lists all the cells we hold
	 */

	@Override
	public String toString() {

		StringBuilder returnText = new StringBuilder();
		getAllCellsInAllRanges().forEach((cell) -> {
			returnText.append(cell.toString() + " ");
		});

		return returnText.toString();

	}

}
