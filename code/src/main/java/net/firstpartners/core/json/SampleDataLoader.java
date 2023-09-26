package net.firstpartners.core.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.firstpartners.core.Config;
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
	 * @param appConfig - can be null, or can contain information on the directories to look in
	 * @throws java.io.IOException
	 * @throws com.fasterxml.jackson.databind.DatabindException
	 * @throws com.fasterxml.jackson.core.exc.StreamReadException
	 * @return a {@link java.util.List} object
	 */
	public static List<SampleData> loadSampleInformation(String jsonSampleDataFileName,Config appConfig) throws StreamReadException, DatabindException, IOException{
		
		ObjectMapper objectMapper = new ObjectMapper();

		File jsonSource = ResourceFinder.getFileResourceUsingConfig(jsonSampleDataFileName, appConfig);
		
		List<SampleData> sampleData = objectMapper.readValue(
		        jsonSource, 
		        new TypeReference<List<SampleData>>(){});

		log.debug("Read in Sample Data:"+sampleData);

		String contents="";

		// Now loop through and try loading readme.html if exists in directory
		for (SampleData singleSample : sampleData) {

			//check for readme.html	
			String sampleReadme = singleSample.getBaseDirectory()+"readme.html";
			log.debug("trying to find readme.html for:"+sampleReadme);

			File readmeHtml = ResourceFinder.getFileResourceUsingConfig(sampleReadme, appConfig);
			log.debug("Found file:"+readmeHtml.exists());

			//Read the contents of this  readmefile	
			try(FileInputStream inputStream = new FileInputStream(readmeHtml)) {     
    			contents = IOUtils.toString(inputStream);
    			// do something with everything string
				singleSample.setSampleDescription(contents);
}				//log.debug("contents:"+contents);
			}
		
		return sampleData;
	}

	
	
	
}
