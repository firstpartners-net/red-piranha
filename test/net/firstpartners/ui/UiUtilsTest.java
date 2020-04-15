package net.firstpartners.ui;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Properties;
import java.util.logging.Logger;

import org.junit.Test;

public class UiUtilsTest {

	// Logger
	private static final Logger log = Logger.getLogger(UiUtilsTest.class.getName());

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
