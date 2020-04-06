package net.firstpartners.sample.ExcelDataRules;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.drools.compiler.compiler.DroolsParserException;

public class ExcelDataRulesExampleTest {

	public final void testRunExcelDataRulesExample() throws DroolsParserException, IOException, ClassNotFoundException {
		HSSFWorkbook hsWorkbook = new ExcelDataRulesExample().runExcelDataRulesExample();

		fail("Need to add checks on output"); // TODO
	}

}
