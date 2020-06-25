package com.test.cle.salesforce.serenity.stepdefinitions.sfpermissionsmanager;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.PermissionSetLicenseAssign;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.User;
import com.sforce.soap.enterprise.sobject.UserRole;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.stepdefinitions.common.BaseDefinition;
import com.test.cle.salesforce.serenity.stepdefinitions.common.SalesforceEnterprisePartnerConnections;
import com.test.cle.salesforce.serenity.steps.common.DBConnectionSteps;
import com.test.cle.salesforce.serenity.steps.sfpermissionsmanager.SalesforceJobSettingsSteps;
import com.test.cle.salesforce.testutils.Constants;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SalesforceJobSettingsDefs extends BaseDefinition {


   // static DBConnectionSteps dbConnectionSteps;

    @Steps
    static SalesforceJobSettingsSteps salesforceJobSettingsSteps;
    static User salesforceUserObject = null;
    EnterpriseConnection enterpriseConnection;
  //  Connection dbStagingConnection;
    // String userJob = null;
    String userName = null;
    String environment = null;

    @Given("^I have a connection to specified staging environment$")
    public void i_have_a_connection_to_specified_staging_environment() {

        environment = System.getProperty("target_salesforce_environment");

        Assert.assertNotNull(environment);
       // dbStagingConnection = dbConnectionSteps.getDBConnection(environment, Constants.STAGING_PROP);

      //  Assert.assertNotNull("Staging Database Connection", dbStagingConnection);
    }

    @Given("^I have a connection to a specified Salesforce Org as admin$")
    public void i_have_a_connection_to_a_specified_Salesforce_Org_as_admin() {

        enterpriseConnection = SalesforceEnterprisePartnerConnections.login();
        Assert.assertNotNull("Enterprise Connection to Salesforce", enterpriseConnection);
    }

    // Assume the refresh has already been triggered before the Automated Suite (Runner) is executed
    @When("^I execute a user refresh of that environment$")
    public void i_execute_a_user_refresh_of_that_environment() {
    }

    @Then("^I expect that the \"([^\"]*)\" settings for \"([^\"]*)\" to be updated$")
    public void i_expect_that_the_permissions_for_to_be_updated(String user, String userJobFunction) {

        userName = user + "." + environment;
        log.debug(userName + "User job added for example display display, to ensure none are ommitted " + userJobFunction);

    }

    @Then("^the users profile \"([^\"]*)\" is the same as the Salesforce Settings$")
    public void the_users_profile_is_the_same_as_the_Salesforce_Settings(String profileName) {

        String sfProfileName =
                salesforceJobSettingsSteps.getSalesforceProfileNameForUser(userName, enterpriseConnection);
        log.debug(userName + " Users name = " + userName);

        log.debug(userName + " Compare Profiles " + profileName + " vs " + sfProfileName);
        Assert.assertEquals(userName + " Profile Name Equals Check", profileName.trim(), sfProfileName);
    }

    @Then("^the users role \"([^\"]*)\" is the same as their Salesforce Settings$")
    public void the_users_role_is_the_same_as_their_Salesforce_Settings(String roleName) {

        Assert.assertEquals(
                roleName,
                salesforceJobSettingsSteps.getuserRoleNameInSFOrg(userName, enterpriseConnection));
    }

    @Then("^the users permissionsets \"([^\"]*)\" are the same as their Salesforce Settings$")
    public void the_users_permissionsets_are_the_same_as_their_Salesforce_Settings(String psList)
            throws ConnectionException {

        Assert.assertTrue(
                userName + " PermissionSetsOK Check?",
                salesforceJobSettingsSteps.arePermissionSetsOK(psList, userName, enterpriseConnection));




    }
    @Then("^that users who are indicated as requiring a license \"([^\"]*)\" have that license$")
    public void that_users_who_are_indicated_as_requiring_a_license_have_that_license(String licenseName) {

        if(!licenseName.isEmpty ()){
            boolean  result= salesforceJobSettingsSteps.isUserEAUser (userName, licenseName, enterpriseConnection);
            if(!result){
                Assert.fail (userName + " is not assigned PermissionSetLicense " + licenseName);
            }
        }

    }



    @When("^I execute a user refresh of that environment and check username \"([^\"]*)\"$")
    public void i_execute_a_user_refresh_of_that_environment_and_check_username(String user) {

        userName = user + "." + environment;
        salesforceUserObject = salesforceJobSettingsSteps.getUserObject(userName, enterpriseConnection);

        Assert.assertNotNull(userName + " Salesforce User Not Null?", salesforceUserObject);
        log.debug("The username found  = " + salesforceUserObject.getUsername());
    }

    @Then("^I expect alias \"([^\"]*)\"  firstname \"([^\"]*)\" lastname \"([^\"]*)\"  to match$")
    public void i_expect_alias_firstname_lastname_to_match(
            String alias, String firstName, String lastName) {

        Assert.assertEquals(
                userName + " Alias ok", alias.toLowerCase(), salesforceUserObject.getAlias().toLowerCase());

        Assert.assertEquals(
                userName + " FirstName Ok",
                firstName.toLowerCase(),
                salesforceUserObject.getFirstName().toLowerCase());

        Assert.assertEquals(
                userName + " LastName OK",
                lastName.toLowerCase(),
                salesforceUserObject.getLastName().toLowerCase());
    }

    @Then(
            "^I expect emailencodingkey \"([^\"]*)\" langualgelocalekey \"([^\"]*)\" timezonekey \""
                    + "([^\"]*)\" to match$")
    public void i_expect__emailencodingkey_langualgelocalekey_timezonekey_to_match(
            String emailencodingkey, String languagelocalekey, String timezonekey) {



        Assert.assertEquals(
                userName + " EmailEncoding OK?",
                emailencodingkey.toLowerCase(),
                salesforceUserObject.getEmailEncodingKey().toLowerCase());

        Assert.assertEquals(
                userName + " LanguageLocaleKey?",
                languagelocalekey.toLowerCase(),
                salesforceUserObject.getLanguageLocaleKey().toLowerCase());

        Assert.assertEquals(
                userName + " Timezonesidkey?",
                timezonekey.toLowerCase(),
                salesforceUserObject.getTimeZoneSidKey().toLowerCase());
    }

    @Then(
            "^I expect salesRegion_c \"([^\"]*)\" isactive \"([^\"]*)\" and localesidkey \"([^\"]*)\" to match$")
    public void i_expect_salesRegion_c_isactive_and_localesidkey_to_match(
            String salesRegion, String isactive, String localesidkey) {

        boolean active = false;
        /** if (Integer.valueOf(isactive.trim()) == 1) {
         active = true;
         }**/
        if (Integer.parseInt (isactive.trim()) == 1) {
            active = true;
        }
        log.debug(userName + "Output of IsActive = " + Boolean.parseBoolean(isactive));

        Assert.assertEquals(
                userName + " SalesRegion", salesRegion.toLowerCase(), salesRegion.toLowerCase());

        Assert.assertEquals(userName + "active", active, salesforceUserObject.getIsActive());
        Assert.assertEquals(
                userName + " localesidkey", localesidkey, salesforceUserObject.getLocaleSidKey());
    }

    @Then("^I expect department \"([^\"]*)\" to match$")
    public void i_expect_department_to_match(String dept) {

        Assert.assertEquals(userName + " department ok?", dept, salesforceUserObject.getDepartment());
    }

    @Then("^I expect the assignment of permissionsets \"([^\"]*)\"$")
    public void i_expect_the_assignment_of_permissionsets(String permissionSets)
            throws ConnectionException {

        permissionSets = permissionSets.trim();
        List permissionSetList = null;

        log.debug("permission sets expected = " + permissionSets + "len=" + permissionSets.length());
        if (!permissionSets.equals("")) {
            permissionSetList =
                    SalesforceJobSettingsSteps.getUsersPermissionSets(enterpriseConnection, userName);

            for (Object psName : permissionSetList) {
                log.debug("PermissionSet assigned to " + userName + "=" + psName);
            }

            String[] expectedPS = permissionSets.split(",");
            for (String expPs : expectedPS) {

                if (!permissionSetList.contains(expPs)) {
                    log.error(userName + Constants.USER_MISSING_PS + expPs);
                }
                Assert.assertTrue(
                        userName + " Verify PermissionSet? " + expPs + " is assigned ",
                        permissionSetList.contains(expPs));
            }
        } else {
            log.debug("No permissionset check required for " + userName);
        }
    }

    @Then("^I expect the assignment of groups \"([^\"]*)\" to match$")
    public void i_expect_the_assignment_of_groups_to_match(String groups) throws ConnectionException {

        if (!groups.equals("")) {
            String[] groupArr = groups.split(",");
            for (String expectedGroupMembership : groupArr) {
                List groupMembersList =
                        SalesforceJobSettingsSteps.getUserGroupMembers(
                                enterpriseConnection, expectedGroupMembership);

                for (Object userMember : groupMembersList) {
                    log.debug("Found group member " + userMember);
                }
                log.debug("checking for " + userName + "in list of group Members");
                if (!groupMembersList.contains(userName.toLowerCase())) {
                    log.error(userName + Constants.USER_MISSING_GROUP_MEMEBERSHIP + expectedGroupMembership);
                }
                Assert.assertTrue(
                        userName + " Expected Group Membership OK? " + expectedGroupMembership,
                        groupMembersList.contains(userName));
            }
        }
    }

    @Then("^I expect the defined user boolean properties \"([^\"]*)\" to be set to true$")
    public void i_expect_the_defined_user_boolean_properties_to_be_set_to_true(
            String userAttributeList)  {

        if (!userAttributeList.equals("")) {
            String[] userArr = userAttributeList.split(",");
            for (String userAttributeName : userArr) {

                Assert.assertTrue(
                        userName + " Is Expected Boolean  Attribute set?" + userAttributeName,
                        salesforceJobSettingsSteps.isAttributeSetForUser(
                                userName, userAttributeName, enterpriseConnection));
            }
        }
    }

  /**  @After
    public void closeConnections() throws SQLException {

        dbConnectionSteps.closeConnection(dbStagingConnection);
    }**/


}
