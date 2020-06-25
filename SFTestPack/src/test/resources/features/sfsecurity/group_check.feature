@SFSecurity

Feature: Salesforce Security Matrix - Groups


  Scenario Outline: Verify that Required Groups exist in the org
    Given I have connect to salesforce to verify groups
    When I check existencce of group "<group>" of type "<groupType>"
    Then The group should exist
    Examples:
      | group                   | groupType |
      | BCLeadAdmin             | Regular   |
      | EventMgt                | Regular   |
      | CustomerServices        | Regular   |
      | ReportViewers           | Regular   |
      | StrategischeEntwicklung | Regular   |
      | SalesforceTeam          | Regular   |
      | Veranstaltungsteam      | Regular   |
      | Vertriebscontrolling    | Regular   |
      | Vertriebstechnologie    | Regular   |
      | VertriebSupport         | Regular   |
      | ViewSalesPolicyMembers  | Regular   |
      | VSkomplett              | Regular   |
      | VTO                     | Regular   |

