@SFBulkData



#Files will be marked for deletion during upload, The delete Test Below will execute to delete items
 # in the order that they appear in the yaml config files
@ignore

Feature: SFBulkData Development, Test Response Codes


  Scenario Outline: I want to verify SFBulkDatas ability to upload csv data reliably
    Given I know the order of upload and delete
    And I have Yaml Configfile "<yamlConfigFile>" configured with entities for the relevant test
    And Log4J will log to"<log4JDir>"
    When I trigger SFBulkData for upload
    Then I expect the appropriate responseCode "<responseCode>"
    Examples:
      | yamlConfigFile                     | responseCode |
      | sfbulkdata_uploads_config_rc0.yaml | 0            |


  Scenario Outline: I want to verify that the latest deployed version of SFBulkData Deletes requested records
    Given I know the order of upload and delete
    And I have Yaml Configfile "<yamlConfigFile>" configured with entities for the relevant test
    And Log4J will log to"<log4JDir>"
    When I trigger SFBulkData for delete
    Then I expect the appropriate responseCode "<responseCode>"
    Examples:
      | yamlConfigFile                     | responseCode |
      | sfbulkdata_deletes_config_rc0.yaml | 0            |
      | sfbulkdata_deletes_config_rc1.yaml | 1            |






