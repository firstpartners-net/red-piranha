# Todo List

Yes, we should be using the GitHub issue tracker. But for this stage in development (multiple small improvements) a todo list works fine.

## Core Aims of next sprint

Platform (with specific examples)

* Good draft of user and dev documentation.
* (Y) Can hand to Java developer and build - proper release
* (Y) Could be run by simple end-user with docker - proper release
* Tests automated and running
* Example - simple for book
* Example - more in detail - for me

## TODO this sprint

* Current Sample

** add tests
** add to rulerunner
** add to webapp
** update to list


* tidy tests - either resolve issues or comment out (note here)
* Pare back to what I need day/what chap12 needs simple sample

* backout sample code in RuleRunnerTest.testRunDmnModel
** Maybe reorg tests

* tidy some docs
* create new sample
* junit to create dmn loader
* add method to load dmn
* get rulerunner test passing

*move scesim to test (and use appendix to test as part of automated build)

## Possible move
Simplify down to Cells (remove all notion or ranges?)

## Todo Next 

* Figure out why current excel example is outputting incorrectly
* More info through to front end
* more tests compile / rule
* tidy of all readme pages

## restore commented out tests (put @Test back on them)
testJSONInOut


## refactor
(Move RuleRunner.java remove RuleBuilder to same pre-built model that DecisionModel uses)

## later

* recover and reused previous examples (to web)
* run from command line
* understand model back and forward better
* trigger exception, make sure it displays ok
* tidy of readme x2
* conversion to-from excel - make sure it works better, json better?
* Dive through stack - can bring back any more info to user
* Tidy Bootstrap on page
* tests running
* RP using model from business central or simliar exported backage
* Move bootstrap offline (and other javascript to make more robust)
* tidy DSL example
* show hide sample
* Serialsiation - Into Tests of convertor / Java test data in JSONu
* use to trest cell conversion
* samples - url to view input, out and rule files (or note how to do this)
* do interim github and docker release
* Better display of Java objects
* Read drools doc in more detail
* Outline how to integrate with KIE Server
* Review / tidy Javadoc
* Maven site - document rp code?
* Update red-piranha-sourceforge.com to point to github (grab info first)
* Enable Swagger
* Turn back on Spring Services in applicaiton.properites
* Understand Github pages, Jekyll and see if can build an even 'friendlier' version of this site.
