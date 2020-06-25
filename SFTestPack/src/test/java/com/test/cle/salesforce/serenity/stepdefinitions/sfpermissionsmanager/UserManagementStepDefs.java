package com.test.cle.salesforce.serenity.stepdefinitions.sfpermissionsmanager;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.test.cle.salesforce.serenity.steps.common.DBConnectionSteps;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.sfpermissionsmanager.SalesforceJobSettingsSteps;
import com.test.cle.salesforce.testutils.Constants;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.After;
import org.junit.Assert;

import java.sql.Connection;

public class UserManagementStepDefs {

    @Steps
    private static SalesforceConnectionSteps salesforceConnectionSteps = null;

    static String environment;
    EnterpriseConnection enterpriseConnection;
    Connection dbStagingConnection;

    // ToDo Implement the stepsdefs and steps for SFPermissionsManager Tests

    @Given(
            "^I have a connection to specified staging environment database identified by \"([^\"]*)\"$")
    public void i_have_a_connection_to_specified_staging_environment_database_identified_by(
            String dbSuffix) {
        environment = System.getProperty("target_salesforce_environment");

        // ToDo use database suffix and split the staging url property into 3 parts
        Assert.assertNotNull(environment);

        dbStagingConnection = DBConnectionSteps.getDBConnection(environment, Constants.STAGING_PROP);

        Assert.assertNotNull("Staging Database Connection check", dbStagingConnection);

    }

    @Given("^I have a salesforce connection to an org identified by target environment$")
    public void i_have_a_salesforce_connection_to_an_org_identified_by_target_environment() {


        enterpriseConnection = salesforceConnectionSteps.getSalesforceEnterpriseConnection(environment);

        Assert.assertNotNull(Constants.ENT_CON_CHECK, enterpriseConnection);
    }

    @When("^I request to disable users specified in \"([^\"]*)\"$")
    public void i_request_to_disable_users_specified_in(String targetUserEnvironment) {
    }

    @Then("^I expect that all users specified in the \"([^\"]*)\" tab will be disabled successfully$")
    public void i_expect_that_all_users_specified_in_the_tab_will_be_disabled_successfully(
            String excelDisableSheetTabName) {
    }

    @When("^I request to enable users specified in \"([^\"]*)\"$")
    public void i_request_to_enable_users_specified_in(String spreadsheetName) {
    }

    @Then("^I expect that all users specified in the \"([^\"]*)\" tab will be enabled successfully$")
    public void i_expect_that_all_users_specified_in_the_tab_will_be_enabled_successfully(
            String excelEnableSheetTabName) {
    }

    @When("^I request to remove security details for a particular environment \"([^\"]*)\"$")
    public void i_request_to_remove_security_details_for_a_particular_environment(
            String environmentName) {
    }

    @Then("^I expect them all records for that environment will be removed successfully$")
    public void i_expect_them_all_records_for_that_environment_will_be_removed_successfully() {
    }


    @After
    public void logout(){

        salesforceConnectionSteps.logoutEnterpriseConnection (enterpriseConnection);

    }

}
