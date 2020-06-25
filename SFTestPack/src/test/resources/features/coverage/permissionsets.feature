@TestCoverage

@ignore
@issue:SAL-4472
Feature: Security/Permission Sets Indicate where Permission Sets have test gaps in relation to Entities and System Privileges
#Note All Custom entities specified in this feature should be listed in the Entity Check Feature

  Scenario: Verify that permission sets are being tested
    Given I have a saleforce connection for security coverage
    And I have a folder where permission set definitions are contained
    When I check them against a salesforce org
    Then I expect the folder to contain all the permission sets


  Scenario Outline: Indicate where custom permission sets do not cover all required, entities, fields and system permissions
    Given I have a saleforce connection for security coverage
    When I provide a permissionSet name "<permissionSetName>"
    Then Entities that are marked as checked, should be checked in salesforce, with permissionSet security settings matching
    And System and User Permissions that are marked as checked, should not be indicated on the permissionSet security settings
    Examples:
      | permissionSetName                    |
      | Feature_Administration               |
      | Full_Access                          |
      | Manage_iSuite_Tasks                  |
      | Manage_Public_Reports_And_Dashboards |
      | Moderate_Chatter                     |
      | Password_Not_Expires                 |
      | Shield_Administrator                 |
      | View_All_Policy_Members              |
      | View_Commission_Agreements           |
      | View_Commission_Payments             |
      | View_Targets                         |
