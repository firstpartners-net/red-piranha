package net.firstpartners.sample.ExcelDataRules;

import static org.junit.Assert.fail;

import java.io.IOException;

import net.firstpartners.sample.ExcelDataRules.ExcelDataRulesExample;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.drools.compiler.DroolsParserException;
import org.junit.Test;

public class ExcelDataRulesExampleTest {

	@Test
	public final void testRunExcelDataRulesExample() throws DroolsParserException, IOException, ClassNotFoundException {
		HSSFWorkbook hsWorkbook = new ExcelDataRulesExample().runExcelDataRulesExample();

		fail("Need to add checks on output"); // TODO
	}

}
