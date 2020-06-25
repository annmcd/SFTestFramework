package com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess;

import io.restassured.response.Response;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasLength;

public class ResponseAsserts {

    public static void assertSuccessfulCreation(Response response, String expectedApiName) {

        response
                .then()
                .assertThat()
                .statusCode(201)
                .body("apiName", is(expectedApiName));

        String id = response.then().extract().jsonPath().getString("id");
        assertThat(id, hasLength(18));
    }

    public static void assertResponseStatus(Response response, int expectedStatusCode) {
        response
                .then()
                .assertThat()
                .statusCode(expectedStatusCode);
    }

    public static void assertFieldErrorCount(Response response, int expectedCount) {
        response
                .then()
                .assertThat()
                .body("output.fieldErrors.size()", is(expectedCount));
    }

    public static void assertErrorsCount(Response response, int expectedCount) {
        response
                .then()
                .assertThat()
                .body("output.errors.size()", is(expectedCount));
    }

    public static void assertResponseFieldError(Response response, String fieldName, String expectedMessage) {
        String jsonPath = format("output.fieldErrors.%s[0].message", fieldName);

        response
                .then()
                .assertThat()
                .body(jsonPath, is(expectedMessage));
    }

    public static void assertErrorMessage(Response response, String expectedMessage) {
        String jsonPath = format("output.errors[0].message");

        response
                .then()
                .assertThat()
                .body(jsonPath, is(expectedMessage));
    }

}
