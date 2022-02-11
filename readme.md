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

The information in this document is aimed at getting Excel Power Users up and running with Red Piranha. If you're a developer, or want to dive 'under the covers' [more detailed info is here](doc).

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

### Notes on the examples the Example

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
### Screens - details

_TODO tidy_
### Logs - More info on what is going on


_TODO tidy_
Log then modified - simple sample of where modified

* Launch4j.log
* Red-piranha.log



## Writing your own Rules

_TODO Update Section_
* _Editing using Business Central_
* _Editing Manually_
* _Editing using IDE_


  1. Edit rules online - e.g. using Google Docs, or host them anywhere on the web (e.g. Google Sites, file on webserver _TODO instructions for these_
  1. Make the rules publicly available (so Red-Piranha can find them)
  1. _TODO Helps if links to red-piranha project_
  1. _TODO Suggest copy existing template_
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



### Writing your first rule

_TODO tidy_

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

### More rules 

_TODO tidy_

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
