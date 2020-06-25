@SFBusinessProcess

Feature: Testing the creation of Campaigns of type Workshop

  @manual
  Scenario: Workshop Campaign with only mandatory fields entered can be saved
    Given I have logged into Salesforce Org as "SalesSupport"
    When I create a new campaign with details:
      | field             | value                   |
      | name              | Communications Workshop |
      | campaign type     | Workshop                |
      | FA-DB Transaction | 1904.917                |
      | WBI Number        | P00000000               |
    Then the "Communications Workshop" campaign should be available in the Campaign List view

  @manual
  Scenario: Minimally populated Workshop Campaign details with defaults are stored correctly
    Given I have logged into Salesforce Org as "SalesSupport"
    When I create a new campaign with details:
      | field             | value     |
      | name              | Minimal   |
      | campaign type     | Workshop  |
      | FA-DB Transaction | 1904.917  |
      | WBI Number        | P00000000 |
    Then the "Minimal" campaign should contain the following details:
      | field                  | value                    |
      | campaign name          | Minimal                  |
      | active                 | false                    |
      | parent campaign        |                          |
      | event common name      |                          |
      | type                   | Workshops (single event) |
      | training type          |                          |
      | document url           |                          |
      | survey url             |                          |
      | campaign content       |                          |
      | Campaign Owner         |                          |
      | Trainer Salutation     |                          |
      | Trainer First name     |                          |
      | Trainer Phone          |                          |
      | Trainer EMail          |                          |
      | confirm registration   | false                    |
      | is restricted campaign | false                    |
      | event capacity         |                          |
      | gut beraten points     |                          |
      | dietry requirement     | false                    |
      | vvidnumber             | false                    |
      | need hotel room        | false                    |
      | status                 |                          |
      | start date             |                          |
      | end date               |                          |
      | goal                   |                          |
      | description            |                          |
      | fa-db transaction      | 1904.917                 |
      | wbi number             | P00000000                |
      | Campaign Owner         | David Kennedy            |
  #Event Location
      | location name          |                          |
      | address extension      |                          |
      | street                 |                          |
      | zip code               |                          |
      | city                   |                          |
      | website                |                          |
  #Event details
      | registration url       |                          |
  #Webinar Details
      | webinar start          |                          |
      | min attendance dur     |                          |
      | dur of participation   |                          |
  #Planning
      | Number sent            | 0                        |
      | expected response      | 0.00%                    |
      | budget cost            |                          |
      | actual cost            |                          |
      | expected revenue       |                          |

  @manual
  Scenario: Maximally populated Workshop Campaign details are stored correctly
    Given I have logged into Salesforce Org as "SalesSupport"
    When I create a new campaign with details:
      | field                     | value                   |
      | Campaign Name             | Maximally-Populated-01  |
      | Active                    | true                    |
      | Parent Campaign           | david-test-campaign-000 |
      | Event Common Name         | jupiter32               |
      | Type                      | Workshop                |
      | Training Type             | Classroom Training      |
      | Document URL              | http://www.doc.url      |
      | survey URL                | http://www.surcey.url   |
      | Campaign Content          | Privat-- Life- /Pension |
      | Contact On Questions      | David Kennedy           |
      | Confirm Registration      | true                    |
      | Is Restricted Campaign    | true                    |
      | Event capacity            | 100                     |
      | Gut Beraten Points        | 15                      |
      | Dietry                    | true                    |
      | VVID                      | true                    |
      | Need hotel                | true                    |
      | Campaign Owner            | David Kennedy           |
      | Status                    | Planned                 |
      | Start Date                | 4/1/2020                |
      | End Date                  | 4/30/2020               |
      | Goal                      | Image/Brand             |
      | Description               | This is a description   |
      | FA-DB Transaction         | 1973.123                |
      | WBI Number                | P12345678               |
  #Event Location
      | Location name             | Hall Room               |
      | Address Extension         | extended                |
      | Street                    | yellow street           |
      | Zip Code                  | 123ZC                   |
      | City                      | Red city                |
      | Website                   | www.web.site            |
  #Event details
      | Registration URL          | www.event.reg.url       |
  #Webinar Details
      | Webinar Start Date        | 4/15/2020               |
      | Webinar Time              | 5:00 PM                 |
      | Min attendance duration   | 123                     |
      | Duration of participation | 234                     |
  #Planning
      | Num Sent                  | 343                     |
      | Expected Response         | 12.42%                  |
      | Budgeted Cost             | 1m                      |
      | Actual Cost               | 0.5m                    |
      | Expected Revenue          | 2.5m                    |
    Then the "Minimal" campaign should be available in the Campaign List view
    And the "Minimal" campaign should contain the following details:
      | field                     | value                   |
      | Campaign Name             | Maximally-Populated-01  |
      | Active                    | true                    |
      | Parent Campaign           | david-test-campaign-000 |
      | Event Common Name         | jupiter32               |
      | type                      | Workshops (single event)|
      | Training Type             | Classroom Training      |
      | Document URL              | http://www.doc.url      |
      | survey URL                | http://www.surcey.url   |
      | Campaign Content          | Privat-- Life- /Pension |
      | Contact On Questions      | David Kennedy           |
      | Trainer Salutation        | <contact.title>         |
      | Trainer First name        | <contact.firstname>     |
      | Trainer Phone             | <contact.phone>         |
      | Trainer EMail             | <contact.email>         |
      | Confirm Registration      | true                    |
      | Is Restricted Campaign    | true                    |
      | Event capacity            | 100                     |
      | Gut Beraten Points        | 15                      |
      | Dietry                    | true                    |
      | VVID                      | true                    |
      | Need hotel                | true                    |
      | Status                    | Planned                 |
      | Start Date                | 4/1/2020                |
      | End Date                  | 4/30/2020               |
      | Goal                      | Image/Brand             |
      | Description               | This is a description   |
      | FA-DB Transaction         | 1973.123                |
      | WBI Number                | P12345678               |
      | Campaign Owner            | David Kennedy           |
  #Event Location
      | location name             | Hall Room               |
      | address extension         | extended                |
      | street                    | yellow street           |
      | zip code                  | 123ZC                   |
      | city                      | Red city                |
      | website                   | www.web.site            |
  #Event details
      | registration url          | www.event.reg.url       |
  #Webinar Details
      | webinar start             | 4/15/2020 5:00 PM       |
      | min attendance dur        | 123                     |
      | dur of participation      | 234                     |
  #Planning
      | Number sent               | 343                     |
      | expected response         | 12.42%                  |
      | budget cost               | 1m                      |
      | actual cost               | 0.5m                    |
      | expected revenue          | 2.5m                    |

  @manual
  #Derived from R2-S1
  Scenario: Creating an Active Workshop Campaign with capacity, no shared common name
    Given I have logged into Salesforce Org as "SalesSupport"
    When I create a new campaign with details:
      | field                     | value                     |
      | Campaign Name             | Active-wCapacity-woCommon |
      | Active                    | true                      |
      | Event Common Name         |                           |
      | Event capacity            | 20                        |
    Then the "Active-wCapacity-woCommon" campaign should be available in the Campaign List view
    And the "Active-wCapacity-woCommon" campaign should contain the following details:
      | field                     | value                     |
      | Campaign Name             | Active-wCapacity-woCommon |
      | Active                    | true                      |
      | Event Common Name         |                           |
      | Event capacity            | 20                        |

  @manual
  #Derived from R2-S2
  Scenario: Creating an Active Workshop Campaign with no capacity, no shared common name
    Given I have logged into Salesforce Org as "SalesSupport"
    When I create a new campaign with details:
      | field                     | value                      |
      | Campaign Name             | Active-woCapacity-woCommon |
      | Active                    | true                       |
      | Event Common Name         |                            |
      | Event capacity            |                            |
    Then the "Active-woCapacity-woCommon" campaign should be available in the Campaign List view
    And the "Active-woCapacity-woCommon" campaign should contain the following details:
      | field                     | value                      |
      | Campaign Name             | Active-woCapacity-woCommon |
      | Active                    | true                       |
      | Event Common Name         |                            |
      | Event capacity            |                            |

  @manual
  #Derived from R2-S3
  Scenario: Creation of unrestricted Workshop Campaign with a shared Event Common Name
    Given I have logged into Salesforce Org as "SalesSupport"
    And I create a new campaign with details:
    | field               | value                      |
    | name                | Communications Workshop 31 |
    | active              | true                       |
    | campaign type       | Workshop                   |
    | dietary requirement | true                       |
    | vvid number         | true                       |
    | need hotel room     | true                       |
    | capacity            | 20                         |
    | event common name   | common-1                   |
    When I create a new campaign with details:
    | field               | value                      |
    | name                | Business Workshop 31       |
    | active              | true                       |
    | campaign type       | Workshop                   |
    | dietary requirement | true                       |
    | vvid number         | true                       |
    | need hotel room     | true                       |
    | capacity            | 20                         |
    | event common name   | common-1                   |
    Then the "Business Workshop" campaign should be available in the Campaign List view
    And the "Business Workshop" campaign should contain the following details:
    | field               | value                      |
    | name                | Business Workshop 31       |
    | active              | true                       |
    | campaign type       | Workshop                   |
    | dietary requirement | true                       |
    | vvid number         | true                       |
    | need hotel room     | true                       |
    | capacity            | 20                         |
    | event common name   | common-1                   |

  @manual
  #Derived from R2-S4
  Scenario: Creation of unrestricted inactive Workshop Campaign
    Given I have logged into Salesforce Org as "SalesSupport"
    When I create a new campaign with details:
      | field               | value                   |
      | name                | Communications Workshop |
      | active              | false                   |
      | campaign type       | Workshop                |
      | dietary requirement | true                    |
      | vvid number         | true                    |
      | need hotel room     | true                    |
      | capacity            | 20                      |
      | event common name   |                         |
    Then the "Communications Workshop" campaign should be available in the Campaign List view
    And the "Communications Workshop" campaign should contain the following details:
      | field               | value                   |
      | name                | Communications Workshop |
      | active              | false                   |
      | campaign type       | Workshop                |
      | dietary requirement | true                    |
      | vvid number         | true                    |
      | need hotel room     | true                    |
      | capacity            | 20                      |
      | event common name   |                         |
