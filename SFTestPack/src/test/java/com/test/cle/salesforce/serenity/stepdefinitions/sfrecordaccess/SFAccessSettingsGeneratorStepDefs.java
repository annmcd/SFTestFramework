package com.test.cle.salesforce.serenity.stepdefinitions.sfrecordaccess;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.sfrecordaccess.AccessSettingsGeneratorSteps;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.testutils.LoadProperties;
import com.test.cle.salesforce.yamlelements.security.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.test.cle.salesforce.testutils.Constants.TARGET_ORG;
import static org.junit.Assert.assertNotNull;

public class SFAccessSettingsGeneratorStepDefs {

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Steps
    private static SalesforceConnectionSteps salesforceConnectionSteps = null;


    private static String environment = System.getProperty(TARGET_ORG);
    String region;
    String type;
    String etlEntity;
    String owner;
    String yamlRecord = null;
    String csvStructureYaml;
    String yamlGeneratedFile;
    String yamlTestAccounts;
    String fileToTest = null;
    String userRole = null;
    EnterpriseConnection enterpriseConnection = null;
    String filePath=null;



    @Given("^Records are uploaded$")
    public void records_are_uploaded() {
        // passive already done

    }

    @Given("^an owner \"([^\"]*)\" owns records for type \"([^\"]*)\" entityType \"([^\"]*)\"$")
    public void an_owner_owns_records_for_type_entityType(
            String username, String entity, String etlE) {

        owner = username;

        type = entity;

        etlEntity = etlE;
    }

    // We expect Region to be populated or UserRole and not both
    @Given("^The owner is identifyable by UserRole \"([^\"]*)\"$")
    public void the_owner_is_identifyable_by_UserRole(String role) {

        userRole = role;
    }

    @Given("^I have a salesforce connection$")
    public void connect() {


        enterpriseConnection = salesforceConnectionSteps.getSalesforceEnterpriseConnection(environment);

        Assert.assertNotNull(Constants.ENT_CON_CHECK, enterpriseConnection);
    }

    @Given("^The connection is valid$")
    public void i_have_a_salesforce_Connection() {

        assertNotNull(enterpriseConnection);
    }

    @Given("^the owner is in region \"([^\"]*)\"$")
    public void the_owner_is_in_region(String reg) {

        region = reg;
    }

    @Given("^the recordData is held in \"([^\"]*)\"$")
    public void the_recordData_is_held_in(String record) {

       /** Path resourceDirectory = Paths.get("src", "test", "resources");

        String filePath = resourceDirectory.toAbsolutePath().toString(); **/

        filePath=  LoadProperties.getTestResourcesDirectory();
        yamlRecord = filePath + File.separator + Constants.RECORD_ACCESS_DEFS_FLDR + File.separator + record;

        Path path = Paths.get(yamlRecord);

        boolean bExists = Files.exists(path);
        log.debug("Seeking csv structure  yaml file " + yamlRecord + " result = " + bExists);

        Assert.assertTrue(yamlRecord, bExists);
    }

    @Given("^the csv structure which defines the data types is held in \"([^\"]*)\"$")
    public void the_csv_structure_which_defines_the_data_types_is_held_in(String csvStructure) {

        csvStructureYaml =
                filePath + File.separator + Constants.RECORD_ACCESS_DEFS_FLDR+ File.separator + csvStructure;

        Path path = Paths.get(csvStructureYaml);

        boolean bExists = Files.exists(path);
        log.debug("Seeking csv structure  yaml file " + csvStructureYaml + " result = " + bExists);

        Assert.assertTrue(csvStructureYaml, bExists);
    }

    @Given(
            "^I have defined a File with all the accounts \"([^\"]*)\" that I wish to print the access levels for$")
    public void
    i_have_defined_a_File_with_all_the_accounts_that_I_wish_to_print_the_access_levels_for(
            String users) {

        Path resourceDirectory = Paths.get("src", "test", "resources");

        String filePath = resourceDirectory.toAbsolutePath().toString();
        yamlTestAccounts = filePath + File.separator + Constants.RECORD_ACCESS_DEFS_FLDR+ File.separator + users;

        Path path = Paths.get(yamlTestAccounts);

        boolean bExists = Files.exists(path);
        log.debug("Seeking yaml test accounts file " + yamlTestAccounts + " result = " + bExists);

        Assert.assertTrue(yamlTestAccounts, bExists);
    }

    @When("^I trigger generation of the records to create \"([^\"]*)\"$")
    public void i_trigger_generation_of_the_records_to_create(String outputFileName) throws IOException{

        Path resourceDirectory = Paths.get("src", "test", "resources");
        String filePath = resourceDirectory.toAbsolutePath().toString();
        yamlGeneratedFile =
                filePath + File.separator + Constants.RECORD_ACCESS_DEFS_FLDR + File.separator + outputFileName;

        // Assign the yaml file
        log.debug("The file we expect =" + yamlGeneratedFile);

        YamlFileGenProperties props = new YamlFileGenProperties();

        props.setEtlEntity(this.etlEntity);
        props.setOwner(this.owner);
        props.setType(this.type);
        props.setRegion(this.region);
        props.setYamlFileRecords(this.yamlRecord);
        props.setEnterpriseConnection(enterpriseConnection);
        props.setYamlFileCSVStructure(csvStructureYaml);
        props.setYamlGeneratedFile(yamlGeneratedFile);
        props.setYamlTestAccountsFile(yamlTestAccounts);
        props.setUserRole(userRole);

        fileToTest = AccessSettingsGeneratorSteps.generateYamlFileForOwner(props);
        assertNotNull(fileToTest, fileToTest);
    }

    @Then("^I that file should exist and be serialisable against the structure required$")
    public void i_that_file_should_exist_and_be_serialisable_against_the_structure_required()
            throws IOException {

        Path path = Paths.get(fileToTest);

        boolean bExists = Files.exists(path);

        Assert.assertTrue("File Exists: " + fileToTest, bExists);
    }
}
