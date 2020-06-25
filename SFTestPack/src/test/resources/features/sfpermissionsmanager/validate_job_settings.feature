@SFPermissionsManager

@notPreprod

Feature:Salesforce Security Matrix - CLE Job and User Settings Verification


  Scenario Outline: Compare expected security entity assignments (Role,Profile,Group,PermissionSet) for a user against settings in a Salesforce Org
    Given I have a connection to specified staging environment
    And I have a connection to a specified Salesforce Org as admin
    When I execute a user refresh of that environment
    Then I expect that the "<User>" settings for "<JobFunction>" to be updated
    And the users profile "<Profile>" is the same as the Salesforce Settings
    And the users role "<Role>" is the same as their Salesforce Settings
    And the users permissionsets "<PermissionSets>" are the same as their Salesforce Settings
    And that users who are indicated as requiring a license "<LicenseName>" have that license
    Examples:
      | User                            | JobFunction                       | Profile                  | Role                          | PermissionSets                                                                                        | LicenseName        |
      | vto_user@canadalife.de          | VTO                               | Business Support         | Full Access Germany           | View_Commission_Payments,View_Commission_Agreements,View_Targets                                      |                    |
      | controlling_user@canadalife.de  | Controlling                       | Business Support         | Full Access Germany           | View_Commission_Payments,View_Commission_Agreements,View_Targets,Manage_Public_Reports_and_Dashboards |                    |
      | sales_management@canadalife.de  | Sales Management                  | Business Support         | Full Access Germany           |                                                                                                       |                    |
      | sales_support@canadalife.de     | Sales Support                     | Sales Support            | Full Access Germany           | View_Commission_Payments,  View_Commission_Agreements,View_Targets                                    |                    |
      | is_sys_admin_user@canadalife.de | IS-System Administrators          | CLE System Administrator | Full Access Germany           | Shield_Administrator                                                                                  |                    |
      | admin_user@canadalife.de        | Admin                             | Business Support         | Full Access Germany           | Manage_iSuite_Tasks                                                                                   |                    |
      | integration.user@canadalife.de  | Integration User                  | CLE System Administrator | Full Access Germany           | Full_Access,Feature_Administration                                                                    |                    |
      | german_legal_rep@canadalife.ie  | German Legal Representative       | Broker Consultant        | Full Access Germany           |                                                                                                       |                    |
      | sales_director@canadalife.de    | Director Sales                    | Broker Consultant        | Director Sales                | View_All_Policy_Members                                                                               |                    |
      | executive_member@canadalife.ie  | Executive Member                  | Broker Consultant        | Full Access Germany           |                                                                                                       |                    |
      | businessadmin@canadalife.de     | Business Administrator            | Business Administrator   | Full Access Germany           |                                                                                                       |                    |
      | ben.sollis@canadalife.ie        | IS-System Administrators          | CLE System Administrator | Full Access Germany           | Shield_Administrator                                                                                  |                    |
      | jonathan.jones@canadalife.ie    | IS-System Administrators          | CLE System Administrator | Full Access Germany           | Shield_Administrator                                                                                  |                    |
      | regdirrka@canadalife.ie         | Regional Director RKA             | Broker Consultant        | Regional Director RKA         |                                                                                                       |                    |
      | regdir_coop@canadalife.de       | Regional Director Cooperation     | Broker Consultant        | Regional Director Cooperation |                                                                                                       |                    |
      | dir_sal_chan_man@canadalife.de  | Director Sales Channel Management | Broker Consultant        | Full Access Germany           | View_All_Policy_Members,EinsteinAnalyticsAdmin                                                        | Analytics Platform |
      | regdircentral@canadalife.ie     | Regional Director Central Sales   | Broker Consultant        | Regional Director Central     |                                                                                                       |                    |


  Scenario Outline: Verify that personal user settings are as expected in a Salesforce Org
    Given I have a connection to specified staging environment
    And I have a connection to a specified Salesforce Org as admin
    When I execute a user refresh of that environment and check username "<Username>"
    Then I expect alias "<Alias>"  firstname "<Firstname>" lastname "<Lastname>"  to match
    And I expect emailencodingkey "<Emailencodingkey>" langualgelocalekey "<Languagelocalekey>" timezonekey "<Timezonesidkey>" to match
    And I expect salesRegion_c "<SalesRegion__c>" isactive "<Isactive>" and localesidkey "<Localesidkey>" to match
    And I expect department "<department>" to match
    And I expect the assignment of permissionsets "<PermissionSets>"
    And I expect the assignment of groups "<Groups>" to match
    And I expect the defined user boolean properties "<UserProperties>" to be set to true
    Examples:
      | Username                        | Alias    | Firstname   | Lastname            | Emailencodingkey | Languagelocalekey | Timezonesidkey | SalesRegion__c      | Isactive | Localesidkey | department                            | PermissionSets | Groups                  | UserProperties                               |
      | admin_user@canadalife.de        | admin    | Admin       | User                | UTF-8            | en_US             | Europe/Dublin  | Mitte               | 1        | en_US        | Finance                               |                |                         |                                              |
      | bcbawu@canadalife.ie            | bcbawu   | Broker      | ConsultantBawu      | UTF-8            | en_US             | Europe/Dublin  | Baden-Württemberg   | 1        | en_US        | Regionaldirektion Baden-Württemberg   |                |                         | UserPermissionsMarketingUser,ForecastEnabled |
      | bcbayern@canadalife.ie          | bcbayer  | Broker      | ConsultantBayern    | UTF-8            | en_US             | Europe/Dublin  | Bayern              | 1        | en_US        | Regionaldirektion Bayern              |                |                         |                                              |
      | bcmitte@canadalife.ie           | bcmitte  | Broker      | ConsultantMitte     | UTF-8            | en_US             | Europe/Dublin  | Mitte               | 1        | en_US        | Regionaldirektion Mitte               |                |                         | UserPermissionsMarketingUser,ForecastEnabled |
      | bcnord@canadalife.ie            | bcnord   | Broker      | ConsultantNord      | UTF-8            | en_US             | Europe/Dublin  | Nord                | 1        | en_US        | Regionaldirektion Nord                |                |                         | UserPermissionsMarketingUser,ForecastEnabled |
      | bcnrw@canadalife.ie             | bcnrw    | Broker      | ConsultantNRW       | UTF-8            | en_US             | Europe/Dublin  | Nordrhein-Westfalen | 1        | en_US        | Regionaldirektion Nordrhein-Westfalen |                |                         | UserPermissionsMarketingUser,ForecastEnabled |
      | controlling_user@canadalife.de  | controll | Controlling | User                | UTF-8            | en_US             | Europe/Dublin  | Zentrale KA 1       | 1        | en_US        | Vertrieb Orga                         |                |                         | ForecastEnabled                              |
      | sales_director@canadalife.de    | salesdir | Director    | Sales               | UTF-8            | en_US             | Europe/Dublin  | Mitte               | 1        | en_US        | Vertrieb                              |                |                         | ForecastEnabled                              |
      | executive_member@canadalife.ie  | execmem  | Executive   | Member              | UTF-8            | en_US             | Europe/Dublin  | Mitte               | 1        | en_US        | Marketing                             |                |                         |                                              |
      | german_legal_rep@canadalife.ie  | gerlegre | German      | LegalRepresentative | UTF-8            | en_US             | Europe/Dublin  | Nord                | 1        | en_US        | Produkt- und Vertriebsmanagement      |                |                         | ForecastEnabled                              |
      | is_sys_admin_user@canadalife.de | issysadm | ISSystem    | Administrator       | UTF-8            | en_US             | Europe/Dublin  | Mitte               | 1        | en_US        | Key Account und Kooperationen         |                |                         |                                              |
      | regdirbawu@canadalife.ie        | regdbaw  | Regional    | DirectorBawu        | UTF-8            | en_US             | Europe/Dublin  | Baden-Württemberg   | 1        | en_US        | Regionaldirektion Baden-Württemberg   |                |                         | UserPermissionsMarketingUser,ForecastEnabled |
      | regdirbayern@canadalife.ie      | regdbay  | Regional    | DirectorBayern      | UTF-8            | en_US             | Europe/Dublin  | Bayern              | 1        | en_US        | Regionaldirektion Bayern              |                |                         | UserPermissionsMarketingUser,ForecastEnabled |
      | regdircentral@canadalife.ie     | regdce   | Regional    | DirectorCentral     | UTF-8            | en_US             | Europe/Dublin  | Zentrale KA 2       | 1        | en_US        | Zentraler Vertrieb                    |                |                         | UserPermissionsMarketingUser,ForecastEnabled |
      | regdirmitte@canadalife.ie       | regdmit  | Regional    | DirectorMitte       | UTF-8            | en_US             | Europe/Dublin  | Mitte               | 1        | en_US        | Regionaldirektion Mitte               |                |                         | UserPermissionsMarketingUser,ForecastEnabled |
      | regdirnord@canadalife.ie        | regdnord | Regional    | DirectorNord        | UTF-8            | en_US             | Europe/Dublin  | Nord                | 1        | en_US        | Regionaldirektion Nord                |                |                         | UserPermissionsMarketingUser,ForecastEnabled |
      | regdirnrw@canadalife.ie         | regdirnw | Regional    | DirectorNRW         | UTF-8            | en_US             | Europe/Dublin  | Nordrhein-Westfalen | 1        | en_US        | Regionaldirektion Nordrhein-Westfalen |                |                         | UserPermissionsMarketingUser,ForecastEnabled |
      | sales_management@canadalife.de  | salesmgt | Sales       | Management          | UTF-8            | en_US             | Europe/Dublin  | Zentrale KA 1       | 1        | en_US        | Customer Services                     |                |                         |                                              |
      | sales_support@canadalife.de     | salesspt | Sales       | Support             | UTF-8            | en_US             | Europe/Dublin  | Nordrhein-Westfalen | 1        | en_US        | Vertrieb Support                      |                | EventMgt,Report Viewers | UserPermissionsMarketingUser,ForecastEnabled |
      | vto_user@canadalife.de          | vtouser  | VTO         | User                | UTF-8            | en_US             | Europe/Dublin  | Zentrale KA 1       | 1        | en_US        | Vorstand                              |                | VTO                     |                                              |
      | zka1@canadalife.de              | zka1     | key account | ZKA1                | UTF-8            | en_US             | Europe/Dublin  | Zentrale KA 1       | 1        | en_US        | Key Account und Kooperationen         |                | Veranstaltungsteam      | UserPermissionsMarketingUser,ForecastEnabled |
      | zka2@canadalife.de              | zka2     | key account | ZKA2                | UTF-8            | en_US             | Europe/Dublin  | Zentrale KA 2       | 1        | en_US        | Key Account und Kooperationen         |                | VS komplett             | UserPermissionsMarketingUser,ForecastEnabled |
      | zka3@canadalife.de              | zka3     | key account | ZKA3                | UTF-8            | en_US             | Europe/Dublin  | Zentrale KA 3       | 1        | en_US        | Key Account und Kooperationen         |                | Vertriebstechnologie    | UserPermissionsMarketingUser,ForecastEnabled |
      # see SAL-3262 for details about business admin Group Membership to BC Lead Admin - Carsten clarified 23/03/2020 -> all Business Admin users should be in group "BC Lead Admin"
      | businessadmin@canadalife.de     | bsadmin  | Business    | Administrator       | UTF-8            | en_US             | Europe/Dublin  | Mitte               | 1        | en_US        | Vertrieb Support                      |                | EventMgt,BC Lead Admin  | UserPermissionsMarketingUser,ForecastEnabled |


