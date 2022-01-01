package net.firstpartners.ui.component;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.firstpartners.utils.Config;

/**
 * Utility class for generating HTML used by the GUI
 * 
 * @author PBrowne
 *
 */
@Component
public class HtmlGenerator {

	//handle for our config
	@Autowired
	Config appConfig;
	
	/**
	 * Get a HTMLEditor Kit with a standard CSS
	 * 
	 * @return HTMLEditorKit with the CSS configured in it
	 */
	public static HTMLEditorKit getCssKit() {

		// add some styles to the html

		HTMLEditorKit kit = new HTMLEditorKit();
		StyleSheet styleSheet = kit.getStyleSheet();
		styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
		styleSheet.addRule("h1 {color: blue;}");
		styleSheet.addRule("h2 {color: #ff00ff;}");
		styleSheet.addRule("pre {font : 11px monaco; color : black; background-color : #fafafa; }");

		return kit;
	}

	/**
	 * Create HTML with our current system status as home page
	 * 
	 * @return String containing the HTML we want to display on the home screen
	 */
	public String getupdatedHomeSreenHtml() {

		// Default if we are running standalone
		String inputFileName = "";
		String outputFileName = "";
		String ruleFileAsString = "";

		// Get UserSpecifc information we need to display
		if(appConfig!=null) {
			inputFileName = StringEscapeUtils.escapeHtml3(appConfig.getInputFileName());
			outputFileName = StringEscapeUtils.escapeHtml4(appConfig.getOutputFileName());
		}

	

		// build multiple rules files
		String rulesFiles[] = appConfig.getRuleConfig().getRulesLocation();
		StringBuilder ruleFile = new StringBuilder();
		for (int a = 0; a < rulesFiles.length; a++) {
			ruleFile.append(StringEscapeUtils.escapeHtml4(rulesFiles[a]) + " | ");
		}
		ruleFile.setLength(ruleFile.length() - 2); // otherwise we have a hanging |
		ruleFileAsString = ruleFile.toString();

		// create some simple html as a string
		String htmlString = "<html>\n" + "<body>\n" + "<h1>Red Piranha - Business Rules for Excel and Office</h1>\n"
				+ "<p>Applying Business rules to your Data. "
				+ "More Information and guides at <a href=\"https://github.com/paulbrowne-irl/red-piranha\">https://github.com/paulbrowne-irl/red-piranha</a>."
				+ "<br/>Clicking the tabs at the bottom of the app will show more detailled information.</p><br/>"
				+ "<h3>Settings</h3>\n" + "<p><I>&nbsp;&nbsp;&nbsp;Input File:" + inputFileName + "</I></p>"
				+ "<p><I>&nbsp;&nbsp;&nbsp;Rules we will apply:" + ruleFileAsString + "</I></p>"
				+ "<p><I>&nbsp;&nbsp;&nbsp;Output File:" + outputFileName + "</I></p><br/>"
				+ "<p>These values can be set in red-piranha.config</p><br/>" + "<h3>Progress</h3>\n"
				+ "<br/>	</body>\n";

		return htmlString;
	}

}
