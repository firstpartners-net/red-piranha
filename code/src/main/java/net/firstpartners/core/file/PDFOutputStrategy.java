package net.firstpartners.core.file;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeList;

/**
 * Strategy class to output of PDF Document
 * 
 * @author paul
 *
 */
public class PDFOutputStrategy implements IDocumentOutStrategy {

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	// Name of the outputfile
	private String outputFileName = null;

	private RangeList savedRange;

	/**
	 * Constructor - takes the name of the file we intend outputting to
	 * 
	 * @param outputFileName
	 */
	public PDFOutputStrategy(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	/**
	 * Not needing to be implemented as part of this strategy
	 */
	@Override
	public void flush() {

	}

	@Override
	public void flush(ILogger logger) {
	}

	/**
	 * String representing where our output is going to
	 */
	@Override
	public String getOutputDestination() {
		return "File:" + outputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	/**
	 * Outputs Red-Piranha's own internal format to a Logging Console
	 * 
	 * @param ranges
	 */
	void outputToConsole(RangeList ranges) {
		for (Range r : ranges) {
			log.debug(r.toString());
		}
	}

	/**
	 * Process the output from the system
	 * 
	 * @param fileToProcess
	 * @param outputFileName
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public void processOutput() throws IOException, InvalidFormatException {

		// Open the outputfile as a stream
		log.debug("trying to output to:" + savedRange);

		PDDocument doc = new PDDocument();
		try {
			PDPage page = new PDPage();
			doc.addPage(page);

			PDFont font = PDType1Font.HELVETICA_BOLD;

			PDPageContentStream contents = new PDPageContentStream(doc, page);
			contents.beginText();
			contents.setFont(font, 12);
			contents.newLineAtOffset(50, 700);
			contents.showText(this.savedRange.toString());
			contents.endText();
			contents.close();

			doc.save(outputFileName);
		} finally {
			doc.close();
		}

	}

	/**
	 * Not needing to be implemented as part of this strategy
	 */
	@Override
	public void setDocumentLogger(ILogger spreadSheetLogger) {

	}

	/**
	 * Update a copy of our Original Document with new data
	 * 
	 * @param ranges
	 * @throws IOException
	 */
	public void setUpdates(OfficeDocument fileToProcess, RangeList range) throws IOException {

		// we ignore the fileToProcess, just more interested in ragne to process later
		// update
		this.savedRange = range;

	}

}
