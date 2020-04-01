#Summary of the main folders in the project
#sidebar SideBarDeveloper

_TODO Match this against the updated project layout_

_TODO Insert Screenshot_


### Other useful pages ###

  * [DevProjectLayout](DevProjectLayout.md) - How the project is organised, important files, places to start reading the code.
  * [DevLibrariesUsed](DevLibrariesUsed.md) - Libraries that Red Piranha uses, and thanks to the projects that we've built on.
  * [RoadMap](RoadMap.md) - When Red Piranha is going , and how you can help.
  * [Running the Examples](RedPiranhaExamples.md)

# Project Layout #

If you're [browsing the code](http://code.google.com/p/red-piranha/source/browse/#svn%2Ftrunk) or
have [the code checked out](DevDeveloperGettingStarted.md) these are some useful start points.

(add RulePlayer Layout)


The project is intended to be used in the following ways, hence the project layout
  * Hosted in in Google App Engine (although the layout is almost standard Java web application)
  * Run from the command line
  * Run via JUnit Tests (mainly for developers to check code quality)


## Useful Start Points ##
  * src/net/firstpartners/ui/Rp2CommandLine.java - call from commandline (Work in progress)
  * src/net/firstpartners/ui/Rp2Servlet.java
  * src/net/firstpartners/ui/Drools/PreCompileRuleBuilder - Main method rebuilds the packages as listed in PreCompileRuleBuilder.properties
  * test/net/firstpartners/rp2/Drools/PreCompileRuleBuilderTest - Also rebuilds the packages

## Project Layout ##
  * Update wiki [DevProjectLayout](DevProjectLayout.md) with new structure
  * add script folder

## Red Piranha Main folders and useful files ##

  * Root of project ; Scripts and project files
  * .classes
  * .Settings - Eclipse Specific Project files
  * build
  * junit-test-reports
  * src - source code for the project
  * src/net/firstpartners/drools - Code to Interface with Drools
  * src/net/firstpartners/gwt - GWT Interface code (labs)
  * src/net/firstpartners/rp2 - Entry points for starting the programme
  * src/net/firstpartners/sample - samples of how to use Drools / Red - Piranha
  * src/net/firstpartners/spreadsheet - datamodel to hold information from Excel and Google Docs
  * test - Junit Test code. Mirrors the above source structure.
  * testbuild
  * war - Compiled code, setup for deployment as a Web archive (war file). This folder also con
  * rpproto2 - GWT compiled code for deployment on the front end
  * WEB-INF








<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />


