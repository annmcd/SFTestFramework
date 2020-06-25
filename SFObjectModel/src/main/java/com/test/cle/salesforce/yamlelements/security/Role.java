package com.test.cle.salesforce.yamlelements.security;

public class Role {

    public String name;
    public String opportunityAccessForAccountOwner;
    public String caseAccessForAccountOwner;
    public String contactAccessForAccountOwner;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getOpportunityAccessForAccountOwner() {

        return opportunityAccessForAccountOwner;
    }

    public void setOpportunityAccessForAccountOwner(String opportunityAccessForAccountOwner) {

        this.opportunityAccessForAccountOwner = opportunityAccessForAccountOwner;
    }

    public String getCaseAccessForAccountOwner() {

        return caseAccessForAccountOwner;
    }

    public void setCaseAccessForAccountOwner(String caseAccessForAccountOwner) {

        this.caseAccessForAccountOwner = caseAccessForAccountOwner;
    }

    public String getContactAccessForAccountOwner() {

        return contactAccessForAccountOwner;
    }

    public void setContactAccessForAccountOwner(String contactAccessForAccountOwner) {

        this.contactAccessForAccountOwner = contactAccessForAccountOwner;
    }
}
