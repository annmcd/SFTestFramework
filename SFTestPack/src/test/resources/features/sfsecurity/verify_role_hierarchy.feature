@SFSecurity


Feature: Role Hierarchy Configuration Verification
  Description: Verify the required role hierarchy configuration is correct


  Scenario Outline: As an Engineer I need to verify that Roles are configured within the required hierarchy structure
    Given I  have a connection to salesforce Org in a given environment and Roles have been deployed
    When I query the role hierarchy against the levels LevelOne  "<Level1>" and LevelTwo "<Level2>" LevelThree "<Level3>" and LevelFour "<Level4>"
    Then I expect the results to match the hierarchy defined
    Examples:
      | Level1              | Level2         | Level3                        | Level4                   |
      | Full Access Germany |                |                               |                          |
      | Full Access Germany | Director Sales | Regional Director BaWu        | Broker Consultant BaWu   |
      | Full Access Germany | Director Sales | Regional Director Bayern      | Broker Consultant Bayern |
      | Full Access Germany | Director Sales | Regional Director Mitte       | Broker Consultant Mitte  |
      | Full Access Germany | Director Sales | Regional Director Nord        | Broker Consultant Nord   |
      | Full Access Germany | Director Sales | Regional Director NRW         | Broker Consultant NRW    |
      | Full Access Germany | Director Sales | Regional Director RKA         |                          |
      | Full Access Germany | Director Sales | Regional Director Cooperation |                          |
      | Full Access Germany | Director Sales | Regional Director Central     |                          |
      | Full Access Germany | Director Sales | ZKA 1                         |                          |
      | Full Access Germany | Director Sales | ZKA 2                         |                          |
      | Full Access Germany | Director Sales | ZKA 2                         |                          |