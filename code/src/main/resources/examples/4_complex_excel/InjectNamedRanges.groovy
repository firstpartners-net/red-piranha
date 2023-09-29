/* This is a Groovy Script called by Red Pirnha <b>after</b> the Excel Spreadsheet 
is loaded into memory, and before the values are inserted as facts into the Rule Engine.

While we can manipulate the Excel data in any way we want, for this sample we use the script
to identify and inject <b>Named Ranges</b> into the Excel document.

This is important as only cells that are part of Excel Named ranges will be added to the 
Rule Engine working memory, and we can't always guarantee that our incoming Excel files
will have them set

* Red Piranha (RP) will pass in a handle to the Excel workbook - the next line confirms this
* Details on the Workbook model are at the Apache POI website.
* Details of the helper class are available in the samples, and the JavaDoc for SheetNames.java
*/

// start careful change ===================================================================

//the next section imports libs that allow this script to interact with the Workbook
import org.apache.poi.ss.usermodel.Workbook;
import net.firstpartners.core.script.SheetNames;


// check that the script has been called in a way that it expects
assert xlWorkbook!=null                                             
assert xlWorkbook instanceof org.apache.poi.ss.usermodel.Workbook
println "InjectNamedRanges.groovy running"   

// Setup a reference to our helper class - makes this script easier to write
ScriptSupport sprt = new ScriptSupport(xlWorkbook)

// end careful change =====================================================================

// Start setting names - single cells
sprt.nameSingleCell("CompanyName", "Accounts", "A4")

// Start Naming Tables - each cell in table will be named individually using 
//the base value, then a combination of the the header and rows
// from our example XL - the range will *not* include the black header row
// but start immediately below it
sprt.nameTable("Info","Accounts","A12:I30")
sprt.nameTable("PL","Accounts","A34:I75")
sprt.nameTable("BS","Accounts","A81:I124")
sprt.nameTable("Cash","Accounts","A131:I195")


// the final value in our script will be returned to Red Piranha
// do-not-change as it expects an Excel Workbook back
xlWorkbook
// end do-not-change

    