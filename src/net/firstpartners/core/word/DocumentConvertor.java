package net.firstpartners.core.word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.firstpartners.core.log.RpLogger;
import net.firstpartners.data.Cell;
import net.firstpartners.data.RangeList;

/**
 * Convert a Word (.docX) into our (Red) Javabeans. Currently does not support
 * the reverse translation.
 * 
 * {@link https://www.devglan.com/corejava/parsing-word-document-example}
 * 
 * @author PBrowne
 *
 */
public class DocumentConvertor {

	private static final String TABLE_MARKER = "TABLE_";

	// Logger
	private static final Logger log = RpLogger.getLogger(DocumentConvertor.class.getName());

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
	static Collection<net.firstpartners.data.Range> convertTablesToBeans(HWPFDocument doc)
			throws InvalidFormatException, IOException {

		// Our converted Beans
		Collection<net.firstpartners.data.Range> returnValues = new ArrayList<net.firstpartners.data.Range>();

		// Handle to all the tables in the document
		org.apache.poi.hwpf.usermodel.Range range = doc.getRange();
		TableIterator itr = new TableIterator(range);

		int tableCounter = 0;

		// Loop through all the tables
		while (itr.hasNext()) {

			tableCounter++;

			Table table = itr.next();
			for (int rowIndex = 0; rowIndex < table.numRows(); rowIndex++) {

				TableRow row = table.getRow(rowIndex);

				// Create a Range for this row of the table, using the value of the first cell
				net.firstpartners.data.Range thisRow = new net.firstpartners.data.Range();
				String possibleRowName = table.getRow(rowIndex).getCell(0).getParagraph(0).text();
				if (possibleRowName != null) {
					thisRow.setRangeName(WORD_TABLE_ROW_AS_RANGELIST + possibleRowName);
				}

				for (int colIndex = 0; colIndex < row.numCells(); colIndex++) {

					String cellName = possibleRowName + "_" + colIndex;
					Cell thisCell = new Cell(cellName, table.getRow(rowIndex).getCell(colIndex).getParagraph(0).text());
					thisCell.setOriginalTableRefernece(TABLE_MARKER + tableCounter);
					thisCell.setOriginalCellReference("R:" + rowIndex + "C:" + colIndex);
					log.debug("created cell" + thisCell);
					thisRow.put(cellName, thisCell);
				}

				// Make sure we add this row to the return list
				returnValues.add(thisRow);
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
	static Collection<net.firstpartners.data.Range> convertParasToBeans(HWPFDocument wordDoc)
			throws InvalidFormatException, IOException {

		Collection<net.firstpartners.data.Range> returnValues = new ArrayList<net.firstpartners.data.Range>();
		int counter = 0;
		
		//Get a handle to the paragraphs in our document
		org.apache.poi.hwpf.usermodel.Range range = wordDoc.getRange();
		int num = range.numParagraphs();
		Paragraph para;
		
		//Loop through the document
		for (int i = 0; i < num; i++) {

			counter++; // make is start at 1 or more
			
			para = range.getParagraph(i);
			
			String name = WORD_PARAGRAPH_NAME_AS_RANGELIST + counter;

			net.firstpartners.data.Range currentPara = new net.firstpartners.data.Range(name);
			Cell redCell = new Cell(name, para.text());

			// Add the return values
			currentPara.put(name, redCell);
			returnValues.add(currentPara);
			
		}
		

		return returnValues;

	}

	/**
	 * 
	 * @param excelWorkBook
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static RangeList convertFromPoiWordIntoRedRange(HWPFDocument wordDoc)
			throws InvalidFormatException, IOException {

		RangeList returnList = new RangeList();

		Collection<net.firstpartners.data.Range> paras = convertParasToBeans(wordDoc);
		Collection<net.firstpartners.data.Range> tables = convertTablesToBeans(wordDoc);

		returnList.addAll(paras);
		returnList.addAll(tables);

		return returnList;
	}

}
