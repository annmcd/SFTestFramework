package com.test.cle.salesforce.serenity.stepdefinitions.sfrecordaccess;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.stepdefinitions.common.RecordAccessStruct;
import com.test.cle.salesforce.serenity.stepdefinitions.common.SalesforceEnterprisePartnerConnections;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.sfrecordaccess.BulkDataExecutionSteps;
import com.test.cle.salesforce.serenity.steps.sfrecordaccess.RecordAccessSteps;
import com.test.cle.salesforce.serenity.steps.sfrecordaccess.RecordGenerationSteps;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.testutils.LoadProperties;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SFRecordAccessStepDefs {

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    static int exitCode;

    @Steps
    RecordAccessSteps steps;

    EnterpriseConnection enterpriseConnection = null;
    String yamlFileCSV = null;
    String filePath = null;
    String yamlFileData = null;
    String yamlFileTestSettings = null;
    String yamlFileRecords = null;
    String region;
    boolean assignedToRegion = false;
    String userRole;


    @Given(
            "^The Data Definition Yaml File \"([^\"]*)\" with record data required for record creation exist$")
    public void the_Data_Definition_Yaml_File_with_record_data_required_for_record_creation_exist(
            String dataYamlFile) {

        filePath= LoadProperties.getTestResourcesDirectory();

        yamlFileData = filePath + File.separator + "recordAccessDefs" + File.separator + dataYamlFile;

        Path path = Paths.get(yamlFileData);

        boolean bExists = Files.exists(path);
        log.debug("Seeking data yaml file " + yamlFileData + " result = " + bExists);
        Assert.assertTrue(yamlFileData, bExists);
    }

    @Given("^The Directors are distinguishable by their UserRole$")
    public void the_Regional_Director_is_identified_by_UserRole() {

        assignedToRegion = false;
    }

    @Given("^The Directors are distinguishable by their UserRole \"([^\"]*)\"$")
    public void set_the_role(String the_role) {

        userRole = the_role;
    }

    @Given("^The lookup Region is \"([^\"]*)\"$")
    public void the_lookup_Region_is(String arg1) {

        region = arg1;
        assignedToRegion = true;
    }

    @Given("^The record data file is \"([^\"]*)\"$")
    public void the_record_data_file_is(String recordDataFile) {

        Path resourceDirectory = Paths.get("src", "test", "resources");
        yamlFileRecords =
                resourceDirectory + File.separator + "recordAccessDefs" + File.separator + recordDataFile;

        Path path = Paths.get(yamlFileRecords);

        boolean bExists = Files.exists(path);

        Assert.assertTrue(yamlFileRecords, bExists);
    }

    @Given("^The owners are assigned to a region$")
    public void assignToRegion() {
        assignedToRegion = true;
    }

    @Given("^The CSV structure Yaml File \"([^\"]*)\" exists$")
    public void the_CSV_structure_Yaml_File_exists(String csvStructureYaml) {

        Path resourceDirectory = Paths.get("src", "test", "resources");
        filePath = resourceDirectory.toAbsolutePath().toString();

        yamlFileCSV =
                filePath + File.separator + "recordAccessDefs" + File.separator + csvStructureYaml;

        Path path = Paths.get(yamlFileCSV);

        boolean bExists = Files.exists(path);
        log.debug("Seeking csv structure  yaml file " + yamlFileCSV + " result = " + bExists);

        Assert.assertTrue(yamlFileCSV, bExists);

        // Generate the CSV files
        RecordGenerationSteps.doStartCSVGenerationProcess(yamlFileData, yamlFileCSV, assignedToRegion);
    }

    @Given("^SFBulkData executes to create those records$")
    public void sfbulkdata_executes_to_create_those_records() throws Exception {

        // create the batchid text file and write the batch id to it
        RecordGenerationSteps.writeBatchIdfile();

        /**
         * Execute BulkData Latest version which has been downloaded from CLe Azure devops [maven
         * dependency]*
         */
        exitCode = BulkDataExecutionSteps.executeBulkData();
    }

    @Then("^the upload is successful with exit code (\\d+)$")
    public void the_upload_is_successful_with_exit_code(int exitCode) {

        if (exitCode != 0) {
            log.error("SFBulkData Upload Failure");
        }

        Assert.assertEquals("BulkData Exit Code", 0, exitCode);

    }

    @Given("^I have a salesforce connection to a target environment salesforce org$")
    public void connect() {

        enterpriseConnection = SalesforceEnterprisePartnerConnections.login();
    }

    // Test Scenario conditions start here
    @Given("^I check that the connection is valid$")
    public void i_have_a_valid_connection_to_specified_salesforce_environment_as_an_admin_user() {

        Assert.assertNotNull(Constants.ENT_CON_CHECK,enterpriseConnection);
    }

    @Given("^I have a yamlFile \"([^\"]*)\" with the expected settings per entity type$")
    public void i_have_a_yamlFile_with_the_expected_settings_per_entity_type(String file) {

        Path resourceDirectory = Paths.get("src", "test", "resources");
        String filePath = resourceDirectory.toAbsolutePath().toString();

        yamlFileTestSettings =
                filePath + File.separator + Constants.FOLDER_RECORD_ACCESS + File.separator + file;

        Path path = Paths.get(yamlFileTestSettings);

        boolean bExists = Files.exists(path);

        Assert.assertTrue("Yaml file Exists " + yamlFileTestSettings, bExists);
    }

    @When("^I verify run a verification on record access$")
    public void i_verify_run_a_verification_on_record_access() {
        // passive context
    }

    @Then(
            "^I expect the access on records owned by owner \"([^\"]*)\" to match the expected settings  represented by "
                    + "test account \"([^\"]*)\"$")
    public void
    i_expect_the_access_on_records_owned_by_owner_to_match_the_expected_settings_represented_by_test_account(
            String ownerUserName, String accessorUserName) {

        RecordAccessStruct recordDetails = new RecordAccessStruct();
        recordDetails.setEnterpriseConnection(enterpriseConnection);
        recordDetails.setOwnerUserName(ownerUserName);
        recordDetails.setAccessorUserName(
                accessorUserName.trim() + "." + System.getProperty("target_salesforce_environment"));
        recordDetails.setBatchId(Constants.BATCH_ID_AUTO);
        recordDetails.setYamlFileCSVStructure(yamlFileCSV);
        recordDetails.setYamlFileData(yamlFileData);
        recordDetails.setYamlFileSecuritySettings(yamlFileTestSettings);
        recordDetails.setEnvironment(System.getProperty("target_salesforce_environment"));
        recordDetails.setRegion(region);
        recordDetails.setYamlFileRecord(
                this.yamlFileRecords); // includes where conditions for the lookup
        recordDetails.setUserRole(userRole);

        // print to the report log only and not the serenity report

        try {

            RecordAccessSteps.reportRecordAccessSettings(false, recordDetails, enterpriseConnection);

        } catch (Exception e) {

            log.error("Exception thrown evaluating Record access, ", e);
            Assert.fail("Runtime Exception Occured: " + yamlFileTestSettings)  ;
        }


        try {

            RecordAccessSteps.reportRecordAccessSettings(true, recordDetails, enterpriseConnection);

        } catch (Exception e) {

            log.error("Exception thrown evaluating access settings " + this.yamlFileRecords, e);
            Assert.fail("Runtime Exception Occured: " + yamlFileTestSettings)  ;
        }
    }

    @After
    public  void logout()throws ConnectionException {

        SalesforceEnterprisePartnerConnections.logout (enterpriseConnection);

    }
}
