@SFSecurity


Feature:Salesforce Security Matrix - SharingRules


  Scenario Outline: Verify that Salesforce Sharing Rules settings are as expected in supplied in the Entity Yaml configuration file
    Given I have a sharingRules yamlDefinition "<yamlDefinition>" for objectType "<objectType>" in the test resources folder
    And I have a connection to a valid salesforce org
    When I check the expected settings versus actual settings
    Then Then the rule settings should be equal
    Examples:
      | yamlDefinition               | objectType              |
      | Account.yaml                 | Account                 |
      | Contact.yaml                 | Contact                 |
      | Commission_Agreement__c.yaml | Commission_Agreement__c |
      | Target__c.yaml               | Target__c               |
      | Lead.yaml                    | Lead                    |