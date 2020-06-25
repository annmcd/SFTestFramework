package com.test.cle.salesforce.serenity.stepdefinitions.sfsecurity;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.sfsecurity.VerifyPermissionSet;
import com.test.cle.salesforce.serenity.steps.sfsecurity.VerifyUserRole;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.testutils.LoadProperties;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;


public class UserRoleStepDefs {

    static String environment = System.getProperty ("target_salesforce_environment");
    static String yamlfile = null;
    StringBuffer failures=null;
    @Steps
    static SalesforceConnectionSteps salesforceConnectionSteps = null;

    EnterpriseConnection enterpriseConnection = null;

    Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());

    @Given("^I have a role yamlDefinition \"([^\"]*)\"$")
    public void i_have_a_role_yamlDefinition(String yamlfileName) {

        String path = LoadProperties.getTestResourcesDirectory ();

        yamlfile = path + File.separator + "roledef" + File.separator + yamlfileName;
    }

    @Given("^I am connected to a salesforce organisation$")
    public void i_am_connected_to_a_salesforce_organisation() {

        enterpriseConnection = salesforceConnectionSteps.getSalesforceEnterpriseConnection (environment);

        Assert.assertNotNull (Constants.ENT_CON_CHECK, enterpriseConnection);
        log.debug ("Connected to salesforce org " + environment);
    }

    @When("^I verify it against a salesforce organisation$")
    public void i_verify_it_against_a_salesforce_organisation()
            throws ConnectionException {

       failures = new StringBuffer ();
       failures = VerifyUserRole.doVerifyUserRole (enterpriseConnection, yamlfile);
    }

    @Then("^I expect the expected and actual values to be the same$")
    public void i_expect_the_expected_and_actual_values_to_be_the_same() {

        if(failures.length ()>0) log.error (failures.toString ());
        VerifyUserRole.assertFailures (failures);
    }


    @After
    public void logout(){

        salesforceConnectionSteps.logoutEnterpriseConnection (enterpriseConnection);

    }

}