package net.firstpartners.player;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;


public class SettingsLoaderTest implements RulePlayerConfigurable {

	
	
	@Test
	public void testPropertiesLoad(){
		
		SettingsLoader loader = new SettingsLoader();
		loader.load(this);
		
		//Check that we have loaded the appropriate properties
		assertNotNull(this.getRuleFile());
		assertNotNull(this.getGoogleUser());
		
		//These are null / empty in the default properties file
		assertNull(this.getProxyHost());
		assertNull(this.getProxyPort());
		
		
	}
	
	
//Methods to ensure we can use this file as a mock-object
	
	String ruleFile=null;
	String googleUser=null;
	String proxyHost=null;
	String proxyPort=null;
	
	
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


	@Override
	public String getProxyHost() {
		return proxyHost;
	}


	@Override
	public String getProxyPort() {
		return proxyPort;
	}

	@Override
	public void setProxyHost(String proxyHost) {
		this.proxyHost=proxyHost;
		
	}


	@Override
	public void setProxyPort(String proxyPort) {
		this.proxyPort =proxyPort;
		
	}

	@Override
	public String getKbFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKbFileName(String kbFileName) {
		// TODO Auto-generated method stub
		
	}
}
