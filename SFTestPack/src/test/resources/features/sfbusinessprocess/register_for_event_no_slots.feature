@SFBusinessProcess

Feature: Event registrations, where events have no slots.

  @manual
  Scenario: Registration link can use Campaign Name
    Given an active workshop event named "Active-woCapacity-woCommon" exists with blank capacity and no shared common name
    When I open the registration page for the "Active-wCapacity-woCommon" campaign using Campaign Name
    Then I should see the registration form

  @manual
  Scenario: Registration link can use Campaign ID
    Given an active workshop event named "Active-woCapacity-woCommon" exists with blank capacity and no shared common name
    When I open the registration page for the "Active-wCapacity-woCommon" campaign using Campaign ID
    Then I should see the registration form

  @manual
  #Derived from R2-S5
  Scenario: The workshop and Campaign ID in Registration Form should be automatically set, and unchangeable
    Given an active workshop event named "Active-wCapacity-woCommon" exists with capacity "20" and no shared common name
    When I open the registration page for the "Active-wCapacity-woCommon" campaign
    Then there should be "1" workshop selection options
    Then the workshop "Active-wCapacity-woCommon" should be automatically selected
    And the campaign ID hidden field should be populated with the campaign ID of "Active-wCapacity-woCommon"

  @manual
  #Derived from R2-S5
  Scenario: A registration form with set capacity and no shared common name can be submitted
    Given an active workshop event named "Active-wCapacity-woCommon" exists with capacity "20" and no shared common name
    And I open the registration page for the "Active-wCapacity-woCommon" campaign
    When I submit the registration form for the "Active-wCapacity-woCommon" campaign with the following details:
    | field        | value            |
    | salutation   | Mr               |
    | company name | Apple            |
    | first name   | bob              |
    | last name    | taylor           |
    | mobile phone | 012345678        |
    | email        | <uniqueTestMail> |
    | accept pp    | true             |
    Then I should see a please wait message
    And I should see a registration confirmation message for the "Active-w-Capacity-woCommon" event
    And I should receive an Event Registration email
    And a Lead record should be created with details corresponding to the registration
    And the Lead record should remain in the lead queue
    And a Campaign Member record should be created with details corresponding to the Lead
    And the status of the Campaign Member record should be "Registered"

  @manual
  #Derived from R2-S6
  Scenario: A registration form with set capacity and a shared common name can be submitted
    Given an active workshop event named "Active-wCapacity-wCommon1" exists with capacity "20" and shared common name "shared-s6"
    Given an active workshop event named "Active-wCapacity-wCommon2" exists with capacity "20" and shared common name "shared-s6"
    And I open the registration page for the "Active-wCapacity-wCommon1" campaign
    When I submit the registration form for the "Active-wCapacity-wCommon1" campaign with the following details:
      | field        | value            |
      | salutation   | Mr               |
      | company name | Apple            |
      | first name   | bob              |
      | last name    | taylor           |
      | mobile phone | 012345678        |
      | email        | <uniqueTestMail> |
      | accept pp    | true             |
    Then I should see a please wait message
    And I should see a registration confirmation message for the "Active-w-Capacity-wCommon1" event
    And I should receive an Event Registration email
    And a Lead record should be created with details corresponding to the registration
    And the Lead record should remain in the lead queue
    And a Campaign Member record should be created with details corresponding to the Lead
    And the status of the Campaign Member record should be "Registered"

  @manual
  #Derived from R2-S7
  Scenario: Support message displayed on registration page if Campaign is inactive
    Given an inactive workshop event named "inActive Workshop" exists with capacity "20" and no shared common name
    When I open the registration page for the "inActive Workshop" campaign
    Then the registration form should not be displayed
    And I should see the registration link invalid message

  @manual
  #Derived from R2-S8
  Scenario: A registration form with blank capacity and no shared common name can be submitted
    Given an active workshop event named "Active-woCapacity-woCommon" exists with blank capacity and no shared common name
    And I open the registration page for the "Active-wCapacity-woCommon" campaign
    When I submit the registration form for the "Active-wCapacity-woCommon" campaign with the following details:
      | field        | value            |
      | salutation   | Mr               |
      | company name | Apple            |
      | first name   | bob              |
      | last name    | taylor           |
      | mobile phone | 012345678        |
      | email        | <uniqueTestMail> |
      | accept pp    | true             |
    Then I should see a please wait message
    And I should see a registration confirmation message for the "Active-w-Capacity-woCommon" event
    And I should receive an Event Registration email
    And a Lead record should be created with details corresponding to the registration
    And the Lead record should remain in the lead queue
    And a Campaign Member record should be created with details corresponding to the Lead
    And the status of the Campaign Member record should be "Registered"

  @manual
  #Derived from R2-S9 (A - capacity reached naturally)
  Scenario: Registration blocked for an event with no capacity remaining (capacity reached) and no shared common name
    Given an active workshop event named "Communications Workshop" exists with capacity "1" and no shared common name
    And a new user has registered for the "Communications Workshop" campaign
    When I open the registration page for the "Communications Workshop" campaign
    Then the registration form should not be displayed
    And I should see Event Fully Booked message

  @manual
  #Derived from R2-S9 (B - capacity reached due to capcity reduction)
  Scenario: Registration blocked for an event with no capacity remaining (capacity reduced) and no shared common name
    Given an active workshop event named "Communications Workshop" exists with capacity "5" and no shared common name
    And a new user has registered for the "Communications Workshop" campaign
    And I set the capacity of the "Communications Workshop" campaign to "1"
    When I open the registration page for the "Communications Workshop" campaign
    Then the registration form should not be displayed
    And I should see Event Fully Booked message

  @manual
  #Derived from R2-S10 (workshop selection options)
  Scenario: Registration blocked for an event with no capacity remaining (capacity reached) and a shared common name
    Given an active workshop event named "Communications Common A" exists with capacity "1" and shared common name "share10"
    Given an active workshop event named "Communications Common B" exists with capacity "1" and shared common name "share10"
    And a new user has registered for the "Communications Common A" campaign
    When I open the registration page for the "Communications Common A" campaign
    Then there should be "2" workshop selection options
    And the workshop "Communications Common B" should be automatically selected
    And the workshop "Communications Common A" should be visible but disabled
    And the campaign ID hidden field should be populated with the Campaign ID of "Communications Common B"

  @manual
  #Derived from R2-S10 (alternate workshop registration)
  Scenario: Registration for alternative workshop with a shared common name
    Given an active workshop event named "Communications Common A" exists with capacity "1" and shared common name "share10"
    Given an active workshop event named "Communications Common B" exists with capacity "1" and shared common name "share10"
    And a new user has registered for the "Communications Common A" campaign
    When I open the registration page for the "Communications Common A" campaign
    And I submit the registration form for the "Communications Common B" campaign with the following details:
      | field        | value            |
      | salutation   | Mr               |
      | company name | Apple            |
      | first name   | bob              |
      | last name    | taylor           |
      | mobile phone | 012345678        |
      | email        | <uniqueTestMail> |
      | accept pp    | true             |
    Then I should see a please wait message
    And I should see a registration confirmation message for the "Communications Common B" event
    And I should receive an Event Registration email
    And a Lead record should be created with details corresponding to the registration
    And the Lead record should remain in the lead queue
    And a Campaign Member record should be created with details corresponding to the Lead
    And the status of the Campaign Member record should be "Registered"

  @manual
  #Derived from R2-S11
  Scenario: Registration for both common events blocked if neither has capacity remaining
    Given an active workshop event named "Communications Common A" exists with capacity "1" and shared common name "share10"
    Given an active workshop event named "Communications Common B" exists with capacity "1" and shared common name "share10"
    And a new user has registered for the "Communications Common A" campaign
    And a new user has registered for the "Communications Common B" campaign
    When I open the registration page for the "Communications Workshop B" campaign
    Then the registration form should not be displayed
    And I should see Event Fully Booked message
