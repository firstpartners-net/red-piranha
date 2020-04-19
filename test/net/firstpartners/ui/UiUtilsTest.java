package net.firstpartners.ui;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Properties;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;

import net.firstpartners.core.spreadsheet.RangeConvertorTest;
import net.firstpartners.core.spreadsheet.RangeHolder;

public class UiUtilsTest {

	
	// Logger
	private static final Logger log = Logger.getLogger(UiUtilsTest.class.getName());
	private static RangeHolder redData=null;

	
	@Before
	public void beforeClass() throws IOException, ClassNotFoundException {

		FileInputStream fileIn = new FileInputStream(RangeConvertorTest.SAVED_RANGEHOLDER_DATA);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		redData = (RangeHolder) in.readObject();
		in.close();
		fileIn.close();
	}
	
	@Test(expected = NullPointerException.class)
	public void testDeepMapObjectToStringNullFail() throws JAXBException{
		
		
		UiUtils.deepMapObjectToJSonString(null);
		
	}
	
	@Test
	public void testDeepMapObjectRandomObjectsFail() throws JAXBException{
		
		ArrayList<Object> testObjects = new ArrayList<Object>();
		testObjects.add( new HashMap<String,Object>());
		testObjects.add("some value");
		testObjects.add(new Object());
		testObjects.add(12);
		
		ListIterator<Object> loopList = testObjects.listIterator();
		while (loopList.hasNext()) {
			try {
				String result = UiUtils.deepMapObjectToJSonString(loopList.next());
				log.info(result);
				fail("Assertion should have caught previous pass");
				
			}catch (AssertionError ae) {
				//ignore - we expect to fail
			}
		}
		
	
		
	}
	
	@Test 
	public void testDeepMapObjectToString() throws JAXBException{
		assertNotNull(redData);
		String desc = UiUtils.deepMapObjectToJSonString(redData);
		assertNotNull(desc);
		log.info(desc);
	}

	@Test
	public final void testReadConfig() {
		
		Properties prop = UiUtils.readConfig();
		log.fine(prop.toString());
		
		assertNotNull(prop.get(UiUtils.DRL1));
		assertNull(prop.get(UiUtils.DRL2));
		assertNull(prop.get(UiUtils.DRL3));
		assertNotNull(prop.get(UiUtils.EXCEL_INPUT));
		assertNotNull(prop.get(UiUtils.EXCEL_OUTPUT));
		assertNotNull(prop.get(UiUtils.LOG_FILE_NAME));
	}

}
