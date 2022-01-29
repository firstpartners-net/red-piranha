package net.firstpartners.core.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.core.log.EmptyStatusUpdate;
import net.firstpartners.core.log.IStatusUpdate;
import net.firstpartners.data.RangeList;

/**
 * Strategy class to output a Json Document. <p
 * 
 * @author paul
 *
 */
public class JsonOutputStrategy implements IDocumentOutStrategy {

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// Name of the output file
	private String outputFile = null;

	// Hold the data until we are asked to process it
	//@SuppressWarnings("unused") // eclipse mistakenly marks this as unused
	private RangeList processedRange;

	// Handle to logger to GUI
	private IStatusUpdate userLogger = new EmptyStatusUpdate(); // we may change later -null safe

	/**
	 * Constructor - takes the name of the file we intend outputting to
	 * 
	 * @param outputFileName - file we want to output to
	 */
	public JsonOutputStrategy(String outputFileName) {
		this.outputFile = outputFileName;
	}

	/**
	 * Not needing to be implemented as part of this strategy
	 */
	@Override
	public void flush() {

	}

	/**
	 * We implement this is part of the interface, but don't do anything with it.
	 */
	@Override
	public void flush(IStatusUpdate logger) {

	}

	/**
	 * String representing where our output is going to
	 * 
	 * @return String - where we will output this to
	 */
	@Override
	public String getOutputDestination() {
		return "File:" + outputFile;
	}

	public String getOutputFileName() {
		return outputFile;
	}

	/**
	 * Process the output from the system
	 * 
	 * @throws IOException            - from underlying libs
	 * @throws InvalidFormatException - from underlying libs
	 */
	public void processOutput() throws IOException, InvalidFormatException {

		// create a writer - set to append (true)
		
		File fileToOutput = new File(outputFile);
		
		Writer writer = new FileWriter(fileToOutput);
		userLogger.debug("Writing Json to :" + fileToOutput.getAbsolutePath());
		log.debug("Writing Json to :" +fileToOutput.getAbsolutePath());
		
		// Objects for use in our loop
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String tmpJson = gson.toJson(processedRange);
		writer.write(tmpJson);

//		HashMap<String, Cell> returnValues = new HashMap<String, Cell>();
//		log.debug("combining all cells in all ranges, returning as Hashmap");
//
//		for (Range range : processedRange) {
//
//			for (net.firstpartners.data.Cell cell : range.values()) {
//				if (cell.getCellName() != null) {
//
//					tmpJson = gson.toJson(cell);
//					log.debug(tmpJson);
//					writer.write(tmpJson);
//				}
//
//			}
//
//		}

		writer.close();
		log.debug("Output complete");

	}

	/**
	 * Handle that we can pass informration back to the user
	 * 
	 * @param userLogger - this strategy does not use
	 */
	@Override
	public void setDocumentLogger(IStatusUpdate userLogger) {
		this.userLogger = userLogger;
	}

	/**
	 * Update a copy of our Original Document with new data
	 * 
	 * @param ignored      - normally the original file, but this strategy ignores
	 *                     it
	 * @param incomingData - our Javabeans to output
	 * @throws IOException fileToProcess
	 */
	public void setUpdates(OfficeDocument ignored, RangeList incomingData) throws IOException {

		// this converter ignores any original , we just store the range output
		processedRange = incomingData;

	}

}
