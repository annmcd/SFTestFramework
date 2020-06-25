package com.test.cle.salesforce.serenity.steps.sfsecurity;

import com.sforce.soap.metadata.*;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.yamlelements.security.*;
import net.thucydides.core.annotations.Step;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class VerifySharingRules {

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static List<SharingCriteriaRule[]> getSharingCriteriaRulesforEntity(
            MetadataConnection metadataConnection, String entityName) throws ConnectionException {

        ReadResult readResult =
                metadataConnection.readMetadata("SharingRules", new String[]{entityName});

        List<SharingCriteriaRule[]> rules = new ArrayList<>();
        Metadata[] mdInfo = readResult.getRecords();
        for (Metadata md : mdInfo) {
            if (md != null) {
                com.sforce.soap.metadata.SharingRules rule = (com.sforce.soap.metadata.SharingRules) md;
                log.debug(entityName + ", Sharing rule name = " + rule.getFullName());

                SharingCriteriaRule[] sharingCriteriaRules = rule.getSharingCriteriaRules();
                rules.add(sharingCriteriaRules);
            }
        }
        return rules;
    }

    public static List<SharingOwnerRule[]> getSharingOwnerRulesforEntity(
            MetadataConnection metadataConnection, String entityName) throws ConnectionException {

        ReadResult readResult =
                metadataConnection.readMetadata("SharingRules", new String[]{entityName});

        List<SharingOwnerRule[]> rules = new ArrayList<>();
        Metadata[] mdInfo = readResult.getRecords();
        for (Metadata md : mdInfo) {
            if (md != null) {
                com.sforce.soap.metadata.SharingRules rule = (com.sforce.soap.metadata.SharingRules) md;
                log.debug(entityName + ", Sharing rule name = " + rule.getFullName());

                SharingOwnerRule[] sharingCriteriaRules = rule.getSharingOwnerRules();
                rules.add(sharingCriteriaRules);
            }
        }
        return rules;
    }

    @Step
    public static void processContact(
            SharingCriteriaRule sfObjectRule, RuleDef rule, List<SharingRuleDef> sharingRuleDefs) {

        List sfCriteriaList = new ArrayList();
        FilterItem[] sfFilterItemforRule = sfObjectRule.getCriteriaItems();

        for (FilterItem item : sfFilterItemforRule) {
            log.debug(
                    "Salesforce FieldDescriptors Rule Name"
                            + rule.getRuleName()
                            + "criteriaField ="
                            + item.getField()
                            + " "
                            + item.getOperation()
                            + " "
                            + item.getValue());
            String value = item.getField() + item.getOperation() + item.getValue();

            log.debug("Adding to Salesforce List " + value);

            sfCriteriaList.add(value);
        }
        for (SharingRuleDef def : sharingRuleDefs) {
            String criteriaField = def.getField();
            String criteriaOperator = def.getOperator();
            String criteriaValue = def.getValue();
            log.debug(
                    "Yaml FieldDescriptors Rule Name "
                            + rule.getRuleName()
                            + " criteriaField ="
                            + criteriaField
                            + Constants.OPERATOR
                            + criteriaOperator
                            + " criteria value ="
                            + criteriaValue);
            String value = criteriaField + criteriaOperator + criteriaValue;

            log.debug("Seeking " + value + " in\n\r " + sfCriteriaList.toString());
            Assert.assertTrue(sfObjectRule.getFullName() + "Criteria Contains value" + value, sfCriteriaList.contains(value));
        }
    }


    @Step
    public static void processLead(
            SharingCriteriaRule sfObjectRule, RuleDef rule, List<SharingRuleDef> sharingRuleDefs, String sharedToRole) {

        List sfCriteriaList = new ArrayList();
        FilterItem[] sfFilterItemforRule = sfObjectRule.getCriteriaItems();

        for (FilterItem item : sfFilterItemforRule) {
            log.debug(
                    "Salesforce FieldDescriptors Rule Name"
                            + rule.getRuleName()
                            + "criteriaField ="
                            + item.getField()
                            + " "
                            + item.getOperation()
                            + " "
                            + item.getValue());
            String value = item.getField() + item.getOperation() + item.getValue();

            log.debug("Adding to Salesforce List " + value);

            sfCriteriaList.add(value);
        }
        for (SharingRuleDef def : sharingRuleDefs) {
            String criteriaField = def.getField();
            String criteriaOperator = def.getOperator();
            String criteriaValue = def.getValue();
            log.debug(
                    "Yaml FieldDescriptors Rule Name "
                            + rule.getRuleName()
                            + " criteriaField ="
                            + criteriaField
                            + Constants.OPERATOR
                            + criteriaOperator
                            + " criteria value ="
                            + criteriaValue);
            String value = criteriaField + criteriaOperator + criteriaValue;

            log.debug("Seeking " + value + " in\n\r " + sfCriteriaList.toString());
            Assert.assertTrue(sfObjectRule.getFullName() + "Criteria Contains value" + value, sfCriteriaList.contains(value));
            Assert.assertEquals(sharedToRole, StringUtils.join(sfObjectRule.getSharedTo().getRole(), ","));
        }
    }

    @Step
    public static void processTarget(
            SharingCriteriaRule sfObjectRule, RuleDef rule, List<SharingRuleDef> sharingRuleDefs, String sharedToRoleAndSubordinates) {

        List sfCriteriaList = new ArrayList();
        FilterItem[] sfFilterItemforRule = sfObjectRule.getCriteriaItems();

        for (FilterItem item : sfFilterItemforRule) {
            log.debug(
                    "Salesforce FieldDescriptors Rule Name"
                            + rule.getRuleName()
                            + " criteriaField = "
                            + item.getField()
                            + Constants.OPERATOR
                            + item.getOperation()
                            + " criteria value = "
                            + item.getValue());
            String value = item.getField() + item.getOperation() + item.getValue();

            log.debug("Adding to Salesforce List " + value);

            sfCriteriaList.add(value);
        }
        for (SharingRuleDef def : sharingRuleDefs) {
            String criteriaField = def.getField();
            String criteriaOperator = def.getOperator();
            String criteriaValue = def.getValue();
            log.debug(
                    "Yaml FieldDescriptors Rule Name "
                            + rule.getRuleName()
                            + " criteriaField ="
                            + criteriaField
                            + Constants.OPERATOR
                            + criteriaOperator
                            + " criteria value ="
                            + criteriaValue);
            String value = criteriaField + criteriaOperator + criteriaValue;

            log.debug("Seeking " + value + " in\n\r " + sfCriteriaList.toString());
            Assert.assertTrue(sfObjectRule.getFullName() + "Criteria Contains value" + value, sfCriteriaList.contains(value));
            Assert.assertEquals(sharedToRoleAndSubordinates, StringUtils.join(sfObjectRule.getSharedTo().getRoleAndSubordinates(), ","));
        }
    }

    public static List processAccount(
            SharingCriteriaRule sfObjectRule,
            String caseAccess,
            String opportunityAccess,
            String contactAccess) {

        AccountSharingRuleSettings accountSharingRuleSettings = sfObjectRule.getAccountSettings();

        Assert.assertEquals(
                sfObjectRule.getFullName() + "Case Access",
                caseAccess,
                accountSharingRuleSettings.getCaseAccessLevel());
        Assert.assertEquals(
                sfObjectRule.getFullName() + " Opportunity Access",
                opportunityAccess,
                accountSharingRuleSettings.getOpportunityAccessLevel());
        Assert.assertEquals(
                sfObjectRule.getFullName() + " Contact Access",
                contactAccess,
                accountSharingRuleSettings.getContactAccessLevel());

        FilterItem[] sfFilterItemforRule = sfObjectRule.getCriteriaItems();

        List<String> sfCriteriaList = new ArrayList();
        for (FilterItem item : sfFilterItemforRule) {

            String value = item.getField() + item.getOperation().toString() + item.getValue().trim();
            sfCriteriaList.add(value);
        }
        return sfCriteriaList;
    }


    private static boolean isItemInList(List<String> list, String item) {

        boolean b = false;
        log.debug("Size of list received = " + list.size());
        for (String listItem : list) {
            log.debug("Seeking Match for item " + item + "  against " + listItem);
            if (listItem.equals(item)) {
                b = true;
                break;
            }
        }
        return b;
    }

    @Step
    public static void doVerifySharingCriteriaRules(
            String sfObjectType, MetadataConnection metadataConnection, String yamlFile) {

        Yaml yaml = new Yaml(new Constructor(RuleDef.class));


        try (InputStream inputStream = new FileInputStream(new File(yamlFile))) {

            int i = 0;
            for (Object object : yaml.loadAll(inputStream)) {
                i++;
                if (object instanceof RuleDef) {

                    RuleDef rule = (RuleDef) object;
                    log.debug("Label: " + rule.ruleLabel);
                    String ruleName = rule.getRuleName();
                    String ruleLabel = rule.getRuleLabel();
                    log.debug(
                            "Number of sharing Rules assigned to " + ruleName + "=" + rule.getSharingRule().size());
                    log.debug(
                            "Number of access settings assigned to " + ruleName + "=" + rule.getAccess().size());
                    log.debug(i + "--------------------------------------------" + yamlFile);

                    // per rule
                    String caseAccess = null;
                    String opportunityAccess = null;
                    String contactAccess = null;
                    //  String leadAccess = null;
                    // String targetAccess = null;
                    String sharedToRole = null;
                    String sharedToRoleAndSubordinates = null;
                    //  String defaultAccess = null;

                    List<SharingRuleAccessDef> sharingRuleAccessList = rule.getAccess();
                    for (SharingRuleAccessDef accessDef : sharingRuleAccessList) {
                        caseAccess = accessDef.getCaseAccess();
                        opportunityAccess = accessDef.getOpportunityAccess();
                        contactAccess = accessDef.getContactAccess();

                        //  targetAccess = accessDef.getTargetAccess();
                        sharedToRole = accessDef.getSharedToRole();
                        sharedToRoleAndSubordinates = accessDef.getSharedToRoleAndSubordinates();
                        //  defaultAccess=accessDef.getDefaultAccess();
                    }
                    List<SharingRuleDef> sharingRuleDefs = rule.getSharingRule();

                    List<SharingCriteriaRule[]> sfSharingCriteriaRules =
                            getSharingCriteriaRulesforEntity(metadataConnection, sfObjectType);

                    log.debug(
                            "The number of sharing Criteria Rules for "
                                    + ruleName
                                    + "="
                                    + sfSharingCriteriaRules.size());
                    for (SharingCriteriaRule[] sfObjectRules : sfSharingCriteriaRules) {

                        log.debug("Process sharing Criterial Rules");
                        for (SharingCriteriaRule sfObjectRule : sfObjectRules) {
                            log.debug(
                                    "Getting details on each Sharing Criteria Rule seeking "
                                            + ruleLabel
                                            + " found "
                                            + sfObjectRule.getLabel());
                            // this is the rule in the yaml we want to evaluate against

                            if (ruleLabel.equals(sfObjectRule.getLabel())) {

                                Assert.assertEquals(ruleLabel, sfObjectRule.getLabel());
                                Assert.assertEquals(ruleName, sfObjectRule.getFullName());


                                if (rule.getObject().equalsIgnoreCase("Account")) {
                                    List<String> saleforceCriteria =
                                            processAccount(
                                                    sfObjectRule, caseAccess,
                                                    opportunityAccess, contactAccess);
                                    for (SharingRuleDef def : sharingRuleDefs) {
                                        String criteriaField = def.getField();
                                        String criteriaOperator = def.getOperator();
                                        String criteriaValue = def.getValue();
                                        String value =
                                                criteriaField.trim() + criteriaOperator.trim() + criteriaValue.trim();

                                        boolean b = isItemInList(saleforceCriteria, value);
                                        log.debug(
                                                "Check that " + value + " for " + ruleName + " exists in Salesforce = "
                                                        + b);
                                        Assert.assertTrue("value " + value + " exists in " + ruleName, b);
                                    }

                                } else if (rule.getObject().equalsIgnoreCase("Contact")
                                        || rule.getObject().equalsIgnoreCase("Target__c")
                                        || rule.getObject().equalsIgnoreCase("Lead")) {

                                    log.debug("--Contact Criteria Sharing rule--");
                                    if (rule.getObject().equalsIgnoreCase("Contact")) {
                                        processContact(sfObjectRule, rule, sharingRuleDefs);
                                    } else if (rule.getObject().equalsIgnoreCase("Target__c")) {
                                        log.debug("--Lead Criteria Sharing rule--");
                                        processTarget(sfObjectRule, rule, sharingRuleDefs, sharedToRoleAndSubordinates);
                                    } else if (rule.getObject().equalsIgnoreCase("Lead")) {
                                        log.debug("--Lead Criteria Sharing rule--");
                                        processLead(sfObjectRule, rule, sharingRuleDefs, sharedToRole);
                                    }

                                    List<SharingRuleAccessDef> yamlAccesDef = rule.getAccess();
                                    for (SharingRuleAccessDef accessSetting : yamlAccesDef) {
                                        String defaultYamlAccess = accessSetting.getDefaultAccess();
                                        log.debug(
                                                rule.getObject()
                                                        + " "
                                                        + rule.getRuleName()
                                                        + " yaml Value "
                                                        + defaultYamlAccess
                                                        + " sf value="
                                                        + sfObjectRule.getAccessLevel());
                                        Assert.assertEquals(defaultYamlAccess, sfObjectRule.getAccessLevel());
                                    }
                                } else {
                                    log.error("Unknown Sharing Rule Entity");

                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error(e);
        } catch (ConnectionException e) {
            log.error("Connection Exception", e);
        }
    }

    @Step
    // For Owner Sharing Rules Commission_Agreement__c
    public static void doVerifySharingOwnerSharingRules(
            String sfObjectType, MetadataConnection metadataConnection, String yamlFile)
            throws Exception {

        Yaml yaml = new Yaml(new Constructor(OwnerRuleDef.class));
        OwnerRuleDef yamlEntity;
        try (InputStream inputStream = new FileInputStream(new File(yamlFile))) {
            yamlEntity = yaml.load(inputStream);
        }
        String type = yamlEntity.getType();
        log.debug(" Owner Rule Type" + type);

        List<SharingOwnerRule[]> sfRules =
                getSharingOwnerRulesforEntity(metadataConnection, sfObjectType);

        List<OwnerSharingRuleDef> yamlRules = yamlEntity.getSharingRule();
        for (OwnerSharingRuleDef yamlRule : yamlRules) {

            String yamlRuleAccess = yamlRule.getAccessLevel();
            String yamlRuleSharedToRole = yamlRule.getSharedToRole();
            String ruleName = yamlRule.getName();
            String ruleLabel = yamlRule.getLabel();
            String sharedFromRole = yamlRule.getOwnedByRole();
            String sharedFromRoleAndSubs = yamlRule.getOwnedByRoleAndSubordinates();

            log.debug("Sharing Rule Name = " + ruleName);

            for (SharingOwnerRule[] rules : sfRules) {

                boolean ruleFound = false;
                for (SharingOwnerRule sfObjectRule : rules) {

                    if (ruleName.equals(sfObjectRule.getFullName())) {

                        SharedTo sharedTo = sfObjectRule.getSharedTo();
                        String sfRuleRoles = StringUtils.join(sharedTo.getRole(), ",");
                        log.debug("AMD Compare Roles " + yamlRuleSharedToRole + " vs " + sfRuleRoles);

                        SharedTo sharedFrom = sfObjectRule.getSharedFrom();
                        String sfRuleFromRoles = StringUtils.join(sharedFrom.getRole(), ",");
                        String sfRuleFromRolesSubordinates =
                                StringUtils.join(sharedFrom.getRoleAndSubordinates(), ",");

                        log.debug(
                                "Found yaml rule we sought in salesforce "
                                        + ruleName
                                        + " SharedFromRoles in SF = "
                                        + sfRuleFromRoles
                                        + "\n\r"
                                        + " RoleandSubs "
                                        + sfRuleFromRolesSubordinates);

                        Assert.assertEquals(yamlRuleAccess, sfObjectRule.getAccessLevel());
                        Assert.assertEquals(yamlRuleSharedToRole, sfRuleRoles);
                        Assert.assertEquals(ruleLabel, sfObjectRule.getLabel());
                        Assert.assertEquals(ruleName, sfObjectRule.getFullName());
                        Assert.assertEquals(sharedFromRole, sfRuleFromRoles);
                        Assert.assertEquals(sharedFromRoleAndSubs, sfRuleFromRolesSubordinates);

                        ruleFound = true;
                        break;
                    }
                }

                if (!ruleFound) {
                    log.error(
                            Constants.OWNER_SHARING_RULE_NOT_FOUND + ruleName + " for entity " + yamlEntity.getObject());

                } else {
                    Assert.assertTrue(ruleFound);
                }
            }
        }
    }
}