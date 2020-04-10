package net.firstpartners.core.drools;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

import org.drools.KnowledgeBase;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;

import net.firstpartners.core.drools.data.RuleSource;
import net.firstpartners.core.drools.log.ILogger;

/**
 * Load rules
 *
 * @author paul
 *
 */
public class RuleRunner {

	// Handle to loader
	private final IRuleLoader loader;

	// Handle to logger
	private static final Logger log = Logger.getLogger(RuleRunner.class
			.getName());

	public RuleRunner(IRuleLoader ruleLoader) {

		loader = ruleLoader;

	}

	/**
	 * Run the rules
	 *
	 * @param rulesUrl
	 *            - array of rule files that we need to load
	 * @param dslFileUrl
	 *            - optional dsl file name (can be null)
	 * @param facts
	 *            - Javabeans to pass to the rule engine
	 * @param globals
	 *            - global variables to pass to the rule engine
	 * @param logger
	 *            - handle to a logging object
	 * @throws IOException
	 * @throws DroolsParserException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public void runStatelessRules(RuleSource ruleSource, ILogger logger)
			throws DroolsParserException, IOException, ClassNotFoundException {

		// The most common operation on a rulebase is to create a new rule
		// session; either stateful or stateless.
		log.info("Creating master rule base");
		KnowledgeBase masterRulebase = loader.loadRules(ruleSource);

		log.info("running stateless rules");
		runStatelessRules(masterRulebase, ruleSource.getFacts(), ruleSource
				.getGlobals(), logger);

	}

	/**
	 * Run Stateless rules using a prebuilt knowledgebase
	 *
	 * @param preBuiltKnowledgeBase
	 * @param facts
	 * @param globals
	 * @param logger
	 * @throws DroolsParserException
	 * @throws IOException
	 */
	public void runStatelessRules(KnowledgeBase preBuiltKnowledgeBase,
			Collection<Object> facts, HashMap<String, Object> globals,
			ILogger logger) throws DroolsParserException, IOException {

		// Create a new stateless session
		log.info("Creating new working memory");
		StatelessKnowledgeSession workingMemory = preBuiltKnowledgeBase
				.newStatelessKnowledgeSession();

		log.info("Checking for globals");
		if (globals != null) {
			for (String o : globals.keySet()) {
				log.info("Inserting global name: " + o + " value:"
						+ globals.get(o));
				workingMemory.setGlobal(o, globals.get(o));
			}
		}
		// Add the logger
		log.info("Inserting handle to logger (via global)");
		workingMemory.setGlobal("log", logger);

		log.info("Using facts:" + facts);

		log
		.info("==================== Calling Rule Engine ====================");

		// Fire using the facts
		workingMemory.execute(facts);

		log.info("==================== Rules Complete ====================");

	}

	/**
	 * Run the rules
	 *
	 * @param rulesUrl
	 *            - array of rule files that we need to load
	 * @param dslFileUrl
	 *            - optional dsl file name (can be null)
	 * @param ruleFlowUrl
	 *            - optional ruleFlow file name (can be null)
	 * @param facts
	 *            - Javabeans to pass to the rule engine
	 * @param globals
	 *            - global variables to pass to the rule engine
	 * @param logger
	 *            - handle to a logging object
	 * @throws IOException
	 * @throws DroolsParserException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public StatefulKnowledgeSession getStatefulSession(RuleSource ruleSource,
			ILogger logger) throws DroolsParserException, IOException,
	ClassNotFoundException {

		// The most common operation on a rulebase is to create a new rule
		// session; either stateful or stateless.
		KnowledgeBase masterRulebase = loader.loadRules(ruleSource);

		return getStatefulSession(masterRulebase, ruleSource.getGlobals(),
				logger);

	}

	/**
	 * Get a Stateful Session for a pre built knowledgebase
	 *
	 * @param preBuiltKnowledgeBase
	 * @param globals
	 * @param logger
	 * @return
	 * @throws DroolsParserException
	 * @throws IOException
	 */
	public StatefulKnowledgeSession getStatefulSession(
			KnowledgeBase preBuiltKnowledgeBase,
			HashMap<String, Object> globals, ILogger logger)
					throws DroolsParserException, IOException {
		// Create a new stateful session
		StatefulKnowledgeSession workingMemory = preBuiltKnowledgeBase
				.newStatefulKnowledgeSession();

		for (String o : globals.keySet()) {
			log
			.info("Inserting global name: " + o + " value:"
					+ globals.get(o));
			workingMemory.setGlobal(o, globals.get(o));
		}

		// Add the logger
		log.info("Inserting handle to logger (via global)");
		workingMemory.setGlobal("log", logger);

		return workingMemory;

	}

}
