# Red Piranha for Drools Develpers

If you've used http://www.jboss.org/drools JBoss Drools, you'll be pretty familiar with most of the concepts. This sections explain what Red-Piranha does (and does not) do compared to JBoss Drools.

Most business users are more comfortable with Excel and the Web than with Java. Red-Piranha packages Java based Drools so that they can start writing and using business rules straight away. Red-Piranha allows them to put your rules and data in Excel, then call Drools in a simple package to do the hard work.


  * Red Piranha uses http://www.jboss.org/drools as a key library. It's focus is more on Excel and Google Docs users looking to add the power of a rule engine to their documents.
  * Google App Engine (GAE) is a key deployment target, and enforces key limitations on the code; For examples rules cannot be 'compiled' in the normal Drools way, hence the offline package step, before you deploy.
  * With Drools, you normally supply (1) The rules file (2) A Model (or data structure) holding the information that the rules act on (3) Java code to tie it all together.
  * With Red-Piranha all you need is 1) the rules file. The Data structure is always excel, and there is standard Java code to combine the Rules and the excel spreadsheet. This makes it easier, although Drools gives you more flexibility (with a bit more work!).