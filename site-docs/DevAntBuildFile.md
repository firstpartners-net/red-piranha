_TODO Tidy this page_

## Instructions for developers on how to start using Red-Piranha ##

  * [Assumptions](#Assumptions.md)
  * [Build Files in the Project](#Build_Files_In_The_Project.md)
  * 
  * 
  * [Deploying Red Piranha to GAE Via Ant](#Deploying_Red_Piranha_to_GAE_Via_Ant.md)


> ## Other Useful Files ##

  * [DevProjectLayout](DevProjectLayout.md) - How the project is organised, important files, places to start reading the code.
  * [DevLibrariesUsed](DevLibrariesUsed.md) - Libraries that Red Piranha uses, and thanks to the projects that we've built on.
  * [DevDeveloperGettingStarted](DevDeveloperGettingStarted.md) - How to build Red Piranha and Red Player from source using Ant]

## Assumptions ##

This page assumes that
  * You have [the Ant Build Tool](http://ant.apache.org) installed on your machine, or you can run Ant via Eclipse
  * You have checked already checked out the Red Piranha source code (see [Developer Getting Started](DevDeveloperGettingStarted.md)
> for more information.


> _TODO_
[Ant build](http://ant.apache.org/) files are provided so that the project can build from the terminal or command line, and the project files modified using your favourite editor.

Remember, there are two parts to the Red-Piranha Project
  * **Red Piranha** - the core project, deployable to Google App Engine (GAE) that takes excel spreadsheets and runs pre-compiled rules against them. While this is a command line and other interace, mostly this project runs on the web.
  * **Rule Player** - A simple program used by Power Users (see [PowerPowerUserGettingStarted](PowerPowerUserGettingStarted.md) in order to compile the rules for running in GAE. This runs (via Java Webstart, or local Java) on the Users PC (Windows, Mac, Linux or other). This local compile is needed as Google currently do not whitelist some of the code Drools needs to compile. Once compiled, the rule package is loaded into Google App Engine where normal users to can use them.


## Build Files in the Project ##

## Red Piranha Build File ##

## Red Player Build File ##

_TODO_
  * Document both build file targets
  * Document Main Targets that you'll actually use (and what they do)
  * build.xml within the ruleplayer
  * RulePlayer ant build will build jar, copy into red-piranha/war


  * repackaging of dependicings
  * security and signing jars using the jar-signer script

  * test in eclipse - right click and 'run as applet'

## Deploying Red Piranha to GAE Via Ant ##

More info at [DevAntBuildFile](DevAntBuildFile.md)


  * Ensure that you have [Apache Ant](http://http://ant.apache.org/) installed.
  * [Information from Google on Using Ant to deploy](http://code.google.com/appengine/docs/java/tools/ant.html)
  * Find and edit ant-gae.xml in the Red-Piranha project. One of the first lines will begin `<property name="sdk.dir" location="...` this needs to match with the Google App Engine tools are installed on your maachine
  * Open a command prompt (terminal window or Dos shell) go to the Red Piranaha main folder (which contains the ant-gae.xml file), check everything is working by typing `ant -f ant-gae.xml`
  * Some useful Ant Commands
```
# Run the Red Piranha on a local Webserver - http://localhost:8888/ 
ant -f ant-gae.xml runserver

# Upload the Current build to the 'Real' Google App Engine 
ant -f ant-gae.xml update

```

  * If you have not logged in before, or not loggedin in the previous 24 hours you may get the error `   Your authentication credentials can't be found and may have expired. Please run appcfg directly from the command line to re-establish your credentials.`
  * To solve this , run the script [login-to-google-apps.sh](http://code.google.com/p/red-piranha/source/browse/trunk/scripts/login-to-google-apps.sh) (in the scripts folder of Red-Piranha) - you may need to edit this to run on Windows / point to where you have the GAE tools installed on your PC. It will prompt you for a email / password - Google will remember this for 24 hours.
  * you may need to run this script from the main Red-Piranha folder so that it knows which project to deploy e.g. on Linux `./scripts/ login-to-google-apps.sh` If successful (after a lot of other updates) you should see the message
{{{Update completed successfully.
Success.}}}
  * More info on the [GAE command line tools used by this script](http://code.google.com/appengine/docs/java/tools/uploadinganapp.html)

  * Run the Ant script again, it should now work.
