package com.test.cle.salesforce.testutils;

public class Constants {


    public static final String ENT_CON_CHECK ="Enterprise Connection null check ";
    public static final String PARTNER_CON_CHECK ="Enterprise Connection null check ";
    public static final String METADATA_CON_CHECK ="Metadata Connection null check ";
    public static final String SERIALISATION_FAILURE = "SERIALISATION_FAILURE_FOR_FILE ";
    public static final String FOLDER_DATE_FORMAT = "dd-MM-yyyy";
    public static final String SR_NOT_FOUND = "SHARING_RULE_NOT_FOUND";
    public static final String SR_INVALID_FIELD_VAL = "SHARING_RULE_INVALID_FIELD_VALUE";
    public static final String PS_NVP_USER_SETTINGS =
            "PERMISSIONSETS_NAME_VALUE_PAIR_COMPARISON_FOR_USER_PERMISSIONS ";
    public static final String PS_NVP_SEC_SETTINGS =
            "PERMISSIONSETS_NAME_VALUE_PAIR_COMPARISON_FOR_ADMINISTRATIVE_PERMISSIONS ";
    public static final String ROLE_ATTRIBUTE_VALUE_UNEXP =
            "UNEXPECTED_ATTRIBUTE_VALUE_FOR_USERROLE: ";
    public static final String ENTITY_IND_FOR_REMOVAL = "FIELD_REMOVAL_FROM_ENTITY_IS_REQUIRED ";
    public static final String OBJ_NOT_MARKED_SF = "OBJECT_TYPE_HAS_NOT_BEEN_MARKED_IN_SALESFORCE: ";
    public static final String FIELD_NOT_DEFINED = "FIELD_HAS_NOT_BEEN_DEFINED_FOR_ENTITY ";
    public static final String PICKLIST_FIELD_NOT_DEFINED = "PICKLIST_FIELD_HAS_NOT_BEEN_DEFINED_FOR_ENTITY ";
    public static final String PR_OBJ_ERR = " OBJECT_LEVEL_ERROR ";
    public static final String PR_NOT_FOUND=" PROFILE_NOT_FOUND_IN_SALESFORCE";
    public static final String PR_OBJ_ADMIN_SEC_ERR = " PROFILE_ADMINISRATIVE_SECURITY_ERROR ";
    public static final String PR_OBJ_GEN_USER_SEC_ERR = " PROFILE_GENERAL_USER_SECURITY_ERROR  ";
    public static final String PER_OBJ_ERR = " PERMISSIONSET_OBJECT_LEVEL_ERROR ";
    public static final String PR_FIELD_ERR = " PROFILE_FIELD_LEVEL_ERROR ";
    public static final String FDM_URL = "financeDataMartDBURL";
    public static final String SRDB_URL = "salesReportingDBURL";
    public static final String ODS_DB_URL = "odsDBURL";
    public static final String STAGING_DB_URL = "stagingDBURL";
    public static final String DATA_TYPES = "datatype";
    public static final String LABEL = "label";
    public static final String DE_LABEL = "de_label";
    public static final String DE_LANG = "-de";
    public static final String DATATYPE_IND = "DATATYPE_ATTR";
    public static final String LABEL_IND = "LABEL_ATTR";
    public static final String MANDATORY_IND = "REQUIRED_MANDATORY_FIELD ";
    public static final String ENCRYPTED_IND = "REQUIRED_AS_ENCRYPTED_FIELD ";
    public static final String RESTRICTED_PL_IND = "EXPECTED_AS_RESTRICTED_PICKLIST_FIELD ";
    public static final String IS_UNIQUE_FIELD_IND = "EXPECTED_IS_UNIQUE_FIELD ";
    public static final String IS_EXTERNAL_ID_FIELD_IND = "EXPECTED_IS_EXTERNAL_ID_FIELD ";
    public static final String DE_LABEL_IND = "DE_LABEL_ATTR";
    public static final String DE_PL = "DE_PICKLIST";
    public static final String ATTR_COM_SUCCESS = "ERR_FIELD_COMPARE_SUCCESS";
    public static final String ATTR_COM_FAILURE = "ERR_FIELD_COMPARE_FAILURE";
    public static final String UNWANTED_ATTR = "ERR_UNWANTED_ATTRIBUTE_FAILURE";
    public static final String MISSING_DE_PL = "ERR_NO_DE_PICKLIST_ENTRY_FAILURE";
    public static final String NO_SUCH_FIELD = "ERR_NO_SUCH_FIELD_ON_ENTITY ";
    public static final String HT_FIELD_VALUE_MISMATCH =" HELPTEXT_FIELD_VALUE_MISMATCH";
    public static final String HT_FIELD_VALUE_IS_NULL="NO_HELP_TEXT_SPECIFIED ";
    public static final String DESCRIPTION_FIELD_VALUE_MISMATCH ="DESCRIPTION_FIELD_VALUE_MISMATCH";
    public static final String DESCRIPTION_FIELD_VALUE_IS_NULL="NO_DESCRIPTION_TEXT_SPECIFIED ";
    public static final String STEP_FAILED_IND = "step failed! {}";
    public static final String STAGING_PROP = "stagingDBURL";
    public static final String CSV_EXPORT_DIRECTORY = "csvExportDirectory";
    public static final String METHOD_NOT_FOUND = "ERR_NO_SUCH_METHOD_EXCEPTION ";
    public static final String ENTITY_FIELD_NOT_FOUND =
            "EXPECTED_ATTRIBUTE_DOES_NOT_EXIST_ON_TARGET_ENTITY";
    public static final String IS_MARKED_ON_PROFILE =
            "IS_A_MARKED_OBJECT_ON_SALESFORCE_PROFILE_BUT_STATED_UNMARKED_IN_YAML";
    public static final String IS_MARKED_ON_PS =
            "IS_A_MARKED_OBJECT_ON_SALESFORCE_PERMISSIONSET_BUT_STATED_UNMARKED_IN_YAML";
    public static final String OBJ_PERMISSIONS_MISSING = "NO_OBJECT_LEVEL_PERMISSIONS_SPECIFIED ";
    public static final String FIELD_PERMISSIONS_MISSING = "NO_FIElD_LEVEL_PERMISSIONS_SPECIFIED ";
    public static final String NO_ADMIN_PERMISSIONS = "WARNING: NO_ADMIN_PERMISSIONS ";
    public static final String MISSING_TRANSLATION = " MISSING_TRANSLATION_FOR_FIELD ";
    public static final String MISSING_PL_TRANSLATION = " MISSING_PICKLIST_TRANSLATION_FOR_FIELD ";
    public static final String NO_UNCHECKED_SPECIFIED = "ERR_NO_UNCHECKED_OBJECTS_SPECIFIED";
    public static final String EXTRA_HEADER = ",markfordeletion__c,ETLVersion__c,BatchId__c";

