@TestCoverage

@ignore
@issue:SAL-4473
Feature: Security/Roles Indicate where Roles show gaps in relation to Test Coverage

  Scenario: Verify coverage of the hierarchy
    Given I have a saleforce connection for security coverage
    When I provide a list of Roles
      | Full Access Germany           |
      | Director Sales                 |
      | Regional Director BaWu        |
      | Regional Director Bayern      |
      | Regional Director Mitte       |
      | Regional Director NRW         |
      | Regional Director BAWU        |
      | Regional Director RKA         |
      | Regional Director Cooperation |
      | Regional Director Central     |
      | ZKA 1                         |
      | ZKA 2                         |
      | ZKA 3                         |
      | Broker Consultant BaWu        |
      | Broker Consultant Bayern      |
      | Broker Consultant Mitte       |
      | Broker Consultant Nord        |
      | Broker Consultant NRW         |
    Then I expect these to be the only Roles in the Role Hierarchy
    And I expect all the hierarchy roles to be in this list of Roles