package net.firstpartners.core.drools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.RedModel;
import net.firstpartners.core.excel.ExcelInputStrategy;
import net.firstpartners.core.excel.ExcelOutputStrategy;

import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

public class RuleRunnerTest {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void testRunDmnModel() throws Exception {
		
		RedModel testModel = new RedModel("some-in-name.xls", "examples/3_simple_dmn/decision_model.dmn", "some-dummy-out.xls");

		
		RuleRunner myRunner = RuleRunnerFactory.getRuleRunner(testModel);
		//myRunner.callRules(testModel);

		KieServices kieServices = KieServices.Factory.get();

		KieContainer kieContainer = kieServices.getKieClasspathContainer();

		DMNRuntime dmnRuntime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);

		String namespace = "https://kiegroup.org/dmn/_54252F75-EDEF-4D4A-81DC-EA924A966D0E";
//		String modelName = TestConstants.EXAMPLES_DECISION_MODEL;
		
		DMNModel dmnModel = dmnRuntime.getModel(namespace, testModel.getRuleFileLocation());

		//Create and evaluate the runtime
		DMNContext dmnContext = dmnRuntime.newContext();  

	//	for (Integer age : Arrays.asList(1,12,13,64,65,66)) {
//			dmnContext.set("Age", age);  
			DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);  
			for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {  
			  log.info(dr.getDecisionName() + "' : " + dr.getResult());
			}
//		  }


	}
}