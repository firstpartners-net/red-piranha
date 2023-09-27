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

// Setup a reference to our helper class - makes script easier to write
SheetNames sn = new SheetNames(xlWorkbook)


// end careful change =====================================================================

// Start setting names
sn.nameSingleCell("testName", "Accounts", "A1:B2")


// the final value in our script will be returned to Red Piranha
// do-not-change as it expects an Excel Workbook back
xlWorkbook
// end do-not-change

    