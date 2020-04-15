package net.firstpartners.sample.ExcelDataRules;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.drools.compiler.compiler.DroolsParserException;
import org.junit.Test;

public class ExcelDataExampleTest {

	@Test
	public final void testRunExcelDataExample() throws DroolsParserException, IOException, ClassNotFoundException, InvalidFormatException {

		Workbook hsWorkbook = new ExcelDataExample().runExcelDataExample();
		assertNotNull(hsWorkbook);

	}

}
