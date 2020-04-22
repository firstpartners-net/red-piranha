package net.firstpartners.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import org.apache.commons.lang.StringUtils;

import net.firstpartners.core.log.IGiveFeedbackToUsers;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;

/**
 * Previously released as 'RulePlayer', Desktop GUI for Red Piranha Desktop User
 * interface to ease calling of Rules / running the webserver needed for the
 * office plugin
 * 
 * @author PBrowne
 *
 */
public class RedGui extends WindowAdapter
		implements WindowListener, ActionListener, Runnable, ILogger, IGiveFeedbackToUsers {

	// Logger
	private static final Logger log = RpLogger.getLogger(RedGui.class.getName());

	static boolean quit = false;

	/**
	 * The Main method is more of a convenience for testing All it does is display
	 * the GUI
	 * 
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		// Open the GUI
		log.fine("Opening GUI");
		RedGui player = new RedGui();
		Runnable readRun = new Thread(player);
		readRun.run();

	}

	/**
	 * Helper method to update font size
	 * 
	 * @param swingComponent
	 */
	static void updateFontSize(JComponent swingComponent) {
		Font currentFont = swingComponent.getFont();
		Font updatedFont = currentFont.deriveFont(currentFont.getSize() * 1.4F);
		swingComponent.setFont(updatedFont);

	}

	// Handle to the Properties
	Properties copyOfProperties = null;
	// Class level GUI Elements - we update these as we get notification from the
	// system via
	// the IGiveFeedbackToUsers interface
	private JFrame frame = new JFrame("Red-Piranha - Java Power Tools for Excel");
	private JButton homePageCloseButton;
	private JLabel homePageErrorMessage;
	private JProgressBar homePageProgressBar;
	private JEditorPane htmlHomePane;
	private JTextArea tab2TextArea;
	private JTextArea tab3TextArea;
	private JTextArea tab4TextArea;
	
	

	/**
	 * Constructor, builds a simple GUI
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public RedGui() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		// Force our look and feel to windows
		try {

			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		} catch (Exception e) {

			log.severe(e.toString());
		}

		// Overall Screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension(screenSize.width / 2, screenSize.height / 2);
		int x = frameSize.width / 2;
		int y = frameSize.height / 2;

		frame.setBounds(x, y, frameSize.width, frameSize.height);
		JTabbedPane jtp = new JTabbedPane(JTabbedPane.BOTTOM, JTabbedPane.WRAP_TAB_LAYOUT);

		this.frame.getContentPane().add(jtp);

		JPanel jp1 = new JPanel();// This will create the first tab
		JPanel jp2 = new JPanel();// This will create the second tab
		JPanel jp3 = new JPanel();// This will create the fourth tab
		JPanel jp4 = new JPanel();// This will create the third tab

		// add the tabs
		updateFontSize(jtp);

		jtp.addTab("  Home  ", jp1);
		jtp.addTab("  Excel Data before rules  ", jp2);
		jtp.addTab("  Rules Engine Log ", jp3);
		jtp.addTab("  Data after business rules applied	  ", jp4);

		// Panel 1 button
		homePageCloseButton = new JButton("  Exit  "); // held at class level
		homePageCloseButton.setEnabled(false);
		updateFontSize(homePageCloseButton);
		homePageCloseButton.addActionListener(this);

		// Panel 1 Progress Bar and error message holder
		homePageProgressBar = new JProgressBar();
		homePageProgressBar.setPreferredSize(new Dimension(x, 50));
		homePageProgressBar.setMinimum(0);
		homePageProgressBar.setMaximum(100);
		homePageProgressBar.setStringPainted(true);
		homePageErrorMessage = new JLabel();

		// Panel 1
		JComponent homePane = getHomePanel();
		jp1.setLayout(new BorderLayout());
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subPanel.add(homePageCloseButton, "East");
		subPanel.add(homePageProgressBar, "Center");
		subPanel.add(homePageErrorMessage,"South"); //placeholder if needed for later
		jp1.add(homePane, "Center");
		jp1.add(subPanel, "South");

		// Panel 2
		JLabel label2 = new JLabel();
		label2.setText("This is the information we have been able to 'read' from your Excel file\n");
		updateFontSize(label2);
		this.tab2TextArea = new JTextArea();
		this.tab2TextArea.setEditable(false);
		this.tab2TextArea.setLineWrap(true);
		jp2.setLayout(new BorderLayout());
		jp2.add(new JScrollPane(this.tab2TextArea), "Center");
		jp2.add(label2, "North");

		// panel 3
		JLabel label3 = new JLabel();
		label3.setText("Updates as the system runs - more details in red-piranha.log and in the 'log' table of the workbook\n");
		updateFontSize(label3);
		this.tab3TextArea = new JTextArea();
		this.tab3TextArea.setEditable(false);
		this.tab3TextArea.setLineWrap(true);
		jp3.setLayout(new BorderLayout());
		jp3.add(new JScrollPane(this.tab3TextArea), "Center");
		jp3.add(label3, "North");

		// Panel 4
		JLabel label4 = new JLabel();
		label4.setText("This the information updated by the business rules, before we save it to Excel\n");
		updateFontSize(label4);
		this.tab4TextArea = new JTextArea();
		this.tab4TextArea.setEditable(false);
		this.tab4TextArea.setLineWrap(true);
		jp4.setLayout(new BorderLayout());
		jp4.add(new JScrollPane(this.tab4TextArea), "Center");
		jp4.add(label4, "North");
		
		// make the window visible
		this.frame.setVisible(true);
		frame.addWindowListener(this);

		// open until finished
		RedGui.quit = false;

		frame.setVisible(true); // otherwise you won't "see" it/
	}

	/**
	 * Event Handler - button pushed
	 * 
	 */
	public synchronized void actionPerformed(ActionEvent evt) {
		log.info("Notified of Action:" + evt);
		System.exit(0);
		
	}

	/**
	 * Allows us to Log to the GUI
	 * 
	 * @param message to log
	 */
	public void debug(String message) {
		this.tab3TextArea.append("DEBUG:" + message + "\n");
		log.fine(message);
	}

	/**
	 * Allows us to Log to the GUI
	 * 
	 * @param message
	 * @param t
	 */
	public void exception(String message, Throwable t) {
		this.tab3TextArea.append("EXCEPTION:" + message + "\n" + t.toString() + "\n");
		log.severe(message + "\n" + t.toString());
		log.log(Level.SEVERE, "Error", t);
		
	}
	
	/**
	 * Construct the HTML Based Home panel the user sees at startup
	 */
	JScrollPane getHomePanel() {

		// create home pnael
		htmlHomePane = new JEditorPane();

		// make it read-only
		htmlHomePane.setEditable(false);

		// create a scrollpane; modify its attributes as desired
		JScrollPane scrollPane = new JScrollPane(htmlHomePane);

		// add an html editor kit
		HTMLEditorKit kit = new HTMLEditorKit();
		htmlHomePane.setEditorKit(kit);

		// add some styles to the html
		StyleSheet styleSheet = kit.getStyleSheet();
		styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
		styleSheet.addRule("h1 {color: blue;}");
		styleSheet.addRule("h2 {color: #ff00ff;}");
		styleSheet.addRule("pre {font : 11px monaco; color : black; background-color : #fafafa; }");

		// create a document, set it on the jeditorpane, then add the html
		Document doc = kit.createDefaultDocument();
		htmlHomePane.setDocument(doc);
		htmlHomePane.setText(getupdatedHomeSreenHtml());

		return scrollPane;

	}

	/**
	 * Create HTML with our current system status as home page
	 */
	String getupdatedHomeSreenHtml() {

		// Default if we are running standalone
		String excelFile = "";
		String outputFileName = "";
		String ruleFileAsString = "";

		// Get UserSpecifc information we need to display
		if (copyOfProperties != null) {
			excelFile = StringUtils.escape(copyOfProperties.getProperty(UiUtils.EXCEL_INPUT));
			outputFileName = StringUtils.escape(copyOfProperties.getProperty(UiUtils.EXCEL_OUTPUT));

			// build multiple rules files
			String rulesFiles[] = UiUtils.getRuleFiles(copyOfProperties).getRulesLocation();
			StringBuilder ruleFile = new StringBuilder();
			for (int a = 0; a < rulesFiles.length; a++) {
				ruleFile.append(StringUtils.escape(rulesFiles[a]) + " | ");
			}
			ruleFile.setLength(ruleFile.length() - 2); // otherwise we have a hanging |
			ruleFileAsString = ruleFile.toString();
		}
		// create some simple html as a string
		String htmlString = "<html>\n" + "<body>\n" + "<h1>Red Piranha - Business Rules for Excel</h1>\n"
				+ "<p>Applying Business rules to your Data. "
				+ "More Information and guides at <a href=\"https://github.com/paulbrowne-irl/red-piranha\">https://github.com/paulbrowne-irl/red-piranha</a>."
				+ "<br/>Clicking the tabs at the bottom of the app will show more detailled information.</p><br/>"
				+ "<h3>Settings</h3>\n" + "<p><I>&nbsp;&nbsp;&nbsp;Input File:" + excelFile + "</I></p>"
				+ "<p><I>&nbsp;&nbsp;&nbsp;Rules we will apply:" + ruleFileAsString + "</I></p>"
				+ "<p><I>&nbsp;&nbsp;&nbsp;Output File:" + outputFileName + "</I></p><br/>"
				+ "<p>These values can be set in red-piranha.config</p><br/>" + "<h3>Progress</h3>\n"
				+ "<br/>	</body>\n";

		return htmlString;
	}

	/**
	 * Allows us to Log to the GUI
	 * 
	 * @param message
	 */
	public void info(String message) {
		this.tab3TextArea.append("INFO:" + message + "\n");
		log.info(message);
	}

	/**
	 * Notifies us if we want Provide a visual notification that an Exception has occured
	 */
	public void notifyExceptionOccured() {
		
		log.info("Updating GUI State");
		
		//Replace Progress bar state
		Container parent = homePageProgressBar.getParent();
		parent.remove(homePageProgressBar);
		parent.remove(homePageCloseButton);
		parent.repaint();
		
		homePageErrorMessage.setText("Houston we may have a problem ... check tabs and logs for more details");
		updateFontSize (homePageErrorMessage);
		homePageErrorMessage.setBackground(Color.RED);;
		homePageErrorMessage.repaint();
	}

	/**
	 * Update the GUI Progress Bar Callback via IGiveFeedbacktoUsers interface
	 * 
	 * @param percentProgressMade 0 to 100
	 */
	public void notifyProgress(int percentProgressMade) {
		assert percentProgressMade >= 0;
		assert percentProgressMade <= 100;
		this.homePageProgressBar.setValue(percentProgressMade);

		if(percentProgressMade==100) {
			homePageCloseButton.setEnabled(true);
		}
	

	}

	/**
	 * Make this class threadable
	 */
	public synchronized void run() {

		// Update the Home panel with what we know about the input / output fies
		// this.homeScreen= getHomePanel();
		htmlHomePane.setText(getupdatedHomeSreenHtml());

		while (true) {
			try {
				if (RedGui.quit = false) {
					Thread.sleep(1000L);
					continue;
				}
			} catch (InterruptedException var2) {
				this.tab3TextArea.append(var2.toString());
			} catch (Throwable var3) {
				this.tab3TextArea.append(var3.toString());
			}

			return;
		}
	}

	public void setGUIProperties(Properties copyOfProperties) {
		this.copyOfProperties = copyOfProperties;
	}

	/**
	 * Allows us to Log to the GUI a snapshot pre rules Callback via
	 * IGiveFeedbacktoUsers interface
	 * 
	 * @param message
	 */
	@Override
	public void showPostRulesSnapShot(String message) {

		this.tab4TextArea.setText(message);

	}

	/**
	 * Allows us to Log to the GUI a snapshot pre rules Callback via
	 * IGiveFeedbacktoUsers interface
	 * 
	 * @param message
	 */
	@Override
	public void showPreRulesSnapShot(String message) {

		this.tab2TextArea.setText(message);

	}

	/**
	 * Event handler - once window is closed
	 */
	public synchronized void windowClosed(WindowEvent evt) {
		RedGui.quit = true;
		this.notifyAll();
		System.exit(0);
	}

	/**
	 * Event Handler - window closing
	 */
	public synchronized void windowClosing(WindowEvent evt) {
		this.frame.setVisible(false);
		this.frame.dispose();
	}

}
