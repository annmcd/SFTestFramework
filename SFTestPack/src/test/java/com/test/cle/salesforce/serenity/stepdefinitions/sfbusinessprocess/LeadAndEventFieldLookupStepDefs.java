package com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.tooling.SoapConnection;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.navigation.NavigateTo;
import com.test.cle.salesforce.serenity.search.SearchForAndEnterText;
import com.test.cle.salesforce.serenity.steps.common.ToolingConnectionSteps;
import com.test.cle.salesforce.serenity.steps.sfsbusinessprocess.LeadAndEventFormSteps;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.yamlelements.security.*;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.annotations.Steps;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class LeadAndEventFieldLookupStepDefs {



    private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private SoapConnection toolingConnection;
    private String entityName = null;
    private String yamlFile = null;
    // custom salesforce fields
    private Map customFieldsDeveloperNameIDMap = new HashMap();
    // expected fields
    private List<FormField> expectedStandardFields;
    private List<CustomFormField> expectedCustomFields;

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @When("^I have a salesforce tooling connection$")
    public void i_have_a_salesforce_tooling_connection() throws ConnectionException, IOException {

        PartnerConnection partnerConnection = ToolingConnectionSteps.getPartnerConnection();

        Assert.assertNotNull(Constants.PARTNER_CON_CHECK, partnerConnection);
        log.debug("PartnerConnection session header" + partnerConnection.getSessionHeader());
    }

    @Given("^I have a list of custom fields that I wish to verify for \"([^\"]*)\" by DeveloperName$")
    public void i_have_a_list_of_custom_fields_that_I_wish_to_verify_for_by_DeveloperName(
            String entity) throws IOException, ConnectionException {
        entityName = entity;
        toolingConnection = ToolingConnectionSteps.getToolingConnection(entityName);
        log.debug("Tooling Connection Session Header " + toolingConnection.getSessionHeader());
        Assert.assertNotNull("ToolingConnection is valid? ", toolingConnection);
    }

    /**
     * There are both mandatory and non mandatory fields on the Lead Form, verify that some of each
     * exist
     *
     * @param configFileName
     */
    @Given("^All the expected custom and standard fields are recorded in \"([^\"]*)\"$")
    public void all_the_settings_are_recorded_in(String configFileName) {

        Path resourceDirectory = Paths.get("src", "test", "resources");

        // serialise the yaml to get an object model of the Expected form properties
        configFileName = resourceDirectory + "/leadandeventDefs/" + configFileName;
        Yaml yaml = new Yaml(new Constructor (FormDef.class));

        try {
            FormDef leadForm;
            try (InputStream inputStream = new FileInputStream (new File (configFileName))) {

                leadForm = yaml.load (inputStream);

                // FormDef leadForm = LeadAndEventFormSteps.getFormModel(configFileName);
                Assert.assertNotNull ("LeadForm Serialised OK? " + configFileName, leadForm);
                yamlFile = configFileName;
                List fields = leadForm.getFormFields ();
                Assert.assertTrue ("Fields have been defined in yaml? ", fields.size () > 0);

                expectedCustomFields = LeadAndEventFormSteps.getExpectedCustomFields (leadForm);
                Assert.assertTrue (
                        "Custom Fields have been defined in yaml? " + configFileName,
                        expectedCustomFields.size () > 0);

                expectedStandardFields = LeadAndEventFormSteps.getExpectedStandardFields (leadForm);
                Assert.assertTrue (
                        "Standard Fields have been defined in yaml? " + configFileName,
                        expectedStandardFields.size () > 0);

                expectedCustomFields = LeadAndEventFormSteps.getExpectedCustomFields (leadForm);
                Assert.assertTrue (
                        "Custom Fields have been defined in yaml? " + configFileName,
                        expectedCustomFields.size () > 0);
            }
        } catch (IOException e) {
            log.error (e);
        }
    }

    @Given("^The location of the URL has been pre configured$")
    public void the_location_of_the_URL_has_been_pre_configured() {
    }

    @When("^I query salesforce to retrieve the Id of Custom Fields$")
    public void i_query_salesforce() {
        // Query the custom fields on the Salesforce Entity via the Tooling API
        String soql =
                "Select Id, DeveloperName from CustomField where  "
                        + "TableEnumOrId = "
                        + "'"
                        + entityName
                        + "'";
        try {
            toolingConnection.query(soql);
        } catch (Exception e) {
            // soap fault will be thrown anyhow but SOAP Response will be accessible in the trace file of
            // the ConnectorConfig
            log.debug("Soap fault occured");

        } finally {
            customFieldsDeveloperNameIDMap = ToolingConnectionSteps.doHandleResponse();

            Assert.assertNotNull("Form Fields Found? ", customFieldsDeveloperNameIDMap);

            Assert.assertTrue(
                    "Number of Expected Custom Fields on Lead?", customFieldsDeveloperNameIDMap.size() > 0);
        }
    }

    @When("^(.*) Lookup both the standard and custom fields to verify they exist$")
    public void i_Lookup_both_the_standard_and_custom_fields_to_verify_they_exist(String actor) {

        // In our case the actor is I but we could accomomdate many different categories of users/actors
        theActorCalled(actor).attemptsTo(NavigateTo.theWebToLeadPage());
    }

    @Then("^I can verify that all required Fields exist$")
    public void i_can_verify_that_all_required_Fields_exist() throws InterruptedException {

        processStandardFields();

        processCustomFields();
    }

    public void processStandardFields() throws InterruptedException {

        String errMsg = "";
        // Process the standard fields first
        Iterator<FormField> i;
        Thread.sleep(3000);

        log.debug(
                "\r\n[################# Standard Fields: "
                        + yamlFile
                        + " against target Form ######################\r\n");

        for (i = expectedStandardFields.iterator(); i.hasNext(); ) {

            FormField standardField = null;

                Object item = i.next();
                standardField = (FormField) item;
                String controlType = standardField.getControlType();

                switch (controlType) {
                    case Constants.TEXT: // standard text fields
                        log.debug(
                                "\r\n\r\n*********Standard Field Sought on Page is ***********"
                                        + standardField.getName());
                        theActorInTheSpotlight().attemptsTo(SearchForAndEnterText.inputField(standardField));
                        break;
                    case Constants.PICKLIST:
                        log.error(" picklist field " + standardField.getName());
                        LeadAndEventFormSteps.checkPicklistExistsWithValues(standardField);
                        break;
                    case Constants.RADIO:
                        log.debug("//TODO Radio Button implementation");
                        break;
                    default:
                        log.debug(Constants.FRM_CONTROL_TYPE_NOT_FOUND + " " + standardField.getName());
                }

        }
    }

    public void processCustomFields() {
        String errMsg = "";
        // Process custom fields
        Iterator<CustomFormField> i;

        log.debug(
                "\r\n[################# Standard Fields: "
                        + yamlFile
                        + " against target Form ######################\r\n");
        CustomFormField customFormField = null;
        for (i = expectedCustomFields.iterator(); i.hasNext(); ) {
            try {

                Object item = i.next();
                customFormField = (CustomFormField) item;
                String controlType = customFormField.getControlType();
                String fieldName = customFormField.getDeveloperName();
                log.error("Searching for key" + fieldName);
                if (customFieldsDeveloperNameIDMap.containsKey(fieldName)) {

                    String fieldId = getKeyValue(fieldName);
                    log.error("Custom Field found " + fieldName + " lookup value on page " + fieldId);
                    switch (controlType) {
                        case Constants.TEXT: // standard text fields
                            log.debug(
                                    "\r\n\r\n*********Standard Field Sought on Page is ***********"
                                            + customFormField.getDeveloperName());
                            theActorInTheSpotlight()
                                    .attemptsTo(SearchForAndEnterText.customInputField(customFormField, fieldId));
                            break;
                        default:
                            log.error(
                                    Constants.FRM_CONTROL_TYPE_NOT_FOUND
                                            + " "
                                            + customFormField.getDeveloperName()
                                            + " "
                                            + controlType);
                    }
                } else {
                    log.error("EXPECTED_CUSTOM_FIELD_NOT_FOUND_IN_SALESFORCE_MAP " + fieldName);
                }
            } catch (NullPointerException e) {
                String message =
                        "ERROR_OCCURRED_EVALUATING_YAML "
                                + yamlFile
                                + "\r\n Field: "
                                + customFormField.getDeveloperName()
                                + " Expected Control Type = "
                                + customFormField.getControlType();
                errMsg += message;
                log.error(errMsg, e);
            }
        }
    }

    private String getKeyValue(String key) {

        String keyValue = null;
        // list of custom fields in form field
        Iterator it = customFieldsDeveloperNameIDMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            // here we get the salesforce values DeveloperName/Id
            Object obj = pair.getKey();
            Object objValue = pair.getValue();

            if (key.equalsIgnoreCase(obj.toString())) {
                keyValue = objValue.toString().substring(0, 15);
            }
        }
        return keyValue;
    }

    /**@After
    public void logout()  {
        salesforceConnectionSteps.logoutEnterpriseConnection ( enterpriseConnection);
    }**/
}
