package net.firstpartners.sample.ExcelDataRules;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.drools.compiler.compiler.DroolsParserException;
import org.junit.Test;

public class ExcelDataExampleTest {

	@Test
	public final void testRunExcelDataExample() throws DroolsParserException, IOException, ClassNotFoundException {

		HSSFWorkbook hsWorkbook = new ExcelDataExample().runExcelDataExample();

	}

}
