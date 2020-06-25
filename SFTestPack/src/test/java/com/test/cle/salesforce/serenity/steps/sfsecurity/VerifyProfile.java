package com.test.cle.salesforce.serenity.steps.sfsecurity;


import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.FieldPermissions;
import com.sforce.soap.enterprise.sobject.ObjectPermissions;
import com.sforce.soap.enterprise.sobject.Profile;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.yamlelements.security.*;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import java.io.*;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class VerifyProfile {
    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private static ProfileDef getProfileDef(String yamlFile) {

        Yaml yaml = new Yaml(new Constructor(ProfileDef.class));
        ProfileDef profileDef = null;
        try (InputStream inputStream = new FileInputStream(new File(yamlFile))) {

            profileDef = yaml.load(inputStream);
        } catch (IOException e) {
            log.error("Serialisation error",e);
        }
        return profileDef ;
    }



    // Assertions
    @Step
    public static StringBuffer doVerifyEntityObjectLevel(
            EnterpriseConnection enterpriseConnection, String yamlFile)
            throws  ConnectionException {

        ProfileDef entity = getProfileDef(yamlFile);
        List<ProfileObjectDef> objectLevel = entity.getObject();
        StringBuffer failures=new StringBuffer();
        String profileName = entity.getName ();
        if (objectLevel != null) {
            // read the expected values first then to the lookup on Salesforce for that type via SOQL
            for (Object attr : objectLevel) {

                ProfileObjectDef objPSYaml = (ProfileObjectDef) attr;

                String objectType = objPSYaml.getType();

                String soql =
                        " select Id, SObjectType,  PermissionsRead, PermissionsCreate, PermissionsEdit, PermissionsDelete,"
                                + " PermissionsModifyAllRecords, PermissionsViewAllRecords from ObjectPermissions "
                                + " where SObjectType = "
                                + "'"
                                + objectType
                                + "'  and ParentId in ( "
                                + " select id  from PermissionSet  where PermissionSet.Profile.Name= "
                                + "'"
                                + profileName
                                + "'"
                                + "  and PermissionSet.IsOwnedByProfile = true) order by SystemModStamp desc Limit 1";

                log.debug(objectType + " "+ Constants.SOQL + soql);

                QueryResult qr = enterpriseConnection.query(soql);

                // iterate over the results here
                if (qr.getSize() > 0) {

                    for (SObject obj : qr.getRecords()) {

                        ObjectPermissions objectPermissions = (ObjectPermissions) obj;

                        boolean expected = Boolean.valueOf (objPSYaml.getCreate ());
                        boolean actual=objectPermissions.getPermissionsCreate();
                       if(expected!=actual){
                            failures.append (Constants.PROFILE + entity.getName() + ", Entity: " + objectType +", 'Create Access' Expected: " +expected + " Actual: "+
                                    actual + Constants.CR);
                        }


                         expected = Boolean.valueOf (objPSYaml.getRead ());
                         actual=objectPermissions.getPermissionsRead();
                        if(expected!=actual){
                            failures.append (Constants.PROFILE + entity.getName() + ", Entity: " + objectType +", 'Read Access' Expected: " + expected + " Actual: "+
                                    actual+ Constants.CR);
                        }


                         expected = Boolean.valueOf (objPSYaml.getDelete ());
                         actual=objectPermissions.getPermissionsDelete();
                        if(expected!=actual){
                            failures.append (Constants.PROFILE + entity.getName() + ", Entity: " + objectType +",  'Delete Access' Expected: " + expected + " Actual: "+
                                    actual+ Constants.CR);
                        }

                        expected = Boolean.valueOf (objPSYaml.getEdit ());
                        actual=objectPermissions.getPermissionsEdit();
                        if(expected!=actual){
                            failures.append (Constants.PROFILE + entity.getName() + ", Entity: " + objectType +", 'Edit Access' Expected: " +  expected+ " Actual: "+
                                   actual+ Constants.CR);
                        }

                        expected = Boolean.valueOf (objPSYaml.getViewAll ());
                        actual=objectPermissions.getPermissionsViewAllRecords ();

                        if(expected!=actual){
                            failures.append (Constants.PROFILE + entity.getName() + ", Entity: " + objectType +",  'View All Access' Expected: " + expected +
                                    " Actual: "+actual+ Constants.CR);
                        }

                        expected = Boolean.valueOf (objPSYaml.getModifyAll ());
                        actual=objectPermissions.getPermissionsModifyAllRecords ();
                        if(expected!=actual){
                            failures.append (Constants.PROFILE + entity.getName() + ", Entity: " + objectType +",  'Modify All Access'  Expected: " +  expected +
                                    " Actual: "+ actual+ Constants.CR);
                        }
                    }

                } else {

                   failures.append("Test Subject profile: " + profileName + " " + Constants.OBJ_NOT_MARKED_SF + objectType + Constants.CR);
                }
            }
        } else {
            log.error("WARNING " + Constants.OBJ_PERMISSIONS_MISSING + entity.getName());
        }
        return failures;
    }


    @Step
    public static StringBuffer doVerifyEntityFieldLevel(
            EnterpriseConnection enterpriseConnection, String yamlFile)
            throws ConnectionException {

        ProfileDef entity = getProfileDef(yamlFile);
        String profileName = entity.getName ();
        List<ProfileFieldDef> fieldLevel = entity.getField();
        StringBuffer failures = new StringBuffer();
        if (fieldLevel != null) {
            // read the expected values first then to the lookup on Salesforce for that type via SOQL
            for (Object attr : fieldLevel) {

                ProfileFieldDef objPSYaml = (ProfileFieldDef) attr;

                String objectType = objPSYaml.getType();

                String soql =
                        " select Id, Field, SObjectType,  PermissionsRead, PermissionsEdit from FieldPermissions "
                                + " where SObjectType = "
                                + "'"
                                + objectType
                                + "'  and ParentId in ( "
                                + " select id  from PermissionSet  where PermissionSet.Profile.Name= "
                                + "'"
                                + profileName
                                + "'"
                                + "  and PermissionSet.IsOwnedByProfile = true) and Field ="
                                + "'"
                                + objectType
                                + "."
                                + objPSYaml.getFieldName()
                                + "'";

                QueryResult qr = enterpriseConnection.query(soql);
                if (qr.getSize() > 0) {

                    for (SObject obj : qr.getRecords()) {

                        FieldPermissions fieldPermissions = (FieldPermissions) obj;

                       boolean expected =  Boolean.valueOf(objPSYaml.getRead());
                       boolean actual =  fieldPermissions.getPermissionsRead();
                       if(expected!=actual){
                           failures.append (profileName + ", Entity: " + objectType + " Expected Read Access: " + expected + " Actual Read Access: " +
                                   actual + Constants.CR);
                        }

                        expected =  Boolean.valueOf(objPSYaml.getEdit());
                        actual =  fieldPermissions.getPermissionsEdit();

                        if(expected!=actual){
                                failures.append (Constants.PROFILE + profileName+ ", Entity: " +objectType + " Expected Edit Access: " + expected +
                                        " Actual Edit Access: " +
                                    actual + Constants.CR);
                        }
                    }
                } else {
                  failures.append(
                          "Test Subject: "+ profileName +
                                     " SECURITY_ON_FIELD_NOT_INDICATED_FOR "  +objPSYaml.getFieldName()
                                    + " "
                                    + "Entity = "
                                    + objectType
                                    + Constants.CR);
                }
            }
        } else {
            failures.append("Test Subject Profile: " +profileName +   " "+ Constants.FIELD_PERMISSIONS_MISSING + entity.getName()+ Constants.CR);
        }
        return failures;
    }

    @Step
    public static StringBuffer doVerifyAdministrativePermissions(
            EnterpriseConnection enterpriseConnection, String yamlFile)
            throws  ConnectionException, NoSuchMethodException,
            IllegalAccessException, java.lang.reflect.InvocationTargetException {


        ProfileDef entity = getProfileDef(yamlFile);
        List<NameValue> adminPermissions = entity.getAdministrative();
        StringBuffer failures = new StringBuffer ();
        if(adminPermissions!=null) {
            String selectClause = "";
            StringBuffer buff = new StringBuffer ();


            for (Object attribute : adminPermissions) {

                NameValue obj = (NameValue) attribute;
                buff.append (obj.getName ());
                buff.append (",");
                ;

            }
            buff.append ("Id");
            selectClause = buff.toString ();
            String soql =
                    "select  " + selectClause + " from Profile where name = " + "'" + entity.getName () + "'";

            log.debug ("SOQL Profile Query = " + soql);
            QueryResult qr = enterpriseConnection.query (soql);
            Profile profile = null;
            if (qr.getSize () > 0) {

                for (SObject obj : qr.getRecords ()) {
                    profile = (Profile) obj;
                    break;
                }
            }


            for (Object adminPermission : adminPermissions) {
                NameValue yamlNameValue = (NameValue) adminPermission;
                String methodName = "get" + yamlNameValue.getName ();
                Method m = Profile.class.getMethod (methodName);
                m.setAccessible (true);

                Boolean result = (Boolean) m.invoke (profile, null);
                boolean expected =yamlNameValue.getValue();
                boolean actual = result;
                if(expected!=actual) {
                    failures.append (profile.getName () +  ", " + methodName+ " Expected: " + expected + " Actual: " + actual + Constants.CR);
                }
            }
        } else{
            failures.append ("WARNING: NO_ADMINISTRATIVE_PERMISSIONS Subject:" + entity.getName());
            }

    return failures;

    }

    public static String getAllTypessMarkedOnProfile(String profileName) {

        return "select Id, SObjectType from objectPermissions where ParentId in (  select id  from PermissionSet  where "
                + "PermissionSet.Profile.Name= "
                + "'"
                + profileName
                + "'"
                + "  and  PermissionSet.IsOwnedByProfile = true)";
    }

    @Step
    public static StringBuffer doVerifyGeneralUserPermissions(
            EnterpriseConnection enterpriseConnection, String yamlFile)
            throws  ConnectionException, NoSuchMethodException,
            IllegalAccessException, java.lang.reflect.InvocationTargetException {

        ProfileDef entity = getProfileDef(yamlFile);
        StringBuffer failures = new StringBuffer ();
        List<NameValue> generalUserPermissions = entity.getGeneralUser();
        String selectClause = "";
        if (generalUserPermissions != null) {
            StringBuffer buff = new StringBuffer ();
            for (Object attribute : generalUserPermissions) {
                NameValue obj = (NameValue) attribute;
                buff.append (obj.getName ());
                buff.append(",");

            }
            buff.append ("Id");

            selectClause=buff.toString ();
            String soql =
                    "select  " + selectClause + " from Profile where name = " + "'" + entity.getName() + "'";


            QueryResult qr = enterpriseConnection.query(soql);
            Profile profile = null;
            if (qr.getSize() > 0) {
                for (SObject obj : qr.getRecords()) {
                    profile = (Profile) obj;
                    break;

                }
            }
            for (Object generalUserPermission : generalUserPermissions) {
                NameValue yamlNameValue = (NameValue) generalUserPermission;
                String methodName = "get" + yamlNameValue.getName();
                Method m = Profile.class.getMethod(methodName);
                m.setAccessible(true);

                Boolean result = (Boolean) m.invoke(profile, null);
                boolean expected=yamlNameValue.getValue ();
                boolean actual = result;
                if(expected!=actual) {
                    failures.append (profile.getName () + ", " + methodName +
                            " Expected: " + expected + " Actual " + actual + Constants.CR);
                }
            }
        } else {
           failures.append ("WARNING: NO_GENERAL_USER_PERMISSIONS Subject:" + entity.getName() + Constants.CR);
        }
        return failures;
    }

    @Step
    public static StringBuffer doVerifyUnCheckedObjectsOnProfile(
            EnterpriseConnection enterpriseConnection, String yamlFile)
            throws ConnectionException {

        StringBuffer failures=new StringBuffer();
        ProfileDef entity = getProfileDef(yamlFile);
        QueryResult qr = enterpriseConnection.query(getAllTypessMarkedOnProfile(entity.getName()));
        List<UncheckedObjectDef> uncheckedObjects = entity.getUnCheckedObject();
        ArrayList<String> yamlumarkedItems = new ArrayList();

        for (UncheckedObjectDef item : uncheckedObjects) {
            yamlumarkedItems.add(item.getName());
        }
        // If an object type is marked in the profile, it should not be in the yaml list of unchecked
        // objects
        for (SObject obj : qr.getRecords()) {

            ObjectPermissions objectPermissions = (ObjectPermissions) obj;
            String sobject = objectPermissions.getSobjectType();
            boolean containsItem = yamlumarkedItems.contains(sobject);

            if (containsItem) {
                  failures.append (entity.getName() + " Unchecked Object is also a checked object " + sobject + Constants.CR);
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
