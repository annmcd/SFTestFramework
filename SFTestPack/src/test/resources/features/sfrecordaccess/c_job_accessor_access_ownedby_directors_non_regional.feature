@SFRecordAccess

@notPreprod
#@slow
#@ignore
Feature: Access settings for all records owned by directors who are not regional directors but are distinguisable by Job Title

  Background:
    Given I have a salesforce connection to a target environment salesforce org


  Scenario Outline: Verify that All Jobs accessing Records owned by Director Sales
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The Directors are distinguishable by their UserRole "<UserRole>"
    And The record data file is "CSVRecordData_directors_non_region.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                   | JobUser                        | Owner                        | UserRole |
      | Director_Sales_Access.yaml | sales_director@canadalife.de   | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | zka1@canadalife.de             | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | zka2@canadalife.de             | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | zka3@canadalife.de             | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | admin_user@canadalife.de       | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | sales_support@canadalife.de    | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | sales_management@canadalife.de | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | german_legal_rep@canadalife.ie | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | controlling_user@canadalife.de | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | vto_user@canadalife.de         | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | businessadmin@canadalife.de    | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | integration.user@canadalife.de | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | bcnord@canadalife.ie           | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | bcbayern@canadalife.ie         | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | bcnrw@canadalife.ie            | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | bcmitte@canadalife.ie          | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | bcbawu@canadalife.ie           | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | regdirnord@canadalife.ie       | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | regdircentral@canadalife.ie    | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | regdirbayern@canadalife.ie     | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | regdirnrw@canadalife.ie        | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | regdirmitte@canadalife.ie      | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | regdirbawu@canadalife.ie       | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | regdirrka@canadalife.ie        | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | regdir_coop@canadalife.de      | sales_director@canadalife.de | Sales    |
      | Director_Sales_Access.yaml | dir_sal_chan_man@canadalife.de | sales_director@canadalife.de | Sales    |


  Scenario Outline: Verify that All Jobs accessing Records owned by ZKA 1
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The Directors are distinguishable by their UserRole "<UserRole>"
    And The record data file is "CSVRecordData_directors_non_region.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                  | JobUser                        | Owner              | UserRole |
      | Director_ZKA1_Access.yaml | zka1@canadalife.de             | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | sales_director@canadalife.de   | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | zka2@canadalife.de             | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | zka3@canadalife.de             | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | admin_user@canadalife.de       | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | sales_support@canadalife.de    | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | sales_management@canadalife.de | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | german_legal_rep@canadalife.ie | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | controlling_user@canadalife.de | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | vto_user@canadalife.de         | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | businessadmin@canadalife.de    | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | integration.user@canadalife.de | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | bcnord@canadalife.ie           | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | bcbayern@canadalife.ie         | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | bcnrw@canadalife.ie            | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | bcmitte@canadalife.ie          | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | bcbawu@canadalife.ie           | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | regdirnord@canadalife.ie       | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | regdircentral@canadalife.ie    | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | regdirbayern@canadalife.ie     | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | regdirnrw@canadalife.ie        | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | regdirmitte@canadalife.ie      | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | regdirbawu@canadalife.ie       | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | regdirrka@canadalife.ie        | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | regdir_coop@canadalife.de      | zka1@canadalife.ie | ZKA 1    |
      | Director_ZKA1_Access.yaml | dir_sal_chan_man@canadalife.de | zka1@canadalife.ie | ZKA 1    |


  Scenario Outline: Verify that All Jobs accessing Records owned by ZKA 2
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The Directors are distinguishable by their UserRole "<UserRole>"
    And The record data file is "CSVRecordData_directors_non_region.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                  | JobUser                        | Owner              | UserRole |
      | Director_ZKA2_Access.yaml | zka2@canadalife.de             | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | sales_director@canadalife.de   | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | zka2@canadalife.de             | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | zka3@canadalife.de             | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | admin_user@canadalife.de       | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | sales_support@canadalife.de    | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | sales_management@canadalife.de | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | german_legal_rep@canadalife.ie | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | controlling_user@canadalife.de | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | vto_user@canadalife.de         | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | businessadmin@canadalife.de    | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | integration.user@canadalife.de | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | bcnord@canadalife.ie           | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | bcbayern@canadalife.ie         | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | bcnrw@canadalife.ie            | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | bcmitte@canadalife.ie          | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | bcbawu@canadalife.ie           | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | regdirnord@canadalife.ie       | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | regdircentral@canadalife.ie    | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | regdirbayern@canadalife.ie     | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | regdirnrw@canadalife.ie        | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | regdirmitte@canadalife.ie      | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | regdirbawu@canadalife.ie       | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | regdirrka@canadalife.ie        | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | regdir_coop@canadalife.de      | zka2@canadalife.ie | ZKA 2    |
      | Director_ZKA2_Access.yaml | dir_sal_chan_man@canadalife.de | zka2@canadalife.ie | ZKA 2    |


  Scenario Outline: Verify that All Jobs accessing Records owned by ZKA 3
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The Directors are distinguishable by their UserRole "<UserRole>"
    And The record data file is "CSVRecordData_directors_non_region.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                  | JobUser                        | Owner              | UserRole |
      | Director_ZKA3_Access.yaml | zka3@canadalife.de             | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | sales_director@canadalife.de   | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | zka3@canadalife.de             | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | zka3@canadalife.de             | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | admin_user@canadalife.de       | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | sales_support@canadalife.de    | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | sales_management@canadalife.de | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | german_legal_rep@canadalife.ie | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | controlling_user@canadalife.de | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | vto_user@canadalife.de         | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | businessadmin@canadalife.de    | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | integration.user@canadalife.de | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | bcnord@canadalife.ie           | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | bcbayern@canadalife.ie         | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | bcnrw@canadalife.ie            | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | bcmitte@canadalife.ie          | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | bcbawu@canadalife.ie           | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | regdirnord@canadalife.ie       | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | regdircentral@canadalife.ie    | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | regdirbayern@canadalife.ie     | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | regdirnrw@canadalife.ie        | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | regdirmitte@canadalife.ie      | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | regdirbawu@canadalife.ie       | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | regdirrka@canadalife.ie        | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | regdir_coop@canadalife.de      | zka3@canadalife.ie | ZKA 3    |
      | Director_ZKA3_Access.yaml | dir_sal_chan_man@canadalife.de | zka3@canadalife.ie | ZKA 3    |


  Scenario Outline: Verify that All Jobs accessing Records owned by Admin
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The Directors are distinguishable by their UserRole "<UserRole>"
    And The record data file is "CSVRecordData_directors_non_region.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                   | JobUser                        | Owner                    | UserRole |
      | Director_Admin_Access.yaml | zka3@canadalife.de             | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | sales_director@canadalife.de   | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | zka3@canadalife.de             | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | zka3@canadalife.de             | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | admin_user@canadalife.de       | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | sales_support@canadalife.de    | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | sales_management@canadalife.de | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | german_legal_rep@canadalife.ie | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | controlling_user@canadalife.de | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | vto_user@canadalife.de         | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | businessadmin@canadalife.de    | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | integration.user@canadalife.de | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | bcnord@canadalife.ie           | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | bcbayern@canadalife.ie         | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | bcnrw@canadalife.ie            | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | bcmitte@canadalife.ie          | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | bcbawu@canadalife.ie           | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | regdirnord@canadalife.ie       | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | regdircentral@canadalife.ie    | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | regdirbayern@canadalife.ie     | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | regdirnrw@canadalife.ie        | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | regdirmitte@canadalife.ie      | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | regdirbawu@canadalife.ie       | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | regdirrka@canadalife.ie        | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | regdir_coop@canadalife.de      | admin_user@canadalife.ie | Admin    |
      | Director_Admin_Access.yaml | dir_sal_chan_man@canadalife.de | admin_user@canadalife.ie | Admin    |


