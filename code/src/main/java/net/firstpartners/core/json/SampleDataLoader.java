package net.firstpartners.core.json;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import net.firstpartners.core.Config;
import net.firstpartners.core.file.ResourceFinder;

/**
 * Class to load our sample data from JSON
 * @author paulf
 *
 */
public class SampleDataLoader {

	public final static String EXAMPLE_INFO_IN_JSON="examples.json";
	
	// Logger
	private static Logger log = LoggerFactory.getLogger(SampleDataLoader.class);
	
	/**
	 * Generate a sample outputfile to show the format
	 * Rename the sample file once you have generated the format - no guarantee that 
	 * it will not be overwritten later.
	 * @param outputFileName
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamWriteException 
	 */
	public static void outputSampleFormat(String outputFileName) throws StreamWriteException, DatabindException, IOException {
		
		List<SampleData> allSamples = new ArrayList<SampleData>();
		
		SampleData sample1 = new SampleData();
		sample1.setId(1);
		sample1.setInputFileLocation("/examples/input.xls");
		sample1.setRuleFileLocation("/examples/rule.drl");
		sample1.setDslFileLocation("/examples/rule.dsl");
		sample1.setOutputFileLocation("/examples.output.xls");
		sample1.setDescriptiontext("blah blah blah");
		allSamples.add(sample1);
		
		SampleData sample2 = new SampleData();
		sample2.setId(2);
		sample2.setInputFileLocation("/examples/input2.xls");
		sample2.setRuleFileLocation("/examples/rule2.drl");
		sample2.setDslFileLocation("/examples/rule2.dsl");
		sample2.setOutputFileLocation("/examples/output2.xls");
		sample2.setDescriptiontext("blah2 blah2 blah2 ");
		allSamples.add(sample2);
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
		writer.writeValue(new File(outputFileName), allSamples);

		log.debug("Output complete to:"+outputFileName);
		
	}
	
	/**
	 * Loads information on our samples
	 * @param jsonSampleDataFileName
	 * @param appConfig - can be null, or can contain information on the directories to look in 
	 *
	 * @return
	 * @throws IOException 
	 * @throws DatabindException 
	 * @throws StreamReadException 
	 */
	public static List<SampleData> loadSampleInformation(String jsonSampleDataFileName,Config appConfig) throws StreamReadException, DatabindException, IOException{
		
		ObjectMapper objectMapper = new ObjectMapper();

		File jsonSource = ResourceFinder.getFileResourceUsingConfig(jsonSampleDataFileName, appConfig);
		
		List<SampleData> sampleData = objectMapper.readValue(
		        jsonSource, 
		        new TypeReference<List<SampleData>>(){});
		
		return sampleData;
	}
	
}
