package com.test.cle.salesforce.serenity.steps.sfpermissionsmanager;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.*;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.steps.sfetl.DBStructureSteps;
import com.test.cle.salesforce.testutils.Constants;
import net.thucydides.core.annotations.Step;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SalesforceJobSettingsSteps {

    static Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());

    public static List getPSListFromPSA(EnterpriseConnection enterpriseConnection, List<String> pSAs)
            throws ConnectionException {

        List<String> psIds = new ArrayList ();
        for (String psaId : pSAs) {
            String soQlQuery =
                    "select name from PermissionSet where Id in (SELECT permissionSetId  FROM "
                            + "PermissionSetAssignment WHERE id = "
                            + "'"
                            + psaId
                            + "')";
            log.debug ("SoQL query for the PermissionSetName =  " + soQlQuery);
            QueryResult qr = enterpriseConnection.query (soQlQuery);
            if (qr.getSize () > 0) {
                log.debug ("Salesforce Permission Sets for User size =  " + qr.getSize ());
                for (SObject obj : qr.getRecords ()) {

                    PermissionSet ps = (PermissionSet) obj;
                    log.debug ("PermissionSetAssignment ID:" + ps.getId ());
                    psIds.add (ps.getName ());
                }
            } else {

                log.debug ("Debug: failed to find results of " + soQlQuery);
            }
        }

        log.debug ("The number of permission sets added to the list = " + psIds.size ());

        return psIds;
    }

    public static List getPSAListOnSalesForceUser(
            EnterpriseConnection enterpriseConnection, String soQlQuery) {

        List<String> pSas = new ArrayList ();
        try {
            QueryResult qr = enterpriseConnection.query (soQlQuery);
            //	if (list.length != qr.getSize()) { return false; }
            if (qr.getSize () > 0) {
                log.debug ("Salesforce Permission Sets for User size =  " + qr.getSize ());
                for (SObject obj : qr.getRecords ()) {

                    PermissionSetAssignment psa = (PermissionSetAssignment) obj;
                    log.debug ("PermissionSetAssignment ID:" + psa.getId ());
                    pSas.add (psa.getId ());
                }
            } else {

                log.debug ("Debug: failed to find results" + soQlQuery);
            }
        } catch (ConnectionException e) {

            log.error (Constants.STEP_FAILED_IND, e);
        }
        return pSas;
    }

    public static List getUsersPermissionSets(EnterpriseConnection enterpriseConnection, String user)
            throws ConnectionException {
        String soQlQuery =
                "SELECT id FROM PermissionSetAssignment WHERE Assignee.username ='"
                        + user
                        + "' AND "
                        + "PermissionSetId IN (SELECT Id  FROM PermissionSet "
                        + "WHERE IsOwnedByProfile =false)";
        List salesForceUserPSAList = getPSAListOnSalesForceUser (enterpriseConnection, soQlQuery);

        return getPSListFromPSA (enterpriseConnection, salesForceUserPSAList);


    }

    @Step
    public static List getUserGroupMembers(EnterpriseConnection enterpriseConnection, String userName)
            throws ConnectionException {
        List<String> members = new ArrayList ();
        String soQlQuery =
                "SELECT userName, id FROM User WHERE Id IN (SELECT UserorGroupId FROM GroupMember WHERE Group.Name ="
                        + "'"
                        + userName
                        + "') ";
        QueryResult qr = enterpriseConnection.query (soQlQuery);
        if (qr.getSize () > 0) {
            log.debug (" Salesforce user group members size =  " + qr.getSize ());
            for (SObject obj : qr.getRecords ()) {

                User user = (User) obj;
                log.debug ("User member of group returned" + user.getId ());
                members.add (user.getUsername ());
            }
        } else {

            log.error (Constants.SOQL_NO_RESULTSSET + soQlQuery);
        }
        return members;
    }

    @Step
    public static ArrayList<String> getGroupGroupMembers(
            EnterpriseConnection enterpriseConnection, String queueName) throws ConnectionException {
        ArrayList<String> members = new ArrayList ();
        String soQlQuery =
                "SELECT name, id FROM Group WHERE Id IN (SELECT UserorGroupId FROM GroupMember WHERE Group.Name ="
                        + "'"
                        + queueName
                        + "') ";
        QueryResult qr = enterpriseConnection.query (soQlQuery);
        if (qr.getSize () > 0) {
            log.debug ("Found: Salesforce group members size =  " + qr.getSize ());
            for (SObject obj : qr.getRecords ()) {

                Group group = (Group) obj;
                log.debug ("group member found:" + group.getId ());
                members.add (group.getName ());
            }
        } else {

            log.error (Constants.SOQL_NO_RESULTSSET + soQlQuery);
        }
        return members;
    }

    @Step
    public String getStagingDBProfileIdForJob(
            String userJob, String environment, Connection dbStagingConnection) throws SQLException {

        String stagingProfileId = null;

        // Get the staging profile Id
        String queryProfileStaging =
                "SELECT distinct profileId  FROM profile as p LEFT JOIN Job as j ON p"
                        + ".ProfileName = j"
                        + ".ProfileName "
                        + "where j.JobName= "
                        + "'"
                        + userJob
                        + "'"
                        + " and j.EnvironmentName = "
                        + "'"
                        + environment
                        + "'";
        log.debug ("The staging profile Query " + queryProfileStaging);

        // We have that profile details
        try (ResultSet rs = DBStructureSteps.getResultSetFromQuery (queryProfileStaging, dbStagingConnection)) {
            if (rs.next ()) {
                stagingProfileId = rs.getString ("ProfileId");
            }

        }

        return stagingProfileId;
    }

    @Step
    public String getSalesforceProfileIdforUser(
            String userName, EnterpriseConnection enterpriseConnection) {

        String salesforceProfileId = null;
        String soQLQuery = "select profileId from user where userName = " + "'" + userName + "'";
        log.debug ("Debug: Salesforce Query " + soQLQuery);

        try {
            QueryResult qr = enterpriseConnection.query (soQLQuery);

            if (qr.getSize () > 0) {

                for (SObject obj : qr.getRecords ()) {
                    User user = (User) obj;
                    salesforceProfileId = user.getProfileId ();
                    log.debug ("Debug: Salesforce ProfileId = " + salesforceProfileId);
                }
            } else {
                log.error (Constants.PR_NOT_FOUND + soQLQuery);

            }
        } catch (ConnectionException e) {

            log.error (Constants.STEP_FAILED_IND, e);
        }

        return salesforceProfileId;
    }

    @Step
    public String getSalesforceProfileNameFromId(
            String profileId, EnterpriseConnection enterpriseConnection) {

        String soQLQuery = "select name from profile where id = " + "'" + profileId + "'";
        log.debug ("Debug: Salesforce Query on ProfileId=" + soQLQuery);
        String profileName = null;
        try {
            QueryResult qr = enterpriseConnection.query (soQLQuery);

            if (qr.getSize () > 0) {
                log.debug ("Found: ");
                for (SObject obj : qr.getRecords ()) {
                    Profile profile = (Profile) obj;

                    profileName = profile.getName ();

                    log.debug ("Debug: Salesforce ProfileName = " + profileName);
                }
            } else {

                log.debug ("Debug: Profile not found for " + soQLQuery);
            }
        } catch (ConnectionException e) {

            log.error (Constants.STEP_FAILED_IND, e);
        }

        return profileName;
    }

    @Step
    public String getSalesforceProfileNameForUser(
            String userName, EnterpriseConnection enterpriseConnection) {

        String soQLQuery =
                "Select name from profile where Id in (select profileId from user where username = '"
                        + userName
                        + "')";
        log.debug ("Find user role query=" + soQLQuery);

        String profileName = null;
        try {
            QueryResult qr = enterpriseConnection.query (soQLQuery);

            if (qr.getSize () > 0) {
                log.debug ("Found: ");
                for (SObject obj : qr.getRecords ()) {
                    Profile profile = (Profile) obj;
                    profileName = profile.getName ();
                    log.debug ("Debug: Salesforce ProfileName = " + profileName);
                }
            } else {

                log.debug ("Debug: Profile not found for " + soQLQuery);
            }
        } catch (ConnectionException e) {

            log.error (Constants.STEP_FAILED_IND, e);
        }

        return profileName;
    }

    @Step
    public boolean isAttributeSetForUser(
            String userName, String attrName, EnterpriseConnection enterpriseConnection) {

        String soQLQuery = "Select " + attrName + ", id from user where username = '" + userName + "'";
        log.debug ("Find user boolean attribute query=" + soQLQuery);

        boolean result = false;
        try {
            QueryResult qr = enterpriseConnection.query (soQLQuery);

            if (qr.getSize () > 0) {
                log.debug ("Found: user" + userName);
                for (SObject obj : qr.getRecords ()) {

                    User user = (User) obj;

                    String userAttributeName = "get" + attrName;
                    Method m = User.class.getMethod (userAttributeName);
                    m.setAccessible (true);
                    result = (Boolean) m.invoke (user, null);
                }
            } else {

                log.debug ("User not found for " + soQLQuery);
            }
        } catch (ConnectionException | NoSuchMethodException e) {

            log.error (Constants.STEP_FAILED_IND, e);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error (e);
        }

        return result;
    }

    public String getuserRoleNameInSFOrg(String userName, EnterpriseConnection enterpriseConnection) {

        String soQLQuery =
                "select name, id from UserRole where id in (select userRoleId from user where userName ="
                        + " "
                        + "'"
                        + userName
                        + "')";
        log.debug ("User Role Query " + soQLQuery);
        String userRoleName = null;
        try {
            QueryResult qr = enterpriseConnection.query (soQLQuery);

            if (qr.getSize () > 0) {
                log.debug ("Found: ");
                for (SObject obj : qr.getRecords ()) {
                    UserRole userRole = (UserRole) obj;
                    userRoleName = userRole.getName ();
                    log.debug ("Debug: Salesforce UserRoleName for " + userName + "= " + userRoleName);
                }
            } else {

                log.debug ("Debug: Failed to find results" + soQLQuery);
            }
        } catch (ConnectionException e) {

            log.error (Constants.STEP_FAILED_IND, e);
        }
        return userRoleName;
    }

    public boolean arePermissionSetsOK(
            String permissionsList, String user, EnterpriseConnection enterpriseConnection)
            throws ConnectionException {

        // get the list of permissionSetAssignments tied to that user
        String soQlQuery =
                "SELECT id FROM PermissionSetAssignment WHERE Assignee.username ='"
                        + user
                        + "' AND "
                        + "PermissionSetId IN (SELECT Id  FROM PermissionSet "
                        + "WHERE IsOwnedByProfile =false)";

        String[] psArray = permissionsList.split (",");
        List<String> listPSProvided = Arrays.asList (psArray);
        listPSProvided = listPSProvided.stream ().map (String::trim).collect (Collectors.toList ());
        log.debug ("The number of permission sets provided in test case = " + listPSProvided.size ());

        log.debug ("Compare with number of PSAs in Sf from query = " + soQlQuery);
        List salesForceUserPSAList = getPSAListOnSalesForceUser (enterpriseConnection, soQlQuery);

        List salesforceUserPermissionSets =
                getPSListFromPSA (enterpriseConnection, salesForceUserPSAList);
        salesforceUserPermissionSets =
                listPSProvided.stream ().map (String::trim).collect (Collectors.toList ());

        log.debug (
                "Compare the bdd list against salesforce list "
                        + Arrays.toString (listPSProvided.toArray ())
                        + " vs "
                        + Arrays.toString (salesforceUserPermissionSets.toArray ()));

        boolean result =
                CollectionUtils.isEqualCollection (listPSProvided, salesforceUserPermissionSets);
        log.debug ("The comparision returned " + result);

        return result;
    }

    @Step
    public User getUserObject(String userName, EnterpriseConnection enterpriseConnection) {

        String soQlQuery =
                "select id, isActive, username, firstname, lastname, alias, email, languagelocalekey, timezonesidkey, "
                        + "emailencodingkey, salesregion__c, localesidkey, department  from user where userName = "
                        + "'"
                        + userName
                        + "'";

        log.debug ("User search query " + soQlQuery);
        User user = null;
        try {
            QueryResult qr = enterpriseConnection.query (soQlQuery);

            if (qr.getSize () > 0) {

                for (SObject obj : qr.getRecords ()) {

                    user = (User) obj;
                    log.debug ("User Found:" + user.getId ());
                    // if (user != null) {
                    return user;
                    // }
                }
            } else {
                log.debug ("Debug: User not found" + soQlQuery);
            }
        } catch (ConnectionException e) {

            log.error (Constants.STEP_FAILED_IND, e);
        }

        return user;
    }


    public boolean isUserEAUser(String userName, String licenseName, EnterpriseConnection enterpriseConnection) {

        boolean found = false;

        //does username have a permissionsetLicense for EA Assigned.
        String soql = "select username, id from user where id in (SELECT AssigneeId" +
                " from PermissionSetLicenseAssign" +
                " where permissionSetLicense.MasterLabel= '" + licenseName + "')";
    
        QueryResult qr = null;
        try {
            qr = enterpriseConnection.query (soql);
        } catch (ConnectionException e) {
            log.error (e);
        }
        if (qr != null) {
            if (qr.getSize () > 0) {
                for (SObject obj : qr.getRecords ()) {
                    User user = (User) obj;
                    String licUser = user.getUsername ();
                    if (userName.equalsIgnoreCase (licUser)) {
                        log.debug ("Debug: Salesforce UserRoleName for " + userName + "= " + userName);
                        found = true;
                        break;
                    }
                }
            }
        }


        return found;
    }
}
