# Todo List

Yes, we should be using the GitHub issue tracker. But for this stage in development (multiple small improvements) a todo list works fine.


## Next - Multiple files

* NEXT RESOLVE

    * Robustness
        * spring timeout make note in example
            * add to readme.md this and other settings in application.properties
        * Move cnd to local
        * Unit test Splitter (search Splitting fields from)
        * Review which items are log.info and which are log.debug (for when deploinging)
        * where cell is "" because of null, pass back null 



# Mini Snapshot -> Main
    * For later pulling down and test

## Next tidy

* test cell conversion
    * Run other unit tests
        * any other unit test to try out specific conversation (search for Spreadhsheet convertor tess) ###
        * Unit test CellConvertorTest (contiguours ranes) - not returning values


## Next - Deployment snaphot (tag and docker)
* Can deploy and work against true docs
* Merge branch back into main (and create bugfix branch)
* Snapshot current code and make note for book readers
* Add note on book to here
* Tests passing - any others I can make work (quick check)


## Upgrade Doc
* add 3 minute quickstart
    * Readme.md how to run the sample in VSCode online
* add about new and old book
* tidy off *notes*.md files

* update notes-release.md and use to improve notes
* Carry out release
    * github
    * docker




## Release
* Or at least update release notes
* do interim github and docker release
* Merge back into main, create new bugfix branch?

## ##############

## Next Sprint - after immediate needs

## Backup Plan
* Possible create https://www.baeldung.com/spring-boot-console-app


## Use case - confirm

* Future
    * Create Fiancial extraction Sample - analsyis of single case POC - use 4 complex tmp
    * multiple output options
    * decision test
* dsl based on existing - working and sharable
* Tests
    * integration tests - run  independalty so a failure in one does not block all

## More Docs
* Automate release (package and docker)
* Resolve GitHub [firstpartners-net/red-piranha] pages build and deployment workflow run 
* graphic to show steps (Prepprocess, Input Strategy convert excel->Java, Rules, Output Strategy convert Java -> CSV) and map against screenshot

    
## Next - Data Quality
* Simple Check rules
    * just enough rule to filter / check for dud data
    * may need to see if can write rule to remove all
* Cross reference back to data
    * add original cell refernece (need to track through as not currently accurate)


## ############## MEDIUM TERM

## Tidy
* Clear down warning
* Figure out why excel->excel example is outputting incorrectly
* More info through to front end
* more tests compile / rule
* tidy of all readme pages
* view vulneribilty report (right click top right of tabs and renable)
* restore commented out tests (put @Test back on them)
    * testJSONInOut

## upgrades and housekeeping


* Migrate to latest version of all libs
* tidy DSL example


# Medium Term Ideas
* better display of information in JSON
* RP using model from business central or simliar exported backage
* Better display of Java objects on webpage- open by default
* Understand Github pages, Jekyll and see if can build an even 'friendlier' version of this site.
* Outline how to integrate with KIE Server


# Medium Term tidy
* Tidy Bootstrap on web page
* final tests running
* Serialsiation - Into Tests of convertor / Java test data in JSON (replace binary)
* Rengerate Javadoc, tidy to site
* recover and reused previous examples (to web) - currently in test folder
* Simplify down to Cells (remove all notion or ranges?)

