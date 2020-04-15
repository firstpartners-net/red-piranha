package net.firstpartners.sample.ExcelDataRules;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.drools.compiler.compiler.DroolsParserException;

public class ExcelDataRulesExampleTest {

	public final void testRunExcelDataRulesExample() throws DroolsParserException, IOException, ClassNotFoundException, InvalidFormatException {
		Workbook workbook = new ExcelDataRulesExample().runExcelDataRulesExample();
		assertNotNull(workbook);
	}

}
