package net.firstpartners.ui;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;
import java.util.logging.Logger;

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
	
	@Test void testDeepMapObjectToString(){
		assertNotNull()
		
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
