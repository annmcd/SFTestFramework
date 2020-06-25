package com.test.cle.salesforce.serenity.stepdefinitions.sfbusinessprocess;

import com.test.cle.salesforce.serenity.api.service.Janitor;
import cucumber.api.java.After;

public class Hooks {

    @After
    public void after() {
        Janitor.getInstance().cleanup();
    }

}
