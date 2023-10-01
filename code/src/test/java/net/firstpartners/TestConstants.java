package net.firstpartners;

/**
 * Constants used for testing - mainly where we store data
 * 
 * @author PBrowne
 *
 */
public class TestConstants {

	// Tmp rules and data file names
	public static final String XLS_DATA_FILE = "src/test/resources/testdata/chocolate-data-tricky-formatting.xls";
	
	public static final String XLSX_DATA_FILE = "src/test/resources/testdata/chocolate-data-tricky-formatting.xlsx";
	
	//Sample word files
	public static final String WORDX_DATA_FILE = "src/test/resources/testdata/simple-word.docx";
	public static final String WORD_DATA_FILE = "src/test/resources/testdata/simple-word.doc";

	//Sample CSV file to append to
	public static final String CSV_APPEND_FILE="src/test/resources/testdata/sample-csv-to-append-to.csv";
	
	
	//Sample Rules files
	public static final String RULES_FILE = "src/test/resources/testrules/log-then-modify-rules.drl";
	
	public static final String[] RULES_FILES_IN_EXCEL = new String[] {
			"src/test/resources/testrules/log-rules.drl", "src/test/resources/testrules/TradingRules.xls" };
	
	public static final String RULE_FILE_FOR_WORD = "src/test/resources/testrules/modify-word.drl";

	public static final String FILE_OUTPUT_FILE = "src/test/resources/testdata/chocolate-output.xls";
	
	//Serialised files
	public static final String JSON_SERIAL_FILE ="src/test/resources/testdata/sample_xls_serialised.json";
	
	//tmp files
	public static final String EXCEL_TMP_FILE = "tmp.xls"; // was src/test/resources/testdata/tmp.xls"
	public static final String CSV_TMP_FILE = "src/test/resources/testdata/tmp.csv";
	public static final String PDF_TMP_FILE = "src/test/resources/testdata/tmp.pdf";
	public static final String JSON_TMP_FILE = "src/test/resources/testdata/tmp.json";

	public static final String KNOWLEDGE_BASE_FILE = "src/test/resources/testrules/log-then-modify-rules.KnowledgeBase";
	public static final String KNOWLEDGE_BASE_FILE_TMP ="src/test/resources/testrules/tmp.KnowledgeBase";

	// the name of the sheet the we log files to
	public static final String EXCEL_LOG_WORKSHEET_NAME = "log";
	
	//Serialized Test Data
	public final static String SAVED_EXCEL_RANGEHOLDER_DATA="src/test/resources/testdata/RANGEHOLDER_RANGE_CELLS.testdata";
	public final static String SAVED_WORD_RANGEHOLDER_DATA="src/test/resources/testdata/RANGEHOLDER_RANGE_WORD.testdata";
	public final static String SAVED_WORDX_RANGEHOLDER_DATA="src/test/resources/testdata/RANGEHOLDER_RANGE_WORDX.testdata";
	
	
	//Rules and Models used in tests
	//public static final String EXAMPLES_DOMAIN_SPECIFIC_LANGUAGE_LOG_RULES_DSLR = "exaprmples/4-domain-specific-language/log-rules.dslr";
	//public static final String EXAMPLES_DOMAIN_SPECIFIC_LANGUAGE_CELL_LOGGING_DSL = "examples/4-domain-specific-language/cell-logging.dsl";


	//Decision model test
	public static final String SIMPLE_DECISION_MODEL = "src/main/resources/examples/3_simple_dmn/decision_model.dmn";
	public final static String SIMPLE_EXCEL="src/main/resources/examples/3_simple_dmn/data.xlsx";

	//Groovy Language test
	public final static String GROOVY_PREPROCESS="src/main/resources/examples/4_complex_excel/InjectNamedRanges.groovy";
	public final static String COMPLEX_EXCEL="src/main/resources/examples/4_complex_excel/Complex_Financial.xlsx";
	

}
