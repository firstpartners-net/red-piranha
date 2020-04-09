# Red Piranha 

![Red Piranha Logo](/site/images/top/02.gif)

## More powerful Excel, Google Docs and Spreadsheets using JBoss Rules and Google App Engine

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

| ![Man Icon](http://icons.iconarchive.com/icons/mart/glaze/48/man-icon.png) | **[Have you just bought the book?](docs/Book.md)** - Beginning Business rules with JBoss Drools. <br /> Where to download the samples and other useful information. |
|:----------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ![icon](http://icons.iconarchive.com/icons/mart/glaze/48/spreadsheet-icon.png) | **[I dabble with Excel - what do I do now? ](docs/u-EndUserGettingStarted.md)** User getting started <br /> including how to run the samples online, how to use with your own Excel / Google Spreadsheets|
| ![icon](http://icons.iconarchive.com/icons/mart/glaze/48/package-development-icon.png) | **[I'm an Excel power user - what do I do now?](docs/PowerUserGettingStarted.md)** Power User Getting Started. <br /> How to build your own rules against these spreadsheets and share them with other people.|
|  ![icon](http://icons.iconarchive.com/icons/mart/glaze/48/source-j-icon.png) | **[I'm a developer - what next?](docs/DevDeveloperGettingStarted.md)** How to extend Red Piranha to get it to do exactly what you want.|


## Look at the Examples

[Examples Online](docs/RedPiranha.md) are a work in progress. For the moment, you can look at the [Downloadable Java examples](docs/RedPiranhaExamples.md). The [Book](docs/Book.md) page also contains samples on how to use Drools

### Where are the samples ###
  * Located In the folder src/net/firstpartners/samples
  * Unit tests (to call) are in the folder test/net/firstpartners/samples
  * Each Unit test contains a 'Main' Method so that they can be called from the command line


## How these sample differ from the book ##

These samples are similar, but differ in several key aspects to the book samples

  1. These samples are more 'Excel Centric' i.e. they use the Red Piranha Code base to execute Rules Code (although Drools is still being called underneath)
  1. These samples are 'work in progress' and will diverge further over time.
  1. For the moment, these samples are less 'user friendly'. E.g. they use an Ant instead of a Maven build, are called using JUnit etc

_For the moment this page presumes that you've followed the instructions on the
[DevDeveloperGettingStarted](DevDeveloperGettingStarted.md) page._

## Looking for Book Code
We have snapshotted the [code that the book was written on here](https://github.com/paulbrowne-irl/red-piranha/tree/v0.9). You probably don't want this, the libraties this code is based on are now deprecated. The code in the rest of this site is much more up to date.

## Links 

* [Business Rules](docs/BusinessRules.md)
* [User Getting Started](docs/u-EndUserGettingStarted.md)
* [PowerUser Getting Started](docs/PowerUserGettingStarted.md)
* [Examples](docs/RedPiranhaExamples.md)
* [FAQ](docs/u-FAQ.md)
* [Book](docs/Book.md)
* [Developer Notes](docs/DeveloperNotes.md)

## Quick Start 

1. Click on run as 'Web application'
1. GWT: open url http://localhost:8888/rpproto2.html
1. Normal Tomcat: open url http://localhost:8888/
 

Images from [here under GPL](http://www.iconarchive.com/show/glaze-icons-by-mart/spreadsheet-icon.html)
