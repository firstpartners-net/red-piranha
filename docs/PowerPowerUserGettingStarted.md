| <img src='http://icons.iconarchive.com/icons/mart/glaze/48/package-development-icon.png' /> |What if you want to write your own business rules? This page shows you how. We assume that you've already checked out the [Online Examples](RedPiranhaExamples.md) . And you've looked at the [User Getting Started](UserEndUserGettingStarted.md) to see how you can put your own information into these examples. |
|:--------------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

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
_TODO Complete Section and update in line with new screenshots / configuration_

There are three options for running the Rule Player. More information on each is provided in the next sections.

  * **Local Download JNLP** Download the [Player as a Zip file from here](http://red-piranha.googlecode.com/svn/red-player/release/red-player.zip) , extract (using Winzip or similar tool) then double click on RulePlayerLocal.jnlp. or to start it [More Instructions here](.md).For more information see later on in this page
[the Rule Player Locally From a Download](#Running_the_Rule_Player_Locally_From_a_Download_Running.md)

  * **Local Download, Batch (.bat) or Shell (.sh) file** Same as the previous option, but click on RulePlayer.bat / RulePlayer.sh to start the Rule Player. The main difference is that is is 'one less thing to go wrong' (i.e. Java Webstart), but you lose the automatic install of Java that Webstart gives you.

  * **Web Start from Google App Engine** - Start the RulePlayer http://red-piranha.appspot.com/rulerunner/RuleRunner.jnlp  directly from Red Piranha running on Google App Engine.For more information see [later on in this page](#Running_the_Rule_Player_Directly_from_Web_Start.md).

Either of these methods, if successful, you should see a screen similar to the following;

> <a href='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/screenshot-rule-runner.png'>
<blockquote><img src='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/screenshot-rule-runner.png' alt='RulePlayer Console' />
</a></blockquote>

Using the defaults (as set in the properties file - next section), you should see the following text;

```
Compiling Rules...
Using file:http://red-piranha.googlecode.com/svn/trunk/war/sampleresources/SpreadSheetServlet/log-then-modify-rules.drl
Compiling complete
```

### Running the Rule Player Locally From a Download ###
_TODO Complete_ 

&lt;BR/&gt;




  * Cover both local JNLP, Local Shell
  * Url : http://red-piranha.googlecode.com/svn/red-player/release/red-player.zip
  * Configuring via rulerunner.properties - find and open this file in your favourite editor

```
# Properties file for when the RulePlayer is unpackaged / run locally

# The Url (Web Address) of the Rule File we wish to package 
RuleFile=http://red-piranha.googlecode.com/svn/trunk/war/sampleresources/SpreadSheetServlet/log-then-modify-rules.drl

# Where we store the packaged rule file locally before uploading
KbFileName=LocalRuleFile.kb

# Your Google UserName - you'll be asked for your password later when the RulePlayer runs
GoogleUser=SOME-GOOGLE-USER@GMAIL.COM

# Only set these if you are having difficulty connecting to the internet (e.g. in a corporate environment)
# Uncomment (remove # at start of line) first
# Ask your friendly IT staff the values that you should use
# ProxyHost=SomeCorporateProxy
# ProxyPort=80

```

  * Configure Proxy - edit the rulerunner.properties file to contain the Proxy Host and Proxy Port that you use to reach the Internet (check your web browser / Internext explorer settings as they are likely to use same values).




### Running the Rule Player Directly from Web Start ###
_TODO Complete_

_Note that this may not currently work on your Machine due to_
  1. The way the Jar (download) is packaged_1. The need to configure the JNLP file_

_Work in progress to resolve this issue_

  * Url: http://red-piranha.appspot.com/rulerunner/RuleRunner.jnlp
  * Form to configure _TODO Implement - as this is not there yet_
  * Splash screen
  * (If version of Jav not installed) - Java Install
  * Run

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



<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />