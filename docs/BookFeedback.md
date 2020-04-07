# Book Feedback #

This page contains reader feedback to the book 'JBoss Drools Business Rules'. Specific technical issues can be found at the [Issues List](http://code.google.com/p/red-piranha/issues/list?can=1&q=&colspec=ID+Type+Status+Priority+Milestone+Owner+Summary&cells=tiles)

# General #

  * It's quite difficult to get started with Drools, especially if you don't have experience in developing Java applications. You might imagine how steep the learning curve is for business analysts, who associates Java with an island or coffee. However this book is aimed at exactly this group of non-technical people, who have knowledge how business runs, but don't know how or simply don't have the tools to persist it and 'transfer it to the world of computers'. Most chapters are kept on a really basic level if it comes to writing Java code. On the other hand it's sufficient to get started. Moreover all used tools are open source software, which means you can use it for free.

  * This book goes through almost all parts of Drools, especially the most important for business people like Guvnor, the application for authoring rules, DSLs allowing to write rules in plain English as well as support for the business analyst's most favorite tool: Excel spreadsheets.

  * The author even introduces typical developers' tools like Eclipse, however I would recommend to go through an updated guide about installing the Eclipse plugin available in the Drools docs:http://downloads.jboss.com/drools/docs/5.0.1.26597.FINAL/drools-introduction/html_single/index.html#d0e1371

  * A new requirement, that did not make it into this book, is to set up the Drools rule engine.
_Paul: All good comments, will include in the next version!_

  * There's a chapter I like very much. It makes very clear that testing rules is an important part of the development phase and introduces three different ways to achieve full coverage of all written rules. The inquisitive reader might also have a look at the QA Analyzer, a quite new and powerful feature available in Guvnor.
_Paul: Thanks - I'm a bit of a testing fanatic, having been burnt badly by **not** having tests. Good to see somebody else agrees!_

  * One chapter is definitely intended for Java developers - about deploying Drools applications in real world scenarios. The business people will still be able to follow, since the author keeps things easy and doesn't go to deep into details, however I would love to read more about this topic.
_Paul: Packt are publishing an Advanced Drools Book soon - also by an Irish Based developer (Michal Bali). I would hope that it gives more technical detail_

  * The last two chapters about rule engine concepts and the newest features are a must-read for all those people who have read the Drools docs, but still need a different point of view to fully understand how Drools works. This topic is very important in my opinion, since it lets you write your rules more effectively.


  * What I didn't like was the use of IE and Excel. Same results could be achieved with open source software, too. But that's a minor one ;)
_Paul: I would tend to use Firefox / OpenOffice myself, but the business readers of the book use Excel and Internet explorer, hence why the screenshots use those_

  * All in all, definitely a guide for non technical users, but also for developers, who have given the Drools docs a (second and third) try and still require a helping hand to sort it out how this Drools thing works and how to use it properly.
_Paul - Thanks!_


## Critical ##

