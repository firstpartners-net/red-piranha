# Red Piranha

![Red Piranha Logo](/site/images/top/02.gif)

## More powerful Excel, Google Docs and Spreadsheets using JBoss Rules and the Cloud

Powered by [Drools](http://www.jboss.org/drools), Red-Piranha allows you to state your Excel business rules in a simple **when ... then** format that everybody can understand.
Instead of having your knowledge 'disappear' into complicated formulas that you can't read, Drools keeps things in a simple 'English' like format.
Even better, because of the way it works 'under the covers' it is faster and more powerful than code translated into a technical language.

Excel is great for collecting data. But once things get more complicated, are your spreadsheets missing something?

* Your excel calculations are good. Maybe too good - now you can't follow what is going on. Wouldn't something a little bit clearer be better?
* Are you sharing your spreadsheets? What if somebody changes a formula without you noticing?
* Are you looking for a better way to share and publish your knowledge?
* Do you need more power to extend what Excel or Google Spreadsheets already provide?

Even better, it's all open source and free to get started. And it works with Google Spreadsheets as well as Excel.

## What Next?

The information in this document is aimed at getting Excel Power Users up and running with Red Piranha. If you're a developer, or want to dive 'under the covers' [code/readme-dev.md](more detailed info is here).

# Red Piranha - User

![Red Piranha Logo](/site/images/top/02.gif)

## Notes on how to use

* See what Red-Piranha is for
** Diagram explaining bridge to excel
* Docker setup
** Starting Docker image(s)
*** Red Piranha
*** Business Centraal

* Screenshot of webpage

Config update
Running turles - three steps

## Look at the Examples

_todo_

## Older Book - Beginning Business rules with JBoss Drools

[![](http://rcm-images.amazon.com/images/I/511yB7Fl-SL._SL110_.jpg)](http://www.amazon.co.uk/dp/1847196063?tag=firstparnet-21&camp=1406&creative=6394&linkCode=as1&creativeASIN=1847196063&adid=0559JR8EAMSMXZ5S3ZWC&)

You may be here looking looking for the code samples from the book **Beginning Business rules with JBoss Drools**. They're [available to download here](http://code.google.com/p/red-piranha/downloads/list?can=2&q=label%3Adroolsbook&colspec=Filename+Summary+Uploaded+ReleaseDate+Size+DownloadCount). If you've any technical issues with the code [check the existing or create a new issue here](http://code.google.com/p/red-piranha/issues/list?can=1&q=&colspec=ID+Type+Status+Priority+Milestone+Owner+Summary&cells=tiles) - remember to add a label ['Book'](http://code.google.com/p/red-piranha/issues/list?can=2&q=book&colspec=ID+Type+Status+Priority+Milestone+Owner+Labels+Summary&x=priority&y=owner&cells=tiles). A general 'comments on the book' page can also be found here [BookFeedback](BookFeedback.md)

We have snapshotted the [code that the book was written on here](https://github.com/paulbrowne-irl/red-piranha/tree/v0.9). You probably don't want this, the libraries this code is based on are now deprecated. The code in the rest of this site is much more up to date.

Images from [here under GPL](http://www.iconarchive.com/show/glaze-icons-by-mart/spreadsheet-icon.html)

This section shows you how to apply Business Rules to your spreadsheets.

* Spreadsheet + Business Rules = Result

Why would we want to do this?

* **Excel Formulas** can get too complicated. Rules are more simple.
* Same for **Excel Macros** - and they have security concerns. You can tightly control the rules.
* Google Spreadsheets are like Excel online, but don't have all the power. Rules add them back.
* You control the rules. Who changes the Excel file when you're not looking?

## How does it work ##

_TODO Complete Section_

When you're finished this section, you should be able to

  1. Tell Red Piranha where the Spreadsheet is, containing all your information, numbers and other data.
  1. Tell Red Piranha where the [Business Rules](BusinessRules.md) are that you want to apply to this spreadsheet.
  1. Run the Rules
  1. View the Result

|| This sample is a work in progress. So for the moment you can use the existing excel spreadsheets, but not
upload your own. Watch this space.

## What you'll need ##

| Google Account | So that you can sign in (via Google) to run the example |
|:---------------|:--------------------------------------------------------|
| Pointer to Red-Piranha on the Web | Unless you've been told otherwise, use this web link: <http://red-piranha.appspot.com/> |

## Step by Step Guide ##

_TODO complete Section_

* Create your spreadsheet using Google Docs or Excel
  * Make sure it has 'named ranges' - Insert ... Name ... Define
  * Sample is at <http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/chocolate-data.xls>

<a href='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/excel-named-ranges.jpg'>Click to Enlarge<br />
<img src='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/excel-named-ranges.jpg' alt='excel named ranges' />
</a>
<br /><br />
Sample Screenshot of Excel Spreadsheet before rules are run
<a href='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/excel-before.jpg'>Click to Enlarge<br />
<img width='450' alt='excel before' height='300' src='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/excel-before.jpg' />
</a>

* Open The Red Piranha Webpage
  * Give URL <http://red-piranha.appspot.com/>
<a href='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/screen1-signin.jpg'>Click to Enlarge<br />
<img width='450' alt='Red Piranha Signin Screen 1' height='300' src='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/screen1-signin.jpg' />
</a>

* Sign In to Google
<a href='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/screen2-signin.jpg'>Click to Enlarge<br />
<img width='420' alt='Red Piranha Signin Screen 2' height='300' src='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/screen2-signin.jpg' />
</a>

* Select the spreadsheet you want to run from the Dropdown
  * Screenshot before <http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/chocolate-data.xls>
<a href='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/screen3-run-rules-online.jpg'>Click to Enlarge<br />
<img width='420' alt='Red Piranha Screen 3 - run rules ' height='300' src='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/screen3-run-rules-online.jpg' />
</a>

* Select the rules you want to run from the drop down
  * Location of Rules; a package of rules that somebody else has already built [PowerUserGettingStarted](PowerUserGettingStarted.md) if you want to do this yourself. Make reference to the samples that come with this.

|| This sample is a work in progress. So for the moment you can use the existing rules, but not yet
upload your own. Watch this space.

* Hit the Go Button ('Run Sample')
* View the Result
  * Screenshot of example

<a href='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/excel-after.jpg'>Click to Enlarge<br />
<img width='420' alt='excel after' height='300' src='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/excel-after.jpg' />
</a>

## What just happened ##

| Another a work in progress. Sample uses full Drools language, being upgraded to use a DSL (i.e. a clearer, more 'English' like language).|
|:-----------------------------------------------------------------------------------------------------------------------------------------|

_TODO complete Section_ <br />
_TODO move this section to 'power rules'_

* These rules match against (when) every **named range** in the spreadsheet and  (then) turns the rules orange.
* Rules that just ran are below

```
  package net.firstpartners.sample.ExcelDataRules;

  import net.firstpartners.drools.log.ILogger

  import net.firstpartners.spreadsheet.Cell;
  import net.firstpartners.spreadsheet.Range;

  global ILogger log;

  rule "log then modify cell values" 

   when
 $cell : Cell(modified==false)
       
    then
            
     //Note: use the 'modify' block instead 
     //want to give the rule engine a chance to react to these changes
     $cell.setModified(true);
     
      //Logging message
     log.info("initial cell value:"+$cell);
     
 end
  
```

## What do I do next ##

_TODO complete Section_

* Suggestions on how to play with the data
* Suggestions on how to write your own business rules - [PowerUserGettingStarted](PowerUserGettingStarted.md) is a good next step as it shows

#

## Introduction to the Rule Player ##

There are two main ways of interacting with Red-Piranha to build and run your business rules

* Red Piranha - the Web Interface for applying rules to your speadsheet. You saw this in the [Online Examples](RedPiranhaExamples.md), and used it as part of the [User Getting Started](UserEndUserGettingStarted.md). These samples allow you to change the information in the spreadsheets, but not the rules that run in them.

* Rule Player; If you want to build your own rules, you'll need the Rule Player. The Rule Player is downloaded from the web, but uses the power of your local machine. After it packages the rules, it uploads the finished package to the web, where we can share it with other users via Red Piranha.

(There is a third way if you're a [developer, as set out here](DevDeveloperGettingStarted.md)),

## How does it work ##

We're not going to actually run any rules using Ruleplayer (see [End User Getting Started for that](UserEndUserGettingStarted.md)). What this page shows you how to do is how to write and prepare your rules so that other people can use them.

* [What you'll need before you get started](#What_you'll_need.md)
* How to [Write your rules somewhere](#Writing_your_own_Rules.md)
* [Getting the Rule Player](#Getting_the_Rule_Player.md)
* [Start the Rule Player to check and package these rules](#Running_the_Rule_Player.md)
* (Optional)[Deploying to Your Own Google App Engine](#Deploying_to_Your_Own_Google_App_Engine.md)
* [How to share your rules online](#Sharing_and_the_Community.md) so that you and other people can run them.

## What you'll need ##

| Google Docs Account | The completed, packaged rules are loaded as a file into Google docs. Obviously you'll need to create a [Google account](http://docs.google.com) if you don't already have one. |
|:--------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

## Writing your own Rules ##

_TODO Complete Section_

  1. Edit rules online - e.g. using Google Docs, or host them anywhere on the web (e.g. Google Sites, file on webserver _TODO instructions for these_
  1. Make the rules publicly available (so Red-Piranha can find them)
  1. _TODO Helps if links to red-piranha project_
  1. _TODO Suggest copy existing template_

## Getting the Rule Player ##

_TODO Complete Section using Docker_

There are three options for running the Rule Player. More information on each is provided in the next sections.

## Running the Rule Player ##

_TODO Complete Section_

For the moment , take a look at the DevDeveloperGettingStarted to see how to run the Rule Player through eclipse

  1. Screenshot; Should start automatically
  1. Run again if press the big green button
  1. Should see the following txt

## Deploying to Your Own Google App Engine ##

_TODO Complete Section_ <br />
_TODO Code for this not yet implemented. For the moment use ours - see [UserEndUserGettingStarted](UserEndUserGettingStarted.md)_

* Link to Google App Engine Launcher
* Link to where to get the WAR file to deploy
* Doc how to get Google App engine account, what it is etc.

### Errors when running the Rule Player ###

_TODO Complete section_

* Refer to [FAQ](FAQ.md)
* No Java Installed
* Errors getting started (Java, download etc)
* Errors in compiling rules
* Errors in uploading

## Using your New Rules ##

_TODO Complete Section_

* By default rules are shared (explain how)
* Link to rules directory
* Highlight differences in running rules from [UserEndUserGettingStarted](UserEndUserGettingStarted.md)

## Sharing and the Community ##

_TODO Tie this doc to the community_

* How do I get help
* Where do I find more examples?
* Where can I show off my example?
* Auto submit of successful deploys
* why your email is added to email list (and how to remove?)
* Gallery

### Now What can I do with this ###

_TODO Complete Section_

* Link to [RedPiranhaExamples](RedPiranhaExamples.md) & [KeyConcepts#Community](KeyConcepts#Community.md) Community pages
* [DevDeveloperGettingStarted](DevDeveloperGettingStarted.md) provides additional information, at a more technical level.
* _TODO Loop back to [UserEndUserGettingStarted](UserEndUserGettingStarted.md), only this time run your own rules_

# Frequently Asked Questions

_TODO Explain what business rules are_

## Community ##

_TODO Explain the nature of sharing and the community_

## Business Rules ##

A business rule is a compact and simple statement that represents some important aspect of a business. By capturing the rules for your business--the logic that governs its operation-- you gain the ability to create systems fully aligned with your business needs.

All people have business rules – except that they might not them of them as such. Examples of rules are 'if the market rises by more than 25%, then sell the stock', 'payout the claim if it is less than Eur100' or similar statements the fit your particular domain.

While the use of well understood algorithms such as Rete and Leaps can make systems that use Business Rules both simple and run quickly, we are interested primarily in the financial knowledge management possibilities afforded to us by Rule engines.

Because business rules can be written in 'near English' language, not hidden in code, they are much more meaningful to non-technical domain experts. In this format, the knowledge they contain is easier to manage, maintain and share. They also carry the advantage of separating the meaning of the business from the actual technical implementation.

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

### Rules ###

### Running the Example ###

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

### Screens - details ###

### Logs - More info on what is going on ###

Log then modified - simple sample of where modified

* Launch4j.log
* Red-piranha.log

Only once

Where to find output when done

Make sure to close output

How to log to console

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

### More on Document Output ###

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

### Explaining the DRL ###

Note \\ needed in java when specifiying directories

In translator.dsl

* Adding #/result will debug the generated rules to the log

### Writing your first rule ###

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

#### More rules ####

* Refer to showcase (of every possible rule)

* Using drl
* Without using drl

Checks for values

Using offsets

Cell Object (and javadoc on it)

Iterative development

Link to sample rules catalog
