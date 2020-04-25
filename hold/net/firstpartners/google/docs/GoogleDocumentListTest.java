/* Based partly on Code sample Copyright (c) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package net.firstpartners.google.docs;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import org.apache.log4j.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gdata.data.acl.AclEntry;
import com.google.gdata.data.acl.AclRole;
import com.google.gdata.data.acl.AclScope;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class GoogleDocumentListTest {

	// The Google Username we use for testing
	public static final String GOOGLE_USERNAME = "paul.m.browne";

	// The Google host we use for testing
	public static final String GOOGLE_HOST = "docs.google.com";

	// The Application name
	// The Google host we use for testing
	public static final String APPLICATION_NAME = "Red-Piranha-Junit-Test v0.1";

	// Name of the file that we upload as a test
	private static final String SAMPLE_EXCEL_TO_UPLOAD_PATH="test/net/firstpartners/google/docs/SampleXlDocumentToUpload.xls";
	private static final String SAMPLE_EXCEL_TO_UPLOAD_NAME="SampleXlDocumentToUpload";

	private static final String SAMPLE_KB_TO_UPLOAD_PATH="test/net/firstpartners/google/docs/SampleKbDocumentToUpload.kb";
	private static final String SAMPLE_KB_TO_UPLOAD_NAME="SampleKbDocumentToUpload";


	// Handle to logger
	private static final Logger log = Logger.getLogger(GoogleDocumentListTest.class
			.getName());

	// Handle to the class under test
	static GoogleDocumentList demo;


	//Handle to the document we uploaded
	private static String uploadedExcelDocumentID=null;
	private static String uploadedKbDocumentID=null;

	@BeforeClass
	public static void preTestSetup() throws DocumentListException,
	AuthenticationException {

		System.out.println("Please enter the google password for:"
				+ GOOGLE_USERNAME
				+ " (or edit DocumentListTest.java to use your own username)");
		Scanner scanner = new Scanner(System.in);
		String password = scanner.nextLine();

		demo = new GoogleDocumentList(APPLICATION_NAME, GOOGLE_HOST);
		demo.login(GOOGLE_USERNAME, password);

		//turnOnLogging();
	}

	private static void turnOnLogging() {
		// Configure the logging mechanisms
		Logger httpLogger = Logger
		.getLogger("com.google.gdata.client.http.HttpGDataRequest");
		httpLogger.setLevel(Level.ALL);
		Logger xmlLogger = Logger.getLogger("com.google.gdata.util.XmlParser");
		xmlLogger.setLevel(Level.ALL);

		// Create a log handler which prints all log events to the console
		ConsoleHandler logHandler = new ConsoleHandler();
		logHandler.setLevel(Level.ALL);
		httpLogger.addHandler(logHandler);
		xmlLogger.addHandler(logHandler);
	}



	@Test
	public void testDocumentUploadExcel() throws IOException, ServiceException, DocumentListException {

		//check that previous upload test has run
		if (uploadedExcelDocumentID!=null){
			fail("Document Upload Test should be the first to run");
		}

		DocumentListEntry entry = demo.uploadFile(SAMPLE_EXCEL_TO_UPLOAD_PATH, SAMPLE_EXCEL_TO_UPLOAD_NAME);
		assertNotNull("Google Docs should return an ID for the entry ",entry);
		demo.printDocumentEntry(entry);
		assertNotNull("Google Docs should return a resourceID as part of the entry",entry.getResourceId());


		//Save the resource ID for later
		uploadedExcelDocumentID = entry.getResourceId();
		log.info("setting uploadedExcelDocumentID to:"+uploadedExcelDocumentID);


	}

	@Test
	public void testDocumentUploadAsKnowledgeBase() throws IOException, ServiceException, DocumentListException  {
		//check that previous upload test has run
		if (uploadedKbDocumentID!=null){
			fail("Document Upload Test should be the first to run");
		}

		DocumentListEntry entry = demo.uploadFile(SAMPLE_KB_TO_UPLOAD_PATH, SAMPLE_KB_TO_UPLOAD_NAME);
		assertNotNull("Google Docs should return an ID for the entry ",entry);
		demo.printDocumentEntry(entry);
		assertNotNull("Google Docs should return a resourceID as part of the entry",entry.getResourceId());

		uploadedKbDocumentID = entry.getResourceId();
		log.info("setting uploadedKbDocumentID to:"+uploadedKbDocumentID);

	}


	@Test
	public void testDocumentList() throws MalformedURLException, IOException,
	ServiceException, DocumentListException {

		//check that previous upload test has run
		if (uploadedExcelDocumentID==null){
			fail("Document Upload Test should run first");
		}

		//Do the call to get the list of the feeds
		DocumentListFeed feed = demo.getDocsListFeed("all");
		assertNotNull("List of Documents returned by Google docs should not be empty",feed);
		assertTrue("DocumentListFeed should have at least one entry (previously uploaded document)",feed.getEntries().size()>0);

		if (feed != null) {

			for (DocumentListEntry entry : feed.getEntries()) {
				demo.printDocumentEntry(entry);
			}
		}


	}


	@Test
	public void testDocumentChangePermissions() throws MalformedURLException, IOException, ServiceException, DocumentListException {

		//check that previous upload test has run
		if (uploadedExcelDocumentID==null){
			fail("Document Upload Test should run first");
		}

		//This request makes our previously uploaded document public
		AclScope scope = new AclScope(AclScope.Type.DOMAIN,uploadedExcelDocumentID);

		AclEntry aclEntry = demo.addAclRole(AclRole.READER, scope,  uploadedExcelDocumentID);
		assertNotNull("aclEntry should not be null",aclEntry);

	}

	@Test
	public void testDocumentDownloadExcel() throws IOException{

		//check that previous upload test has run
		if (uploadedExcelDocumentID==null){
			fail("Document Upload Test should run first");
		}

		String downloadLink = GoogleDownloadLinkGenerator.generateXlDownloadLink(uploadedExcelDocumentID);

		URL url = new URL(downloadLink);
		URLConnection urlConnection = url.openConnection();
		urlConnection.connect();
		InputStream input = url.openStream();
		FileWriter fw = new FileWriter("tmpOutput.kb");


		Reader reader = new InputStreamReader(input);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String strLine = "";

		int count = 0;
		while(count < 10000)
		{
			strLine = bufferedReader.readLine();
			if(strLine != null && strLine != "")
			{

				fw.write(strLine);
			}
			count++;
		}
	}

	@Test
	public void testDocumentDownloadKnowledgeBase() throws IOException {

		//check that previous upload test has run
		if (uploadedKbDocumentID==null){
			fail("Document Upload Test should run first");
		}

		String downloadLink = GoogleDownloadLinkGenerator.generateTextDownloadLink(uploadedKbDocumentID);

		URL url = new URL(downloadLink);
		URLConnection urlConnection = url.openConnection();
		urlConnection.connect();
		InputStream input = url.openStream();
		FileWriter fw = new FileWriter("tmpOutput.kb");


		Reader reader = new InputStreamReader(input);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String strLine = "";

		int count = 0;
		while(count < 10000)
		{
			strLine = bufferedReader.readLine();
			if(strLine != null && strLine != "")
			{

				fw.write(strLine);
			}
			count++;
		}

	}

	@Test
	public void testDocumentDelete() throws MalformedURLException, IOException, ServiceException, DocumentListException {

		//check that previous upload test has run
		if (uploadedExcelDocumentID==null||uploadedKbDocumentID==null){
			fail("Document Upload Test should run first");
		}

		//Just check that we can delete objects
		demo.trashObject(uploadedExcelDocumentID,true);
		demo.trashObject(uploadedKbDocumentID,true);


	}



}
