package com.test.cle.salesforce.yamlelements.security;

import java.util.List;

public class RuleDef {

    public String object;
    public String type;
    public String ruleName;
    public String ruleLabel;
    List<SharingRuleAccessDef> access;
    List<SharingRuleDef> sharingRule;

    public List<SharingRuleAccessDef> getAccess() {

        return access;
    }

    public void setAccess(List<SharingRuleAccessDef> access) {

        this.access = access;
    }

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

    public String getRuleName() {

        return ruleName;
    }

    public void setRuleName(String ruleName) {

        this.ruleName = ruleName;
    }

    public String getRuleLabel() {

        return ruleLabel;
    }

    public void setRuleLabel(String ruleLabel) {

        this.ruleLabel = ruleLabel;
    }

    public List<SharingRuleDef> getSharingRule() {

        return sharingRule;
    }

    public void setSharingRule(List<SharingRuleDef> sharingRule) {

        this.sharingRule = sharingRule;
    }
}
