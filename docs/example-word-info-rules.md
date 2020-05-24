# Red Piranha Examples

General Statement on what Red Piranha does

This section shows you how to apply Business Rules to your spreadsheets.

  * Spreadsheet + Business Rules = Result

Why would we want to do this?

  * **Excel Formulas** can get too complicated. Rules are more simple.
  * Same for **Excel Macros** - and they have security concerns. You can tightly control the rules.
  * Google Spreadsheets are like Excel online, but don't have all the power. Rules add them back.
  * You control the rules. Who changes the Excel file when you're not looking?
 
## Business Problem ##
Statement: 


## What this sample does ##
High level 

 1. Walk through input file
 2. Walk through running RP - 1 screenshot
 3. Walk through output

## More detail on how it does it ##


### Setup ###
Text
Where get from
Pre-reqs

-   How to check you have Java

-   Mininum Version
-   Picture of command window

-   Test:
- First time run
- 

(take from notes)

### Configuration ###
Walk through

> Example of config file

### Rules ###

### Running the Example ###
-   Running

-   Does not overwrite
-   Batch
-   Config
-   Logs
-   Screens
-   Step through what it is doing

-   Reads from Word ..
-   Applies rules
-   Appends to CSV

-   Useful Tools

-   Drools / Eclipse
-   GitHub source and pages
-   Visual studio drl

-   GUI Notes

-   Drill down , view cell, can copy paste into rules (and remove filters)

-   DRL Notes

-   Case senstive

-   CSV Notes

-   Case Sensitive headings
-   Case senstive file name in config
-   Doesn't like it when file is left open
-   Notion of templating

### Screens - details ###

## Extending the example ##

### Investigating Data ##
* from the Screens
* Using the JSON Output

### Other config ###
GUI Mode

### Dev Setup ###
* Eclipse
* VSCode
* Java install

Eclipse download

Jboss / Drools tools

For tasks

[http://download.eclipse.org/egit/github/updates-nightly/](http://download.eclipse.org/egit/github/updates-nightly/)

[https://wiki.eclipse.org/EGit/GitHub/User_Guide](https://wiki.eclipse.org/EGit/GitHub/User_Guide)

Github

### More on Document Conversion ###

-   How to set as input file
-   Word (docx) and not doc - not yet impmeneted / how to covnert
-   Why we map everything to a cell
-   Viewing the data in the view
-   And how to use this to write data gainst it
- Link to Javadoc

INPUT

-   XLS/XLSX
-   WORD/WORDX

BASIC RP MODEL

RangeList

0 more Ranges

0 or more Cells

CONVERTOR MODEL - word

Document

-   Para

-   Text

RangeList 1 per Doc

-   Range 1 per Para

-   Cell 1 per Para

Cell Value Gives Text

Sample Rule to Access

???

Document

-   Tables

-   Rows

-   Cells in Table

RangeList 1 per Doc

[all rows in tables added] - but using

Table 1 Row 1 Convention

-   Range = one row on table

-   Cell = 1 cell per cell on table

Name is the fist row

Names - based on …

-   First row
-   thisCell.setOriginalTableRefernece("TABLE_"+tableCounter);
-   thisCell.setOriginalCellReference("R:"+rowCounter+" C:"+cellCounter);

Update: Paragraph / Cells - new doc?

Other ntoes

-   Filters to Ascii sets
-   Only table cells with a value get in (so can't guarantee all cells from table are present in working memory

Sample Doc: Before (picture of word)

Sample Doc after (from viewr)

Only document in and out …

### Explaining the DRL ###
Note \\ needed in java when specifiying directories

In translator.dsl

-   Adding #/result will debug the generated rules to the log

### Writing your first rule ###

Writing your first rule

-   Refer to install notes
-   Start with existing Batch
-   Update config file

-   Input and output ; close to what you want as possible
-   Data Model is same, but there may be small differneces

-   First run

-   What you should see (work through tabs)
-   Possible exceptions

-   Esp around

## What Next ##


<!--stackedit_data:
eyJoaXN0b3J5IjpbLTIxMjA4NDE2MjEsLTc1Mjk0MzU5OCwtOT
E0MzkwNjc2LC0zMTEwNjkzMTNdfQ==
-->