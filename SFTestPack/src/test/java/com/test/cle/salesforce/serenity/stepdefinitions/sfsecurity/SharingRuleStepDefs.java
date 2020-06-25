package com.test.cle.salesforce.serenity.stepdefinitions.sfsecurity;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.sfsecurity.VerifySharingRules;
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
import static com.test.cle.salesforce.testutils.Constants.ENTITY_NOT_FOUND;



public class SharingRuleStepDefs {


    static String environment = System.getProperty("target_salesforce_environment");
    static String yamlfile = null;
    @Steps
    static SalesforceConnectionSteps salesforceConnectionSteps = null;
    Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    String sfObjectType = null;
    EnterpriseConnection enterpriseConnection = null;
    MetadataConnection metadataConnection = null;


    @Given("^I have a sharingRules yamlDefinition \"([^\"]*)\" for objectType \"([^\"]*)\" in the test resources "
            + "folder$")
    public void i_have_a_sharingRules_yamlDefinition_for_objectType_in_the_test_resources_folder(String yamlFileName,
                                                                                                 String objectType) {

        sfObjectType = objectType;
        yamlfile = yamlFileName;
        log.debug("Testing Sharing Rule for " + objectType);

       String path = LoadProperties.getTestResourcesDirectory();

        yamlfile = path + File.separator + "sharingRulesdefs" + File.separator + yamlFileName;
    }


    @Given("^I have a connection to a valid salesforce org$")
    public void i_have_a_connection_to_a_valid_salesforce_org() {

        enterpriseConnection = salesforceConnectionSteps.getSalesforceEnterpriseConnection(environment);

        Assert.assertNotNull(Constants.ENT_CON_CHECK,enterpriseConnection);
        metadataConnection = SalesforceConnectionSteps.getMetadataConnection(enterpriseConnection);


        log.debug("Connected to salesforce org " + environment);
    }

    @When("^I check the expected settings versus actual settings$")
    public void i_check_the_expected_settings_versus_actual_settings() throws Exception {

        switch (sfObjectType) {
            case "Account":
                VerifySharingRules.doVerifySharingCriteriaRules(sfObjectType, metadataConnection, yamlfile);
                break;
            case "Target__c":
                VerifySharingRules.doVerifySharingCriteriaRules(sfObjectType, metadataConnection, yamlfile);
                break;
            case "Lead":
                VerifySharingRules.doVerifySharingCriteriaRules(sfObjectType, metadataConnection, yamlfile);
                break;
            case "Contact":
                VerifySharingRules.doVerifySharingCriteriaRules(sfObjectType, metadataConnection, yamlfile);
                break;
            case "Commission_Agreement__c":
                VerifySharingRules.doVerifySharingOwnerSharingRules(sfObjectType, metadataConnection, yamlfile);
                break;
            default:
                log.error(ENTITY_NOT_FOUND + sfObjectType);

        }

    }

    @Then("^Then the rule settings should be equal$")
    public void then_the_rule_settings_should_be_equal() {

    }

    @After
    public void logout(){
        salesforceConnectionSteps.logoutEnterpriseConnection (enterpriseConnection);

    }

}
