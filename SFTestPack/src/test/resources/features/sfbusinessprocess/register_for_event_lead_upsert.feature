@SFBusinessProcess

Feature: Lead upsert from event registration form

  @manual
  @issue:SAL-4403
  Scenario: Lead upsert from event registration form
  Given an active workshop event named "Active-wCapacity" exists with capacity "20" and no shared common name
  When I open the registration page for the "Active-wCapacity" campaign
  Then if should observe the following behaviour upon submitting the form:
  | VVID Value                | Email Value               | Result                                                    |
  | Blank                     | Blank                     | Campaign Member created, New lead created                 |
  | Blank                     | Non-match                 | Campaign Member created, New lead created                 |
  | Blank                     | match existing Lead       | Campaign Member created, Existing lead is updated         |
  | Blank                     | Match existing Contact    | Campaign Member created, Existing contact is updated      |
  | Non-match                 | Blank                     | Campaign Member created, New lead created                 |
  | Non-match                 | Non-match                 | Campaign Member created, New lead created                 |
  | Non-match                 | Match existing Lead       | Campaign Member created, Existing lead is updated         |
  | Non-match                 | Match existing Contact    | Campaign Member created, Existing contact is updated      |
  | Match existing Lead       | Blank                     | Campaign Member created, Existing lead is updated         |
  | Match existing Lead       | Non-match                 | Campaign Member created, Existing lead is updated         |
  | Match existing Lead       | Match existing Lead       | Campaign Member created, Existing lead is updated         |
  | Match existing Contact    | Blank                     | Campaign Member created, Existing contact is updated      |
  | Match existing Contact    | Non-match                 | Campaign Member created, Existing contact is updated      |
  | Match existing Contact    | Match existing Contact    | Campaign Member created, Existing contact is updated      |
  | Match existing Contact(A) | Match existing Contact(B) | Campaign Member created, Existing contact/lead is updated |
  | Match existing Contact(A) | Match existing Lead(B)    | Campaign Member created, Existing contact/lead is updated |
  | Match existing Lead(A)    | Match existing Contact(B) | Campaign Member created, Existing contact/lead is updated |
  | Match existing Lead(A)    | Match existing Contact(B) | Campaign Member created, Existing contact/lead is updated |
    # Note: Updating a lead registrant involves new CampaignRegisteredTo__c value
