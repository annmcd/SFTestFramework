@SFBusinessProcess

@notPreprod
Feature: Testing creation of Contacts

  @validation_rule
  Scenario: Creating a Contact with no salutation
    When I create a Contact with no salutation
    Then an error occurs due to the following error messages:
      | message                       |
      | Bitte wählen sie eine Anrede. |

  Scenario: Creating a Contact with no last name
    When I create a Contact with no last name
    Then an error occurs due to the following validation messages:
      | field | message                                 |
      | Name  | Required fields are missing: [LastName] |

  Scenario: Creating a valid Contact
    When I create a Contact with salutation: "Mr" and last name "Smith"
    Then the Contact should be successfully created

  @validation_rule
  Scenario: Creating a Contact with invalid VVID Number
    When I create a Contact with VVID Number "abc"
    Then an error occurs due to the following error messages:
      | message                                   |
      | Wrong Format!  Must be YYYYMMDD-nnnnnn-nn |

  @validation_rule
  Scenario: Creating a Contact with valid VVID Number
    When I create a Contact with VVID Number "20200101-123456-88"
    Then the Contact should be successfully created