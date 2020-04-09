
#!/bin/bash
# Run this command to sign the jar with your own key
# http://java.sun.com/developer/Books/javaprogramming/JAR/sign/signing.html
#
jarsigner -keystore ~/.keystore red-player.jar mykey 
