@SFBusinessProcess

@notPreprod
Feature: Testing creation of Campaign Event Slots

  Scenario: Creating a Campaign Event Slot with no campaign
    When I create a Campaign Event Slot named "Banana" with no campaign ID
    Then an error occurs due to the following validation messages:
      | field             | message                                    |
      | Campaign__c       | Required fields are missing: [Campaign__c] |
