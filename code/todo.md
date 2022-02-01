# Todo List

Yes, we should be using the GitHub issue tracker. But for this stage in development (multiple small improvements) a todo list works fine.

##


Next: 
* Run one samples as integration test in java
* Run all Samples as integration test (in json / Java)


* tidy html display and UI
    * line by line display of logs (loop in th)
    * if samples empty, display link to main page "Reset and view sameples)
    * Tidy current settings fields
    * samples - url to view input, out and rule files
    * Better json like dispaly as tree
    * Dive through stack - can bring back any more info to user

Try running file in docker


READ JSON - UNIT TEST
* unit test loading
* Serialsiation - Into Tests of convertor / Java test data in JSON
* use to trest cell conversion



LATER
* Move bootstrap offline
* tidy DSL example
* show hide sample

BOOK NOTES
* csv as part of a chain

## Aim of Next Sprint



Platform (with existing examples) and good draft of user and dev documentation.

* Can hand to Java developer and build
* Could be run by simple end-user
    * (e.g. (with Docker or Native) - )
    * NTH - auto maven build on Githib 
* Maven build jar - to try out object model on business central

* Move Later
    * Find More info on docker
    * from book, spring docker name and firsrpatners
    * push image to docker hub

    

### Design

Set in place key design principles

* Robust simple
* As much est as possible
* integration and pre/post release tests (e.g. Selenumum)
* Examples easy to use

## To understand

* Understand different kie objects
* add logging around KIE (to file, but also debug listener)
* WorkingMemoryEventListener ->  RuleRuntimeEventListener for new cells - wokring?
* Understand bootstrap and different layout options

## Next Step

* Example Template (for chocolate rules)
    * Standard format
	* Automatically gets picked up by RP Index.html
	* Config to turn off?
	* readme.md
* Maven build (Java Model)

## Todo Next 
- [ ] rules Steps (break out compile)
- [ ] Figure out why current excel example is outputting incorrectly
- [ ] More info through to front end
- [ ] Tidy front end
- [ ] more tests compile / rule - currently 
- [ ] Tests compiling Testing: 55 tests, 2 Errors, 2 failures
- [ ] look at moving over private samples
- [ ] Integrate test examples into application (can be run from standard deployment) and UI upgrade to faciltiate.
- [ ] Understand Github pages, Jekyll and see if can build an even 'friendlier' version of this site.
- [ ] (another) review and tidy of two readme pages


## Todo Soon

* Better display of Java objects
* Read drools doc in more detail
* Initial Docker Build (just RP in container)
* Extended Docker Compose (Make Kie Server and Business Central available) OR link to kie services/ online
* Outline how to integrate with KIE Server
* split out compile steps (at from end)
* Review / tidy Javadoc
* Add Spring MVC Tests
* Add Integration Tests
* Review update dependencies (in Pom.xml)
* upgrade display of Java objects (e.g. before and after Model) to be more user friendly

## Todo Book Samples

* What book sample (to be integrated into standard deploy)
* what features needed for book examples - Possible
    * Extract Named ranges from Excel
    * Choice of colours to update (?)

## Todo Later

* Maven site - document rp code?
* Update red-piranha-sourceforge.com to point to github (grab info first)
* Enable Swagger
* Turn back on Spring Services in applicaiton.properites