package com.test.cle.salesforce.serenity.stepdefinitions.sfsecurity;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.sfsecurity.VerifyPermissionSet;
import com.test.cle.salesforce.serenity.steps.sfsecurity.VerifyProfile;
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
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PermissionSetsStepdefs {


    private static String environment = System.getProperty(Constants.TARGET_ORG);
    private static String yamlfile = null;
    private static String psName = null;
    @Steps
    private static SalesforceConnectionSteps salesforceConnectionSteps = null;
    private Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private EnterpriseConnection enterpriseConnection = null;
    private MetadataConnection metadataConnection = null;
    private StringBuffer entityErrors= null;
    private StringBuffer uncheckedErrors= null;
    private StringBuffer systemErrors=null;
    private StringBuffer userPermissionErrors=null;

    public PermissionSetsStepdefs() {
    }

    @Given(
            "^I have a permissionset yamlDefinition \"([^\"]*)\" for permissionSet \"([^\"]*)\" in the test resources "
                    + "folder$")
    public void i_have_a_permissionset_yamlDefinition_for_permissionSet_in_the_test_resources_folder(
            String yamlfileName, String permissionSetName) {

       String path= LoadProperties.getTestResourcesDirectory();

        yamlfile = path + File.separator + "permissionsetdefs" + File.separator + yamlfileName;

        psName = permissionSetName;
    }

    @Given("^I have a connection to a salesforce org$")
    public void i_have_a_connection_to_a_salesforce_organisation() {

        enterpriseConnection = salesforceConnectionSteps.getSalesforceEnterpriseConnection(environment);
        Assert.assertNotNull(Constants.ENT_CON_CHECK, enterpriseConnection);

        metadataConnection = SalesforceConnectionSteps.getMetadataConnection(enterpriseConnection);

        Assert.assertNotNull(Constants.METADATA_CON_CHECK, metadataConnection);

        log.debug("Connected to salesforce org " + environment);
    }

    @When("^I check expected settings versus actual$")
    public void i_check_expected_settings_versus_actual() throws Exception{
        entityErrors=new StringBuffer ();
        entityErrors= VerifyPermissionSet.doVerifyEntity(enterpriseConnection, yamlfile);
        if(entityErrors.length ()>0 )log.error (entityErrors);

        uncheckedErrors=new StringBuffer();
        uncheckedErrors = VerifyPermissionSet.doVerifyUnCheckedObjectsOnPermissionSet(enterpriseConnection, yamlfile);
        if(uncheckedErrors .length ()>0 )log.error (uncheckedErrors );

        systemErrors=new StringBuffer ();
        systemErrors= VerifyPermissionSet.doVerifySystemPermissionSetSettings(enterpriseConnection, yamlfile);
        if(systemErrors.length ()>0 )log.error (systemErrors);

        userPermissionErrors=new StringBuffer();
        userPermissionErrors=VerifyPermissionSet.doVerifyUserPermissionSetSettings(enterpriseConnection, yamlfile);
        if(userPermissionErrors.length ()>0 )log.error (userPermissionErrors);
    }



   // I evaluate all the results

    @Then("^Then they should both be equal$")
    public void then_they_should_both_be_equal()  {
        StringBuffer errors = new StringBuffer ();
        if(entityErrors.length ()>0) errors.append (entityErrors);
        if(uncheckedErrors.length ()>0) errors.append (uncheckedErrors);
        if(systemErrors.length ()>0)errors.append (systemErrors);
        if(userPermissionErrors.length ()>0)errors.append (userPermissionErrors);

       VerifyPermissionSet.assertFailures (errors);

    }

    @After
    public void logout() {

        salesforceConnectionSteps.logoutEnterpriseConnection (enterpriseConnection);

    }
}
