@SFLeadAndEvent

@ignore

Feature: Lead and Event Form Validation and Salesforce Behaviour when Campaign Id is entered

  Background:
    Given I Know the form fields to submit a lead successfully


  Scenario Outline: A lead is auto submitted in the same manner as that created by a user
    And I have a known environment
    And I launch the form url "<URL"
    And I enter the field "<field>" value "<value>" pairs
    And I enter a valid campaign id "<campaign id>"
    When I click submit
    Then I get redirected to the landing page "<landingPage>" with url "<url>"
    Examples:
      | field     | value      |
      | fieldName | fieldValue |


  Scenario: Lead Submitted within the one hour time lag
    Given An hour has not yet elapsed.
    When I query the lead object
    Then The owner is set to Integration user
    And IsConverted is set to false
    And the lead is linked to the campaign via the campaignId for a matching "<campaignStatus>"


  Scenario: Lead Submitted and an hour has elapsed
    Given An hour has elapsed.
    When I query the lead object
    Then I expect the owner to be set to Integration user
    And IsConverted is set to true
    And ConvertedAccountId is populated with the correct accountId
    And the ConvertedContactId is populated with the correct ContactId



   Scenario: I want to verify form validation for all Lead form fields
    Given I have a url "<URLToForm>" for Lead Creation
#    And A vvid has been entered - there is no VVID on the lead form
    When I enter Lead Data successfully and click submit
    And I expect a lead to be created (Timelag between creation of lead and triggering process is 1 hour)
    And a process will be started called "<AutoConvertLead>"
    And The VVID and the Lead Id exist on the lead record
    And A contact which matches the vvid is found in salesforce
    And A salesforce Flow named "Auto Convert Leads>" is called with ActionName  "<callLeadConversionFlow">
    And a Contact.VVIDNumber__c exists which matches the vvid on the lead (varVVID in flow)
    And I have the contact.Id and Contact.AccountId from the contact found
    Then I expect and apex class to be triggered which will calls ConverLeadAction( Which is out of the box ConvertLeadAction)
    And I expect that the lead is related to the contact via the ConvertedContactId
    And No new opportunity is created.
