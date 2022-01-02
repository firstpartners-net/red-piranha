package net.firstpartners.core.drools;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.drools.KnowledgeBase;
import org.drools.compiler.compiler.DroolsParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.firstpartners.core.drools.loader.IRuleLoaderStrategy;
import net.firstpartners.data.RedModel;

/**
 * Pre compile rules, and save them as Drools Knowledge Packages for later
 * (faster) use)
 *
 * @author paul
 *
 */
public class PreCompileRuleBuilder {


	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * * Cache the pre built knowledgebase.
	 *
	 * We encode using base64 (instead of Binary) so that it can be treated as a
	 * 
	 * @param kbToCache
	 * @param cacheResourceUnderName
	 * @throws IOException
	 */
	void cacheKnowledgeBase(KnowledgeBase kbToCache, String cacheResourceUnderName) throws IOException {

		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		org.drools.core.common.DroolsObjectOutputStream outBytes = new org.drools.core.common.DroolsObjectOutputStream(
				bytes);
		outBytes.writeObject(kbToCache);

		Base64 base64Code = new Base64();
		byte[] base64Bytes = base64Code.encode(bytes.toByteArray());

		FileOutputStream out = new FileOutputStream(cacheResourceUnderName);
		out.write(base64Bytes);
		out.close();

	}

	/**
	 * Load and cache the rule to a file
	 * 
	 * @param key
	 * @param outputFile
	 * @throws IOException
	 * @throws DroolsParserException
	 * @throws ClassNotFoundException
	 */
	public void compileRule(RedModel ruleSource, String outputFile)
			throws DroolsParserException, IOException, ClassNotFoundException {

		log.debug("Loading Knowledgebase from " + ruleSource);

		// Get a handle to the rule loader that we will use
		IRuleLoaderStrategy ruleLoader = RuleRunnerFactory.getRuleLoader(ruleSource);

		KnowledgeBase kb = ruleLoader.loadRules(ruleSource);
		if (outputFile != null) {
			cacheKnowledgeBase(kb, outputFile);
			log.debug("Cached Knowledgebase to " + outputFile);

		}

	}

}
