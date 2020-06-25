package com.test.cle.salesforce.yamlelements.security;

import java.util.List;

;

public class OwnerRuleDef {

    public String object;
    public String type;
    List<OwnerSharingRuleDef> sharingRule;

    public String getObject() {

        return object;
    }

    public void setObject(String object) {

        this.object = object;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public List<OwnerSharingRuleDef> getSharingRule() {

        return sharingRule;
    }

    public void setSharingRule(List<OwnerSharingRuleDef> sharingRule) {

        this.sharingRule = sharingRule;
    }
}
