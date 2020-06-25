package com.test.cle.salesforce.serenity.stepdefinitions.sfentityconfig;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.stepdefinitions.common.BaseDefinition;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.common.TranslationValidator;
import com.test.cle.salesforce.testutils.Constants;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import java.io.File;
import java.lang.invoke.MethodHandles;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VerifyObjectMetadata extends BaseDefinition {

    static String environment = System.getProperty("target_salesforce_environment");
    static String entityName = null;
    static String entityYamlFile = null;

   /** @Steps
    static SalesforceConnectionSteps salesforceConnectionSteps = null;

    PartnerConnection partnerConnection = null;
    MetadataConnection metadataConnection = null;
    EnterpriseConnection eConnection=null;

    Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Given("^I  have a connection to salesforce Org in a given environment$")
    public void i_have_a_connection_to_salesforce_Org_in_a_given_environment() {


        eConnection =
                salesforceConnectionSteps.getSalesforceEnterpriseConnection(environment);
        Assert.assertNotNull(Constants.ENT_CON_CHECK,eConnection);

        metadataConnection = SalesforceConnectionSteps.getMetadataConnection(eConnection);
        Assert.assertNotNull(Constants.METADATA_CON_CHECK,eConnection);

        log.debug("Connected to salesforce org " + environment);

        partnerConnection =
                salesforceConnectionSteps.getSalesforcePartnerConnection (environment);
        Assert.assertNotNull(Constants.PARTNER_CON_CHECK,eConnection);
    }

    @Given("^I have pre defined the object configuration in \"([^\"]*)\"  for entity \"([^\"]*)\"$")
    public void i_have_pre_defined_the_object_configuration_in_for_entity(
            String fileName, String name) {

        entityName = name;
        Path resourceDirectory = Paths.get("src", "test", "resources");
        String path = resourceDirectory.toAbsolutePath().toString();
        entityYamlFile = path + File.separator + "entitydefs" + File.separator + fileName;

        Path yamlFilePath = Paths.get(entityYamlFile);

        boolean bExists = Files.exists(yamlFilePath);
        log.debug("Seeking data yaml file " + entityYamlFile + " result = " + bExists);
        Assert.assertTrue(entityYamlFile + " exists? ", bExists);
    }

    // Passive, will be dynamic at some point in the future
    @When("^I verify the results$")
    public void i_verify_the_results() {
    }

    @Then("^I expect the settings in salesforce to match those required$")
    public void i_expect_the_settings_in_salesforce_to_match_those_required() throws Exception {

        VerifyEntity.doVerifyEntity(metadataConnection, partnerConnection, entityYamlFile, entityName);

    }


    @After
    public void logout()  {
       salesforceConnectionSteps.logoutEnterpriseConnection (eConnection);
       salesforceConnectionSteps.logoutPartnerConnection (partnerConnection);
    }**/
}
