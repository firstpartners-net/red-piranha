/* This is a Groovy Script called by Red Pirnha <b>after</b> the Excel Spreadsheet 
is loaded into memory, and before the values are inserted as facts into the Rule Engine.

While we can manipulate the Excel data in any way we want, for this sample we use the script
to identify and inject <b>Named Ranges</b> into the Excel document.

This is important as only cells that are part of Excel Named ranges will be added to the 
Rule Engine working memory, and we can't always guarantee that our incoming Excel files
will have them set*/

//the next section imports libs allows this script to interact with the Workbook
//unlikely to need to change
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
// end of imports

//Red Piranha will pass in a handle to the Excel workbook - the next line confirms this
//Details on the Workbook model are at 
assert xlWorkbook!=null

//Loop through the sheets
// for (int i = 0; i < xlWorkbook.getNumberOfSheets(); i++)
// {
//     Sheet sheet = xlWorkbook.getSheetAt(i)
//     println sheet.getSheetName()
//     // Process your sheet here.
// }

//Add a name - this corrposponds to the year headings
XSSFName name2 = xlWorkbook.createName()
name2.setNameName("FYHeadings")
name2.setComment("This name is scoped to Sheet1")
name2.setRefersToFormula("Accounts!B14:I14")




// the final value in our script will be returned to Red Piranha
xlWorkbook


    