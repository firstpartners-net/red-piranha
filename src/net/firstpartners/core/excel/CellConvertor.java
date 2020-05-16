package net.firstpartners.core.excel;

import java.util.Date;
import org.apache.log4j.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import net.firstpartners.core.log.RpLogger;

/**
 * Convert from Excel values (as represented by Apache POI) to Javaeans (used by
 * RedPiranha / Rules) and vice versa
 */
public class CellConvertor {

	private static final Logger log = RpLogger.getLogger(CellConvertor.class.getName());

	/**
	 * Convert from Excel(Apache) to RedPiranha - Javabean version of a Cell
	 * 
	 * @param cellNameFromRange - allows us to treat this cell as a member of a range
	 * @param poiCell
	 * @return
	 */
	public static net.firstpartners.data.Cell convertPoiCellToRedCell(String cellNameFromRange,
			org.apache.poi.ss.usermodel.Cell poiCell) {

		// Check for null paramater
		if (poiCell == null) {
			return new net.firstpartners.data.Cell();
		}

		// Start building our new (Red) Cell
		net.firstpartners.data.Cell redCell = new net.firstpartners.data.Cell();

		// Keep a reference to the original cell location
		redCell.setOriginalCellReference(poiCell.getAddress().getRow(),poiCell.getAddress().getColumn());
		redCell.setOriginalTableReference(poiCell.getSheet().getSheetName());

		// The name makes them as a ranage
		redCell.setName(cellNameFromRange);

		org.apache.poi.ss.usermodel.CellType myCellType = poiCell.getCellType();
		log.debug("Working with Poi Cell Type:" + myCellType);

		Object value = null; // to capture the output
		switch (myCellType.toString()) {

		case "BOOLEAN":
			value = poiCell.getBooleanCellValue();
			break;
		case "NUMERIC":
			value = poiCell.getNumericCellValue();
			break;
		case "STRING":
			value = poiCell.getRichStringCellValue().getString();
			break;
		case "BLANK":
			value = "";
			break;
		case "ERROR":
			value = "";
			break;
		case "FORMULA":

			// treat forumlas using their cached values
			switch (poiCell.getCachedFormulaResultType()) {
			case BOOLEAN:
				value = poiCell.getBooleanCellValue();
				break;
			case NUMERIC:
				value = poiCell.getNumericCellValue();
				break;
			case STRING:
				value = poiCell.getRichStringCellValue();
				break;
			default:
				value = "";
				break;
			}

		}

		redCell.setValue(value);

		// copy over the comments
		Comment anyComment = poiCell.getCellComment();
		if (anyComment != null) {
			redCell.setComment(anyComment.getString().toString());
		}

		// Reset the modified flag
		redCell.setModified(false);

		return redCell;

	}

	/**
	 * Convert from Standard JavaBean to Excel (Apache Poi) Cells
	 * 
	 * @param rangeName
	 * @param poiCell
	 * @param redCell
	 */
	public static void convertRedCellToPoiCell(org.apache.poi.ss.usermodel.Workbook wb,
			org.apache.poi.ss.usermodel.Cell poiCell, net.firstpartners.data.Cell redCell) {

		// If the cell has no value , then it is null
		// We should create the cell, as we have a value to update into it
		// but for now we just ignfore the update
		if (poiCell == null) {
			log.debug("Ignoring null Poi Cell:");
			return;
		}

		if (!redCell.isModified()) {
			log.debug("Ignoring unmodified cell");
			return;
		}

		// Update the cell style to modified
		CellStyle style = getUpdatedPoiCellStyle(wb);
		poiCell.setCellStyle(style);

		// Update comment if specified
		if (redCell.getComment() != null && redCell.getComment() != "") {
			setPoiCellComment(wb, poiCell, "", redCell.getComment());

		}

		// copy over the comments
		Comment anyComment = poiCell.getCellComment();
		if (anyComment != null) {
			redCell.setComment(anyComment.getString().toString());
		}

		// Update the cell value
		Object redCellValue = redCell.getValue();

		// Ugly, but we can't switch on objects and
		// operator overloading breaks down as we have a handle to a generic 'object'
		if (redCellValue instanceof String) {

			log.debug("UpdatingCell:" + redCell.getName() + " value:" + redCellValue + " as String");
			poiCell.setCellValue(redCellValue.toString());

		} else if (redCellValue instanceof Boolean) {
			log.debug("UpdatingCell:" + redCell.getName() + " value:" + redCellValue + " as Boolean");
			poiCell.setCellValue((Boolean) redCellValue);

		} else if (redCellValue instanceof Number) {

			log.debug("UpdatingCell:" + redCell.getName() + " value:" + redCellValue + " as Number");
			Double number = ((Number) redCellValue).doubleValue();
			poiCell.setCellValue(number);

		} else if (redCellValue instanceof Date) {

			log.debug("UpdatingCell:" + redCell.getName() + " value:" + redCellValue + " as Date");
			poiCell.setCellValue((Date) redCellValue);

		} else if (redCellValue != null) {

			// Treat as object, use toString() method
			log.debug("UpdatingCell:" + redCell.getName() + " value:" + redCellValue + " as Generic Object");
			poiCell.setCellValue(redCellValue.toString());

		} else {

			// value is null, blank cell
			log.debug("UpdatingCell:" + redCell.getName() + " value is null, treating as empty string");
			poiCell.setCellValue("");

		}

		return;

	}



	/**
	 * Get the 'updated' style that we use to show that a cell value has been
	 * changed
	 * 
	 * @return
	 */
	protected static org.apache.poi.ss.usermodel.CellStyle getUpdatedPoiCellStyle(
			org.apache.poi.ss.usermodel.Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.ORANGE.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return style;

	}

	/**
	 * Add a comment to the Poi Workbook Cell
	 * 
	 * @param workbook
	 * @param cell
	 * @param author
	 * @param commentText
	 */
	static void setPoiCellComment(Workbook workbook, Cell cell, String author, String commentText) {

		// Create the anchor onto the workbook
		CreationHelper factory = workbook.getCreationHelper();
		ClientAnchor anchor = factory.createClientAnchor();

		// i found it useful to show the comment box at the bottom right corner
		anchor.setCol1(cell.getColumnIndex() + 1); // the box of the comment starts at this given column...
		anchor.setCol2(cell.getColumnIndex() + 3); // ...and ends at that given column
		anchor.setRow1(cell.getRowIndex() + 1); // one row below the cell...
		anchor.setRow2(cell.getRowIndex() + +5); // ...and 4 rows high

		Drawing<?> drawing = cell.getSheet().createDrawingPatriarch();
		Comment comment = drawing.createCellComment(anchor);

		// set the comment text and author
		comment.setString(factory.createRichTextString(commentText));
		comment.setAuthor(author);
		cell.setCellComment(comment);
	}

}
