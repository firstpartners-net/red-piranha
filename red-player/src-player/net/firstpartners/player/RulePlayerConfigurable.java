package net.firstpartners.player;

/**
 * Interface to allow us to load / test/ configure the RulePlayer
 * 
 * @author paul
 * 
 */
public interface RulePlayerConfigurable {

	public String getSourceRuleFile();

	public void setSourceRuleFile(String ruleFile);

	public String getGoogleUserName();

	public void setGoogleUserName(String googleUser);
	
	public String getGooglePassword();

	public void setGooglePassword(String password);

	public String getProxyHost();

	public void setProxyHost(String proxyHost);

	public String getProxyPort();

	public void setProxyPort(String proxyPort);

	public String getKbFileName();

	public void setKbFileName(String kbFileName);

	public String getApplicationName();

	public void setApplicationName(String applicationName);

	public void setGoogleHost(String googleHost);

	public String getGoogleHost();

}
