@SFBusinessProcess

Feature: CLE Events Portal page - event and session capacity.


  @manual
  Scenario: ConfirmRegistration & IsRestrictedCampaign fields do not affect the registration form
    Given the following Campaign exists:
      | field                     | value                   |
      | Campaign Name             | event-restricted-conReg |
      | Active                    | true                    |
      | Type                      | Workshop                |
      | Confirm Registration      | true                    |
      | Is Restricted Campaign    | true                    |
    When I open the registration page for the "event-restricted-conReg" campaign
    Then I should see the registration form with the expected basic fields

  @manual
  Scenario: Registration is closed for a Campaign with negative event capacity
    Given an active workshop event named "event-negativeCapacity" exists with capacity "-1" and no shared common name
    When I open the registration page for the "event-negativeCapacity" campaign
    Then I should see the Event Booked Out message

  @manual
  Scenario: Registration is closed for a Campaign with Zero event capacity
    Given an active workshop event named "event-zeroCapacity" exists with capacity "0" and no shared common name
    When I open the registration page for the "event-zeroCapacity" campaign
    Then I should see the Event Booked Out message

  @manual
  Scenario: Registration is open for a Campaign with blank event capacity
    Given an active workshop event named "event-blankCapacity" exists with blank capacity and no shared common name
    When I open the registration page for the "event-blankCapacity" campaign
    Then I should see the registration form with the expected basic fields

  @manual
  Scenario: Registration is open for a Campaign with 1 capacity
    Given an active workshop event named "event-oneCapacity" exists with capacity "1" and no shared common name
    When I open the registration page for the "event-oneCapacity" campaign
    Then I should see the registration form with the expected basic fields


  @manual
  Scenario: Registration is open for a Campaign where event capacity has not been reached
    Given an active workshop event named "event-capacityNotYetReached" exists with capacity "3" and no shared common name
    And a new user has registered for the "event-capacityNotYetReached" campaign
    And a new user has registered for the "event-capacityNotYetReached" campaign
    When I open the registration page for the "event-capacityNotYetReached" campaign
    Then I should see the registration form with the expected basic fields

  @manual
  Scenario: Registration is closed for a Campaign where event capacity has been reached
    Given an active workshop event named "event-capacityReached" exists with capacity "3" and no shared common name
    And a new user has registered for the "event-capacityReached" campaign
    And a new user has registered for the "event-capacityReached" campaign
    And a new user has registered for the "event-capacityReached" campaign
    When I open the registration page for the "event-capacityReached" campaign
    Then I should see the Event Booked Out message
