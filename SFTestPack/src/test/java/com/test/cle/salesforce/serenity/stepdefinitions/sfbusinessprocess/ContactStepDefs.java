package com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess;

import com.test.cle.salesforce.serenity.api.objects.Contact;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

import static com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess.ResponseAsserts.assertSuccessfulCreation;

public class ContactStepDefs extends BaseSteps {

    @When("^I create a Contact with no salutation")
    public void iCreateAContactWithNoSalutation() {
        Contact contact = Contact.getDefaultMinimal()
                .removeSalutation();

        uiApiConnector.createRecord(contact);
    }

    @When("^I create a Contact with no last name")
    public void iCreateAContactWithNoLastName() {
        Contact contact = Contact.getDefaultMinimal()
                .removeLastName();

        uiApiConnector.createRecord(contact);
    }

    @When("^I create a Contact with VVID Number \"([^\"]*)\"")
    public void iCreateAContactWithVVIDNumber(String vvidNumber) {
        Contact contact = Contact.getDefaultMinimal()
                .withVVIDNumber(vvidNumber);

        uiApiConnector.createRecord(contact);
    }

    @When("^I create a Contact with salutation: \"([^\"]*)\" and last name \"([^\"]*)\"")
    public void iCreateAContactWithSalutationAndLastName(String salutation, String lastName) {
        Contact contact = new Contact()
                .withSalutation(salutation)
                .withLastName(lastName);

        uiApiConnector.createRecord(contact);
    }

    @Then("^the Contact should be successfully created$")
    public void theContactShouldBeSuccessfullyCreated() {
        Response response = uiApiConnector.getLastResponse();
        assertSuccessfulCreation(response, Contact.getApiName());
    }
}
