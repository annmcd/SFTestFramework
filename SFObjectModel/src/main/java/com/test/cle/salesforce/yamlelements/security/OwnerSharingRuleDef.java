package com.test.cle.salesforce.yamlelements.security;

;


public class OwnerSharingRuleDef {

    public String label;
    public String name;
    public String sharedToRole;
    public String sharedToRoleAndSubordinates;
    public String accessLevel;
    public String ownedByRoleAndSubordinates;
    public String ownedByRole;

    public String getOwnedByRoleAndSubordinates() {

        return ownedByRoleAndSubordinates;
    }

    public void setOwnedByRoleAndSubordinates(String ownedByRoleAndSubordinates) {

        this.ownedByRoleAndSubordinates = ownedByRoleAndSubordinates;
    }

    public String getOwnedByRole() {

        return ownedByRole;
    }

    public void setOwnedByRole(String ownedByRole) {

        this.ownedByRole = ownedByRole;
    }

    public String getSharedToRole() {

        return sharedToRole;
    }

    public void setSharedToRole(String sharedToRole) {

        this.sharedToRole = sharedToRole;
    }

    public void setSharedToRoleAndSubordinates(String sharedToRoleAndSubordinates) {
        this.sharedToRoleAndSubordinates = sharedToRoleAndSubordinates;
    }

    public String getSharedToRoleAndSubordinates() {
        return sharedToRoleAndSubordinates;
    }

    public String getLabel() {

        return label;
    }

    public void setLabel(String label) {

        this.label = label;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getAccessLevel() {

        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {

        this.accessLevel = accessLevel;
    }
}
