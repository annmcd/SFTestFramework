package com.test.cle.salesforce.serenity.steps.sfrecordaccess;

import com.cle.crm.salesforce.sfbulkdata.core.Const;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.User;
import com.sforce.soap.enterprise.sobject.UserRecordAccess;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.stepdefinitions.common.RecordAccessStruct;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.yamlelements.security.RecordAccessObjectDef;
import com.test.cle.salesforce.yamlelements.security.RecordElements;
import com.test.cle.salesforce.yamlelements.security.RecordEntityAccessDef;
import com.test.cle.salesforce.yamlelements.security.RecordLookupWhereCondition;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class RecordAccessSteps {

    static private Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());

    public static void reportRecordAccessSettings(
            Boolean mode, RecordAccessStruct testParams, EnterpriseConnection enterpriseConnection)
            throws IOException {


        String targetRegion = testParams.getRegion ();
        String targetAccessor = testParams.getAccessorUserName ();
        Yaml yaml = new Yaml (new Constructor (RecordEntityAccessDef.class));

        Iterable<Object> yamlaccessRecords;
        try (InputStream inputStream = new FileInputStream (new File (testParams.getYamlFileSecuritySettings ()))) {

            yamlaccessRecords = yaml.loadAll (inputStream);

            for (Object object : yamlaccessRecords) { // full list of Entities in the yaml list
                try {
                    RecordEntityAccessDef entity =
                            (RecordEntityAccessDef) object; // Each separate Entity structure
                    List<RecordAccessObjectDef> accessSettings = entity.getAccessSettings ();
                    String targetETLEntity = entity.getEtlEntity ();
                    String targetSFEntity = entity.getType ();

                    boolean jobFound = false;
                    //int recordsNotFound=0;
                    if (accessSettings == null) {
                        log.error ("NO RECORDS FOUND FOR  " + entity.getAccessSettings ());
                    } else {
                        for (Object obj : accessSettings) {

                            RecordAccessObjectDef yamlSecuritySettings = (RecordAccessObjectDef) obj;

                            if (targetAccessor.equalsIgnoreCase (
                                    yamlSecuritySettings.getJob ()
                                            + "."
                                            + System.getProperty ("target_salesforce_environment"))) {
                                String soql =
                                        getRecordForLookup (targetETLEntity, targetSFEntity, targetRegion, testParams);
                                // get id of record owned by targetRecordOwner for the specified region.
                                String recordId = getIdFromSoql (soql, testParams);

                                log.debug ("RecordId = " + recordId);
                                if (recordId == null) {
                                    Assert.fail ("No Record Found: " + soql);
                                }
                                // potentially add the system property here
                                String accessorId =
                                        getUserId (
                                                enterpriseConnection,
                                                yamlSecuritySettings.getJob ()
                                                        + "."
                                                        + System.getProperty ("target_salesforce_environment"));
                                log.debug (
                                        yamlSecuritySettings.getJob ()
                                                + " = "
                                                + targetAccessor
                                                + " accessorId = "
                                                + accessorId);
                                doCompareExepectedAndActualSettingsForRecord (
                                        mode, yamlSecuritySettings,
                                        recordId, accessorId,
                                        testParams, targetSFEntity);
                                jobFound = true;
                                break;
                            }
                        }
                    }

                    if (!jobFound) {

                        log.error (
                                "Job: "
                                        + targetAccessor
                                        + " is not specified in yaml file "
                                        + testParams.getYamlFileSecuritySettings ()
                                        + " for entity "
                                        + targetETLEntity);
                        if (mode) {
                            Assert.fail (
                                    "Job: "
                                            + targetAccessor
                                            + " is not specified in yaml file "
                                            + testParams.getYamlFileSecuritySettings ()
                                            + " for entity "
                                            + targetETLEntity);
                        }
                    }
                } catch (ConnectionException e) {
                    log.debug ("Check Accessor Exists " + targetAccessor + " " + targetRegion, e);
                }
            }
        }
    }


    public static String getRecordForLookup(
            String targetEntity, String targetSFEntity, String region, RecordAccessStruct testParams) {

        String targetLookup;

        if (region != null) {
            targetLookup = targetEntity + "." + region;
        } else {
            targetLookup = targetEntity + "." + testParams.getUserRole ();
        }
        StringBuilder whereCondition = new StringBuilder ();

        Yaml yaml = new Yaml (new Constructor (RecordElements.class));
        Iterable<Object> records;
        try (InputStream inputStream = new FileInputStream (new File (testParams.getYamlFileRecord ()))) {
            records = yaml.loadAll (inputStream);

            log.debug ("Target lookup = " + targetLookup);
            for (Object object : records) {
                RecordElements elements = (RecordElements) object;

                if (targetLookup.toLowerCase ().trim ().equals (elements.getLookupName ().toLowerCase ().trim ())) {
                    log.debug ("Lookup found  = " + targetLookup);
                    List<RecordLookupWhereCondition> whereConditions = elements.getWhereConditions ();
                    for (Object obj : whereConditions) {

                        RecordLookupWhereCondition recordCondition = (RecordLookupWhereCondition) obj;
                        String condition = recordCondition.getCondition ();
                        if (!whereCondition.toString ().equals ("")) {
                            whereCondition.append (" and ").append (condition).append (" ");
                        } else {
                            whereCondition.append (" ").append (condition).append (" ");
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            log.error (e);
        }

        String soql = "select id from  " + targetSFEntity + " where " + whereCondition + " limit 1";
        soql = soql.replace ("$environment", System.getProperty ("target_salesforce_environment"));

        return soql;
    }

    @Step
    private static String getIdFromSoql(String soql, RecordAccessStruct testParams)
            throws ConnectionException {

        String recordId = null;
        QueryResult qr = testParams.getEnterpriseConnection ().query (soql);

        if (qr.getSize () > 0) {
            log.debug ("Found: a record from  " + soql);
            for (SObject obj : qr.getRecords ()) {

                // this is the record id we want to test against
                recordId = obj.getId ();
                log.debug ("record id: " + recordId);
            }
        } else {

            log.error (Const.ERR_LABEL + " No Id returned from soql= " + soql);
        }
        return recordId;
    }

    @Step
    public static String getUserId(EnterpriseConnection enterpriseConnection, String userName) {

        String userId = null;

        // userName = userName
        //   + "." + System.getProperty("target_salesforce_environment");
        String soQLQuery = " select id from user where userName = '" + userName + "'";
        log.debug ("SOQL Find User Query=" + soQLQuery);

        try {
            QueryResult qr = enterpriseConnection.query (soQLQuery);

            if (qr.getSize () > 0) {
                log.debug ("Found: a record");
                for (SObject obj : qr.getRecords ()) {
                    User user = (User) obj;

                    userId = user.getId ();
                    log.debug ("Found: a user " + userId);
                    return userId;
                }
            } else {
                log.error ("UserId  not found for" + soQLQuery);
            }

        } catch (ConnectionException e) {

            log.debug ("Returning user id " + userId);
            Assert.fail ("User does not exist " + userName);
        }

        return userId;
    }

    /**
     * @param mode Serenity Junit Assertions enabled
     */
    public static void doCompareExepectedAndActualSettingsForRecord(
            boolean mode,
            RecordAccessObjectDef jobAccessDef,
            String recordId,
            String accessorId,
            RecordAccessStruct testParams,
            String entityType)
            throws ConnectionException {

        String soql = getRecordAccessSoql (accessorId, recordId);
        log.debug ("SOQL for record access =" + soql);
        QueryResult qr = testParams.getEnterpriseConnection ().query (soql);

        if (!mode) {
            logToDebug (jobAccessDef, testParams, entityType, qr);
        } else {
            for (SObject obj : qr.getRecords ()) {

                UserRecordAccess salesforceAccessValueSet = (UserRecordAccess) obj;

                Assert.assertEquals (
                        Constants.ENTITY
                                + entityType
                                + Constants.SOQL
                                + soql
                                + Constants.ACCESSOR
                                + testParams.getAccessorUserName ()
                                + " "
                                + entityType
                                + ", MaxAccessLevel",
                        jobAccessDef.getMaxAccessLevel (),
                        salesforceAccessValueSet.getMaxAccessLevel ());

                Assert.assertEquals (
                        Constants.ENTITY
                                + entityType
                                + Constants.SOQL
                                + soql
                                + Constants.ACCESSOR
                                + testParams.getAccessorUserName ()
                                + " "
                                + entityType
                                + ", HasAllAccess",
                        jobAccessDef.isHasAllAccess (),
                        salesforceAccessValueSet.getHasAllAccess ());

                Assert.assertEquals (
                        Constants.ENTITY
                                + entityType
                                + Constants.SOQL
                                + soql
                                + Constants.ACCESSOR
                                + testParams.getAccessorUserName ()
                                + " "
                                + entityType
                                + ", HasDeleteAccess",
                        jobAccessDef.isHasDeleteAccess (),
                        salesforceAccessValueSet.getHasDeleteAccess ());

                Assert.assertEquals (
                        Constants.ENTITY
                                + entityType
                                + Constants.SOQL
                                + soql
                                + Constants.ACCESSOR
                                + testParams.getAccessorUserName ()
                                + " "
                                + "HasEditAccess",
                        jobAccessDef.isHasEditAccess (),
                        salesforceAccessValueSet.getHasEditAccess ());

                Assert.assertEquals (
                        Constants.ENTITY
                                + entityType
                                + Constants.SOQL
                                + soql
                                + Constants.ACCESSOR
                                + testParams.getAccessorUserName ()
                                + " "
                                + entityType
                                + ", HasReadAccess",
                        jobAccessDef.isHasReadAccess (),
                        salesforceAccessValueSet.getHasReadAccess ());

                Assert.assertEquals (
                        Constants.ENTITY
                                + entityType
                                + Constants.SOQL
                                + soql
                                + Constants.ACCESSOR
                                + testParams.getAccessorUserName ()
                                + " "
                                + entityType
                                + ", HasTransferAccess",
                        jobAccessDef.isHasTransferAccess (),
                        salesforceAccessValueSet.getHasTransferAccess ());
            }
        }
    }

    /**
     * @Step public static Iterable<Object> getRecordEntities(String yamlFile) throws FileNotFoundException {
     * <p>
     * Yaml yaml = new Yaml(new Constructor(RecordElements.class));
     * <p>
     * InputStream inputStream = new FileInputStream(new File(yamlFile));
     * <p>
     * return yaml.loadAll(inputStream);
     * <p>
     * <p>
     * }
     **/

    public static String getRecordAccessSoql(String accessorId, String recordId) {

        return "select RecordId, MaxAccessLevel, HasAllAccess, HasDeleteAccess, HasEditAccess, HasReadAccess, "
                + "HasTransferAccess FROM UserRecordAccess where UserId ='"
                + accessorId
                + "'"
                + " and RecordId IN ('"
                + recordId
                + "') ";
    }

    public static void logToDebug(
            RecordAccessObjectDef jobAccessDef,
            RecordAccessStruct testParams,
            String entityType,
            QueryResult qr) {

        if (qr.getSize () > 0) {
            log.debug ("UserAccessRecord Retrieved sucessfully output to debug log file");

            for (SObject obj : qr.getRecords ()) {

                log.debug (
                        "\r\n[Processing Entity: "
                                + entityType
                                + " Accessor Job: "
                                + jobAccessDef.getJob ()
                                + " Owned By: "
                                + testParams.getOwnerUserName ()
                                + "]\r\n\r\n");

                String message =
                        "Entity="
                                + entityType
                                + " Owner= "
                                + testParams.ownerUserName
                                + " Job Accessor User "
                                + testParams.getAccessorUserName ()
                                + " ";
                UserRecordAccess salesforceAccessValueSet = (UserRecordAccess) obj;

                if (!jobAccessDef
                        .getMaxAccessLevel ()
                        .equalsIgnoreCase (salesforceAccessValueSet.getMaxAccessLevel ())) {

                    log.error (
                            Constants.ERR_RECORD_ACCESS_SETTINGS
                                    + message
                                    + Constants.LEVEL
                                    + " MaxAccessLevel " + Constants.EXPECTED
                                    + jobAccessDef.getMaxAccessLevel ()
                                    + Constants.ACTUAL
                                    + salesforceAccessValueSet.getMaxAccessLevel ());
                }

                if (jobAccessDef.isHasAllAccess () != salesforceAccessValueSet.getHasAllAccess ()) {

                    log.error (
                            Constants.ERR_RECORD_ACCESS_SETTINGS
                                    + message
                                    + Constants.LEVEL
                                    + " HasAllAccess " + Constants.EXPECTED
                                    + jobAccessDef.isHasAllAccess ()
                                    + Constants.ACTUAL
                                    + salesforceAccessValueSet.getHasAllAccess ());
                }

                if (jobAccessDef.isHasDeleteAccess () != salesforceAccessValueSet.getHasDeleteAccess ()) {
                    log.error (
                            Constants.ERR_RECORD_ACCESS_SETTINGS
                                    + message
                                    + Constants.LEVEL
                                    + " HasDeleteAccess " + Constants.EXPECTED
                                    + jobAccessDef.isHasDeleteAccess ()
                                    + Constants.ACTUAL
                                    + salesforceAccessValueSet.getHasDeleteAccess ());
                }

                if (jobAccessDef.isHasReadAccess () != salesforceAccessValueSet.getHasReadAccess ()) {

                    log.error (
                            Constants.ERR_RECORD_ACCESS_SETTINGS
                                    + message
                                    + Constants.LEVEL
                                    + " HasReadAccess " + Constants.EXPECTED
                                    + jobAccessDef.isHasReadAccess ()
                                    + Constants.ACTUAL
                                    + salesforceAccessValueSet.getHasReadAccess ());
                }
                if (jobAccessDef.isHasTransferAccess () != salesforceAccessValueSet.getHasTransferAccess ()) {

                    log.error (
                            Constants.ERR_RECORD_ACCESS_SETTINGS
                                    + message
                                    + Constants.LEVEL
                                    + " HasTransferAccess " + Constants.EXPECTED
                                    + jobAccessDef.isHasTransferAccess ()
                                    + Constants.ACTUAL
                                    + salesforceAccessValueSet.getHasTransferAccess ());
                }
            }
        }
    }
}
