package com.test.cle.salesforce.yamlelements.security;

import java.util.List;

public class PSDef {

    public String name;
    List<PSObjectDef> object;
    List<NameValue> systemPermissions;
    List<NameValue> userPermissions;
    List<UncheckedObjectDef> unCheckedObject;

    public List<NameValue> getSystemPermissions() {

        return systemPermissions;
    }

    public void setSystemPermissions(List<NameValue> systemPermissions) {

        this.systemPermissions = systemPermissions;
    }

    public List<NameValue> getUserPermissions() {

        return userPermissions;
    }

    public void setUserPermissions(List<NameValue> userPermissions) {

        this.userPermissions = userPermissions;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public List<PSObjectDef> getObject() {

        return object;
    }

    public void setObject(List<PSObjectDef> object) {

        this.object = object;
    }

    public List<UncheckedObjectDef> getUnCheckedObject() {

        return unCheckedObject;
    }

    public void setUnCheckedObject(List<UncheckedObjectDef> unCheckedObject) {

        this.unCheckedObject = unCheckedObject;
    }
}
