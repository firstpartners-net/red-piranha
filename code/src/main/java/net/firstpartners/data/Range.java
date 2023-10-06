package net.firstpartners.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * A range is just a holder for a group of named cells - maps to a similar
 * Concept in MS Excel We use it as a convenience for loading our values into
 * working memory
 *
 * Since we also map from other sources into these classes, a Range could
 * contain one table from a Word Document.
 *
 * @see Map which it implmements for convenience
 * @author paul
 * @version $Id: $Id
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Range implements Map<String, Cell>, Serializable {

	/** Constant <code>CELLNAME_NUM_SEPARATOR="_"</code> */
	public static final String CELLNAME_NUM_SEPARATOR = "_";

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = -2208591825859424164L;

	/**
	 * Main internal data structure holder
	 */
	private Map<String, Cell> cells = new HashMap<String, Cell>();

	private String rangeName = null;

	/**
	 * Default bean like constructor
	 */
	public Range() {
		super();
	}

	/**
	 * Create the bean , the name of the original name e.g. equivalent to named
	 * range in Excel
	 *
	 * @param rangeName a {@link java.lang.String} object
	 */
	public Range(String rangeName) {
		this.rangeName = rangeName;
	}

	/**
	 * <p>cascadeResetIsModifiedFlag.</p>
	 */
	public void cascadeResetIsModifiedFlag() {
		
		for (Cell  cell : cells.values()) {
			cell.setModified(false);
		}
		
	}

	/**
	 * <p>clear.</p>
	 */
	public void clear() {
		cells.clear();
	}

	/** {@inheritDoc} */
	public boolean containsKey(Object arg0) {
		return cells.containsKey(arg0);
	}

	/** {@inheritDoc} */
	public boolean containsValue(Object arg0) {

		return cells.containsValue(arg0);
	}

	/**
	 * <p>entrySet.</p>
	 *
	 * @return a {@link java.util.Set} object
	 */
	public Set<java.util.Map.Entry<String, Cell>> entrySet() {
		return cells.entrySet();
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Range other = (Range) obj;
		if (cells == null) {
			if (other.cells != null)
				return false;
		} else if (!cells.equals(other.cells))
			return false;
		if (rangeName == null) {
			if (other.rangeName != null)
				return false;
		} else if (!rangeName.equals(other.rangeName))
			return false;
		return true;
	}

	/** {@inheritDoc} */
	public Cell get(Object arg0) {
		return cells.get(arg0);
	}

	/**
	 * Get the Actual Cell of a position cell in the range e.g. if the cell name is
	 * range_8 then , we can ask for the first cell range_0
	 *
	 * @param newCellPosition a int
	 * @return a {@link net.firstpartners.data.Cell} object
	 */
	public Cell getCell(int newCellPosition) {

		log.debug("base range name:" + rangeName);

		// The last "_" is to fool the cell based algorithm into working when
		// we supply the range name only
		// e.g. cell name range_0 , range is called 'range'

		String requestedCellName = getCellName(rangeName + "_", newCellPosition);

		log.debug("Looking up Cell value" + requestedCellName);

		return cells.get(requestedCellName);

	}

	/**
	 * Get the Actual Cell of a position cell in the range e.g. if the cell name is
	 * range_8 then , we can ask for the first cell range_0
	 *
	 * @param currentFullCellName a {@link java.lang.String} object
	 * @param newCellPosition a int
	 * @return a {@link net.firstpartners.data.Cell} object
	 */
	public Cell getCellInRange(String currentFullCellName, int newCellPosition) {

		String requestedCellName = getCellName(currentFullCellName, newCellPosition);
		return cells.get(requestedCellName);

	}

	/**
	 * Get the name of a position cell in the range e.g. if the cell name is range_8
	 * then , we can ask for the first cell range_0
	 *
	 * @param currentFullCellName a {@link java.lang.String} object
	 * @param newCellPosition a int
	 * @return String with the name
	 */
	public String getCellName(String currentFullCellName, int newCellPosition) {

		if (currentFullCellName == null) {
			// log.debug("currentFullCellName is null, returning null");
			return null;
		}

		// Break at the *last* separator
		int breakpoint = currentFullCellName.lastIndexOf(CELLNAME_NUM_SEPARATOR);
		String mainPart = currentFullCellName.substring(0, breakpoint);

		// make up desired name and return , leaving off the last
		return mainPart + CELLNAME_NUM_SEPARATOR + newCellPosition;

	}

	/**
	 * Get the Actual Cell of a position cell in the range e.g. if the cell name is
	 * range_8 then , we can ask for the first cell range_0
	 *
	 * @param newCellPosition a int
	 * @return - Object, Representing the cell Value
	 */
	public Object getCellValue(int newCellPosition) {

		Cell thisCell = getCell(newCellPosition);

		if (thisCell != null) {
			// log.debug("cell value is null , returning null");
			return thisCell.getValue();
		}

		// Default
		return null;

	}

//	public Cell[] getCellValueArray() {
//
//		return getCellValueList().toArray();
//
//	}

	/**
	 * <p>getCellValueList.</p>
	 *
	 * @return a {@link java.util.List} object
	 */
	public List<Object> getCellValueList() {

		ArrayList<Object> returnList = new ArrayList<Object>();

		for (Cell c : cells.values()) {
			if (c != null) {
				// log.debug("Cell Value list:"+c.getValue());
				returnList.add(c.getValue());
			}
		}

		return returnList;

	}

	/**
	 * Matches on contents of cells, unlike conventional containsValue method that
	 * matches exact objects
	 *
	 * @param value a {@link java.lang.Object} object
	 * @return a boolean
	 */
	public boolean getRangeContainsValue(Object value) {

		return getCellValueList().contains(value);

	}

	/**
	 * <p>Getter for the field <code>rangeName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getRangeName() {
		return rangeName;
	}

	/**
	 * Generate cell name based on internal string
	 *
	 * @param cellInRange a int
	 * @return a {@link java.lang.String} object
	 */
	public String getUniqueCellName(int cellInRange) {

		return getUniqueCellName(rangeName, cellInRange);

	}

	/**
	 * Generate cell name based on supplied string
	 *
	 * @param cellInRange a int
	 * @param rangeNameExt a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public String getUniqueCellName(String rangeNameExt, int cellInRange) {

		return rangeNameExt + CELLNAME_NUM_SEPARATOR + cellInRange;

	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cells == null) ? 0 : cells.hashCode());
		result = prime * result + ((rangeName == null) ? 0 : rangeName.hashCode());
		return result;
	}

	/**
	 * <p>isEmpty.</p>
	 *
	 * @return a boolean
	 */
	public boolean isEmpty() {
		return cells.isEmpty();
	}


	/**
	 * <p>keySet.</p>
	 *
	 * @return a {@link java.util.Set} object
	 */
	public Set<String> keySet() {
		return cells.keySet();
	}

	/**
	 * <p>put.</p>
	 *
	 * @param rangeName a {@link java.lang.String} object
	 * @param cell a {@link net.firstpartners.data.Cell} object
	 * @return a {@link net.firstpartners.data.Cell} object
	 */
	public Cell put(String rangeName, Cell cell) {
		assert rangeName != null;
		assert cell != null;
		return cells.put(rangeName, cell);

	}

	/** {@inheritDoc} */
	public void putAll(Map<? extends String, ? extends Cell> arg0) {
		cells.putAll(arg0);
	}


	/** {@inheritDoc} */
	public Cell remove(Object arg0) {
		return cells.remove(arg0);

	}

	/**
	 * <p>Setter for the field <code>rangeName</code>.</p>
	 *
	 * @param rangeName a {@link java.lang.String} object
	 */
	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;

	}

	/**
	 * <p>size.</p>
	 *
	 * @return a int
	 */
	public int size() {
		return cells.size();
	}

	/**
	 * <p>toShortString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toShortString() {

		return "Range:" + rangeName;

	}

	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toString() {

		StringBuffer returnString = new StringBuffer("Range:" + rangeName + "\n");

		for (String cellName : cells.keySet()) {
			returnString.append(cellName + ": " + cells.get(cellName) + "\n");
		}

		return returnString.toString();
	}

	/** {@inheritDoc} */
	@Override
	public Collection<Cell> values() {
		return cells.values();
	}

	/**
	 * <p>put.</p>
	 *
	 * @param newFacts a {@link java.util.Collection} object
	 */
	public void put(Collection<Cell> newFacts) {
		
		for(Cell c: newFacts) {
			put(c.getName(),c);
			log.debug("Added new fact:"+c.getName());
		}
		
	}

}
