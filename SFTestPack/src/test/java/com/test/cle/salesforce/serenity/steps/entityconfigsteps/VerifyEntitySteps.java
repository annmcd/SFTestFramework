package com.test.cle.salesforce.serenity.steps.entityconfigsteps;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.FieldDefinition;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.metadata.*;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.PicklistEntry;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.yamlelements.security.*;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class VerifyEntitySteps {

    static Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());

    @Step
    public static EntityDef loadYaml(String entityYamlFile) {
        log.debug ("yaml file = " + entityYamlFile);

        Yaml yaml = new Yaml (new Constructor (EntityDef.class));

        EntityDef entity = null;
        try (InputStream inputStream = new FileInputStream (new File (entityYamlFile))) {

            entity = yaml.load (inputStream);

        } catch (IOException e) {
            log.error (e);
        }
        return entity;
    }


    @Step
    public static List<String> checkDatatTypes(EnterpriseConnection enterpriseConnection, EntityDef entityDef) {

        List<String> errorLists= new ArrayList<> ();
        List<DataType> expectedDataTypes = entityDef.getDataTypes ();

        if (expectedDataTypes != null) {
            String entityName = entityDef.getName ();
            String soql = " SELECT EntityDefinition.QualifiedApiName, QualifiedApiName, DataType " +
                    "  FROM FieldDefinition  WHERE EntityDefinition.QualifiedApiName IN (" + "'" + entityName + "')";

            QueryResult qr = null;
            try {
                qr = enterpriseConnection.query (soql);
            } catch (ConnectionException e) {
                log.error (e);
            }
            //DataTypes
            HashMap<String, String> mapSFDataTypes
                    = new HashMap<> ();

            if (qr.getSize () > 0) {
                for (SObject sfObj : qr.getRecords ()) {
                    FieldDefinition fObj = (FieldDefinition) sfObj;
                    mapSFDataTypes.put (fObj.getQualifiedApiName (), fObj.getDataType ());

                    log.debug ("FieldName: " + fObj.getQualifiedApiName () + " datatType: " + fObj.getDataType ());
                }
            }

            for (Object attr : expectedDataTypes) {
                String errMsg = "";

                String definedFieldName = ((DataType) attr).getName ();
                String definedDataTypeValue = ((DataType) attr).getValue ();

                if (!mapSFDataTypes.containsKey (definedFieldName)) {
                    //field has not been created on Entity in salesforce

                    errMsg = "DatatTypes " + Constants.SUBJECT_ENTITY + entityName + " " + Constants.FIELD_NOT_DEFINED + definedFieldName;
                    errorLists.add (errMsg);
                } else {
                    //compare the values
                    String salesforceValue = mapSFDataTypes.get (definedFieldName);
                    if (!definedDataTypeValue.equalsIgnoreCase (salesforceValue)) {
                        errMsg= Constants.CR+"DataTypes" + Constants.SUBJECT_ENTITY + entityName + ", Field: " + definedFieldName + Constants.CR +
                                Constants.EXPECTED + definedDataTypeValue + Constants.CR +
                                Constants.ACTUAL + salesforceValue + Constants.CR;
                        errorLists.add (errMsg);
                    }

                }
                if (!errMsg.isEmpty ()) {
                    log.error (errMsg);

                }
            }

        }
        return errorLists;
    }


    @Step
    public static List<String> checkLabels(EnterpriseConnection enterpriseConnection, EntityDef entityDef) {


        List<String> errorLists= new ArrayList<> ();
        List<EnLabel> expectedLabels = entityDef.getLabels ();

        if (expectedLabels != null) {
            String entityName = entityDef.getName ();
            String soql = " SELECT EntityDefinition.QualifiedApiName, QualifiedApiName, Label" +
                    "  FROM FieldDefinition  WHERE EntityDefinition.QualifiedApiName IN (" + "'" + entityName + "')";

            QueryResult qr = null;
            try {
                qr = enterpriseConnection.query (soql);
            } catch (ConnectionException e) {
                log.error (e);
            }
            //English Labels
            HashMap<String, String> mapSFEnLabels
                    = new HashMap<> ();

            if (qr.getSize () > 0) {
                for (SObject sfObj : qr.getRecords ()) {
                    FieldDefinition fObj = (FieldDefinition) sfObj;
                    mapSFEnLabels.put (fObj.getQualifiedApiName ().toLowerCase (), fObj.getLabel ());
                    log.debug (entityName + " FieldName: " + fObj.getQualifiedApiName () + " Label: " + fObj.getLabel ());
                }
            }

            for (Object attr : expectedLabels) {
                String errMsg = "";

                String definedFieldName = ((EnLabel) attr).getName ().toLowerCase ();
                String definedLabel = ((EnLabel) attr).getValue ();

                if (!mapSFEnLabels.containsKey (definedFieldName)) {
                    //field has not been created on Entity in salesforce
                    errMsg = "En Labels " + Constants.SUBJECT_ENTITY + entityName + " " + Constants.FIELD_NOT_DEFINED + definedFieldName;
                    errorLists.add (errMsg);
                } else {
                    //compare the values
                    String salesforceValue = mapSFEnLabels.get (definedFieldName);
                    if (!definedLabel.equals (salesforceValue)) {
                        errMsg = Constants.CR+ "En Labels " + Constants.SUBJECT_ENTITY + entityName + ", Field: " + definedFieldName + Constants.CR +
                                Constants.EXPECTED + definedLabel + Constants.CR +
                                Constants.ACTUAL + salesforceValue + Constants.CR;
                        errorLists.add (errMsg);
                    }
                }
                if (!errMsg.isEmpty ()) {
                    log.error (errMsg);

                }
            }

        }
        return errorLists;
    }


    @Step
    public static List<String> checkMandatory(EnterpriseConnection enterpriseConnection, EntityDef entityDef) {

        String errMsg = "";

        List<String> errorLists= new ArrayList<> ();
        List<MandatoryAttr> expectedMandatoryFields = entityDef.getMandatory ();

        if (expectedMandatoryFields != null) {
            String entityName = entityDef.getName ();
            String soql = " SELECT EntityDefinition.QualifiedApiName, QualifiedApiName, IsNillable" +
                    "  FROM FieldDefinition  WHERE EntityDefinition.QualifiedApiName IN (" + "'" + entityName + "')";

            QueryResult qr = null;
            try {
                qr = enterpriseConnection.query (soql);
            } catch (ConnectionException e) {
                log.error (e);
            }

            List mandatoryFieldsSF = new ArrayList ();
            if (qr.getSize () > 0) {
                for (SObject sfObj : qr.getRecords ()) {
                    FieldDefinition fObj = (FieldDefinition) sfObj;
                    if (fObj.getIsNillable () == false) {
                        mandatoryFieldsSF.add (fObj.getQualifiedApiName ().toLowerCase ());
                        log.debug (entityName + " FieldName: " + fObj.getQualifiedApiName () + " IsNillable(): " + fObj.getIsNillable ());
                    }
                }
            }

            for (Object attr : expectedMandatoryFields) {
                errMsg = "";

                String definedFieldName = ((MandatoryAttr) attr).getName ().toLowerCase ();

                if (!mandatoryFieldsSF.contains (definedFieldName)) {
                    //field has not been created on Entity in salesforce
                    errMsg =Constants.CR+ "Mandatory Field " + Constants.SUBJECT_ENTITY + entityName + " " + Constants.MANDATORY_IND + " " + definedFieldName+ Constants.CR;
                    errorLists.add (errMsg);
                }
                if (!errMsg.isEmpty ()) {
                    log.error (errMsg);
                }
            }
        }
        return errorLists;
    }


    @Step
    public static CustomObjectTranslation getCutomObjectTranslation(
            MetadataConnection metadataConnection, String type) {

        CustomObjectTranslation objectTranslation = null;
        try {

            log.debug ("Get translation for " + type);
            ReadResult result =
                    metadataConnection.readMetadata ("CustomObjectTranslation", new String[]{type});
            for (Metadata data : result.getRecords ()) {
                objectTranslation = (CustomObjectTranslation) data;
            }
        } catch (ConnectionException e) {
            log.error (Constants.STEP_FAILED_IND, e);
        }
        return objectTranslation;
    }


    @Step
    public static List<String> checkDELabels(MetadataConnection metadataConnection, EntityDef entityDef) {

        String assertMsg = "";
        List<String> errorLists= new ArrayList<> ();

        List<DeLabel> expectedDELabels = entityDef.getDeLabels ();
        if (expectedDELabels != null) {
            CustomObjectTranslation objectTranslation =
                    getCutomObjectTranslation (
                            metadataConnection, entityDef.getName () + Constants.DE_LANG);

            HashMap<String, String> mapSFDELabels
                    = new HashMap<> ();
            for (CustomFieldTranslation fieldTranslation : objectTranslation.getFields ()) {
                mapSFDELabels.put (fieldTranslation.getName (), fieldTranslation.getLabel ());
            }

            for (Object attr : expectedDELabels) {
                String errMsg = "";

                String definedFieldName = ((DeLabel) attr).getName ();
                String definedDELabel = ((DeLabel) attr).getValue ();

                if (!mapSFDELabels.containsKey (definedFieldName)) {
                    errMsg =Constants.CR+ "DE Labels " + Constants.SUBJECT_ENTITY + entityDef.getName () + " " + Constants.MISSING_TRANSLATION + definedFieldName+Constants.CR;
                    errorLists.add (errMsg);

                } else {
                    String salesforceValue = mapSFDELabels.get (definedFieldName);
                    if (!definedDELabel.equals (salesforceValue)) {
                        errMsg =Constants.CR+ "DE Labels" + Constants.SUBJECT_ENTITY + entityDef.getName () + ", Field: " + definedFieldName + Constants.CR +
                                Constants.EXPECTED + definedDELabel + Constants.CR +
                                Constants.ACTUAL + salesforceValue + Constants.CR;
                        errorLists.add (errMsg);
                    }
                }
                if (!errMsg.isEmpty ()) {
                    log.error (errMsg);

                }
            }

        }
        return errorLists;
    }


    public static Field[] getSalesforceFieldInfo(PartnerConnection connection, String type) {

        Field[] fields = null;
        try {
            DescribeSObjectResult describeSObjectResult = connection.describeSObject (type);
            if (describeSObjectResult != null) {
                log.debug ("sObject name: " + describeSObjectResult.getName ());
                // Get the salesforce fields value
                fields = describeSObjectResult.getFields ();

            }
        } catch (ConnectionException e) {
            log.error ("step failed! {}", e);
        }
        return fields;
    }


    @Step
    public static List<String> checkPicklistValues(PartnerConnection partnerConnection, EntityDef entityDef) {

        List<String> errorLists= new ArrayList<> ();

        List<EnPicklist> expectedPicklists = entityDef.getPicklists ();
        if (expectedPicklists != null) {
            HashMap<String, String> mapPicklistFields
                    = new HashMap<> ();
            Field[] fields = getSalesforceFieldInfo (partnerConnection, entityDef.getName ());
            if (fields != null) {

                for (int i = 0; i < fields.length; i++) {
                    StringBuffer sfPickListValue = new StringBuffer ();
                    Field field = fields[i];

                    if (field.getType ().equals (com.sforce.soap.partner.FieldType.picklist)
                            || field.getType ().equals (com.sforce.soap.partner.FieldType.multipicklist)) {

                        PicklistEntry[] picklistValues = field.getPicklistValues ();

                        for (int j = 0; j < picklistValues.length; j++) {
                            if (j == 0) {
                                sfPickListValue.append (picklistValues[j].getLabel ());
                            } else {
                                sfPickListValue.append (",").append (picklistValues[j].getLabel ());
                            }

                        }
                    }
                    if (sfPickListValue.length () > 0) {
                        mapPicklistFields.put (field.getName (), sfPickListValue.toString ().trim ());
                    }
                }
            }
            //Does the picklist Field exist
            for (Object attr : expectedPicklists) {
                String errMsg = "";

                String definedFieldName = ((EnPicklist) attr).getName ();
                String definedPlValue = ((EnPicklist) attr).getValue ();

                if (!mapPicklistFields.containsKey (definedFieldName)) {
                    //field has not been created on Entity in salesforce
                    errMsg = Constants.CR+"EN Picklists " + Constants.SUBJECT_ENTITY + entityDef.getName () + " " + Constants.PICKLIST_FIELD_NOT_DEFINED + definedFieldName+ Constants.CR;
                    errorLists.add (errMsg);
                } else {

                    String salesforceValue = mapPicklistFields.get (definedFieldName);
                    if (!definedPlValue.equals (salesforceValue)) {
                        errMsg =Constants.CR+ "EN Picklists" + Constants.SUBJECT_ENTITY + entityDef.getName () + ", Field: " + definedFieldName + Constants.CR +
                                Constants.EXPECTED + definedPlValue + Constants.CR +
                                Constants.ACTUAL + salesforceValue + Constants.CR;
                        errorLists.add (errMsg);
                    }

                }
                if (!errMsg.isEmpty ()) {
                    log.error (errMsg);

                }
            }
            // Does the picklist value match

        }
        return errorLists;
    }


    @Step
    public static List<String> checkRemoval(PartnerConnection partnerConnection, EntityDef entityDef) {

        String assertMsg = "";
        List<Removal> expectedRemovedFields = entityDef.getRemovals ();
        List<String> errorLists= new ArrayList<> ();

        if (expectedRemovedFields != null) {
            Field[] salesforceFieldInfo = getSalesforceFieldInfo (partnerConnection, entityDef.getName ());
            //salesforce Fields that exist on the entity
            List<String> fields = new ArrayList<> ();
            for (Field field : salesforceFieldInfo) {
                fields.add (field.getName ());
            }

            for (Object attr : expectedRemovedFields) {
                String errMsg = "";
                String definedFieldName = ((Removal) attr).getName ();

                if (fields.contains (definedFieldName)) {
                    errMsg =Constants.CR+ "Removals" + Constants.SUBJECT_ENTITY + entityDef.getName () + ", Field: " + definedFieldName + Constants.CR +
                            Constants.ENTITY_IND_FOR_REMOVAL;
                    errorLists.add (errMsg);
                }
                if (!errMsg.isEmpty ()) {
                    log.error (errMsg);

                }
            }
        }
        return errorLists;
    }


    @Step
    public static List<String> checkDEPicklists(MetadataConnection metadataConnection, EntityDef entityDef) {

        String assertMsg = "";
        List<String> errorLists= new ArrayList<> ();
        CustomObjectTranslation objectTranslationPL =
                getCutomObjectTranslation (
                        metadataConnection, entityDef.getName () + Constants.DE_LANG);

        List<DePicklist> expectedDEPicklists = entityDef.getDePicklists ();

        HashMap<String, String> mapDEPicklistFields
                = new HashMap<> ();
        if (objectTranslationPL != null) {

            for (CustomFieldTranslation fieldTranslationPL : objectTranslationPL.getFields ()) {
                String name = fieldTranslationPL.getName ();
                PicklistValueTranslation[] translatedPicklists = fieldTranslationPL.getPicklistValues ();
                StringBuffer picklistsStr = new StringBuffer ();
                int i = 0;
                for (PicklistValueTranslation value : translatedPicklists) {

                    if (i == 0) {
                        picklistsStr.append (value.getTranslation ());
                    } else {
                        if (!value.getTranslation ().isEmpty ()) {
                            picklistsStr.append (",").append (value.getTranslation ());
                        }
                    }
                    i++;
                }
                if (picklistsStr.length () > 0) {
                    log.debug ("Translated Field name " + name + " DE picklist value " + picklistsStr.toString ());
                    mapDEPicklistFields.put (name.toLowerCase (), picklistsStr.toString ());
                }
            }
            if (expectedDEPicklists != null) {
                for (Object attr : expectedDEPicklists) {
                    String errMsg = "";
                    String definedFieldName = ((DePicklist) attr).getName ().toLowerCase ();
                    String definedFieldValue = ((DePicklist) attr).getValue ();

                    if (!mapDEPicklistFields.containsKey (definedFieldName)) {
                        errMsg =Constants.CR+ "DE Picklist " + Constants.SUBJECT_ENTITY + entityDef.getName () +
                                " " + Constants.MISSING_PL_TRANSLATION + definedFieldName + Constants.CR;
                        errorLists.add (errMsg);
                    } else {
                        String salesforceValue = mapDEPicklistFields.get (definedFieldName);
                        if (!definedFieldValue.toLowerCase ().equals (salesforceValue.toLowerCase ())) {
                            errMsg = Constants.CR+"DE Picklists" + Constants.SUBJECT_ENTITY + entityDef.getName () + ", Field: " +
                                    definedFieldName + Constants.CR +
                                    Constants.EXPECTED + definedFieldValue + Constants.CR +
                                    Constants.ACTUAL + salesforceValue + Constants.CR;
                            errorLists.add (errMsg);
                        }
                    }

                    if (!errMsg.isEmpty ()) {
                        log.error (errMsg);

                    }
                }
            }
        }
        return errorLists;
    }

    @Step
    public static List<String> checkEncryptedFields(PartnerConnection partnerConnection, EntityDef entityDef) {


        List<String> errorLists= new ArrayList<> ();
        List<EncryptedField> expectedEncryptedFields = entityDef.getEncryptedFields ();
        Field[] fields = getSalesforceFieldInfo (partnerConnection, entityDef.getName ());
        List<String> encryptedSFFields = new ArrayList<> ();
        for (Field field : fields) {
            if (field.isEncrypted()) {
                String fieldName = field.getName().toLowerCase();
                encryptedSFFields.add(fieldName);
            }
        }
        if (expectedEncryptedFields != null) {
            for (Object attr : expectedEncryptedFields) {
                String errMsg = "";
                String definedFieldName = ((EncryptedField) attr).getName ().toLowerCase ();
                if (!encryptedSFFields.contains (definedFieldName)) {
                    errMsg =Constants.CR+ "Encrypted Field " + Constants.SUBJECT_ENTITY + entityDef.getName () + " " + Constants.ENCRYPTED_IND + " " + definedFieldName;
                    errorLists.add (errMsg);
                }
                if (!errMsg.isEmpty ()) {
                    log.error (errMsg);

                }
            }
        }
        return errorLists;
    }


    @Step
    public static List<String> checkRestrictedPicklist(Field[] fields, EntityDef entityDef) {


        List<String> errorLists= new ArrayList<> ();

        if (entityDef.getRestrictedPickLists () != null) {
            List<RestrictedPickList> expectedPicklists = entityDef.getRestrictedPickLists ();
            //Contains salesforce Restricted Picklists
            List<String> restrictedPLFields = new ArrayList<> ();
            for (Field field : fields) {
                if (field.isRestrictedPicklist ()) {
                    restrictedPLFields.add (field.getName ().toLowerCase ());
                }
            }
            if (expectedPicklists.isEmpty ()) {
                for (Object attr : expectedPicklists) {
                    String errMsg = "";
                    String definedFieldName = ((RestrictedPickList) attr).getName ().toLowerCase ();
                    //If salesforce list of restricted picklists for the entity does not contain our fields
                    if (!restrictedPLFields.contains (definedFieldName)) {
                        errMsg =Constants.CR+ "Restricted Picklist Field " + Constants.SUBJECT_ENTITY + entityDef.getName () +
                                " " + Constants.RESTRICTED_PL_IND + " " + definedFieldName + Constants.CR;
                        errorLists.add (errMsg);
                    }
                    if (!errMsg.isEmpty ()) {
                        log.error (errMsg);

                    }
                }
            }
        }

        return errorLists;
    }

    /** @Step public static String checkisUniqueField(Field[] fields, EntityDef entityDef) {

    String assertMsg = "";
    List<UniqueField> expectedUniques = entityDef.getUniqueFields ();
    //Contains salesforce Unique Fields
    List<String> uniqueFields = new ArrayList<> ();
    for (Field field : fields) {
    if (field.isUnique ()) {
    uniqueFields.add (field.getName ().toLowerCase ());
    }
    }
    if (expectedUniques != null) {
    for (Object attr : expectedUniques) {
    String errMsg = "";
    String definedFieldName = ((UniqueField) attr).getName ().toLowerCase ();
    //If salesforce list  for the entity does not contain our fields
    if (!uniqueFields.contains (definedFieldName)) {
    errMsg = "Unique Field " + Constants.SUBJECT_ENTITY + entityDef.getName () +
    " " + Constants.IS_UNIQUE_FIELD_IND + " " + definedFieldName;
    }
    if (!errMsg.isEmpty ()) {
    log.error (errMsg);
    assertMsg = errMsg;
    }
    }
    }
    return assertMsg;
    }**/


    /**
     * @Step public static String checkisExternalIdField(Field[] fields, EntityDef entityDef) {
     * <p>
     * String assertMsg = "";
     * List<ExternalIDField> expectedExternalIDs = entityDef.getExternalIDs ();
     * //Contains salesforce Unique Fields
     * List<String> externalIdFields = new ArrayList<> ();
     * for (Field field : fields) {
     * if (field.isUnique ()) {
     * externalIdFields.add (field.getName ().toLowerCase ());
     * }
     * }
     * if (expectedExternalIDs != null) {
     * for (Object attr : expectedExternalIDs) {
     * String errMsg = "";
     * String definedFieldName = ((ExternalIDField) attr).getName ().toLowerCase ();
     * //If salesforce list  for the entity does not contain our fields
     * if (!externalIdFields.contains (definedFieldName)) {
     * errMsg = "Unique Field " + Constants.SUBJECT_ENTITY + entityDef.getName () +
     * " " + Constants.IS_EXTERNAL_ID_FIELD_IND + " " + definedFieldName;
     * }
     * if (!errMsg.isEmpty ()) {
     * log.error (errMsg);
     * assertMsg = errMsg;
     * }
     * }
     * }
     * return assertMsg;
     * }
     **/

    @Step
    public static List<String> checkHelpText(Field[] fields, String entityName) {

        String assertMsg = "";
        List<String> errorLists= new ArrayList<> ();
        for (Field field : fields) {
            String errMsg = "";
            if (field.isCustom () && field.getInlineHelpText () == null) {
                errMsg = Constants.CR+"Help Text: " + Constants.SUBJECT_ENTITY + entityName +
                        " " + Constants.HT_FIELD_VALUE_IS_NULL + " " + field.getName ();
                errorLists.add (errMsg);
            }

            if (!errMsg.isEmpty ()) {
                log.error (errMsg);

            }
        }
        return errorLists;
    }


    @Step
    public static List<String> checkFieldDescription(EnterpriseConnection enterpriseConnection, String entityName) {
        String assertMsg = "";
        List<String> errorLists= new ArrayList<> ();
        String soql = " SELECT EntityDefinition.QualifiedApiName, QualifiedApiName, Description " +
                "  FROM FieldDefinition  WHERE EntityDefinition.QualifiedApiName IN (" + "'" + entityName +
                "') and QualifiedApiName like '%__c'";
        QueryResult qr = null;
        try {
            qr = enterpriseConnection.query (soql);
        } catch (ConnectionException e) {
            log.error (e);
        }

        //these are the fields with descriptions in salesforce for the entity

        if (qr.getSize () > 0) {
            for (SObject sfObj : qr.getRecords ()) {
                String errMsg = "";
                FieldDefinition fObj = (FieldDefinition) sfObj;

                if (fObj.getDescription () == null) {
                    errMsg = Constants.CR+"Description: " + Constants.SUBJECT_ENTITY + entityName +
                            " " + Constants.DESCRIPTION_FIELD_VALUE_IS_NULL + " " + fObj.getQualifiedApiName () + Constants.CR;
                    errorLists.add (errMsg);
                }

                if (!errMsg.isEmpty ()) {
                    log.error (errMsg);

                }
            }
        }
        return errorLists;
    }
}