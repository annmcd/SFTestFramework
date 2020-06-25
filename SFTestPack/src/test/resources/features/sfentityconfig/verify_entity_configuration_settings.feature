@SFEntityConfig



Feature: Entity-Salesforce Configuration
  Description: Verify that entities are configured as required in a salesforce org

  @issues:SAL-4788,SAL-3508,SAL-4396
  Scenario Outline: NEW-Verify that Entity Metadata settings are as required
    Given I have a connection to salesforce Org in a given environment
    And I have generated the yaml file "<EntityYamlDef>" for entity "<EntityName>"
    When I trigger a compare expected against actual values in salesforce
    Then I expect them to match
    Examples:
      | EntityYamlDef                            | EntityName                          |
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
      | Event.yaml                               | Event                               |
      | GoToWebinarRequestJob__c.yaml            | GoToWebinarRequestJob__c            |
      | GoToWebinarSession__c.yaml               | GoToWebinarSession__c               |
      | IHK_Number__c.yaml                       | IHK_Number__c                       |
      | Individual.yaml                          | Individual                          |
      | ISuite_Task__c.yaml                      | iSuite_Task__c                      |
      | Lead.yaml                                | Lead                                |
      | Opportunity.yaml                         | Opportunity                         |
      | OpportunityLineItem.yaml                 | OpportunityLineItem                 |
      | OrgControlPanel__c.yaml                  | OrgControlPanel__c                  |
      | Policy_Application__c.yaml               | Policy_Application__c               |
      | Policy_Contact_Relationship__c.yaml      | Policy_Contact_Relationship__c      |
      | Pricebook2.yaml                          | Pricebook2                          |
      | PricebookEntry.yaml                      | PricebookEntry                      |
      | Product2.yaml                            | Product2                            |
      | Region__c.yaml                           | Region__c                           |
      | SalesReport__c.yaml                      | SalesReport__c                      |
      | SalesReportTarget__c.yaml                | SalesReportTarget__c                |
      | Statement_Broker__c.yaml                 | Statement_Broker__c                 |
      | Strategic_Data__c.yaml                   | Strategic_Data__c                   |
      | Target__c.yaml                           | Target__c                           |
      | User.yaml                                | User                                |