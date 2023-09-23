## Running the Examples
### How does it work



In General, to Use Red Piranha you:

  1. Tell it where the Spreadsheet is, containing all your information, numbers and other data.
  1. Tell it there Business Rules are that you want to apply to this spreadsheet.
  1. Run the Rules
  1. View the Result

Where are the logs?

  
_TODO tidy_
* Running
* Does not overwrite
* Config
* Logs
* Screens
* Step through what it is doing
* Reads from Word ..
* Applies rules
* Appends to CSV
* Useful Tools
* GUI Notes
* Drill down , view cell, can copy paste into rules (and remove filters)
* DRL Notes
* Case senstive
* CSV Notes
* Doesn't like it when file is left open
* Notion of templating


## Running the examples fromt eh command line

* Have Spring boot server up and running
* call via url (e.g http://localhost:7000/runRules?inputFileLocation=1_hello_world%2Fhello-excel.xls&ruleFileLocation=1_wello_world%2Fhello.drl&outputFileLocation=1_hello_world%2Foutput.xls )
* make sure the files referecnes are available locally to server