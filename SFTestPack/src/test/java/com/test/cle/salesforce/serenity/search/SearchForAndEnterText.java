package com.test.cle.salesforce.serenity.search;


import com.test.cle.salesforce.yamlelements.security.*;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

public class SearchForAndEnterText {

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static Performable inputField(FormField field) {

        String searchField = field.getName();
        String term = null;
        By SEARCH_FIELD = By.name(searchField);
        log.debug("Searching for field " + searchField);

        Optional optional = Optional.ofNullable(field.getInputTerm());

        if (optional.isPresent()) {

            log.debug("Found Test Entry value for " + field.getName());

            term = remvoveTag(field.getInputTerm().toString());
            log.error("The value " + term + " will be entered into " + field.getName());
            return Task.where(
                    "{0} attempts to search for #field " + searchField + " and enter text #term" + term,
                    Clear.field(SEARCH_FIELD),
                    Enter.theValue(term).into(SEARCH_FIELD),
                    Click.on(SEARCH_FIELD))
                    .with("term")
                    .of(term);

        } else {

            log.error(
                    "\r\nCheck Yaml, No declarative text was specified for entry into field " + searchField);
            return Task.where(
                    "{0} attempts to search for field " + searchField,
                    Clear.field(SEARCH_FIELD),
                    Click.on(SEARCH_FIELD));
        }
    }


    public static Performable customInputField(CustomFormField field, String searchField) {

        String term = null;
        By SEARCH_FIELD = By.name(searchField);
        log.debug("Searching for field " + searchField);

        Optional optional = Optional.ofNullable(field.getInputTerm());

        if (optional.isPresent()) {

            log.debug("Found Test Entry value for " + field.getDeveloperName());

            term = remvoveTag(field.getInputTerm().toString());
            log.error("The value " + term + " will be entered into " + searchField);
            return Task.where(
                    "{0} attempts to search for #field " + searchField + " and enter text #term" + term,
                    Clear.field(SEARCH_FIELD),
                    Enter.theValue(term).into(SEARCH_FIELD),
                    Click.on(SEARCH_FIELD))
                    .with("term")
                    .of(term);

        } else {

            log.error(
                    "\r\nCheck Yaml, No declarative text was specified for entry into field " + searchField);
            return Task.where(
                    "{0} attempts to search for field " + searchField,
                    Clear.field(SEARCH_FIELD),
                    Click.on(SEARCH_FIELD));
        }
    }


    private static String remvoveTag(String s) {

        s = s.replace("Optional[", "");
        s = s.replace("]", "");
        return s;
    }
}
