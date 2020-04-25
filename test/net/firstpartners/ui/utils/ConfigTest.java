package net.firstpartners.ui.utils;

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
import net.firstpartners.data.RangeHolder;

public class ConfigTest {

	
	// Logger
	private static final Logger log = Logger.getLogger(ConfigTest.class.getName());
	@SuppressWarnings("unused")
	private static RangeHolder redData=null;

	
	@Before
	public void beforeClass() throws IOException, ClassNotFoundException {

		FileInputStream fileIn = new FileInputStream(RangeConvertorTest.SAVED_RANGEHOLDER_DATA);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		redData = (RangeHolder) in.readObject();
		in.close();
		fileIn.close();
	}

	@Test
	public final void testReadConfig() {
		
		Properties prop = Config.readConfig();
		log.fine(prop.toString());
		
		assertNotNull(prop.get(Config.DRL1));
		assertNull(prop.get(Config.DRL2));
		assertNull(prop.get(Config.DRL3));
		assertNotNull(prop.get(Config.EXCEL_INPUT));
		assertNotNull(prop.get(Config.EXCEL_OUTPUT));
		assertNotNull(prop.get(Config.LOG_FILE_NAME));
	}

}
