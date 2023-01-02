package net.firstpartners.core.drools;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kie.dmn.api.core.DMNModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;

public class DecisionModelRunnerTest {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * @throws Exception
	 */
	@Test
	public void testRunDmnModel() throws RPException {

		RedModel testModel = new RedModel(TestConstants.SIMPLE_EXCEL, TestConstants.SIMPLE_DECISION_MODEL,
		TestConstants.CSV_TMP_FILE);

		//implicit test - we should always get a decision model		
		DecisionModelRunner myRunner = (DecisionModelRunner)RunnerFactory.getRuleRunner(testModel);

		//Try running the code - this is a direct call, not intended for access
		myRunner.runModel(testModel);
		
		/// TODO REFACTOR AFTER H
		/// DECIDE HOW TO TREAT CELLS
		RedModel returnModel = myRunner.callRules(testModel);




/*
		// Create and evaluate the runtime
		DMNContext dmnContext = dmnRuntime.newContext();

		Cell testCell = new Cell("Name", "Paul");

		dmnContext.set("InputCell", testCell);

		DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
		for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
			log.info("Result:" + dr.getDecisionName() + " : " + dr.getResult());
		}

		//Loop through messages
		for (DMNMessage dm : dmnResult.getMessages()) {
			log.info("Message:"+dm.getText());
		}

		//Assert on values getting passed back
 */
		
	}

	@Test
	public void testGetModelMadeupNoNameSpace() throws RPException{

		DecisionModelRunner testRunner = new DecisionModelRunner(null,null,null);

		DMNModel myModel = testRunner.getDmnModel("namespace-wont-match",TestConstants.SIMPLE_DECISION_MODEL);

		assertNotNull(myModel);

	}

	@Test
	public void testGetModelWithNameSpace() throws RPException{

		DecisionModelRunner testRunner = new DecisionModelRunner(null,null,null);

		DMNModel myModel = testRunner.getDmnModel("https://kiegroup.org/dmn/_54252F75-EDEF-4D4A-81DC-EA924A966D0E",TestConstants.SIMPLE_DECISION_MODEL);

		assertNotNull(myModel);

	}
}