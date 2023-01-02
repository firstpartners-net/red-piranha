package net.firstpartners.core.drools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.kie.dmn.api.core.DMNModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.TestConstants;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.data.Cell;

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
		Collection<Cell> result = myRunner.runModel(testModel);

		//Assert on values getting passed back
		assertNotNull(result);
		assert(result.size()>0);	
		//log.debug("UPDATE ME!!"+result.toArray()[0].toString());
		assert("Cell (name=='Name' value=='Paul')".equals(result.toArray()[0].toString()));
	

		
	}

	@Test
	public void testconvertDecisionResultToCells() throws RPException{
		RedModel testModel = new RedModel(TestConstants.SIMPLE_EXCEL, TestConstants.SIMPLE_DECISION_MODEL,
		TestConstants.CSV_TMP_FILE);

		//implicit test - we should always get a decision model		
		DecisionModelRunner myRunner = (DecisionModelRunner)RunnerFactory.getRuleRunner(testModel);

		Collection<Cell>tr1=myRunner.convertDecisionResultToCells("Hello");
		assert(tr1.toString().equals("[Cell (name=='' value=='Hello')]"));
		//log.debug("TEST1!!!! "+myRunner.convertDecisionResultToCells("Hello"));
		
		Collection<Cell>tr2=myRunner.convertDecisionResultToCells(true);
		assert(tr2.toString().equals("[Cell (name=='' value=='true')]"));
		//log.debug("TEST2!!!! "+myRunner.convertDecisionResultToCells(true));
		
		Collection<Cell>tr3=myRunner.convertDecisionResultToCells(-100);
		assert(tr3.toString().equals("[Cell (name=='' value=='-100')]"));
		//	log.debug("TEST3!!!! "+myRunner.convertDecisionResultToCells(-100));
		
		Cell testCell = new Cell("myName","myValue");
		Collection<Cell>tr4=myRunner.convertDecisionResultToCells(testCell);
		log.debug(""+tr4);
		assert(tr4.size()==1);

		ArrayList<Cell> testCells = new ArrayList<Cell>();
		testCells.add(new Cell("n1","v1"));
		testCells.add(new Cell("n2","v2"));
		testCells.add(new Cell("n3","v3"));
		Collection<Cell>tr5=myRunner.convertDecisionResultToCells(testCells);
		log.debug(""+tr5);
		assert(tr5.size()==3);
		

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