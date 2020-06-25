@SFRecordAccess

@notPreprod
#@slow
#@ignore
Feature: Access settings for all records owned by broker consultants

  Background:
    Given I have a salesforce connection to a target environment salesforce org



  Scenario Outline: Verify that All Jobs accessing Records owned by Broker Consultant Nord have the expected access to records in a Salesforce Org
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The lookup Region is "Nord"
    And The record data file is "CSVRecordData_broker_consultant.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                           | JobUser                        | Owner                |
      | Broker_Consultant_Nord_Access.yaml | bcnord@canadalife.ie           | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | bcbayern@canadalife.ie         | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | bcnrw@canadalife.ie            | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | bcmitte@canadalife.ie          | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | bcbawu@canadalife.ie           | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | regdirnord@canadalife.ie       | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | regdircentral@canadalife.ie    | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | regdirbayern@canadalife.ie     | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | regdirnrw@canadalife.ie        | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | regdirmitte@canadalife.ie      | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | regdirbawu@canadalife.ie       | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | zka1@canadalife.de             | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | zka2@canadalife.de             | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | zka3@canadalife.de             | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | sales_support@canadalife.de    | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | sales_management@canadalife.de | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | german_legal_rep@canadalife.ie | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | controlling_user@canadalife.de | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | vto_user@canadalife.de         | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | businessadmin@canadalife.de    | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | integration.user@canadalife.de | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | sales_director@canadalife.de   | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | admin_user@canadalife.de       | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | regdirrka@canadalife.ie        | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | regdir_coop@canadalife.de      | bcnord@canadalife.ie |
      | Broker_Consultant_Nord_Access.yaml | dir_sal_chan_man@canadalife.de | bcnord@canadalife.ie |


  Scenario Outline: Verify that All Jobs accessing Records owned by Broker Consultant Bayern have the expected access to records in a Salesforce Org
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The lookup Region is "Bayern"
    And The record data file is "CSVRecordData_broker_consultant.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                             | JobUser                        | Owner                  |
      | Broker_Consultant_Bayern_Access.yaml | bcnord@canadalife.ie           | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | bcbayern@canadalife.ie         | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | bcnrw@canadalife.ie            | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | bcmitte@canadalife.ie          | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | bcbawu@canadalife.ie           | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | regdirnord@canadalife.ie       | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | regdircentral@canadalife.ie    | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | regdirbayern@canadalife.ie     | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | regdirnrw@canadalife.ie        | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | regdirmitte@canadalife.ie      | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | regdirbawu@canadalife.ie       | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | zka1@canadalife.de             | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | zka2@canadalife.de             | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | zka3@canadalife.de             | bcentral@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | sales_support@canadalife.de    | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | sales_management@canadalife.de | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | german_legal_rep@canadalife.ie | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | controlling_user@canadalife.de | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | vto_user@canadalife.de         | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | businessadmin@canadalife.de    | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | integration.user@canadalife.de | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | sales_director@canadalife.de   | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | admin_user@canadalife.de       | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | regdirrka@canadalife.ie        | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | regdir_coop@canadalife.de      | bcbayern@canadalife.ie |
      | Broker_Consultant_Bayern_Access.yaml | dir_sal_chan_man@canadalife.de | bcbayern@canadalife.ie |


  Scenario Outline: Verify that All Jobs accessing Records owned by Broker Consultant NRW have the expected access to records in a Salesforce Org
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The lookup Region is "NRW"
    And The record data file is "CSVRecordData_broker_consultant.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                          | JobUser                        | Owner               |
      | Broker_Consultant_NRW_Access.yaml | bcnord@canadalife.ie           | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | bcbayern@canadalife.ie         | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | bcnrw@canadalife.ie            | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | bcmitte@canadalife.ie          | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | bcbawu@canadalife.ie           | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | regdirnord@canadalife.ie       | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | regdircentral@canadalife.ie    | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | regdirbayern@canadalife.ie     | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | regdirnrw@canadalife.ie        | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | regdirmitte@canadalife.ie      | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | regdirbawu@canadalife.ie       | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | zka1@canadalife.de             | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | zka2@canadalife.de             | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | zka3@canadalife.de             | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | sales_support@canadalife.de    | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | sales_management@canadalife.de | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | german_legal_rep@canadalife.ie | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | controlling_user@canadalife.de | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | vto_user@canadalife.de         | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | businessadmin@canadalife.de    | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | integration.user@canadalife.de | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | sales_director@canadalife.de   | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | admin_user@canadalife.de       | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | regdirrka@canadalife.ie        | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | regdir_coop@canadalife.de      | bcnrw@canadalife.ie |
      | Broker_Consultant_NRW_Access.yaml | dir_sal_chan_man@canadalife.de | bcnrw@canadalife.ie |


  Scenario Outline: Verify that All Jobs accessing Records owned by BrokerConsultant Mitte have the expected access to records in a Salesforce Org
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The lookup Region is "Mitte"
    And The record data file is "CSVRecordData_broker_consultant.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                            | JobUser                        | Owner                 |
      | Broker_Consultant_Mitte_Access.yaml | bcnord@canadalife.ie           | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | bcbayern@canadalife.ie         | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | bcnrw@canadalife.ie            | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | bcmitte@canadalife.ie          | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | bcbawu@canadalife.ie           | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | regdirnord@canadalife.ie       | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | regdircentral@canadalife.ie    | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | regdirbayern@canadalife.ie     | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | regdirnrw@canadalife.ie        | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | regdirmitte@canadalife.ie      | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | regdirbawu@canadalife.ie       | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | zka1@canadalife.de             | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | zka2@canadalife.de             | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | zka3@canadalife.de             | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | sales_support@canadalife.de    | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | sales_management@canadalife.de | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | german_legal_rep@canadalife.ie | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | controlling_user@canadalife.de | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | vto_user@canadalife.de         | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | businessadmin@canadalife.de    | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | integration.user@canadalife.de | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | sales_director@canadalife.de   | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | admin_user@canadalife.de       | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | regdirrka@canadalife.ie        | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | regdir_coop@canadalife.de      | bcmitte@canadalife.ie |
      | Broker_Consultant_Mitte_Access.yaml | dir_sal_chan_man@canadalife.de | bcmitte@canadalife.ie |


  Scenario Outline: Verify that All Jobs accessing Records owned by Broker Consultant Bawu have the expected access to records in a Salesforce Org
    Given I check that the connection is valid
    And I have a yamlFile "<YamlFile>" with the expected settings per entity type
    And The lookup Region is "BAWU"
    And The record data file is "CSVRecordData_broker_consultant.yaml"
    When I verify run a verification on record access
    Then I expect the access on records owned by owner "<Owner>" to match the expected settings  represented by test account "<JobUser>"
    Examples:
      | YamlFile                           | JobUser                        | Owner                |
      | Broker_Consultant_BAWU_Access.yaml | bcnord@canadalife.ie           | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | bcbayern@canadalife.ie         | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | bcnrw@canadalife.ie            | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | bcmitte@canadalife.ie          | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | bcbawu@canadalife.ie           | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | regdirnord@canadalife.ie       | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | regdircentral@canadalife.ie    | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | regdirbayern@canadalife.ie     | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | regdirnrw@canadalife.ie        | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | regdirmitte@canadalife.ie      | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | regdirbawu@canadalife.ie       | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | zka1@canadalife.de             | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | zka2@canadalife.de             | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | zka3@canadalife.de             | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | sales_support@canadalife.de    | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | sales_management@canadalife.de | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | german_legal_rep@canadalife.ie | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | controlling_user@canadalife.de | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | vto_user@canadalife.de         | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | businessadmin@canadalife.de    | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | integration.user@canadalife.de | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | sales_director@canadalife.de   | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | admin_user@canadalife.de       | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | regdirrka@canadalife.ie        | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | regdir_coop@canadalife.de      | bcbawu@canadalife.ie |
      | Broker_Consultant_BAWU_Access.yaml | dir_sal_chan_man@canadalife.de | bcbawu@canadalife.ie |