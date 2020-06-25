@SFBusinessProcess

Feature: Create events with slots and sessions

  @manual
  Scenario: Creating one Slot in a Workshop Campaign
    Given I have logged into Salesforce Org as "SalesSupport"
    And an active workshop event named "Communications Workshop" exists with blank capacity and no shared common name
    When I create the following slots in the "Communications Workshop" Campaign:
      | slotName      | slotOrder |
      | Morning Slot  | 1         |
    Then the "Communications Workshop" should have the following slots:
      | slotName      | slotOrder |
      | Morning Slot  | 1         |

  @manual
  Scenario: Creating multiple Slots in a Workshop Campaign
    Given I have logged into Salesforce Org as "SalesSupport"
    And an active workshop event named "Communications Workshop" exists with blank capacity and no shared common name
    When I create the following slots in the "Communications Workshop" Campaign:
      | slotName        | slotOrder |
      | Very Early Slot | 1         |
      | Early Slot      | 2         |
      | Midday Slot     | 3         |
      | Late Slot       | 4         |
      | Very Late Slot  | 5         |
    Then the "Communications Workshop" should have the following slots:
      | slotName        | slotOrder |
      | Very Early Slot | 1         |
      | Early Slot      | 2         |
      | Midday Slot     | 3         |
      | Late Slot       | 4         |
      | Very Late Slot  | 5         |

  @manual
  Scenario: Creating one event slot with one session
    Given I have logged into Salesforce Org as "SalesSupport"
    When I create a new Campaign with details:
      | field         | value                         |
      | name          | Communications Workshop       |
      | active        | true                          |
      | campaign type | Workshop                      |
    And I create the following slots and sessions in the "Communications Workshop" Campaign:
      | slotName      | slotOrder | sessionName       | sessionCapacity |
      | Full Day Slot | 1         | Effective Comms 1 | 5               |
    Then the "Communications Workshop" Campaign should have the following slots and sessions:
      | slotName      | slotOrder | sessionName       | sessionCapacity |
      | Full Day Slot | 1         | Effective Comms 1 | 5               |


  @manual
  Scenario: Creating one event slot with multiple sessions
    Given I have logged into Salesforce Org as "SalesSupport"
    When I create a new Campaign with details:
      | field         | value                         |
      | name          | Communications Workshop       |
      | active        | true                          |
      | campaign type | Workshop                      |
    And I create the following slots and sessions in the "Communications Workshop" Campaign:
      | slotName      | slotOrder | sessionName       | sessionCapacity |
      | Full Day Slot | 1         | Effective Comms 1 |                 |
      | Full Day Slot | 1         | Effective Comms 2 |                 |
      | Full Day Slot | 1         | Effective Comms 3 |                 |
      | Full Day Slot | 1         | Effective Comms 4 |                 |
      | Full Day Slot | 1         | Effective Comms 5 |                 |
    Then the "Communications Workshop" Campaign should have the following slots and sessions:
      | slotName      | slotOrder | sessionName       | sessionCapacity |
      | Full Day Slot | 1         | Effective Comms 1 |                 |
      | Full Day Slot | 1         | Effective Comms 2 |                 |
      | Full Day Slot | 1         | Effective Comms 3 |                 |
      | Full Day Slot | 1         | Effective Comms 4 |                 |
      | Full Day Slot | 1         | Effective Comms 5 |                 |

  @manual
  # Derived from R3-S1
  Scenario: Multiple event slots with multiple sessions, and no shared common name
    Given I have logged into Salesforce Org as "SalesSupport"
    When I create a new Campaign with details:
      | field             | value                                   |
      | name              | Communications Workshop                 |
      | active            | true                                    |
      | campaign type     | Workshop                                |
      | capacity          | 35                                      |
      | event common name |                                         |
    And I create the following slots and sessions in the "Communications Workshop" Campaign:
      | slotName          | slotOrder | sessionName                 | sessionCapacity |
      | Morning Slots     | 1         | Effective Comms 1           | 15              |
      | Morning Slots     | 1         | Modern Business Standards 1 | 20              |
      | Afternoon Slots   | 2         | Effective Comms 2           | 15              |
      | Afternoon Slots   | 2         | Modern Business Standards 2 | 20              |
    Then the "Communications Workshop" Campaign should have the following slots and sessions:
      | slotName          | slotOrder | sessionName                 | sessionCapacity |
      | Morning Slots     | 1         | Effective Comms 1           | 15              |
      | Morning Slots     | 1         | Modern Business Standards 1 | 20              |
      | Afternoon Slots   | 2         | Effective Comms 2           | 15              |
      | Afternoon Slots   | 2         | Modern Business Standards 2 | 20              |

  @manual
  # Derived from R3-S2
  Scenario: Multiple event slots with multiple sessions, and a shared common name
    Given I have logged into Salesforce Org as "SalesSupport"
    When I create a new Campaign with details:
      | field             | value                   |
      | name              | Communications Workshop |
      | active            | true                    |
      | campaign type     | Workshop                |
      | capacity          | 35                      |
      | event common name | common-r2-s2            |
    And I create the following slots and sessions in the "Communications Workshop" Campaign:
      | slotName          | slotOrder | sessionName                 | sessionCapacity |
      | Morning Slots     | 1         | Effective Comms 1           | 15              |
      | Morning Slots     | 1         | Modern Business Standards 1 | 20              |
      | Afternoon Slots   | 2         | Effective Comms 2           | 15              |
      | Afternoon Slots   | 2         | Modern Business Standards 2 | 20              |
    When I create a new Campaign with details:
      | field             | value                   |
      | name              | Business Workshop       |
      | active            | true                    |
      | campaign type     | Workshop                |
      | capacity          | 35                      |
      | event common name | common-r2-s2            |
    And I create the following slots and sessions in the "Business Workshop" Campaign:
      | slotName          | slotOrder | sessionName                 | sessionCapacity |
      | Introduction Slot | 1         | Basic Sales                 | 15              |
      | Introduction Slot | 1         | Basic Service               | 20              |
      | Advanced Slot     | 2         | Advanced                    | 15              |
      | Advanced Slot     | 2         | Advanced Service            | 20              |
    Then the "Communications Workshop" Campaign should have the following slots and sessions:
      | slotName          | slotOrder | sessionName                 | sessionCapacity |
      | Morning Slots     | 1         | Effective Comms 1           | 15              |
      | Morning Slots     | 1         | Modern Business Standards 1 | 20              |
      | Afternoon Slots   | 2         | Effective Comms 2           | 15              |
      | Afternoon Slots   | 2         | Modern Business Standards 2 | 20              |
    And the "Business Workshop" Campaign should have the following slots and sessions:
      | slotName          | slotOrder | sessionName                 | sessionCapacity |
      | Introduction Slot | 1         | Basic Sales                 | 15              |
      | Introduction Slot | 1         | Basic Service               | 20              |
      | Advanced Slot     | 2         | Advanced                    | 15              |
      | Advanced Slot     | 2         | Advanced Service            | 20              |

  @manual
  # Derived from R3-S3
  Scenario: Multiple event slots with multiple blank capacity sessions, and a shared common name
    Given I have logged into Salesforce Org as "SalesSupport"
    When I create a new Campaign with details:
      | field             | value                   |
      | name              | Communications Workshop |
      | active            | true                    |
      | campaign type     | Workshop                |
      | capacity          | 35                      |
      | event common name | common-r2-s3            |
    And I create the following slots and sessions in the "Communications Workshop" Campaign:
      | slotName          | slotOrder | sessionName                 | sessionCapacity |
      | Morning Slos      | 1         | Effective Comms 1           |                 |
      | Morning Slos      | 1         | Modern Business Standards 1 |                 |
      | Afternoon Slot    | 2         | Effective Comms 2           |                 |
      | Afternoon Slot    | 2         | Modern Business Standards 2 |                 |
    When I create a new Campaign with details:
      | field             | value                   |
      | name              | Business Workshop       |
      | active            | true                    |
      | campaign type     | Workshop                |
      | capacity          | 35                      |
      | event common name | common-r2-s3            |
    And I create the following slots and sessions in the "Communications Workshop" Campaign:
      | slotName          | slotOrder | sessionName                 | sessionCapacity |
      | Introduction Slot | 1         | Basic Sales                 |                 |
      | Introduction Slot | 1         | Basic Service               |                 |
      | Advanced Slot     | 2         | Advanced                    |                 |
      | Advanced Slot     | 2         | Advanced Service            |                 |
    Then the "Communications Workshop" Campaign should have the following slots and sessions:
      | slotName          | slotOrder | sessionName                 | sessionCapacity |
      | Morning Slots     | 1         | Effective Comms 1           |                 |
      | Morning Slots     | 1         | Modern Business Standards 1 |                 |
      | Afternoon Slots   | 2         | Effective Comms 2           |                 |
      | Afternoon Slots   | 2         | Modern Business Standards 2 |                 |
    And the "Business Workshop" Campaign should have the following slots and sessions:
      | slotName          | slotOrder | sessionName                 | sessionCapacity |
      | Introduction Slot | 1         | Basic Sales                 |                 |
      | Introduction Slot | 1         | Basic Service               |                 |
      | Advanced Slot     | 2         | Advanced                    |                 |
      | Advanced Slot     | 2         | Advanced Service            |                 |
