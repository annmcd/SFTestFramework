@SFBusinessProcess

Feature: Slots are ordered correctly in the Registration form

  @manual
  Scenario: Slots are ordered as expected when slot order is sequential
    Given an active workshop event named "Active-wCapacity-woCommon" exists with capacity "35" and no shared common name
    And the following slots and sessions in the "Active-wCapacity-woCommon" Campaign exist:
    | slotName | slotOrder | sessionName | sessionCapacity |
    | Slot A   | 1         | Session A   | 15              |
    | Slot B   | 2         | Session B   | 15              |
    | Slot C   | 3         | Session C   | 15              |
    | Slot D   | 4         | Session D   | 15              |
    | Slot E   | 5         | Session E   | 15              |
    When I open the registration page for the "Active-wCapacity-woCommon" campaign
    Then I should see the following slots in this order:
    | slotName |
    | Slot A   |
    | Slot B   |
    | Slot C   |
    | Slot D   |
    | Slot E   |

  @manual
  Scenario: Slots are ordered as expected when slot order is obscure
    Given an active workshop event named "Active-wCapacity-woCommon" exists with capacity "35" and no shared common name
    And the following slots and sessions in the "Active-wCapacity-woCommon" Campaign exist:
      | slotName | slotOrder | sessionName | sessionCapacity |
      | Slot A   | -10       | Session A   | 15              |
      | Slot B   | 0         | Session B   | 15              |
      | Slot C   | 0.1       | Session C   | 15              |
      | Slot D   | 20        | Session D   | 15              |
      | Slot E   | 20.1      | Session E   | 15              |
    When I open the registration page for the "Active-wCapacity-woCommon" campaign
    Then I should see the following slots in this order:
      | slotName |
      | Slot A   |
      | Slot B   |
      | Slot C   |
      | Slot D   |
      | Slot E   |
