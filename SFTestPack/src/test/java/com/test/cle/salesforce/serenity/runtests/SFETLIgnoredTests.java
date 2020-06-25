package com.test.cle.salesforce.serenity.runtests;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;
import net.thucydides.core.annotations.WithTag;

@WithTag("SFETL")
        @RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features/sfetl"},
        tags = {"@CLEIgnore and @SFETL"},
        glue = {"com.test.cle.salesforce.serenity.stepdefinitions.sfetl"})
public class SFETLIgnoredTests {
}
