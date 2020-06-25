package com.test.cle.salesforce.serenity.stepdefinitions.sfbulkdata;

import com.test.cle.salesforce.serenity.stepdefinitions.common.BaseDefinition;
import com.test.cle.salesforce.serenity.steps.sfbulkdata.BulkDataResponses;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SFBulkDataTriggerResponseStepDefs extends BaseDefinition {

    @Steps
    static BulkDataResponses bulkDataResponses = null;

    String yamlFile = null;
    int responseCode = 0;
    String log4JDir = null;


    public int getResponseCode() {

        return responseCode;
    }

    public void setResponseCode(int responseCode) {

        this.responseCode = responseCode;
    }

    public String getYamlFileForEnvironment(String yamlFile) {

        Path resourceDirectory = Paths.get("src", "test", "resources");
        String path = resourceDirectory.toAbsolutePath().toString() + "/sfbulkdatadefs";
        return path + "/" + getEnvironment() + "_" + yamlFile.trim();

    }

    @Given("^I know the order of upload and delete$")
    public void i_know_the_order_of_upload_and_delete() {
        // assumed to be correct in the config file for triggering the appropriate response code
    }

    @Given("^I have Yaml Configfile \"([^\"]*)\" configured with entities for the relevant test$")
    public void i_have_Yaml_Configfile_configured_with_entities_for_the_relevant_test(
            String yamlConfigFile) {

        yamlFile = yamlConfigFile;
    }

    @Given("^Log(\\d+)J will log to\"([^\"]*)\"$")
    public void log_J_will_log_to(int arg1, String directory) {
        log4JDir = directory;
    }

    @When("^I trigger SFBulkData for upload$")
    public void i_trigger_SFBulkData_upload_for_upload() throws IOException, InterruptedException {

        String yamlConfig = getYamlFileForEnvironment(yamlFile);

        System.out.println("The config file returned " + yamlConfig);

        responseCode = bulkDataResponses.triggerBulkData(yamlConfig, log4JDir);
    }

    @When("^I trigger SFBulkData for delete$")
    public void i_trigger_SFBulkData_upload_for_delete() throws InterruptedException, IOException {

        String yamlConfig = getYamlFileForEnvironment(yamlFile);

        System.out.println("The config file returned " + yamlConfig);

        responseCode = bulkDataResponses.triggerBulkData(yamlConfig, log4JDir);
    }

    @Then("^I expect the appropriate responseCode \"([^\"]*)\"$")
    public void i_expect_the_appropriate_responseCode(String expectedExitCode) {

        Assert.assertEquals(Integer.parseInt(expectedExitCode), getResponseCode());
    }
}
