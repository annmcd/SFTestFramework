package com.cle.crm.salesforce.sfconfigmanager.utils;

public class Const {

    //Column References
    public static final int ENTITY_ATTR_NAME_COL = 0;
    public static final int ENTITY_ATTR_SIZE_COL = 2;
    public static final int ENTITY_ATTR_DATA_TYPE_COL = 3;
    public static final int ENTITY_ATTR_REF_COL = 4;
    public static final int ENTITY_ATTR_EN_LABEL_COL = 5;
    public static final int ENTITY_ATTR_DE_LABEL_COL = 6;
    public static final int ENTITY_ATTR_WORKBENCH_COL = 7;
    public static final int ENTITY_ATTR_EN_PL_COL = 8;
    public static final int ENTITY_ATTR_DE_PL_COL = 9;
    public static final int ENTITY_ATTR_MANDATORY_COL = 12;
    public static final int ENTITY_ATTR_REMOVAL_COL = 13;
    public static final int ENTITY_ATTR_ENCRYPTED_COL = 15;
    public static final int ENTITY_ATTR_RESTRICTED_COL = 16;

    //categories
    public static final String YAML_HEADER_DATA_TYPE = "dataTypes:";
    public static final String YAML_HEADER_EN_LABEL = "labels:";
    public static final String YAML_HEADER_DE_LABEL = "deLabels:";
    public static final String YAML_HEADER_EN_PL = "picklists:";
    public static final String YAML_HEADER_DE_PL = "dePicklists:";
    public static final String YAML_HEADER_WORKBENCH = "picklistWorkbench:";
    public static final String YAML_HEADER_REMOVALS = "removals:";
    public static final String YAML_HEADER_MANDATORY = "mandatory:";
    public static final String YAML_HEADER_ENCRYPTED = "encryptedFields:";
    public static final String YAML_HEADER_RESTRICTED = "restrictedPickLists:";

    //module
    public static final String MODULE_NAME = "SFConfigManager";
}