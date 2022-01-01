package net.firstpartners.core.drools.loader;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.data.RedModel;

/**
 * Build the
 * 
 * 
 * @author paulf
 * @todo refactor into Rule Loader pattern, part of move to KIE / Drools 7.5
 */
public class RedRuleBuilder {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public KieModule getRulesFromDisk(RedModel ruleSource) {

		KieServices ks = KieServices.Factory.get();
		KieFileSystem kfs = ks.newKieFileSystem();

		// Loop through and read rule files locations
		String[] rulesLocs = ruleSource.getRulesLocation();
		for (int counter = 0; counter < rulesLocs.length; counter++) {
			log.debug("loading into KFS:" + rulesLocs[counter]);
			kfs.read(rulesLocs[counter]);
		}

		// Try to compile the rules
		KieBuilder kBuilder = KieServices.Factory.get().newKieBuilder(kfs);
		Results results = kBuilder.getResults();

		if (results.hasMessages(Message.Level.ERROR)) {
			log.warn("There are warning in a score DRL:\n{}", results);
			throw new IllegalStateException("There are errors in a score DRL:\n");

		}

		return kBuilder.getKieModule();
	}

}
