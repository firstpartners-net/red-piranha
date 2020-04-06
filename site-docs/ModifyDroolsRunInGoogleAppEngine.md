  * [Modifications to Third Party Jars](#Modifications_to_Third_Party_Jars.md)
  * [Modifications to allow Drools to run in Google App Engine](#Modifications_to_allow_Drools_to_run_in_Google_App_Engine.md)

# Modifications to Third Party Jars #

The following Jars have signatures / manifest that interfere with the signing of RulePlayer.jar
Versions that are checking in have the following changes made;

_TODO_ Go through and doc libs missing

  * 


# Modifications to allow Drools to run in Google App Engine #

By default Red Piranha comes with modified Drools libraries needed to run within Google app engine.
If you want a copy the libs can be downloaded from these links, based on Drools 5.1.1 M1
  * [Drools Core](http://red-piranha.googlecode.com/svn/trunk/war/WEB-INF/lib/drools-core-5.1.1.jar)
  * [Drools Api](http://red-piranha.googlecode.com/svn/trunk/war/WEB-INF/lib/drools-api-5.1.1.jar)


An important point is that Red Piranha currently **builds** the rules on your own PC, and the pre-built package of rules is what deployed / runs in GAE (Google App Engine). The reason for the 2 steps is that GAE does not like some of the Dynamic reflection in the MVEL Libraries (used by Drools) during the rule building.


Below lists these modifications if you need to replicate them.

  * Download Drools Source  http://www.jboss.org/drools/ and extract to folder.
    * Alternative is to get the source via GIT, see Drools 'Source' Documentation
  * Check out the Red Piranha code [(instructions here)](http://code.google.com/p/red-piranha/wiki/DevDeveloperGettingStarted#Checking_out_the_code).
  * Script (build-drools.sh) expects Drools source to be in paralel folder to Red Piranha (but you can edit)
    * e.g. source/red-piranha and source/drools\_5\_1\_1
  * Make sure you have Apache Maven installed
  * open command prompt to red-piranha folder
  * Type (no quotes) './build-drools.sh' to start script
  * Instructions are for unix, but easily modified for windoes


## Specific Changes Made ##

**Drools Core
```
 AbstractRuleBase.java // line 265 surrounded by try / catch 
      this.config = (RuleBaseConfiguration) droolsStream.readObject();
```
```
 RuleBaseConfiguration.java // - line 985 surround by try / catch
	 this.classLoader = ClassLoaderUtil.getClassLoader
```
```
 RuleBaseConfiguration.java // line 395  surround by try / catch
     this.chainedProperties = new ChainedProperties( "rulebase.conf",
```
```
 RuleBaseConfiguration.java // line 457 surround by try / catch, default Conflict Resolver
	  setConflictResolver( determineConflictResolver( this.chainedProperties.getProperty( "drools.conflictResolver", 
```**


**Drools Util**

```
 ChainedProperties.java // line 98 - exception check around ClassLoader.getSystemClassLoader();
```
```
 ChainedProperties.java // line 125 - exception check around ClassLoader.getSystemClassLoader();
```
```
 ChainedProperties.java // line 160 
	- null check for properties /key and return default
```
```
 ChainedProperties.java // line 240 - surround by try / catch for securityaccessexception
    if ( file != null && file.exists() ) {
```
```
 ChainedProperties.java // line 270 - surround by try / catch for securityaccessexception
    if ( file != null && file.exists() ) {
```
```
 ClassFieldInspector.java // line 166 - 
	 return "/" + clazz.getCanonicalName() + ".class";
```
```
 ClassLoaderUtil.java // line 25 - surround by try / catch for securityaccessexception
     ClassLoader systemClassLoader = 
```