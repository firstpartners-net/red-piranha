# Todo List

Yes, we should be using the GitHub issue tracker. But for this stage in development (multiple small improvements) a todo list works fine.

## NEXT STEP 

* Test more real life
    * Run against three tables in first sheet #### DEBUG ###
        * check for already exists and how deal with it
        * check for blank header
    * Review and tweak other anmes being output
    * PreprocessTest Unit to green
    * Example 4 running via webpage



* Update documentation (go through again)
    * In example 4 readme.html
    * In script comments
    * In Javadoc
        * What is is
        * Name of Class
        * See scripts in exampleds
    * Readme.md
    * Other .md files

## Next - CSV Output
* Output to CSV
    * output as 2 or three columns - update previous class with this

## Next - Multiple files

* Generate 2nd File into 5th sample folder (for multiplel filtes test)
    * copy generate spreasheet
    * create new DirectortInputStrategy to "walk" folder and call other input strategy

## Next - Data Quality
* Simple Check rules
    * just enough rule to filter / check for dud data
    * may need to see if can write rule to remove all

## Next - Deployment
* Can deploy and work against true docs


## Refresh knowledge and tidy
* Snapshot current code and make note for book readers
* dsl based on existing - working and sharable
* Read all docs and refresh
* Add note on book to here
* Resolve GitHub [firstpartners-net/red-piranha] pages build and deployment workflow run 
* Tests passing

## Next Sprint

* Docs
    ** Tidy current docs - next pass
* Future
    * Create Fiancial extraction Sample - analsyis of single case
    * multiple output options
    * decision test

## Release
* Automate release (package and docker)
* Or at least update release notes
* do interim github and docker release
* Merge back into main, create new branch?


## Tidy
* Clear down warning
* Figure out why current excel example is outputting incorrectly
* More info through to front end
* more tests compile / rule
* tidy of all readme pages
* view vulneribilty report (right click top right of tabs and renable)



## Confirm delivered on previous sprint

* Platform (with specific examples)
* Good draft of user and dev documentation.
* (Y) Can hand to Java developer and build - proper release
* (Y) Could be run by simple end-user with docker - proper release

## refactor and upgrades
* (Move RuleRunner.java remove RuleBuilder to same pre-built model that * DecisionModel uses)
* Simplify down to Cells (remove all notion or ranges?)
* restore commented out tests (put @Test back on them)
    * testJSONInOut
* run JavaDoc checker and tidy comments
* Migrate to latest version of all libs
* tidy DSL example

## Move to above, delete, or capture as future dieas
* recover and reused previous examples (to web) - currently in test folder
* understand model back and forward better
* conversion to-from excel - make sure it works better, json better?
* Dive through stack - can bring back any more info to user
* Tidy Bootstrap on web page
* final tests running
* RP using model from business central or simliar exported backage
* Move bootstrap offline (and other javascript to make more robust)
* Serialsiation - Into Tests of convertor / Java test data in JSON
* use to test cell conversion
* Better display of Java objects
* Read drools doc in more detail
* Outline how to integrate with KIE Server
* Update red-piranha-sourceforge.com to point to github (grab info first)
* Enable Swagger
* Turn back on Spring Services in applicaiton.properites
* Understand Github pages, Jekyll and see if can build an even 'friendlier' version of this site.

# Medium Term Ideas
