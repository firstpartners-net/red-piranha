package net.firstpartners.core.drools;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.RedModel;
import net.firstpartners.data.Cell;

public class RuleRunnerTest {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testRunDmnModel() throws Exception {

		RedModel testModel = new RedModel("some-in-name.xls", "examples/3_simple_dmn/decision_model.dmn",
				"some-dummy-out.xls");

		// RuleRunner myRunner = RuleRunnerFactory.getRuleRunner(testModel);
		// myRunner.callRules(testModel);

		KieServices kieServices = KieServices.Factory.get();

		KieContainer kieContainer = kieServices.getKieClasspathContainer();

		DMNRuntime dmnRuntime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);

		String namespace = "https://kiegroup.org/dmn/_54252F75-EDEF-4D4A-81DC-EA924A966D0E";
		// String modelName = TestConstants.EXAMPLES_DECISION_MODEL;

		// Debugging code to see the models available to us
		List<DMNModel> modelList = dmnRuntime.getModels();
		Iterator<DMNModel> modelLoop = modelList.iterator();
		while (modelLoop.hasNext()) {

			DMNModel thisModel = modelLoop.next();
			log.info("Name:" + thisModel.getName());
			log.info("NameSpace:" + thisModel.getNamespace());

		}

		DMNModel dmnModel = dmnRuntime.getModel(namespace, "decision_model");

		if (dmnModel == null) {
			throw new Exception("DMNModel not found");
		}

		// Create and evaluate the runtime
		DMNContext dmnContext = dmnRuntime.newContext();

		Cell testCell = new Cell("Name", "Paul");

		dmnContext.set("InputCell", testCell);

		DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
		for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
			log.info("Result:" + dr.getDecisionName() + " : " + dr.getResult());
		}

	}
}