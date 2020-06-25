package com.test.cle.salesforce.serenity.navigation;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class NavigateTo {

    public static Performable theWebToLeadPage() {
        return Task.where(
                "{0} opens the web to lead home page", Open.browserOn().the(WebToLeadHomePage.class));
    }
}
