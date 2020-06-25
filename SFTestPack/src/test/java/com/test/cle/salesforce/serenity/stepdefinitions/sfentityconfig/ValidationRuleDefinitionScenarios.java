package com.test.cle.salesforce.serenity.stepdefinitions.sfentityconfig;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.entityconfigsteps.ValidationRuleSteps;
import com.test.cle.salesforce.testutils.Constants;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;

import java.lang.invoke.MethodHandles;

import static com.test.cle.salesforce.testutils.Constants.TARGET_ORG;

public class ValidationRuleDefinitionScenarios {

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    EnterpriseConnection enterpriseConnection;
    MetadataConnection metadataConnection;

    private static String environment = System.getProperty(TARGET_ORG);

    @Steps
    private static SalesforceConnectionSteps salesforceConnectionSteps = null;

    @Given("^I have a valid salesforce connection$")
    public void i_have_a_valid_salesforce_connection() {


        enterpriseConnection = salesforceConnectionSteps.getSalesforceEnterpriseConnection(environment);
        Assert.assertNotNull(Constants.ENT_CON_CHECK, enterpriseConnection);

        metadataConnection = SalesforceConnectionSteps.getMetadataConnection(enterpriseConnection);
        Assert.assertNotNull("metadata Connection", metadataConnection);

        log.debug("session header" + enterpriseConnection.getSessionHeader());
    }

    @Given("^I have an  entity \"([^\"]*)\" with validation rule \"([^\"]*)\"$")
    public void i_have_an_entity_with_validation_rule(String entityName, String ruleName)
            throws ConnectionException {

        ValidationRuleSteps.ruleExists(
                enterpriseConnection, metadataConnection, ruleName.trim(), entityName.trim());
    }

    @When("^I execute a verification against validation rules$")
    public void i_execute_a_verification_against_validation_rules() {
        // this serenity run
    }

    @Then(
            "^I expect the errorFormula specified in the properties file \"([^\"]*)\" to match salesforce values$")
    public void i_expect_the_errorFormula_specified_in_the_properties_file_to_match_salesforce_values(
            String propertiesFileName) {

        ValidationRuleSteps.isErrorConditionFormulaOK(propertiesFileName);
    }

    @Then("^I expect the errorMessage \"([^\"]*)\" to match salesforce values$")
    public void i_expect_the_errorMessage_to_match_salesforce_values(String errorMsg) {

        ValidationRuleSteps.isErrorConditionMessageOk(errorMsg);
    }

    @Then("^The rule activeState \"([^\"]*)\" is as expected$")
    public void the_rule_activeState_is_as_expected(String activeState) {

        ValidationRuleSteps.isActiveState(Boolean.valueOf(activeState.trim()));
    }

    @After
    public void logout()  {
        salesforceConnectionSteps.logoutEnterpriseConnection ( enterpriseConnection);
    }

}
