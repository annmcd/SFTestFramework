@SFSecurity
#feature and scenario SAL numbers updated AND ORDERED ALPHABETICALLY + DUPLICATES REMOVED!

Feature: Salesforce Security Matrix - PermissionSets


  Scenario Outline: Verify that Salesforce PermissionSet settings are as expected in supplied Yaml configuration
    Given I have a permissionset yamlDefinition "<yamlDefinition>" for permissionSet "<permissionSet>" in the test resources folder
    And I have a connection to a salesforce org
    When I check expected settings versus actual
    Then Then they should both be equal
    Examples:
      | yamlDefinition                            | permissionSet                        |
      | Feature_Administration.yaml               | Feature_Administration               |
      | Full_Access.yaml                          | Full_Access                          |
      | Manage_iSuite_Tasks.yaml                  | Manage_iSuite_Tasks                  |
      | Manage_Public_Reports_And_Dashboards.yaml | Manage_Public_Reports_And_Dashboards |
      | Moderate_Chatter.yaml                     | Moderate_Chatter                     |
      | Password_Not_Expires.yaml                 | Password_Not_Expires                 |
      | Shield_Administrator.yaml                 | Shield_Administrator                 |
      | View_All_Policy_Members.yaml              | View_All_Policy_Members              |
      | View_Commission_Agreements.yaml           | View_Commission_Agreements           |
      | View_Commission_Payments.yaml             | View_Commission_Payments             |
      | View_Targets.yaml                         | View_Targets                         |