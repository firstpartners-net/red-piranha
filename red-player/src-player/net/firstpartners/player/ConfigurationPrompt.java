package net.firstpartners.player;


import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ConfigurationPrompt {

	static void confirmConfiguration(
			RulePlayerConfigurable confirmUpdateTheseValues) {

		// User
		JTextField googleUserName = new JTextField(confirmUpdateTheseValues
				.getGoogleUserName());

		// Password
		JPasswordField password = new JPasswordField();

		//Rule Source
		JTextField sourceRulesName = new JTextField(confirmUpdateTheseValues.getSourceRuleFile());
		
		// Packaged Rules Name
		JTextField packagedRulesName = new JTextField(confirmUpdateTheseValues.getKbFileName());

		// Application Name
		JTextField applicationName = new JTextField(confirmUpdateTheseValues
				.getApplicationName());

		// Google Host
		JTextField googleHost = new JTextField(confirmUpdateTheseValues
				.getGoogleHost());

		// Proxy URL
		JTextField proxyUrl = new JTextField(confirmUpdateTheseValues
				.getProxyHost());

		// Proxy Port
		JTextField proxyPort = new JTextField(confirmUpdateTheseValues
				.getProxyPort());

		// Show the Confirmation dialog
		final JComponent[] inputs = new JComponent[] {
				new JLabel("Google Username"),
				googleUserName,
				new JLabel("Google Password"),
				password,
				new JLabel("Start / Source Rules "),
				sourceRulesName,
				new JLabel("Finished / packaged Rules Names"),
				packagedRulesName,
				new JLabel(""),
				new JLabel("Advanced Options (should not need to change)"),
				new JLabel("Application Name"),
				applicationName,
				new JLabel("Google Host"),
				googleHost,
				new JLabel(""),
				new JLabel(
						"Use the same values as in Internet explorer if you're having trouble connecting"),
				new JLabel("proxy URL"), proxyUrl, new JLabel("proxy Port"),
				proxyPort };

		JOptionPane.showMessageDialog(null, inputs, "Please confirm Google settings for Package Rule File upload",
				JOptionPane.QUESTION_MESSAGE);

		// Copy over values and return

		// User
		confirmUpdateTheseValues.setGoogleUserName(googleUserName.getText());

		// Password
		confirmUpdateTheseValues.setGooglePassword(password.getText());

		//Source Rules file
		confirmUpdateTheseValues.setSourceRuleFile(sourceRulesName.getText());
		
		// Packaged Rules Name
		confirmUpdateTheseValues.setKbFileName(packagedRulesName.getText());

		// Application Name
		confirmUpdateTheseValues.setApplicationName(applicationName.getText());

		// Google Host
		confirmUpdateTheseValues.setGoogleHost(googleHost.getText());

		// Proxy URL
		confirmUpdateTheseValues.setProxyHost(proxyUrl.getText());

		// Proxy Port
		confirmUpdateTheseValues.setProxyPort(proxyPort.getText());
	}

}
