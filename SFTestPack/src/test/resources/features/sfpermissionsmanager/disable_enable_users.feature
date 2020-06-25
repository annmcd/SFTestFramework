@SFPermissionsManager
#ToDo Implementation is not complete and requires sfpermissionsmanager running in the framework to enable/disable
@ignore
@notPreprod
Feature:Salesforce SFpermissionsManager Functionality for Enabling and  Disabling users


  Scenario Outline: Verify that users requested for DISABLING in salesforce are disabled using the disable function of SFPermissionsManager
    Given I have a connection to specified staging environment database identified by "<dbSuffix>"
    And I have a salesforce connection to an org identified by target environment
    When I request to disable users specified in "<PermissionsSettings.xls>"
    Then I expect that all users specified in the "DisabledUsers" tab will be disabled successfully
    Examples:
      | dbSuffix |
      | sprints  |


  Scenario Outline: Verify that users requested for ENABLING in salesforce are enabled using the enable function of SFPermissionsManager
    Given I have a connection to specified staging environment database identified by "<dbSuffix>"
    And I have a salesforce connection to an org identified by target environment
    When I request to enable users specified in "<PermissionsSettings.xls>"
    Then I expect that all users specified in the "EnabledUsers" tab will be enabled successfully
    Examples:
      | dbSuffix |
      | sprints  |