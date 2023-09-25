# Todo List

Yes, we should be using the GitHub issue tracker. But for this stage in development (multiple small improvements) a todo list works fine.


# NEXT STEP 
* Groovy
    * test script running and binding excel / as part of Unit test
    * decide on model to pass in script
    * check if preprocesser needed in red controller
    * update documentation sample 4
    * web page (top, samples, button),model, examples.json, sample class add pre-process


# DECIDE
* Groovy script 
* BACKUP: Airflow, Apache Nifi etc
* BACKUP: RP calls Python or Grovy script to extract (** most likely **)



## Refresh knowledge and tidy
* Snapshot current code and make note for book readers

* dsl based on existing - working and sharable
* tidy docs to first draft
* Add note on book to here
* Resolve GitHub [firstpartners-net/red-piranha] pages build and deployment workflow run 
* Tests passing

## TODO Next Sprint

* Create folder sample
    * update readme in folder
** Read all files in folder
** Output
* Docs
** Tidy current docs - next pass
* Future
** Create Fiancial extraction Sample - analsyis of single case
** multiple output options
* decision test
* Release
** Automate release (package and docker)
** Or at least update release notes

## Tidy
* Clear down warnings
* resolve spring boot issue
* get rulerunner test passing
* check output through to csv
* Figure out why current excel example is outputting incorrectly
* More info through to front end
* more tests compile / rule
* tidy of all readme pages
* view vulneribilty report (right click top right of tabs and renable)

## TODO on Deployment
* Can deploy and work against true docs

## Confirm delivered on previous spring

Platform (with specific examples)

* Good draft of user and dev documentation.
* (Y) Can hand to Java developer and build - proper release
* (Y) Could be run by simple end-user with docker - proper release
* Tests automated and running

## refactor
* (Move RuleRunner.java remove RuleBuilder to same pre-built model that 
* DecisionModel uses)
* Simplify down to Cells (remove all notion or ranges?)
* Remove .xls support
* restore commented out tests (put @Test back on them)
** testJSONInOut

## Upgrades
* Migrate to latest version of all libs

## later - tidy off these
* recover and reused previous examples (to web) - currently in test folder
* understand model back and forward better
* conversion to-from excel - make sure it works better, json better?
* Dive through stack - can bring back any more info to user
* Tidy Bootstrap on page
* final tests running
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
