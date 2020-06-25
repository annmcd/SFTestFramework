package com.test.cle.salesforce.serenity.stepdefinitions.sfentityconfig;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.partner.Field;
import com.sforce.soap.partner.PartnerConnection;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.entityconfigsteps.VerifyEntitySteps;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.testutils.LoadProperties;
import com.test.cle.salesforce.yamlelements.security.EntityDef;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.test.cle.salesforce.testutils.Constants.TARGET_ORG;

public class VerifyEntityStepDefs {
    static EnterpriseConnection enterpriseConnection = null;
    static MetadataConnection metadataConnection;
    static String entityName = "";
    static String yamlFilePath = "";

    @Steps
    private static SalesforceConnectionSteps salesforceConnectionSteps = null;


    private static String environment = System.getProperty (TARGET_ORG);
    private static PartnerConnection partnerConnection = null;

    @When("^I trigger a compare expected against actual values in salesforce$")
    public static void i_trigger_a_compare_expected_against_actual_values_in_salesforce() {

        List<String> allErrors = new ArrayList ();

        EntityDef entityDef = VerifyEntitySteps.loadYaml (yamlFilePath);
        Assert.assertNotNull (yamlFilePath + " Yaml File Serialised OK Check? ", entityDef);

        List dataErrors = VerifyEntitySteps.checkDatatTypes (enterpriseConnection, entityDef);
        allErrors.addAll (dataErrors);

        List labelErrors = VerifyEntitySteps.checkLabels (enterpriseConnection, entityDef);
        allErrors.addAll (labelErrors);

        List mandatoryErrors = VerifyEntitySteps.checkMandatory (enterpriseConnection, entityDef);
        allErrors.addAll (mandatoryErrors);

        List deLabelErrors = VerifyEntitySteps.checkDELabels (metadataConnection, entityDef);
        allErrors.addAll (deLabelErrors);

        List picklistErrors = VerifyEntitySteps.checkPicklistValues (partnerConnection, entityDef);
        allErrors.addAll (picklistErrors);

        List removalErrors = VerifyEntitySteps.checkRemoval (partnerConnection, entityDef);
        allErrors.addAll (removalErrors);

        List dePicklistErrors = VerifyEntitySteps.checkDEPicklists (metadataConnection, entityDef);
        allErrors.addAll (dePicklistErrors);

        List encryptedMsgErrors = VerifyEntitySteps.checkEncryptedFields (partnerConnection, entityDef);
        allErrors.addAll (encryptedMsgErrors);
        Field[] fields = VerifyEntitySteps.getSalesforceFieldInfo (partnerConnection, entityDef.getName ());

        List restrictedPLMsgErrors = VerifyEntitySteps.checkRestrictedPicklist( fields,  entityDef) ;
        allErrors.addAll (restrictedPLMsgErrors );
     //   List descMsg = VerifyEntitySteps.checkFieldDescription(enterpriseConnection, entityName);

        if(! allErrors.isEmpty ()){
            String assertMsg=format(allErrors);
            Assert.fail (assertMsg);
        }

    }

    
    static String format(Collection<?> c) {
        String s = c.stream().map(Object::toString).collect(Collectors.joining(","));
        return String.format("[%s]", s);
    }


    @Given("^I have a connection to salesforce Org in a given environment$")
    public void i_have_a_connection_to_salesforce_Org_in_a_given_environment() {
        enterpriseConnection = salesforceConnectionSteps.getSalesforceEnterpriseConnection (environment);
        Assert.assertNotNull (Constants.ENT_CON_CHECK, enterpriseConnection);

        partnerConnection = salesforceConnectionSteps.getSalesforcePartnerConnection (environment);
        Assert.assertNotNull (Constants.PARTNER_CON_CHECK, partnerConnection);

        metadataConnection = SalesforceConnectionSteps.getMetadataConnection (enterpriseConnection);
        Assert.assertNotNull (Constants.METADATA_CON_CHECK, metadataConnection);
    }

    @Given("^I have generated the yaml file \"([^\"]*)\" for entity \"([^\"]*)\"$")
    public void i_have_generated_the_yaml_file_for_entity(String yamlFile, String entity) {
        entityName = entity;
        Assert.assertNotNull ("EntityName must be specified in feature file " + entity);
        yamlFilePath = LoadProperties.getTestResourcesDirectory () + "/entitydefs/" + yamlFile.trim ();

        Assert.assertTrue (yamlFilePath + " Exists? ", LoadProperties.fileExists (yamlFilePath));

    }

    @Then("^I expect them to match$")
    public void i_expect_them_to_match() {


    }

}
