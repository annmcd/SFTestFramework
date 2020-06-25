@TestCoverage

@ignore
@issue:SAL-4471
Feature: Security/Profiles Indicate where Profiles show gaps in relation to Test Coverage


  Scenario: Verify that all CLE Profiles are being tested
    Given I have a saleforce connection for security coverage
    When I provide a list of custom profiles
      | Broker Consultant        |
      | Business Administrator   |
      | Sales Support            |
      | Business Support         |
      | CLE System Administrator |
    Then I expect yaml files to have been created with tests for those profiles


  Scenario Outline: Indicate where custom entities and fields are not listed in Profile Entity Permissions
    Given I have a saleforce connection for security coverage
    When I provide a profile name "<profileName>"
    Then Entities that are marked as checked should be checked in salesforce, with profile security settings matching
    And Fields that are marked as checked should not be indicated on the profile security settings
    And I expect issues to be flagged in the error log
    Examples:
      | profileName              |
      | Broker Consultant        |
      | Business Administrator   |
      | Sales Support            |
      | Business Support         |
      | CLE System Administrator |


  Scenario Outline: Indicate where permissions are not listed for the profile
    Given I have a saleforce connection for security coverage
    When I provide a profile name "<profileName>"
    Then Any permissions which are enabled on the profile but not on the test should be highlighted
    Examples:
      | profileName              |
      | Broker Consultant        |
      | Business Administrator   |
      | Sales Support            |
      | Business Support         |
      | CLE System Administrator |