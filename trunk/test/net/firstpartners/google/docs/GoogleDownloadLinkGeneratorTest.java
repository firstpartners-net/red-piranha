package net.firstpartners.google.docs;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import org.junit.Test;

public class GoogleDownloadLinkGeneratorTest {

	// Handle to logger
	private static final Logger log = Logger.getLogger(GoogleDownloadLinkGeneratorTest.class
			.getName());

	@Test
	public final void testGenerateGoogleXlDownloadLink() {

		String xlResourceId = "tL4ZXxrtEeInpERn0XHEsmw.04732767912870830876.8581956527660014973";
		String expectedLink = "https://spreadsheets.google.com/fm?id=tL4ZXxrtEeInpERn0XHEsmw.04732767912870830876.8581956527660014973&hl=en&fmcmd=4";

		log.info("Expected:"+expectedLink);
		log.info("Actual:  "+GoogleDownloadLinkGenerator.generateXlDownloadLink(xlResourceId));

		assertEquals(expectedLink,
				GoogleDownloadLinkGenerator
				.generateXlDownloadLink(xlResourceId));

	}

	@Test
	public final void testGenerateGoogleTextlDownloadLink() {

		String txtResourceId = "1bbIBAqwUPOs1XvCsS6Lb92A_T38p1aUQUqyIpaJxYLQ";
		String expectedLink = "https://docs.google.com/document/d/1bbIBAqwUPOs1XvCsS6Lb92A_T38p1aUQUqyIpaJxYLQ/export?format=txt&id=1bbIBAqwUPOs1XvCsS6Lb92A_T38p1aUQUqyIpaJxYLQ&tfe=yn_134";
		assertEquals(expectedLink,
				GoogleDownloadLinkGenerator
				.generateTextDownloadLink(txtResourceId));

	}

}
