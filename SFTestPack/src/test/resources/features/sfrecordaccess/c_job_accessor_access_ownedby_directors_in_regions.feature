@SFRecordAccess

@notPreprod
#@slow
#@ignore
Feature: Access settings for all records owned by Regional Directors

  Background:
    Given I have a salesforce connection to a target environment salesforce org


  Scenario Outline: Verify that All Jobs accessing Records owned by Regional Director Nord have the expected access to records in a Salesforce Org
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The lookup Region is "Nord"
    And The record data file is "CSVRecordData_directors_in_regions.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                           | JobUser                        | Owner                    |
      | Regional_Director_Nord_Access.yaml | bcnord@canadalife.ie           | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | bcbayern@canadalife.ie         | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | bcnrw@canadalife.ie            | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | bcmitte@canadalife.ie          | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | bcbawu@canadalife.ie           | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | regdirnord@canadalife.ie       | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | regdircentral@canadalife.ie    | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | regdirbayern@canadalife.ie     | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | regdirnrw@canadalife.ie        | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | regdirmitte@canadalife.ie      | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | regdirbawu@canadalife.ie       | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | zka1@canadalife.de             | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | zka2@canadalife.de             | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | zka3@canadalife.de             | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | sales_support@canadalife.de    | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | sales_management@canadalife.de | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | german_legal_rep@canadalife.ie | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | controlling_user@canadalife.de | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | vto_user@canadalife.de         | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | businessadmin@canadalife.de    | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | integration.user@canadalife.de | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | sales_director@canadalife.de   | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | admin_user@canadalife.de       | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | regdirrka@canadalife.ie        | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | regdir_coop@canadalife.de      | regdirnord@canadalife.ie |
      | Regional_Director_Nord_Access.yaml | dir_sal_chan_man@canadalife.de | regdirnord@canadalife.ie |


  Scenario Outline: Verify that All Jobs accessing Records owned by Regional  Director Bayern have the expected access to records in a Salesforce Org
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The lookup Region is "Bayern"
    And The record data file is "CSVRecordData_directors_in_regions.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                             | JobUser                        | Owner                      |
      | Regional_Director_Bayern_Access.yaml | bcnord@canadalife.ie           | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | bcbayern@canadalife.ie         | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | bcnrw@canadalife.ie            | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | bcmitte@canadalife.ie          | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | bcbawu@canadalife.ie           | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | regdirnord@canadalife.ie       | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | regdircentral@canadalife.ie    | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | regdirbayern@canadalife.ie     | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | regdirnrw@canadalife.ie        | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | regdirmitte@canadalife.ie      | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | regdirbawu@canadalife.ie       | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | zka1@canadalife.de             | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | zka2@canadalife.de             | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | zka3@canadalife.de             | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | sales_support@canadalife.de    | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | sales_management@canadalife.de | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | german_legal_rep@canadalife.ie | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | controlling_user@canadalife.de | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | vto_user@canadalife.de         | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | businessadmin@canadalife.de    | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | integration.user@canadalife.de | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | sales_director@canadalife.de   | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | admin_user@canadalife.de       | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | regdirrka@canadalife.ie        | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | regdir_coop@canadalife.de      | regdirbayern@canadalife.ie |
      | Regional_Director_Bayern_Access.yaml | dir_sal_chan_man@canadalife.de | regdirbayern@canadalife.ie |

  Scenario Outline: Verify that All Jobs accessing Records owned by Regional  Director NRW have the expected access to records in a Salesforce Org
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The lookup Region is "NRW"
    And The record data file is "CSVRecordData_directors_in_regions.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                          | JobUser                        | Owner                   |
      | Regional_Director_NRW_Access.yaml | bcnord@canadalife.ie           | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | bcbayern@canadalife.ie         | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | bcnrw@canadalife.ie            | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | bcmitte@canadalife.ie          | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | bcbawu@canadalife.ie           | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | regdirnord@canadalife.ie       | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | regdircentral@canadalife.ie    | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | regdirbayern@canadalife.ie     | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | regdirnrw@canadalife.ie        | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | regdirmitte@canadalife.ie      | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | regdirbawu@canadalife.ie       | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | zka1@canadalife.de             | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | zka2@canadalife.de             | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | zka3@canadalife.de             | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | sales_support@canadalife.de    | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | sales_management@canadalife.de | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | german_legal_rep@canadalife.ie | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | controlling_user@canadalife.de | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | vto_user@canadalife.de         | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | businessadmin@canadalife.de    | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | integration.user@canadalife.de | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | sales_director@canadalife.de   | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | admin_user@canadalife.de       | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | regdirrka@canadalife.ie        | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | regdir_coop@canadalife.de      | regdirnrw@canadalife.ie |
      | Regional_Director_NRW_Access.yaml | dir_sal_chan_man@canadalife.de | regdirnrw@canadalife.ie |

  Scenario Outline: Verify that All Jobs accessing Records owned by Regional  Director Mitte have the expected access to records in a Salesforce Org
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The lookup Region is "Mitte"
    And The record data file is "CSVRecordData_directors_in_regions.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                            | JobUser                        | Owner                     |
      | Regional_Director_Mitte_Access.yaml | bcnord@canadalife.ie           | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | bcbayern@canadalife.ie         | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | bcnrw@canadalife.ie            | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | bcmitte@canadalife.ie          | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | bcbawu@canadalife.ie           | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | regdirnord@canadalife.ie       | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | regdircentral@canadalife.ie    | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | regdirbayern@canadalife.ie     | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | regdirnrw@canadalife.ie        | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | regdirmitte@canadalife.ie      | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | regdirbawu@canadalife.ie       | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | zka1@canadalife.de             | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | zka2@canadalife.de             | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | zka3@canadalife.de             | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | sales_support@canadalife.de    | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | sales_management@canadalife.de | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | german_legal_rep@canadalife.ie | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | controlling_user@canadalife.de | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | vto_user@canadalife.de         | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | businessadmin@canadalife.de    | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | integration.user@canadalife.de | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | sales_director@canadalife.de   | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | admin_user@canadalife.de       | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | regdirrka@canadalife.ie        | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | regdir_coop@canadalife.de      | regdirmitte@canadalife.ie |
      | Regional_Director_Mitte_Access.yaml | dir_sal_chan_man@canadalife.de | regdirmitte@canadalife.ie |

  Scenario Outline: Verify that All Jobs accessing Records owned by Regional  Director BaWu have the expected access to records in a Salesforce Org
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The lookup Region is "BAWU"
    And The record data file is "CSVRecordData_directors_in_regions.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                           | JobUser                        | Owner                    |
      | Regional_Director_BAWU_Access.yaml | bcnord@canadalife.ie           | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | bcbayern@canadalife.ie         | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | bcnrw@canadalife.ie            | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | bcmitte@canadalife.ie          | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | bcbawu@canadalife.ie           | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | regdirnord@canadalife.ie       | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | regdircentral@canadalife.ie    | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | regdirbayern@canadalife.ie     | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | regdirnrw@canadalife.ie        | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | regdirmitte@canadalife.ie      | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | regdirbawu@canadalife.ie       | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | zka1@canadalife.de             | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | zka2@canadalife.de             | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | zka3@canadalife.de             | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | sales_support@canadalife.de    | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | sales_management@canadalife.de | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | german_legal_rep@canadalife.ie | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | controlling_user@canadalife.de | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | vto_user@canadalife.de         | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | businessadmin@canadalife.de    | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | integration.user@canadalife.de | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | sales_director@canadalife.de   | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | admin_user@canadalife.de       | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | regdirrka@canadalife.ie        | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | regdir_coop@canadalife.de      | regdirbawu@canadalife.ie |
      | Regional_Director_BAWU_Access.yaml | dir_sal_chan_man@canadalife.de | regdirbawu@canadalife.ie |


  Scenario Outline: Verify that All Jobs accessing Records owned by Regional Director Central have the expected access to records in a Salesforce Org
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The lookup Region is "Central"
    And The record data file is "CSVRecordData_directors_in_regions.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                                    | JobUser                        | Owner                       |
      | Regional_Director_Central_Sales_Access.yaml | bcnord@canadalife.ie           | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | bcbayern@canadalife.ie         | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | bcnrw@canadalife.ie            | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | bcmitte@canadalife.ie          | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | bcbawu@canadalife.ie           | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | regdirnord@canadalife.ie       | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | regdircentral@canadalife.ie    | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | regdirbayern@canadalife.ie     | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | regdirnrw@canadalife.ie        | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | regdirmitte@canadalife.ie      | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | regdirbawu@canadalife.ie       | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | zka1@canadalife.de             | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | zka2@canadalife.de             | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | zka3@canadalife.de             | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | sales_support@canadalife.de    | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | sales_management@canadalife.de | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | german_legal_rep@canadalife.ie | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | controlling_user@canadalife.de | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | vto_user@canadalife.de         | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | businessadmin@canadalife.de    | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | integration.user@canadalife.de | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | sales_director@canadalife.de   | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | admin_user@canadalife.de       | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | regdirrka@canadalife.ie        | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | regdir_coop@canadalife.de      | regdircentral@canadalife.ie |
      | Regional_Director_Central_Sales_Access.yaml | dir_sal_chan_man@canadalife.de | regdircentral@canadalife.ie |

  Scenario Outline: Verify that All Jobs accessing Records owned by Regional Director Central have the expected access to records in a Salesforce Org
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The lookup Region is "Kooperationen 1"
    And The record data file is "CSVRecordData_directors_in_regions.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                                  | JobUser                        | Owner                     |
      | Regional_Director_CoOperation_Access.yaml | bcnord@canadalife.ie           | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | bcbayern@canadalife.ie         | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | bcnrw@canadalife.ie            | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | bcmitte@canadalife.ie          | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | bcbawu@canadalife.ie           | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | regdirnord@canadalife.ie       | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | regdircentral@canadalife.ie    | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | regdirbayern@canadalife.ie     | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | regdirnrw@canadalife.ie        | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | regdirmitte@canadalife.ie      | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | regdirbawu@canadalife.ie       | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | zka1@canadalife.de             | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | zka2@canadalife.de             | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | zka3@canadalife.de             | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | sales_support@canadalife.de    | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | sales_management@canadalife.de | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | german_legal_rep@canadalife.ie | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | controlling_user@canadalife.de | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | vto_user@canadalife.de         | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | businessadmin@canadalife.de    | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | integration.user@canadalife.de | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | sales_director@canadalife.de   | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | admin_user@canadalife.de       | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | regdirrka@canadalife.ie        | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | regdir_coop@canadalife.de      | regdir_coop@canadalife.de |
      | Regional_Director_CoOperation_Access.yaml | dir_sal_chan_man@canadalife.de | regdir_coop@canadalife.de |


  Scenario Outline: Verify that All Jobs accessing Records owned by Regional Director Central have the expected access to records in a Salesforce Org
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The lookup Region is "regionale ka"
    And The record data file is "CSVRecordData_directors_in_regions.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                          | JobUser                        | Owner                     |
      | Regional_Director_RKA_Access.yaml | bcnord@canadalife.ie           | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | bcbayern@canadalife.ie         | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | bcnrw@canadalife.ie            | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | bcmitte@canadalife.ie          | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | bcbawu@canadalife.ie           | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | regdirnord@canadalife.ie       | regdir_coop@canadalife.de |
      | Regional_Director_RKA_Access.yaml | regdircentral@canadalife.ie    | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | regdirbayern@canadalife.ie     | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | regdirnrw@canadalife.ie        | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | regdirmitte@canadalife.ie      | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | regdirbawu@canadalife.ie       | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | zka1@canadalife.de             | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | zka2@canadalife.de             | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | zka3@canadalife.de             | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | sales_support@canadalife.de    | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | sales_management@canadalife.de | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | german_legal_rep@canadalife.ie | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | controlling_user@canadalife.de | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | vto_user@canadalife.de         | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | businessadmin@canadalife.de    | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | integration.user@canadalife.de | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | sales_director@canadalife.de   | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | admin_user@canadalife.de       | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | regdirrka@canadalife.ie        | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | regdir_coop@canadalife.de      | regdirrka@canadalife.ie   |
      | Regional_Director_RKA_Access.yaml | dir_sal_chan_man@canadalife.de | regdirrka@canadalife.ie   |


