@SFLoadRecords


@notPreprod
@slow
@ignore

Feature: Generate Yaml files with current access settings for Broker Consultants


  Background:
    Given I have a salesforce connection



  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Broker Consultants in Region Nord
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And the owner is in region "<Region>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_broker_consultant.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Broker_Consultant_Nord_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                | Region |
      | Account                      | BrokerAccount           | bcnord@canadalife.ie | nord   |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | bcnord@canadalife.ie | nord   |
      | Statement_Broker__c          | StatementBroker         | bcnord@canadalife.ie | nord   |
      | Contract                     | Policy                  | bcnord@canadalife.ie | nord   |
      | Commission_Payment__c        | CommissionPayments      | bcnord@canadalife.ie | nord   |
      | Commission_Agreement__c      | CommissionAgreements    | bcnord@canadalife.ie | nord   |
      | Agreement_Line_Item__c       | AgreementLineItem       | bcnord@canadalife.ie | nord   |
      | Policy_Application__c        | PolicyApplication       | bcnord@canadalife.ie | nord   |
      | IHK_Number__c                | IHKNumber               | bcnord@canadalife.ie | nord   |
      | Target__c                    | Target                  | bcnord@canadalife.ie | nord   |
      | Opportunity                  | Opportunity             | bcnord@canadalife.ie | nord   |
      | OpportunityLineItem          | OpportunityLineItem     | bcnord@canadalife.ie | nord   |
      | iSuite_Task__c               | ISuiteTask              | bcnord@canadalife.ie | nord   |



  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Broker Consultants in Region Bayern
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And the owner is in region "<Region>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_broker_consultant.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Broker_Consultant_Bayern_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                  | Region |
      | Account                      | BrokerAccount           | bcbayern@canadalife.ie | bayern |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | bcbayern@canadalife.ie | bayern |
      | Statement_Broker__c          | StatementBroker         | bcbayern@canadalife.ie | bayern |
      | Contract                     | Policy                  | bcbayern@canadalife.ie | bayern |
      | Commission_Payment__c        | CommissionPayments      | bcbayern@canadalife.ie | bayern |
      | Commission_Agreement__c      | CommissionAgreements    | bcbayern@canadalife.ie | bayern |
      | Agreement_Line_Item__c       | AgreementLineItem       | bcbayern@canadalife.ie | bayern |
      | Policy_Application__c        | PolicyApplication       | bcbayern@canadalife.ie | bayern |
      | IHK_Number__c                | IHKNumber               | bcbayern@canadalife.ie | bayern |
      | Target__c                    | Target                  | bcbayern@canadalife.ie | bayern |
      | Opportunity                  | Opportunity             | bcbayern@canadalife.ie | bayern |
      | OpportunityLineItem          | OpportunityLineItem     | bcbayern@canadalife.ie | bayern |
      | iSuite_Task__c               | ISuiteTask              | bcbayern@canadalife.ie | bayern |



  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Broker Consultants in Region NRW
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And the owner is in region "<Region>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_broker_consultant.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Broker_Consultant_NRW_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner               | Region |
      | Account                      | BrokerAccount           | bcnrw@canadalife.ie | nrw    |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | bcnrw@canadalife.ie | nrw    |
      | Statement_Broker__c          | StatementBroker         | bcnrw@canadalife.ie | nrw    |
      | Contract                     | Policy                  | bcnrw@canadalife.ie | nrw    |
      | Commission_Payment__c        | CommissionPayments      | bcnrw@canadalife.ie | nrw    |
      | Commission_Agreement__c      | CommissionAgreements    | bcnrw@canadalife.ie | nrw    |
      | Agreement_Line_Item__c       | AgreementLineItem       | bcnrw@canadalife.ie | nrw    |
      | Policy_Application__c        | PolicyApplication       | bcnrw@canadalife.ie | nrw    |
      | IHK_Number__c                | IHKNumber               | bcnrw@canadalife.ie | nrw    |
      | Target__c                    | Target                  | bcnrw@canadalife.ie | nrw    |
      | Opportunity                  | Opportunity             | bcnrw@canadalife.ie | nrw    |
      | OpportunityLineItem          | OpportunityLineItem     | bcnrw@canadalife.ie | nrw    |
      | iSuite_Task__c               | ISuiteTask              | bcnrw@canadalife.ie | nrw    |



  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Broker Consultants in Region Mitte
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And the owner is in region "<Region>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_broker_consultant.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Broker_Consultant_Mitte_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                 | Region |
      | Account                      | BrokerAccount           | bcmitte@canadalife.ie | mitte  |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | bcmitte@canadalife.ie | mitte  |
      | Statement_Broker__c          | StatementBroker         | bcmitte@canadalife.ie | mitte  |
      | Contract                     | Policy                  | bcmitte@canadalife.ie | mitte  |
      | Commission_Payment__c        | CommissionPayments      | bcmitte@canadalife.ie | mitte  |
      | Commission_Agreement__c      | CommissionAgreements    | bcmitte@canadalife.ie | mitte  |
      | Agreement_Line_Item__c       | AgreementLineItem       | bcmitte@canadalife.ie | mitte  |
      | Policy_Application__c        | PolicyApplication       | bcmitte@canadalife.ie | mitte  |
      | IHK_Number__c                | IHKNumber               | bcmitte@canadalife.ie | mitte  |
      | Target__c                    | Target                  | bcmitte@canadalife.ie | mitte  |
      | Opportunity                  | Opportunity             | bcmitte@canadalife.ie | mitte  |
      | OpportunityLineItem          | OpportunityLineItem     | bcmitte@canadalife.ie | mitte  |
      | iSuite_Task__c               | ISuiteTask              | bcmitte@canadalife.ie | mitte  |



  Scenario Outline: Generate Yaml structure for object types owned or directly referenced by Broker Consultants in Region BAWU
    Given Records are uploaded
    And an owner "<Owner>" owns records for type "<Type>" entityType "<EntityType>"
    And I have defined a File with all the accounts "Salesforce_UserJobs.yaml" that I wish to print the access levels for
    And the owner is in region "<Region>"
    And The connection is valid
    And the recordData is held in "CSVRecordData_broker_consultant.yaml"
    And the csv structure which defines the data types is held in "CSV_Structure.yaml"
    When I trigger generation of the records to create "Broker_Consultant_Bawu_Access_1.yaml"
    Then I that file should exist and be serialisable against the structure required
    Examples:
      | Type                         | EntityType              | Owner                | Region |
      | Account                      | BrokerAccount           | bcbawu@canadalife.ie | bawu   |
      | Broker_Consultant_Account__c | BrokerConsultantAccount | bcbawu@canadalife.ie | bawu   |
      | Statement_Broker__c          | StatementBroker         | bcbawu@canadalife.ie | bawu   |
      | Contract                     | Policy                  | bcbawu@canadalife.ie | bawu   |
      | Commission_Payment__c        | CommissionPayments      | bcbawu@canadalife.ie | bawu   |
      | Commission_Agreement__c      | CommissionAgreements    | bcbawu@canadalife.ie | bawu   |
      | Agreement_Line_Item__c       | AgreementLineItem       | bcbawu@canadalife.ie | bawu   |
      | Policy_Application__c        | PolicyApplication       | bcbawu@canadalife.ie | bawu   |
      | IHK_Number__c                | IHKNumber               | bcbawu@canadalife.ie | bawu   |
      | Target__c                    | Target                  | bcbawu@canadalife.ie | bawu   |
      | Opportunity                  | Opportunity             | bcbawu@canadalife.ie | bawu   |
      | OpportunityLineItem          | OpportunityLineItem     | bcbawu@canadalife.ie | bawu   |
      | iSuite_Task__c               | ISuiteTask              | bcbawu@canadalife.ie | bawu   |
