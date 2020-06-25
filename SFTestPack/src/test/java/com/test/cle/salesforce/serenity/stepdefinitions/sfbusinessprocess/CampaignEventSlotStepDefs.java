package com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess;

import com.test.cle.salesforce.serenity.api.objects.CampaignEventSlot;
import com.test.cle.salesforce.serenity.api.service.Context;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

import static com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess.ResponseAsserts.assertSuccessfulCreation;

public class CampaignEventSlotStepDefs extends BaseSteps {

    @When("^I create an associated Campaign Event Slot named \"([^\"]*)\"")
    public void iCreateAnAssociatedCampaignEventSlotNamed(String slotName) {
        CampaignEventSlot slot = new CampaignEventSlot()
                .withCampaign(Context.getCampaignId())
                .withName(slotName);

        uiApiConnector.createRecord(slot);
    }

    @When("^I create a Campaign Event Slot named \"([^\"]*)\" with no campaign ID")
    public void iCreateCampaignEventSlotWithNoCampaignID(String slotName) {
        CampaignEventSlot slot = new CampaignEventSlot()
                .withName(slotName);

        uiApiConnector.createRecord(slot);
    }

    @Then("^the Campaign Event Slot should be successfully created$")
    public void theCampaignEventSlotShouldBeSuccessfullyCreated() {
        Response response = uiApiConnector.getLastResponse();
        assertSuccessfulCreation(response, CampaignEventSlot.getApiName());
    }

}
