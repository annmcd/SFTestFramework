@SFBusinessProcess

Feature: GTWSession Actions permissions and error messages

  @manual
  Scenario: User with edit access can create/retrieve GTWSession
    Given an active Webinar Campaign exists, with a GoToWebinarSession
    And I am a marketing user with edit access to the GoToWebinarSession record
    When I click to create or retrieve the session
    Then the creation/retrieval is successful

  @manual
  Scenario: User without edit access can't create/retrieve GTWSession
    Given an active Webinar Campaign exists, with a GoToWebinarSession
    And I am a marketing user without edit access to the GoToWebinarSession record
    When I click to create or retrieve the session
    Then the session should not be created/updated
    And I should see an appropriate error message

  @manual
  Scenario: User with delete access can cancel GTWSession
    Given an active Webinar Campaign exists, with a GoToWebinarSession
    And I am a marketing user with delete access to the GoToWebinarSession record
    When I click to cancel the session
    Then the cancelation is successful

  @manual
  Scenario: User without delete access can't cancel GTWSession
    Given an active Webinar Campaign exists, with a GoToWebinarSession
    And I am a marketing user without delete access to the GoToWebinarSession record
    When I click to cancel the session
    Then the session should not be cancelled
    And I should see an appropriate error message

  @manual
  Scenario: User without marketing user enabled can't create/edit/delete GTWSession
    Given an active Webinar Campaign exists, with a GoToWebinarSession
    And I am not a marketing user with edit and delete access to the GoToWebinarSession record
    When I click to create, retrieve, or delete the session
    Then the session should not be created/updated/deleted
    And I should see an appropriate error message
