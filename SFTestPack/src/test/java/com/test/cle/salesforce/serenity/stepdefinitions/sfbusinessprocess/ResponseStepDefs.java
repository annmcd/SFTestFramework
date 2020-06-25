package com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess.ResponseAsserts.*;

public class ResponseStepDefs extends BaseSteps {

    @Then("^an error occurs due to the following validation messages:$")
    public void anErrorOccursDueToTheFollowingValidationMessages(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Response response = uiApiConnector.getLastResponse();

        assertResponseStatus(response, 400);
        assertFieldErrorCount(response, rows.size());
        for (Map<String, String> columns : rows) {
            assertResponseFieldError(response, columns.get("field"), columns.get("message"));
        }
    }

    @Then("^an error occurs due to the following error messages:$")
    public void anErrorOccursDueToTheFollowingErrorMessages(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Response response = uiApiConnector.getLastResponse();

        assertResponseStatus(response, 400);
        assertErrorsCount(response, rows.size());
        for (Map<String, String> columns : rows) {
            assertErrorMessage(response, columns.get("message"));
        }
    }

}