_Paul: I am currently working at book samples online via the Google App Engine (that's what the Red Piranha project is about!). That may resolve some of the issues below, but I hope to provide 'interim' fixes._

  * The sample 'droolsbook\_chapter3\_sample.zip' contains a repository that can no longer be built by Guvnor. This affects the current version of Guvnor, but the reader has no chance to download the version the author used (I guess some CR or milestone release - no longer available).


  * The rule flow is broken as well as the DSL. This sample is build up on code that is no longer developed so the user cannot download the current working version. I would suggest to fix this (quite easy, just update the rule flow and remove colons in the DSL - they have now special meaning) and reupload the sample.
_Paul: Packt have two good articles from Michal Bali at
http://www.packtpub.com/article/drools-jboss-rules-5.0-flow-part1 and
http://www.packtpub.com/article/drools-jboss-rules-5.0-flow-part2__

## page 61, section 2 ##
  * The search screen... Ooook, that's a tricky one. In one word, in my opinion that section doesn't tell the truth. Maybe you could pass it to the author and he could rephrase it? You cannot use the search feature to look for versioned assets. Versioned assets are something different then archived assets. Version management is done by the underlying JCR implementation and is available from the assets view (under [more info...](Show.md) -> Version history). Saving a new version of a rule does not **archive** the previous version. You have (currently) no chance to search for previous versions of an asset, however I think that might be a good idea and would be worth a jira under the GUVNOR project. To sum it up, IMHO the last sentence about the 'Include archived items in list' should be rephrased and tell that you are able to search for assets that have been archived, besides (the **current** version of) assets that are stored in a package.
_I've re-read the section and you're correct - when I wrote this it was meant as a general comment that you can search for rules - I will update the text_

## page 108, section 2 ##
  * The answer is: never. ...Either it's wrong worded or I don't understand what the author meant. The explanation of the rule is IMHO wrong. The rule does **not** say that it should fire when sales is greater than or equal to 100 and the customer sounds like 'acme' and the customer name matches 'acme corp'. The rule says that if there are sales, where one sale is greater than or equal to 100, one sale has a customer name that sounds like 'acme' and one sale has a customer name that matches 'acme corp' then fire. The rule engine doesn't care if these are 3 different sales objects, two different, or if it's a single object. So I can easilly make this rule fire, just by inserting 3 different sales satisfying the restrictions. Maybe the author meant that this rule cannot be fired with just one sales fact - that would be obviously true, but that's not the picture I get from reading this section. But that's just my opinion.
_Paul: Good point - Never is a very strong statement, and it's really meant to illustrate the difficulty in getting that rule to fire (even if it is possible)_


## page 260, section 1 ##
  * If this unit test runs correctly, only one event will match the rule... The previous pages show the first part of a test. If you look carefully at the code, you will notice that the statement above isn't true. That sentence summarize the **whole** test, which is not shown in the book. When I read it I was quite surprised of this conclusion, lost some time, until I went to my computer to download the test case to see it as a whole.
_Paul : OK_


## major ##

_Paul : Will pick up on these typo's in a future version of the book_

page 100:
bad screenshot (it's a copy of the screen shot from page 108) - this screen shot is then referred to on page 102.

page 104, note:
What exactly is this working memory? ...
I don't see the term working memory mentioned before ,so either it should be changed to:
What exactly is a working memory? ...
Or this note is in a wrong place or some text about the working memory has been deleted/moved.

page 141, 2nd example (collect)
This won't compile, besides the typo in OoomplaLoompaDate this is rule has a wrong syntax, you cannot use collect this way.
Here's a working one:

rule 'collect rule'
> when
> > $numberofShipmentsOnDay : java.util.ArrayList() from collect (
> > > ChocolateShipment (
> > > > shipmentDate == (new OoompaLoompaDate(1,1,2009))

> > > )

> > )
then

> log.info("Boxes shipped today:"+$numberofShipmentsOnDay);
end



## Minor ##
page 21, section 6:
The mailing lists ... that your pleas for assistance ...
'pleas' - should be 'plea'

page 57, bullet 5.:
If you have any problems ... then run the Run the command ...
'Run the' should be removed

page 106, section 2:
This is no: ...
should be:
There is no: ...

page 119, last section
... way of wiring the same ...
typo, should be 'writing'

page 122, section 6:
... from that example (sales.java), ...
typo, should be uppercase s: '(Sales.java)', ...

page 123:
$mySales : Sales(name == "acme corp" name == "beta corp")
missing '||', should be:
$mySales : Sales(name == "acme corp" || name == "beta corp")

page 128, note:
When
should be lowercase w: when

page 191, section 1:
... more Drools-like language $patent : Patient()
typo in patient, should be
... more Drools-like language $patient : Patient()

page 226, section 1:
Change the director
typo, should be:
Change the directory

page 232, section 8 and
page 236, section 2:
typo in RuleRunner.Java, should be RuleRunner.java (lowercase 'J')

# Book Feedback
#sidebar sidebar

# Book Feedback #

This page contains reader feedback to the book 'JBoss Drools Business Rules'.


# Details #

Add your content here.  Format your content with:
  * Text in **bold** or _italic_
  * Headings, paragraphs, and lists
  * Automatic links to other wiki pages
What I didn't like was the use of IE and Excel. Same results could be achieved with open source software, too. But that's a minor one ;)

All in all, definitely a guide for non technical users, but also for developers, who have given the Drools docs a (second and third) try and still require a helping hand to sort it out how this Drools thing works and how to use it properly.



I've found following issues.

minor:
page 21, section 6:
The mailing lists ... that your pleas for assistance ...
'pleas' - should be 'plea'

page 57, bullet 5.:
If you have any problems ... then run the Run the command ...
'Run the' should be removed

page 106, section 2:
This is no: ...
should be:
There is no: ...

page 119, last section
... way of wiring the same ...
typo, should be 'writing'

page 122, section 6:
... from that example (sales.java), ...
typo, should be uppercase s: '(Sales.java)', ...

page 123:
$mySales : Sales(name == "acme corp" name == "beta corp")
missing '||', should be:
$mySales : Sales(name == "acme corp" || name == "beta corp")

page 128, note:
When
should be lowercase w: when

page 191, section 1:
... more Drools-like language $patent : Patient()
typo in patient, should be
... more Drools-like language $patient : Patient()

page 226, section 1:
Change the director
typo, should be:
Change the directory

page 232, section 8 and
page 236, section 2:
typo in RuleRunner.Java, should be RuleRunner.java (lowercase 'J')




major:
page 100:
bad screenshot (it's a copy of the screen shot from page 108) - this screen shot is then referred to on page 102.

page 104, note:
What exactly is this working memory? ...
I don't see the term working memory mentioned before ,so either it should be changed to:
What exactly is a working memory? ...
Or this note is in a wrong place or some text about the working memory has been deleted/moved.

page 141, 2nd example (collect)
This won't compile, besides the typo in OoomplaLoompaDate this is rule has a wrong syntax, you cannot use collect this way.
Here's a working one:

rule 'collect rule'
> when
> > $numberofShipmentsOnDay : java.util.ArrayList() from collect (
> > > ChocolateShipment (
> > > > shipmentDate == (new OoompaLoompaDate(1,1,2009))

> > > )

> > )
then

> log.info("Boxes shipped today:"+$numberofShipmentsOnDay);
end




critical:

The sample 'droolsbook\_chapter3\_sample.zip' contains a repository that can no longer be built by Guvnor. This affects the current version of Guvnor, but the reader has no chance to download the version the author used (I guess some CR or milestone release - no longer available).
The rule flow is broken as well as the DSL. This sample is build up on code that is no longer developed so the user cannot download the current working version. I would suggest to fix this (quite easy, just update the rule flow and remove colons in the DSL - they have now special meaning) and reupload the sample.


page 61, section 2:
The search screen...
Ooook, that's a tricky one. In one word, in my opinion that section doesn't tell the truth. Maybe you could pass it to the author and he could rephrase it?
You cannot use the search feature to look for versioned assets. Versioned assets are something different then archived assets. Version management is done by the underlying JCR implementation and is available from the assets view (under [more info...](Show.md) -> Version history). Saving a new version of a rule does not **archive** the previous version.
You have (currently) no chance to search for previous versions of an asset, however I think that might be a good idea and would be worth a jira under the GUVNOR project.
To sum it up, IMHO the last sentence about the 'Include archived items in list' should be rephrased and tell that you are able to search for assets that have been archived, besides (the **current** version of) assets that are stored in a package.


page 108, section 2:
The answer is: never. ...
Either it's wrong worded or I don't understand what the author meant. The explanation of the rule is IMHO wrong. The rule does **not** say that it should fire when sales is greater than or equal to 100 and the customer sounds like 'acme' and the customer name matches 'acme corp'. The rule says that if there are sales, where one sale is greater than or equal to 100, one sale has a customer name that sounds like 'acme' and one sale has a customer name that matches 'acme corp' then fire. The rule engine doesn't care if these are 3 different sales objects, two different, or if it's a single object.
So I can easily make this rule fire, just by inserting 3 different sales satisfying the restrictions.
Maybe the author meant that this rule cannot be fired with just one sales fact - that would be obviously true, but that's not the picture I get from reading this section. But that's just my opinion.



page 260, section 1:
If this unit test runs correctly, only one event will match the rule...

The previous pages show the first part of a test. If you look carefully at the code, you will notice that the statement above isn't true. That sentence summarize the **whole** test, which is not shown in the book. When I read it I was quite surprised of this conclusion, lost some time, until I went to my computer to download the test case to see it as a whole.