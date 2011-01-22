package net.firstpartners.drools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import net.firstpartners.drools.data.RuleSource;

import org.drools.KnowledgeBase;
import org.drools.compiler.DroolsParserException;



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


	FileRuleLoader ruleLoader = new FileRuleLoader();

	public static String LIST_OF_DRL_FILES_TO_COMPILE="src/net/firstpartners/drools/PreCompileRuleList.properties";

	/**
	 * Cache the pre built knowledgebase
	 */
	void cacheKnowledgeBase(KnowledgeBase kbToCache,
			String cacheResourceUnderName) throws IOException {

		org.drools.common.DroolsObjectOutputStream out = new org.drools.common.DroolsObjectOutputStream( new FileOutputStream( cacheResourceUnderName ));
		out.writeObject( kbToCache );
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
	void cacheRule(String ruleLocation, String outputFile) throws DroolsParserException, IOException, ClassNotFoundException {


		log.info("Loading Knowledgebase from "+ruleLocation);


		RuleSource ruleSource = new RuleSource();
		ruleSource.setRulesLocation(ruleLocation);


		KnowledgeBase kb= ruleLoader.loadRules(ruleSource);

		cacheKnowledgeBase(kb,outputFile);
		log.info("Cached Knowledgebase to "+outputFile);

	}

}
