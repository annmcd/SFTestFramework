package com.test.cle.salesforce.serenity.stepdefinitions.sfsecurity;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.sfsecurity.VerifyProfile;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.testutils.LoadProperties;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;

public class ProfileStepdefs {

    static String yamlfile = null;
    @Steps
    static SalesforceConnectionSteps salesforceConnectionSteps = null;
    private static String environment = System.getProperty ("target_salesforce_environment");
    Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());
    EnterpriseConnection enterpriseConnection = null;
    StringBuffer entityFailures = null;
    StringBuffer uncheckedFailures = null;
    StringBuffer fieldFailures = null;
    StringBuffer adminFailures = null;
    StringBuffer generalUserFailures = null;

    @Given(
            "^I have a profile yamlDefinition \"([^\"]*)\" for profile \"([^\"]*)\" in the test resources folder$")
    public void i_have_a_profile_yamlDefinition_for_profile_in_the_test_resources_folder(
            String yamlfileName, String profile) {

        log.debug ("Testing profile " + profile);

        String path = LoadProperties.getTestResourcesDirectory ();
        yamlfile = path + File.separator + "profileDefs" + File.separator + yamlfileName;
    }

    @Given("^I have a connection to a salesforce organisation$")
    public void i_have_a_connection_to_a_salesforce_organisation() {

        enterpriseConnection = salesforceConnectionSteps.getSalesforceEnterpriseConnection (environment);

        Assert.assertNotNull (Constants.ENT_CON_CHECK, enterpriseConnection);
        log.debug ("Connected to salesforce org " + environment);
    }

    @When("^I check expected security settings for object level$")
    public void i_check_expected_security_settings_for_object_level()
            throws PendingException,
            ConnectionException {

        //    VerifyProfile.dumpProfile(enterpriseConnection, yamlfile);
        entityFailures = new StringBuffer ();
        entityFailures = VerifyProfile.doVerifyEntityObjectLevel (enterpriseConnection, yamlfile);

        if (entityFailures.length () > 0) {
            log.error (entityFailures.toString ());
        }

        uncheckedFailures = new StringBuffer ();
        uncheckedFailures = VerifyProfile.doVerifyUnCheckedObjectsOnProfile (enterpriseConnection, yamlfile);
        if (uncheckedFailures.length () > 0) {
            log.error (uncheckedFailures.toString ());
        }
    }

    @When("^I check expected security settings at field level$")
    public void i_check_expected_security_settings_at_field_level()
            throws ConnectionException {
        fieldFailures = new StringBuffer ();
        fieldFailures = VerifyProfile.doVerifyEntityFieldLevel (enterpriseConnection, yamlfile);
        if (fieldFailures.length () > 0) {
            log.error (fieldFailures.toString ());
        }
    }

    @When("^I check expected security settings at administrative level$")
    public void i_check_expected_security_settings_at_administrative_level()
            throws ConnectionException, InvocationTargetException,
            PendingException, IllegalAccessException, NoSuchMethodException {
        adminFailures = new StringBuffer ();
        adminFailures = VerifyProfile.doVerifyAdministrativePermissions (enterpriseConnection, yamlfile);
        if (adminFailures.length () > 0)
            log.error (adminFailures.toString ());
    }

    @When("^I check expected security settings at general user level$")
    public void i_check_expected_security_settings_at_general_user_level()
            throws ConnectionException, InvocationTargetException,
            PendingException, IllegalAccessException, NoSuchMethodException {
        generalUserFailures = new StringBuffer ();
        generalUserFailures = VerifyProfile.doVerifyGeneralUserPermissions (enterpriseConnection, yamlfile);
        if (generalUserFailures.length () > 0) {
            log.error (generalUserFailures.toString ());
        }

    }


    @When("^I evaluate the results")
    public void i_evaluate_the_results() {

        StringBuffer errors = new StringBuffer ();
        if (entityFailures.length () > 0) errors.append (entityFailures);
        if (fieldFailures.length () > 0) errors.append (fieldFailures);
        if (uncheckedFailures.length () > 0) errors.append (uncheckedFailures);
        if (adminFailures.length () > 0) errors.append (adminFailures);
        if (generalUserFailures.length () > 0) errors.append (generalUserFailures);

        VerifyProfile.assertFailures (errors);
    }

    @Then("^Then they should both match the expected values$")
    public void then_they_should_both_match_the_expected_values() {


    }

    @After
    public void logout() {
        salesforceConnectionSteps.logoutEnterpriseConnection (enterpriseConnection);

    }
}
