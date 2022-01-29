package net.firstpartners.core.drools.loader;

import java.io.File;
import java.util.Iterator;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
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
public class RedRuleBuilder implements IRuleLoaderStrategy {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	
	public KieBuilder loadRules(RedModel ruleSource) {

		
		// Handles
		File currentFile;

		// For Kie later
		KieServices ks = KieServices.Factory.get();
		KieFileSystem kfs = ks.newKieFileSystem();

		// Loop through and read rule files locations
		String[] rulesLocs = ruleSource.getRulesFilesLocations();
		for (int counter = 0; counter < rulesLocs.length; counter++) {

			log.debug("loading into KFS:" + rulesLocs[counter]);
			currentFile = new File(rulesLocs[counter]);
			Resource resource = ks.getResources().newFileSystemResource(currentFile).setResourceType(ResourceType.DRL);
			kfs.write(resource);
		}

		// Try to compile the rules
		KieBuilder kBuilder = KieServices.Factory.get().newKieBuilder(kfs);
		kBuilder.buildAll();
		Results results = kBuilder.getResults();

		if (results.hasMessages(Message.Level.ERROR)) {
			log.warn("There are warning in a score DRL:\n{}", results);
			throw new IllegalStateException("There are errors in a score DRL:\n");

		}

		// Log the messages
		log.debug("Rule Compilation results:");

		Message thisCompileMessage;

		Iterator<Message> iter = results.getMessages().iterator();
		while (iter.hasNext()) {
			thisCompileMessage = iter.next();
			log.debug(thisCompileMessage.getLevel() + " | " + thisCompileMessage.getPath() + " | "
					+ thisCompileMessage.getLine() + " | " + thisCompileMessage.getText());
		}

		return kBuilder;
	}


}
