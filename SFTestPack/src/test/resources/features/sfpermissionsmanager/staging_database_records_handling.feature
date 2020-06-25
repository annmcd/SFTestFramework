@SFPermissionsManager
#ToDo automated build includes scripts for these since phase 3 release 1 and should be added

@notPreprod
@ignore
Feature:Salesforce SFpermissionsManager Functionality for managing users in the Staging database


  Scenario Outline: Verify that records for all security settings in relation to  a  particular environment can be removed
    Given I have a connection to specified staging environment database identified by "<dbSuffix>"
    And I have a salesforce connection to an org identified by target environment
    When I request to remove security details for a particular environment "<environment>"
    Then I expect them all records for that environment will be removed successfully
    Examples:
      | environment | dbSuffix |
      | dev         | sprints  |
