package net.firstpartners.core.spreadsheet;

import java.util.Date;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;

import net.firstpartners.core.log.RpLogger;

//Convert from POI Cell into our type of cell

public class CellConvertor {

	private static final Logger log = RpLogger.getLogger(CellConvertor.class.getName());

	/**
	 * Convert from Excel(Apache) to RedPiranha - Javabean version of a Cell
	 * 
	 * @param uniqueCellHandle
	 * @param poiCell
	 * @return
	 */
	public static net.firstpartners.core.spreadsheet.Cell convertPoiCellToRedCell(String uniqueCellHandle,
			org.apache.poi.ss.usermodel.Cell poiCell) {

		net.firstpartners.core.spreadsheet.Cell redCell = new net.firstpartners.core.spreadsheet.Cell();
		redCell.setCellName(uniqueCellHandle);
		Object value = null;

		if (poiCell != null) {
			int cellType = poiCell.getCellType();

			switch (cellType) {

			case Cell.CELL_TYPE_BLANK:
				value = "blank";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = poiCell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_ERROR:
				value = "error";
				break;
			case Cell.CELL_TYPE_FORMULA:
				value = "formula";
			case Cell.CELL_TYPE_NUMERIC:
				value = poiCell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_STRING:
				RichTextString richValue = poiCell.getRichStringCellValue();
				if (richValue != null) {
					value = richValue.getString();
				}
				break;
			default:
				value = "default";// do nothing
			}
		} else {
			value = null;
		}

		redCell.setValue(value);

		// Reset the modified flag
		redCell.setModified(false);

		return redCell;
	}

	/**
	 * Convert from Standard JavaBean to Excel
	 * 
	 * @param rangeName
	 * @param poiCell
	 * @param cell
	 */
	public static void convertRedCellToPoiCell(org.apache.poi.ss.usermodel.Workbook wb,
			org.apache.poi.ss.usermodel.Cell poiCell, net.firstpartners.core.spreadsheet.Cell cell) {

		// If the cell has no value , then it is null
		// We should create the cell, as we have a value to update into it
		// but for now we just ignfore the update
		if (poiCell == null) {
			log.fine("Ignoring null value:");
			return;
		}

		CellStyle style = getExcelCellStyle(wb);

		if (cell.isModified()) {

			// Set the generic updated style
			poiCell.setCellStyle(style);

			Object value = cell.getValue();

			// Ugly, but we can't switch on objects and
			// operator overloading breaks down as we have a handle to a generic 'object'
			if (value != null && value instanceof String) {

				log.finest("UpdatingCell:" + cell.getCellName() + " value:" + value + " as String");
				poiCell.setCellValue(value.toString());
				poiCell.setCellType(Cell.CELL_TYPE_STRING);

			} else if (value != null && value instanceof Boolean) {
				log.finest("UpdatingCell:" + cell.getCellName() + " value:" + value + " as Boolean");
				poiCell.setCellValue((Boolean) cell.getValue());
				poiCell.setCellType(Cell.CELL_TYPE_BOOLEAN);

			} else if (value != null && value instanceof Number) {
				log.finest("UpdatingCell:" + cell.getCellName() + " value:" + value + " as Number");
				Double number = ((Number) value).doubleValue();
				poiCell.setCellValue(number);
				poiCell.setCellType(Cell.CELL_TYPE_NUMERIC);

			} else if (value != null && value instanceof Date) {

				// Excel dates are numbers with a special style
				// We rely on the cell being in the correct date format already!
				log.finest("UpdatingCell:" + cell.getCellName() + " value:" + value + " as Date");
				Double number = ((Number) value).doubleValue();
				poiCell.setCellValue(number);
				poiCell.setCellType(Cell.CELL_TYPE_NUMERIC);

			} else if (value != null) {

				// Treat as object, use toString() method
				log.finest("UpdatingCell:" + cell.getCellName() + " value:" + value + " as Generic Object");
				poiCell.setCellValue(value.toString());
				poiCell.setCellType(Cell.CELL_TYPE_STRING);

			} else {

				// value is null, blank cell
				log.finest("UpdatingCell:" + cell.getCellName() + " value is null");
				poiCell.setCellValue("");
				poiCell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK);

			}

		}

	}

	/**
	 * Get the 'updated' style that we use to show that a cell value has been
	 * changed
	 * 
	 * @return
	 */
	protected static org.apache.poi.ss.usermodel.CellStyle getExcelCellStyle(org.apache.poi.ss.usermodel.Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.ORANGE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		return style;

	}

}
