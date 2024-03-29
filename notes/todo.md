# Todo List

Yes, we should be using the GitHub issue tracker. But for this stage in development (multiple small improvements) a todo list works fine.


* Branch notes
    * RC2 = active development
    * RC1 = bugfix and stable

* NEXT 

    * NTH
        * rule to prune cells with error
        * red -> Poi -> red test

    
## Next - Deployment snaphot (tag and docker)


## Upgrade Doc
* update 3 minute quickstart (as first section)
* tidy off *notes*.md files
* get book images working (as link)


## Update release docs
* update notes-release.md and use to improve notes
* Carry out release and update notes (first pass)
    * github
    * docker
* Tidy release instructions (second pass)

## Release
* Or at least update release notes
* do interim github and docker release


## ################
* END OF THIS PUSH
## ################

* Carry over from previous sprint

    * Robustness 2nd phase

        * Upgrade poiCell -> RedCell conversion
            * debug CellConvertor in more detail
            * Unit test CellConvertorTest (contiguours ranes) - not returning values
        
        * Upgrade CellConvertorTest
            * getCellAsStringForceDateConversion (called from script support)


        * Again 
            * Look at DateFormatter - can it be used first
            * review @TODOs
            * try get unit test passing


        * Move CDN to local (Or move this back?) for computers without internet access

* GitHub build
    * build 

* Rules 
    * being loaded each run - can we load once and reuse (caching)
    * check code - might already be implemented

* Test Data
    * update paths to put in some test folder (currently in root of examples)

* Deprecation errors resolve
    * Update CSV Output


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
* Figure out why excel->excel example is outputting incorrectly
* More info through to front end
* more tests compile / rule
* tidy of all readme pages
* view vulneribilty report (right click top right of tabs and renable)