    public static final String FOLDER_RECORD_ACCESS = "recordAccessDefs";
    public static final String CONFIG_FILE_SFBULKDATA = "_sfbulkdata_config.yaml";
    public static final String BATCH_ID_AUTO = "sftestpack";
    public static final String ETLVERSION__c = "2";

    public static final String ERR_RECORD_ACCESS_SETTINGS = "ERR_RECORD_ACCESS_SETTINGS_MISMATCH ";
    public static final double API_VERSION = 46.0;

    public static final String VR_RULE_MISSING = "ERR_VALIDATION_RULE_NOT_IN_SALESFORCE ";
    public static final String VR_FORMULA_MISMATCH =
            "ERR_VALIDATION_RULE_SETTINGS_IN_SALESFORCE_DO_NOT_MATCH_EXPECTED_SETTINGS";
    public static final String VR_ERROR_MSG_INVALID =
            "ERR_VALIDATION_RULE_ERRROR_MESSAGE_DOES_NOT_MATCH_SALESFORCE_SETTING";
    public static final String VR_ERROR_STATE =
            "ERR_VALIDATION_RULE_INVALID_ACTIVE_STATE_IN_SALESFORCE";
    public static final String USER_MISSING_PS = "ERR_USER_IS_MISSING_PERMISSION_SET ";
    public static final String USER_MISSING_GROUP_MEMEBERSHIP =
            "ERR_USER_IS_MISSING_GROUP_MEMBERSHIP ";
    public static final String MISSING_GROUP="GROUP_DOES_NOT_EXIST_IN_ORG ";
    public static final String SOQL_NO_RESULTSSET = "ERR_NO_VALUES_RETURNED_FROM_SOQL ";
    public static final String ERR_STEP_FAILED="step failed! {}";
    public static final String ERR_MISSING_PROFILE_ENTITY_TEST="MISSING_TEST_FOR_ENTITY_SECURIY_SETTINGS_DEFINED_ON PROFILE ";
    public static final String ERR_MISSING_PERMISSIONSET_ENTITY_TEST="MISSING_TEST_FOR_ENTITY_SECURIY_SETTINGS_DEFINED_ON PERMISSIONSET ";
    public static final String ERR_MISSING_PROFILE_FIELD_TEST="MISSING_TEST_FOR_FIELD_SECURIY_SETTINGS_DEFINED_ON PROFILE ";
    public static final String SUBJECT=" Test Subject: ";
    public static final String SUBJECT_ENTITY=" Test Subject: ENTITY ";
    public static final String EXPECTED=" Expected Value: ";
    public static final String ACTUAL=" Actual Value: ";
    public static final String NO_SUCH_METHOD = " NO_SUCH_SALESFORCE_METHOD_NAME_AVAILABLE_IN_API ";

