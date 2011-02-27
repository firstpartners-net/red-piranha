package net.firstpartners.player;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;


public class SettingsLoaderTest implements RulePlayerConfigurable {

	@Test
	public void testLogLocation(){
		File file=new File(".");
		System.out.println("Current Location:"+file.getAbsolutePath());
	}
	
	
	@Test
	public void testPropertiesLoad(){
		
		SettingsLoader loader = new SettingsLoader();
		loader.load(this);
		
		//Check that we have loaded the appropriate properties
		assertNotNull(this.getRuleFile());
		assertNotNull(this.getGoogleUser());
		
	}
	
	
//Methods to ensure we can use this file as a mock-object
	
	String ruleFile=null;
	String googleUser=null;
	
	@Override
	public String getRuleFile() {
		return ruleFile;
	}

	@Override
	public void setRuleFile(String ruleFile) {
		this.ruleFile = ruleFile;
	}

	@Override
	public String getGoogleUser() {
		return googleUser;
	}
	
	@Override
	public void setGoogleUser(String googleUser) {
		this.googleUser = googleUser;
	}
}
