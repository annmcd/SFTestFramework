package com.test.cle.salesforce.utils;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.LoginResult;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.FieldDefinition;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.metadata.*;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Field;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.PicklistEntry;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.test.cle.salesforce.testutils.Constants;
import net.thucydides.core.annotations.Step;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


public class PrintEntityMetadata {

    static PartnerConnection partnerConnection = null;
    static MetadataConnection metadataConnection = null;
    static EnterpriseConnection enterpriseConn;

    public static Properties getProperties(String filePath) throws IOException {

        Properties prop = new Properties ();
        FileInputStream input = null;
        try {
            input = new FileInputStream (filePath);
            prop.load (input);

        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            if (input != null) {
                input.close ();
            }
        }

        return prop;
    }


    public static String getPropertiesFileNameForEnvironment(String env) {
        String userDirectory = Paths.get ("")
                .toAbsolutePath ()
                .toString ();
        Path resourceDirectory = null;
        String path = "";
        if (userDirectory.endsWith ("SFTestFramework")) {
            resourceDirectory = Paths.get ("SFTestPack", "src", "test", "resources");

        } else {
            resourceDirectory = Paths.get ("src", "test", "resources");

        }
        path = resourceDirectory.toAbsolutePath ().toString () + "/" + env + "_configuration" + ".properties";

        return path;
    }


    public static void setPartnerConnection(String targetEnvironment) {

        try {
            String file = getPropertiesFileNameForEnvironment (targetEnvironment);

            Properties propertyFileSettings = getProperties (file);
            ConnectorConfig config = new ConnectorConfig ();
            String userName = propertyFileSettings.getProperty ("user");
            config.setUsername (userName);

            String key = Secret.decrypt (propertyFileSettings.getProperty ("key"));
            config.setPassword (key);

            //  config.setAuthEndpoint ("https://test.salesforce.com/services/Soap/u/46.0");
            config.setAuthEndpoint (propertyFileSettings.getProperty ("endPoint"));
            String useProxy = propertyFileSettings.getProperty ("use_proxy");
            if (useProxy.equalsIgnoreCase ("true")) {
                config.setProxy (
                        propertyFileSettings.getProperty ("proxyServer"),
                        Integer.parseInt (propertyFileSettings.getProperty ("proxyPort")));
            }
            partnerConnection = new PartnerConnection (config);

        } catch (IOException | ConnectionException e) {
            e.printStackTrace ();
        }
    }


