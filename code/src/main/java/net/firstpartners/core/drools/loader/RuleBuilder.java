package net.firstpartners.core.drools.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import net.firstpartners.core.RPException;
import net.firstpartners.core.RedModel;
import net.firstpartners.core.file.ResourceFinder;

/**
 * Obtain the Drools Rules in a runnable format Could be from the Web, local
 * filesystem. May need to compile if needed
 *
 * @author paulf
 * @version $Id: $Id
 */
public class RuleBuilder {

	// Handle to the logger
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Load the rules from the sources listed in the Data model and configuration
	 *
	 * @param redModel  - the data model
	 * @param appConfig - application configuration, including where the rules files
	 *                  are stored
	 * @throws java.lang.Exception
	 * @return a {@link org.kie.api.builder.KieBuilder} object
	 */
	public KieBuilder loadRules(RedModel redModel, Config appConfig) throws RPException {

		// Handles
		File currentFile;

		// final KieBaseConfiguration config =
		// KnowledgeBaseFactory.newKnowledgeBaseConfiguration();

		// config.setOption(DeclarativeAgendaOption.DISABLED); //this is default, but
		// resolves another issue?

		// config.setOption( EventProcessingOption.STREAM);
		// config.setOption("","");

		// Set system property
		// java.lang.System.setProperty("org.kie.api.internal.assembler.KieAssemblers","org.kie.api.internal.utils.AnotherMockAssemblersImpl;4");

		// For Kie later
		KieServices ks = KieServices.Factory.get();
		KieFileSystem kfs = ks.newKieFileSystem();

		// Loop through and read rule files locations
		String[] rulesLocs = redModel.getRulesFilesLocations();
		for (int counter = 0; counter < rulesLocs.length; counter++) {

			// Check for DMN Model - shouldn't need this - just in case
			if (rulesLocs[counter].toLowerCase().endsWith(".dmn")) {
				throw new RPException("Rule Builder not configured to process decision models");
			} else {

				log.debug("loading into KFS:" + rulesLocs[counter]);
				try {
					currentFile = ResourceFinder.getFileResourceUsingConfig(rulesLocs[counter], appConfig);
				} catch (FileNotFoundException e) {
					throw new RPException("Error when loading rules",e);
				}
				Resource resource = ks.getResources().newFileSystemResource(currentFile)
						.setResourceType(ResourceType.DRL);
				kfs.write(resource);

			}

		}

		// Try to compile the rules
		KieBuilder kBuilder = KieServices.Factory.get().newKieBuilder(kfs);
		kBuilder.buildAll();
		Results results = kBuilder.getResults();

		// Log the messages and rule building errors as appropriate
		log.debug("Rule Compilation results:");

		Message thisRuleBuildMessage;
		String messageAsString;
		List<String> messages = new ArrayList<String>();

		Iterator<Message> iter = results.getMessages().iterator();
		while (iter.hasNext()) {
			thisRuleBuildMessage = iter.next();

			// build our user friendly messages for display line by line
			messages.add("Rule Build Message: " + thisRuleBuildMessage.getText());
			messages.add("File: " + thisRuleBuildMessage.getPath());
			messages.add("Line: " + thisRuleBuildMessage.getLine());
			messages.add("Column: " + thisRuleBuildMessage.getColumn());

			// More compact message for logging
			messageAsString = thisRuleBuildMessage.getPath() + " | " + thisRuleBuildMessage.getLine() + " | "
					+ thisRuleBuildMessage.getText();

			if (thisRuleBuildMessage.getLevel() == Message.Level.ERROR) {
				log.warn(messageAsString);
				redModel.addUIWarnMessage(messages);
			} else {
				log.debug(messageAsString);
				redModel.addUIDebugMessage(messages);
			}

		}

		// Check if we have an errors and raise excpetion
		if (results.hasMessages(Message.Level.ERROR)) {

			log.warn("There are warning in a DRL:\n{}", results);
			throw new Exception("There were errors in a building a Rule file\n");

		}

		return kBuilder;
	}



}
