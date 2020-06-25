@SFBusinessProcess

Feature: CLE Events Portal page - URL access


  @manual
  #Example: https://test-canadalife.cs129.force.com/events/CLE_Events_Portal
  Scenario: Visit the CLE Events Portal with no URL parameters
    When I open the CLE Events Portal page passing in no URL parameters
    Then I should see an Invalid Link message

  @manual
  # Example: https://test-canadalife.cs129.force.com/events/CLE_Events_Portal?donkey=blue
  Scenario: Visit the CLE Events Portal with an invalid URL parameter
    When I open the CLE Events Portal page passing in URL Parameter: "donkey=blue"
    Then I should see an Invalid Link message

  @manual
  # Example: https://test-canadalife.cs129.force.com/events/CLE_Events_Portal?cid=7013O000000DyWNQA0
  Scenario: Visit the CLE Events Portal with a valid Campaign ID
    Given an active workshop event named "event-123" exists with blank capacity and no shared common name
    When I open the registration page for the "event-123" campaign using Campaign Name
    Then I should see the registration form

  @manual
  # Example:   https://test-canadalife.cs129.force.com/events/CLE_Events_Portal?cid=garbage43412423garbage
  Scenario: Visit the CLE Events Portal with an invalid Campaign ID
    When I open the CLE Events Portal page passing in URL Parameter: "cid=garbage43412423garbage"
    Then I should see an Invalid Link message

  @manual
  # Example: https://test-canadalife.cs129.force.com/events/CLE_Events_Portal?cn=sal-3858-minimal
  Scenario: Visit the CLE Events Portal with a valid Campaign ID
    Given an active workshop event named "event-234" exists with blank capacity and no shared common name
    When I open the registration page for the "event-234" campaign using Campaign ID
    Then I should see the registration form

  @manual
  # Example: https://test-canadalife.cs129.force.com/events/CLE_Events_Portal?cn=sal-3858-symbolsinname-%5C%2F%21%22%C2%A3%24%24%25%5E%26%29%28%2A%26%5B%5D%7B%7D%3B%27%23%3A%40~%2C%3C%3E
  Scenario: Visit the CLE Events Portal with a valid Campaign Name containing symbols
    Given an active workshop event named "event-\/!£$$%^&)(*&[]{};'#:@~,<>" exists with blank capacity and no shared common name
    When I open the registration page for the "event-\/!£$$%^&)(*&[]{};'#:@~,<>" campaign using Campaign ID
    Then I should see the registration form

  @manual
  # Example:   https://test-canadalife.cs129.force.com/events/CLE_Events_Portal?cn=garbage43412423garbage
  Scenario: Visit the CLE Events Portal with an invalid Campaign Name
    When I open the CLE Events Portal page passing in URL Parameter: "cn=garbage43412423garbage"
    Then I should see an Invalid Link message

  @manual
  # Example: https://test-canadalife.cs129.force.com/events/CLE_Events_Portal?cn=sal-3858-minimal&banana=true
  Scenario: Visit CLE Events Portal with extra invalid parameter
    Given an active workshop event named "event-345" exists with blank capacity and no shared common name
    When I open the CLE Events Portal page passing in URL Parameter: "cn=event-345"
    Then I should see the registration form

  @manual
  # Requested confirmation that this is correct behaviour on SAL-3858
  Scenario: Visit CLE Events Portal Campaign which has a duplicate Campaign Name
    Given an active workshop event named "event-456" exists with blank capacity and no shared common name
    And an active workshop event named "event-456" exists with blank capacity and no shared common name
    When I open the registration page for the "event-456" campaign using Campaign Name
    Then I should see an Invalid Link message

  @manual
  Scenario: Visit CLE Events Portal for an inactive Campaign
    Given an inactive workshop event named "event-567" exists with blank capacity and no shared common name
    When I open the registration page for the "event-567" campaign using Campaign Name
    Then I should see an Invalid Link message
