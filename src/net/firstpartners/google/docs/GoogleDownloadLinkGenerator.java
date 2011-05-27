package net.firstpartners.google.docs;

/**
 * Given a oAuthID, generate a link to download it as Txt or XL
 * @author paul
 *
 */
public class GoogleDownloadLinkGenerator {

	public static final String XL_FIRST_PART= "https://spreadsheets.google.com/fm?id=";
	public static final String XL_FINAL_PART= "&hl=en&fmcmd=4";

	public static final String TEXT_FIRST_PART="https://docs.google.com/document/d/";
	public static final String TEXT_MID_PART="/export?format=txt&id=";
	public static final String TEXT_LAST_PART="&tfe=yn_134";



	public static String generateXlDownloadLink(String resourceId){
		return XL_FIRST_PART+resourceId+XL_FINAL_PART;
	}

	public static String generateTextDownloadLink(String resourceId){
		return TEXT_FIRST_PART+resourceId+TEXT_MID_PART+resourceId+TEXT_LAST_PART;
	}

}
