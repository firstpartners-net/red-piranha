package net.firstpartners.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import net.firstpartners.drools.PreCompileRuleBuilder;

import org.drools.compiler.DroolsParserException;

/**
 * Applet to allow compilition (outside of GAE) of rules
 * 
 * @author paul
 * 
 */
public class RulePlayer extends JApplet implements ActionListener {

	// The component that actually presents the GUI.
	public class Gui extends JPanel {
		public Gui() {
			super(new BorderLayout());
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

		}
	}

	Gui gui; // the applet's content pane
	JTextArea mainTextArea;
	String ruleFile = null;

	public void actionPerformed(ActionEvent e) {
	}

	public String getAppletInfo() {
		return "Title: Red Piranha Applet\n" + "Author: Paul Browne\n"
				+ "Applet to package up rules";
	}

	public String[][] getParameterInfo() {
		String[][] info = { { "rule", "string",
				"the rule file we wish to compile" } };
		return info;
	}

	// Called when this applet is loaded into the browser.
	public void init() {
		loadAppletParameters();

		// Execute a job on the event-dispatching thread:
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					setupGui();

					if (ruleFile == null) {
						mainTextArea.append("Please set 'rule' as a param pointing at the rule file you wish to compile ");
						
						
					} else {
						mainTextArea.append("Compiling Rules...\n");
						mainTextArea.append("Using file:"+ruleFile+"\n");
						
						PreCompileRuleBuilder rulebuilder = new PreCompileRuleBuilder();
						try {
							rulebuilder.cacheRule(ruleFile, null);
						} catch (DroolsParserException e) {
							mainTextArea.append("DroolsParserException:" + e);
							e.printStackTrace();
						} catch (IOException e) {
							mainTextArea
									.append("IOException:" + e.getMessage());
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							mainTextArea.append("ClassNotFoundException:"
									+ e.getMessage());
							e.printStackTrace();
						}
						mainTextArea.append("Compiling complete");
					}
				}
			});

		} catch (Throwable t) {
			mainTextArea.append("Error:" + t);
			t.printStackTrace();
		}

	}

	// Called by init.
	protected void loadAppletParameters() {
		// Get the applet parameters.
		String at = getParameter("rule");
		if (at != null) {
			ruleFile = at;
		}

	}

	/**
	 * Create the GUI. For thread safety, this method should be invoked from the
	 * event-dispatching thread.
	 */
	private void setupGui() {

		BorderLayout thisLayout = new BorderLayout();

		super.setLayout(thisLayout);

		mainTextArea = new JTextArea();
		mainTextArea.setOpaque(true);
		mainTextArea.setBackground(Color.white);

		// Custom component to draw the current image
		// at a particular offset.
		setContentPane(mainTextArea);

	}

	public void start() {
		super.start();
	}

	public void stop() {
		super.stop();
	}

}
