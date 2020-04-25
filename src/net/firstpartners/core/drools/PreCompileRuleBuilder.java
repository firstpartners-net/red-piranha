package net.firstpartners.core.drools;

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

import net.firstpartners.core.drools.config.RuleSource;
import net.firstpartners.core.log.ILogger;
import net.firstpartners.core.log.RpLogger;



/**
 * Pre compile rules, build into Drools Knowledge Packages.
 *
 *
 * @author paul
 *
 */
public class PreCompileRuleBuilder {

	private static final Logger log = RpLogger.getLogger(PreCompileRuleBuilder.class
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
		org.drools.core.common.DroolsObjectOutputStream outBytes = new org.drools.core.common.DroolsObjectOutputStream(bytes);
		outBytes.writeObject( kbToCache );

		Base64 base64Code = new Base64();
		byte[] base64Bytes = base64Code.encode(bytes.toByteArray());

		FileOutputStream out = new FileOutputStream( cacheResourceUnderName );
		out.write(base64Bytes);
		out.close();

	}

	
	@SuppressWarnings("rawtypes")
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
	@SuppressWarnings("unchecked")
	public static void main(Object[] args) throws Exception {

		PreCompileRuleBuilder ruleBuilder = new PreCompileRuleBuilder();
		
		Map<String, ?> fileList = ruleBuilder.readProperties();

		Set<String> keys = fileList.keySet();
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

	/**
	 * Compile the rules using the values that we have been passed
	 * 
	 * @param gui
	 * @param ruleFile
	 */
	public void compileRules(ILogger gui, String ruleFile) {

		if (ruleFile == null) {
			gui.info("Please set 'rule' as a param pointing at the rule file you wish to compile ");
		} else {
			gui.info("Compiling Rules...\n");
			gui.info("Using file:" + ruleFile + "\n");
			PreCompileRuleBuilder rulebuilder = new PreCompileRuleBuilder();

			try {
				rulebuilder.cacheRule(ruleFile, (String) null);
			} catch (DroolsParserException ex) {
				gui.exception("DroolsParserException:" + ex + "\n", ex);
			} catch (IOException ex) {
				gui.exception("IOException:" + ex + "\n", ex);
			} catch (ClassNotFoundException ex) {
				gui.exception("ClassNotFoundException:" + ex + "\n", ex);
			}

			gui.info("Compiling complete\n");
		}

	}
	
}
