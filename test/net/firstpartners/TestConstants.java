package net.firstpartners;

/**
 * Constants used for testing - mainly where we store data
 * 
 * @author PBrowne
 *
 */
public class TestConstants {

	// Tmp rules and data file names
	public static final String EXCEL_DATA_FILE = "war/sampleresources/ExcelDataRules/chocolate-data.xls";

	public static final String[] RULES_FILES = new String[] {
			"http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/log-then-modify-rules.drl" };

	public static final String EXCEL_OUTPUT_FILE = "war/sampleresources/ExcelDataRules/chocolate-output.xls";

	public static final String KNOWLEDGE_BASE_FILE = "war/sampleresources/SpreadSheetServlet/log-then-modify-rules.KnowledgeBase";

	// the name of the sheet the we log files to
	public static final String EXCEL_LOG_WORKSHEET_NAME = "log";
}
