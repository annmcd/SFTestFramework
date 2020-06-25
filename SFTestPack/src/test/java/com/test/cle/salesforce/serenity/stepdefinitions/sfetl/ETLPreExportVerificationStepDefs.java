package com.test.cle.salesforce.serenity.stepdefinitions.sfetl;

import com.test.cle.salesforce.serenity.steps.sfetl.ETLPreExportVerificationSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.io.IOException;
import java.sql.Connection;

public class ETLPreExportVerificationStepDefs {

    private static Connection stagingConnection;


    @Steps
    private static ETLPreExportVerificationSteps etlSteps;

  @Given("^I  have a connection to the staging database$")
  public void i_have_a_connection_to_the_staging_database() throws IOException {

      Assert.assertNotNull("Staging DB Connection valid?", ETLPreExportVerificationSteps.getDBConnection());
      stagingConnection=ETLPreExportVerificationSteps.getDBConnection();
  }

  @When("^I trigger the verification$")
  public void i_trigger_the_verification() {
    //check if stored proc exists
  }

  @Then("^I expect that test type \"([^\"]*)\" will be verified by stored procedure \"([^\"]*)\"$")
  public void i_expect_that_test_type_will_be_verified_by_stored_procedure(
      String testType, String storedProcName) {

   int ret= ETLPreExportVerificationSteps.executeStoredProcedure(testType, stagingConnection,storedProcName);

      Assert.assertEquals("Stored Procedure" + testType, 0, ret);
  }

}
