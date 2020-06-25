@EntityConfig

@ignore
Feature: Entity-Validation Rules
  Description: Verify that entities are configured with the correct validation rules


  Scenario Outline: As an Engineer I need to verify that Validation Rules are correct
    Given I have a valid salesforce connection
    And I have an  entity "<entity>" with validation rule "<rulename>"
    When I execute a verification against validation rules
    Then I expect the errorFormula specified in the properties file "Validation_Rules.properties" to match salesforce values
    And I expect the errorMessage "<errorMessage>" to match salesforce values
    And The rule activeState "<activeState>" is as expected
    Examples:
      | entity         | rulename                                | errorMessage                                                                                                      | activeState |
      | User           | Department_should_be_from_specific_list | Department should be specified as in legacy system.                                                               | true        |
      | Contact        | PreventContactSourceChange              | Contacts cannot change Contact Source.                                                                            | true        |
      | Contact        | PreventContactEdit                      | ISuite Contacts Cannot be Edited.                                                                                 | true        |
      | Contact        | SalutationMustExist                     | Bitte wählen sie eine Anrede.                                                                                                                | true        |
      | Account        | Prevent_Edit_Broker_Account             | Broker Accounts cannot be edited                                                                                  | true        |
      | Account        | Account_Creation                        | Account Records are managed by the ETL Process. Users cannot create accounts.                                     | true        |
      | Contract       | Prevent_Edit_Contract                   | Policies cannot be updated.                                                                                       | true        |
      | Product2       | Not_Name_Modifications                  | Product Name cannot be changed.                                                                                   | true        |
      | Campaign       | WBINumberValidation                     | WBI Number is incorrect. Please use format P, E or W followed by 8 Numbers                                        | true        |
      | CampaignMember | StatusChanges                           | Dieser Status kann nur durch einen Bentzer im Profil 'Sales Support’ oder 'Business Administrator’ gesetzt werden | true        |
