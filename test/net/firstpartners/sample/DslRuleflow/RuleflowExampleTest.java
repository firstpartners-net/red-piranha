package net.firstpartners.sample.DslRuleflow;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.drools.compiler.compiler.DroolsParserException;

public class RuleflowExampleTest {

	public final void testRunRuleflowExample() throws DroolsParserException, IOException, ClassNotFoundException, InvalidFormatException {

		RuleflowExample thisSample =new RuleflowExample();
		Workbook wb = thisSample.runRuleflowExample();

		assertNotNull(wb);

	}

}
