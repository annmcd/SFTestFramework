**SFLoadRecords**


![Canada Life Europe](../assets/cle_logo.png)

# What does SFLoadRecords Suite do?

This is a utility Suite used to load the records necessary for the SFRecordAccess Feature
It also generates the accessor settings for those records i.e the expected security settings
on the records when accessed by others.
Note: Its vitally important that records generated match the expected test records  
**Implementation**

- Records need to be uploaded in the target environment, which is provided for in feature file *"a_load_records.feature"*
The test uploads records defined in CSVRecordData_<job(s)> to a target salesforce org
**Approach to creating Records**
The SFRecord access Test Suite will test records from a job ownership stand point. i.e if I work in JOB X then for records
that I OWN, all other CLE Jobs will have their access settings verified against records I own

### Generating and Loading Records into Salesforce? ###

Records are generated and loaded into salesforce via the SFLoadRecords Suite using the 
feature a_load_records.feature

Records are prepared using rules which comprise a minnimal data set in order to create records in Salesforce via 
an upsert, using the database constraints. Only records that are owned by representative CLE Jobs should be
created. Objects uploaded by integration user are not generated.
All configuration files required for record generation are in src/test/resources/recordAccessDefs

There are 3 Scenarios in the feature file _a_load_records.feature_, one each for records relating to

* Broker Consultants who own records
* Regional Directors who own records
* Other Directors who own records and are not Regional Directors ****



####Salesforce_UserJobs.yaml ####
All accessors of these records are listed in the file Salesforce_UserJobs.yaml

#### CSV_Structure.yaml ####
Regions listed indicate that a record is created for Broker Consultants and regional directors assigned to those
Regions, e.g 
* Broker Consultant
* Regional Director bawu
Its a given that Regional Directors and Broker Consultants are in the regions specified in 
the regionList/region fragments. Rules are expected for each Broker Consultant and each Regional Director
under the regions listed, and should be created in 
CSVRecordData_broker_consultant.yaml and CSVRecordData_directors_in_regions.yaml
Other Directors that create records are listed under userRoleList and are all assumed to
have the prefix "Director "
```
etlEntity: BrokerConsultantAccount
     type: Broker_Consultant_Account__c
     csv: BrokerConsultantAccount.csv
     header: BrokerConsultantAccountId__c,User:Owner.Username,Region__r.regionID__c
     regionList:
       - { region: nord}
       - { region: bayern}
       - { region: nrw}
       - { region: bawu}
       - { region: mitte}
     userRoleList:
       - { userRole: Director Sales}
       - { userRole: ZKA 1}
       - { userRole: ZKA 2}
       - { userRole: ZKA 3}
       - { userRole: Admin}
```
#### Creation/Modification of Rules ####
Rules pertaining to the creation of records are defined in the files below, the all have the same Yaml
format
1. CSVRecordData_broker_consultant.yaml
1. CSVRecordData_directors_in_regions.yaml
1. CSVRecordData_directors_non_region.yaml


##### Example taken from  CSVRecordData_broker_consultant.yaml #####
The generator will parse this file, and generate a CSV File "BrokerConsultantAccount.csv" for the region identified with the suffix
The Column/Headers/header pairs indicate the csv column name and pair values for the records
the $environment indcates the salesforce sandbox/org environment
CSV Files are created in /TempFiles

### Generating Expected Results in yaml format from a salesforce org ###
The owner yaml files are created using b_yaml_file_generator*.feature files
**Note:** The generator adds a --- to the end of the generated feature files and this should be removed
before running the record access tests. The output of each generated file, should also be checked to ensure that
settings are generated for each Entity


```
lookupName: BrokerConsultantAccount.nord
whereConditions:
  - {condition: "Region__r.regionID__c=1" }
  - {condition: "Owner.Username = 'bcnord@canadalife.ie.$environment'"}
columnHeaders:
  - { header: "BrokerConsultantAccountId__c=20191"}
  - { header: "User:Owner.Username=bcnord@canadalife.ie.$environment"}
  - { header: "Region__r.regionID__c = 1"}
```

_Note:_ WhereConditions: are used by the Access Settings Generator part of this test framework to find the records
which we need to generate access settings for.
Generating Access Settings, is something that should be done once only. After that use incremental generation
managed by the b_yaml_file_generator* files.  

### Verify SFBulkData Configuration ##
<env>_sfbulkdata_config.yaml contains all the configuration settings used to upload the records. Ensure that
the file contains the correct order for the CSV file upload and that the external ids, entity names
etc are all correct.


### Triggering Record Upload ###
_a_load_records.feature_ contains the 3 scenarios. 
Uncomment the scenario you need to execute but do not execute all 3 together, unless changes required reflect
all 3.
 ```
RunSuite SFLoadRecords.java 
 ```
