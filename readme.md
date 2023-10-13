
![Red Piranha Logo](/site/images/top/02.gif)

# More powerful Excel, Word and Office Documents using Business Rules

Excel is great for collecting data. But once things get more complicated, you may be asking "what next"?

* Your Excel calculations are good. Maybe too good - now you can't follow what is going on. Wouldn't something a little bit clearer be better?
* Are you sharing your spreadsheets? What if somebody changes a formula without you noticing?
* Your business absolutely needs to be sure the rules are being applied - and you need a way of checking what those rules are later.
* You need to extract information from Excel to feed into Machine Learning pipelines, but have no easy, robust way to do it.

Powered by [KIE from IBM and RedHat Drools](https://incubator.apache.org/clutch/kie.html), Red-Piranha allows you to state your Excel business rules in a simple **when ... then** format that everybody can understand.  It acts as a bridge between Excel / Microsoft Office and Business Rules and workflow Engine (including AI and Planner) from Red Hat.

Instead of having your knowledge 'disappear' into complicated formulas that you can't read, a Rules Engine keeps things in a simple 'English' like format. Even better, because of the way it works 'under the covers' it is faster and more powerful than code translated into a technical language.

## Books Featuring Red Piranha

While you don't need the books to use Red Piranha (or vice versa), both talk about the same problem; Rules Based AI, and applying these business rules to your Excel Data. <a href="https://www.amazon.com/stores/Paul-Browne/author/B007MCQ55I?ref=ap_rdr&store_ref=ap_rdr&isDramIntegrated=true&shoppingPortalEnabled=true">Both are from Packt Publishing.</a>.

![Drools Business Rules](https://github.com/firstpartners-net/red-piranha/blob/main/images/book_drools.jpg)
![AI and Business Rules for Excel Power Users](https://github.com/firstpartners-net/red-piranha/blob/main/images/ai_book.jpg)

## What is a Business Rule

A business rule is a simple statement that represents some important aspect of a business. By capturing the rules for your business --the logic that governs its operation-- you gain the ability to create systems fully aligned with your business needs.

All people have business rules. Examples of a 'real' busniess rules that can be fed into the engine are:

* `When the market rises by more than 25%, Then sell the stock`
* `When the total is less than €100 And the claimant is low risk Then transfer the funds`

Because business rules can be written in 'near English' language they are much more meaningful to non-technical domain experts. In this format, the knowledge they contain is easier to manage, maintain and share. They also carry the advantage of separating the meaning of the business from the actual technical implementation.

## What is Red Piranha

By default, KIE and Drools doesn't read in Excel Data. Red Piranha fills in this gap with:

* A user Interface to run the rules from your desktop / web browser
* Conversion of your Excel Data into a format the rule engine can read.
* Applies Rules based AI to your Excel Data.
* Conversion of the outout back into a (different) Excel file or other format.
* Samples to get your started.
* Information to show you what is going on within the Rule Engine.

## Main features 

**Main Screenshot Before**
![Red Piranha Screenshot](/images/main-screenshot-before.jpg)


* View in your favourite browser
* Running on Localhost (your local machine)
* Current Settings - shows the current input file, the rules being applied to it, the output file.
* Samples to help you get started.
* Progress messages - did the rules run successfully?
* More detailled messages from within the Rule Engine
* Information from Input files - what our co
nversion from Excel into Java Objects looks like.
* Information output - what the information coming out of the rule engine looks like before we save it as Excel.


## 3 minute start - Running the project online in GitHub Codespaces

It is also possible to run the project in GitHub Code spaces.
1. Click on the blue `code` button above
1. Select the `Codespaces` tab.
1. Select the button to `Create Codespace on main`
## Running the project on your laptop 

The Project follows a standard structure, using the Spring Boot Framework and the Maven build tool. So if you're a Java developer, it's pretty easy for you to download and run.

1. Download and install Java
1. Download and install Apache Maven
1. Download (or checkout) the code from this GitHub Repository, unzip it if needed.
1. Open the `code` folder within this project in a console / terminal. You'll know you're in the correct place if you see a `pom.xml` file. 
1. Start the application using  `mvn spring-boot:run`
1. Open a Browser and point at `http://localhost:7000/`

Samples are integrated into the page at this link - so take a look at them as a next step.



Once the VSCode Editor and Terminal is open in your browser, follow the laptop instructions above from step 3.

## Running in Docker
If you're into Docker, a packaged example is available on Dockerhub.
https://hub.docker.com/repository/docker/paulbrowne/redpiranha

When running the image, map port 7000, so that you can connect your browser to the application running within docker.

The docker build is not yet automated, so could be slightly (or a lot) behind the code in this repository.
## Samples

Opening the Red Piranha webpage (normally at `http://localhost:7000/`) will show a list of ready to go samples.

Each of the samples has a readme.html to explain what the sample is showcasing. If you want to open in your editor, these are located in `code/src/main/resources/examples` . 

[Link to this folder on GitHub](https://github.com/firstpartners-net/red-piranha/tree/main/code/src/main/resources/examples/1_hello_world)

1. [Sample 1 - a simple hello world](https://github.com/firstpartners-net/red-piranha/tree/main/code/src/main/resources/examples/1_hello_world)
1. [Sample 2 - slightly more sophisticated example with rule based decision making](https://github.com/firstpartners-net/red-piranha/tree/main/code/src/main/resources/examples/2_chocolate-factory)
1. [Sample 3 - making decisions on Excel data using a graphical decision model (DMN) ](https://github.com/firstpartners-net/red-piranha/tree/main/code/src/main/resources/examples/3_simple_dmn)
1. [Sample 4 - Complex Excel document, extracting data using named ranges and rule based validation, output to a single CSV file](https://github.com/firstpartners-net/red-piranha/tree/main/code/src/main/resources/examples/4_complex_excel)
1. [Sample 5 - Multiple Excel documents in one folder, extracting data using named ranges and rule based validation, output to a single csv file](https://github.com/firstpartners-net/red-piranha/tree/main/code/src/main/resources/examples/5_multi_excel)

**After running**
The top part of screen will alter - showing the base directory, input file, business rules, DSL (Language mapping) and output file used.
![Red Piranha Screenshot](/images/main-screenshot-after.jpg)


The bottom part of screen will also update - it will show messages as the sample runs, and a snapshot of the data before and after the business rules (to allow you to write your rules).
![Red Piranha Screenshot](/images/main-screenshot-after-2.jpg)

## Running command line only

* For longer running examples (e.g. Example 5) it is sometimes better to run them __without__ the web interface.
* Passing the example number into the application will run the 
example as listed in `èxamples.json` . 
* For example
  `mvn spring-boot:run -Dspring-boot.run.arguments=--examples=5` will run the fith sample.
  







