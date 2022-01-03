# Red Piranha - Information for Developers

Red Piranha is aimed at 'Excel Power Users'. The information in this page goes 'under the covers' if you (as a Developer) are looking to understand and extend the project.
Here if you're a coder


What happens on startup
* trys to run using the config in application 
* further queries based on rest api


Standard Maven package layout
Standard Srping boot app

entry points ...


Spring end points ....
https://docs.spring.io/spring-boot/docs/2.6.2/reference/htmlsingle/#actuator.endpoints

http://localhost:7000/swagger-ui.html
http://localhost:7000/swagger-ui/

configuration ...

Logging ...

extending ...   






## Getting Started With Spring and Maven

* To run as spring boot applicaiton mvn spring-boot:run


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.2/maven-plugin/reference/html/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.6.2/reference/htmlsingle/#using-boot-devtools)

## Project Layout and useful files

* pom.xml
* Spring start point
## Other Project Notes

* Note that running main in TestConstants regenerates the serialized test data

# Red Piranha for Drools Developers

If you've used http://www.jboss.org/drools JBoss Drools, you'll be pretty familiar with most of the concepts. This sections explain what Red-Piranha does (and does not) do compared to JBoss Drools.

Most business users are more comfortable with Excel and the Web than with Java. Red-Piranha packages Java based Drools so that they can start writing and using business rules straight away. Red-Piranha allows them to put your rules and data in Excel, then call Drools in a simple package to do the hard work.


  * Red Piranha uses http://www.jboss.org/drools as a key library. It's focus is more on Excel and Google Docs users looking to add the power of a rule engine to their documents.
  * Google App Engine (GAE) is a key deployment target, and enforces key limitations on the code; For examples rules cannot be 'compiled' in the normal Drools way, hence the offline package step, before you deploy.
  * With Drools, you normally supply (1) The rules file (2) A Model (or data structure) holding the information that the rules act on (3) Java code to tie it all together.
  * With Red-Piranha all you need is 1) the rules file. The Data structure is always excel, and there is standard Java code to combine the Rules and the excel spreadsheet. This makes it easier, although Drools gives you more flexibility (with a bit more work!).
