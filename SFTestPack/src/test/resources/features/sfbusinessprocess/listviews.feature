@SFBusinessProcess

Feature: Tests around List Views


  @manual
  @issue:SAL-4515
  Scenario: The Recently Viewed Listview for Policy does not contain Policy Number
    Given I am on the Policies List page
    When I select the Recently Viewed List View
    Then I should not see the a Policy Number column

  @manual
  @issue:SAL-4349
  Scenario: The All Campaigns Listview exists for Campaign
    Given I am on the Campaigns List page
    Then the All Campaigns german:alle Kampagnen Listview exists
    And the Listview should display all campgaigns including inactive ones
