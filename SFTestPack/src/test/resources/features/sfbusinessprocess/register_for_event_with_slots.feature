@SFBusinessProcess

  Feature: Registration for an event with Slots and Sessions

  @manual
  #Derived from R3-S9
  Scenario: The workshop and Campaign ID in Registration Form should be automatically set, and unchangeable, when slots and sessions exist
    Given an active workshop event named "Active-wCapacity-woCommon" exists with capacity "35" and no shared common name
    And the following slots and sessions in the "Active-wCapacity-woCommon" Campaign exist:
    | slotName          | slotOrder | sessionName                 | sessionCapacity |
    | Morning Slots     | 1         | Effective Comms 1           | 15              |
    | Morning Slots     | 1         | Modern Business Standards 1 | 20              |
    | Afternoon Slots   | 2         | Effective Comms 2           | 15              |
    | Afternoon Slots   | 2         | Modern Business Standards 2 | 20              |
    When I open the registration page for the "Active-wCapacity-woCommon" campaign
    Then there should be "1" workshop selection options
    Then the workshop "Active-wCapacity-woCommon" should be automatically selected
    And the campaign ID hidden field should be populated with the campaign ID of "Active-wCapacity-woCommon"

  @manual
  #Derived from R3-S9
  Scenario: Slots and sessions should be populated on registration form
    Given an active workshop event named "Active-wCapacity-woCommon" exists with capacity "35" and no shared common name
    And the following slots and sessions in the "Active-wCapacity-woCommon" Campaign exist:
    | slotName          | slotOrder | sessionName                 | sessionCapacity |
    | Morning Slots     | 1         | Effective Comms 1           | 15              |
    | Morning Slots     | 1         | Modern Business Standards 1 | 20              |
    | Afternoon Slots   | 2         | Effective Comms 2           | 15              |
    | Afternoon Slots   | 2         | Modern Business Standards 2 | 20              |
    When I open the registration page for the "Active-wCapacity-woCommon" campaign
    Then the "Morning Slots" session options should be as follows:
    | sessionName                 | enabled |
    | Effective Comms 1           | true    |
    | Modern Business Standards 1 | true    |
    And the "Afternoon Slots" session options should be as follows:
    | sessionName                 | enabled |
    | Effective Comms 2           | true    |
    | Modern Business Standards 2 | true    |

  @manual
  #Derived from R3-S9
  Scenario: ab
    Given an active workshop event named "Active-wCapacity-woCommon" exists with capacity "35" and no shared common name
    And the following slots and sessions in the "Active-wCapacity-woCommon" Campaign exist:
    | slotName          | slotOrder | sessionName                 | sessionCapacity |
    | Morning Slots     | 1         | Effective Comms 1           | 15              |
    | Morning Slots     | 1         | Modern Business Standards 1 | 20              |
    | Afternoon Slots   | 2         | Effective Comms 2           | 15              |
    | Afternoon Slots   | 2         | Modern Business Standards 2 | 20              |
    And I open the registration page for the "Active-wCapacity-woCommon" campaign
    And I select the "Effective Comms 1" Session in the "Morning Slots"
    And I select the "Effective Comms 2" Session in the "Afternoon Slots"
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
    And the Campaign Member record should have "2" event session registrations containing the lead, member ID, member Name, and expected sessions

  @manual
  # Derived from r3-s10
  Scenario: Events, slots and sessions are selectable when Events have a shared common name
    Given I have logged into Salesforce Org as "SalesSupport"
    And an active workshop event named "Communications Workshop" exists with capacity "35" and shared common name "common-r3-s10"
    And an active workshop event named "Business Workshop" exists with capacity "35" and shared common name "common-r3-s10"
    And I create the following slots and sessions in the "Communications Workshop" Campaign:
    | slotName          | slotOrder | sessionName                 | sessionCapacity |
    | Morning Slots     | 1         | Effective Comms 1           | 15              |
    | Morning Slots     | 1         | Modern Business Standards 1 | 20              |
    | Afternoon Slots   | 2         | Effective Comms 2           | 15              |
    | Afternoon Slots   | 2         | Modern Business Standards 2 | 20              |
    And I create the following slots and sessions in the "Business Workshop" Campaign:
    | slotName          | slotOrder | sessionName                 | sessionCapacity |
    | Introduction Slot | 1         | Basic Sales                 | 15              |
    | Introduction Slot | 1         | Basic Service               | 20              |
    | Advanced Slot     | 2         | Advanced Sales              | 15              |
    | Advanced Slot     | 2         | Advanced Service            | 20              |
    When I open the registration page for the "Communications Workshop" campaign
    Then I should have the following options for Event Selection
    | eventName               |
    | Communications Workshop |
    | Business Workshop       |
    And selecting "Business Workshop" Event results in the following Slots and Sessions:
    | slot name         | sessionName      |
    | Introduction Slot | Basic Sales      |
    | Introduction Slot | Basic Service    |
    | Advanced Slot     | Advanced Sales   |
    | Advanced Slot     | Advanced Service |

  @manual
  # Derived from r3-s10
  Scenario: Events, slots and sessions are selectable when Events have a shared common name
    Given I have logged into Salesforce Org as "SalesSupport"
    And an active workshop event named "Communications Workshop" exists with capacity "35" and shared common name "common-r3-s10"
    And an active workshop event named "Business Workshop" exists with capacity "35" and shared common name "common-r3-s10"
    And I create the following slots and sessions in the "Communications Workshop" Campaign:
    | slotName          | slotOrder | sessionName                 | sessionCapacity |
    | Morning Slots     | 1         | Effective Comms 1           | 15              |
    | Morning Slots     | 1         | Modern Business Standards 1 | 20              |
    | Afternoon Slots   | 2         | Effective Comms 2           | 15              |
    | Afternoon Slots   | 2         | Modern Business Standards 2 | 20              |
    And I create the following slots and sessions in the "Business Workshop" Campaign:
    | slotName          | slotOrder | sessionName                 | sessionCapacity |
    | Introduction Slot | 1         | Basic Sales                 | 15              |
    | Introduction Slot | 1         | Basic Service               | 20              |
    | Advanced Slot     | 2         | Advanced Sales              | 15              |
    | Advanced Slot     | 2         | Advanced Service            | 20              |
    When I open the registration page for the "Communications Workshop" campaign
    And I select the "Business Workshop" Event
    And I select the "Basic Service" Session in the "Introduction Slot"
    And I select the "Advanced Service" Session in the "Advanced Slot"
    When I submit the registration form for the "Business Workshop" campaign with the following details:
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
  # Derived from R3-S11
  Scenario: Registration for Event with no Shared Common Name - 2 Slots each with 2 sessions to select - No Capacity added
    Given I have logged into Salesforce Org as "SalesSupport"
    When I create a new Campaign with details:
    | field             | value                                   |
    | name              | Communications Workshop                 |
    | active            | true                                    |
    | campaign type     | Workshop                                |
    | capacity          |                                         |
    | event common name |                                         |
    And I create the following slots and sessions in the "Communications Workshop" Campaign:
    | slotName          | slotOrder | sessionName                 | sessionCapacity |
    | Morning Slots     | 1         | Effective Comms 1           |                 |
    | Morning Slots     | 1         | Modern Business Standards 1 |                 |
    | Afternoon Slots   | 2         | Effective Comms 2           |                 |
    | Afternoon Slots   | 2         | Modern Business Standards 2 |                 |
    When I open the registration page for the "Communications Workshop" campaign
    And I select the "Modern Business Standards 1" Session in the "Morning Slots"
    And I select the "Effective Comms 2" Session in the "Afternoon Slots"
    When I submit the registration form for the "Communications Workshop" campaign with the following details:
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
    And the Campaign Member record should have "2" event session registrations containing the lead, member ID, member Name, and expected sessions

  @manual
  # Derived from R3-S12
  Scenario: Sessions automatically selected if they are the only available ones
    Given an active workshop event named "S12 Campaign" exists with capacity "35" and no shared common name
    And the following slots and sessions in the "S12 Campaign" Campaign exist:
    | slotName          | slotOrder | sessionName                 | sessionCapacity |
    | Morning Slots     | 1         | Effective Comms 1           | 1               |
    | Morning Slots     | 1         | Modern Business Standards 1 | 1               |
    | Afternoon Slots   | 2         | Effective Comms 2           | 1               |
    | Afternoon Slots   | 2         | Modern Business Standards 2 | 1               |
    And a new user has registered for the "S12 Campaign" campaign selecting the following sessions:
    | slotName          | sessionName                 |
    | Morning Slots     | Effective Comms 1           |
    | Afternoon Slots   | Effective Comms 2           |
    When I open the registration page for the "S12 Campaign" campaign
    Then there should be "1" workshop selection options
    And the workshop "S12 Campaign" should be automatically selected
    And the "Morning Slots" session options should be as follows:
    | sessionName                            | enabled |
    | Effective Comms 1 (bereits ausgebucht) | false   |
    | Modern Business Standards 1            | true    |
    And the slot "Morning Slots" should have "Modern Business Standards 1" automatically selected
    And the "Afternoon Slots" session options should be as follows:
    | sessionName                            | enabled |
    | Effective Comms 2 (bereits ausgebucht) | false   |
    | Modern Business Standards 2            | true    |
    And the slot "Afternoon Slots" should have "Modern Business Standards 2" automatically selected

  @manual
  # Derived from R3-S12
  Scenario: Session registrations created successfully for workshop with slots, sessions, and capacity
    Given an active workshop event named "S12 Campaign" exists with capacity "35" and no shared common name
    And the following slots and sessions in the "S12 Campaign" Campaign exist:
    | slotName          | slotOrder | sessionName                 | sessionCapacity |
    | Morning Slots     | 1         | Effective Comms 1           | 1               |
    | Morning Slots     | 1         | Modern Business Standards 1 | 1               |
    | Afternoon Slots   | 2         | Effective Comms 2           | 1               |
    | Afternoon Slots   | 2         | Modern Business Standards 2 | 1               |
    And a new user has registered for the "S12 Campaign" campaign selecting the following sessions:
    | slotName          | sessionName                 |
    | Morning Slots     | Effective Comms 1           |
    | Afternoon Slots   | Effective Comms 2           |
    When I open the registration page for the "S12 Campaign" campaign
    And I select the "Modern Business Standards 1" Session in the "Morning Slots"
    And I select the "Modern Business Standards 2" Session in the "Afternoon Slots"
    And I complete and submit the registration form  with the following details:
    | field        | value            |
    | salutation   | Mr               |
    | company name | Apple            |
    | first name   | bob              |
    | last name    | taylor           |
    | mobile phone | 012345678        |
    | email        | <uniqueTestMail> |
    | accept pp    | true             |
    Then I should see a please wait message
    And I should see a registration confirmation message for the "S12 Campaign" event
    And I should receive an Event Registration email
    And a Lead record should be created with details corresponding to the registration
    And the Lead record should remain in the lead queue
    And a Campaign Member record should be created with details corresponding to the Lead
    And the status of the Campaign Member record should be "Registered"
    And the Campaign Member record should have "2" event session registrations containing the lead, member ID, member Name, and expected sessions

  @manual
  # Derived from R3-S13
  Scenario: No Sessions available message displayed when capacity is reached
    Given an active workshop event named "S12 Campaign" exists with capacity "35" and no shared common name
    And the following slots and sessions in the "S12 Campaign" Campaign exist:
    | slotName          | slotOrder | sessionName                 | sessionCapacity |
    | Morning Slots     | 1         | Effective Comms 1           | 1               |
    | Morning Slots     | 1         | Modern Business Standards 1 | 1               |
    | Afternoon Slots   | 2         | Effective Comms 2           | 1               |
    | Afternoon Slots   | 2         | Modern Business Standards 2 | 1               |
    And a new user has registered for the "S12 Campaign" campaign selecting the following sessions:
    | slotName          | sessionName                 |
    | Morning Slots     | Effective Comms 1           |
    | Afternoon Slots   | Effective Comms 2           |
    And a new user has registered for the "S12 Campaign" campaign selecting the following sessions:
    | slotName          | sessionName                 |
    | Morning Slots     | Modern Business Standards 1 |
    | Afternoon Slots   | Modern Business Standards 2 |
    When I open the registration page for the "S12 Campaign" campaign
    Then there should be "1" workshop selection options
    And the workshop "Active-wCapacity-woCommon" should be automatically selected
    And the campaign ID hidden field should be populated with the campaign ID of "Active-wCapacity-woCommon"
    And I should see a No Sessions Available message under the "Morning Slots" slot
    And I should see a No Sessions Available message under the "Afternoon Slots" slot

  @manual
  # Derived from R3-S13
  Scenario: No Registrations create a user who registeres when no capacity remains for sessions
    Given an active workshop event named "S12 Campaign" exists with capacity "35" and no shared common name
    And the following slots and sessions in the "S12 Campaign" Campaign exist:
      | slotName          | slotOrder | sessionName                 | sessionCapacity |
      | Morning Slots     | 1         | Effective Comms 1           | 1               |
      | Morning Slots     | 1         | Modern Business Standards 1 | 1               |
      | Afternoon Slots   | 2         | Effective Comms 2           | 1               |
      | Afternoon Slots   | 2         | Modern Business Standards 2 | 1               |
    And a new user has registered for the "S12 Campaign" campaign selecting the following sessions:
      | slotName          | sessionName                 |
      | Morning Slots     | Effective Comms 1           |
      | Afternoon Slots   | Effective Comms 2           |
    And a new user has registered for the "S12 Campaign" campaign selecting the following sessions:
      | slotName          | sessionName                 |
      | Morning Slots     | Modern Business Standards 1 |
      | Afternoon Slots   | Modern Business Standards 2 |
    When I open the registration page for the "S12 Campaign" campaign
    And I complete and submit the registration form  with the following details:
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
    And the Campaign Member record should have no event session registrations
