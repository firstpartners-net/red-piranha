package net.firstpartners.sample.DslRuleflow;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.drools.compiler.compiler.DroolsParserException;

public class RuleflowExampleTest {

	public final void testRunRuleflowExample() throws DroolsParserException, IOException, ClassNotFoundException {

		RuleflowExample thisSample =new RuleflowExample();
		HSSFWorkbook wb = thisSample.runRuleflowExample();

		assertNotNull(wb);

		//fail("Test needs further assertions"); // TODO
	}

}
