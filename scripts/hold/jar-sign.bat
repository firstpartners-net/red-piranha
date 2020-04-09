REM  Run this command to sign the jar with your own key
REM http://java.sun.com/developer/Books/javaprogramming/JAR/sign/signing.html
REM
jarsigner -keystore ~/.keystore ../war/ruleplayer.lib/*.jar mykey 
