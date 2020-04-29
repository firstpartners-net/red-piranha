package net.firstpartners.core.word;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import net.firstpartners.core.log.RpLogger;
import net.firstpartners.data.RangeList;

public class DocumentConvertor {

	private static final Logger log = RpLogger.getLogger(DocumentConvertor.class.getName());

	
	
	static void convertTables(String fileName) throws InvalidFormatException, IOException {
		
		
		
			FileInputStream fis = new FileInputStream(fileName);
			XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
			Iterator<IBodyElement> bodyElementIterator = xdoc.getBodyElementsIterator();
			while (bodyElementIterator.hasNext()) {
				IBodyElement element = bodyElementIterator.next();

				log.info("Element Type:"+element.toString());
				
				if ("TABLE".equalsIgnoreCase(element.getElementType().name())) {
					java.util.List<XWPFTable> tableList = element.getBody().getTables();
					for (XWPFTable table : tableList) {
						log.info("Total Number of Rows of Table:" + table.getNumberOfRows());
						for (int i = 0; i < table.getRows().size(); i++) {

							for (int j = 0; j < table.getRow(i).getTableCells().size(); j++) {
								log.info(table.getRow(i).getCell(j).getText());
							}
						}
					}
				}
			}
		
	}
	
	
	static void convertParas(String fileName) throws InvalidFormatException, IOException {
		
			FileInputStream fis = new FileInputStream(fileName);
			XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
			

			java.util.List<XWPFParagraph> paragraphList = xdoc.getParagraphs();

			for (XWPFParagraph paragraph : paragraphList) {

				log.info(paragraph.getText());
				log.info(paragraph.getAlignment());
				System.out.print(paragraph.getRuns().size());
				log.info(paragraph.getStyle());

				// Returns numbering format for this paragraph, eg bullet or lowerLetter.
				log.info(paragraph.getNumFmt());
				log.info(paragraph.getAlignment());

				log.info(paragraph.isWordWrapped());

				log.info("********************************************************************");
			}

	}
	

	
	/**
	 * 
	 * @param excelWorkBook
	 * @return
	 */
	public static RangeList convertFromPoiWordIntoRedRange(XWPFDocument wordDoc) {
		
		return new RangeList();
	}

	/*
	 * 
	 */
	public static void updateRedRangeintoPoiWord(XWPFDocument wordDocToUpdate, RangeList range) {
		// TODO Auto-generated method stub
		throw new IllegalAccessError("method not implemented yet");
	}
	
}
