package com.test.cle.salesforce.serenity.stepdefinitions.common;

import com.test.cle.salesforce.serenity.steps.common.DBConnectionSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.sql.Connection;
import java.sql.SQLException;

public class ETLDatabaseConnections {

    private static Connection connectionStaging;
    private static String environment;

    @Steps
    private static DBConnectionSteps connectionSteps;

    @Given("^I require connections to the warehouse and staging databases in a given environment$")
    public static void
    i_require_connections_to_the_warehouse_and_staging_databases_in_a_given_environment() {

        environment = System.getProperty("target_salesforce_environment");

        Assert.assertNotNull(environment);
    }

    @Given("^the staging connection property is  \"([^\"]*)\"$")
    public static void the_staging_connection_property_is(String arg1) {

        connectionStaging = connectionSteps.getDBConnection(environment, arg1);
        Assert.assertNotNull(connectionStaging);
    }

    @Then("^I should have valid connections$")
    public static void i_should_have_valid_connections() throws SQLException {
        // Write code here that turns the phrase above into concrete actions

        String stagingCatalog = connectionStaging.getCatalog();

        /**ToDo this should match an environment specific connection in a future release
        eg  Assert.assertEquals("CLE_SFStagingDB_" + environment, stagingCatalog);
         **/
        Assert.assertNotNull(stagingCatalog);
        //Assert.assertEquals("CLE_SFStagingDB_sprints" + environment, stagingCatalog);

        connectionStaging.close();
    }

    @When("^I issue connect$")
    public void i_issue_connect() {
        //passive

    }
}
