#Note region here is merely a sales region use to identify a matching record in csv structure via the region list
@SFLoadRecordTests

@notPreprod
@slow
@ignore

Feature: Generate Yaml files with current access settings for Directors assigned to regions


  Background:
    Given I have a salesforce connection


  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Regional Directors in Region Nord
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And the owner is in region "<Region>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_directors_in_regions.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Regional_Director_Nord_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                    | Region |
      | Account                      | BrokerAccount           | regdirnord@canadalife.ie | nord   |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | regdirnord@canadalife.ie | nord   |
      | Statement_Broker__c          | StatementBroker         | regdirnord@canadalife.ie | nord   |
      | Contract                     | Policy                  | regdirnord@canadalife.ie | nord   |
      | Commission_Payment__c        | CommissionPayments      | regdirnord@canadalife.ie | nord   |
      | Commission_Agreement__c      | CommissionAgreements    | regdirnord@canadalife.ie | nord   |
      | Agreement_Line_Item__c       | AgreementLineItem       | regdirnord@canadalife.ie | nord   |
      | Policy_Application__c        | PolicyApplication       | regdirnord@canadalife.ie | nord   |
      | IHK_Number__c                | IHKNumber               | regdirnord@canadalife.ie | nord   |
      | Target__c                    | Target                  | regdirnord@canadalife.ie | nord   |
      | Opportunity                  | Opportunity             | regdirnord@canadalife.ie | nord   |
      | OpportunityLineItem          | OpportunityLineItem     | regdirnord@canadalife.ie | nord   |
      | iSuite_Task__c               | ISuiteTask              | regdirnord@canadalife.ie | nord   |



  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Regional Directors in Region Bayern
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And the owner is in region "<Region>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_directors_in_regions.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Regional_Director_Bayern_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                      | Region |
      | Account                      | BrokerAccount           | regdirbayern@canadalife.ie | bayern |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | regdirbayern@canadalife.ie | bayern |
      | Statement_Broker__c          | StatementBroker         | regdirbayern@canadalife.ie | bayern |
      | Contract                     | Policy                  | regdirbayern@canadalife.ie | bayern |
      | Commission_Payment__c        | CommissionPayments      | regdirbayern@canadalife.ie | bayern |
      | Commission_Agreement__c      | CommissionAgreements    | regdirbayern@canadalife.ie | bayern |
      | Agreement_Line_Item__c       | AgreementLineItem       | regdirbayern@canadalife.ie | bayern |
      | Policy_Application__c        | PolicyApplication       | regdirbayern@canadalife.ie | bayern |
      | IHK_Number__c                | IHKNumber               | regdirbayern@canadalife.ie | bayern |
      | Target__c                    | Target                  | regdirbayern@canadalife.ie | bayern |
      | Opportunity                  | Opportunity             | regdirbayern@canadalife.ie | bayern |
      | OpportunityLineItem          | OpportunityLineItem     | regdirbayern@canadalife.ie | bayern |
      | iSuite_Task__c               | ISuiteTask              | regdirbayern@canadalife.ie | bayern |



  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Regional Directors in Region NRW
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And the owner is in region "<Region>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_directors_in_regions.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Regional_Director_NRW_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                   | Region |
      | Account                      | BrokerAccount           | regdirnrw@canadalife.ie | nrw    |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | regdirnrw@canadalife.ie | nrw    |
      | Statement_Broker__c          | StatementBroker         | regdirnrw@canadalife.ie | nrw    |
      | Contract                     | Policy                  | regdirnrw@canadalife.ie | nrw    |
      | Commission_Payment__c        | CommissionPayments      | regdirnrw@canadalife.ie | nrw    |
      | Commission_Agreement__c      | CommissionAgreements    | regdirnrw@canadalife.ie | nrw    |
      | Agreement_Line_Item__c       | AgreementLineItem       | regdirnrw@canadalife.ie | nrw    |
      | Policy_Application__c        | PolicyApplication       | regdirnrw@canadalife.ie | nrw    |
      | IHK_Number__c                | IHKNumber               | regdirnrw@canadalife.ie | nrw    |
      | Target__c                    | Target                  | regdirnrw@canadalife.ie | nrw    |
      | Opportunity                  | Opportunity             | regdirnrw@canadalife.ie | nrw    |
      | OpportunityLineItem          | OpportunityLineItem     | regdirnrw@canadalife.ie | nrw    |
      | iSuite_Task__c               | ISuiteTask              | regdirnrw@canadalife.ie | nrw    |



  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Regional Directors in Region Mitte
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And the owner is in region "<Region>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_directors_in_regions.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Regional_Director_Mitte_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                     | Region |
      | Account                      | BrokerAccount           | regdirmitte@canadalife.ie | mitte  |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | regdirmitte@canadalife.ie | mitte  |
      | Statement_Broker__c          | StatementBroker         | regdirmitte@canadalife.ie | mitte  |
      | Contract                     | Policy                  | regdirmitte@canadalife.ie | mitte  |
      | Commission_Payment__c        | CommissionPayments      | regdirmitte@canadalife.ie | mitte  |
      | Commission_Agreement__c      | CommissionAgreements    | regdirmitte@canadalife.ie | mitte  |
      | Agreement_Line_Item__c       | AgreementLineItem       | regdirmitte@canadalife.ie | mitte  |
      | Policy_Application__c        | PolicyApplication       | regdirmitte@canadalife.ie | mitte  |
      | IHK_Number__c                | IHKNumber               | regdirmitte@canadalife.ie | mitte  |
      | Target__c                    | Target                  | regdirmitte@canadalife.ie | mitte  |
      | Opportunity                  | Opportunity             | regdirmitte@canadalife.ie | mitte  |
      | OpportunityLineItem          | OpportunityLineItem     | regdirmitte@canadalife.ie | mitte  |
      | iSuite_Task__c               | ISuiteTask              | regdirmitte@canadalife.ie | mitte  |



  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Regional Directors in Region BAWU
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And the owner is in region "<Region>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_directors_in_regions.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Regional_Director_BAWU_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                    | Region |
      | Account                      | BrokerAccount           | regdirbawu@canadalife.ie | bawu   |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | regdirbawu@canadalife.ie | bawu   |
      | Statement_Broker__c          | StatementBroker         | regdirbawu@canadalife.ie | bawu   |
      | Contract                     | Policy                  | regdirbawu@canadalife.ie | bawu   |
      | Commission_Payment__c        | CommissionPayments      | regdirbawu@canadalife.ie | bawu   |
      | Commission_Agreement__c      | CommissionAgreements    | regdirbawu@canadalife.ie | bawu   |
      | Agreement_Line_Item__c       | AgreementLineItem       | regdirbawu@canadalife.ie | bawu   |
      | Policy_Application__c        | PolicyApplication       | regdirbawu@canadalife.ie | bawu   |
      | IHK_Number__c                | IHKNumber               | regdirbawu@canadalife.ie | bawu   |
      | Target__c                    | Target                  | regdirbawu@canadalife.ie | bawu   |
      | Opportunity                  | Opportunity             | regdirbawu@canadalife.ie | bawu   |
      | OpportunityLineItem          | OpportunityLineItem     | regdirbawu@canadalife.ie | bawu   |
      | iSuite_Task__c               | ISuiteTask              | regdirbawu@canadalife.ie | bawu   |



  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Regional Directors in Region Central
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And the owner is in region "<Region>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_directors_in_regions.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Regional_Director_Central_Sales_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                       | Region  |
      | Account                      | BrokerAccount           | regdircentral@canadalife.ie | central |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | regdircentral@canadalife.ie | central |
      | Statement_Broker__c          | StatementBroker         | regdircentral@canadalife.ie | central |
      | Contract                     | Policy                  | regdircentral@canadalife.ie | central |
      | Commission_Payment__c        | CommissionPayments      | regdircentral@canadalife.ie | central |
      | Commission_Agreement__c      | CommissionAgreements    | regdircentral@canadalife.ie | central |
      | Agreement_Line_Item__c       | AgreementLineItem       | regdircentral@canadalife.ie | central |
      | Policy_Application__c        | PolicyApplication       | regdircentral@canadalife.ie | central |
      | IHK_Number__c                | IHKNumber               | regdircentral@canadalife.ie | central |
      | Target__c                    | Target                  | regdircentral@canadalife.ie | central |
      | Opportunity                  | Opportunity             | regdircentral@canadalife.ie | central |
      | OpportunityLineItem          | OpportunityLineItem     | regdircentral@canadalife.ie | central |
      | iSuite_Task__c               | ISuiteTask              | regdircentral@canadalife.ie | central |




  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Regional Directors in Region Central
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And the owner is in region "<Region>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_directors_in_regions.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Regional_Director_RKA_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                   | Region       |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | regdirrka@canadalife.ie | regionale ka |
      | Account                      | BrokerAccount           | regdirrka@canadalife.ie | regionale ka |
      | Statement_Broker__c          | StatementBroker         | regdirrka@canadalife.ie | regionale ka |
      | Contract                     | Policy                  | regdirrka@canadalife.ie | regionale ka |
      | Commission_Payment__c        | CommissionPayments      | regdirrka@canadalife.ie | regionale ka |
      | Commission_Agreement__c      | CommissionAgreements    | regdirrka@canadalife.ie | regionale ka |
      | Agreement_Line_Item__c       | AgreementLineItem       | regdirrka@canadalife.ie | regionale ka |
      | Policy_Application__c        | PolicyApplication       | regdirrka@canadalife.ie | regionale ka |
      | IHK_Number__c                | IHKNumber               | regdirrka@canadalife.ie | regionale ka |
      | Target__c                    | Target                  | regdirrka@canadalife.ie | regionale ka |
      | Opportunity                  | Opportunity             | regdirrka@canadalife.ie | regionale ka |
      | OpportunityLineItem          | OpportunityLineItem     | regdirrka@canadalife.ie | regionale ka |
      | iSuite_Task__c               | ISuiteTask              | regdirrka@canadalife.ie | regionale ka |



  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Regional Directors in Region Central
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And the owner is in region "<Region>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_directors_in_regions.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Regional_Director_CoOperation_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                     | Region          |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | regdir_coop@canadalife.de | kooperationen 1 |
      | Account                      | BrokerAccount           | regdir_coop@canadalife.de | kooperationen 1 |
      | Statement_Broker__c          | StatementBroker         | regdir_coop@canadalife.de | kooperationen 1 |
      | Contract                     | Policy                  | regdir_coop@canadalife.de | kooperationen 1 |
      | Commission_Payment__c        | CommissionPayments      | regdir_coop@canadalife.de | kooperationen 1 |
      | Commission_Agreement__c      | CommissionAgreements    | regdir_coop@canadalife.de | kooperationen 1 |
      | Agreement_Line_Item__c       | AgreementLineItem       | regdir_coop@canadalife.de | kooperationen 1 |
      | Policy_Application__c        | PolicyApplication       | regdir_coop@canadalife.de | kooperationen 1 |
      | IHK_Number__c                | IHKNumber               | regdir_coop@canadalife.de | kooperationen 1 |
      | Target__c                    | Target                  | regdir_coop@canadalife.de | kooperationen 1 |
      | Opportunity                  | Opportunity             | regdir_coop@canadalife.de | kooperationen 1 |
      | OpportunityLineItem          | OpportunityLineItem     | regdir_coop@canadalife.de | kooperationen 1 |
      | iSuite_Task__c               | ISuiteTask              | regdir_coop@canadalife.de | kooperationen 1 |

