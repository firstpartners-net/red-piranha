# Common Error Messages

Common error messages from Red Piranha and how to solve them

| **Error Message** | **Possible Cause and Solution** |
|:------------------|:--------------------------------|
| Unexpected exception from servlet: java.io.InvalidClassException: org.drools.impl.KnowledgeBaseImpl; | KnowledgeBase deployed may have been compiled on a different version of Drools. Recompile / Rebuild. May have to increment Version of application on Google App Engine |
| Security Exception when running RuleRunner (JNLP or Local script) | Make sure rule-runner.jar is signed |
| Unexpected Key Exception when running RuleRunner (JNLP or Local script)| Make sure no third-party manifest files included in rule-runner.jar |
| Description| log cannot be resolved , Rule Compilation error : [Rule name='sample of rule warnings cell to capture warnings'] |
| Filenotfound error| java.io.FileNotFoundException: Proposal-list.csv (The process cannot access the file because it is being used by another process)
| Configuration Errors| error|
| Null pointer in rules - where it expects a value but we don't pass it in| java.lang.NullPointerException|
| Description |  $cell cannot be resolved to a variable|





<!--stackedit_data:
eyJoaXN0b3J5IjpbMTQ3MjM2NDkwN119
-->