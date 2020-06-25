**Salesforce Standard and Custom Objects**


# What are salesforce entities?

They represent the underlying data model upon which the CLE Salesforce Application is built. The model is made up of standard and custom objects. Each entities configuration is specified declaratively using YAML. A java model has been written for the entities from which the YAML configs are serialised. All functionality is delivered as part of SFTestpPack

**Test Scope**

*The following attributes are verified via a declarative YAML specification for each entity for, which a yaml specification has been defined.*
- Datatype
- English Labels
- German Labels
- German picklist values
- Mandatory Attributes
- Attributes that should be removed (Should be cleared at the end of a release)

**How do I generate Yaml Files which show the expected test results?**


The automated build includes a profile to generate updated yaml files based on settings defined for each entity in src/main/resources/SFEntities.xlsx of the module SFConfigManager
The tab names in the spreadsheet should be of the same name of the entities defned in src/main/resourcesSFEntities.xlsx
cmd GenerateEntityYaml.bat "Comma delimited list of entity names"

**i.e** _GenerateEntityYaml.bat "Account,Agreement_Line_Item__c"_
Yaml Files are created in src/test/resources as a temporary location with a .1.yaml suffix


- Yaml Files under SFTestPack src/test/resources/entityDefs, are actually the expected results for the suite EntityConfigTests.java

- Each file relates  to an entity and is named accordingly

- The SFConfigManager src/main/resources/SFEntities.xls file, is the master reference for each Entity Yaml File. Each entities details are specified on a separate tab.

# Test the Yaml Generation

Backup the existing entity yaml file ins sftestpack/src/test/resources/entitydefs. and copy the newly generated file into this folder to replace it, taking care to remove the .1 suffix.

Execute RunSuite.cmd EnityConfigTests.java 

Execute RunSuite.cmd CoverageTests.java 

The CoverageTests.java suite should not contain missing entries for the entity you have tested.

 

In a given sprint the Test Engineer should maintain a register of Yaml Entities that have changed and replace the
corresponding definition in src/test/resources/entityDefs with the newly generated spreadsheet.
The Expected tests results are ultimately formulated by the Owner of the spreadsheet and thus it is their responsibilty
for the expected test result values.
The Automated Test Engineer is ultimately responsible for ensuring that those changes are incorporated into the SFTestPack module.
Results must be verified on a feature branch for no broken changes before creating a Pull Request for their merge into the _development_ branch

