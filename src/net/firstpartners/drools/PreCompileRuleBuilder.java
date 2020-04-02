package net.firstpartners.drools;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.drools.KnowledgeBase;
import org.drools.compiler.compiler.DroolsParserException;

import net.firstpartners.drools.data.RuleSource;



/**
 * Load Rules, build into Drools Knowledge Packages.
 *
 * This package is designed to be run from the command line, not from Google App Engine
 * To pre-build the rules
 *
 * @author paul
 *
 */
public class PreCompileRuleBuilder {

	private static final Logger log = Logger.getLogger(PreCompileRuleBuilder.class
			.getName());




	public static String LIST_OF_DRL_FILES_TO_COMPILE="src/net/firstpartners/drools/PreCompileRuleList.properties";

	/**
	 * Cache the pre built knowledgebase.
	 *
	 * We encode using base64 (instead of Binary) so that it can be treated as a normal text file
	 */
	void cacheKnowledgeBase(KnowledgeBase kbToCache,
			String cacheResourceUnderName) throws IOException {

		ByteArrayOutputStream bytes= new ByteArrayOutputStream();
		org.drools.common.DroolsObjectOutputStream outBytes = new org.drools.common.DroolsObjectOutputStream(bytes);
		outBytes.writeObject( kbToCache );

		Base64 base64Code = new Base64();
		byte[] base64Bytes = base64Code.encode(bytes.toByteArray());

		FileOutputStream out = new FileOutputStream( cacheResourceUnderName );
		out.write(base64Bytes);
		out.close();

	}

	public Map readProperties() throws IOException{

		// Read properties file.
		Properties properties = new Properties();
		properties.load(new FileInputStream(LIST_OF_DRL_FILES_TO_COMPILE));

		return properties;
	}



	/**
	 * Entry points that allows rebuilding all the rule files into packages
	 * @param args - standard Java params passed from command line. Contains name of
	 * directory to start in.
	 *
	 * Method opens the property file, pre compiles the resources listed there
	 * @throws IOException
	 * @throws DroolsParserException
	 */
	public static void main(Object[] args) throws Exception {

		PreCompileRuleBuilder ruleBuilder = new PreCompileRuleBuilder();
		Map fileList = ruleBuilder.readProperties();

		Set keys = fileList.keySet();
		if(keys!=null){
			Iterator<String> it = keys.iterator();
			while (it.hasNext()){
				Object key = it.next();
				Object outputFile = fileList.get(key);

				if((key!=null)&&(outputFile!=null)){
					ruleBuilder.cacheRule(key.toString(),outputFile.toString());
				}

			}
		}


	}

	/**
	 * Load and cache the rule
	 * @param key
	 * @param outputFile
	 * @throws IOException
	 * @throws DroolsParserException
	 * @throws ClassNotFoundException
	 */
	public void cacheRule(String ruleLocation, String outputFile) throws DroolsParserException, IOException, ClassNotFoundException {


		log.info("Loading Knowledgebase from "+ruleLocation);

		//Get a handle to the rule loader that we will use
		IRuleLoader ruleLoader = getRuleLoader(ruleLocation);

		//Setup the source
		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(ruleLocation);


		KnowledgeBase kb= ruleLoader.loadRules(ruleSource);
		if(outputFile!=null){
			cacheKnowledgeBase(kb,outputFile);
			log.info("Cached Knowledgebase to "+outputFile);

		}

	}
	/**
	 *
	 */
	private IRuleLoader  getRuleLoader(String ruleLocation){


		IRuleLoader ruleLoader;

		if(ruleLocation.startsWith("http")){
			ruleLoader = new URLRuleLoader();
		} else {
			ruleLoader = new FileRuleLoader();
		}

		//Default - url rule loader
		return ruleLoader;
	}

}
