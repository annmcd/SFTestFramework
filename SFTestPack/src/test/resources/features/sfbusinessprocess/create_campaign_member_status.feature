@SFBusinessProcess


Feature: Create Campaign Member Status for Workshop variations

  @manual
  # Derived from R2-S1
  Scenario Outline: Creation of Campaign Member Status for Active Workshop with Capacity
    Given I have logged into Salesforce Org as "SalesSupport"
    And I have a new active Workshop Campaign named "active-hasCapacity" with Capacity "20"
    When I open the "active-hasCapacity" Campaign record
    And I create a new Campaign Member Status named "<statusName>" with responded "<statusResponded>"
    Then the Campaign Member Status "<statusName>" should be available with responded "<statusResponded>"
    Examples:
      | statusName | statusResponded |
      | Registered | true            |
      | Registered | false           |

  @manual
  # Derived from R2-S2
  Scenario Outline: Creation of Campaign Member Status for Active Workshop with no Capacity
    Given I have logged into Salesforce Org as "SalesSupport"
    And I have a new active Workshop Campaign named "active-hasNoCapacity" with blank Capacity
    When I open the "active-hasNoCapacity" Campaign record
    And I create a new Campaign Member Status named "<statusName>" with responded "<statusResponded>"
    Then the Campaign Member Status "<statusName>" should be available with responded "<statusResponded>"
    Examples:
      | statusName | statusResponded |
      | Registered | true            |
      | Registered | false           |

  @manual
  #Derived from R2-S4
  Scenario Outline: Creation of Campaign Member Status for Inactive Workshop with Capacity
    Given I have logged into Salesforce Org as "SalesSupport"
    And I have a new inactive Workshop Campaign named "inactive-hasCapacity" with Capacity "20"
    When I open the "inactive-hasCapacity" Campaign record
    And I create a new Campaign Member Status named "<statusName>" with responded "<statusResponded>"
    Then the Campaign Member Status "<statusName>" should be available with responded "<statusResponded>"
    Examples:
      | statusName | statusResponded |
      | Registered | true            |
      | Registered | false           |
