package net.firstpartners.ui;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.firstpartners.RedConstants;
import net.firstpartners.drools.SpreadSheetRuleRunner;
import net.firstpartners.drools.URLRuleLoader;
import net.firstpartners.drools.data.RuleSource;
import net.firstpartners.security.RedSecurityManager;
import net.firstpartners.spreadsheet.SpreadSheetOutputter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class Rp2Servlet extends HttpServlet {

	private static final String PARAM_EXCEL_DATA_FILE = "excelDataFile";

	private static final String PARAM_KNOWLEDGE_BASE = "knowledgeBase";

	private static final Logger log = Logger.getLogger(Rp2Servlet.class
			.getName());



	//Handle to common utility file
	private final  SpreadSheetRuleRunner commonSpreadsheetUtils = new SpreadSheetRuleRunner(new URLRuleLoader());



	/**
	 * Standard Servlet Entry Point
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp)
	throws IOException, ServletException {


		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		//	logEnvironment();

		String knowledgeBaseFile = req.getParameter(PARAM_KNOWLEDGE_BASE);
		String excelFile = req.getParameter(PARAM_EXCEL_DATA_FILE);


		//Identify where the rules are stored
		RuleSource ruleSource = new RuleSource();
		ruleSource.setKnowledgeBaseLocation(knowledgeBaseFile);
		log.info("Using knowledgeBaseFile"+knowledgeBaseFile);

		if ((user != null)&&(knowledgeBaseFile!=null)&&!knowledgeBaseFile.equals("")&&(excelFile!=null)&&!excelFile.equals("")) {

			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
			"attachment; filename=result.xls");

			//Check the RUL
			RedSecurityManager.checkUrl(knowledgeBaseFile);
			RedSecurityManager.checkUrl(excelFile);

			log.info("Using Excel File"+excelFile);


			URL url = new URL(excelFile);

			HSSFWorkbook wb;
			try {
				wb = commonSpreadsheetUtils.callRules(url,ruleSource, RedConstants.EXCEL_LOG_WORKSHEET_NAME);




				SpreadSheetOutputter.outputToStream(wb, resp.getOutputStream());
			} catch (Exception e) {

				log.warning(e.getMessage());
				throw new ServletException(e);
			}

		} else {

			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
		}
	}


	private void logEnvironment(){

		StringBuffer output = new StringBuffer();

		Properties pr = System.getProperties();
		TreeSet propKeys = new TreeSet(pr.keySet());  // TreeSet sorts keys
		for (Iterator it = propKeys.iterator(); it.hasNext(); ) {
			String key = (String)it.next();
			output.append("" + key + "=" + pr.get(key) + "\n");
		}
		log.info(output.toString());


	}


}