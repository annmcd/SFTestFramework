**SFRecordAccess**


![Canada Life Europe](../assets/cle_logo.png)

# What do SFRecordAccess Suites do?

These tests verify the level of access to Records in a Salesforce Org.
They are driven by the Users CLE Job definition which comprises a Role, Profile and one or more Permissions sets or Groups.



**Note** Record access verification is carried out using test accounts which are only available in NON Production Salesforce Orgs owning to licensing restrictions.
A utility feature *b_yaml_file_generator.feature* can be used to generate once of definitions of the record access settings in a target organisation. Settings are generated for the users defined in Salesforce_UsersJobs.yaml against a lookup in *CSVRecordData_<jobfunction>.yaml* and produces a yaml file for the access if each user (users specified in Salesforce_UsersJob.yaml) acccess settings, per owner and entity type defined in the feature file scenario outline.

**Practical Scenaio**
e.g  If My job is "Broker Consultant" I need to know that  
I have the required level of access to all of the used entity types in the system, and I need to ensure that others access to my records is correct.

**Implementation**

- Records need to be uploaded in the target environment, which is provided for in feature file *"a_load_records.feature"*
The test uploads records defined in CSVRecordData_<job(s)> to a target salesforce org
- Access to all records per job function is verified via tests defined in feature files job_accessor_access_ownedby_<job(s>.feature






*Note: A feature file is used to define the expected settings, and a yaml file used to define the expected results*

