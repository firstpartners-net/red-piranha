package net.firstpartners.sample.ExcelDataRules;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.drools.compiler.compiler.DroolsParserException;

public class ExcelDataRulesExampleTest {

	public final void testRunExcelDataRulesExample() throws DroolsParserException, IOException, ClassNotFoundException {
		HSSFWorkbook hsWorkbook = new ExcelDataRulesExample().runExcelDataRulesExample();

	}

}
