@SFBusinessProcess

@ignore
Feature: Apex Unit and process Tests by Suite

  Scenario Outline: I want to run a specified suite of Apex Tests
    Given I have a jwt flow connected
    When I run tests in suite "<Suite>"
    Then I expect them all to pass
    Examples:
    |Suite|
    |LeadAndEvent |