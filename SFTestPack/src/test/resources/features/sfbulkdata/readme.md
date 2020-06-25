**SFBulkData**

# What is SFBulkData?

Its a CLE Java tool used to upload records (Output of the ETL Export) to a salesforce Org. Exit codes determine the outcome of a run.

**Build**

Its a maven app which uses the Salesforce Bulk API via an enterprise connection.  All components are dynamically delivered from Maven the VSTS artifactory repository.

https://cle-crm.visualstudio.com/_git/SFBulkData?path=%2FReadme.md&version=GBdevelopment

**Test Scope**

Verify the response codes and provide test criteria required to test those scenarios. 
Implementation of these have superseeded Provar's data upload tests.
