package com.test.cle.salesforce.serenity.steps.sfcoverage;


import com.sforce.soap.enterprise.DescribeGlobalSObjectResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.FieldDefinition;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.metadata.*;
import com.sforce.soap.partner.Field;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.steps.common.SObjectFields;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.yamlelements.security.*;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class EntityFieldCoverageSteps {

    static Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());

    static EnterpriseConnection enterpriseConnection;
    static PartnerConnection partnerConnection;

   /** public static Field[] getSalesforceFieldInfo(String type) {
        Field[] fields = null;
        try {

            DescribeSObjectResult describeSObjectResult =
                    partnerConnection.describeSObject (type);

            if (describeSObjectResult != null) {
                log.debug ("sObject name: " +
                        describeSObjectResult.getName ());
                fields = describeSObjectResult.getFields ();
                log.debug ("Has " + fields.length + " fields");
            }
        } catch (Exception e) {
            log.error (e);
        }
        return fields;
    }**/




    public static List<String> getDataTypeFieldList(EntityDef entity) {
        List<String> list = new ArrayList ();
        List dataTypes = entity.getDataTypes ();
        if (dataTypes != null) {
            for (Object attr : dataTypes) {
                DataType beanAttribute = (DataType) attr;
                list.add (beanAttribute.getName ().toLowerCase ());
            }
        }
        return list;
    }

    public static List<String> getPicklistFieldList(EntityDef entity) {
        List<String> list = new ArrayList ();
        List plFields = entity.getPicklists ();
        if (plFields != null) {
            for (Object attr : plFields) {
                EnPicklist beanAttribute = (EnPicklist) attr;
                list.add (beanAttribute.getName ().toLowerCase ());
            }
        }
        return list;
    }


    public static List<String> getLabelFieldList(EntityDef entity) {
        List<String> list = new ArrayList ();
        List enLabels = entity.getLabels ();
        for (Object attr : enLabels) {
            EnLabel beanAttribute = (EnLabel) attr;
            list.add (beanAttribute.getName ().toLowerCase ());
        }
        return list;
    }


    public static List<String> getDeLabelFieldList(EntityDef entity) {
        List<String> list = new ArrayList ();
        List deLabels = entity.getDeLabels ();
        if (deLabels != null) {
            for (Object attr : deLabels) {
                DeLabel beanAttribute = (DeLabel) attr;
                list.add (beanAttribute.getName ().toLowerCase ());
            }
        }
        return list;
    }


    public static List<String> getMandatoryList(EntityDef entity) {
        List<String> list = new ArrayList ();
        List mandatory = entity.getMandatory ();
        if (mandatory != null) {
            for (Object attr : mandatory) {
                MandatoryAttr beanAttribute = (MandatoryAttr) attr;
                list.add (beanAttribute.getName ().toLowerCase ());
            }
        }
        return list;
    }

    public static List<String> getDEPicklistsList(EntityDef entity) {
        List<String> list = new ArrayList ();
        List dePL = entity.getDePicklists ();
        if (dePL != null) {
            for (Object attr : dePL) {
                DePicklist beanAttribute = (DePicklist) attr;
                list.add (beanAttribute.getName ().toLowerCase ());
            }
        }
        return list;
    }


    private static void checkYamlList(List types, String category, String entityName, boolean applyAssertions, String checkTypes) {

        //  Field[] fields = getSalesforceFieldInfo (entityName);
        SObjectFields.setEnterpriseConnection (enterpriseConnection);
        List<FieldDefinition> fields = SObjectFields.getSalesforceFieldDefinitionInfo (entityName);
        for (int i = 0; i < fields.size (); i++) {
            FieldDefinition field = fields.get (i);

            // log.debug ("Found Custom Field" + field.getName ());
            if (!types.contains (field.getQualifiedApiName ().toLowerCase ())) {
                String errMessage = Constants.ENTITY + entityName + ", Custom Attribute not specified in " +
                        category + " check: " + field.getQualifiedApiName ();
                if (checkTypes.equalsIgnoreCase ("datatypes")) {
                    errMessage += " salesforce value=" + field.getDataType ();
                } else if (checkTypes.equalsIgnoreCase ("labels")) {
                    errMessage += " salesforce value=" + field.getLabel ();
                    //Its a Mandatory field and should be indicated in yaml
                } else if (checkTypes.equalsIgnoreCase ("mandatory")) {
                    if (!field.getIsNillable ()) {
                        errMessage += " salesforce isNillable() value =" + field.getIsNillable ();
                    }
                }
                if (applyAssertions) {
                    Assert.fail (errMessage);
                } else {
                    log.error (errMessage);
                }
            }

        }
    }

    public static CustomObjectTranslation getCutomObjectTranslation(
            MetadataConnection metadataConnection, String type) {

        CustomObjectTranslation objectTranslation = null;
        try {
            ReadResult result =
                    metadataConnection.readMetadata ("CustomObjectTranslation", new String[]{type});
            for (Metadata data : result.getRecords ()) {
                objectTranslation = (CustomObjectTranslation) data;

            }
        } catch (ConnectionException e) {
            e.printStackTrace ();
        }
        return objectTranslation;
    }


    public static boolean checkYamlListForPicklists(PartnerConnection conn, EntityDef entityDef) {

        partnerConnection = conn;
        boolean success = true;
        SObjectFields.setConnection(partnerConnection);
        List<String> definedENPicklists = getPicklistFieldList (entityDef);
        if (definedENPicklists.size ()>0) {
            Field[] fields = SObjectFields.getSalesforceFieldInfo (entityDef.getName ());
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.isDependentPicklist () || field.isRestrictedPicklist ()) {
                    if (!definedENPicklists.contains (field.getName ().toLowerCase ())) {
                        log.error (Constants.ENTITY + entityDef.getName () + " EN_PICKLIST_NOT_DEFINED " + field.getName ());
                        success = false;
                    }
                }
            }
        } else {
            log.error (Constants.ENTITY + " No Picklists defined in Yaml");
        }
        return success;
    }

    private static void checkYamlListForMandtory(List types, String category, String entityName, boolean applyAssertions) {
        SObjectFields.setConnection(partnerConnection);
       // Field[] fields = getSalesforceFieldInfo (entityName);
        Field[] fields = SObjectFields.getSalesforceFieldInfo (entityName);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            if (field.isCustom ()) {

                if (!types.contains (field.getName ().toLowerCase ())) {
                    //we have an issue if the attribute is also mandatory
                    if (!field.isNillable ()) {
                        String errMessage = Constants.ENTITY + entityName + ", Custom Attribute not specified in " + category + " check: " + field.getName ();
                        if (applyAssertions) {
                            Assert.fail (errMessage);
                        } else {
                            log.error (errMessage);
                        }
                    }
                }
            }
        }
    }

    @Step
    public static void checkTranslation(MetadataConnection metadataConnection, String entityName, String entityYamlFile, boolean assertOnFailure) {

        EntityDef entity = null;
        try (InputStream inputStream = new FileInputStream (new File (entityYamlFile))) {
            Yaml yaml = new Yaml (new Constructor (EntityDef.class));
            entity = yaml.load (inputStream);
        } catch (IOException e) {
            log.error (e);
        }

        List deLabelList = getDeLabelFieldList (entity);
        CustomObjectTranslation objectTranslation =
                getCutomObjectTranslation (
                        metadataConnection, entityName + Constants.DE_LANG);
        if (objectTranslation != null) {
            for (CustomFieldTranslation fieldTranslation : objectTranslation.getFields ()) {
                String fieldName = fieldTranslation.getName ().toLowerCase ();


                if (fieldTranslation.getName ().toLowerCase ().endsWith ("__c")) {
                    //custom attribute found
                    if (!deLabelList.isEmpty ()) {
                        if (!deLabelList.contains (fieldName)) {

                            String errMessage = Constants.ENTITY + entityName + ", Custom Attribute not specified in DE_LABEL check: " + fieldName + " salesforce value=" + fieldTranslation.getLabel ();
                            if (assertOnFailure == true) {
                                Assert.fail (errMessage);
                            } else {
                                log.error (errMessage);
                            }
                        }

                    } else {
                        String msg = Constants.ENTITY + entityName + " NO DE_LABELS SUPPLIED FOR " + fieldName + " ,salesforce value " + fieldTranslation.getLabel ();
                        if (assertOnFailure == true) {
                            Assert.fail (msg);
                        } else {
                            log.error (msg);
                        }
                    }
                }
            }
        }

        //check DE Picklist entries
        List dePicks = getDEPicklistsList (entity);
        CustomObjectTranslation objectTranslationPL =
                getCutomObjectTranslation (
                        metadataConnection, entityName + Constants.DE_LANG);
        if (objectTranslationPL != null && dePicks.isEmpty ()) {
            String msg = Constants.ENTITY + entityName + " NO_DE_PICKLISTS Entered in Yaml File for entity " + entityName;
            if (assertOnFailure) {
                Assert.fail (msg);
            }
            log.error (msg);
        } else if (objectTranslation != null && !dePicks.isEmpty ()) {
            for (CustomFieldTranslation fieldTranslationPL : objectTranslationPL.getFields ()) {
                String name = fieldTranslationPL.getName ().toLowerCase ();
                if (name.endsWith ("__c")) {
                    PicklistValueTranslation[] translatedPicklists = fieldTranslationPL.getPicklistValues ();
                    if (!dePicks.contains (fieldTranslationPL.getName ().toLowerCase ())) {
                        StringBuffer buff = new StringBuffer ();

                        for (PicklistValueTranslation value : translatedPicklists) {
                            String message = Constants.ENTITY + entityName + " NO_DE_PICKLSITS SPECIFIED check: " +
                                    name + " Translation object salesforce value=" + value.getTranslation ();
                            if (!value.getTranslation ().isEmpty ()) {
                                log.error (message);
                            }

                        }

                    }
                }

            }
        }
    }


    @Step
    public static void checkENMetadata(EnterpriseConnection eConn, PartnerConnection pConn,
                                       String entityYamlFile, String entityName, boolean applyAssertions) throws FileNotFoundException {

        enterpriseConnection = eConn;

        partnerConnection = pConn;
        //Serialise Yaml Here, get lists of all groupsings
        EntityDef entity = null;
        try (InputStream inputStream = new FileInputStream (new File (entityYamlFile))) {
            Yaml yaml = new Yaml (new Constructor (EntityDef.class));
            entity = yaml.load (inputStream);
        } catch (IOException e) {
            log.error (e);
        }

        List dataTypes = getDataTypeFieldList (entity);
        checkYamlList (dataTypes, "DATA_TYPE", entityName, applyAssertions, "datatypes");


        List labels = getLabelFieldList (entity);
        checkYamlList (labels, "EN_LABEL", entityName, applyAssertions, "labels");


        List mandatories = getMandatoryList (entity);
        checkYamlListForMandtory (mandatories, "MANDATORY", entityName, applyAssertions);


    }


    public static StringBuffer checkCustomEntities(EnterpriseConnection eConn, List<String> entities) {

        StringBuffer nonTestedEntities = new StringBuffer ();
        DescribeGlobalSObjectResult[] result = new DescribeGlobalSObjectResult[0];
        try {

            result = eConn.describeGlobal ().getSobjects ();
        } catch (ConnectionException e) {
            log.error (e);
        }
        entities = entities.stream ()
                .map (String::toLowerCase)
                .collect (toList ());


        List<String> salesforceCustomEntities = new ArrayList<> ();
        for (DescribeGlobalSObjectResult sObject : result) {


            if (sObject.getName ().endsWith ("__c")) {
                salesforceCustomEntities.add (sObject.getName ().toLowerCase ());
            }
        }

        log.error ("No of Custom Entities in the ORG=" + salesforceCustomEntities.size ());
        for (String salesforceEntity : salesforceCustomEntities) {
            if (!entities.contains (salesforceEntity)) {
                nonTestedEntities.append (" ").append (salesforceEntity);
                log.error ("NO TEST COVERAGE FOR CUSTOM ENTITY " + salesforceEntity);
            }
        }

        return nonTestedEntities;

    }


    public static boolean checkEntityFieldDescriptions(EnterpriseConnection eConn, String entity) {

        boolean success = true;

        String soql = "Select EntityDefinition.QualifiedApiName, QualifiedApiName," +
                " DataType, Description FROM FieldDefinition  WHERE EntityDefinition.QualifiedApiName " +
                "IN(" + "'" + entity + "')  and QualifiedApiName like '%__c'";


        QueryResult qr = null;
        try {
            qr = eConn.query (soql);
        } catch (ConnectionException e) {
            log.error (e);
        }

        if (qr.getSize () > 0) {
            for (SObject obj : qr.getRecords ()) {
                FieldDefinition fieldDefinition = (FieldDefinition) obj;
                String desc = fieldDefinition.getDescription ();

                if (desc == null) {
                    success = false;
                    log.error (Constants.ENTITY + entity + " FIELD_DESCRIPTION_IS_NULL -field: " + fieldDefinition.getQualifiedApiName () + Constants.CR);
                }
            }


        }

        return success;
    }


    @Step
    public static boolean checkEncryptedFields(PartnerConnection pConn, EntityDef entityDef) {
        boolean result = true;
        partnerConnection = pConn;
        SObjectFields.setConnection (pConn);
        Field[] fields = SObjectFields.getSalesforceFieldInfo (entityDef.getName ());
        List<EncryptedField> definedEncrypted = entityDef.getEncryptedFields ();
        if (definedEncrypted != null) {
            List<String> expectedList = new ArrayList<> ();
            for (EncryptedField pl : definedEncrypted) {
                expectedList.add (pl.getName ().toLowerCase ());
            }


            for (Field field : fields) {
                if (field.isEncrypted ()) {

                    if (!expectedList.contains (field.getName ().toLowerCase ())) {
                        result = false;
                        log.error (Constants.ENTITY + entityDef.getName () + ", ENCRYPTED_FIELD_NOT_DEFINED " + field.getName ());
                    }
                }
            }
        } else {
            log.error (Constants.ENTITY + entityDef.getName () + " Warning: NO SPECIFICATION IN YAML FOR ENCRYPTED_FIELD " + entityDef.getName ());
        }
        return result;
    }


    @Step
    public static boolean checkRestrictedPicklists(PartnerConnection pConn, EntityDef entityDef) {
        boolean result = true;
        partnerConnection = pConn;
        List<RestrictedPickList> definedRestrictedPLs = entityDef.getRestrictedPickLists ();
        if (definedRestrictedPLs != null) {
            List<String> expectedList = new ArrayList<> ();
            for (RestrictedPickList pl : definedRestrictedPLs) {
                expectedList.add (pl.getName ().toLowerCase ());
            }


            SObjectFields.setConnection (pConn);
            Field[] fields = SObjectFields.getSalesforceFieldInfo (entityDef.getName ());

            for (Field field : fields) {
                if (field.isRestrictedPicklist ()) {

                    if (!expectedList.contains (field.getName ().toLowerCase ())) {
                        result = false;
                        log.error (Constants.ENTITY + entityDef.getName () + ", RESTRICTED_PICKLIST_NOT_DEFINED " + field.getName ());
                    }
                }
            }
        } else {
            log.error (Constants.ENTITY + entityDef.getName () + " Warning: NO SPECIFICATION IN YAML FOR RESTRICTED_PICKLIST " + entityDef.getName ());
        }
        return result;
    }


    @Step
    public static boolean checkHelpText(String entityName, PartnerConnection pConn) {

        boolean result = true;

        SObjectFields.setConnection (pConn);
        Field[] fields = SObjectFields.getSalesforceFieldInfo (entityName);

        for (Field field : fields) {
            if (field.getInlineHelpText () == null) {
                result = false;
                log.error (Constants.ENTITY + entityName + ", FIELD_HELP_TEXT_IS_NULL " + field.getName ());
            }

        }

        return result;
    }

}
