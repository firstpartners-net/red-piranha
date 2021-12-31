package net.firstpartners.core.word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.data.Cell;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeList;

/**
 * Convert a Word (.docX) into our (Red) Javabeans.  Currently does not support the reverse translation.
 * 
 * @author PBrowne
 *
 */
public class DocumentXConvertor {

	// Logger
	private static final Logger log = LoggerFactory.getLogger(DocumentXConvertor.class);
	
	private static final String TABLE_IN_POI_WORD_MARKER = "TABLE";


	/**
	 * The name we store Word Paragraphs under
	 */
	public static final String WORD_PARAGRAPH_NAME_AS_RANGELIST = "PARA_";

	/**
	 * The name we store Word Tables under
	 */
	public static final String WORD_TABLE_ROW_AS_RANGELIST = "ROW_";

	/**
	 * Extract the Tables from a Word Document, convert to Red JavaBeans
	 * 
	 * @param wordDoc
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	static Collection<Range> convertTablesToBeans(XWPFDocument wordDoc) throws InvalidFormatException, IOException {

		// Our converted Beans
		Collection<Range> returnValues = new ArrayList<Range>();

		Iterator<IBodyElement> bodyElementIterator = wordDoc.getBodyElementsIterator();

		while (bodyElementIterator.hasNext()) {

			// Get the next POI Word Element
			IBodyElement element = bodyElementIterator.next();

			// Check if this is a taBLE
			if (TABLE_IN_POI_WORD_MARKER.equalsIgnoreCase(element.getElementType().name())) {

				// Get the Tables in this if its
				java.util.List<XWPFTable> tableList = element.getBody().getTables();
				int tableCounter =0;
				
				
				// Loop through the tables
				for (XWPFTable table : tableList) {

					//we start at one
					tableCounter++;
					
					//loop through the rows in this table
					for (int rowCounter = 0; rowCounter < table.getRows().size(); rowCounter++) {

						// Create a Range for this row of the table, using the value of the first cell
						Range thisRow = new Range();
						String possibleRowName = table.getRow(rowCounter).getCell(0).getText();
						if (possibleRowName != null) {
							thisRow.setRangeName(WORD_TABLE_ROW_AS_RANGELIST+possibleRowName);
						}

						//Loop throuh the Cells in this row
						for (int cellCounter = 0; cellCounter < table.getRow(rowCounter).getTableCells()
								.size(); cellCounter++) {

							String cellName = possibleRowName + "_" + cellCounter;
							Cell thisCell = new Cell(cellName, table.getRow(rowCounter).getCell(cellCounter).getText());
							thisCell.setOriginalTableReference("TABLE_"+tableCounter);
							thisCell.setOriginalCellReference(rowCounter,cellCounter);
							log.debug("created cell"+thisCell);
							thisRow.put(cellName, thisCell);
						}

						// Make sure we add this row to the return list
						returnValues.add(thisRow);
					}
				}
			}
		}

		return returnValues;

	}

	/**
	 * Extract the Tables from a Word Document, convert to Red JavaBeans
	 * 
	 * @param wordDoc
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	static Collection<Range> convertParasToBeans(XWPFDocument wordDoc) throws InvalidFormatException, IOException {

		Collection<Range> returnValues = new ArrayList<Range>();
		int counter = 0;

		java.util.List<XWPFParagraph> paragraphList = wordDoc.getParagraphs();

		for (XWPFParagraph paragraph : paragraphList) {

			counter++; // make is start at 1
			String name = WORD_PARAGRAPH_NAME_AS_RANGELIST + counter;

			Range currentPara = new Range(name);
			Cell redCell = new Cell(name, paragraph.getText());

			// Add the return values
			currentPara.put(name, redCell);
			returnValues.add(currentPara);
		}

		return returnValues;

	}

	/**
	 * Convert from WordDocX (Apache Poi) into our Javabean format
	 * @param wordDoc 
	 * @return RangeList
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static RangeList convertFromPoiWordIntoRedRange(XWPFDocument wordDoc)
			throws InvalidFormatException, IOException {

		RangeList returnList = new RangeList();

		Collection<Range> paras = convertParasToBeans(wordDoc);
		Collection<Range> tables = convertTablesToBeans(wordDoc);

		returnList.addAll(paras);
		returnList.addAll(tables);

		return returnList;
	}

}
