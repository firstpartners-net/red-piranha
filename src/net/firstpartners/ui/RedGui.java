package net.firstpartners.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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

import org.apache.log4j.Logger;

import net.firstpartners.core.log.IGiveFeedbackToUsers;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;
import net.firstpartners.data.RangeHolder;
import net.firstpartners.ui.component.HtmlGenerator;
import net.firstpartners.ui.component.RangeCellTree;
import net.firstpartners.ui.component.SwingGuiUtils;

/**
 * Desktop GUI for Red Piranha Desktop User interface to ease calling of Rules / running the webserver needed for the
 * office plugin.
 * 
 * This is just a facade, called by RedCommandLine (unless the user has configured it not to)
 * 
 * @author PBrowne
 *
 */
public class RedGui extends WindowAdapter
		implements WindowListener, ActionListener, Runnable, ILogger, IGiveFeedbackToUsers {

	// Logger
	private static final Logger log = RpLogger.getLogger(RedGui.class.getName());

	static boolean quit = false;

	private JLabel frontPageMessage;

	
	// Class level GUI Elements - we update these as we get notification from the
	// system via the IGiveFeedbackToUsers interface
	private JFrame frame = new JFrame("Red-Piranha - Java Power Tools for Excel");
	private JButton homePageCloseButton;

	private JProgressBar homePageProgressBar;

	private JEditorPane htmlHomePane;

	private RangeCellTree preRangeCellTree;
	private JTextArea tab3TextArea;
	private RangeCellTree postRangeCellTree;

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

			log.warn(e.toString());
		}

		// Overall Screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension(screenSize.width / 2, screenSize.height / 2);
		int x = frameSize.width / 2;
		int y = frameSize.height / 2;

		frame.setBounds(x, y, frameSize.width, frameSize.height);
		JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

		this.frame.getContentPane().add(jtp);

		JPanel jp1 = new JPanel();// This will create the first tab
		JPanel jp2 = new JPanel();// This will create the second tab
		JPanel jp3 = new JPanel();// This will create the fourth tab
		JPanel jp4 = new JPanel();// This will create the third tab

		// add the tabs
		SwingGuiUtils.updateFontSize(jtp);

		jtp.addTab("  Home  ", jp1);
		jtp.addTab("  Excel Data before rules  ", jp2);
		jtp.addTab("  Rules Engine Log ", jp3);
		jtp.addTab("  Data after business rules applied	  ", jp4);

		// Label that we will use across all tabs
		frontPageMessage = new JLabel();
		SwingGuiUtils.updateFontSize(frontPageMessage);

		// Panel 1 button
		homePageCloseButton = new JButton("  Exit  "); // held at class level
		homePageCloseButton.setEnabled(false);
		SwingGuiUtils.updateFontSize(homePageCloseButton);
		homePageCloseButton.addActionListener(this);

		// Panel 1 Progress Bar and error message holder
		homePageProgressBar = new JProgressBar();
		homePageProgressBar.setPreferredSize(new Dimension(x, 50));
		homePageProgressBar.setMinimum(0);
		homePageProgressBar.setMaximum(100);
		homePageProgressBar.setStringPainted(true);

		// Panel 1
		JComponent homePane = getHomePanel();
		jp1.setLayout(new BorderLayout());
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subPanel.add(homePageCloseButton, "East");
		subPanel.add(homePageProgressBar, "Center");
		subPanel.add(frontPageMessage, "South"); // placeholder if needed for later
		jp1.add(homePane, "Center");
		jp1.add(subPanel, "South");

		// Panel 2
		JLabel label2 = new JLabel();
		label2.setText("This is the information we have been able to 'read' from your Excel file\n");
		SwingGuiUtils.updateFontSize(label2);
		preRangeCellTree = new RangeCellTree();
		jp2.setLayout(new BorderLayout());
		jp2.add(new JScrollPane(this.preRangeCellTree), "Center");
		jp2.add(label2, "North");

		// panel 3
		JLabel label3 = new JLabel();
		label3.setText(
				"Updates as the system runs - more details in red-piranha.log and in the 'log' table of the workbook\n");
		SwingGuiUtils.updateFontSize(label3);
		this.tab3TextArea = new JTextArea();
		this.tab3TextArea.setEditable(false);
		this.tab3TextArea.setLineWrap(true);
		jp3.setLayout(new BorderLayout());
		jp3.add(new JScrollPane(this.tab3TextArea), "Center");
		jp3.add(label3, "North");

		// Panel 4
		JLabel label4 = new JLabel();
		label4.setText("This the information updated by the business rules, that we are about to save to Excel\n");
		SwingGuiUtils.updateFontSize(label4);
		this.postRangeCellTree = new RangeCellTree();
		jp4.setLayout(new BorderLayout());
		jp4.add(new JScrollPane(this.postRangeCellTree), "Center");
		jp4.add(label4, "North");

		// make the window visible
		this.frame.pack();
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
		log.debug(message);
	}

	/**
	 * Allows us to Log to the GUI
	 * 
	 * @param message
	 * @param t
	 */
	public void exception(String message, Throwable t) {
		this.tab3TextArea.append("EXCEPTION:" + message + "\n" + t.toString() + "\n");
		log.error(message,t);

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
		HTMLEditorKit kit = HtmlGenerator.getCssKit();
		htmlHomePane.setEditorKit(kit);

		// create a document, set it on the jeditorpane, then add the html
		Document doc = kit.createDefaultDocument();
		htmlHomePane.setDocument(doc);
		htmlHomePane.setText(HtmlGenerator.getupdatedHomeSreenHtml());

		return scrollPane;

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
	 * Notifies us if we want Provide a visual notification that an Exception has
	 * occured
	 */
	public void notifyExceptionOccured() {

		log.info("Updating GUI State");

		// Replace Progress bar state
		Container parent = homePageProgressBar.getParent();
		parent.remove(homePageProgressBar);
		parent.remove(homePageCloseButton);
		parent.repaint();

		setFrontPageMessage("Houston we may have a problem ... check tabs and logs for more details");

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

		if (percentProgressMade == 100) {
			homePageCloseButton.setEnabled(true);
		}

	}

	/**
	 * Make this class threadable
	 */
	public synchronized void run() {

		// Update the Home panel with what we know about the input / output fies
		// this.homeScreen= getHomePanel();
		htmlHomePane.setText(HtmlGenerator.getupdatedHomeSreenHtml());

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

	/**
	 * Update the message that will be shown on all tabs
	 * 
	 * @param frontPageMessage
	 */
	public void setFrontPageMessage(String allPageMessage) {
		this.frontPageMessage.setText(allPageMessage);
		this.frontPageMessage.repaint();
	}


	/**
	 * Allows us to Log to the GUI a snapshot pre rules Callback via
	 * IGiveFeedbacktoUsers interface
	 * 
	 * @param message
	 */
	@Override
	public void showPostRulesSnapShot(Object message) {

		if (message instanceof RangeHolder) {
			postRangeCellTree.setDataModel((RangeHolder) message);
		} else {
			// redirect this info to the logger
			info("Data Extracted from Excel Spreadsheet");
			info(message.toString());
		}


	}

	/**
	 * Allows us to Log to the GUI a snapshot pre rules Callback via
	 * IGiveFeedbacktoUsers interface
	 * 
	 * @param message
	 */
	@Override
	public void showPreRulesSnapShot(Object message) {

		if (message instanceof RangeHolder) {
			preRangeCellTree.setDataModel((RangeHolder) message);
		} else {
			// redirect this info to the logger
			info("Data Extracted from Excel Spreadsheet");
			info(message.toString());
		}

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
