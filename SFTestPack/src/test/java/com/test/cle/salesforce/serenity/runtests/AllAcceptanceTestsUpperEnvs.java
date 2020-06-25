package com.test.cle.salesforce.serenity.runtests;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Required for excution on pipeline Run tests regardless of tags.
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        tags = {"not @notPreprod", "not @ignore"},
        glue = {"com.test.cle.salesforce.serenity.stepdefinitions"})
public class AllAcceptanceTestsUpperEnvs {
}
