@SFSecurity
#feature and scenario SAL numbers updated AND ORDERED ALPHABETICALLY + DUPLICATES REMOVED!



Feature: Salesforce Security Matrix - Profiles

  @issues:SAL-4668,SAL-4748,SAL-4669,SAL-4756,SAL-4671,SAL-4672,SAL-4708,SAL-4724
  @issues:SAL-4716,SAL-4732,SAL-4740,SAL-4764,SAL-4772,SAL-4780,SAL-4828,SAL-4670,SAL-4901
  Scenario Outline: Verify that Salesforce Profile settings are as expected in supplied Yaml configuration
    Given I have a profile yamlDefinition "<yamlDefinition>" for profile "<profile>" in the test resources folder
    And I have a connection to a salesforce organisation
    When I check expected security settings for object level
    And I check expected security settings at field level
    And I check expected security settings at administrative level
    And I check expected security settings at general user level
    Then Then they should both match the expected values
    Examples:
      | yamlDefinition                     | profile                          |
      | BrokerConsultant.yaml              | Broker Consultant                |
      | BusinessAdministrator.yaml         | Business Administrator           |
      | BusinessSupport.yaml               | Business Support                 |
      | CLESystemAdministrator.yaml        | CLE System Administrator         |
      | SalesSupport.yaml                  | Sales Support                    |
      | AnalyticsCloudIntegrationUser.yaml | Analytics Cloud Integration User |
      | AnalyticsCloudSecurityUser.yaml    | Analytics Cloud Security User    |

