# Todo List

Yes, we should be using the GitHub issue tracker. But for this stage in development (multiple small improvements) a todo list works fine.

## Next - CSV Output

* Debug
    * introduce space to sheet name
    * regenerate test data in JSON?
    * work through tests
    * work through Calling in spring

* Output to CSV
    * Write Code to meet test
        * Get CSVOutputStrategyMultiLineTest#testCreateAppendToCSV running and passing (write code / remove fails)
    * Update Code  
        * Code append to csv??
        *   test check that works with both create and append

## Next - Multiple files
* Generate 2nd File and 3rd files in the 5-sample folder 
    * copy generate spreasheet
    * more simple json extraction (just first tample)
    * create new DirectortInputStrategy to "walk" folder and call other input strategy
    * Create DirectortInputStrategyTest and stub
    * Write and test behaviou

## Next tidy

* test cell conversion
    * Run other unit tests
        * any other unit test to try out specific conversation (search for Spreadhsheet convertor tess) ###
        * Unit test CellConvertorTest (contiguours ranes) - not returning values
    * Restore all integration tests
        * maybe run them independalty so a failure in one does not block all

* Decide
    * how to deploy
    * how to extend


## Next - Data Quality
* Simple Check rules
    * just enough rule to filter / check for dud data
    * may need to see if can write rule to remove all
* Cross reference back to data
    * add original cell refernece (need to track through as not currently accurate)

## Next - Deployment
* Can deploy and work against true docs
* Snapshot current code and make note for book readers
* dsl based on existing - working and sharable
* Read all docs and refresh
* Add note on book to here
* Resolve GitHub [firstpartners-net/red-piranha] pages build and deployment workflow run 
* Tests passing

## Next upgrade Doc pre release
* add 3 minute quickstart
    * Readme.md how to run the sample in VSCode online
* add about new and old book
* tidy off *notes*.md files
* update notes-release.md
* Carry out release
    * github
    * docker

## ##############

## Next Sprint - after immediate needs

* Future
    * Create Fiancial extraction Sample - analsyis of single case POC
    * multiple output options
    * decision test
* Improve Data Extraction
    * Where we "Ignoring name already exists" - update script to cope better

## Release
* Automate release (package and docker)
* Or at least update release notes
* do interim github and docker release
* Merge back into main, create new branch?

## ##############

## Tidy
* Clear down warning
* Figure out why current excel example is outputting incorrectly
* More info through to front end
* more tests compile / rule
* tidy of all readme pages
* view vulneribilty report (right click top right of tabs and renable)


## upgrades and housekeeping

* restore commented out tests (put @Test back on them)
    * testJSONInOut
* run JavaDoc checker and tidy comments
* Migrate to latest version of all libs
* tidy DSL example


# Medium Term Ideas
* better display of information in JSON
* better handling of invalid cell refs - function to remove invalid?
* Convert tesets using binary serizlied data to json (comment out in test constants)
* RP using model from business central or simliar exported backage
* Better display of Java objects - open by default
* Understand Github pages, Jekyll and see if can build an even 'friendlier' version of this site.
* Outline how to integrate with KIE Server


# Medium Term tidy
* Tidy Bootstrap on web page
* final tests running
* Serialsiation - Into Tests of convertor / Java test data in JSON
* Rengerate Javadoc, tidy to site
* recover and reused previous examples (to web) - currently in test folder
* Simplify down to Cells (remove all notion or ranges?)