    public static Field[] getSalesforceFieldInfo(String type) {
        Field[] fields = null;
        try {
            System.out.println ("checking connection " + partnerConnection.getSessionHeader () + " type " + type);
            DescribeSObjectResult describeSObjectResult =
                    partnerConnection.describeSObject (type);
            if (describeSObjectResult != null) {
                System.out.println ("sObject name: " +
                        describeSObjectResult.getName ());
                if (describeSObjectResult.isCreateable ()) {
                    System.out.println ("Createable");
                }
                fields = describeSObjectResult.getFields ();
                System.out.println ("Has " + fields.length + " fields");
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return fields;
    }


    public static void setEnterpriseConnection(String targetEnvironment) {
        try {
            String file = getPropertiesFileNameForEnvironment (targetEnvironment);

            Properties propertyFileSettings = getProperties (file);
            ConnectorConfig config = new ConnectorConfig ();
            String userName = propertyFileSettings.getProperty ("user");
            config.setUsername (userName);

            String key = Secret.decrypt (propertyFileSettings.getProperty ("key"));
            config.setPassword (key);

            config.setAuthEndpoint ("https://test.salesforce.com/services/Soap/c/46.0");
            config.setProxy (
                    propertyFileSettings.getProperty ("proxyServer"),
                    Integer.parseInt (propertyFileSettings.getProperty ("proxyPort")));
            enterpriseConn = new EnterpriseConnection (config);
            LoginResult lr = enterpriseConn.login (enterpriseConn.getConfig ().getUsername (),
                    enterpriseConn.getConfig ().getPassword ());

            //Connector for the metadata connection via enterprise connection login result
            ConnectorConfig connConfig = new ConnectorConfig ();
            connConfig.setSessionId (lr.getSessionId ());
            connConfig.setServiceEndpoint (lr.getMetadataServerUrl ());
            String useProxy = propertyFileSettings.getProperty ("use_proxy");
            if (useProxy.equalsIgnoreCase ("true")) {
                System.out.println ("proxy server " + propertyFileSettings.getProperty ("proxyServer"));
                connConfig.setProxy (propertyFileSettings.getProperty ("proxyServer"),
                        Integer.parseInt (propertyFileSettings.getProperty ("proxyPort")));
            }
            metadataConnection = new MetadataConnection (connConfig);

        } catch (Exception e) {
            e.printStackTrace ();
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


    public static void printSalesforceGenericValues(String entityName) {

        Field[] fields = getSalesforceFieldInfo (entityName);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            StringBuffer salesForceValue = new StringBuffer ();
            if (field.getType ().equals (com.sforce.soap.partner.FieldType.picklist)
                    || field.getType ().equals (com.sforce.soap.partner.FieldType.multipicklist)) {
                PicklistEntry[] picklistValues = field.getPicklistValues ();

                salesForceValue.append ("picklist");
                for (int j = 0; j < picklistValues.length; j++) {
                    //   System.out.println ("\tItem: " + picklistValues[j].getLabel ());
                    salesForceValue.append (",").append (picklistValues[j].getLabel ());

                }
            } else if (field.getType ().toString ().contains ("reference")) {

                String[] salesForceReferenceToValues = field.getReferenceTo ();
                salesForceValue.append ("reference");
                for (String refValue : salesForceReferenceToValues) {
                    salesForceValue.append (",").append (refValue);
                }
            } else {

                salesForceValue.append (field.getType ().toString () + field.getLength ());
            }

            System.out
                    .println ("FIELD, " + field.getName () + ", DATATYPE, " + salesForceValue + ", EN_LABEL, " + field.getLabel ());

        }

    }


    public static void printGermanLabel(String entityName) {
        try {
            CustomObjectTranslation objectTranslation =
                    getCutomObjectTranslation (
                            metadataConnection, entityName + Constants.DE_LANG);
            for (CustomFieldTranslation fieldTranslation : objectTranslation.getFields ()) {
                String fieldName = fieldTranslation.getName ();
                System.out.println ("FIELD, " + fieldName + ", DE_LABEL," + fieldTranslation.getLabel ());
            }

        } catch (Exception e) {
            e.printStackTrace ();
        }
    }


    public static void printFieldTranslations(String entityName) {
        CustomObjectTranslation objectTranslationPL =
                getCutomObjectTranslation (
                        metadataConnection, entityName + Constants.DE_LANG);

        for (CustomFieldTranslation fieldTranslationPL : objectTranslationPL.getFields ()) {

            for (PicklistValueTranslation value : fieldTranslationPL.getPicklistValues ()) {

                System.out.println ("FIELD, " + fieldTranslationPL.getName () + ", PL_TRANSLATION, " + value.getMasterLabel () + " -> " + value.getTranslation ());

            }
        }

    }


    public static void printBooleanFieldValues(String entityName) {

        Field[] fields = getSalesforceFieldInfo (entityName);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            if (field.isRestrictedPicklist ()) {
                System.out.println ("FIELD, " + field.getName () + ", IS_A_RESTRICTED_PICLKIST_FIELD");

            }
            if (field.isUnique ()) {
                System.out.println ("FIELD, " + field.getName () + ", IS_A_UNIQUE_FIELD");

            }
            if (field.isExternalId ()) {
                System.out.println ("FIELD, " + field.getName () + ", IS_AN_EXTERNAL_ID_FIELD");

            }
            if (field.getInlineHelpText () != null) {
                System.out.println ("FIELD, " + field.getName () + ", HELP_TEXT=" + field.getInlineHelpText ());
            }

        }
    }


    @Step
    public static void printFieldDescription(String entityName) {

        String soql = " SELECT EntityDefinition.QualifiedApiName, QualifiedApiName, Description " +
                "  FROM FieldDefinition  WHERE EntityDefinition.QualifiedApiName IN (" + "'" + entityName + "')";
        QueryResult qr = null;
        try {
            qr = enterpriseConn.query (soql);
            if (qr.getSize () > 0) {
                for (SObject sfObj : qr.getRecords ()) {
                    FieldDefinition fObj = (FieldDefinition) sfObj;
                    if (fObj.getDescription () != null) {
                        System.out.println ("FIELD, " + fObj.getDeveloperName () + ", DESCRIPTION=" + fObj.getDescription ());
                    }
                }
            }
        } catch (ConnectionException e) {
            e.printStackTrace ();
        }
    }


    public static void main(String[] args) {

        String env = args[0];
        System.out.println ("Environment= " + args[1]);
        String[] entities = args[1].split (",");
        setPartnerConnection (env);
        setEnterpriseConnection (env);

        System.out.println ("partner conn" + partnerConnection.getSessionHeader ());
        System.out.println ("enterprise conn" + enterpriseConn.getSessionHeader ());

        for (String entityName : entities) {

            System.out.println ("******************* Generic Values-" + entityName + "*********************");
            printSalesforceGenericValues (entityName);

            System.out.println ("******************* German Labels-" + entityName + "*********************");
            printGermanLabel (entityName);

            printFieldTranslations (entityName);


            System.out.println ("*******************  Boolean Values & Inline Help Text-" + entityName + "*********************");

            printBooleanFieldValues (entityName);


            System.out.println ("*******************  Field Descriptions-" + entityName + "*********************");
            printFieldDescription (entityName);

            System.out.println ("\r\n########################## Report Complete for " + entityName + " #################################\r\n\r\n");

            System.out.println ("Note if no values are listed on this report for Description and HelpText Field values, it implies that there are no entries.");
        }
    }


}