    public static final String SOAP_ID = "sf:id";
    public static final String SOAP_DEVELOPER_NAME = "sf:DeveloperName";
    public static final String SOAP_RESULT = "result";
    public static final String SFDX_USER="integration.user@canadalife.de";
    public static final String SFDX_LOGIN_URL="sfdx_login_url";
    public static final String SFDX_CLIENT_ID="sdfx_client_id";
    public static final String SFDX_PRIVATE_KEY="src/test/resources/sdfx/prod.key";

    //Forms
    public static final String TEXT = "text";
    public static final String PICKLIST = "picklist";
    public static final String RADIO = "radio";
    public static final String FRM_CONTROL_TYPE_NOT_FOUND = "CONTROL_TYPE_NOT_FOUND_ON_FORM";
    public static final String FRM_OPTION_PL_WITH_NO_VALUES = "OPTION_PICKLIST_FOUND_WITH_NO_OPTION_VALUES ";

    //Message display
    public static final String ENTITY= " Entity: ";
    public static final String SOQL = "SOQL: ";
    public static final String ACCESSOR = "\r\n  Accessor: ";
    public static final String LEVEL= " Level: ";
    public static final String CR = "\r\n";
    public static final String ERR = "ERROR";
    public static final String PROFILE = "Profile ";
    public static final String PS= "PermissionSet ";
    public static final String OBJECT_ENTRY_MISSING= "NO_ENTRY_FOR_OBJECTS ";
    public static final String ENTITY_TYPE = " Entity Type:";
    public static final String RECORD_ACCESS_DEFS_FLDR="recordAccessDefs";
    public static final String SOQL_SELECT = " select ";
    public static final String SOQL_FROM = " from ";
    public static final String SOQL_WHERE = " where ";
    public static final String OPERATOR= " operator =";
    public static final String SOQL_STR="SOQL = ";
    public static final String OWNER_SHARING_RULE_NOT_FOUND= "OWNER_SHARING_RULE_NOT_FOUND: ";
    public static final String ENTITY_NOT_FOUND = "SALESFORCE_ENTITY_NOT_FOUND: ";
    public static final String ATTRIBUTE_MISSING="ATTRIBUTE_MISSING: ";
    public static final String CSV_FILE_ENCODING="cp1251";
    public static final String VALUE=" value: ";
    public static final String TARGET_ORG="target_salesforce_environment";
    public static final String ROLE_NOT_FOUND="ROLE_NOT_FOUND ";
    private Constants() {

        throw new IllegalStateException("Constants class");
    }
}
