package com.test.cle.salesforce.serenity.steps.sfsecurity;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.ObjectPermissions;
import com.sforce.soap.enterprise.sobject.PermissionSet;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.yamlelements.security.NameValue;
import com.test.cle.salesforce.yamlelements.security.PSDef;
import com.test.cle.salesforce.yamlelements.security.PSObjectDef;
import com.test.cle.salesforce.yamlelements.security.UncheckedObjectDef;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class VerifyPermissionSet {
    static Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());
    static EnterpriseConnection eConnection = null;
    static PSDef entity = null;

    public static PermissionSet getPermissionSet(String soql) throws ConnectionException {

        PermissionSet ps = null;


        QueryResult qr = eConnection.query (soql);
        if (qr.getSize () > 0) {

            for (SObject obj : qr.getRecords ()) {

                if (obj != null) {
                    ps = (PermissionSet) obj;

                    break;
                }
            }
        } else {
            Assert.fail ("NoSuchPermissionSet " + entity.getName ());
        }

        return ps;
    }

    public static PSDef getEntity(String yamlFile) {

        Yaml yaml = new Yaml (new Constructor (PSDef.class));

        try {
            try (InputStream inputStream = new FileInputStream (new File (yamlFile))) {

                entity = yaml.load (inputStream);
            }
        } catch (IOException e) {
            log.error (e);
        }

        return entity;
    }

    public static StringBuffer doVerifyEntity(EnterpriseConnection enterpriseConnection, String yamlFile)
            throws Exception {

        eConnection = enterpriseConnection;
        entity = getEntity (yamlFile);
        StringBuffer errors = new StringBuffer ();
        //Check entity access in PermissionSet
        List<PSObjectDef> objectTypes = entity.getObject ();
        if (objectTypes != null) {

            // read the expected values first then to the lookup on Salesforce for that type via SOQL
            for (Object attr : objectTypes) {

                PSObjectDef objPSYaml = (PSObjectDef) attr;
                String objectType = objPSYaml.getType ();

                String soql =
                        "select Id, SObjectType,  PermissionsRead, PermissionsEdit, PermissionsCreate, PermissionsDelete, "
                                + "PermissionsModifyAllRecords, PermissionsViewAllRecords "
                                + " from ObjectPermissions "
                                + " where (ParentId IN(select id from PermissionSet where name = "
                                + "'"
                                + entity.getName ()
                                + "'))"
                                + " and sObjectType = '"
                                + objectType
                                + "'";


                QueryResult qr = enterpriseConnection.query (soql);
                if (qr.getSize () > 0) {

                    for (SObject obj : qr.getRecords ()) {
                        ObjectPermissions objectPermissions = (ObjectPermissions) obj;
                        log.debug ("Verifying " + objectType + " on PermissionSet " + entity.getName ());

                        boolean expected = Boolean.valueOf (objPSYaml.getCreate ());
                        boolean actual = objectPermissions.getPermissionsCreate ();

                        if (expected != actual) {
                            errors.append (objectType + " 'Create',  Expected Value: " + expected + " Actual value: " + actual +Constants.CR );
                        }

                        expected = Boolean.valueOf (objPSYaml.getEdit ());
                        actual = objectPermissions.getPermissionsEdit ();

                        if (expected != actual) {
                            errors.append (Constants.PS + entity.getName () + " " + objectType + " 'Edit',  Expected Value: " + expected + " Actual value: " + actual +Constants.CR);
                        }

                        expected = Boolean.valueOf (objPSYaml.getDelete ());
                        actual = objectPermissions.getPermissionsDelete ();
                        if (expected != actual) {
                            errors.append (Constants.PS + entity.getName () + " " + objectType + " 'Delete',  Expected Value: " + expected + " Actual value: " + actual +Constants.CR);
                        }

                        expected = Boolean.valueOf (objPSYaml.getRead ());
                        actual = objectPermissions.getPermissionsRead ();
                        if (expected != actual) {
                            errors.append (Constants.PS + entity.getName () + " " + objectType + " 'Read',  Expected Value: " + expected + " Actual value: " + actual +Constants.CR);
                        }
                        expected = Boolean.valueOf (objPSYaml.getViewAll ());
                        actual = objectPermissions.getPermissionsViewAllRecords ();
                        if (expected != actual) {
                            errors.append (Constants.PS + entity.getName () + " " + objectType + " 'View All',  Expected Value: " + expected + " Actual value: " + actual+Constants.CR);
                        }

                        expected = Boolean.valueOf (objPSYaml.getModifyAll ());
                        actual = objectPermissions.getPermissionsModifyAllRecords ();
                        if (expected != actual) {
                            errors.append (Constants.PS + entity.getName () + " " + objectType + " 'Modify All',  Expected Value: " +
                                    expected + " Actual value: " + actual +Constants.CR );
                        }

                    }
                } else {

                    errors.append ("Test Subject " + Constants.PS + entity.getName ()+ " " + Constants.OBJ_NOT_MARKED_SF + objectType + Constants.CR);

                }
            }
        }
        return errors;
    }

    public static String getSoqlAllTypessMarkedOnPermissionSet(String psName) {

        return "select Id, SObjectType from objectPermissions where ParentId in (  select id  from PermissionSet  where "
                + "PermissionSet.Profile.Name= "
                + "'"
                + psName
                + "'"
                + "  and  PermissionSet.IsOwnedByProfile = true)";


    }

    @Step
    public static StringBuffer doVerifyUnCheckedObjectsOnPermissionSet(
            EnterpriseConnection enterpriseConnection, String yamlFile)
            throws ConnectionException {

        Yaml yaml = new Yaml (new Constructor (PSDef.class));
        StringBuffer failures = new StringBuffer ();
        try (InputStream inputStream = new FileInputStream (new File (yamlFile))) {

            entity = yaml.load (inputStream);
        } catch (IOException e) {
            log.error (e);
        }

        QueryResult qr =
                enterpriseConnection.query (getSoqlAllTypessMarkedOnPermissionSet (entity.getName ()));

        List<UncheckedObjectDef> uncheckedObjects = entity.getUnCheckedObject ();

        if (uncheckedObjects != null) {
            ArrayList<String> yamlumarkedItems = new ArrayList ();

            for (UncheckedObjectDef item : uncheckedObjects) {
                yamlumarkedItems.add (item.getName ());

            }
            // If an object type is marked in the profile, it should not be in the yaml list of unchecked
            // objects
            for (SObject obj : qr.getRecords ()) {

                ObjectPermissions objectPermissions = (ObjectPermissions) obj;
                String sobject = objectPermissions.getSobjectType ();
                boolean containsItem = yamlumarkedItems.contains (sobject);
                if (containsItem == true) {
                    failures.append (Constants.PS + entity.getName () + " Contains Unmarked Item" + entity.getName () + Constants.CR);
                }

            }
        }
        return failures;
    }

    @Step
    public static StringBuffer doVerifySystemPermissionSetSettings(
            EnterpriseConnection enterpriseConnection, String yamlFile)
            throws InvocationTargetException, IllegalAccessException, FileNotFoundException,
            ConnectionException {
        StringBuffer failures = new StringBuffer ();
        eConnection = enterpriseConnection;
        entity = getEntity (yamlFile);
        StringBuilder selectClause = new StringBuilder ();
        List<NameValue> systemPermissions = entity.getSystemPermissions ();
        if (systemPermissions != null) {
            for (Object attribute : systemPermissions) {

                NameValue obj = (NameValue) attribute;
                if (obj != null) {

                    String name = obj.getName ();
                    if (name != null) {
                        selectClause.append (obj.getName ()).append (",");
                    }
                }
            }
        }

        selectClause.append ("id, name, label");
        String soql =
                "select  "
                        + selectClause
                        + " from PermissionSet where name = "
                        + "'"
                        + entity.getName ()
                        + "'";
        log.debug ("SOQL = " + soql);
        PermissionSet permissionSet = getPermissionSet (soql);

        if (systemPermissions != null) {
            for (Object setting : systemPermissions) {
                NameValue obj = (NameValue) setting;
                Boolean expectedValue = obj.getValue ();
                String methodName = "get" + obj.getName ().trim ();
                String entityName = entity.getName ();
                if (methodName != null) {
                    Method m = null;
                    boolean actualValue = false;
                    try {
                        if (permissionSet != null) {
                            m = PermissionSet.class.getMethod (methodName);
                            m.setAccessible (true);
                            actualValue = (Boolean) m.invoke (permissionSet, null);
                            m.invoke (permissionSet, null);
                        }
                    } catch (NoSuchMethodException e) {
                        log.error (
                                entityName + Constants.METHOD_NOT_FOUND + ", for method name " + methodName);
                    }

                //    Assert.assertEquals (expectedValue, actualValue);
                    if (expectedValue != actualValue) {
                        failures.append (Constants.PS + entityName + ", '" + obj.getName () + "' Expected: " + expectedValue +
                                " Actual:" + actualValue + Constants.CR);
                    }
                }
            }
        }
        return failures;
    }


    @Step
    public static StringBuffer doVerifyUserPermissionSetSettings(
            EnterpriseConnection enterpriseConnection, String yamlFile)
            throws InvocationTargetException, IllegalAccessException, FileNotFoundException,
            ConnectionException {

        eConnection = enterpriseConnection;
        entity = getEntity (yamlFile);

        StringBuffer failures = new StringBuffer ();
        StringBuilder selectClause = new StringBuilder ();
        List<NameValue> userPermissions = entity.getUserPermissions ();
        for (Object attribute : userPermissions) {

            NameValue obj = (NameValue) attribute;
            if (obj != null) {

                String name = obj.getName ();
                if (name != null) {
                    selectClause.append (obj.getName ()).append (",");
                }
            }
        }

        selectClause.append ("id, name, label");
        String soql =
                "select  "
                        + selectClause
                        + " from PermissionSet where name = "
                        + "'"
                        + entity.getName ()
                        + "'";
        log.debug ("SOQL = " + soql);
        PermissionSet permissionSet = getPermissionSet (soql);


        for (Object setting : userPermissions) {
            NameValue obj = (NameValue) setting;
            boolean expectedValue = obj.getValue ();
            String methodName = "get" + obj.getName ().trim ();
            String entityName = entity.getName ();
            if (methodName != null) {
                Method m = null;
                boolean actualValue = false;
                try {
                    if (permissionSet != null) {
                        m = PermissionSet.class.getMethod (methodName);
                        m.setAccessible (true);
                        actualValue = (Boolean) m.invoke (permissionSet, null);
                        log.debug ("Actual value " + actualValue);
                        m.invoke (permissionSet, null);
                    }
                } catch (NoSuchMethodException e) {
                    log.error (
                            entityName + Constants.METHOD_NOT_FOUND + ", for method name " + methodName);
                }
                if (expectedValue != actualValue) {
                    failures.append (Constants.PS + entity.getName () + " " +
                            methodName + " Expected: " + expectedValue + " Actual: " + actualValue + Constants.CR);
                }
               // Assert.assertEquals (expectedValue, actualValue);
            }
        }
        return failures;

    }

    @Step
    public static void assertFailures(StringBuffer buff) {

        if(buff.length ()>0){

            Assert.assertTrue (buff.toString (),buff.length ()==0);
            buff.delete(0, buff.length());
        }
    }
}
