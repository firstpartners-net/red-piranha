package net.firstpartners.core.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.firstpartners.TestConstants;
import net.firstpartners.core.RPException;
import net.firstpartners.core.drools.ClassAndLocation;
import net.firstpartners.core.json.JsonInputStrategy;
import net.firstpartners.data.RangeList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CSVOutputStrategyMultiLineTest {


	// Logger
	private static Logger log = LoggerFactory.getLogger(CSVOutputStrategyMultiLineTest.class);

	@Test
	public final void testCreateAppendToCSVNoOverwrite() throws IOException, InvalidFormatException, ClassNotFoundException {

		// Delete previous output file if it exists - file should not fail if it doesn't
		ResourceFinder.deleteOutputFileIfExists(TestConstants.CSV_APPEND_FILE);
		

		//confirm file does not exist yet
		// Delete previous output file if it exists - file should not fail if it doesn't

		try{
			File tmpOutputFileConfirm = ResourceFinder.getFileResource(TestConstants.CSV_APPEND_FILE);
			
			// confirm file does not exist
		//	assertTrue(tmpOutputFileConfirm==null);
			assertTrue(!tmpOutputFileConfirm.exists());
	
		} catch (FileNotFoundException fnfe){
			log.debug("Tmpfile "+TestConstants.CSV_APPEND_FILE+" not found when checking - this is ok");
		}


		//get sample data - medium sized
		JsonInputStrategy jsInput = new JsonInputStrategy(new ClassAndLocation(TestConstants.JSON_SERIAL_FILE_MEDIUM));
		RangeList testRange = jsInput.getJavaBeansFromSource();
		assertNotNull("input data should not be empty",testRange);

		//output to csv - pass 1
		CSVOutputStrategyMultiLine csvout = new CSVOutputStrategyMultiLine(TestConstants.CSV_APPEND_FILE);

		//Update our output strategy with additional info we want it to use
		HashMap<String,String> additionalOutputs = new HashMap<String,String>();
		additionalOutputs.put("Input","TestFile");
		additionalOutputs.put("Runtime",LocalDateTime.now().toString());
		csvout.additionalDataToInclude=additionalOutputs;

		// pass in the data that will be output and do the output
		csvout.setUpdates(null, testRange);
		csvout.processOutput();
		log.debug("csv data is saved in:"+TestConstants.CSV_TMP_FILE_MULTI_LINE);

		//test body and test hearers , capture number of rows
		String[] csvFileArray = csvout.getCsvFileAsStringArray();
		log.debug(csvFileArray[0]);
		int firstPassNumberRows = csvFileArray.length;

		//Test for correct headers (already read from file)
		assertEquals("Input,Runtime,Name,Value,Sheet,Ref,Table,Row,Col" ,csvFileArray[0]);

		//output our sample data a second time - pass 2
		csvout.processOutput();
		log.debug("More CSV data is saved in:"+TestConstants.CSV_APPEND_FILE);

		String[] csvFileArrayPass2 = csvout.getCsvFileAsStringArray();
		log.debug(csvFileArrayPass2[0]);
		int secondPassNumberRows = csvFileArrayPass2.length;

		//check that our number of rows is now doubled.
		log.debug("First Size:"+firstPassNumberRows+" second size:"+secondPassNumberRows);
		assertEquals("CSV is not double -1 to allow for headers", secondPassNumberRows,(firstPassNumberRows*2)-1);
		
		//Test for correct headers (already read from file)
		assertEquals("Input,Runtime,Name,Value,Sheet,Ref,Table,Row,Col" ,csvFileArrayPass2[0]);

		log.debug(csvFileArray[1]);

		//make sure there are not multiple empty values
		assertTrue("Should not be multiple ,,,,, in field",csvFileArray[1].indexOf(",,,,,,")<0);


		//now check for acme / roadrunner - the company names from our example 5 files
		File tmpResultCSV = ResourceFinder.getFileResource(TestConstants.CSV_APPEND_FILE);

		FileUtils.readFileToString(tmpResultCSV,Charset.defaultCharset()).contains("ACME");
		FileUtils.readFileToString(tmpResultCSV,Charset.defaultCharset()).contains("RoadRunner");
	}

	@Test
	public final void testSplitFieldNameIntoThreeParts() {


		CSVOutputStrategyMultiLine csvOut = new CSVOutputStrategyMultiLine(TestConstants.CSV_APPEND_FILE);

		String[] splitResult = csvOut.splitFieldName("Cash_NetClosingCashExclShortTermFacilities_BaseYear_plus_1_0");
		assertTrue(splitResult.length==3);

		log.debug(splitResult[0]);
		log.debug(splitResult[1]);
		log.debug(splitResult[2]);

		assertEquals(splitResult[0],"Cash");
		assertEquals(splitResult[1],"NetClosingCashExclShortTermFacilities");
		assertEquals(splitResult[2],"BaseYear_plus_1");

		splitResult = csvOut.splitFieldName("BS_InvoicediscountingYEBalance_31_12_19_0");
		assertTrue(splitResult.length==3);
		assertEquals(splitResult[0],"BS");
		assertEquals(splitResult[1],"InvoicediscountingYEBalance");
		assertEquals(splitResult[2],"31_12_19");

		splitResult = csvOut.splitFieldName("Cash_Row1861_BaseYear_plus_2_0");
		assertTrue(splitResult.length==3);
		assertEquals(splitResult[0],"Cash");
		assertEquals(splitResult[1],"Row1861");
		assertEquals(splitResult[2],"BaseYear_plus_2");

		//Try for single fields
		splitResult = csvOut.splitFieldName("CompanyName_0");
		assertTrue(splitResult.length==3);
		assertEquals(splitResult[0],"CompanyName");
		assertEquals(splitResult[1],"");
		assertEquals(splitResult[2],"");

		


	}



	@Test
	public final void testGenerateHeaders() {

		//Additional Header and other test info
		Map<String,String> additionalInfo = new HashMap<String,String>();
		additionalInfo.put("date-field","someDate");
		additionalInfo.put("file-field","someFile");
		String [] subRecords = {"sub1","sub2","sub3"};

		CSVOutputStrategyMultiLine test = new CSVOutputStrategyMultiLine("Dummy-output.csv");
		String [] headers = test.generateHeaders(additionalInfo);

		for (int i=0; i<headers.length;i++){
			log.debug(headers[i]);
		}


		assertNotNull(headers);
		assertTrue(headers.length==9);
		assertEquals(headers[0],"file-field");
		assertEquals(headers[1],"date-field");
		assertEquals(headers[2],"Name");
		assertEquals(headers[3],"Value");

		assertEquals(headers[8],"Col");

		//Important that body and header length are the same
		
		String [] bodyrow = test.generateBodyRow(additionalInfo,subRecords,"Name","Value","Sheet","Ref");
		assertEquals(headers.length,bodyrow.length);

	}

	@Test
	public final void testGenerateBodyLine() {

		Map<String,String> additionalInfo = new HashMap<String,String>();
		additionalInfo.put("date-field","someDate");
		additionalInfo.put("file-field","someFile");
		String [] subRecords = {"sub1","sub2","sub3"};

		CSVOutputStrategyMultiLine test = new CSVOutputStrategyMultiLine("Dummy-output.csv");
		String [] bodyrow = test.generateBodyRow(additionalInfo,subRecords,"Name","Value","Sheet","Ref");

		assertNotNull(bodyrow);

		for (int i=0; i<bodyrow.length;i++){
			log.debug(bodyrow[i]);
		}


		assertTrue(bodyrow.length==9);
		assertEquals(bodyrow[0],"someFile");
		assertEquals(bodyrow[1],"someDate");
		assertEquals(bodyrow[2],"Name");
		assertEquals(bodyrow[3],"Value");
		assertEquals(bodyrow[4],"Sheet");
		assertEquals(bodyrow[5],"Ref");
		assertEquals(bodyrow[6],"sub1");
		assertEquals(bodyrow[7],"sub2");
		assertEquals(bodyrow[8],"sub3");




	}



	/**
	 * Reasd Serialised JSON info, check we can output to CSV
	 * @throws RPException
	 */
	@Test
	public final void testJsonInCsvOut() throws Exception, RPException {


		// Delete previous output file if it exists - file should not fail if it doesn't
		try{
			File tmpOutputFile = ResourceFinder.getFileResource(TestConstants.CSV_TMP_FILE_MULTI_LINE);
			if(tmpOutputFile!=null){
				tmpOutputFile.delete();
			}
		} catch (FileNotFoundException fnfe){
			log.debug("Tmpfile "+TestConstants.CSV_TMP_FILE_MULTI_LINE+" not found, assume already deleted");
		}

		//Get our sample data
		JsonInputStrategy jsInput = new JsonInputStrategy(new ClassAndLocation(TestConstants.JSON_SERIAL_FILE_COMPLEX));
		RangeList testRange = jsInput.getJavaBeansFromSource();
		assertNotNull("input data should not be empty",testRange);

		//setup our CSV Outputter
		CSVOutputStrategyMultiLine csvout = new CSVOutputStrategyMultiLine(TestConstants.CSV_TMP_FILE_MULTI_LINE); 
		
		//Update our output strategy with additional info we want it to use
		HashMap<String,String> additionalOutputs = new HashMap<String,String>();
		additionalOutputs.put("Input","TestFile");
		additionalOutputs.put("Runtime",LocalDateTime.now().toString());
		csvout.additionalDataToInclude=additionalOutputs;

		
		// pass in teh data that will be output
		csvout.setUpdates(null, testRange);

		//Do the output
		csvout.processOutput();

		log.debug("csv data is saved in:"+TestConstants.CSV_TMP_FILE_MULTI_LINE);

		String[] csvFileArray = csvout.getCsvFileAsStringArray();
		log.debug(csvFileArray[0]);

		//Test for correct headers (already read from file)
		assertEquals("Input,Runtime,Name,Value,Sheet,Ref,Table,Row,Col" ,csvFileArray[0]);

		log.debug(csvFileArray[1]);

		//make sure there are not multiple empty values
		assertTrue("Should not be multiple ,,,,, in field",csvFileArray[1].indexOf(",,,,,,")<0);


	}

}
