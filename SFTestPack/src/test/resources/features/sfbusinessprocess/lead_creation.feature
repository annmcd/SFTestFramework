@SFBusinessProcess

@notPreprod
Feature: Testing creation of Leads

  Scenario: Creating a Lead with no last name
    When I create a Lead with no last name
    Then an error occurs due to the following validation messages:
      | field | message                                 |
      | Name  | Required fields are missing: [LastName] |

  @validation_rule
  Scenario: Creating a Lead with invalid VVID Number
    When I create a Lead with VVID Number "abc"
    Then an error occurs due to the following error messages:
      | message                                   |
      | Wrong Format!  Must be YYYYMMDD-nnnnnn-nn |
