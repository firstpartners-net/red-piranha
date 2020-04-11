package net.firstpartners.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.firstpartners.core.drools.log.ILogger;

/**
 * Previously released as 'RulePlayer', Desktop GUI for Red Piranha
 * 
 * @author PBrowne
 *
 */
public class Rp2Gui extends WindowAdapter implements WindowListener, ActionListener, Runnable, ILogger {

	// Logger
	private static final Logger log = Logger.getLogger(Rp2Gui.class.getName());

	// Class level GUI Elements
	private JFrame frame = new JFrame("Red-Piranha - Java Power Tools for Excel");
	private JTextArea textArea;
	private boolean quit = false;

	// Hold the property level for the class

	/**
	 * Constructor, builds a simple GUI
	 */
	public Rp2Gui() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension(screenSize.width / 2, screenSize.height / 2);
		int x = frameSize.width / 2;
		int y = frameSize.height / 2;
		this.frame.setBounds(x, y, frameSize.width, frameSize.height);
		this.textArea = new JTextArea();
		this.textArea.setEditable(false);
		this.textArea.setLineWrap(true);
		JButton button = new JButton("Run Again");
		this.frame.getContentPane().setLayout(new BorderLayout());
		this.frame.getContentPane().add(new JScrollPane(this.textArea), "Center");
		this.frame.getContentPane().add(button, "South");
		this.frame.setVisible(true);
		this.frame.addWindowListener(this);
		button.addActionListener(this);
		this.quit = false;
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
		this.textArea.setText("");
		Runnable readRun = new Thread(this);
		readRun.run();
	}

	/**
	 * Event handler - once window is closed
	 */
	public synchronized void windowClosed(WindowEvent evt) {
		this.quit = true;
		this.notifyAll();
		System.exit(0);
	}

	/**
	 * Make this class threadable
	 */
	public synchronized void run() {
		while (true) {
			try {
				if (this.quit = false) {
					Thread.sleep(1000L);
					continue;
				}
			} catch (InterruptedException var2) {
				this.textArea.append(var2.toString());
			} catch (Throwable var3) {
				this.textArea.append(var3.toString());
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
		this.textArea.append("DEBUG:" + message+"\n");
	}

	/**
	 * Allows us to Log to the GUI
	 * 
	 * @param message
	 */
	public void info(String message) {
		this.textArea.append("INFO:" + message+"\n");
	}

	/**
	 * Allows us to Log to the GUI
	 * 
	 * @param message
	 * @param t
	 */
	public void exception(String message, Throwable t) {
		this.textArea.append("EXCEPTION:" + message+"\n"+t.toString()+"\n");
	}

}
