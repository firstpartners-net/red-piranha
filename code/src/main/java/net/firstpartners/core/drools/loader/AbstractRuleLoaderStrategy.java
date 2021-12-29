package net.firstpartners.core.drools.loader;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.common.DroolsObjectInputStream;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;

import net.firstpartners.core.log.RpLogger;

/**
 * Base class to support loading Rules Related files
 * 
 * @author PBrowne
 *
 */
public abstract class AbstractRuleLoaderStrategy implements IRuleLoaderStrategy {

	// How we recognize rules written in a Domain Specific language files extensions
	public static final String DSLR_FILE_EXTENSION = ".dslr";

	private static final Logger log = RpLogger.getLogger(AbstractRuleLoaderStrategy.class.getName());

	// How we recognize Excel rule files extensions
	public static final String XLS_FILE_EXTENSION = ".xls";

	/**
	 * Load the rule file (non Excel)
	 *
	 * @param ruleUrl
	 * @param dslFileUrl
	 * @param ruleFlowFileUrl
	 * @param addRulesToThisBuilder
	 * @throws DroolsParserException
	 * @throws IOException
	 */
	private void addDslFileToKnowledgeBuilder(String dslFileUrl, KnowledgeBuilder addRulesToThisBuilder)
			throws DroolsParserException, IOException {


		log.debug("Loading DSL file:" + dslFileUrl);
		
		// Check if the user has passed in a DSL
		if (dslFileUrl != null) {

			Reader dslFileAsReader = getReader(dslFileUrl);

			addRulesToThisBuilder.add(ResourceFactory.newReaderResource(dslFileAsReader), ResourceType.DSL);
			dslFileAsReader.close();

		}

	}

	/**
	 * Load Excel Rules (where the rules are stored in Excel)
	 *
	 * @param excelRuleFileUrl
	 * @param addRulesToThisBuilder
	 * @throws DroolsParserException
	 * @throws IOException
	 */
	private void addExcelRulesToKnowledgeBuilder(String excelRuleFileUrl, KnowledgeBuilder addRulesToThisBuilder)
			throws DroolsParserException, IOException {

		// //same as previous - we add the excel to our package
		byte[] excelAsBytes = getFile(excelRuleFileUrl);

		addRulesToThisBuilder.add(ResourceFactory.newByteArrayResource(excelAsBytes), ResourceType.DTABLE);

	}

	/**
	 * Load the rule file (non Excel)
	 *
	 * @param ruleLocation
	 * @param dslFileUrl
	 * @param ruleFlowFileUrl
	 * @param addRulesToThisBuilder
	 * @throws DroolsParserException
	 * @throws IOException
	 */
	private void addRuleToKnowledgeBuilder(String ruleLocation, KnowledgeBuilder addRulesToThisBuilder)
			throws DroolsParserException, IOException {

		// Load the main rule file
		
		Reader ruleFileAsReader = getReader(ruleLocation);

		// Decide the type of rule we have
		if (ruleLocation.toLowerCase().endsWith(DSLR_FILE_EXTENSION)) {
			log.debug("Loading as DSLR:"+ruleLocation);
			addRulesToThisBuilder.add(ResourceFactory.newReaderResource(ruleFileAsReader), ResourceType.DSLR);

		} else {
			log.debug("Loading as DRL:"+ruleLocation);
			addRulesToThisBuilder.add(ResourceFactory.newReaderResource(ruleFileAsReader), ResourceType.DRL);

		}

		ruleFileAsReader.close();

	}

	/**
	 * Load the rule file (non Excel)
	 *
	 * @param ruleUrl
	 * @param dslFileUrl
	 * @param ruleFlowFileUrl
	 * @param addRulesToThisBuilder
	 * @throws DroolsParserException
	 * @throws IOException
	 */
	private void addRuleFlowToKnowledgeBuilder(String ruleFlowFileUrl, KnowledgeBuilder addRulesToThisBuilder)
			throws DroolsParserException, IOException {

		// if we've specified a ruleflow, add this to the package
		if (ruleFlowFileUrl != null) {

			log.debug("Loading RuleFlow file");
			Reader ruleFlowAsReader = getReader(ruleFlowFileUrl);

			addRulesToThisBuilder.add(ResourceFactory.newReaderResource(ruleFlowAsReader), ResourceType.DSLR);

		}


	}

	/**
	 * Abstract methods, implented in sub classes
	 *
	 * @param excelRuleFileUrl
	 * @return
	 * @throws IOException
	 */
	abstract byte[] getFile(String excelRuleFileResource) throws IOException;

	/**
	 * Get an InputStream for a given resource
	 *
	 * @param resource to find
	 * @return
	 * @throws IOException
	 */
	abstract InputStream getInputStream(String resource) throws IOException;

	/**
	 * Get a reader for a given resource
	 *
	 * @param ruleFlowFileUrl
	 * @return
	 * @throws IOException
	 */
	abstract Reader getReader(String resource) throws IOException;

