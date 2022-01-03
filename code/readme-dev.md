# Red Piranha - Information for Developers

Red Piranha is aimed at 'Excel Power Users'. The information in this page goes 'under the covers' if you (as a Developer) are looking to understand and extend the project.

What happens on startup

* trys to run using the config in application
* further queries based on rest api

Standard Maven package layout
Standard Srping boot app

entry points ...

Spring end points ....
<https://docs.spring.io/spring-boot/docs/2.6.2/reference/htmlsingle/#actuator.endpoints>

<http://localhost:7000/swagger-ui.html>
<http://localhost:7000/swagger-ui/>

configuration ...

Logging ...

extending ...

## Getting Started With Spring and Maven

* To run as spring boot applicaiton mvn spring-boot:run

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.2/maven-plugin/reference/html/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.6.2/reference/htmlsingle/#using-boot-devtools)

## Project Layout and useful files

* pom.xml
* Spring start point

## Other Project Notes

* Note that running main in TestConstants regenerates the serialized test data

# Red Piranha for Drools Developers

If you've used <http://www.jboss.org/drools> JBoss Drools, you'll be pretty familiar with most of the concepts. This sections explain what Red-Piranha does (and does not) do compared to JBoss Drools.

Most business users are more comfortable with Excel and the Web than with Java. Red-Piranha packages Java based Drools so that they can start writing and using business rules straight away. Red-Piranha allows them to put your rules and data in Excel, then call Drools in a simple package to do the hard work.

* Red Piranha uses <http://www.jboss.org/drools> as a key library. It's focus is more on Excel and Google Docs users looking to add the power of a rule engine to their documents.
* Google App Engine (GAE) is a key deployment target, and enforces key limitations on the code; For examples rules cannot be 'compiled' in the normal Drools way, hence the offline package step, before you deploy.
* With Drools, you normally supply (1) The rules file (2) A Model (or data structure) holding the information that the rules act on (3) Java code to tie it all together.
* With Red-Piranha all you need is 1) the rules file. The Data structure is always excel, and there is standard Java code to combine the Rules and the excel spreadsheet. This makes it easier, although Drools gives you more flexibility (with a bit more work!).

# Find Home For ##

Explain for example

* Prerequest
* Files in directory
* Console / logs

* Where
* Contents

* More details

* Input file
* Rules applied
* Output file

What objects are


## More detail on how it does it ##

### Setup ###

Text
Where get from
Pre-reqs

* How to check you have Java

* Mininum Version
* Picture of command window
* Java Admin rights

* Test:
* First time run

-

(take from notes)

### Where to get the sample from ###

* Link to project
 
### Files in Directory ###

* Walk through

### Configuration ###

Walk through

> Example of config file

## Other notes to include

Datamodel

* Why Excle and Word maps to cells
* Java

* Rangeholder
* Range
* Cell

* Methods on Cell

* Dev

* Extending document strategies
* List the ones available

Example rules of how to call each type of info available

* Update Site : readup on jeckyll

* Eclipse tools (Jboss / market place etc)

* document properties file

* config in exe

* logging in exe

* Main Classes: RedCommandLine,

* Main Unit Tests

Main aim: desktop running of rules

Datamodel

* How to view
* Why we call a word-document a 'cell'
* Sample Word & XL &

* what this looks like in Java (and RP
* How to call it from a rule

### Key Technical Documents and Classes ###

* Main Javadoc <https://paulbrowne-irl.github.io/red-piranha/javadocs/>
* Cell - <https://paulbrowne-irl.github.io/red-piranha/javadocs/net/firstpartners/data/Cell.html>
* Range  - <https://paulbrowne-irl.github.io/red-piranha/javadocs/net/firstpartners/data/Range.html>
* RangeList - <https://paulbrowne-irl.github.io/red-piranha/javadocs/net/firstpartners/data/RangeList.html>
* Convertors
** Excel Input Strategy <https://paulbrowne-irl.github.io/red-piranha/javadocs/net/firstpartners/core/excel/ExcelInputStrategy.html>
** Excel Output Strategy <https://paulbrowne-irl.github.io/red-piranha/javadocs/net/firstpartners/core/excel/ExcelOutputStrategy.html>
** CSV Output Strategy <https://paulbrowne-irl.github.io/red-piranha/javadocs/net/firstpartners/core/file/CSVOutputStrategy.html>
** JSON Output Strategy <https://paulbrowne-irl.github.io/red-piranha/javadocs/net/firstpartners/core/file/JsonOutputStrategy.html>
** WordInput Strategy <https://paulbrowne-irl.github.io/red-piranha/javadocs/net/firstpartners/core/word/WordInputStrategy.html>
** Word (newer version) Input Strategy
<https://paulbrowne-irl.github.io/red-piranha/javadocs/net/firstpartners/core/word/WordXInputStrategy.html>


### Dev Setup

* Eclipse
* VSCode
* Java install

Eclipse download

Jboss / Drools tools

For tasks

[http://download.eclipse.org/egit/github/updates-nightly/](http://download.eclipse.org/egit/github/updates-nightly/)

[https://wiki.eclipse.org/EGit/GitHub/User_Guide](https://wiki.eclipse.org/EGit/GitHub/User_Guide)

Github

### More on Document Conversion

* How to set as input file
* Word (docx) and not doc - not yet impmeneted / how to covnert
* Why we map everything to a cell
* Viewing the data in the view
* And how to use this to write data gainst it
* Link to Javadoc

INPUT

* XLS/XLSX
* WORD/WORDX

BASIC RP MODEL

RangeList

0 more Ranges

0 or more Cells

CONVERTOR MODEL - word

Document

* Para

* Text

RangeList 1 per Doc

* Range 1 per Para

* Cell 1 per Para

Cell Value Gives Text

Sample Rule to Access

???

Document

* Tables

* Rows

* Cells in Table

RangeList 1 per Doc

[all rows in tables added] - but using

Table 1 Row 1 Convention

* Range = one row on table

* Cell = 1 cell per cell on table

Name is the fist row

Names - based on …

* First row
* thisCell.setOriginalTableRefernece("TABLE_"+tableCounter);
* thisCell.setOriginalCellReference("R:"+rowCounter+" C:"+cellCounter);

Update: Paragraph / Cells - new doc?

Other ntoes

* Filters to Ascii sets
* Only table cells with a value get in (so can't guarantee all cells from table are present in working memory

Sample Doc: Before (picture of word)

Sample Doc after (from viewr)

Only document in and out …

### More on Document Output

Dev Note on how to extend

OUTPUT
Available File formats

* XLS / XLSX in and out
* Word, WordX In
  ○ Wriite rules to detect if format of incomfing doc changes
  ○ "fingerprint" a couple of key points
  ○ If values are repeated multiple times in doc, try to t
  ○ Double check names nearby to
   § e.g. on our doc
   § // cellName=Department Manager Name:_0 , originalTableReference ="TABLE_9"
  ○ Prefer Table values to document, less likely to hcange
  ○ Refer to Table name in rules
  ○
* ?? PDF - very simple
  ○ How to create a template which we can then fill
* ?? CSV Out - very simple
  ○ Tries to append ??
  ○ Ignores original document
  ○ @see net.firstpartners.core.file.CSVOutputStrategy
  ○ Note on how to create a CSV file
* .json
  ○ Use for debugging
  ○ Use alongside GUI
  ○ Go through Cycle of mappign document
* Note about data independent of layout

NTOE IN WRITING RULES
PUT IN STRAGN VLAUES

### Explaining the DRL

Note \\ needed in java when specifiying directories

In translator.dsl

* Adding #/result will debug the generated rules to the log
