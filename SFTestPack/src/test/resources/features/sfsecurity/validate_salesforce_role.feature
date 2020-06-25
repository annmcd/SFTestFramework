@SFSecurity


Feature:Salesforce Security Matrix - Roles


  Scenario Outline: Verify that Salesforce Role settings are as expected in supplied Yaml configuration
    Given I have a role yamlDefinition "<yamlDefinition>"
    And I am connected to a salesforce organisation
    When I verify it against a salesforce organisation
    Then I expect the expected and actual values to be the same
    Examples:
      | yamlDefinition |
      | UserRole.yaml  |
