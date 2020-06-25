package com.test.cle.salesforce.yamlelements.security;

/**
 * Required as generic case used by PermissionSets and Profiles
 */
public class NameValue {

    String name;
    Boolean value;

    public Boolean getValue() {

        return value;
    }

    public void setValue(Boolean value) {

        this.value = value;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
