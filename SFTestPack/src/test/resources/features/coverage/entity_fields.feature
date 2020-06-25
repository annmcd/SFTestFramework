@TestCoverage

@ignore

@issue:SAL-4335
Feature: Entities-Indicate where Entities show gaps in relation to Test Coverage


  @ignore
  Scenario: Verify that all custom Entities are being tested
    Given I have a saleforce connection
    When I provide the custom CLE Entities I am verifying field checks on
      | Agreement_Line_Item__c              |
      | Broker_Consultant_Account__c        |
      | CampaignEventSessionRegistration__c |
      | CampaignEventSlot__c                |
      | CampaignEventSession__c             |
      | CampaignMember                      |
      | Commission_Agreement__c             |
      | Commission_Payment__c               |
      | Competitors__c                      |
      | Gotowebinarrequestjob__c            |
      | Gotowebinarsession__c               |
      | IHK_Number__c                       |
      | iSuite_Task__c                      |
      | Orgcontrolpanel__c                  |
      | Policy_Application__c               |
      | Policy_Contact_Relationship__c      |
      | Region__c                           |
      | SalesReportTarget__c                |
      | SalesReport__c                      |
      | Statement_Broker__c                 |
      | Strategic_Data__c                   |
      | Target__c                           |

    Then I expect that list to contain all of the custom entities in salesforce

  @issue:SAL-4470
  Scenario Outline: Verify that all custom fields are entered for testing in Entity Yaml Files
    Given I have a saleforce connection
    And I have Yaml Configfile "<yamlConfigFile>" for an Entity "<entityName>"
    Then I expect boolean fields to be compared
    And I expect all fields to have helptext and descriptions
    And I expect all the custom fields to be compared for datatypes, labels and delabels
    And I expect all failures to be logged

    Examples:
      | yamlConfigFile                           | entityName                          |
      | Account.yaml                             | Account                             |
      | AccountTeamMember.yaml                   | AccountTeamMember                   |
      | AccountContactRelation.yaml              | AccountContactRelation              |
      | Agreement_Line_Item__c.yaml              | Agreement_Line_Item__c              |
      | Broker_Consultant_Account__c.yaml        | Broker_Consultant_Account__c        |
      | Campaign.yaml                            | Campaign                            |
      | CampaignEventSessionRegistration__c.yaml | CampaignEventSessionRegistration__c |
      | CampaignEventSlot__c.yaml                | CampaignEventSlot__c                |
      | CampaignEventSession__c.yaml             | CampaignEventSession__c             |
      | CampaignMember.yaml                      | CampaignMember                      |
      | Commission_Agreement__c.yaml             | Commission_Agreement__c             |
      | Commission_Payment__c.yaml               | Commission_Payment__c               |
      | Competitors__c.yaml                      | Competitors__c                      |
      | Contact.yaml                             | Contact                             |
      | Contract.yaml                            | Contract                            |
      | IHK_Number__c.yaml                       | IHK_Number__c                       |
      | Individual.yaml                          | Individual                          |
      | ISuite_Task__c.yaml                      | iSuite_Task__c                      |
      | Lead.yaml                                | Lead                                |
      | Opportunity.yaml                         | Opportunity                         |
      | OpportunityLineItem.yaml                 | OpportunityLineItem                 |
      | Policy_Application__c.yaml               | Policy_Application__c               |
      | Policy_Contact_Relationship__c.yaml      | Policy_Contact_Relationship__c      |
      | Pricebook2.yaml                          | Pricebook2                          |
      | PricebookEntry.yaml                      | PricebookEntry                      |
      | Product2.yaml                            | Product2                            |
      | Region__c.yaml                           | Region__c                           |
      | Statement_Broker__c.yaml                 | Statement_Broker__c                 |
      | Strategic_Data__c.yaml                   | Strategic_Data__c                   |
      | SalesReport__c.yaml                      | SalesReport__c                      |
      | SalesReportTarget__c.yaml                | SalesReportTarget__c                |
      | Target__c.yaml                           | Target__c                           |
      | User.yaml                                | User                                |



