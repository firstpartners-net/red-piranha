package net.firstpartners.core.json;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import net.firstpartners.core.Config;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.file.OfficeDocument;
import net.firstpartners.core.file.ResourceFinder;
import net.firstpartners.data.RangeList;

/**
 * Strategy class to output a Json Document. <p
 *
 * @author paul
 *
 */
public class JsonOutputStrategy implements IDocumentOutStrategy {

	private Config appConfig;

	// Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// Name of the output file
	private String outputFile = null;

	// Hold the data until we are asked to process it
	// @SuppressWarnings("unused") // eclipse mistakenly marks this as unused
	private RangeList processedRange;

	
	/**
	 * Constructor - takes the name of the file we intend outputting to
	 *
	 * @param outputFileName - file we want to output to
	 */
	public JsonOutputStrategy(String outputFileName) {
		this.outputFile = outputFileName;
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
	@Override
	public void processOutput() throws IOException, InvalidFormatException {

		// create a writer - set to append (true)
		String outputDir = ResourceFinder.getDirectoryResourceUsingConfig(appConfig);
		
		log.debug("Writing Json to :" + outputFile);

		// Objects for use in our loop

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
		writer.writeValue(new File(outputDir+outputFile), processedRange);

		log.debug("Output complete");

	}

	public void setConfig(Config appConfig) {
		this.appConfig = appConfig;
	}

	/**
	 * Update a copy of our Original Document with new data
	 *
	 * @param ignored      - normally the original file, but this strategy ignores
	 *                     it
	 * @param incomingData - our Javabeans to output
	 * @throws IOException fileToProcess
	 */
	@Override
	public void setUpdates(OfficeDocument ignored, RangeList incomingData) throws IOException {

		// this converter ignores any original , we just store the range output
		processedRange = incomingData;

	}

}
