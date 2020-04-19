package net.firstpartners.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.firstpartners.core.log.IDataSnapshot;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;

/**
 * Previously released as 'RulePlayer', Desktop GUI for Red Piranha
 * 
 * @author PBrowne
 *
 */
public class RedGui extends WindowAdapter implements WindowListener, ActionListener, Runnable, ILogger, IDataSnapshot {

	// Logger
	private static final Logger log = RpLogger.getLogger(RedGui.class.getName());

	// Class level GUI Elements
	private JFrame frame = new JFrame("Red-Piranha - Java Power Tools for Excel");
	private JTextArea tab1TextArea;
	private JTextArea tab2TextArea;
	private JTextArea tab3TextArea;

	static boolean quit = false;

	// Hold the property level for the class

	/**
	 * Constructor, builds a simple GUI
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public RedGui() {

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

		// panel.setLayout(new GridLayout());
		JPanel jp1 = new JPanel();// This will create the first tab
		JPanel jp2 = new JPanel();// This will create the second tab
		JPanel jp3 = new JPanel();// This will create the second tab

		// add the tabs
		jtp.addTab("Info", jp1);
		jtp.addTab("Excel Data before rules", jp2);
		jtp.addTab("Data after rules before Excel", jp3);

		// panel 1
		JLabel label1 = new JLabel();
		label1.setText("Updates as the system runs");
		this.tab1TextArea = new JTextArea();
		this.tab1TextArea.setEditable(false);
		this.tab1TextArea.setLineWrap(true);
		jp1.setLayout(new BorderLayout());
		JButton button = new JButton("Run Again");
		jp1.add(new JScrollPane(this.tab1TextArea), "Center");
		jp1.add(button, "South");
		jp1.add(label1, "North");

		// Panel 2
		JLabel label2 = new JLabel();
		label2.setText("This is the information we have been able to 'read' from your Excel file");
		this.tab2TextArea = new JTextArea();
		this.tab2TextArea.setEditable(false);
		this.tab2TextArea.setLineWrap(true);
		jp2.setLayout(new BorderLayout());
		jp2.add(new JScrollPane(this.tab2TextArea), "Center");
		jp2.add(label2, "North");

		// Panel 3
		JLabel label3 = new JLabel();
		label3.setText("This the information updated by the business rules, before we save it to Excel");
		this.tab3TextArea = new JTextArea();
		this.tab3TextArea.setEditable(false);
		this.tab3TextArea.setLineWrap(true);
		jp3.setLayout(new BorderLayout());
		jp3.add(new JScrollPane(this.tab3TextArea), "Center");
		jp3.add(label3, "North");

		this.frame.setVisible(true);
		frame.addWindowListener(this);
		button.addActionListener(this);

		// open until finished
		RedGui.quit = false;

		frame.setVisible(true); // otherwise you won't "see" it
	}

	/**
	 * Event Handler - window closing
	 */
	public synchronized void windowClosing(WindowEvent evt) {
		this.frame.setVisible(false);
		this.frame.dispose();
	}

	/**
	 * Even Handler - button pushed
	 */
	public synchronized void actionPerformed(ActionEvent evt) {
		this.tab1TextArea.setText("");
		Runnable readRun = new Thread(this);
		readRun.run();
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
	 * Make this class threadable
	 */
	public synchronized void run() {
		while (true) {
			try {
				if (RedGui.quit = false) {
					Thread.sleep(1000L);
					continue;
				}
			} catch (InterruptedException var2) {
				this.tab1TextArea.append(var2.toString());
			} catch (Throwable var3) {
				this.tab1TextArea.append(var3.toString());
			}

			return;
		}
	}

	/**
	 * Allows us to Log to the GUI
	 * 
	 * @param message to log
	 */
	public void debug(String message) {
		this.tab1TextArea.append("DEBUG:" + message + "\n");
		log.fine(message);
	}

	/**
	 * Allows us to Log to the GUI
	 * 
	 * @param message
	 */
	public void info(String message) {
		this.tab1TextArea.append("INFO:" + message + "\n");
		log.info(message);
	}

	/**
	 * Allows us to Log to the GUI
	 * 
	 * @param message
	 * @param t
	 */
	public void exception(String message, Throwable t) {
		this.tab1TextArea.append("EXCEPTION:" + message + "\n" + t.toString() + "\n");
		log.severe(message + "\n" + t.toString());
		log.log(Level.SEVERE, "Error", t);
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		// Open the GUI
		log.fine("Opening GUI");
		RedGui player = new RedGui();
		Runnable readRun = new Thread(player);
		readRun.run();

	}

	/**
	 * Allows us to Log to the GUI a snapshot pre rules
	 * 
	 * @param message
	 */
	@Override
	public void showPreRulesSnapShot(Object message) {
		this.tab2TextArea.setText(message.toString());

	}

	/**
	 * Allows us to Log to the GUI a snapshot pre rules
	 * 
	 * @param message
	 */
	@Override
	public void showPostRulesSnapShot(Object message) {
		this.tab3TextArea.setText(message.toString());

	}

}
