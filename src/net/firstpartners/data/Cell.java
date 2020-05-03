package net.firstpartners.data;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * JavaBean equivalent of a cell in an Excel Spreadsheet. Since we also map from
 * other sources into these classes, a Cell could be a cell from a table in a
 * Word Document. For simplicity, we map Paragraphs from a word document into a
 * cell (to making writing rules easier)
 * 
 */
public class Cell implements PropertyChangeListener, Serializable {

	private static final long serialVersionUID = -763504507901540819L;

	private String cellName = null;

	private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private String comment;

	private boolean modified = false;

	private String originalCellReference = null;

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
	 * @param cellName
	 * @param value
	 */
	public Cell(String cellName, Object value) {
		super();
		this.cellName = cellName;
		this.value = value;
	}

	public void addPropertyChangeListener(final PropertyChangeListener l) {
		this.changes.addPropertyChangeListener(l);
	}

	/**
	 * Check for equality with another Object / cell
	 * 
	 * @param obj - the object to compare us to
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
		if (cellName == null) {
			if (other.cellName != null)
				return false;
		} else if (!cellName.equals(other.cellName))
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
	 * If possible, get the value of the Cell as a Boolean
	 * 
	 * @return null if this conversion is not possible
	 */
	public Boolean getBooleanValue() {
		if ((value != null) && (value instanceof Boolean)) {
			return ((Boolean) value);
		}

		// Default
		return null;
	}

	public String getCellName() {
		return cellName;
	}

	public String getComment() {
		return comment;
	}


	/**
	 * If possible, get the value of the Cell as an Integer
	 * 
	 * @return null if this conversion is not possible
	 */
	public Integer getIntValue() {
		if ((value != null) && (value instanceof Number)) {
			return ((Number) value).intValue();
		}

		// Default
		return null;
	}

	/**
	 * Get the original Cell reference (if available) - e.g. the Cell address from
	 * the Original (Apache Poi) Spreadsheet
	 * 
	 * @return - null if this is not available
	 */
	public String getOriginalCellReference() {
		return originalCellReference;
	}

	/**
	 * Get the original Table reference (if available) - e.g.from the Original
	 * (Word Table, sheet in Spreadsheet) that this came from
	 * @return
	 */
	public String getOriginalTableReference() {
		return originalTableReference;
	}

	public Object getValue() {
		return value;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cellName == null) ? 0 : cellName.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + (modified ? 1231 : 1237);
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/**
	 * Flag that allows us to signal if we have modified the cell
	 * 
	 * @return
	 */
	public boolean isModified() {
		return modified;
	}

	public void propertyChange(PropertyChangeEvent arg0) {

		// Pass on the event to the registered listener
		changes.firePropertyChange(arg0);

	}

	public void removePropertyChangeListener(final PropertyChangeListener l) {
		this.changes.removePropertyChangeListener(l);
	}

	public void setCellName(String cellName) {

		String oldValue = this.cellName;
		this.cellName = cellName;
		this.modified = true;
		this.changes.firePropertyChange("cellName", oldValue, cellName);

	}

	public void setComment(String comment) {
		String oldValue = this.comment;
		this.comment = comment;
		this.modified = true;
		this.changes.firePropertyChange("comment", oldValue, comment);
	}

//	public void setHoldingRange(Range holdingRange) {
//		this.holdingRange = holdingRange;
//	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	/**
	 * set the original Cell reference (if available) - e.g. the Cell address from
	 * the Original (Apache Poi) Spreadsheet
	 * @param originalCellReference
	 */
	public void setOriginalCellReference(String originalCellReference) {
				
		Object oldValue = this.originalCellReference;
		this.originalCellReference = originalCellReference;
		this.modified = true;
		this.changes.firePropertyChange("originalCellReference", oldValue, value);
	}
	/**
	 * Set the original Table (Word Table, sheet in Spreadsheet) that this came from
	 * @param newOriginalSheetReference
	 */
	public void setOriginalTableRefernece(String newOriginalSheetReference) {

		Object oldValue = this.originalTableReference;
		this.originalTableReference = newOriginalSheetReference;
		this.modified = true;
		this.changes.firePropertyChange("value", oldValue, value);
	}

	public void setValue(Object value) {
		Object oldValue = this.value;
		this.value = value;
		this.modified = true;
		this.changes.firePropertyChange("originalsheetreference", oldValue, value);
	}

	/**
	 * Print an internal representation of the Cell contents. This is the long
	 * version. If used for every cell in a large dataset it could cause an
	 * OutOfMemoryError
	 * 
	 * @see toString()
	 */
	public String toLongString() {

		return "Cell [cellName=" + cellName + ", value=" + value + ", comment=" + comment + ", modified=" + modified + ", originalCellReference=" + originalCellReference
				+ ", originalTableReference=" + originalTableReference  + "]";

	}

	/**
	 * @see toLongString() if more comprehensive data needed This is the short
	 *      version.
	 */
	@Override
	public String toString() {

		return "Cell [cellName=" + cellName + "]";
	}

}
