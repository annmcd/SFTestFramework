package com.test.cle.salesforce.yamlelements.security;

public class SharingRuleAccessDef {

    String defaultAccess;
    String contactAccess;
    String opportunityAccess;
    String leadAccess;
    String caseAccess;
    String targetAccess;
    String sharedToRole; // shared with role
    String sharedToRoleAndSubordinates; //shared with role and its subordinates
    String sharedWith; //shared with group or person

    public void setSharedWith(String sharedWith) {
        this.sharedWith = sharedWith;
    }

    public String getSharedWith() {
        return sharedWith;
    }

    public void setCaseAccess(String caseAccess) {
        this.caseAccess = caseAccess;
    }

    public String getCaseAccess() {
        return caseAccess;
    }

    public void setContactAccess(String contactAccess) {
        this.contactAccess = contactAccess;
    }

    public String getContactAccess() {
        return contactAccess;
    }

    public void setDefaultAccess(String defaultAccess) {
        this.defaultAccess = defaultAccess;
    }

    public String getDefaultAccess() {
        return defaultAccess;
    }

    public void setLeadAccess(String leadAccess) {
        this.leadAccess = leadAccess;
    }

    public String getLeadAccess() {
        return leadAccess;
    }

    public void setOpportunityAccess(String opportunityAccess) {
        this.opportunityAccess = opportunityAccess;
    }

    public String getOpportunityAccess() {
        return opportunityAccess;
    }

    public void setSharedToRole(String sharedToRole) {
        this.sharedToRole = sharedToRole;
    }

    public String getSharedToRole() {
        return sharedToRole;
    }

    public void setSharedToRoleAndSubordinates(String sharedToRoleAndSubordinates) {
        this.sharedToRoleAndSubordinates = sharedToRoleAndSubordinates;
    }

    public String getSharedToRoleAndSubordinates() {
        return sharedToRoleAndSubordinates;
    }

    public void setTargetAccess(String targetAccess) {
        this.targetAccess = targetAccess;
    }

    public String getTargetAccess() {
        return targetAccess;
    }
}