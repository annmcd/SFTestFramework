package com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess;

import com.test.cle.salesforce.serenity.api.objects.CampaignEventSession;
import com.test.cle.salesforce.serenity.api.service.Context;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

import static com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess.ResponseAsserts.assertSuccessfulCreation;

public class CampaignEventSessionStepDefs extends BaseSteps {

    @When("^I create a Campaign Event Session named \"([^\"]*)\" with no campaign event slot ID")
    public void iCreateCampaignEventSessionNamedWithNoSlot(String sessionName) {
        CampaignEventSession session = new CampaignEventSession()
                .withName(sessionName);

        uiApiConnector.createRecord(session);
    }

    @When("^I create an associated Campaign Event Session named \"([^\"]*)\"")
    public void iCreateAnAssociatedCampaignEventSessionNamed(String sessionName) {
        CampaignEventSession slot = new CampaignEventSession()
                .withCampaignEventSlot(Context.getCampaignEventSlotId())
                .withName(sessionName);

        uiApiConnector.createRecord(slot);
    }

    @Then("^the Campaign Event Session should be successfully created$")
    public void theCampaignEventSessionShouldBeSuccessfullyCreated() {
        Response response = uiApiConnector.getLastResponse();
        assertSuccessfulCreation(response, CampaignEventSession.getApiName());
    }
}
