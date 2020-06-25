package com.test.cle.salesforce.serenity.runtests;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.thucydides.core.annotations.WithTag;
import org.junit.runner.RunWith;

/**
 * Required for excution on pipeline Run tests regardless of tags.
 */
@WithTag("SFPermissionsManager")
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features/sfpermissionsmanager"},
        tags = {"not @ignore"},
        glue = {"com.test.cle.salesforce.serenity.stepdefinitions"})
public class SFPermissionsManagerTests {
}
