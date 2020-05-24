_TODO Tidy this page_

|  <img src='http://icons.iconarchive.com/icons/mart/glaze/48/source-j-icon.png'> <table><thead><th> You've already checked out the <a href='RedPiranhaExamples.md'>Online Examples</a> . And you've looked at the  <a href='UserEndUserGettingStarted.md'>UserEndUserGettingStarted</a> and <a href='PowerUserGettingStarted.md'>PowerUserGettingStarted</a> to see how to add your own information and new rules to these samples.But what if there is something that Red-Piranha doesn't yet do, a feature you want to add? This page shows you how to get started.</th></thead><tbody></tbody></table>


There are two parts to the Red-Piranha Project<br>
<ul><li><b>Red Piranha</b> - the core project, deployable to Google App Engine (GAE) that takes excel spreadsheets and runs pre-compiled rules against them. While this is a command line and other interace, mostly this project runs on the web.<br>
</li><li><b>Rule Player</b> - A simple program used by Power Users (see <a href='PowerPowerUserGettingStarted.md'>PowerPowerUserGettingStarted</a> in order to compile the rules for running in GAE. This runs (via Java Webstart, or local Java) on the Users PC (Windows, Mac, Linux or other). This local compile is needed as Google currently do not whitelist some of the code Drools needs to compile. Once compiled, the rule package is loaded into Google App Engine where normal users to can use them.</li></ul>


<h2>Instructions for developers on how to start using Red-Piranha</h2>

<ul><li><a href='#Setup_the_environment.md'>Setup the environment</a>
</li><li><a href='#Checking_out_the_code.md'>Checking out the code</a>
</li><li><a href='#Building_the_code.md'>Building the code</a>
</li><li><a href='#Running_the_code_locally.md'>Running the code locally</a>
</li><li><a href='#Deploying_to_App_Engine.md'>Deploying to App Engine</a>
</li><li><a href='#Building_your_own_rules.md'>Building your own rules</a>
</li><li><a href='#More_Options_on_running_the_Rule_Player.md'>More Options on running the Rule Player</a>
</li><li><a href='#Extending_Red_Piranha.md'>Extending Red Piranha</a></li></ul>

<h3>Other useful pages</h3>

<ul><li><a href='DevProjectLayout.md'>DevProjectLayout</a> - How the project is organised, important files, places to start reading the code.<br>
</li><li><a href='DevLibrariesUsed.md'>DevLibrariesUsed</a> - Libraries that Red Piranha uses, and thanks to the projects that we've built on.<br>
</li><li><a href='RoadMap.md'>RoadMap</a> - When Red Piranha is going , and how you can help.<br>
</li><li><a href='RedPiranhaExamples.md'>Running the Examples</a>
</li><li><a href='DevAntBuildFile.md'>- How to build Red Piranha and Red Player from source using Ant</a>
</li><li><a href='DevRedPiranhaForDroolsDevelopers.md'>Red Piranha for Drools Developers</a></li></ul>


<h2>Setup the environment</h2>
<ol><li>Ensure you have Java installed on your Machine<br>
</li><li>If you don't already have a google account, get one at <a href='https://www.google.com/accounts/NewAccount?continue=http://code.google.com/hosting/faq.html'>here</a>
</li><li>Download and install <a href='http://www.eclipse.org/home/categories/index.php?category=enterprise'>Eclipse</a>
</li><li>Get the <a href='http://subclipse.tigris.org/'>Subversion Plugin for Eclipse</a>:<br>
</li><li>Get the <a href='http://code.google.com/eclipse/docs/download.htmlGoogle'>App Engine Plugin for Eclipse</a>
</li><li>Get the <a href='http://www.jboss.org/drools/downloads.html'>Drools Eclipse Plugin</a></li></ol>

<h2>Checking out the code</h2>

The source code for Red Piranha is hosted by Google using Subversion. Background Information on Subversion (SVN) source control, hosted at Google code can be found at:<br>
<ul><li>Google FAQ on using Subversion; <a href='http://code.google.com/p/support/wiki/SubversionFAQ'>http://code.google.com/p/support/wiki/SubversionFAQ</a>
</li><li><a href='http://internetducttape.com/2007/0/03/howto_google_code_hosting_subversion_tortoisesvn/'>This post</a> is a good description of how to checkout the Google code.<br>
</li><li>Browse the source at <a href='http://code.google.com/p/red-piranha/source/checkout'>source</a>.</li></ul>

If you're logged in with google,<br>
<a href='http://code.google.com/p/red-piranha/source/checkout'>This page</a> will give you more detailed instructions on how to Checkout the code<br>
<br>
<br>
When checking out the two projects, they should end up in two parallel folders i.e.<br>
<ul><li>'parent_dir'/red-piranha<br>
</li><li>'parent_dir'/rule-player</li></ul>

This is because the rule-player build depends on some items from the 'core' red-piranha project (e.g. Drools Libraries, the Core Red Piranha code) and puts the result (e.g. the Jar with JNLP files) back into the Red-Piranha project. Once deployed to the App Engine, Power Users can download and run the completed Rule Player as part of the deployed project.<br>
<br>
Source folders for both projects are:<br>
<ul><li>red-piranha - checkout from <a href='http://code.google.com/p/red-piranha/source/browse/#svn%2Ftrunk'>http://code.google.com/p/red-piranha/source/browse/#svn%2Ftrunk</a>
</li><li>rule-player - checkout from <a href='http://code.google.com/p/red-piranha/source/browse/#svn%2Fred-player'>http://code.google.com/p/red-piranha/source/browse/#svn%2Fred-player</a></li></ul>

Take care, as the structure in subversion differs slightly from the way the should be on your local disk after check out (see previous paragraph).<br>
<br>
<h2>Building the code</h2>

<i>TODO Complete these instructions</i>


There two ways to build the Red Piranha Code - Via the supplied ant build files, or via Eclipse.<br>
<br>
<h3>How to build in Ant</h3>

<a href='DevAntBuildFile.md'>DevAntBuildFile</a> gives details on how to build the Red Piranha Project via Ant.<br>
<br>
<h3>How to build  in Eclipse</h3>

Personally, I use Eclipse to edit and build the code (hence the reason why Eclipse build files are included when you check out the code).<br>
The Eclipse file should work once you check it out (as all the libs are provided), but right clicking on the Red Piranha will allow you<br>
to resolve them.<br>
<br>
<h2>Running the code locally</h2>

<b>Rule Player Console</b>

The Rule Player - can be run directly in Eclipse<br>
<ol><li>Right click on RulePlayer Project in Eclipse, select 'Run as Java Application'<br>
</li><li>In the Dialog that follows, select the 'RulePlayer' Class - its under net.firstpartners.player<br>
</li><li>If this works ok, you should see the Rule Rule Console appear, similar to the following<br>
</li></ol><blockquote><img src='http://red-piranha.googlecode.com/svn/wiki/images/screenshots/console-after.png' alt='RulePlayer Console' /></blockquote>

<b>Red Piranha Junit Tests</b>
<blockquote>(see <a href='DevProjectLayout.md'>DevProjectLayout</a>), right click and run as JUnit test.</blockquote>

<b>Red Piranha Main</b>
<ol><li>Find the Rp2CommandLine file (it's under net.firstpartners.ui)<br>
</li><li>Right click on this file, select 'Run Configurations ...'<br>
</li><li>Add any Arguments under the 'Arguments' tab<br>
</li><li>Click Run</li></ol>

<b>Google App Engine Local</b>
<ol><li>Make sure you have Google App Engine plugin for Eclipse installed.<br>
</li><li><i>TODO</i> more detailled instructions</li></ol>



<h3>Developer signing Rule Player jars</h3>

<ul><li>Explain Why - Webstart security (what is<br>
</li><li>Jarsigner - file in rp/scripts<br>
</li><li>More info at <a href='http://java.sun.com/developer/Books/javaprogramming/JAR/sign/signing.html'>http://java.sun.com/developer/Books/javaprogramming/JAR/sign/signing.html</a>
</li><li>Command similar to <code>jarsigner -keystore ~/.keystore *.jar mykey</code> but make sure that you are in<br>
</li></ul><blockquote>red-piranha/war/ruleplayer/lib folder when you execute this.</blockquote>



<h2>Deploying to App Engine</h2>

In general, most people will only want to deploy the packaged rules. These instructions are only if you are editing the Java Code in the Core Red Piranha build, or if you want to have your own instance of Red Piranha on the Google App Engine<br>
<br>
<h3>Deploying Red Piranha to GAE Via Eclipse</h3>

<ul><li>Install <a href='http://code.google.com/appengine/docs/java/tools/eclipse.html#Installing_the_Google_Plugin_for_Eclipse'>The Google App Engine Toolbar in Eclipse</a>
</li><li>Ensure the Red-Piranha project in Eclipse is setup as a GAE Project (similar to <a href='http://code.google.com/appengine/docs/java/tools/eclipse.html#Creating_a_Project'>Creating a new App Engine Project</a>
</li><li>Upload the Project to GAE (<a href='http://code.google.com/appengine/docs/java/tools/eclipse.html#Uploading_to_Google_App_Engine'>Instructions here</a>) by pressing the GAE button in Eclipse. <img src='http://red-piranha.googlecode.com/svn/wiki/images/google_app_engine_eclipse_button.png' /></li></ul>

<h3>Deploying Red Piranha to GAE Via Ant</h3>

More info at <a href='DevAntBuildFile.md'>DevAntBuildFile</a>


<h3>Deploying Red Piranha to GAE Via Rule Player</h3>
<ul><li>Feature not implemented yet <i>TODO</i> Document when done</li></ul>



<h2>Building your own rules</h2>

<i>TODO - complete or remove this section</i>

This is for building your rules in the Eclipse Development environment. <a href='PowerPowerUserGettingStarted.md'>PowerPowerUserGettingStarted</a> details how most users will build their rules and then share them via the web<br>
<br>
<ul><li><i>work in progress</i>  For the moment, you need to<br>
</li></ul><ol><li>Write your rule (similar to <a href='http://code.google.com/p/red-piranha/source/browse/trunk/war/sampleresources/ExcelDataRules/log-then-modify-rules.drl'>log-then-modify-rule.drl</a>
</li><li>Add you new rule using <a href='http://code.google.com/p/red-piranha/source/browse/trunk/src/net/firstpartners/drools/PreCompileRuleList.properties'>PreCompileRuleList.properties</a>
</li><li>Create the pre-built KnowledgePackage using [PreCompileRuleBuilder.java <a href='http://code.google.com/p/red-piranha/source/browse/trunk/src/net/firstpartners/drools/PreCompileRuleBuilder.java'>http://code.google.com/p/red-piranha/source/browse/trunk/src/net/firstpartners/drools/PreCompileRuleBuilder.java</a>] . TestPreCompileRuleBuilder is one easy way of running this through eclipse<br>
</li><li>Modify the <a href='http://code.google.com/p/red-piranha/source/browse/trunk/war/service.jsp'>Service.jsp</a> to include the option of selecting your newly created knowledgebase.<br>
</li><li>Deploy to Google App Engine and enjoy!</li></ol>

<h3>More Options on running the Rule Player</h3>

<i>TODO Complete Section</i>
<ul><li>Refer to online in <a href='PowerPowerUserGettingStarted.md'>PowerPowerUserGettingStarted</a>
</li><li>But some more options<br>
<ul><li>Url <a href='http://localhost:8888/ruleplayer/RulePlayer.jnlp'>http://localhost:8888/ruleplayer/RulePlayer.jnlp</a>
</li><li>Running through Eclipse</li></ul></li></ul>


<h2>Extending Red Piranha</h2>

<i>TODO Complete Section</i>

for the moment you'll have to explore the code!<br>
<br>
<b><a href='http://code.google.com/p/red-piranha/source/browse/trunk/src/net/firstpartners/drools/PreCompileRuleBuilder.java'>Link to Builder</a></b> The code that builds the pre-compiled rule packages. Start with the main<a href='.md'>.md</a> method.<br>
<br>
<b><a href='http://code.google.com/p/red-piranha/source/browse/trunk/src/net/firstpartners/ui/Rp2Servlet.java'>Link to Servlet</a></b> The code that calls the pre-compiled rule packages. Start with the service() method that runs in response to the form submitted by the jsp submission.<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />