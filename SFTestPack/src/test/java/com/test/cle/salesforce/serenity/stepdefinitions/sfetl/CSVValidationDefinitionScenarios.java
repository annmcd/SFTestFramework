package com.test.cle.salesforce.serenity.stepdefinitions.sfetl;

import com.test.cle.salesforce.serenity.stepdefinitions.common.BaseDefinition;
import com.test.cle.salesforce.serenity.stepdefinitions.common.BeforeStepsDBConnection1;
import com.test.cle.salesforce.serenity.steps.sfetl.CSVValidationSteps;
import com.test.cle.salesforce.serenity.steps.sfetl.DBStructureSteps;
import com.test.cle.salesforce.testutils.Constants;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.test.cle.salesforce.serenity.steps.sfetl.CSVValidationSteps.setCSVRecords;

public class CSVValidationDefinitionScenarios extends BaseDefinition {

    private static Connection connectionStaging;

    // Declare the steps that are needed by this definition
    private static ResultSet rsStagingTable;
    private static String csvFile;
    private static String exportFolder = null;
    private static String environment = null;

    @Steps
    static  CSVValidationSteps csvValidationSteps;

    /**
     * Run this cucumber @Before step in advance of performing the gherkin steps we require our
     * database connections to staging and warehouse
     */

    public static void makeDBConnections() throws IOException {

        environment = System.getProperty("target_salesforce_environment");

        BeforeStepsDBConnection1.get_connection_forEnv(environment);

        connectionStaging = BeforeStepsDBConnection1.getStagingConnection();

        exportFolder = CSVValidationSteps.getExportDirectory(environment);

        log.debug("Folder location of the csv files is returned as " + exportFolder);
    }


    //passive
    @Given("^I  have an ETL Export Facility$")
    public void i_have_an_ETL_Export_Facility() {
        environment = System.getProperty("target_salesforce_environment");
        exportFolder = CSVValidationSteps.getExportDirectory(environment);
        log.debug("Folder location of the csv files is returned as " + exportFolder);
    }

    @Given(
            "^the staging table \"(.*?)\" is poplulated with records via etlimport and ordered by column \"(.*?)\"$")
    public void the_staging_table_is_poplulated_with_records_via_etlimport_and_ordered_by_column(
            String tableName, String columnName) {

        String query = "select * from " + tableName + " order by " + columnName;

        rsStagingTable = DBStructureSteps.getResultSetFromQuery(query, connectionStaging);

        Assert.assertNotNull(rsStagingTable);
    }

    @When("^I trigger etlexport$")
    public void i_trigger_etlexport() throws Throwable {

        // Passive action
    }

    @Then("^I expect to have a csv file \"([^\"]*)\" created in the environment csv export folder$")
    public void i_expect_to_have_a_csv_file_created_in_the_environment_csv_export_folder(
            String csvFileName) throws IOException {

        String filePath = exportFolder + File.separator + csvFileName.trim();
        csvFile = filePath;

        log.debug("Verify existence of CSV File " + filePath);


        // 1st does the file exist
        boolean exists = CSVValidationSteps.fileExists(filePath);

        if (!exists) {
            log.error("File does not exist " + filePath);

            Assert.fail("File does not exist " + filePath);
        }


        Assert.assertTrue(Constants.CSV_FILE_ENCODING, CSVValidationSteps.verifyEncoding(filePath));

        setCSVRecords(filePath);
    }

    // 3 check column data values for no leading or trailing spaces
    @Then("^the output CSV files column values contain no leading or trailing spaces$")
    public void the_output_CSV_files_column_values_contain_no_leading_or_trailing_spaces() {

        log.debug("Leading and trailing spaces check, Seeking file " + csvFile);

        CSVValidationSteps.assertCSVHasNoLeadingTrailingSpaces();
    }

    // 4 the rows have no trailing commas
    @Then("^the output CSV files last column contains no trailing comma$")
    public void the_output_CSV_files_last_column_contains_no_trailing_comma() {

        log.debug("Trailing spaces check, Seeking file " + csvFile);

        CSVValidationSteps.assertCSVHasNoRowTrailingComma();
    }

    @Then(
            "^the output CSV files contain data and not just data headers where data is required \"([^\"]*)\"$")
    public void the_output_CSV_files_contain_data_and_not_just_data_headers_where_data_is_required(
            String dataRequired) {

        if (Boolean.valueOf(dataRequired.trim())) {
            Assert.assertTrue(
                    "Header and rows count  > 1", CSVValidationSteps.getCSVRowCount(csvFile) > 1);
        }
    }

    @After
    public void close_connections() {

        try {

            if (connectionStaging != null) {
                connectionStaging.close();
            }
            log.debug("Cucumber @After done");

        } catch (SQLException e) {

            log.error("Step definition failed! {}", e);
        }
    }
}
