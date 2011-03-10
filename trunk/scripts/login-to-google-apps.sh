#!/bin/bash
#
# Call Google Appcfg tool - normally this is done automatically by the (Google supplied) Ant Script
# But we need to do this manually every 24 hours (then ant will work ok again)
#
# Deploys to http://red-piranha.appspot.com/
#
# Path to file below will likely vary on your system
#
export APP_ENGINE_ECLIPSE_PLUGIN_DIR="/home/paul/software/eclipse_3_5/plugins/com.google.appengine.eclipse.sdkbundle.1.4.0_1.4.0.v201012021500/appengine-java-sdk-1.4.0/"
echo "using Google app engine at"
echo $APP_ENGINE_ECLIPSE_PLUGIN_DIR


#Use the App engine code to upload war (and force login)
$APP_ENGINE_ECLIPSE_PLUGIN_DIR/bin/appcfg.sh update war