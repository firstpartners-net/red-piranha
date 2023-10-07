package net.firstpartners;

/**
 * Constants used for testing - mainly where we store data
 * 
 * @author PBrowne
 *
 */
public class TestConstants {

	// Sample Excel files
	public static final String XLS_DATA_FILE = "src/test/resources/testdata/chocolate-data-tricky-formatting.xls";
	public static final String XLSX_DATA_FILE = "src/test/resources/testdata/chocolate-data-tricky-formatting.xlsx";
	public final static String COMPLEX_EXCEL="src/main/resources/examples/4_complex_excel/Complex_Financial.xlsx";
	
	//Sample word files
	public static final String WORDX_DATA_FILE = "src/test/resources/testdata/simple-word.docx";
	public static final String WORD_DATA_FILE = "src/test/resources/testdata/simple-word.doc";

	//Sample CSV file to append to
	public static final String CSV_APPEND_FILE="src/test/resources/testdata/sample-csv-to-append-to.csv";
	
	
	//Sample Rules files
	public static final String SIMPLE_LOG_MODIFY_RULES_FILE = "src/test/resources/testrules/log-then-modify-rules.drl";
	public static final String[] MULTIPLE_RULES_FILES_IN_EXCEL = new String[] {
			"src/test/resources/testrules/log-rules.drl", "src/test/resources/testrules/TradingRules.xls" };
	
	public static final String RULE_FILE_FOR_WORD = "src/test/resources/testrules/modify-word.drl";

	//Sample Output (excel)
	public static final String FILE_OUTPUT_FILE = "src/test/resources/testdata/chocolate-output.xls";
	
	//Serialized Test Data(JSON)
	public static final String JSON_SERIAL_FILE_SIMPLE ="src/test/resources/testdata/sample_xls_serialised.json";
	public static final String JSON_SERIAL_FILE_MEDIUM ="src/test/resources/testdata/medium_xls_serialised.json";///root/rp2/code/src/test/resources/testdata/medium_xls_serialised.json
	public static final String JSON_SERIAL_FILE_COMPLEX ="src/test/resources/testdata/complex_xls_serialised.json";
	
	//Serialized Test Data (Binary)
	public final static String SAVED_EXCEL_RANGEHOLDER_DATA="src/test/resources/testdata/RANGEHOLDER_RANGE_CELLS.testdata";
	public final static String SAVED_WORD_RANGEHOLDER_DATA="src/test/resources/testdata/RANGEHOLDER_RANGE_WORD.testdata";
	public final static String SAVED_WORDX_RANGEHOLDER_DATA="src/test/resources/testdata/RANGEHOLDER_RANGE_WORDX.testdata";
	
	//Serialized Knowledge Bases
	public static final String KNOWLEDGE_BASE_FILE = "src/test/resources/testrules/log-then-modify-rules.KnowledgeBase";
	public static final String KNOWLEDGE_BASE_FILE_TMP ="src/test/resources/testrules/tmp.KnowledgeBase";

	//Decision model test
	public static final String SIMPLE_DECISION_MODEL = "src/main/resources/examples/3_simple_dmn/decision_model.dmn";
	public final static String SIMPLE_EXCEL="src/main/resources/examples/3_simple_dmn/data.xlsx";

	//Groovy Language scripts
	public final static String GROOVY_PREPROCESS="src/main/resources/examples/4_complex_excel/InjectNamedRanges.groovy";

	//tmp files
	public static final String EXCEL_TMP_FILE = "tmp.xls"; 
	public static final String XLSX_TMP_FILE = "src/test/resources/testdata/tmp.xlsx";
	public static final String CSV_TMP_FILE_SINGLE_LINE = "src/test/resources/testdata/tmp-single.csv";
	public static final String CSV_TMP_FILE_MULTI_LINE = "src/test/resources/testdata/tmp-multi.csv";

	public static final String PDF_TMP_FILE = "src/test/resources/testdata/tmp.pdf";
	public static final String JSON_TMP_FILE = "src/test/resources/testdata/tmp.json";
	
	

}
