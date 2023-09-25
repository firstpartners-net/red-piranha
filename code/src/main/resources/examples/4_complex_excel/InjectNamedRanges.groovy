/* This is a Groovy Script called by Red Pirnha <b>after</b> the Excel Spreadsheet 
is loaded into memory, and before the values are inserted as facts into the Rule Engine.

While we can manipulate the Excel data in any way we want, for this sample we use the script
to identify and inject <b>Named Ranges</b> into the Excel document.

This is important as only cells that are part of Excel Named ranges will be added to the 
Rule Engine working memory, and we can't always guarantee that our incoming Excel files
will have them set*/

//the next section allows this script to interact with the Workbook
import org.apache.poi.ss.usermodel.Workbook

println 'Groovy world outside!'
println xlWorkbook==null


def preProcess(xlWorkbook, arg2) {
    println 'Groovy world!'+arg2
}

// the final value in our script will be returned to Red Piranha
"hello:"+arg2



    