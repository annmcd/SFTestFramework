package com.test.cle.salesforce.yamlelements.security;

import java.util.List;

public class ProfileDef {

    String name;
    List<ProfileObjectDef> object;
    List<UncheckedObjectDef> unCheckedObject;
    List<ProfileFieldDef> field;
    List<NameValue> administrative;
    List<NameValue> generalUser;

    public List<UncheckedObjectDef> getUnCheckedObject() {

        return unCheckedObject;
    }

    public void setUnCheckedObject(List<UncheckedObjectDef> unCheckedObject) {

        this.unCheckedObject = unCheckedObject;
    }

    /**
     * recordType: - name: Accounts value Policy Business Account,Policy Members,Brokerage - name:
     * Contacts value Salesforce Contact
     */
    public List<NameValue> getGeneralUser() {

        return generalUser;
    }

    public void setGeneralUser(List<NameValue> generalUser) {

        this.generalUser = generalUser;
    }

    public List<NameValue> getAdministrative() {

        return administrative;
    }

    public void setAdministrative(List<NameValue> administrative) {

        this.administrative = administrative;
    }

    public List<ProfileObjectDef> getObject() {

        return object;
    }

    public void setObject(List<ProfileObjectDef> object) {

        this.object = object;
    }

    public List<ProfileFieldDef> getField() {

        return field;
    }

    public void setField(List<ProfileFieldDef> field) {

        this.field = field;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
