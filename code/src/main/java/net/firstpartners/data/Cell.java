package net.firstpartners.data;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * JavaBean equivalent of a cell in an Excel Spreadsheet. Since we also map from
 * other sources into these classes, a Cell could be a cell from a table in a
 * Word Document. For simplicity, we map Paragraphs from a word document into a
 * cell (to make writing rules easier)
 *
 * @author paulf
 * @version $Id: $Id
 */
public class Cell implements PropertyChangeListener, Serializable {

	private static final long serialVersionUID = -763504507901540819L;

	private transient PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private String comment;
	
	private boolean modified = false;

	private String name = null;

	private String nextName =null;

	private int originalCellCol = -1;

	private int originalCellRow = -1;

	private String originalTableReference = null;

	private Object value;

	/**
	 * Default constructor - needed to keep this a Javabean
	 */
	public Cell() {
	}
	/**
	 * Most Basic useful Cell - with a name and value
	 *
	 * @param name a {@link java.lang.String} object
	 * @param value a {@link java.lang.Object} object
	 */
	public Cell(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

	/**
	 * <p>addPropertyChangeListener.</p>
	 *
	 * @param l a {@link java.beans.PropertyChangeListener} object
	 */
	public void addPropertyChangeListener(final PropertyChangeListener l) {
		this.changes.addPropertyChangeListener(l);
	}

	/**
	 * Appends textToAdd to existing value.
	 * Converts existing cell value to string if it already is there.
	 * If cell is null, then textToAdd will be the new value
	 *
	 * @param textToAdd a {@link java.lang.String} object
	 */
	public void appendValue(String textToAdd) {
		
		//for property change listener
		Object oldValue =value;
		
		if(value ==null) {
			value="";
		}
		
		StringBuffer tmpValue = new StringBuffer();
		tmpValue.append(value); //handles version of numbers
		tmpValue.append(textToAdd);
		
		value=tmpValue.toString();
		
		this.changes.firePropertyChange("value", oldValue, value);
		
	}

	/**
	 * {@inheritDoc}
	 *
	 * Check for equality with another Object / cell
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (modified != other.modified)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	/**
	 * <p>Getter for the field <code>comment</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getName() {
		return name;
	}

	/**
	 * checks if a name starts with this text we supply*
	 *
	 * @return true if it is
	 * @param textToCompareAgainst a {@link java.lang.String} object
	 */
	public boolean getNameStartsWith(String textToCompareAgainst) {
		if (name != null) {
			return name.startsWith(textToCompareAgainst);

		}

		// otherwise
		return false;
	}

	/**
	 * <p>Getter for the field <code>nextName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getNextName() {
		return nextName;
	}

	/**
	 * <p>Getter for the field <code>originalCellCol</code>.</p>
	 *
	 * @return a int
	 */
	public int getOriginalCellCol() {
		return originalCellCol;
	}

	/**
	 * Get the original Cell reference (if available) - e.g. the Cell address from
	 * the Original (Apache Poi) Spreadsheet
	 *
	 * @return - null if this is not available
	 */
	public String getOriginalCellReference() {
		if (this.originalCellCol > -1 && this.originalCellRow > -1) {
			return ("R" + originalCellRow + "C" + originalCellCol);
		}

		return null;
	}

	/**
	 * <p>Getter for the field <code>originalCellRow</code>.</p>
	 *
	 * @return a int
	 */
	public int getOriginalCellRow() {
		return originalCellRow;
	}

	/**
	 * Get the original Table reference (if available) - e.g.from the Original (Word
	 * Table, sheet in Spreadsheet) that this came from
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getOriginalTableReference() {

		return originalTableReference;
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link java.lang.Object} object
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * If possible, get the value of the Cell as a Boolean
	 *
	 * @return null if this conversion is not possible
	 */
	public Boolean getValueAsBoolean() {

		if (value == null) {
			return null;
		}

		if (value instanceof Boolean) {
			return ((Boolean) value);
		}

		if (value.toString().equalsIgnoreCase("true")) {
			return true;
		}

		if (value.toString().equalsIgnoreCase("false")) {
			return false;
		}

		// Default
		return null;
	}

	/**
	 * If possible, get the value of the Cell as an Long
	 *
	 * @return null if this conversion is not possible
	 */
	public Long getValueAsLong() {

		if (value == null) {
			return null;
		}

		if ((value instanceof Number)) {
			return ((Number) value).longValue();
		}

		if (NumberUtils.isCreatable(value.toString())) {
			return NumberUtils.createDouble(value.toString()).longValue();
		}

		// Default
		return null;
	}

	/**
	 * Get the value of the Cell as a String
	 *
	 * @return null if this conversion is not possible
	 */
	public String getValueAsText() {

		if (value != null) {
			return value.toString();
		}

		// default
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + (modified ? 1231 : 1237);
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/**
	 * Flag that allows us to signal if we have modified the cell
	 *
	 * @return a boolean
	 */
	public boolean isModified() {
		return modified;
	}

	/** {@inheritDoc} */
	public void propertyChange(PropertyChangeEvent arg0) {

		// Pass on the event to the registered listener
		changes.firePropertyChange(arg0);

	}

	/**
	 * Puts quotes around internal, but ensure null is still null
	 * 
	 * @param value to quote
	 * @return internal value with quotes around it (escaped) as approprate
	 */
	private String quoteInternalValueIfNotNumber() {

		if (value == null) {
			return null;
		}

		// if we can return this as a number, do so without quotes.
		Long tmpNum = getValueAsLong();
		if (tmpNum != null) {
			return tmpNum.toString();
		}

		Boolean tmpBool = getValueAsBoolean();
		if (tmpBool != null) {
			return tmpBool.toString();
		}

		// default treat as string and return
		return "\"" + value.toString() + "\"";
	}

	/**
	 * Puts quotes around strings, but ensure null is still null
	 * 
	 * @param value to quote
	 * @return same value with quots around it (escaped)
	 */
	private String quoteString(Object value) {

		if (value == null) {
			return null;
		}

		return "\"" + value.toString() + "\"";
	}

	/**
	 * <p>removePropertyChangeListener.</p>
	 *
	 * @param l a {@link java.beans.PropertyChangeListener} object
	 */
	public void removePropertyChangeListener(final PropertyChangeListener l) {
		this.changes.removePropertyChangeListener(l);
	}

//	public void setHoldingRange(Range holdingRange) {
//		this.holdingRange = holdingRange;
//	}

	/**
	 * <p>Setter for the field <code>comment</code>.</p>
	 *
	 * @param comment a {@link java.lang.String} object
	 */
	public void setComment(String comment) {
		String oldValue = this.comment;
		this.comment = comment;
		this.modified = true;
		this.changes.firePropertyChange("comment", oldValue, comment);
	}

	/**
	 * <p>Setter for the field <code>modified</code>.</p>
	 *
	 * @param modified a boolean
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
	}

	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 */
	public void setName(String name) {

		String oldValue = this.name;
		this.name = name;
		this.modified = true;
		this.changes.firePropertyChange("name", oldValue, name);

	}

	/**
	 * <p>Setter for the field <code>nextName</code>.</p>
	 *
	 * @param nextName a {@link java.lang.String} object
	 */
	public void setNextName(String nextName) {
		this.nextName = nextName;
	}

	/**
	 * * set the original Cell reference (if available) - e.g. the Cell address from
	 * the Original (Apache Poi) Spreadsheet
	 *
	 * @param cellCol a int
	 * @param cellRow a int
	 */
	public void setOriginalCellReference(int cellRow, int cellCol) {

		Object oldColValue = this.originalCellCol;
		Object oldRowValue = this.originalCellRow;

		this.originalCellCol = cellCol;
		this.originalCellRow = cellRow;

		this.modified = true;
		this.changes.firePropertyChange("originalCellReCol", oldColValue, cellCol);
		this.changes.firePropertyChange("originalCellReCol", oldRowValue, cellRow);
	}

	/**
	 * Set the original Table (Word Table, sheet in Spreadsheet) that this came from
	 *
	 * @param newOriginalSheetReference a {@link java.lang.String} object
	 */
	public void setOriginalTableReference(String newOriginalSheetReference) {

		Object oldValue = this.originalTableReference;
		this.originalTableReference = newOriginalSheetReference;
		this.modified = true;
		this.changes.firePropertyChange("value", oldValue, value);
	}

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a {@link java.lang.Object} object
	 */
	public void setValue(Object value) {
		Object oldValue = this.value;
		this.value = value;
		this.modified = true;
		this.changes.firePropertyChange("originalsheetreference", oldValue, value);
	}

	/**
	 * Print an internal representation of the Cell contents. This is the long
	 * version. If used for every cell in a large dataset it could cause an
	 * OutOfMemoryError. This version should be copy-pastable into rules / drl files
	 *
	 * @see toString()
	 * @return a {@link java.lang.String} object
	 */
	public String toLongString() {

		return "$cell : Cell (\n    name==" + quoteString(name) + "\n    value=="
				+ quoteInternalValueIfNotNumber() + "\n    comment==" + quoteString(comment) + "\n    modified=="
				+ modified + "\n    originalCellReference==" + quoteString(getOriginalCellReference())
				+ "\n    originalTableReference==" + quoteString(originalTableReference) + "\n    valueAsBoolean=="
				+ getValueAsBoolean() + "\n    valueAsLong==" + getValueAsLong() + "\n)";

	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {

		return "Cell ( name==\"" + name + "\" )";
	}

}
