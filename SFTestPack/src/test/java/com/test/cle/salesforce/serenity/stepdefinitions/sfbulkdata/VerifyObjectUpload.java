package com.test.cle.salesforce.serenity.stepdefinitions.sfbulkdata;

import com.sforce.soap.partner.PartnerConnection;
import com.test.cle.salesforce.serenity.stepdefinitions.common.BaseDefinition;
import com.test.cle.salesforce.serenity.steps.common.SalesForceQuerySteps;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.sfetl.CSVValidationSteps;
import com.test.cle.salesforce.testutils.Constants;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.io.File;

public class VerifyObjectUpload extends BaseDefinition {

    static String environment = System.getProperty("target_salesforce_environment");
    static PartnerConnection partnerConnection = null;
    @Steps
    static SalesforceConnectionSteps salesforceConnectionSteps = null;

    int salesforceRecordSize = 0;
    int priceBookCount = 0;
    long csvRowCount = 0;
    String csvFile = null;

    @Given(
            "^I  have a connection to salesforce Org in a given environment as a configured integration user$")
    public void
    i_have_a_connection_to_salesforce_Org_in_a_given_environment_as_a_configured_integration_user() {

        partnerConnection = salesforceConnectionSteps.getSalesforcePartnerConnection((environment));

        Assert.assertNotNull(Constants.PARTNER_CON_CHECK,partnerConnection);
    }

    @Given("^I have an entity csv file \"([^\"]*)\" in the environment directory$")
    public void i_have_an_entity_csv_file_in_the_environment_directory(String csvFileName) {

        String exportFolder = CSVValidationSteps.getExportDirectory(environment);

        csvFile = exportFolder + File.separator + csvFileName;

        log.debug("Seeking CSV File " + csvFile);

        Assert.assertTrue(CSVValidationSteps.fileExists(csvFile));
    }

    @When("^I trigger sfbulkdata for the environment$")
    public void i_trigger_sfbulkdata_for_the_environment() {
        // Currently Passive

    }

    @Then("^I expect to get a result set when I issue query \"([^\"]*)\"$")
    public void i_expect_to_get_a_result_set_when_I_issue_query(String soqlQuery) {

        Assert.assertTrue(SalesForceQuerySteps.execQuery(soqlQuery, partnerConnection));
    }

    @Then("^I expect to get a count of objects defined in Query \"([^\"]*)\"$")
    public void i_expect_to_get_a_count_of_objects_defined_in_Query(String query) {

        Assert.assertTrue(SalesForceQuerySteps.execQuery(query, partnerConnection));

        salesforceRecordSize = SalesForceQuerySteps.getQueryRowCount(query, partnerConnection);

        Assert.assertTrue(salesforceRecordSize > 0);
    }

    @When("^price book and price book entries have been uploaded via sfbulkdata$")
    public void price_book_and_price_book_entries_have_been_uploaded_via_sfbulkdata() {

        priceBookCount =
                SalesForceQuerySteps.getQueryRowCount("Select id from Pricebook2", partnerConnection);
    }

    @Then("^The price book entry should have a price book$")
    public void the_price_book_entry_should_have_a_price_book() {

        Assert.assertEquals(1, priceBookCount);
    }

    @When(
            "^Products identified in \"([^\"]*)\" have been associated with price book entries in environment directory "
                    + "via SFBulkdata$")
    public void
    products_identified_in_have_been_associated_with_price_book_entries_in_environment_directory_via_SFBulkdata(
            String productCsv) {

        String exportFolder = CSVValidationSteps.getExportDirectory(environment);
        csvFile = exportFolder + File.separator + productCsv;
        String sfQuery = "select id from Product2 where id in (select product2id from pricebookEntry)";

        csvRowCount = CSVValidationSteps.getCSVRowCount(csvFile);

        salesforceRecordSize = SalesForceQuerySteps.getQueryRowCount(sfQuery, partnerConnection);

        Assert.assertTrue(csvRowCount > 0);

        Assert.assertTrue(salesforceRecordSize > 0);
    }

    @Then("^Each product should have a price book entry$")
    public void then_each_product_should_have_a_price_book_entry() {

        Assert.assertEquals(salesforceRecordSize, csvRowCount);
    }

    @Then("^the count of objects should reflect the count of records in the CSV file$")
    public void the_count_of_objects_should_reflect_the_count_of_records_in_the_CSV_file() {

        long csvRowCount = CSVValidationSteps.getCSVRowCount(csvFile);

        Assert.assertTrue(csvRowCount > 0);

        log.debug("Csv Row Count =" + csvRowCount + " VS salesforceRecordSize=" + salesforceRecordSize);

        Assert.assertEquals(csvRowCount, salesforceRecordSize);
    }

    @After
    public void logout()  {
        salesforceConnectionSteps.logoutPartnerConnection (partnerConnection);
    }
}
