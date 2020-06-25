package com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess;

import com.test.cle.salesforce.serenity.api.objects.Lead;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

import static com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess.ResponseAsserts.assertSuccessfulCreation;

public class LeadStepDefs extends BaseSteps {

    @When("^I create a Lead with no last name")
    public void iCreateALeadWithNoLastName() {
        Lead lead = Lead.getDefaultMinimal()
                .removeLastName();

        uiApiConnector.createRecord(lead);
    }

    @When("^I create a Lead with last name \"([^\"]*)\"")
    public void iCreateALeadWithLastName(String lastName) {
        Lead lead = new Lead()
                .withLastName(lastName);

        uiApiConnector.createRecord(lead);
    }

    @When("^I create a Lead with VVID Number \"([^\"]*)\"")
    public void iCreateALeadWithVVIDNumber(String vvidNumber) {
        Lead lead = Lead.getDefaultMinimal()
                .withVVIDNumber(vvidNumber);

        uiApiConnector.createRecord(lead);
    }

    @Then("^the Lead should be successfully created$")
    public void theLeadShouldBeSuccessfullyCreated() {
        Response response = uiApiConnector.getLastResponse();
        assertSuccessfulCreation(response, Lead.getApiName());
    }
}
