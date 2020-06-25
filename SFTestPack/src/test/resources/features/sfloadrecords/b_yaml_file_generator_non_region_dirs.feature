#UserRole is the job suffix in RoleList of CSVStructure
@SFLoadRecords

@notPreprod
@slow
@ignore
Feature: Generate Yaml files with current access settings for Regional Directors


  Background:
    Given I have a salesforce connection


  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by ZKA 1
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And The owner is identifyable by UserRole "<UserRole>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_directors_non_region.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Director_ZKA1_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner              | UserRole |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | zka1@canadalife.de | ZKA 1    |
      | Account                      | BrokerAccount           | zka1@canadalife.de | ZKA 1    |
      | Statement_Broker__c          | StatementBroker         | zka1@canadalife.de | ZKA 1    |
      | Contract                     | Policy                  | zka1@canadalife.de | ZKA 1    |
      | Commission_Payment__c        | CommissionPayments      | zka1@canadalife.de | ZKA 1    |
      | Commission_Agreement__c      | CommissionAgreements    | zka1@canadalife.de | ZKA 1    |
      | Agreement_Line_Item__c       | AgreementLineItem       | zka1@canadalife.de | ZKA 1    |
      | Policy_Application__c        | PolicyApplication       | zka1@canadalife.de | ZKA 1    |
      | IHK_Number__c                | IHKNumber               | zka1@canadalife.de | ZKA 1    |
      | Target__c                    | Target                  | zka1@canadalife.de | ZKA 1    |
      | Opportunity                  | Opportunity             | zka1@canadalife.de | ZKA 1    |
      | OpportunityLineItem          | OpportunityLineItem     | zka1@canadalife.de | ZKA 1    |
      | iSuite_Task__c               | ISuiteTask              | zka1@canadalife.de | ZKA 1    |


  Scenario Outline: Generate Yaml structure for object types owned or  directly referenced by ZKA 2
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And The owner is identifyable by UserRole "<UserRole>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_directors_non_region.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Director_ZKA2_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner              | UserRole |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | zka2@canadalife.de | ZKA 2    |
      | Account                      | BrokerAccount           | zka2@canadalife.de | ZKA 2    |
      | Statement_Broker__c          | StatementBroker         | zka2@canadalife.de | ZKA 2    |
      | Contract                     | Policy                  | zka2@canadalife.de | ZKA 2    |
      | Commission_Payment__c        | CommissionPayments      | zka2@canadalife.ie | ZKA 2    |
      | Commission_Agreement__c      | CommissionAgreements    | zka2@canadalife.de | ZKA 2    |
      | Agreement_Line_Item__c       | AgreementLineItem       | zka2@canadalife.de | ZKA 2    |
      | Policy_Application__c        | PolicyApplication       | zka2@canadalife.de | ZKA 2    |
      | IHK_Number__c                | IHKNumber               | zka2@canadalife.de | ZKA 2    |
      | Target__c                    | Target                  | zka2@canadalife.de | ZKA 2    |
      | Opportunity                  | Opportunity             | zka2@canadalife.de | ZKA 2    |
      | OpportunityLineItem          | OpportunityLineItem     | zka2@canadalife.de | ZKA 2    |
      | iSuite_Task__c               | ISuiteTask              | zka2@canadalife.de | ZKA 2    |


  Scenario Outline: Generate Yaml structure for object types owned or  directly referenced by ZKA 3
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And The owner is identifyable by UserRole "<UserRole>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_directors_non_region.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Director_ZKA3_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner               | UserRole |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | zka3@canadalife.de  | ZKA 3    |
      | Account                      | BrokerAccount           | zka3@canadalife.de  | ZKA 3    |
      | Statement_Broker__c          | StatementBroker         | zka3@canadalife.de  | ZKA 3    |
      | Contract                     | Policy                  | zka3@canadalife.de  | ZKA 3    |
      | Commission_Payment__c        | CommissionPayments      | zka3@canadalife.de  | ZKA 3    |
      | Commission_Agreement__c      | CommissionAgreements    | zka3@canadalife.de  | ZKA 3    |
      | Agreement_Line_Item__c       | AgreementLineItem       | zka3@canadalife.de  | ZKA 3    |
      | Policy_Application__c        | PolicyApplication       | zzka3@canadalife.de | ZKA 3    |
      | IHK_Number__c                | IHKNumber               | zka3@canadalife.de  | ZKA 3    |
      | Target__c                    | Target                  | zka3@canadalife.de  | ZKA 3    |
      | Opportunity                  | Opportunity             | zka3@canadalife.de  | ZKA 3    |
      | OpportunityLineItem          | OpportunityLineItem     | zka3@canadalife.de  | ZKA 3    |
      | iSuite_Task__c               | ISuiteTask              | zka3@canadalife.de  | ZKA 3    |


  Scenario Outline: Generate Yaml structure for object types owned ordirectly referenced by Admin
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And The owner is identifyable by UserRole "<UserRole>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_directors_non_region.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Director_Admin_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                     | UserRole |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | admin_user@canadalife .de | Admin    |
      | Account                      | BrokerAccount           | admin_user@canadalife .de | Admin    |
      | Statement_Broker__c          | StatementBroker         | admin_user@canadalife .de | Admin    |
      | Contract                     | Policy                  | admin_user@canadalife .de | Admin    |
      | Commission_Payment__c        | CommissionPayments      | admin_user@canadalife .de | Admin    |
      | Commission_Agreement__c      | CommissionAgreements    | admin_user@canadalife .de | Admin    |
      | Agreement_Line_Item__c       | AgreementLineItem       | admin_user@canadalife .de | Admin    |
      | Policy_Application__c        | PolicyApplication       | admin_user@canadalife .de | Admin    |
      | IHK_Number__c                | IHKNumber               | admin_user@canadalife .de | Admin    |
      | Target__c                    | Target                  | admin_user@canadalife .de | Admin    |
      | Opportunity                  | Opportunity             | admin_user@canadalife .de | Admin    |
      | OpportunityLineItem          | OpportunityLineItem     | admin_user@canadalife .de | Admin    |
      | iSuite_Task__c               | ISuiteTask              | admin_user@canadalife .de | Admin    |




  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Director Sales
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And The owner is identifyable by UserRole "<UserRole>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_directors_non_region.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Director_Sales_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                        | UserRole |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | sales_director@canadalife.de | Sales    |
      | Account                      | BrokerAccount           | sales_director@canadalife.de | Sales    |
      | Statement_Broker__c          | StatementBroker         | sales_director@canadalife.de | Sales    |
      | Contract                     | Policy                  | sales_director@canadalife.de | Sales    |
      | Commission_Payment__c        | CommissionPayments      | sales_director@canadalife.de | Sales    |
      | Commission_Agreement__c      | CommissionAgreements    | sales_director@canadalife.de | Sales    |
      | Agreement_Line_Item__c       | AgreementLineItem       | sales_director@canadalife.de | Sales    |
      | Policy_Application__c        | PolicyApplication       | sales_director@canadalife.de | Sales    |
      | IHK_Number__c                | IHKNumber               | sales_director@canadalife.de | Sales    |
      | Target__c                    | Target                  | sales_director@canadalife.de | Sales    |
      | Opportunity                  | Opportunity             | sales_director@canadalife.de | Sales    |
      | OpportunityLineItem          | OpportunityLineItem     | sales_director@canadalife.de | Sales    |
      | iSuite_Task__c               | ISuiteTask              | sales_director@canadalife.de | Sales    |
