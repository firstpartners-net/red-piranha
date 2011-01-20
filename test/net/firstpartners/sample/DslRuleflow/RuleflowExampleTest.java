package net.firstpartners.sample.DslRuleflow;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import net.firstpartners.sample.DslRuleflow.RuleflowExample;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.drools.compiler.DroolsParserException;
import org.junit.Test;

public class RuleflowExampleTest {

	@Test
	public final void testRunRuleflowExample() throws DroolsParserException, IOException, ClassNotFoundException {

		RuleflowExample thisSample =new RuleflowExample();
		HSSFWorkbook wb = thisSample.runRuleflowExample();

		assertNotNull(wb);

		fail("Test needs further assertions"); // TODO
	}

}
