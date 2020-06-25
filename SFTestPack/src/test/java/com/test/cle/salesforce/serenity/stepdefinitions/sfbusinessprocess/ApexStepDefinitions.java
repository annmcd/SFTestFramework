package com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess;
import com.test.cle.salesforce.serenity.steps.sfsbusinessprocess.ApexSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;

import static com.test.cle.salesforce.serenity.steps.sfsbusinessprocess.ApexSteps.*;


final public class ApexStepDefinitions {

    final private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());


   @Steps
   private ApexSteps apexSteps;

    @Given("^I have a jwt flow connected$")
    public void i_have_a_jwt_flow_connected() {

        ApexSteps.setup();
        boolean ret= authoriseOrg();

        Assert.assertTrue("sfdx authorisation ?",ret);

        log.debug("The value of ret=" + ret);

    }

    @When("^I run tests in suite \"([^\"]*)\"$")
    public void i_run_tests_in_suite(String suiteName) {

        Assert.assertTrue("Apex Test Execution Successful for suite "+ suiteName +" ?", apexSteps.runAllTests());
    }


    @Then("^I expect them all to pass$")
    public void i_expect_them_all_to_pass() {

    }



}
