package net.firstpartners.player;

/**
 * Interface to allow us to load / test/ configure the RulePlayer
 * 
 * @author paul
 * 
 */
public interface RulePlayerConfigurable {

	public String getRuleFile();

	public void setRuleFile(String ruleFile);

	public String getGoogleUser();

	public void setGoogleUser(String googleUser);

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
