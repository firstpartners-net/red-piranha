package net.firstpartners.core.drools.loader;

import java.io.File;
import java.io.FileNotFoundException;
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

import net.firstpartners.core.Config;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.file.ResourceFinder;

/**
 * Obtain the Drools Rules in a runnable format Could be from the Web, local
 * filesystem. May need to compile if needed
 * 
 * @author paulf
 * @todo refactor into Rule Loader pattern, part of move to KIE / Drools 7.5
 */
public class RuleBuilder {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());


	/**
	 * Load the rules from the sources listed in the Data model and configuration
	 * 
	 * @param ruleSource - the data model
	 * @param appConfig  - application configuration, including where the rules
	 *                   files are stored
	 * @return
	 * @throws FileNotFoundException 
	 */
	public KieBuilder loadRules(RedModel ruleSource, Config appConfig) throws FileNotFoundException {

		// Handles
		File currentFile;

		// For Kie later
		KieServices ks = KieServices.Factory.get();
		KieFileSystem kfs = ks.newKieFileSystem();

		// Loop through and read rule files locations
		String[] rulesLocs = ruleSource.getRulesFilesLocations();
		for (int counter = 0; counter < rulesLocs.length; counter++) {

			log.debug("loading into KFS:" + rulesLocs[counter]);
			currentFile = ResourceFinder.getFileResourceUsingConfig(rulesLocs[counter], appConfig);
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
