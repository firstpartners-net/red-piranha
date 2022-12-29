package net.firstpartners.core.word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.firstpartners.data.Cell;
import net.firstpartners.data.RangeList;

/**
 * Convert a Word (.docX) into our (Red) Javabeans. Currently does not support
 * the reverse translation.
 *
 * @author PBrowne
 * @version $Id: $Id
 */
public class DocumentConvertor {

	private static final String TABLE_MARKER = "TABLE_";

	// Logger
	private static Logger log = LoggerFactory.getLogger(DocumentConvertor.class);


	// first number of chars we use in names
	/** Constant <code>FIRST_X_IN_NAMES=30</code> */
	public static final int FIRST_X_IN_NAMES = 30;

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

		Cell previousCell = new Cell();
		
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
				possibleRowName = tidyText(possibleRowName);
				
				if ((possibleRowName != null)&&(possibleRowName.length()>0)) {

					// truncate and add rowname
					if (possibleRowName.length() > FIRST_X_IN_NAMES) {
						possibleRowName = possibleRowName.substring(0, FIRST_X_IN_NAMES);
					}

					thisRow.setRangeName(WORD_TABLE_ROW_AS_RANGELIST + possibleRowName);
				} else {
					
					//It is possible that the first cell in a row is blank	
					// we give it a name as there may be other useful values in the row (col 2, col3 etc)
					possibleRowName= TABLE_MARKER+tableCounter+"_"+WORD_TABLE_ROW_AS_RANGELIST+rowIndex;
					thisRow.setRangeName(TABLE_MARKER+tableCounter+"_"+WORD_TABLE_ROW_AS_RANGELIST+rowIndex);
			
				}

				// Loop through the cell in the row
				for (int colIndex = 0; colIndex < row.numCells(); colIndex++) {

					String cellName = possibleRowName + "_" + colIndex;
					String paraText = tidyText(table.getRow(rowIndex).getCell(colIndex).getParagraph(0).text());

					Cell thisCell = new Cell(cellName, paraText);	
					thisCell.setOriginalTableReference(TABLE_MARKER + tableCounter);
					thisCell.setOriginalCellReference(rowIndex,colIndex);
					log.debug("created cell" + thisCell);
					thisRow.put(cellName, thisCell);
					
					//Cells are aware of the next name
					previousCell.setNextName(cellName);
					previousCell = thisCell;
					
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

		// Get a handle to the paragraphs in our document
		org.apache.poi.hwpf.usermodel.Range range = wordDoc.getRange();
		int num = range.numParagraphs();
		Paragraph para;
		
		Cell previousCell = new Cell();

		// Loop through the document
		for (int i = 0; i < num; i++) {


			//The current text we ae working with
			para = range.getParagraph(i);

			
			String possibleText = tidyText(para.text());
			
			
			//Filter out empty and cells with only spaces
			if ((possibleText != null) && (possibleText.length()>0) ) {

				//Only update for non empty paras
				counter++; // make is start at 1 or more

				
				//Log info on our para
				
				String name = "";
				if (possibleText.length() > FIRST_X_IN_NAMES) {
					name = WORD_PARAGRAPH_NAME_AS_RANGELIST + counter + "_"
							+ possibleText.substring(0, FIRST_X_IN_NAMES);
				} else {
					name = WORD_PARAGRAPH_NAME_AS_RANGELIST + counter + "_" + possibleText;
				}
				
				
				
				// Add this to our list of values
				net.firstpartners.data.Range currentPara = new net.firstpartners.data.Range(name);
				Cell redCell = new Cell(name, possibleText);

				//Cells are aware of the next name
				previousCell.setNextName(name);
				previousCell = redCell;
				
				// Add the return values
				currentPara.put(name, redCell);
				returnValues.add(currentPara);
			}

		}

		return returnValues;

	}
	
	/**
	 * Tidy the Name - remove leading / trailing spaces, escape the string, remove special character
	 * @param incomingName
	 * @return
	 */
	static String tidyText(String incomingName) {
		
		//Defaut return value
		String returnString =null;
		
		if(incomingName!=null) {
		
			//Replace all carraige returns with spaces
			incomingName= incomingName.replace((char)7, (char)32);
			incomingName= incomingName.replace((char)8, (char)32);
			incomingName= incomingName.replace((char)19, (char)32);
			incomingName = incomingName.replaceAll("[\\r\\n]", "");
			
			//remove all trailing spaces - this may give us an empty string.
			incomingName = incomingName.strip();
			
			//For safety later - make Json safe
			returnString = incomingName;

			
		}
			
		
		return returnString;
	}

	/**
	 * Convert from WordDoc (Apache Poi) into our Javabean format
	 *
	 * @param wordDoc a {@link org.apache.poi.hwpf.HWPFDocument} object
	 * @throws java.io.IOException
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
	 * @return a {@link net.firstpartners.data.RangeList} object
	 */
	public static RangeList convertFromPoiWordIntoRedRange(HWPFDocument wordDoc)
			throws InvalidFormatException, IOException {

		RangeList returnList = new RangeList();

		//Convert Tables then Paras - more useful info in the former, and table info will then show up first
		Collection<net.firstpartners.data.Range> tables = convertTablesToBeans(wordDoc);
		Collection<net.firstpartners.data.Range> paras = convertParasToBeans(wordDoc);
		
		returnList.addAll(tables);
		returnList.addAll(paras);
		

		return returnList;
	}

}
