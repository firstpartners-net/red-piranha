/**
 *
 */
package net.firstpartners.core.drools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.drools.compiler.compiler.DroolsParserException;
import org.junit.Test;

import net.firstpartners.TestConstants;
import net.firstpartners.core.log.ConsoleLogger;

/**
 * @author paul
 *
 */
public class PreCompileRuleBuilderTest {

	//Class under test
	private final PreCompileRuleBuilder preCompileRuleBuilder = new PreCompileRuleBuilder();

	//Handle to Logger
	private static final Logger log = Logger.getLogger(PreCompileRuleBuilderTest.class
			.getName());


	/**
	 * Test method for {@link net.firstpartners.drools.PreCompileRuleBuilder#main(java.lang.Object[])}.
	 * This has the good side-effect of reloading all the rules
	 * @throws IOException
	 * @throws DroolsParserException
	 */
	@Test
	public final void testMain() throws Exception {

		log.info("Calling example");
		
		clearPreviousPackages();

		//Call the main method with it
		PreCompileRuleBuilder.main(null);

		testForNewPackages();


	}

	private void testForNewPackages() throws IOException {
		@SuppressWarnings("unchecked")
		Map<String, ?> fileList = preCompileRuleBuilder.readProperties();
		File currentFile =null;

		Set<String>keys = fileList.keySet();
		if(keys!=null){
			Iterator<String> it = keys.iterator();
			while (it.hasNext()){
				Object key = it.next();
				Object outputFile = fileList.get(key);
				currentFile = new File(outputFile.toString());
				assertTrue(currentFile.exists());

			}
		}


	}

	private void clearPreviousPackages() throws IOException {


		@SuppressWarnings("unchecked")
		Map<String, ?> fileList = preCompileRuleBuilder.readProperties();
		File currentFile =null;

		Set <String>keys = fileList.keySet();
		if(keys!=null){
			Iterator<String> it = keys.iterator();
			while (it.hasNext()){
				Object inputFile = it.next();
				Object outputFile = fileList.get(inputFile.toString());

				if(outputFile!=null){
					currentFile=new File(outputFile.toString());
					currentFile.delete();
				}

			}
		}

	}

	@Test
	public final void testPropertyRead() throws IOException {

		@SuppressWarnings("unchecked")
		Map<String, ?> preLoadRules = preCompileRuleBuilder.readProperties();
		assertNotNull(preLoadRules);
		assertTrue(!preLoadRules.isEmpty());
		assertTrue(preLoadRules.get("war/sampleresources/SpreadSheetServlet/log-then-modify-rules.drl")!=null);

	}
	
	@Test
	public final void testpreCompileRules() {
		
		
		preCompileRuleBuilder.compileRules(new ConsoleLogger(), TestConstants.RULES_FILES[0]);
		fail("need to implement tests");	
			
			
}

}
