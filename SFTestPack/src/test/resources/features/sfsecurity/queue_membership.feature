@SFSecurity
#feature and scenario SAL numbers updated AND ORDERED ALPHABETICALLY + DUPLICATES REMOVED!


Feature: Salesforce Security Matrix - Queues

  Scenario Outline: Verify that Queues have correct group membership
    Given I have a connection to a salesforce org
    And I know that queue "<queueName>" should have as a member "<groupName>"
    When I check queue membership
    Then Then the group should be a member of the queue
    Examples:
      | queueName       | groupName     |
      | Lead Mgt Queue  | BC Lead Admin |
      | Event Mgt Queue | EventMgt      |



