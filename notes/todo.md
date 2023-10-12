# Todo List

Yes, we should be using the GitHub issue tracker. But for this stage in development (multiple small improvements) a todo list works fine.

## Next - Multiple files

* NEXT 

     * try get unit test passing
     * try error handling loop against failing case
        * more etests
     * remove III from split strings
     * try Tests passing - any others I can make work (quick check, then move to spillover)
     * any "null" or error or CEcell values coming through - any any more I can do
     * push stable version


* NEXT RESOLVE

    * Cover
        - remove words EI and grant (search whole sheet)

    * Robustness

        * Upgrade poiCell -> RedCell conversion
            * debug CellConvertor in more detail
            * Unit test CellConvertorTest (contiguours ranes) - not returning values
        
        * Find some_user_log
        
        * Upgrade CellConvertorTest
            * getCellAsStringForceDateConversion (called from script support)
    

        * (DONE??) Upgrade RP Logging to show different documents (Y/N)
            * Flush this to disk
            * Remove input and output
            * make a note of this in readme.html
            * Review which items are log.info and which are log.debug (for when deploinging)

        * Look at DateFormatter - can it be used first
        * review @TODOs

    * MH
        * rule to prune cells with error

    * NTH
        * can I use rules to support the above
        * red -> Poi -> red test

        
        

## Next - Deployment snaphot (tag and docker)

* (Decide) Merge branch back into main (and create bugfix branch)


## Upgrade Doc
* add 3 minute quickstart (as first section)
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
* END OF MAIN PUSH
## ################

* Carry over from previous sprint
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


