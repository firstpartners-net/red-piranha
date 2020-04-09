# Script to rebuild the Core Drools Libraries, copy then into /war/WEB-INF/lib
# By default, Red Piranha comes with the necessary modifications to run in Google App Engine.
#
# This script is only needed if you modify Drools Core / API code and need to update the Red Piranha Project
# More Info at http://code.google.com/p/red-piranha/wiki/ModifyDroolsRunInGoogleAppEngine

#Build the Core, copy to Red Piranha

#Old Location
##cd ~/projects/drools-5.1.1/drools-core

cd ~/projects/droolsjbpm/drools-core
mvn -Dmaven.test.skip=true install
cp target/drools-core-5.2.0-SNAPSHOT.jar ~/projects/red-piranha/war/WEB-INF/lib/drools-core-5.2.0-SNAPSHOT.jar

#Build the API, copy to Red Piranha

#Old Location
##cd ~/projects/drools-5.1.1/drools-api

cd ~/projects/droolsjbpm/drools-api
mvn -Dmaven.test.skip=true install
cp target/drools-api-5.2.0-SNAPSHOT.jar ~/projects/red-piranha/war/WEB-INF/lib/drools-api-5.2.0-SNAPSHOT.jar

#Return to start directory 
cd ~/projects/red-piranha

#Use Ant to deploy to Google App Engine
# ant -file ant-app-engine.xml

