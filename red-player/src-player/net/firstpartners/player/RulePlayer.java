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
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.firstpartners.drools.PreCompileRuleBuilder;
import net.firstpartners.google.docs.DocumentListException;
import net.firstpartners.google.docs.GoogleDocumentList;

import org.drools.compiler.DroolsParserException;

import com.google.gdata.data.acl.AclRole;
import com.google.gdata.data.acl.AclScope;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

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

	// Class level variables, populated from properties file
	private String ruleFile = null;
	private String googleUser = null;
	private String password;
	private String applicationName = null;
	private String googleHost = null;
	private String KbFileName = null;

	// Detect if user has press stopped
	private boolean quit = false;
	
	// Main App method, called when GUI has loaded
	void compileRules() {

		// Local Variables
		// Handle to the google uploader
		GoogleDocumentList googleUploader;

		//Confirm our configuration
		ConfigurationPrompt.confirmConfiguration(this);

		
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
			textArea.append("Packaged Knowledgebase stored in:"
					+ this.getKbFileName() + "\n");

			// Signin
			try {
			

				googleUploader = new GoogleDocumentList(this
						.getApplicationName(), this.getGoogleHost());

				googleUploader.login(this.getGoogleUserName(), this.getGooglePassword());

				// Upload
				textArea.append("Starting Document Upload to Google Docs\n");
				DocumentListEntry entry = googleUploader.uploadFile(this
						.getKbFileName(), this.getKbFileName());
				String uploadedKbDocumentID = entry.getResourceId();
				textArea.append("Document Upload complete\n");

				// change permissions
				textArea.append("Changing Permissions to Public\n");
				// This request makes our previously uploaded document public
				AclScope scope = new AclScope(AclScope.Type.DOMAIN,
						uploadedKbDocumentID);

				googleUploader.addAclRole(AclRole.READER, scope,
						uploadedKbDocumentID);

				textArea.append("Process complete\n");

			} catch (DocumentListException e) {
				textArea.append("DocumentListException:" + e.getMessage()
						+ "\n");
				e.printStackTrace();

			} catch (AuthenticationException e) {
				textArea.append("AuthenticationException:" + e.getMessage()
						+ "\n");
				e.printStackTrace();

			} catch (ServiceException e) {
				textArea.append("ServiceException:" + e.getMessage() + "\n");
				e.printStackTrace();

			} catch (IOException e) {
				textArea.append("IOException:" + e.getMessage() + "\n");
				e.printStackTrace();

			} 
		}

	}



	public RulePlayer() {
		// create all components and add them

		frame = new JFrame(
				"Red-Piranha RulePlayer - Package and Deploy your Business rules");
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

		// Load the settings if available
		SettingsLoader settingsLoader = new SettingsLoader();
		settingsLoader.load(player);

		// Start a thread to keep window open
		Runnable readRun = new Thread(player);
		readRun.run();

		player.compileRules();
		System.out.println("RulePlayer Complete");

	}

	@Override
	public String getSourceRuleFile() {
		return ruleFile;
	}

	@Override
	public void setSourceRuleFile(String ruleFile) {
		this.ruleFile = ruleFile;
	}

	@Override
	public String getGoogleUserName() {
		return googleUser;
	}

	@Override
	public void setGoogleUserName(String googleUser) {
		this.googleUser = googleUser;
	}

	public String getGooglePassword(){
		return this.password;
	}

	public void setGooglePassword(String password){
		this.password=password;
	}
	
	@Override
	public String getProxyHost() {
		return System.getProperty("http.proxyHost");
	}

	@Override
	public void setProxyHost(String proxyHost) {
		if (proxyHost != null) {
			System.setProperty("http.proxyHost", proxyHost);
		}

	}

	@Override
	public String getProxyPort() {
		return System.getProperty("http.proxyHost");
	}

	@Override
	public void setProxyPort(String proxyPort) {
		if (proxyPort != null) {
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

	@Override
	public String getApplicationName() {
		return this.applicationName;
	}

	@Override
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	@Override
	public void setGoogleHost(String googleHost) {
		this.googleHost = googleHost;
	}

	@Override
	public String getGoogleHost() {
		return this.googleHost;
	}
}