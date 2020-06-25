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
        glue = {"com.test.cle.salesforce.serenity.stepdefinitions"},
        tags = {"not @slow", "not @ignore"})
public class AllFastTests {
}
