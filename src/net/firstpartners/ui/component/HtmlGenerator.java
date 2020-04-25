package net.firstpartners.ui.component;

import java.util.Properties;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import org.apache.commons.lang.StringUtils;

import net.firstpartners.ui.utils.Config;

/**
 * Utility class for generating HTML used by the GUI
 * @author PBrowne
 *
 */
public class HtmlGenerator {

	/**
	 * Get a HTMLEditor Kit with a standard CSS
	 * @return
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
	 */
	public static String getupdatedHomeSreenHtml(Properties copyOfProperties) {

		// Default if we are running standalone
		String excelFile = "";
		String outputFileName = "";
		String ruleFileAsString = "";

		// Get UserSpecifc information we need to display
		if (copyOfProperties != null) {
			excelFile = StringUtils.escape(copyOfProperties.getProperty(Config.EXCEL_INPUT));
			outputFileName = StringUtils.escape(copyOfProperties.getProperty(Config.EXCEL_OUTPUT));

			// build multiple rules files
			String rulesFiles[] = Config.getRuleFiles(copyOfProperties).getRulesLocation();
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


}
