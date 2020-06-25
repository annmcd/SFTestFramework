package com.test.cle.salesforce.serenity.steps.sfrecordaccess;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.UserRecordAccess;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.csv.CSVEntityDef;
import com.test.cle.salesforce.yamlelements.security.*;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AccessSettingsGeneratorSteps {

    final private static Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());
    static YamlFileGenProperties testProperties;

    /**
     * public static String getTargetRegion(String region, String etlEntity) {
     * <p>
     * String targetEntityRegion;
     * <p>
     * if (region != null) {
     * targetEntityRegion = etlEntity + "." + region;
     * } else {
     * targetEntityRegion = etlEntity;
     * }
     * <p>
     * return targetEntityRegion;
     * }
     **/

    /**  public static Iterable<Object> getCSVEntity(String yamlFile) throws FileNotFoundException {

     Yaml yaml = new Yaml (new Constructor (CSVEntityDef.class));

     InputStream inputStream = new FileInputStream (new File (yamlFile));

     return yaml.loadAll (inputStream);

     }**/

    /**
     * public static Iterable<Object> getDataEntity(String yamlFile) throws FileNotFoundException {
     * <p>
     * Yaml yaml = new Yaml (new Constructor (RecordElements.class));
     * <p>
     * InputStream inputStream = new FileInputStream (new File (yamlFile));
     * <p>
     * return yaml.loadAll (inputStream);
     * <p>
     * <p>
     * }
     **/


    @Step
    public static String generateYamlFileForOwner(YamlFileGenProperties featureTestProperties)
            throws FileNotFoundException {

        // return value is a file
        String generatedFile = featureTestProperties.getYamlGeneratedFile ();
        testProperties = featureTestProperties;

        String entityCSVYaml = featureTestProperties.getYamlFileCSVStructure ();

        Yaml yaml = new Yaml (new Constructor (CSVEntityDef.class));
        InputStream inputStream = new FileInputStream (new File (entityCSVYaml));
        //replaced with getEntity
        Iterable<Object> typeList = yaml.loadAll (inputStream);
        String targetLookup = getTargetLookup (featureTestProperties);
        boolean bFound = false;

        for (Object typeDef : typeList) {

            writeYaml ("type: " + testProperties.getType () + "\r\n");
            writeYaml ("etlEntity: " + testProperties.getEtlEntity () + "\r\n");
            writeYaml ("accessSettings:\r\n");

            // we have the type, and the owner, now with the data specification we can get record details
            // target is driven by what is in the feature file
            log.debug ("[Target Lookup=" + targetLookup);
            Yaml yamlData = new Yaml (new Constructor (RecordElements.class));
            InputStream inputStreamData = new FileInputStream (new File (featureTestProperties.getYamlFileRecords ()));
            Iterable<Object> recordElements = yamlData.loadAll (inputStreamData);

            for (Object obj : recordElements) {

                RecordElements record = (RecordElements) obj;
                String lookupName = record.getLookupName ();
                if (lookupName.equalsIgnoreCase (targetLookup)) {
                    bFound = true;
                    log.debug ("LookupName Found " + lookupName);
                    String whereCondition = getWhereCondition (record.getWhereConditions ());

                    String recordId =
                            getRecordId (
                                    featureTestProperties.getType (),
                                    whereCondition,
                                    featureTestProperties.getEnterpriseConnection ());
                    log.debug ("RecordId = " + recordId);
                    try {
                        // now get our accessor users
                        Yaml yamlJob = new Yaml (new Constructor (TestJobDef.class));
                        InputStream inputStreamJob = new FileInputStream (new File (featureTestProperties.getYamlTestAccountsFile ()));
                        TestJobDef jobsDef = yamlJob.load (inputStreamJob);
                        // TestJobDef jobsDef = getUsersEntity (featureTestProperties.getYamlTestAccountsFile ());
                        List<RecordUser> users = jobsDef.getJobs ();
                        for (Object userObj : users) {
                            RecordUser userDef = (RecordUser) userObj;
                            log.debug ("User found= " + userDef.getUser ());
                            String accessorUserId =
                                    RecordAccessSteps.getUserId (
                                            featureTestProperties.getEnterpriseConnection (),
                                            userDef.getUser ().trim ()
                                                    + "."
                                                    + System.getProperty ("target_salesforce_environment"));
                            doCompareRecordSettingsForAJob (
                                    featureTestProperties.getEnterpriseConnection (),
                                    recordId,
                                    accessorUserId,
                                    userDef.getUser ()); // + "." +
                        }
                    } catch (ConnectionException e) {
                        log.error (e);
                    }
                    writeYaml ("---\r\n");

                    break;
                }
            }
            if (bFound) {
                log.debug (targetLookup + " lookup was found");
                break;
            }
        }
        if (!bFound) {
            log.error (targetLookup + " lookup not found");
        }
        return generatedFile;
    }

    public static String getTargetLookup(YamlFileGenProperties properties) {

        String region = properties.getRegion ();
        String userRole = properties.getUserRole ();
        String entity = properties.getEtlEntity ();
        String targetLookup = null;
        if (region != null) {

            targetLookup = entity + "." + region;
        } else {
            targetLookup = entity + "." + userRole;
        }

        return targetLookup;
    }

    // public static void writeYaml(String type, String entity, String accessSettingsValue) throws
    // IOException {
    public static void writeYaml(String accessSettingsValue) {

        String s = testProperties.getYamlGeneratedFile ();
        Path yamlFile = Paths.get (s);
        log.debug ("Checking for existence of  = " + s);
        try {
            if (Files.exists (yamlFile)) {
                //BufferedWriter bw;
                try (FileWriter fw = new FileWriter (new File (s).getAbsoluteFile (), true)) {
                    try (BufferedWriter bw = new BufferedWriter (fw)) {
                        bw.write (accessSettingsValue);
                    }
                }

            } else {

                try (FileWriter fw = new FileWriter (s, true)) {
                    fw.write (accessSettingsValue); // appends the string to the file
                    log.debug ("Append to file done ");
                }

            }
        } catch (IOException e) {
            log.error (e);
        }
    }

    private static String getWhereCondition(List<RecordLookupWhereCondition> whereConditions) {

        StringBuilder whereConditionBuilder = new StringBuilder ();
        for (Object obj : whereConditions) {

            RecordLookupWhereCondition recordCondition = (RecordLookupWhereCondition) obj;
            String condition = recordCondition.getCondition ();
            if (!whereConditionBuilder.toString ().equals ("")) {
                whereConditionBuilder.append (" and ").append (condition).append (" ");
            } else {
                whereConditionBuilder.append (" ").append (condition).append (" ");
            }
        }
        String whereCondition = whereConditionBuilder.toString ();
        whereCondition =
                whereCondition.replace ("$environment", System.getProperty ("target_salesforce_environment"));

        return whereCondition;
    }

    public static String getRecordId(
            String sfEntity, String whereCondition, EnterpriseConnection enterpriseConnection) {

        String soQLQuery = "select id from " + sfEntity + " where " + whereCondition + " limit 1";
        log.debug (" soql = " + soQLQuery);
        String recordId = null;
        try {
            QueryResult qr = enterpriseConnection.query (soQLQuery);

            if (qr.getSize () > 0) {
                log.debug ("Found: a record");
                for (SObject obj : qr.getRecords ()) {
                    recordId = obj.getId ();
                }
            } else {
                log.error ("Record not found for query: " + soQLQuery);
            }

        } catch (ConnectionException e) {

            log.debug ("Exception on executing " + soQLQuery);
            Assert.fail ("No results found for query: " + soQLQuery);
        }

        return recordId;
    }

    /**
     * public static TestJobDef getUsersEntity(String yamlFile) throws FileNotFoundException {
     * <p>
     * Yaml yaml = new Yaml (new Constructor (TestJobDef.class));
     * <p>
     * InputStream inputStream = new FileInputStream (new File (yamlFile));
     * <p>
     * return yaml.load (inputStream);
     * <p>
     * }
     **/

    public static void doCompareRecordSettingsForAJob(
            EnterpriseConnection enterpriseConnection,
            String recordId,
            String accessorId,
            String userName)
            throws ConnectionException {

        String soql =
                "select RecordId, MaxAccessLevel, HasAllAccess, HasDeleteAccess, HasEditAccess, HasReadAccess, "
                        + "HasTransferAccess FROM UserRecordAccess where UserId ='"
                        + accessorId
                        + "'"
                        + " and RecordId IN ('"
                        + recordId
                        + "') Limit 1\r\n\r\n";

        log.debug ("Record Access soql=" + soql);
        QueryResult qr = enterpriseConnection.query (soql);

        if (qr.getSize () > 0) {
            log.debug ("record retrieved successfully");
            for (SObject obj : qr.getRecords ()) {

                UserRecordAccess salesforceAccessValueSet = (UserRecordAccess) obj;

                log.debug ("MaxAccessLevel " + salesforceAccessValueSet.getMaxAccessLevel ());

                log.debug ("HasAllAccess " + salesforceAccessValueSet.getHasAllAccess ());

                log.debug ("HasDeleteAccess " + salesforceAccessValueSet.getHasDeleteAccess ());

                log.debug ("HasEditAccess " + salesforceAccessValueSet.getHasEditAccess ());

                log.debug ("HasReadAccess" + salesforceAccessValueSet.getHasReadAccess ());

                log.debug ("HasTransferAccess" + salesforceAccessValueSet.getHasTransferAccess ());

                writeYaml (
                        "    - { job: "
                                + userName
                                + ", MaxAccessLevel: "
                                + salesforceAccessValueSet.getMaxAccessLevel ()
                                + ", HasAllAccess: "
                                + salesforceAccessValueSet.getHasAllAccess ()
                                + ", HasDeleteAccess: "
                                + salesforceAccessValueSet.getHasDeleteAccess ()
                                + ", HasEditAccess: "
                                + salesforceAccessValueSet.getHasEditAccess ()
                                + ", HasReadAccess: "
                                + salesforceAccessValueSet.getHasReadAccess ()
                                + ", HasTransferAccess: "
                                + salesforceAccessValueSet.getHasTransferAccess ()
                                + "}\r\n");
            }
        } else {

            log.error ("No Record found " + soql);
        }
    }


}
