package com.test.cle.salesforce.serenity.runtests;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.thucydides.core.annotations.WithTag;
import org.junit.runner.RunWith;


@WithTag("SFBusinessProcess")
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features/sfbusinessprocess"},
        tags = {"not @ignore"},
        glue = {"com.test.cle.salesforce.serenity.stepdefinitions"})
public class SFBusinessProcessTests {
}
