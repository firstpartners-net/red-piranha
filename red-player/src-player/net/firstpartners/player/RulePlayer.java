package net.firstpartners.player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.firstpartners.drools.PreCompileRuleBuilder;

import org.drools.compiler.DroolsParserException;

/**
 * Java client to allow compilation (outside of GAE) of rules Some console code
 * from RJHM van den Bergh , rvdb@comweb.nl
 * 
 * @author paul
 * 
 */

public class RulePlayer extends WindowAdapter implements WindowListener,
		ActionListener, Runnable, RulePlayerConfigurable {

	private JFrame frame;
	private JTextArea textArea;

	private String ruleFile =null;
	private String googleUser=null;
	
	private String KbFileName=null;
	
	


	private boolean quit = false;

	void compileRules() {


		if (ruleFile == null) {
			textArea
					.append("Please set 'rule' as a param pointing at the rule file you wish to compile ");

		} else {
			textArea.append("Compiling Rules...\n");
			textArea.append("Using file:" + ruleFile + "\n");

			PreCompileRuleBuilder rulebuilder = new PreCompileRuleBuilder();
			try {
				rulebuilder.cacheRule(ruleFile, this.getKbFileName());
				
			} catch (DroolsParserException e) {
				textArea.append("DroolsParserException:" + e + "\n");
				e.printStackTrace();
			} catch (IOException e) {
				textArea.append("IOException:" + e.getMessage() + "\n");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				textArea.append("ClassNotFoundException:" + e.getMessage()
						+ "\n");
				e.printStackTrace();
			}
			textArea.append("Compiling complete\n");
			textArea.append("Packaged Knowledgebase stored in:"+this.getKbFileName());
			
			
		}

	}

	public RulePlayer() {
		// create all components and add them

		frame = new JFrame("Red-Piranha RulePlayer - Package and Deploy your Business rules");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension((int) (screenSize.width / 2),
				(int) (screenSize.height / 2));
		int x = (int) (frameSize.width / 2);
		int y = (int) (frameSize.height / 2);
		frame.setBounds(x, y, frameSize.width, frameSize.height);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);

		JButton button = new JButton("Run Again");

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(new JScrollPane(textArea),
				BorderLayout.CENTER);
		frame.getContentPane().add(button, BorderLayout.SOUTH);
		frame.setVisible(true);

		frame.addWindowListener(this);
		button.addActionListener(this);

		quit = false; // signals the Threads that they should exit

	}

	/**
	 * Window is closed, signal exit
	 */
	public synchronized void windowClosed(WindowEvent evt) {
		quit = true;
		this.notifyAll(); // stop all threads
		System.exit(0);
	}

	public synchronized void windowClosing(WindowEvent evt) {
		frame.setVisible(false); // default behaviour of JFrame
		frame.dispose();
	}

	public synchronized void actionPerformed(ActionEvent evt) {
		textArea.setText("");

		// Start new thread to keep window open
		Runnable readRun = new Thread(this);
		readRun.run();

	}

	/**
	 * This method is called by new Runnable
	 */
	public synchronized void run() {

		try {
			// Loop , but pause to let the user do something!

			while (this.quit = false) {
				Thread.sleep(1000);

			}

		} catch (java.lang.InterruptedException ex) {
			textArea.append(ex.toString());
		} catch (Throwable t) {
			textArea.append(t.toString());
		}

	}

	public static void main(String[] arg) {

		RulePlayer player = new RulePlayer();

		//Load the settings if available
		SettingsLoader settingsLoader = new SettingsLoader();
		settingsLoader.load(player);
		
		
		// Start a thread to keep window open
		Runnable readRun = new Thread(player);
		readRun.run();

		player.compileRules();
		System.out.println("RulePlayer Complete");	

	}
	
	@Override
	public String getRuleFile() {
		return ruleFile;
	}

	@Override
	public void setRuleFile(String ruleFile) {
		this.ruleFile = ruleFile;
	}
	
	@Override
	public String getGoogleUser() {
		return googleUser;
	}

	@Override
	public void setGoogleUser(String googleUser) {
		this.googleUser = googleUser;
	}

	@Override
	public String getProxyHost() {
		return System.getProperty("http.proxyHost");
	}

	@Override
	public void setProxyHost(String proxyHost) {
		if(proxyHost!=null){
			System.setProperty("http.proxyHost", proxyHost);
		}
		
	}

	@Override
	public String getProxyPort() {
		return System.getProperty("http.proxyHost");
	}

	@Override
	public void setProxyPort(String proxyPort) {
		if(proxyPort!=null){
			System.setProperty("http.proxyPort", proxyPort);
		}
		
	}
	
	@Override
	public String getKbFileName() {
		return KbFileName;
	}

	@Override
	public void setKbFileName(String kbFileName) {
		KbFileName = kbFileName;
	}


	
}
