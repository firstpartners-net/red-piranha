package net.firstpartners.core.json;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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
 * Strategy class to output a Json Document.
 *
 * @author paul
 * @version $Id: $Id
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

	//sub directory e.g. for samples
	private String subDirectory;

	
	/**
	 * Constructor - takes the name of the file we intend outputing to
	 *
	 * @param outputFileName - file we want to output to
	 */
	public JsonOutputStrategy(String outputFileName) {
		this.outputFile = outputFileName;
	}



	public String getSubDirectory() {
		return subDirectory;
	}



	public void setSubDirectory(String subDirectory) {
		this.subDirectory = subDirectory;
	}
	
	/**
	 * To conform to the interface - not (yet) implemented in this strategy
	 */
	public void setAdditionalOutputData(Map<String,String> ignored){}


	/**
	 * {@inheritDoc}
	 *
	 * String representing where our output is going to
	 */
	@Override
	public String getOutputDestination() {
		return "File:" + outputFile;
	}

	/**
	 * <p>getOutputFileName.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getOutputFileName() {
		return outputFile;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Process the output from the system
	 */
	@Override
	public void processOutput() throws IOException, InvalidFormatException {

		// create a writer - set to append (true)
		String outputDir = ResourceFinder.getDirectoryResourceUsingConfig(appConfig);
		
		log.debug("Writing Json to :" +outputDir+ outputFile);

		// Objects for use in our loop
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
		writer.writeValue(new File(outputDir+outputFile), processedRange);

		log.debug("Output complete");

	}

	/** {@inheritDoc} */
	public void setConfig(Config appConfig) {
		this.appConfig = appConfig;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Update a copy of our Original Document with new data
	 */
	@Override
	public void setUpdates(OfficeDocument ignored, RangeList incomingData) throws IOException {

		// this converter ignores any original , we just store the range output
		processedRange = incomingData;

	}

}
