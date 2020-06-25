@SFBusinessProcess

@notPreprod
Feature: Testing creation of Campaign Event Slots

  Scenario: Creating a Campaign Event Session with no Campaign Event Slot
    When I create a Campaign Event Session named "Apple" with no campaign event slot ID
    Then an error occurs due to the following validation messages:
      | field                | message                                             |
      | CampaignEventSlot__c | Required fields are missing: [CampaignEventSlot__c] |
