package com.test.cle.salesforce.serenity.steps.sfcoverage;

import com.sforce.soap.enterprise.DescribeGlobalSObjectResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.*;
import com.sforce.soap.enterprise.sobject.PermissionSet;
import com.sforce.soap.metadata.*;
import com.sforce.soap.partner.Field;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.steps.common.SObjectFields;
import com.test.cle.salesforce.yamlelements.security.NameValue;
import com.test.cle.salesforce.yamlelements.security.PSDef;
import com.test.cle.salesforce.yamlelements.security.ProfileDef;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SecurityCoverageSteps {

    static Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());

    public static void setEnterpriseConnection(EnterpriseConnection enterpriseConnection) {
        SecurityCoverageSteps.enterpriseConnection = enterpriseConnection;
    }

    static EnterpriseConnection enterpriseConnection;

    public static List<String> getProfileList() {

        List<String> profiles = new ArrayList<> ();

        String soql =
                "SELECT Id, Profile.Name FROM PermissionSet whERE IsOwnedByProfile = true AND IsCustom = true AND profile.name NOT IN('No Access','Event Registrations Profile', 'GDPR Profile')";

        QueryResult qr = null;
        try {
            qr = enterpriseConnection.query (soql);
        } catch (ConnectionException e) {
            log.error (e);
        }
        PermissionSet ps;
        if (qr.getSize () > 0) {

            for (SObject obj : qr.getRecords ()) {
                ps = (PermissionSet) obj;
                profiles.add (ps.getProfile ().getName ());
            }
        }
        return profiles;
    }

    public static List<String> getUserRoleList() {

        List<String> userRoles = new ArrayList<> ();

        String soql =
                "SELECT Id, name from userRole";

        QueryResult qr = null;
        try {
            qr = enterpriseConnection.query (soql);
        } catch (ConnectionException e) {
            log.error (e);
        }
        UserRole userRole;
        if (qr.getSize () > 0) {

            for (SObject obj : qr.getRecords ()) {
                userRole = (UserRole) obj;
                userRoles.add (userRole.getName ());
            }
        }
        return userRoles;
    }




    public static List<String> getCustomEntities() {

        List customEntities = new ArrayList ();
        DescribeGlobalSObjectResult[] result = new DescribeGlobalSObjectResult[0];
        try {

            result = enterpriseConnection.describeGlobal ().getSobjects ();
        } catch (ConnectionException e) {
            log.error (e);
        }
        for (DescribeGlobalSObjectResult sObject : result) {

            if (sObject.getName ().endsWith ("__c")) {
                customEntities.add (sObject.getName ().toLowerCase ());
            }
        }

        return customEntities;
    }


    public static Map<String, String> geFieldsWithSecuritySetOnProfile(PartnerConnection pConn, String profileName, List<String> entitiesThatMatter) {

        String permissionHeader = " PermissionsRead, PermissionsEdit from FieldPermissions ";
        Map<String, String> fieldPermissions = new HashMap<> ();
        com.sforce.soap.partner.DescribeGlobalSObjectResult[] result = new com.sforce.soap.partner.DescribeGlobalSObjectResult[0];

        try {
            result = pConn.describeGlobal ().getSobjects ();
        } catch (ConnectionException e) {
            log.error (e);
        }

        for (com.sforce.soap.partner.DescribeGlobalSObjectResult sObject : result) {
            String entityName = sObject.getName ();
            if (entitiesThatMatter.contains (entityName.toLowerCase ())) {
                SObjectFields.setConnection (pConn);
                Field[] entityFields = SObjectFields.getSalesforceFieldInfo (entityName);
                for (Field field : entityFields) {
                    if (field.isCustom ()) {
                        String soql =
                                " select Id, Field, SObjectType, " + permissionHeader + " where SObjectType = "
                                        + "'"
                                        + entityName
                                        + "'  and ParentId in ( "
                                        + " select id  from PermissionSet  where PermissionSet.Profile.Name= "
                                        + "'"
                                        + profileName
                                        + "'"
                                        + "  and PermissionSet.IsOwnedByProfile = true) and Field ="
                                        + "'"
                                        + entityName + "." + field.getName ()
                                        + "'";
                        QueryResult qr = null;
                        try {
                            qr = enterpriseConnection.query (soql);
                        } catch (ConnectionException e) {
                            log.error (e);
                        }
                        StringBuffer buff = new StringBuffer ();
                        // log.error (soql);
                        if (qr.getSize () > 0) {
                            for (SObject obj : qr.getRecords ()) {
                                FieldPermissions permissions = (FieldPermissions) obj;
                                buff.append (permissions.getPermissionsRead ());
                                buff.append (".").append (permissions.getPermissionsEdit ());
                                // log.error("Adding to map " + permissions.getField ().toLowerCase ());
                                fieldPermissions.put (permissions.getField ().toLowerCase (), buff.toString ()); //values for read and edit on the field
                            }
                        }
                    }
                }
            }
        }
        return fieldPermissions;
    }


    //To Do modify to send in a flag to show read and edit or all security settings
    @Step
    public static Map<String, String> getEntityProfileAccess(String profileName, String entityName) {

        String permissionHeader = " PermissionsRead, PermissionsCreate, PermissionsEdit, PermissionsDelete, PermissionsModifyAllRecords, PermissionsViewAllRecords";
        Map<String, String> entityPermissions = new HashMap<> ();

        String soql =
                " select Id, SObjectType, " + permissionHeader + " from ObjectPermissions "
                        + " where SObjectType = "
                        + "'"
                        + entityName
                        + "'  and ParentId in ( "
                        + " select id  from PermissionSet  where PermissionSet.Profile.Name= "
                        + "'"
                        + profileName
                        + "'"
                        + "  and PermissionSet.IsOwnedByProfile = true) order by SystemModStamp desc Limit 1";

        QueryResult qr = null;
        try {
            qr = enterpriseConnection.query (soql);
        } catch (ConnectionException e) {
            log.error (e);
        }

        if (qr.getSize () > 0) {

            for (SObject obj : qr.getRecords ()) {
                if (obj != null) {
                    ObjectPermissions permissions = (ObjectPermissions) obj;
                    entityPermissions.put ("read", String.valueOf (permissions.getPermissionsRead ()));
                    entityPermissions.put ("edit", String.valueOf (permissions.getPermissionsEdit ()));
                }
            }
        }

        return entityPermissions;
    }


    //Get all the entities in the org and check the security settings for it on this profile/permissionset
    @Step
    public static Map<String, String> getCustomEntitiesWithSecuritySetOnProfileORPS(String type, String typeName, boolean allPermissions) {

        String selectStmt = "";
        String andCondition="";
        if (type.equalsIgnoreCase ("profile")) {
            selectStmt = " select id  from PermissionSet where PermissionSet.Profile.Name= ";
            andCondition= " and PermissionSet.IsOwnedByProfile = true) order by SystemModStamp desc Limit 1";
        } else {
            selectStmt = " select id from permissionset where name="; //assume permissionset
            andCondition= " ) order by SystemModStamp desc Limit 1";
        }



        String permissionHeader = " PermissionsRead, PermissionsCreate, PermissionsEdit, PermissionsDelete, PermissionsModifyAllRecords, PermissionsViewAllRecords";

        Map<String, String> customEntities = new HashMap<> ();
        DescribeGlobalSObjectResult[] result = new DescribeGlobalSObjectResult[0];

        try {
            result = enterpriseConnection.describeGlobal ().getSobjects ();
        } catch (ConnectionException e) {
            log.error (e);
        }

        for (DescribeGlobalSObjectResult sObject : result) {
            if (sObject.getName ().endsWith ("__c")) {
                String soql =
                        " select Id, SObjectType, " + permissionHeader + " from ObjectPermissions "
                                + " where SObjectType = "
                                + "'"
                                + sObject.getName ()
                                + "'  and ParentId in ( "
                                +   selectStmt
                                + "'"
                                + typeName //permissionset or profile name
                                + "'"
                                + andCondition;

                QueryResult qr = null;

                try {
                    qr = enterpriseConnection.query (soql);
                } catch (ConnectionException e) {
                    log.error (e);
                }
                StringBuffer buff = new StringBuffer ();
                if (qr.getSize () > 0) {

                    for (SObject obj : qr.getRecords ()) {
                        ObjectPermissions permissions = (ObjectPermissions) obj;
                        if (allPermissions) {
                            buff.append (" ").append (permissions.getPermissionsRead ());
                            buff.append (" ").append (permissions.getPermissionsCreate ());
                            buff.append (" ").append (permissions.getPermissionsEdit ());
                            buff.append (" ").append (permissions.getPermissionsDelete ());
                            buff.append (" ").append (permissions.getPermissionsModifyAllRecords ());
                            buff.append (" ").append (permissions.getPermissionsViewAllRecords ());
                            customEntities.put (sObject.getName ().toLowerCase (), permissionHeader + "=" + buff.toString ());
                        } else {
                            buff.append (" ").append (permissions.getPermissionsRead ());
                            buff.append (" ").append (permissions.getPermissionsEdit ());
                            customEntities.put (sObject.getName ().toLowerCase (), buff.toString ());
                        }
                    }

                }

            }
        }
        return customEntities;
    }


   /** public static List<String> getCustomAttributesForEntity(String entityName) {

        List<String> cutomAttributes = new ArrayList<> ();


        String soql = "Select EntityDefinition.QualifiedApiName, QualifiedApiName," +
                " DataType, Description FROM FieldDefinition  WHERE EntityDefinition.QualifiedApiName " +
                "IN(" + "'" + entityName + "')  and QualifiedApiName like '%__c'";


        QueryResult qr = null;
        try {
            qr = enterpriseConnection.query (soql);
        } catch (ConnectionException e) {
            log.error (e);
        }

        if (qr.getSize () > 0) {

            for (SObject obj : qr.getRecords ()) {

                FieldDefinition fieldDefinition = (FieldDefinition) obj;
                String attributeName = fieldDefinition.getQualifiedApiName ();
                cutomAttributes.add (attributeName);
            }
        }
        return cutomAttributes;

    }**/



    public static ProfileDef getProfile(String yamlFileName) {
        Yaml yaml = new Yaml (new Constructor (ProfileDef.class));
        ProfileDef profileDef = null;
        try (
                InputStream inputStream = new FileInputStream (new File (yamlFileName))) {
            profileDef = yaml.load (inputStream);
            if (profileDef.getName () == null) {
                log.error ("Non Serialisable file " + yamlFileName);

            }
        } catch (
                IOException e) {
            log.error (e);
        }
        return profileDef;
    }


    @Step
    public static void getPermissionDifferences(Object obj, MetadataConnection metadataConnection, String testSubjectName){


        String errMsg="";
        String testSubject; //Either profile or permission set
        ProfileDef profileDef;
        PSDef psDef;
        List<String> permissionsNames = new ArrayList ();

        if(obj instanceof ProfileDef){
            testSubject= "Profile";
            profileDef = (ProfileDef)obj;
            List<NameValue> adminPermissions=profileDef.getAdministrative ();
          for(NameValue ps: adminPermissions){
              permissionsNames.add (ps.getName ().toLowerCase ());
          }
            List<NameValue> userPermissions=profileDef.getGeneralUser ();
            for(NameValue ps: userPermissions){
                permissionsNames.add (ps.getName ().toLowerCase ());
            }

        }else{
            testSubject="PermissionSet";
            psDef = (PSDef)obj;
            List<NameValue> adminPermissions=psDef.getSystemPermissions ();
            for(NameValue ps: adminPermissions){
                permissionsNames.add (ps.getName ().toLowerCase ());
            }
            List<NameValue> userPermissions= psDef.getUserPermissions ();
            for(NameValue ps: userPermissions){
                permissionsNames.add (ps.getName ().toLowerCase ());
            }
        }

        String[] profileORPermissionSets = new String[]{testSubjectName};
        try {
            ReadResult metaDataResult = metadataConnection.readMetadata (testSubject, profileORPermissionSets);

            for (Metadata md : metaDataResult.getRecords ()) {
                if (md != null) {
                    if(testSubject.equalsIgnoreCase ("profile")) {
                        com.sforce.soap.metadata.Profile profile = (com.sforce.soap.metadata.Profile) md;
                        ProfileUserPermission[] permissions = profile.getUserPermissions ();
                        for (ProfileUserPermission permission : permissions) {
                            String salesforcePermissionName = permission.getName ().toLowerCase ();
                            boolean enabled = permission.getEnabled ();

                            if (enabled) {

                                if (!permissionsNames.contains ("permissions" + salesforcePermissionName)) {
                                    errMsg = testSubject + " " + testSubjectName + " MISSING_PERMISSIONS_TEST " + "Permissions" + permission.getName ();
                                    log.error (errMsg);
                                }
                            }
                        }
                    }else{
                        com.sforce.soap.metadata.PermissionSet permissionSet = (com.sforce.soap.metadata.PermissionSet) md;
                        PermissionSetUserPermission[] permissions = permissionSet.getUserPermissions ();
                        for (PermissionSetUserPermission permission : permissions) {
                            String salesforcePermissionName = permission.getName ().toLowerCase ();
                            boolean enabled = permission.getEnabled ();

                            if (enabled) {

                                if (!permissionsNames.contains ("permissions" + salesforcePermissionName)) {
                                    errMsg = testSubject + " " + testSubjectName + " MISSING_PERMISSIONS_TEST " + "Permissions" + permission.getName ();
                                    log.error (errMsg);
                                }
                            }
                        }
                    }
                }
            }
            if(!errMsg.isEmpty ()){
                Assert.fail (errMsg);
            }
        } catch (ConnectionException e) {
            log.error ( e);
        }


    }

    public static List<String> getPermissionSets() {

        List<String> permissionSetList = new ArrayList<> ();

       String soql = "select id, name from permissionset where isCustom = true and isOwnedByProfile = false";
        QueryResult qr = null;
        try {
            qr = enterpriseConnection.query (soql);
        } catch (ConnectionException e) {
            log.error (e);
        }

        if (qr.getSize () > 0) {

            for (SObject obj : qr.getRecords ()) {
                PermissionSet permissionSet = (PermissionSet) obj;
                String attributeName = permissionSet.getName ();
                permissionSetList.add (attributeName);
            }
        }
        return permissionSetList;
    }


}
