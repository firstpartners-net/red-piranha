# Red Piranha

![Red Piranha Logo](/site/images/top/02.gif)

## More powerful Excel, Word and Office Documents using Business Rules

Excel is great for collecting data. But once things get more complicated, you may be asking "what next"?

* Your Excel calculations are good. Maybe too good - now you can't follow what is going on. Wouldn't something a little bit clearer be better?
* Are you sharing your spreadsheets? What if somebody changes a formula without you noticing?
* Your business absolutely needs to be sure the rules are being applied - and you need a way of checking what those rules are later.

Powered by [KIE from IBM and RedHat Drools](https://incubator.apache.org/clutch/kie.html), Red-Piranha allows you to state your Excel business rules in a simple **when ... then** format that everybody can understand.  It acts as a bridge between Excel / Microsoft Office and Business Rules and workflow Engine (including AI and Planner) from Red Hat.

Instead of having your knowledge 'disappear' into complicated formulas that you can't read, a Rules Engine keeps things in a simple 'English' like format. Even better, because of the way it works 'under the covers' it is faster and more powerful than code translated into a technical language.

## What is a Business Rule

A business rule is a simple statement that represents some important aspect of a business. By capturing the rules for your business--the logic that governs its operation-- you gain the ability to create systems fully aligned with your business needs.

All people have business rules. Examples of a 'real' busniess rules that can be fed into the engine are:

* `When the market rises by more than 25%, Then sell the stock`
* `When the total is less than €100 And the claimant is low risk Then transfer the funds`

Because business rules can be written in 'near English' language they are much more meaningful to non-technical domain experts. In this format, the knowledge they contain is easier to manage, maintain and share. They also carry the advantage of separating the meaning of the business from the actual technical implementation.

## What is Red Piranha

By default, Drools,  doesn't read in Excel Data. Red Piranha fills in this gap with:

* A user Interface to run the rules from your desktop
* Conversion of your Excel Data into a format the rule engine can read.
* Conversion of the outout back into a (different) Excel file.
* Samples to get your started.
* Information to show you what is going on within the Rule Engine.

## Main features 


![Red Piranha Screenshot](/images/main-screenshot.jpg)

* View in your favourite browser
* Running on Localhost (your local machine)
* Current Settings - shows the current input file, the rules being applied to it, the output file.
* Samples to help you get started.
* Progress messages - did the rules run successfully?
* More detailled messages from within the Rule Engine
* Information from Input files - what our conversion from Excel into Java Objects looks like.
* Information output - what the information coming out of the rule engine looks like before we save it as Excel.

## 3 Minute Quickstart

_Work in progress_ - 
The information in this document is aimed at getting Excel Power Users up and running with Red Piranha. Take a 
[look at the notes in this folder](.).

## Docker Example
If you're into Docker, a packaged example is available on Dockerhub.
https://hub.docker.com/repository/docker/paulbrowne/redpiranha

When running the image, map port 7000, so that you can connect your browser to the application running within docker.

The docker  build is not yet automated, so could be slightly (or a lot) behind the code in this repository.

## Downloading and running the code

The Project follows a standard structure, using the Spring Boot Framework and the Maven build tool. So if you're a Java developer, it's pretty easy for you to download and run.

1. Download and install Java
1. Download and install Apache Maven
1. Download (or checkout) the code from this GitHub Repository, unzip it if needed.
1. Open the `code` folder within this project in a console / terminal. You'll know you're in the correct place if you see a `pom.xml` file. 
1. Start the application using  `mvn spring-boot:run`
1. Open a Browser and point at `http://localhost:7000/`

Samples are integrated into the main webpage - so take a look at them as a next step.

## Older Book - Beginning Business rules with JBoss Drools

[![](http://rcm-images.amazon.com/images/I/511yB7Fl-SL._SL110_.jpg)](http://www.amazon.co.uk/dp/1847196063?tag=firstparnet-21&camp=1406&creative=6394&linkCode=as1&creativeASIN=1847196063&adid=0559JR8EAMSMXZ5S3ZWC&)

You may be here looking looking for the code samples from the book **Beginning Business rules with JBoss Drools**. They're [available to download here](http://code.google.com/p/red-piranha/downloads/list?can=2&q=label%3Adroolsbook&colspec=Filename+Summary+Uploaded+ReleaseDate+Size+DownloadCount). If you've any technical issues with the code [check the existing or create a new issue here](http://code.google.com/p/red-piranha/issues/list?can=1&q=&colspec=ID+Type+Status+Priority+Milestone+Owner+Summary&cells=tiles) - remember to add a label ['Book'](http://code.google.com/p/red-piranha/issues/list?can=2&q=book&colspec=ID+Type+Status+Priority+Milestone+Owner+Labels+Summary&x=priority&y=owner&cells=tiles). A general 'comments on the book' page can also be found here [BookFeedback](BookFeedback.md)

We have snapshotted the [code that the book was written on here](https://github.com/paulbrowne-irl/red-piranha/tree/v0.9). You probably don't want this, the libraries this code is based on are now deprecated. The code in this site is much more up to date.