	/**
	 * Load a previously cached resource (that has been saved using Base64)
	 *
	 * @param cacheResourceUnderName
	 * @return - the first serialized Knowledgebase in the file
	 * @throws DroolsParserException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	org.drools.KnowledgeBase loadKnowledgeBase(RuleConfig ruleSource)
			throws IOException, ClassNotFoundException, SecurityException {

		BufferedInputStream inStream = new BufferedInputStream(getInputStream(ruleSource.getKnowledgeBaseLocation()));

		ByteArrayOutputStream holdStream = new ByteArrayOutputStream();

		// Write stream
		while (inStream.available() != 0) {
			holdStream.write(inStream.read());
		}

		// Convert this string from Base64 t0 binary
		byte[] base64Bytes = holdStream.toByteArray();
		byte[] binaryBytes = Base64.decodeBase64(base64Bytes);

		DroolsObjectInputStream in = new DroolsObjectInputStream(new ByteArrayInputStream(binaryBytes));

		Object inObject = in.readObject();
		log.debug("inObject:" + inObject.getClass());

		return (org.drools.KnowledgeBase) inObject;

	}

	/**
	 * Load multiple rules, with optional dsl and ruleflow file
	 *
	 * @param rulesUrl
	 * @param dslFileUrl
	 * @param ruleFlowUrl
	 * @return
	 * @throws IOException
	 * @throws DroolsParserException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public org.drools.KnowledgeBase loadRules(RuleConfig ruleSource)
			throws DroolsParserException, IOException, ClassNotFoundException {

		// Use cached rules if possible
		if (ruleSource.getKnowledgeBaseLocation() != null) {

			log.debug("diverting to use cached Knowledgebase: ruleSource.getKnowledgeBaseLocation() ");
			return loadKnowledgeBase(ruleSource);

		}

		log.info("Incoming DSL file:"+ruleSource.getDslFileLocation());
		
		// Load the rules
		KnowledgeBuilder localBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

		// load the DSL file if we have it
		addDslFileToKnowledgeBuilder(ruleSource.getDslFileLocation(), localBuilder);

		// Loop and load rules files
		for (String ruleFile : ruleSource.getRulesLocation()) {
			log.debug("Loading file: " + ruleFile);

			// Check the type of rule file, then load it
			if (ruleFile.toLowerCase().endsWith(XLS_FILE_EXTENSION)) {

				log.debug("Loading Excel rules file: " + ruleFile);
				addExcelRulesToKnowledgeBuilder(ruleFile, localBuilder);
			} else {

				log.debug("Loading Drl / DSR file: " + ruleFile);
				addRuleToKnowledgeBuilder(ruleFile, localBuilder);
			}

		}

		// Add the Ruleflow file if we have it
		addRuleFlowToKnowledgeBuilder(ruleSource.getRuleFlowFileUrl(), localBuilder);

		// check that there are no errors
		if (localBuilder.hasErrors()) {

			log.warn("Drools Errors\n");
			KnowledgeBuilderErrors errors = localBuilder.getErrors();
			Iterator<KnowledgeBuilderError> itErrors = errors.iterator();
			int[] errorLines;
			StringBuffer errorLineMessage;
			while (itErrors.hasNext()) {
				KnowledgeBuilderError thisError = itErrors.next();
				log.warn(thisError.getMessage() + "\n");
				log.warn(thisError.toString() + "\n");
				errorLines = thisError.getLines();
				errorLineMessage = new StringBuffer();
				if (errorLines != null) {
					for (int errorLine : errorLines) {
						errorLineMessage.append(errorLine);
						errorLineMessage.append(",\n");
					}
					log.warn("Error Lines:" + errorLineMessage.toString());
				}

			}

			log.warn("****/nDrools Errors:" + localBuilder.getErrors().toString());
			log.warn("****/nEnd Drools Errors");

			throw new RuntimeException("Check your Rules files (.drl, .dsl, .dslr) for Errors. Details:"
					+ localBuilder.getErrors().toString());

		} else {
			log.debug("No Drools Errors");
		}

		// Print out what we know of the built packages
		logKnowledgePackages(localBuilder);

		// Use these
		log.debug("Creating new knowledgebase");
		org.drools.KnowledgeBase localBase = org.drools.KnowledgeBaseFactory.newKnowledgeBase();

		log.debug("Adding packages to localBase");
		localBase.addKnowledgePackages(localBuilder.getKnowledgePackages());

		return localBase;
	}

	/**
	 * Print out what we know of the built packages
	 *
	 * @param localBuilder
	 */
	void logKnowledgePackages(KnowledgeBuilder localBuilder) {
		Collection<KnowledgePackage> kpCollection = localBuilder.getKnowledgePackages();
		log.debug("Number of packages" + kpCollection.size());

		// Loop through and log
		Iterator<KnowledgePackage> kpIterator = kpCollection.iterator();
		while (kpIterator.hasNext()) {
			log.debug(kpIterator.next().toString());
		}

	}
}
