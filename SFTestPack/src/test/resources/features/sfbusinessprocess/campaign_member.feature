@SFBusinessProcess


Feature: Campaign Member and Lead creation

  @manual
  Scenario: New Lead details are stored when a user registers for an event
    Given an active workshop event named "event-new-lead" exists with blank capacity and no shared common name
    When a new user has registered for the "event-new-lead" campaign
    Then a new Lead record should be created with the expected CampaignRegisteredTo and CampaignMemberID values

  @manual
  Scenario: New lead is of type Event_Mgt
    Given an active workshop event named "event-leadType" exists with blank capacity and no shared common name
    When a new user has registered for the "event-leadType" campaign
    Then a new Campaign Member record should be created
    And the associated Lead record is created of Lead Record Type "Event_Mgt"

  @manual
  Scenario: New Lead with unknown contact details, email not configured
    Given an active workshop event named "event-newLeadUnknown" exists with blank capacity and no shared common name
    And the campaign "event-newLeadUnknown" does not have the "Registered" Campaign Member Status
    When a new user has registered for the "event-newLeadUnknown" campaign
    Then a Campaign Member record should be created with the expected CampaignID, LeadID, blank ContactID, and status: "Sent"
    And no email should be sent to the registrant

  @manual
  Scenario: New Lead with known contact details, email not configured
    Given an active workshop event named "event-newLeadKnownContact" exists with blank capacity and no shared common name
    And the campaign "event-newLeadKnownContact" does not have the "Registered" Campaign Member Status
    When a new user has registered for the "event-newLeadKnownContact" campaign
    Then a Campaign Member record should be created with the expected CampaignID, LeadID, populated ContactID, and status "Sent"
    And no email should be sent to the registrant


  @manual
  Scenario: New Lead with unknown contact details, email is configured
    Given an active workshop event named "event-newLeadUnknown" exists with blank capacity and no shared common name
    And the campaign "event-newLeadUnknown" does have the "Registered" Campaign Member Status
    When a new user has registered for the "event-newLeadUnknown" campaign
    Then a Campaign Member record should be created with the expected CampaignID, LeadID, blank ContactID, and status: "Registered"
    And no email should be sent to the registrant

  @manual
  Scenario: New Lead with known contact details, email is configured
    Given an active workshop event named "event-newLeadKnownContact" exists with blank capacity and no shared common name
    And the campaign "event-newLeadKnownContact" does have the "Registered" Campaign Member Status
    When a new user has registered for the "event-newLeadKnownContact" campaign
    Then a Campaign Member record should be created with the expected CampaignID, LeadID, populated ContactID, and status "Registered"
    And no email should be sent to the registrant

  @manual
  Scenario: Existing Lead updates details
    Given an active workshop event named "event-knownLead" exists with blank capacity and no shared common name
    When an existing Lead registers for the  "event-knownLead" campaign with details:
    | field  | value  |
    | mobile | 998877 |
    Then the existing Lead record is updated to include:
    | field  | value  |
    | mobile | 998877 |
