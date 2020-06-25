package com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess;

import com.test.cle.salesforce.serenity.api.objects.Campaign;
import com.test.cle.salesforce.serenity.api.service.Context;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

public class CampaignStepDefs extends BaseSteps {

    @Given("^an ([^\" ]*) ([^\" ]*) event named \"([^\"]*)\" exists with capacity \"([^\"]*)\" and no shared common name$")
    public void anEventNamedExistsWithCapacityAndNoSharedCommonName(String activeStatus, String campaignType, String campaignName, String campaignCapacity) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^an ([^\" ]*) ([^\" ]*) event named \"([^\"]*)\" exists with capacity \"([^\"]*)\" and shared common name \"([^\"]*)\"$")
    public void anEventNamedExistsWithCapacityAndSharedCommonName(String activeStatus, String campaignType, String campaignName, String campaignCapacity, String eventCommonName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^an ([^\" ]*) ([^\" ]*) event named \"([^\"]*)\" exists with blank capacity and no shared common name$")
    public void anEventNamedExistsWithBlankCapacityAndNoSharedCommonName(String activeStatus, String campaignType, String campaignName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^I set the capacity of the \"([^\"]*)\" campaign to \"([^\"]*)\"$")
    public void iSetTheCapacityOfTheCampaignTo(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^the following Campaign exists:$")
    public void theFollowingCampaignExists(DataTable dataTable) {
    }

    @When("^I create a new campaign with details:$")
    public void iCreateANewCampaignWithDetails(DataTable dataTable) {
        throw new PendingException();
    }

    @Then("^the \"([^\"]*)\" campaign should be available in the Campaign List view$")
    public void theCampaignShouldBeAvailableInTheCampaignListView(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the \"([^\"]*)\" campaign should contain the following details:$")
    public void theCampaignShouldHaveTheFollowingDetails(String arg0, DataTable dataTable) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the Campaign Member record should have \"([^\"]*)\" event session registrations containing the lead, member ID, member Name, and expected sessions$")
    public void theCampaignMemberRecordShouldHaveEventSessionRegistrationsContainingTheLeadMemberIDMemberNameAndExpectedSessions(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^a new Campaign Member record should be created$")
    public void aNewCampaignMemberRecordShouldBeCreated() {

    }

    @And("^the associated Lead record is created of Lead Record Type \"([^\"]*)\"$")
    public void theAssociatedLeadRecordIsCreatedOfLeadRecordType(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^the campaign \"([^\"]*)\" does have the \"([^\"]*)\" Campaign Member Status$")
    public void theCampaignDoesHaveTheCampaignMemberStatus(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the existing Lead record is updated to include:$")
    public void theExistingLeadRecordIsUpdatedToInclude(DataTable dataTable) {
    }

    @Given("^an (active|inactive) (workshop|webinar) campaign named \"([^\"]*)\" exists")
    public void aCampaignNamedExists(String activeStateStr, String campaignTypeStr, String campaignName) {
        boolean isActive = activeStateStr.equalsIgnoreCase("active");
        String campaignType = getCampaignType(campaignTypeStr);

        Campaign campaign = new Campaign()
                .getDefaultMinimal()
                .withIsActive(isActive)
                .withType(campaignType)
                .withName(campaignName);

        uiApiConnector.createRecord(campaign);

        Response response = uiApiConnector.getLastResponse();
        response
                .then()
                .assertThat()
                .statusCode(201);
        String id = response.then().extract().jsonPath().getString("id");
        Context.storeCampaignId(id);
    }


    private String getCampaignType(String campaignTypeStr) {
        String sfCampaignType = null;

        if (campaignTypeStr.equalsIgnoreCase("workshop")) {
            sfCampaignType = "Workshops (single event)";
        } else if (campaignTypeStr.equalsIgnoreCase("webinar")) {
            sfCampaignType = "Webinars";
        }

        return sfCampaignType;
    }
}
