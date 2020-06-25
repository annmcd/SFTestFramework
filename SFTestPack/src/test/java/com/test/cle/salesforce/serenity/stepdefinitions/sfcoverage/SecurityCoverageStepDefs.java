package com.test.cle.salesforce.serenity.stepdefinitions.sfcoverage;


import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.partner.PartnerConnection;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.sfcoverage.SecurityCoverageSteps;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.testutils.LoadProperties;
import com.test.cle.salesforce.yamlelements.security.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.test.cle.salesforce.testutils.Constants.TARGET_ORG;


public class SecurityCoverageStepDefs {


    static EnterpriseConnection enterpriseConnection = null;
    @Steps
    private static SalesforceConnectionSteps salesforceConnectionSteps = null;
    private static MetadataConnection metadataConnection;
    private static String environment = System.getProperty (TARGET_ORG);
    Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());
    PartnerConnection partnerConnection = null;
    List<String> expectedProfileNames = new ArrayList ();
    List<String> salesforceProfiles = new ArrayList<> ();
    String yamlDirectoryPath = LoadProperties.getTestResourcesDirectory () + "/profiledefs";
    String entityErrMsg = "";
    String fieldErrMsg = "";
    String secruityEntityErrMsg="";
    String psTestedErrMsg = "";
    String roleErrMsg="";
    String currentProfile;
    String currentPermissionSet;
    List<String> expectedRoleList=new ArrayList<> ();
    List<String> salesforceRoles = new ArrayList<>();


    @Given("^I have a saleforce connection for security coverage$")
    public void i_have_a_saleforce_connection_for_security_coverage() {

        enterpriseConnection = salesforceConnectionSteps.getSalesforceEnterpriseConnection (environment);
        Assert.assertNotNull (Constants.ENT_CON_CHECK, enterpriseConnection);

        partnerConnection = salesforceConnectionSteps.getSalesforcePartnerConnection (environment);
        Assert.assertNotNull (Constants.PARTNER_CON_CHECK, partnerConnection);

        metadataConnection = SalesforceConnectionSteps.getMetadataConnection (enterpriseConnection);
        Assert.assertNotNull (Constants.METADATA_CON_CHECK, metadataConnection);
    }


    @When("^I provide a list of custom profiles$")
    public void i_provide_a_list_of_custom_profiles(List<String> profileNames) {

        expectedProfileNames = profileNames;
        SecurityCoverageSteps.setEnterpriseConnection (enterpriseConnection);
        salesforceProfiles = SecurityCoverageSteps.getProfileList ();

    }

    @Then("^I expect yaml files to have been created with tests for those profiles$")
    public void i_expect_yaml_files_to_have_been_created_with_tests_for_those_profiles() {

        //Is the declared profile in the feature file a custom profile?
        String errMsg = "";
        for (String declaredProfile : expectedProfileNames) {
            if (!salesforceProfiles.contains (declaredProfile)) {
                errMsg = "Profile: " + declaredProfile + " is not a custom salesforce profile";
                log.error (errMsg);
            }
        }

        //Does the feature file list contain all the custom profiles?
        for (String sfProfile : salesforceProfiles) {
            if (!expectedProfileNames.contains (sfProfile)) {
                errMsg = "Profile: " + sfProfile + " should be listed in the feature file, for this scenario, it is a Custom CLE Profile";
                log.error (errMsg);
            }
        }

        //does a yaml file exist for all custom cle profiles?


        //Does the feature file list contain all the custom profiles?
        for (String sfProfile : salesforceProfiles) {

            String name = sfProfile.replaceAll (" ", "");
            String fileName = yamlDirectoryPath + "/" + name + ".yaml";

            if (!LoadProperties.fileExists (fileName)) {
                errMsg = "File does not exist " + fileName;
                log.error (errMsg);
            }
        }

        if (!errMsg.isEmpty ()) {
            Assert.fail (errMsg);
        }


    }


    @When("^I provide a profile name \"([^\"]*)\"$")
    public void i_provide_a_profile_name(String targetProfile) {
        currentProfile = targetProfile;
    }

    @Then("^Entities that are marked as checked should be checked in salesforce, with profile security settings matching$")
    public void entities_that_are_marked_as_checked_should_be_checked_in_salesforce_with_profile_security_settings_matching() {


        List<String> yamlProfileEntities = new ArrayList<> ();

        String name = currentProfile.replaceAll (" ", "");

        String yamlFileName = yamlDirectoryPath + "/" + name + ".yaml";
        ProfileDef profile = SecurityCoverageSteps.getProfile (yamlFileName);
        List<ProfileObjectDef> profileObjects = profile.getObject ();
        //each profile
        for (ProfileObjectDef pdef : profileObjects) {
            yamlProfileEntities.add (pdef.getType ().toLowerCase ());
        }

        Map<String, String> salesforcMarkedEntities = SecurityCoverageSteps.getCustomEntitiesWithSecuritySetOnProfileORPS ("profile", currentProfile, true);

        for (Map.Entry<String, String> entry : salesforcMarkedEntities.entrySet ()) {
            String key = entry.getKey ();
            if (!yamlProfileEntities.contains (key)) {
                entityErrMsg = Constants.ERR_MISSING_PROFILE_ENTITY_TEST + currentProfile + Constants.ENTITY + key + " Value" + entry.getValue ();
                log.error (entityErrMsg);
            }
        }


    }

    @Then("^Fields that are marked as checked should not be indicated on the profile security settings$")
    public void fields_that_are_marked_as_checked_should_not_be_indicated_on_the_profile_security_settings() {


        List<String> yamlEntityList = new ArrayList<> ();
        List<String> yamlFieldList = new ArrayList<> ();
        String yamlFileName = yamlDirectoryPath + "/" + currentProfile.replaceAll (" ", "") + ".yaml";
        ProfileDef profile = SecurityCoverageSteps.getProfile (yamlFileName);

        List<ProfileFieldDef> profileYamlFields = profile.getField ();
        List<ProfileObjectDef> profileYamlEntities = profile.getObject ();

        //only check field security settings on entities defined in the yaml. i.e ones that we are interested in
        for (ProfileObjectDef objectdDef : profileYamlEntities) {
            yamlEntityList.add (objectdDef.getType ().toLowerCase ());
        }

        //each profile
        for (ProfileFieldDef fieldDef : profileYamlFields) {
            yamlFieldList.add (fieldDef.getType ().toLowerCase () + "." + fieldDef.getFieldName ().toLowerCase ());
        }
        Map<String, String> salesforceMarkedEntities = null;
        log.debug ("..Assesing field permissions for all custom fields which are on entities defined in this profile " +
                currentProfile + "\r\n please wait...");
        salesforceMarkedEntities = SecurityCoverageSteps.geFieldsWithSecuritySetOnProfile (partnerConnection, currentProfile, yamlEntityList);

        for (Map.Entry<String, String> entry : salesforceMarkedEntities.entrySet ()) {
            String key = entry.getKey ().toLowerCase (); //Entity.Field Combination"
            String value = entry.getValue ();  //permissions values
            //If salesforce entity.field name combination in the yaml file is contained in the salesforce org Entity Field combination
            if (!yamlFieldList.contains (key)) {
                log.debug (currentProfile + ", does not contain an entry for " + key);
                if (!key.isEmpty ()) {
                    String[] entityFieldSF = key.split ("\\.");
                    Map<String, String> salesforceEntityPermissions = SecurityCoverageSteps.getEntityProfileAccess (currentProfile, entityFieldSF[0]);
                    if (salesforceEntityPermissions.size () > 0) {
                        //  log.error (currentProfile + " Defined: " + key);;
                        String[] fieldReadEditValuev = value.split ("\\.");
                        if (fieldReadEditValuev[0].equalsIgnoreCase ("true") &&
                                salesforceEntityPermissions.get ("read").equalsIgnoreCase ("false")) {
                            fieldErrMsg = Constants.ERR_MISSING_PROFILE_FIELD_TEST + " Profile: " +
                                    currentProfile + " Entity.FieldName " + key + " Read Permission on Field = true, Read Permission on Entity=false";
                            log.error (fieldErrMsg);
                        }
                        if (fieldReadEditValuev[1].equalsIgnoreCase ("true") &&
                                salesforceEntityPermissions.get ("edit").equalsIgnoreCase ("false")) {
                            fieldErrMsg = Constants.ERR_MISSING_PROFILE_FIELD_TEST + " Profile: " +
                                    currentProfile + " Entity.FieldName " + key + " Edit Permission on Field = true, Edit Permission on Entity=false";
                            log.error (fieldErrMsg);
                        }
                    }
                }
            }
        }
    }


    @Then("^I expect issues to be flagged in the error log$")
    public void i_expect_issues_be_flagged_in_the_error_log() {
        if (!entityErrMsg.isEmpty ()) {
            Assert.fail (entityErrMsg);
        }

        if (!fieldErrMsg.isEmpty ()) {
            Assert.fail (fieldErrMsg);
        }

    }


    @Then("^Any permissions which are enabled on the profile but not on the test should be highlighted$")
    public void any_permissions_which_are_enabled_on_the_profile_but_not_on_the_test_should_be_highlighted() {


        String yamlFileName = yamlDirectoryPath + "/" + currentProfile.replaceAll (" ", "") + ".yaml";
        ProfileDef profileDef = SecurityCoverageSteps.getProfile (yamlFileName);

        Assert.assertNotNull (profileDef);

        SecurityCoverageSteps.getPermissionDifferences (profileDef, metadataConnection, currentProfile);


    }

    //Permission Sets


    /**
     * All custom permission sets should be in this folder
     */
    @Given("^I have a folder where permission set definitions are contained$")
    public void i_have_a_folder_where_permission_set_definitions_are_contained() {


        String yamlFileDir = LoadProperties.getTestResourcesDirectory () + "/permissionsetdefs";
        String[] yamlNames;
        File f = new File (yamlFileDir);
        yamlNames = f.list ();
        if(yamlNames !=null) {
            String[] yamlPSNames = yamlNames;
            List<String> lcList = new ArrayList<> ();

            for (String item : yamlPSNames) {
                String lcitem = item.toLowerCase ().replace (".yaml", "");
                lcList.add (lcitem);
            }
            SecurityCoverageSteps.setEnterpriseConnection (enterpriseConnection);
            List<String> salesforceList = SecurityCoverageSteps.getPermissionSets ();
            for (String sfItem : salesforceList) {
                if (!lcList.contains (sfItem.toLowerCase ())) {
                    psTestedErrMsg = "PermissionSet: not tested " + sfItem.toLowerCase ();
                    log.error (psTestedErrMsg);
                }
            }
        }
    }

    //PS Test
    @When("^I check them against a salesforce org$")
    public void i_check_them_against_a_salesforce_org() {

    }

    //PS Test
    @Then("^I expect the folder to contain all the permission sets$")
    public void i_expect_the_folder_to_contain_all_the_permission_sets() {

        if (!psTestedErrMsg.isEmpty ()) {
            Assert.fail (psTestedErrMsg);
        }
    }

    //PS Test
    @When("^I provide a permissionSet name \"([^\"]*)\"$")
    public void i_provide_a_permissionSet_name(String targetPs) {

        currentPermissionSet = targetPs;
    }


    //PS Test
    @Then("^Entities that are marked as checked, should be checked in salesforce, with permissionSet security settings matching$")
    public void entities_that_are_marked_as_checked_should_be_checked_in_salesforce_with_permissionSet_security_settings_matching() throws IOException{


        List<String> yamlPSEntities = new ArrayList<> ();

        String yamlFileName = LoadProperties.getTestResourcesDirectory () + "/permissionsetdefs/" + currentPermissionSet + ".yaml";
        boolean exists = LoadProperties.fileExists (yamlFileName);
        PSDef psDef=null;
        Assert.assertTrue (yamlFileName + " Exists? ", exists);
        InputStream inputStream = null;
        Yaml yaml = new Yaml (new Constructor (PSDef.class));
        try {
            inputStream = new FileInputStream (new File (yamlFileName));
             psDef = yaml.load (inputStream);
        } catch (IOException e) {
            log.error (e);
        }finally {
            if(inputStream !=null){
                inputStream.close ();
            }
        }


        List<PSObjectDef> psObjectDefs = psDef.getObject ();
        if (psObjectDefs != null) {
            //each permissionset
            for (PSObjectDef pdef : psObjectDefs) {
                yamlPSEntities.add (pdef.getType ().toLowerCase ());
            }
            Map<String, String> salesforcMarkedEntities =
                    SecurityCoverageSteps.getCustomEntitiesWithSecuritySetOnProfileORPS ("permissionset", currentPermissionSet, true);
            for (Map.Entry<String, String> entry : salesforcMarkedEntities.entrySet ()) {
                String key = entry.getKey ();
                if (!yamlPSEntities.contains (key)) {
                    entityErrMsg = "MISSING_PS_ENTITY_TEST: " +
                            currentPermissionSet + Constants.ENTITY + key + " Value" + entry.getValue ();
                    log.error (entityErrMsg);
                }
            }
        } else {
            log.error ("Warning Only: No Entity tests defined for PermissionSet: " + currentPermissionSet);
        }


        SecurityCoverageSteps.setEnterpriseConnection (enterpriseConnection);
        //PS Test
        Map<String, String> salesforcMarkedEntities = SecurityCoverageSteps.getCustomEntitiesWithSecuritySetOnProfileORPS ("permissionset", currentPermissionSet, true);

        for (Map.Entry<String, String> entry : salesforcMarkedEntities.entrySet ()) {
            String key = entry.getKey ();
            if (!yamlPSEntities.contains (key)) {
                secruityEntityErrMsg = Constants.ERR_MISSING_PERMISSIONSET_ENTITY_TEST + currentPermissionSet + Constants.ENTITY + key + " Value" + entry.getValue ();
                log.error (entityErrMsg);
            }
        }

    }


    //PS Test
    @Then("^System and User Permissions that are marked as checked, should not be indicated on the permissionSet security settings$")
    public void fields_that_are_marked_as_checked_should_not_be_indicated_on_the_permissionSet_security_settings() throws IOException{


        InputStream inputStream = null;
        String yamlFileName = LoadProperties.getTestResourcesDirectory () + "/permissionsetdefs/" + currentPermissionSet + ".yaml";
        Yaml yaml = new Yaml (new Constructor (PSDef.class));
        PSDef psDef=null;
        try {
            inputStream = new FileInputStream (new File (yamlFileName));
            psDef = yaml.load (inputStream);

        } catch (IOException e) {
            log.error (e);
        }finally {
            if(inputStream!=null){
                inputStream.close ();
            }
        }



        SecurityCoverageSteps.getPermissionDifferences (psDef, metadataConnection, currentPermissionSet);

        //system permissions will already have asserted and entity error messages will already be in the error log
        if (!secruityEntityErrMsg.isEmpty ()) {

            Assert.fail (secruityEntityErrMsg);
        }
    }


    //Roles
    @When("^I provide a list of Roles$")
    public void i_provide_a_list_of_Roles(List<String> expectedRoles) {
        expectedRoleList=expectedRoles;

        expectedRoleList= expectedRoleList.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }


    @Then("^I expect these to be the only Roles in the Role Hierarchy$")
    public void i_expect_these_to_be_the_only_Roles_in_the_Role_Hierarchy() {

        SecurityCoverageSteps.setEnterpriseConnection (enterpriseConnection);
        salesforceRoles =SecurityCoverageSteps. getUserRoleList();

        salesforceRoles= salesforceRoles.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        for(String expRole: expectedRoleList ){
            if(!salesforceRoles.contains (expRole)){
                roleErrMsg= "UNEXPECTED ROLE_IN_TEST: " +expRole;
              //  log.error ("Expected Role " + expRole);
            }
        }
    }

    @Then("^I expect all the hierarchy roles to be in this list of Roles$")
    public void i_expect_all_the_hierarchy_roles_to_be_in_this_list_of_Roles() {
        roleErrMsg="";

        for(String sfRole: salesforceRoles ){
            if(!expectedRoleList.contains (sfRole)){
                roleErrMsg= "UNEXPECTED ROLE_FOUND_IN_SALESFORCE: " +sfRole;
              //  log.error ("actual role " + sfRole);
                log.error (roleErrMsg);
            }
        }

        if(!roleErrMsg.isEmpty ()){
            Assert.fail (roleErrMsg);
        }
    }



    @After
    public void logout() {
        salesforceConnectionSteps.logoutEnterpriseConnection (enterpriseConnection);
    }


}
