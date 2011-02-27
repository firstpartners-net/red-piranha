package net.firstpartners.player;

/**
 * Interface to allow us to load / test/ configure the RulePlayer 
 * @author paul
 *
 */
public interface RulePlayerConfigurable {

	public String getRuleFile();
	public void setRuleFile(String ruleFile);


	public String getGoogleUser();
	public void setGoogleUser(String googleUser) ;
	
}
