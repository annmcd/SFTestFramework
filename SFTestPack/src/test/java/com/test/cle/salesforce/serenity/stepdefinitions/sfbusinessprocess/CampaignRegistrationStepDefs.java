package com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CampaignRegistrationStepDefs {

    private static final String EVENT_FULLY_BOOKED_MSG_DE = "Die Veranstaltung ist ausgebucht. Eine Registrierung für dieses Datum/diesen Ort ist daher nicht mehr möglich. Bitte wählen Sie eine";
    private static final String REGISTRATION_LINK_INVALID_MSG = "The registration link used is not valid. Please contact support.";
    private static final String PLEASE_WAIT_MSG_DE = "Bitte warten Sie, Ihre Daten werden übermittelt.";


    @Given("^the campaign \"([^\"]*)\" does not have the \"([^\"]*)\" Campaign Member Status$")
    public void theCampaignIsNotConfiguredToSendEmails(String arg0, String campaignMemberStatus) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^a new user has registered for the \"([^\"]*)\" campaign$")
    public void aNewUserHasRegisteredForTheCampaign(String campaignName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I open the CLE Events Portal page passing in no URL parameters$")
    public void iOpenTheCLEEventsPortalPagePassingInNoURLParameters() {
    }

    @When("^I open the registration page for the \"([^\"]*)\" campaign$")
    public void iOpenTheRegistrationPageForTheCampaign(String campaignName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I open the CLE Events Portal page passing in URL Parameter: \"([^\"]*)\"$")
    public void iOpenTheCLEEventsPortalPagePassingInURLParameter(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I open the registration page for the \"([^\"]*)\" campaign using Campaign Name$")
    public void iOpenTheRegistrationPageForTheCampaignUsingCampaignName(String campaignName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I open the registration page for the \"([^\"]*)\" campaign using Campaign ID$")
    public void iOpenTheRegistrationPageForTheCampaignUsingCampaignId(String campaignName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the registration form should not be displayed$")
    public void theRegistrationFormShouldNotBeDisplayed() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I submit the registration form for the \"([^\"]*)\" campaign with the following details:$")
    public void iSubmitTheRegistrationFormForTheCampaign(String campaignName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should see the registration form$")
    public void iShouldSeeTheRegistrationForm() {
        throw new PendingException();
    }

    @Then("^I should see Event Fully Booked message$")
    public void iShouldSeeEventFullyBookedMessage() {
        // assert against EVENT_FULLY_BOOKED_MSG_DE
        throw new PendingException();
    }

    @Then("^the workshop \"([^\"]*)\" should be automatically selected$")
    public void theWorkshopShouldBeAutomaticallySelected(String eventName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^there should be \"([^\"]*)\" workshop selection options$")
    public void thereShouldBeWorkshopSelectionOptions(String nWorkshopOptions) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the workshop \"([^\"]*)\" should be visible but disabled$")
    public void theWorkshopShouldBeVisibleButDisabled(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the campaign ID hidden field should be populated with the campaign ID of \"([^\"]*)\"$")
    public void theCampaignIDHiddenFieldShouldBePopulatedWithTheCampaignIDOf(String campaignName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should see the registration link invalid message$")
    public void iShouldSeeTheRegistrationLinkInvalidMessage() {
        // assert against REGISTRATION_LINK_INVALID_MSG
        throw new PendingException();
    }

    @Then("^I should see a please wait message$")
    public void iShouldSeeAPleaseWaitMessage() {
        // assert against PLEASE_WAIT_MSG_DE
        throw new PendingException();
    }

    @And("^I should see a registration confirmation message for the \"([^\"]*)\" event$")
    public void iShouldSeeARegistrationConfirmationMessageForTheEvent(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should receive an Event Registration email$")
    public void iShouldReceiveAnEventRegistrationEmail() {
        throw new PendingException();
    }

    @Then("^a Lead record should be created with details corresponding to the registration$")
    public void aLeadRecordShouldBeCreatedWithDetailsCorrespondingToTheRegistration() {
        throw new PendingException();
    }

    @Then("^the Lead record should remain in the lead queue$")
    public void theLeadRecordShouldRemainInTheLeadQueue() {
        throw new PendingException();
    }

    @Then("^a Campaign Member record should be created with details corresponding to the Lead$")
    public void aCampaignMemberRecordShouldBeCreatedWithDetailsCorrespondingToTheLead() {
        throw new PendingException();
    }

    @Then("^the status of the Campaign Member record should be \"([^\"]*)\"$")
    public void theStatusOfTheCampaignMemberRecordShouldBe(String expectedStatus) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should have the following options for Event Selection$")
    public void iShouldHaveTheFollowingOptionsForEventSelection() {
        throw new PendingException();
    }

    @And("^selecting \"([^\"]*)\" Event results in the following Slots and Sessions:$")
    public void selectingEventResultsInTheFollowingSlotsAndSessions(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^I select the \"([^\"]*)\" Event$")
    public void iSelectTheEvent(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^a new user has registered for the \"([^\"]*)\" campaign selecting the following sessions:$")
    public void aNewUserHasRegisteredForTheCampaignSelectingTheFollowingSessions(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the slot \"([^\"]*)\" should have \"([^\"]*)\" automatically selected$")
    public void theSlotShouldHaveAutomaticallySelected(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I complete and submit the registration form  with the following details:$")
    public void iCompleteAndSubmitTheRegistrationFormWithTheFollowingDetails() {
    }

    @Then("^I should see a No Sessions Available message under the \"([^\"]*)\" slot$")
    public void iShouldSeeANoSessionsAvailableMessageUnderTheSlot(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the Campaign Member record should have no event session registrations$")
    public void theCampaignMemberRecordShouldHaveNoEventSessionRegistrations() {
        throw new PendingException();
    }

    @Then("^I should see the following slots in this order:$")
    public void iShouldSeeTheFollowingSlotsInThisOrder() {
        throw new PendingException();
    }

    @Then("^I should see an Invalid Link message$")
    public void iShouldSeeAnInvalidLinkMessage() {

    }

    @Then("^I should see the Event Booked Out message$")
    public void iShouldSeeTheEventBookedOutMessage() {

    }

    @Then("^I should see the registration form with the expected basic fields$")
    public void iShouldSeeTheRegistrationFormWithTheExpectedBasicFields() {
    }


    @Then("^I should see the registration form with the expected basic fields as well as:$")
    public void iShouldSeeTheRegistrationFormWithTheExpectedBasicFieldsAsWellAs(DataTable dataTable) {
        
    }

    @Then("^I should see no slots on the registration form$")
    public void iShouldSeeNoSlotsOnTheRegistrationForm() {
        

    }

    @Then("^a new Lead record should be created with the expected CampaignRegisteredTo and CampaignMemberID values$")
    public void aNewLeadRecordShouldBeCreatedWithTheExpectedCampaignRegisteredToAndCampaignMemberIDValues() {

    }

    @And("^no email should be sent to the registrant$")
    public void noEmailShouldBeSentToTheRegistrant() {
    }

    @Then("^a Campaign Member record should be created with the expected CampaignID, LeadID, blank ContactID, and status: \"([^\"]*)\"$")
    public void aCampaignMemberRecordShouldBeCreatedWithTheExpectedCampaignIDLeadIDBlankContactIDAndStatus(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^a Campaign Member record should be created with the expected CampaignID, LeadID, populated ContactID, and status \"([^\"]*)\"$")
    public void aCampaignMemberRecordShouldBeCreatedWithTheExpectedCampaignIDLeadIDPopulatedContactIDAndStatus(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^an existing Lead registers for the  \"([^\"]*)\" campaign with details:$")
    public void anExistingLeadRegistersForTheCampaignWithDetails(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^if should observe the following behaviour upon submitting the form:$")
    public void ifShouldObserveTheFollowingBehaviourUponSubmittingTheForm() {
    }
}
