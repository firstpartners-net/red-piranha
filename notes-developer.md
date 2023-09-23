# Notes on extending Red Piranha as a developer

Red Piranha is aimed at 'Excel Power Users'. The information in this page goes 'under the covers' if you (as a Developer) are looking to understand and extend the project.

## Get, Build and Run the Code

* Checkout project as normal from GitHub
* Ensure you have Java installed on your machine. The current codebase has been tested against `JDK 11` (Open JDK from RedHat), but other (and earlier versions) should work.
* Install the `Maven` Build tool on your machine.
* Open a Command prompt in this directory containing `pom.xml`. Normally this is in the code directory
* Since the project contains `Spring Boot` you can start the embedded web server by typing `mvn spring-boot:run` in the command prompt.
* If you don't see any error messages open a web browser at `http://localhost:7000`and the Red Piranha home page should be displayed.

## Project Layout and Setup for Editing

* The project is based on Java, Spring and Maven, so many of the directories should be in the standard locations. JUnit tests are also included.
* The `mvn eclipse:eclipse` command (or equivalent for your favourite IDE ) will generate files for you to easily open and run in the editor.
* In general, the system is stateless and input files are **not** overwritten. This means you can run it (several times) and get the same results, or make changes without breaking too much. Output files might be deleted, but  they can always be regenerated.

## Key files

* `pom.xml` lists the dependencies for the project. Maven will auto-download these
* `application.properties` contains the values Spring will pass in as it auto-wires the project on startup. `Config.java` will hold many of these at runtime.
* `RedController.java` and `RedRestController.java`have Spring annotations, to it knows to pass any web requests to them.
* `RedModel.java` holds much of the information as it is passed around the system - the configuration, the input files, the rules to apply and the output once the rules has run.
* `RuleRunner.java` carries out the call to the Rule Engine. It is created using a factory method - so it will the appropriate implmentation of `ÌDocumentInStrategy.java` to import data from XLS, XSLX, Word etc. A similar pattern is for output files.
* Different implementations of `IStatusUpdate.java` allow us to log what is happening in th system / pass back user friendly updates.
* `Ìndex.html`is templated using Spring Thymeleaf - displays the output value back to the user.
* Samples are contained in `src/main/resources/examples`. 

### Design

Key design principles

* Robust simple
* As much Established frameowrks as possible
* integration and pre/post release tests (e.g. Selenumum)
* Examples easy to use

## Other Project Notes

* Running `main` method in `TestConstants.java regenerates the serialized test data
* Unit tests are contained in the `test` folder and give additional code samples.

## Red Piranha for Drools Developers

If you've used <http://www.jboss.org/drools> JBoss Drools, you'll be pretty familiar with most of the concepts. This sections explain what Red-Piranha does (and does not) do compared to JBoss Drools.

Most business users are more comfortable with Excel and the Web than with Java. Red-Piranha packages Java based Drools so that they can start writing and using business rules straight away. Red-Piranha allows them to put your rules and data in Excel, then call Drools in a simple package to do the hard work.

* Red Piranha uses <http://www.jboss.org/drools> as a key library. It's focus is more on Excel and Google Docs users looking to add the power of a rule engine to their documents.
* With Drools, you normally supply (1) The rules file (2) A Model (or data structure) holding the information that the rules act on (3) Java code to tie it all together.
* With Red-Piranha all you need is 1) the rules file. The Data structure is always excel, and there is standard Java code to combine the Rules and the excel spreadsheet. This makes it easier, although Drools gives you more flexibility (with a bit more work!).



