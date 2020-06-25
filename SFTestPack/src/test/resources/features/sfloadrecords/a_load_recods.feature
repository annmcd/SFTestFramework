@SFLoadRecords

#@slow
#@ignore


@slow
@ignore
Feature:Salesforce Generate Records required for testing user record access



  Scenario: Verify that records required for testing records created by Broker Consultants with assigned regions have been uploaded to salesforce
    Given The Data Definition Yaml File "CSVRecordData_broker_consultant.yaml" with record data required for record creation exist
    And The owners are assigned to a region
    And The CSV structure Yaml File "CSV_Structure.yaml" exists
    And SFBulkData executes to create those records
    Then the upload is successful with exit code 0



  Scenario: Verify that records required for testing records created by Directors assigned to regions
    Given The Data Definition Yaml File "CSVRecordData_directors_in_regions.yaml" with record data required for record creation exist
    And The owners are assigned to a region
    And The CSV structure Yaml File "CSV_Structure.yaml" exists
    And SFBulkData executes to create those records
    Then the upload is successful with exit code 0



  Scenario: Verify that records required for testing records created by  Regional Directors not assigned to regions have been uploaded to salesforce
    Given The Data Definition Yaml File "CSVRecordData_directors_non_region.yaml" with record data required for record creation exist
    And The Directors are distinguishable by their UserRole
    And The CSV structure Yaml File "CSV_Structure.yaml" exists
    And SFBulkData executes to create those records
    Then the upload is successful with exit code 0