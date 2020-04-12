# Common Error Messages #
Common error messages from Red Piranha and how to solve them

| **Error Message** | **Possible Cause and Solution** |
|:------------------|:--------------------------------|
| Unexpected exception from servlet: java.io.InvalidClassException: org.drools.impl.KnowledgeBaseImpl; | KnowledgeBase deployed may have been compiled on a different version of Drools. Recompile / Rebuild. May have to increment Version of application on Google App Engine |
| Security Exception when running RuleRunner (JNLP or Local script) | Make sure rule-runner.jar is signed |
| Unexpected Key Exception when running RuleRunner (JNLP or Local script)| Make sure no third-party manifest files included in rule-runner.jar |