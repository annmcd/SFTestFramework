@ETLValidation

  #CLEIgore the ignore and add to the test Runner
@CLEIgnore
@ignore

Feature: Entity ETL Feature - Verify Records before CSV Export
  Description: Requirement is to ensure that no records are elected for export to csv files if they are orphans

  Scenario Outline: All records must have a valid parent record(s) in its salesforce hierarchy
    Given I  have a connection to the staging database
    When I trigger the verification
    Then I expect that test type "<TestType>" will be verified by stored procedure "<StoredProcedure>"
    Examples:
      | TestType    | StoredProcedure |
      | MyTestType1 | MyStoredProc()    |


# scenario added here temporarily as double ignore is not being honoured  when 2 feature files are included in
  #a feature folder to be ussed by ETL Export output only

  Scenario Outline: As an Engineer I need to verify that a csv file has some basic  formatting requirements to ensure it is valid for upload
    Given I  have an ETL Export Facility
    When I trigger etlexport
    Then I expect to have a csv file "<fileName>" created in the environment csv export folder
    And csv records contain no unclosed single or double quotes
    And the output CSV files column values contain no leading or trailing spaces
    And the output CSV files last column contains no trailing comma
    And the output CSV files contain data and not just data headers where data is required "<dataRequired>"
    Examples:
      | fileName                           | dataRequired |
      | AccountTeamMembers.csv             | false        |
      | AgreementLineItem.csv              | false        |
      | BAHierarchy.csv                    | false        |
      | BKGISBrokerContact.csv             | false        |
      | BKGISBrokerContactRelationship.csv | false        |
      | BrokerAccount.csv                  | false        |
      | BrokerConsultantAccount.csv        | false        |
      | CANetworkHierachy.csv              | false        |
      | CommissionAgreement.csv            | false        |
      | CommissionPayment.csv              | false        |
      | ContactAccountTypes.csv            | false        |
      | DeleteAccountTeamMembers.csv       | false        |
      | DeleteOpportunityLineItem.csv      | false        |
      | DummyTarget.csv                    | false        |
      | IHKNumber.csv                      | false        |
      | iSuiteTask.csv                     | false        |
      | NewPolicy.csv                      | false        |
      | OpportunityLineItem.csv            | false        |
      | OpportunityParallel.csv            | false        |
      | OpportunitySeries.csv              | false        |
      | PCAccountTeamMembers.csv           | false        |
      | Policy.csv                         | false        |
      | PolicyApplication.csv              | false        |
      | PolicyBusinessAccount.csv          | false        |
      | PolicyContact.csv                  | false        |
      | PolicyContactRelationship.csv      | false        |
      | PricebookEntry.csv                 | false        |
      | product.csv                        | false        |
      | Region.csv                         | false        |
      | StatementBroker.csv                | false        |
      | Target.csv                         | false        |
