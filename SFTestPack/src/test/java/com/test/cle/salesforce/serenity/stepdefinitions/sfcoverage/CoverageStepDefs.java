package com.test.cle.salesforce.serenity.stepdefinitions.sfcoverage;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.partner.PartnerConnection;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.sfcoverage.EntityFieldCoverageSteps;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.testutils.LoadProperties;
import com.test.cle.salesforce.yamlelements.security.EntityDef;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.List;

import static com.test.cle.salesforce.testutils.Constants.TARGET_ORG;

public class CoverageStepDefs {

    static Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());

    @Steps
    private static SalesforceConnectionSteps salesforceConnectionSteps = null;
    private static String environment = System.getProperty (TARGET_ORG);
    EntityDef entity = null;
    EnterpriseConnection enterpriseConnection = null;
    PartnerConnection partnerConnection = null;
    MetadataConnection metadataConnection;
    String entityName = "";
    String yamlFilePath = "";
    StringBuffer nonTested = new StringBuffer ();
    boolean enPicklistsOk;
    boolean descriptionsOK;
    boolean helpTextOk;
    boolean isRestrictedPicklistOK;
    boolean isEncryptedOK;



    @Given("^I have a saleforce connection$")
    public void i_have_a_saleforce_connection() {

        enterpriseConnection = salesforceConnectionSteps.getSalesforceEnterpriseConnection (environment);
        Assert.assertNotNull (Constants.ENT_CON_CHECK, enterpriseConnection);

        partnerConnection = salesforceConnectionSteps.getSalesforcePartnerConnection (environment);
        Assert.assertNotNull (Constants.PARTNER_CON_CHECK, partnerConnection);

        metadataConnection = SalesforceConnectionSteps.getMetadataConnection (enterpriseConnection);
        Assert.assertNotNull (Constants.METADATA_CON_CHECK, metadataConnection);

    }

    @Given("^I have Yaml Configfile \"([^\"]*)\" for an Entity \"([^\"]*)\"$")
    public void i_have_Yaml_Configfile_for_an_Entity(String yamlFile, String entity) {

        entityName = entity;
        Assert.assertNotNull ("EntityName must be specified in feature file " + entity);
        yamlFilePath = LoadProperties.getTestResourcesDirectory () + "/entitydefs/" + yamlFile.trim ();

        Assert.assertTrue (yamlFilePath + " Exists? ", LoadProperties.fileExists (yamlFilePath));


    }


    @Then("^I expect all the custom fields to be compared for datatypes, labels and delabels$")
    public void i_expect_all_the_custom_fields_to_be_compared_for_datatypes_labels_and_delabels() throws FileNotFoundException {

        //Print all results first.
        EntityFieldCoverageSteps.checkENMetadata (enterpriseConnection, partnerConnection,
                yamlFilePath, entityName, false);

        EntityFieldCoverageSteps.checkTranslation (metadataConnection, entityName, yamlFilePath, false);


        EntityFieldCoverageSteps.checkENMetadata (enterpriseConnection, partnerConnection,
                yamlFilePath, entityName, true);


        EntityFieldCoverageSteps.checkTranslation (metadataConnection, entityName, yamlFilePath, true);


    }

    @Then("^I expect boolean fields to be compared$")
    public void i_expect_boolean_fields_to_be_compared() {


        try (InputStream inputStream = new FileInputStream (new File (yamlFilePath))) {
            Yaml yaml = new Yaml (new Constructor (EntityDef.class));
            entity = yaml.load (inputStream);
        } catch (IOException e) {
            log.error (e);
        }


        isEncryptedOK = EntityFieldCoverageSteps.checkEncryptedFields (partnerConnection, entity);

        isRestrictedPicklistOK = EntityFieldCoverageSteps.checkRestrictedPicklists (partnerConnection, entity);

        enPicklistsOk= EntityFieldCoverageSteps.checkYamlListForPicklists(partnerConnection, entity);

    }

    //For entities only
    @When("^I provide the custom CLE Entities I am verifying field checks on$")
    public void i_provide_the_custom_CLE_Entities_I_am_veririfying_field_checks_on(List<String> entities) {

        for (String entity : entities) {
            System.out.println ("Entities provided=" + entity);
        }
        nonTested = EntityFieldCoverageSteps.checkCustomEntities (enterpriseConnection, entities);
    }

    //For Entities only
    @Then("^I expect that list to contain all of the custom entities in salesforce$")
    public void i_expect_that_list_to_contain_all_of_the_custom_entities_in_salesforce() {

        if (nonTested.length () > 0) {
            Assert.fail ("ENTITIES WITH NO TEST COVERAGE" + nonTested.toString ());
        }
    }


    @Then("^I expect all fields to have helptext and descriptions$")
    public void i_expect_all_fields_to_have_helptext_and_descriptions() {

        descriptionsOK = EntityFieldCoverageSteps.checkEntityFieldDescriptions (enterpriseConnection, entityName);

        helpTextOk = EntityFieldCoverageSteps.checkHelpText (entityName, partnerConnection);

    }


    @Then("^I expect all failures to be logged$")
    public void i_expect_all_failures_to_be_logged() {

        if(!enPicklistsOk){
            Assert.fail (entityName + " missing en picklist values, see error log");
        }
        if(isRestrictedPicklistOK){
            Assert.fail (entityName + " missing restricted picklist indicator values in yaml, see error log");
        }
        if(isEncryptedOK){
            Assert.fail (entityName + " missing encryption values in yaml, see error log");
        }
        if(descriptionsOK){
            Assert.fail (entityName + " missing descriptions, see error log");
        }
        if(!helpTextOk){
            Assert.fail (entityName + " missing helptext see error log");
        }

    }

    @After
    public void logout() {
        salesforceConnectionSteps.logoutEnterpriseConnection (enterpriseConnection);
    }

}
