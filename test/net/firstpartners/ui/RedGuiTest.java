package net.firstpartners.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Logger;

import javax.swing.UnsupportedLookAndFeelException;

import org.junit.Test;

import net.firstpartners.core.spreadsheet.RangeConvertorTest;
import net.firstpartners.data.RangeHolder;

/**
 * Test to call the main method
 * 
 * @author PBrowne
 *
 */
public class RedGuiTest {

	// Logger
	private static final Logger log = Logger.getLogger(RedGuiTest.class.getName());


	@Test
	public final void testCallMain() throws Throwable {

		// Check where we are
		File whereAmI = new File(".");
		log.info("Default file location:" + whereAmI.getAbsolutePath());

		// Call test

	}

	/**
	 * The Main method is more of a convenience for testing All it does is display
	 * the GUI
	 * 
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException, IOException {

		// Open the GUI
		log.fine("Opening GUI");
		RedGui player = new RedGui();

		// Get the test data
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		RangeHolder redData = null;
		fileIn = new FileInputStream(RangeConvertorTest.SAVED_RANGEHOLDER_DATA);
		in = new ObjectInputStream(fileIn);
		redData = (RangeHolder) in.readObject();
		//in.;
		fileIn.close();

		player.showPreRulesSnapShot(redData);
		player.showPostRulesSnapShot(redData);

		Runnable readRun = new Thread(player);
		readRun.run();
		
		

	}
}
