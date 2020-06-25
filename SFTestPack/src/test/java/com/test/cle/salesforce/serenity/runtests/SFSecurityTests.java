package com.test.cle.salesforce.serenity.runtests;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.thucydides.core.annotations.WithTag;
import org.junit.runner.RunWith;

/**
 * Required class for runtime execution on pipeline
 */
@WithTag("SFSecurity")
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features/sfsecurity"},
        tags = {"not @ignore"},
        glue = {"com.test.cle.salesforce.serenity.stepdefinitions"})
public class SFSecurityTests {
}
