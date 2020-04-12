_TODO Tidy this page_ <br />
_TODO Resize images_


| <img src='http://icons.iconarchive.com/icons/mart/glaze/48/spreadsheet-icon.png' /> | You've already checked out the [Online Examples](RedPiranhaExamples.md) . But how do you try out those examples with your own information? This page shows you how.|
|:------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------|

This section shows you how to apply [Business Rules](BusinessRules.md) to your spreadsheets.

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
| Pointer to Red-Piranha on the Web | Unless you've been told otherwise, use this web link: http://red-piranha.appspot.com/ |

## Step by Step Guide ##

_TODO complete Section_

  * Create your spreadsheet using Google Docs or Excel
    * Make sure it has 'named ranges' - Insert ... Name ... Define
    * Sample is at http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/chocolate-data.xls

<a href='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/excel-named-ranges.jpg'>Click to Enlarge<br />
<img src='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/excel-named-ranges.jpg' alt='excel named ranges' />
</a>
<br /><br />
Sample Screenshot of Excel Spreadsheet before rules are run
<a href='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/excel-before.jpg'>Click to Enlarge<br />
<img width='450' alt='excel before' height='300' src='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/excel-before.jpg' />
</a>

  * Open The Red Piranha Webpage
    * Give URL http://red-piranha.appspot.com/
<a href='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/screen1-signin.jpg'>Click to Enlarge<br />
<img width='450' alt='Red Piranha Signin Screen 1' height='300' src='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/screen1-signin.jpg' />
</a>

  * Sign In to Google
<a href='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/screen2-signin.jpg'>Click to Enlarge<br />
<img width='420' alt='Red Piranha Signin Screen 2' height='300' src='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/screen2-signin.jpg' />
</a>

  * Select the spreadsheet you want to run from the Dropdown
    * Screenshot before http://red-piranha.appspot.com/sampleresources/SpreadSheetServlet/chocolate-data.xls
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









<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />