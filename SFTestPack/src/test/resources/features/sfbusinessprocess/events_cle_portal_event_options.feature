@SFBusinessProcess

  Feature: CLE Events Portal page - dietary requirements, hotel room, and VVID number status.


  @manual
  Scenario: ConfirmRegistration & IsRestrictedCampaign fields do not affect the registration form
   Given the following Campaign exists:
    | field                  | value                    |
    | campaign name          | event-reg-res            |
    | active                 | true                     |
    | type                   | Workshops (single event) |
    | confirm registration   | true                     |
    | is restricted campaign | true                     |
    | fa-db transaction      | 1904.917                 |
    | wbi number             | P00000000                |
    When I open the registration page for the "event-reg-res" campaign using Campaign Name
    Then I should see the registration form with the expected basic fields

  @manual
  Scenario: Registration form with dietary requirement enabled
    Given the following Campaign exists:
    | field                  | value                    |
    | campaign name          | event-diet               |
    | active                 | true                     |
    | type                   | Workshops (single event) |
    | dietry requirement     | true                     |
    | vvidnumber             | false                    |
    | need hotel room        | false                    |
    | fa-db transaction      | 1904.917                 |
    | wbi number             | P00000000                |
    When I open the registration page for the "event-diet" campaign using Campaign Name
    Then I should see the registration form with the expected basic fields as well as:
    | Field                  | present |
    | VVID Nummer            | false   |
    | Hotelzimmer benötigt   | false   |
    | Besondere Lebensmittel | true    |

  @manual
  Scenario: Registration form with VVID Number enabled
    Given the following Campaign exists:
    | field                  | value                    |
    | campaign name          | event-vvid               |
    | active                 | true                     |
    | type                   | Workshops (single event) |
    | dietry requirement     | false                    |
    | vvidnumber             | true                     |
    | need hotel room        | false                    |
    | fa-db transaction      | 1904.917                 |
    | wbi number             | P00000000                |
    When I open the registration page for the "event-vvid" campaign using Campaign Name
    Then I should see the registration form with the expected basic fields as well as:
    | Field                  | present |
    | VVID Nummer            | true    |
    | Hotelzimmer benötigt   | false   |
    | Besondere Lebensmittel | false   |

  @manual
  Scenario: Registration form with Need hotel room enabled
    Given the following Campaign exists:
    | field                  | value                    |
    | campaign name          | event-hotel              |
    | active                 | true                     |
    | type                   | Workshops (single event) |
    | dietry requirement     | false                    |
    | vvidnumber             | false                    |
    | need hotel room        | true                     |
    | fa-db transaction      | 1904.917                 |
    | wbi number             | P00000000                |
    When I open the registration page for the "event-hotel" campaign using Campaign Name
    Then I should see the registration form with the expected basic fields as well as:
    | Field                  | present |
    | VVID Nummer            | false   |
    | Hotelzimmer benötigt   | true    |
    | Besondere Lebensmittel | false   |

  @manual
  Scenario: Registration form with diet, vvid, and hotel options enabled
    Given the following Campaign exists:
    | field                  | value                    |
    | campaign name          | event-hotel              |
    | active                 | true                     |
    | type                   | Workshops (single event) |
    | dietry requirement     | true                     |
    | vvidnumber             | true                     |
    | need hotel room        | true                     |
    | fa-db transaction      | 1904.917                 |
    | wbi number             | P00000000                |
    When I open the registration page for the "event-hotel" campaign using Campaign Name
    Then I should see the registration form with the expected basic fields as well as:
    | Field                  | present |
    | VVID Nummer            | true    |
    | Hotelzimmer benötigt   | true    |
    | Besondere Lebensmittel | true    |
