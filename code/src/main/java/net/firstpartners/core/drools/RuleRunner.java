package net.firstpartners.core.drools;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.drools.drl.parser.DroolsParserException;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.logger.KnowledgeRuntimeLoggerFactory;

import net.firstpartners.core.Config;
import net.firstpartners.core.IDocumentInStrategy;
import net.firstpartners.core.IDocumentOutStrategy;
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.drools.loader.RuleBuilder;
import net.firstpartners.core.log.IStatusUpdate;
import net.firstpartners.data.Cell;

/**
 * Call JBoss Drools (Rules Engine) passing in Document data as Java Objects
 *
 * This class uses an IDocumentStrategy Object to handle different types
 *
 * @author paulf
 * @version $Id: $Id
 */
public class RuleRunner extends AbstractRunner  {



	/**
	 * Construct a new RuleRunner.
	 *
	 * @see RunnerFactory in this package which we use to build a properly
	 *      constructed instance of this class
	 * @param documentStrategy a {@link net.firstpartners.core.IDocumentInStrategy}
	 *                         object
	 * @param outputStrategy   a {@link net.firstpartners.core.IDocumentOutStrategy}
	 *                         object
	 * @param appConfig        a {@link net.firstpartners.core.Config} object
	 */
	protected RuleRunner(IDocumentInStrategy documentStrategy, IDocumentOutStrategy outputStrategy, Config appConfig) {
		this.inputStrategy = documentStrategy;
		this.outputStrategy = outputStrategy;
		this.appConfig = appConfig;
	}

	
	

	/**
	 * Run the rules
	 *
	 * @param rulesUrl   - array of rule files that we need to load
	 * @param dslFileUrl - optional dsl file name (can be null)
	 * @param facts      - Javabeans to pass to the rule engine
	 * @param globals    - global variables to pass to the rule engine
	 * @param logger     - handle to a logging object
	 * @return
	 * @throws Exception
	 */
	 Collection<Cell> runModel(RedModel model)
			throws RPException {

		// The most common operation on a rulebase is to create a new rule
		// session; either stateful or stateless.
		log.debug("Creating new rule base");
		KieModule masterRulebase = new RuleBuilder().loadRules(model, appConfig).getKieModule();

		boolean showFullRuleEngineLogs = appConfig.getShowFullRuleEngineLogs();

		log.debug("running stateless rules");
		return runModel(masterRulebase, model.getFacts(), model.getGlobals(), showFullRuleEngineLogs, model);
	}

	/**
	 * Run Stateless rules using a pre built knowledge base
	 *
	 * @param preBuiltKnowledgeBase
	 * @param facts
	 * @param globals
	 * @param logger
	 * @return any new facts created by the working memory
	 * @throws DroolsParserException
	 * @throws IOException
	 */
	Collection<Cell> runModel(KieModule preBuiltKnowledgeBase, Collection<Cell> facts,
			HashMap<String, Cell> globals, boolean showFullRuleEngineLogs, IStatusUpdate modelAsLogger) throws RPException {

		log.debug("Creating new stateless working memory");

		KieContainer kc = KieServices.Factory.get().newKieContainer(preBuiltKnowledgeBase.getReleaseId());
		StatelessKieSession kSession = kc.newStatelessKieSession();

		// Add the logger
		log.debug("Inserting handle to logger (via global)");
		kSession.setGlobal("log", modelAsLogger);

		log.debug("Checking for globals");
		if (globals != null) {
			for (String o : globals.keySet()) {
				log.debug("Inserting global name: " + o + " value:" + globals.get(o));
				kSession.setGlobal(o, globals.get(o));
			}
		}

		// We need to 'listen' for new cells being created so we can add to our results
		SessionCellListener cellListener = new SessionCellListener();
		kSession.addEventListener(cellListener);

		// setup listeners
		if (showFullRuleEngineLogs) {
			kSession.addEventListener(new DebugAgendaEventListener());
			kSession.addEventListener(new DebugRuleRuntimeEventListener());

			// Set up a file based audit logger
			KnowledgeRuntimeLoggerFactory.newFileLogger(kSession, "WorkItemConsequence.log");
		}



		log.debug("==================== Starting Rules ====================");

		// Fire using the facts
		kSession.execute(facts);

		log.debug("==================== Rules Complete ====================");
		List<Cell> additionalFacts = cellListener.getNewCells(facts);

		return additionalFacts;

	}


}
