# Todo List

Yes, we should be using the GitHub issue tracker. But for this stage in development (multiple small improvements) a todo list works fine.

## NEXT Book Samples

* What book samples

### Aim of Next Sprint

Platform (with existing examples) and good draft of user and dev documentation.

* Can hand to Java developer and build
* Could be run by simple end-user
    * (e.g. (with Docker or Native) - )

    

## Next

* Example for simple hellow world
  * 1st sample to "Hello world"
  * look at moving over private samples as basis for book

* LATER
  * understand model back and forward better
  * trigger exception, make sure it displays ok
  * tidy of readme x2
  * conversion to-from excel - make sure it works better, json better?
  * Dive through stack - can bring back any more info to user
  * Tidy Bootstrap on page
  * tests running
  * RP using model from business central
  * Move bootstrap offline (and other javascript to make more robust)
  * tidy DSL example
  * show hide sample
  * Serialsiation - Into Tests of convertor / Java test data in JSONu
  * use to trest cell conversion
  * samples - url to view input, out and rule files
    * or note how to do this
  * do interim github and docker release
  * Better display of Java objects
  * Read drools doc in more detail
  * Outline how to integrate with KIE Server
  * Review / tidy Javadoc
  * Add Spring MVC Tests
  * Review update dependencies (in Pom.xml)

## Todo Next 

* Figure out why current excel example is outputting incorrectly
* More info through to front end
* more tests compile / rule - currently 

* Understand Github pages, Jekyll and see if can build an even 'friendlier' version of this site.
* (another) review and tidy of two readme pages

* what features needed for book examples - Possible
    * Extract Named ranges from Excel
    * Choice of colours to update (?)



## Todo Later

* Maven site - document rp code?
* Update red-piranha-sourceforge.com to point to github (grab info first)
* Enable Swagger
* Turn back on Spring Services in applicaiton.properites



TIDY THESE

NEED FOM RP
	• Aim Book: Intro to Rules, link to other relevant tech (me), onboarding to project
	• Aim RP Robust, easily deployable app (e.g. Docker image, link to online Jboss tools / images) allowing excel user to run rules
	•  Examples and docs to support this 
		○ Next steps in /code/todo.md - Examples to form basis for this
		○ Link to Sandbox / Business Central image provided by JBoss
	
TODO BOOK
	
	• NEXT : 
	• Read Docs - understand features and examples, esp busienss centraal that I can leverage 
		○ Drools
		○ Kogito
		○ JPBM
		○ Business Central
			§ Deploy to remote servers
		○ Github
			§ https://github.com/kiegroup/
		○ Spring Boot / Docker / Native
		○ Maven
		○ Other close tabs
		
	• Make notes against Kie sandbox
		○ https://learn-dmn-in-15-minutes.com/
			§ https://sandbox.kie.org/#/7249f3bf-3a51-4eb1-930f-6d681746b2a4/file/Untitled.dmn
			§ Any other on this site?
		○ Possible OpenShift Tutorial - https://github.com/eschabell/openshift-bpmsuite-workshop
	
	• First pass through other books and vdeos
		Squizz other drools books (get from z?)
		Youtube Key: https://www.youtube.com/c/KIE-community-channel/videos
	• Poke through Business Centraal
		○ Examples
		○ Loading a new example
		○ Loading a new model
		○ Can I just post JSON?
	• Second pass through docs
		○ Map to where I explain docs in book
	• Initial investigation of tech samples needed / spec of RP needed to tie to exisitng Jboss compontnes (eg Kogito sandbox)
		○ 2nd pass Jboss Workbench  / business ctrnal and other parts needed
		○ Spec (below and todo.md) Version of RP to support examples in the book
	• Next level pass through mappings (table below)
	• Review other key points / tech I want to hit on for SSP 
		○ AI
		○ PMML
		
	• Understand 
		○ Does it help deploy
		○ Any extra features
	• Possible topics
		○ Scorecards
		○ Optaplanner
	• Landscape
		○ Azure Logic apps
		○ Other windows solutions in this areas
			§ Biztalk
			§ Powersuite
	• Red Piranha version of Drools samples
		○ Use rules to map over
	• Other Material
		○ Work through other book (to Google docs)
		○ Work though youtube video


QUESTIONS
	• What new chapter headings - Update below
	• What I have have to learn (e.g. AI, azure, ..)
	• What samples needed for each chapter
	• What RP needed to support (or how much can be done with KIE/Drools)
	• Wider tech understanding - another pass - but note for later
	• State of community and ecosystem - anything I'm missing?

HOLD BUT SOONISH
LATER
	• Review chapters (and switch around for better flow)
	• Investigate more on Biztalk
	• Position RP as example into Drools
	• Mark Proctor - forward but also tie into Red Hat / IBM
	• How tie this into learning course
	• create book2 landing page for github
	• Reachout Again  Mark Proctor (when understand community and existing roadmap)
	
	
	
		
			

Current Tech Example needed
	• Docker version of Jboss / Wildfly / Business Centraal - 
		○ CONFIG
			§ How to use GUI to configure share (local, then Network)
		○ EXTRA  - extend
			§ Code to watch for excel loaded, then run rules (Red Piranha)
	• Sample that can be loaded by user into this
	• Excel sheet to tie to existing sample (e.g. Mortgage application)
	• Power Automoate flow sample
		○ Watches for onedrive file chagnes
		○ Runs flow
	• Testing
		○ ??
	• AI
		○ ??
	• Possible
		○ Calling Drools from Python
	• SAMPLEt
		○ Docker (for use in main book)
		○ Azure (Plan B?)
			§ Possible use of App Service
		○ Intructions (Plan C - give to tech department)
			§ How to set everything up from first principles
		○ Red Hat Tooling (based on Eclipse)
		

Update table below
	• Straight run through on docker local examples
Move Azure / other options to appendix