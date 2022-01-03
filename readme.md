# Red Piranha

![Red Piranha Logo](/site/images/top/02.gif)

## More powerful Excel, Word and Office Documents using Business Rules

Excel is great for collecting data. But once things get more complicated, you may be asking "what next"?

* Your Excel calculations are good. Maybe too good - now you can't follow what is going on. Wouldn't something a little bit clearer be better?
* Are you sharing your spreadsheets? What if somebody changes a formula without you noticing?
* Your business absolutely needs to be sure the rules are being applied - and you need a way of checking what those rules are later.

Powered by [RedHat Drools](http://www.jboss.org/drools), Red-Piranha allows you to state your Excel business rules in a simple **when ... then** format that everybody can understand.  It acts as a bridge between Excel / Microsoft Office and Business Rules and workflow Engine (including AI and Planner) from Red Hat.

_TODO Diagram Showing how RP maps Excel to Business Rule Engine_

* _Spreadsheet + Business Rules = Result_

Instead of having your knowledge 'disappear' into complicated formulas that you can't read, a Rules Engine keeps things in a simple 'English' like format. Even better, because of the way it works 'under the covers' it is faster and more powerful than code translated into a technical language.

## What is a Business Rule

A business rule is a simple statement that represents some important aspect of a business. By capturing the rules for your business--the logic that governs its operation-- you gain the ability to create systems fully aligned with your business needs.

All people have business rules. Examples of a 'real' busniess rules that can be fed into the engine are:

* `When the market rises by more than 25%, Then sell the stock`
* `When the total is less than €100 And the claimant is low risk Then transfer the funds` `

Because business rules can be written in 'near English' language they are much more meaningful to non-technical domain experts. In this format, the knowledge they contain is easier to manage, maintain and share. They also carry the advantage of separating the meaning of the business from the actual technical implementation.

## 3 Minute Quickstart

The information in this document is aimed at getting Excel Power Users up and running with Red Piranha. If you're a developer, or want to dive 'under the covers' [more detailed info is here](code/readme-dev.md).

_TODO Docker_

* Explain what docker is 
Docker setup
* Starting Docker image(s)
* Red Piranha
* Business Centraal (?)
* Screenshot of webpage

## Running the Examples
### How does it work

In General, to Use Red Piranha you:

  1. Tell it where the Spreadsheet is, containing all your information, numbers and other data.
  1. Tell it there Business Rules are that you want to apply to this spreadsheet.
  1. Run the Rules
  1. View the Result

### View the included examples

_TODO Complete Section_


## Writing your own Rules

_TODO Update Section_
* _Editing using Business Central_
* _Editing Manually_
* _Editing using IDE_


  1. Edit rules online - e.g. using Google Docs, or host them anywhere on the web (e.g. Google Sites, file on webserver _TODO instructions for these_
  1. Make the rules publicly available (so Red-Piranha can find them)
  1. _TODO Helps if links to red-piranha project_
  1. _TODO Suggest copy existing template_

## Getting the Rule Player

_TODO Complete Section using Docker_

There are three options for running the Rule Player. More information on each is provided in the next sections.

## Running the Rule Player

_TODO Complete Section_

For the moment , take a look at the DevDeveloperGettingStarted to see how to run the Rule Player through eclipse

  1. Screenshot; Should start automatically
  1. Run again if press the big green button
  1. Should see the following txt

## Using your New Rules

_TODO Complete Section_

* By default rules are shared (explain how)
* Link to rules directory
* Highlight differences in running rules from [UserEndUserGettingStarted](UserEndUserGettingStarted.md)

## Sharing and the Community

_TODO Tie this doc to the community_

* How do I get help
* Where do I find more examples?
* Where can I show off my example?
* Auto submit of successful deploys
* why your email is added to email list (and how to remove?)
* Gallery

# Frequently Asked Questions

_TODO Explain what business rules are_

# Red Piranha Examples

Jump to

* [Example]()
* [Setup]()

 Red-Piranha is a set of Power tools for Word, Excel and Office Power users.

![Red Piranha Splash Screen](https://paulbrowne-irl.github.io/red-piranha/images/splash.png)

It packages the Drools Business Rules Management System (BRMS) solution from RedHat (IBM) to be more accessible to Windows Desktop users. In particular, it makes it easier to feed information from Excel and Word (Office) Documents, apply business rules to these documents, and get useful results.

Most importantly, because your business rules are in an 'English like' language, it makes it very easy for you (and colleagues) to see what checks are being made

* Word Document + Business Rules = Result

Why would we want to do this?

* **Excel Formulas** can get too complicated. Rules are more simple.
* Same for **Excel Macros** - and they have security concerns. You can tightly control the rules.
* Google Spreadsheets are like Excel online, but don't have all the power. Rules add them back.
* You control the rules. Who changes the Excel file when you're not looking?

#

### Rules

### Running the Example

* Running

* Does not overwrite
* Batch
* Config
* Logs
* Screens
* Step through what it is doing

* Reads from Word ..
* Applies rules
* Appends to CSV

* Useful Tools

* Drools / Eclipse
* GitHub source and pages
* Visual studio drl

* GUI Notes

* Drill down , view cell, can copy paste into rules (and remove filters)

* DRL Notes

* Case senstive

* CSV Notes

* Case Sensitive headings
* Case senstive file name in config
* Doesn't like it when file is left open
* Notion of templating

### Screens - details

### Logs - More info on what is going on

Log then modified - simple sample of where modified

* Launch4j.log
* Red-piranha.log

Only once

Where to find output when done

Make sure to close output

How to log to console

## Extending the example

### Investigating Data

* from the Screens
* Using the JSON Output

### Other config

GUI Mode

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

### Writing your first rule

Writing your first rule

* Refer to install notes
* Start with existing Batch
* Update config file

* Input and output ; close to what you want as possible
* Data Model is same, but there may be small differneces

* First run

* What you should see (work through tabs)
* Possible exceptions

* Esp around

#### More rules #

* Refer to showcase (of every possible rule)

* Using drl
* Without using drl

Checks for values

Using offsets

Cell Object (and javadoc on it)

Iterative development

Link to sample rules catalog

## Older Book - Beginning Business rules with JBoss Drools

[![](http://rcm-images.amazon.com/images/I/511yB7Fl-SL._SL110_.jpg)](http://www.amazon.co.uk/dp/1847196063?tag=firstparnet-21&camp=1406&creative=6394&linkCode=as1&creativeASIN=1847196063&adid=0559JR8EAMSMXZ5S3ZWC&)

You may be here looking looking for the code samples from the book **Beginning Business rules with JBoss Drools**. They're [available to download here](http://code.google.com/p/red-piranha/downloads/list?can=2&q=label%3Adroolsbook&colspec=Filename+Summary+Uploaded+ReleaseDate+Size+DownloadCount). If you've any technical issues with the code [check the existing or create a new issue here](http://code.google.com/p/red-piranha/issues/list?can=1&q=&colspec=ID+Type+Status+Priority+Milestone+Owner+Summary&cells=tiles) - remember to add a label ['Book'](http://code.google.com/p/red-piranha/issues/list?can=2&q=book&colspec=ID+Type+Status+Priority+Milestone+Owner+Labels+Summary&x=priority&y=owner&cells=tiles). A general 'comments on the book' page can also be found here [BookFeedback](BookFeedback.md)

We have snapshotted the [code that the book was written on here](https://github.com/paulbrowne-irl/red-piranha/tree/v0.9). You probably don't want this, the libraries this code is based on are now deprecated. The code in this site is much more up to date.

Images from [here under GPL](http://www.iconarchive.com/show/glaze-icons-by-mart/spreadsheet-icon.html)
