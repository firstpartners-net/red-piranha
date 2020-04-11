<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="net.firstpartners.*" %>


<html>
  <body>

 <form action="/rp2" method="get">
  <div>Excel Data File
  <select name="excelDataFile">
	<option value="http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/chocolate-data.xls">Excel Deployed on App engine http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/chocolate-data.xls</option>
	<option value="https://spreadsheets.google.com/fm?id=tXRbhvtEt9W6s_muzikHHfw.04732767912870830876.8975815832715783135&authkey=CNHB8vUM&hl=en&fmcmd=4">Excel Shared on Google Spreadsheets https://spreadsheets.google.com/fm?id=tXRbhvtEt9W6s_muzikHHfw.04732767912870830876.8975815832715783135&authkey=CNHB8vUM&hl=en&fmcmd=4</option>
	
	</select>
  </div>
  
   <div>Rule Package
  <select name="knowledgeBase">
	<option value="http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/log-then-modify-rules.KnowledgeBase">Deployed within Red Piranha - http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/log-then-modify-rules.KnowledgeBase</option>
	<option value="http://red-piranha.googlecode.com/svn/trunk/war/sampleresources/SpreadSheetServlet/log-then-modify-rules.KnowledgeBase">From Source on Red Piranha http://red-piranha.googlecode.com/svn/trunk/war/sampleresources/SpreadSheetServlet/log-then-modify-rules.KnowledgeBase</option>
	</select>
  </div>
  
 
    <div><input type="submit" value="Run Sample" /></div>
  </form>

  </body>

</html>
