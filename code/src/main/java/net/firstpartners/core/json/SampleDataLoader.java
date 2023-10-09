package net.firstpartners.core.json;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.firstpartners.core.file.ResourceFinder;

/**
 * Class to load our sample data from JSON
 *
 * @author paulf
 * @version $Id: $Id
 */

public class SampleDataLoader {

	/** Constant <code>SAMPLE_INFO_IN_JSON="examples.json"</code> */
	public final static String SAMPLE_INFO_IN_JSON="examples.json";
	
	// Logger
	private static Logger log = LoggerFactory.getLogger(SampleDataLoader.class);
	
	/**
	 * Loads information on our samples
	 *
	 * @param jsonSampleDataFileName a {@link java.lang.String} object
	 * @throws java.io.IOException
	 * @throws com.fasterxml.jackson.databind.DatabindException
	 * @throws com.fasterxml.jackson.core.exc.StreamReadException
	 * @return a {@link java.util.List} object
	 */
	public static List<SampleData> loadSampleInformation(String jsonSampleDataFileName) throws StreamReadException, DatabindException, IOException{
		
		ObjectMapper objectMapper = new ObjectMapper();

		File jsonSource = ResourceFinder.getFileResourceUsingConfig(jsonSampleDataFileName);
		
		List<SampleData> sampleData = objectMapper.readValue(
		        jsonSource, 
		        new TypeReference<List<SampleData>>(){});

		log.debug("Read in Sample Data:"+sampleData);

		String contents="";

		// Now loop through and try loading readme.html if exists in directory
		for (SampleData singleSample : sampleData) {

			//check for readme.html	
			String sampleReadme = singleSample.getSubDirectory()+"readme.html";
			log.debug("trying to find readme.html for:"+sampleReadme);

			//Dind the sample file and read teh contents
			File readmeHtml = ResourceFinder.getFileResourceUsingConfig(sampleReadme);
			log.debug("Found file:"+readmeHtml.exists());

			contents = FileUtils.readFileToString(readmeHtml, "utf-8");
			singleSample.setSampleDescription(contents);
		
		}
			 
 
		
		return sampleData;
	}

	
	
	
}
