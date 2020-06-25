package com.test.cle.salesforce.serenity.steps.sfsbusinessprocess;

import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.yamlelements.security.CustomFormField;
import com.test.cle.salesforce.yamlelements.security.FormDef;
import com.test.cle.salesforce.yamlelements.security.FormField;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.ensure.web.ElementLocated;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class LeadAndEventFormSteps {

    private static Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());

  /**  @Step
    public static FormDef getFormModel(String yamlFile) {

        FormDef form = null;
        try {
            Yaml yaml = new Yaml (new Constructor (FormDef.class));

            InputStream inputStream = new FileInputStream (new File (yamlFile));

            form = yaml.load (inputStream);

            if (form == null) {
                log.error ("Form is null for" + yamlFile);
            }
        } catch (IOException e) {
            log.error (Constants.SERIALISATION_FAILURE + yamlFile, e);
        }

        return form;
    }**/

    public static List<CustomFormField> getExpectedCustomFields(FormDef form) {

        List<CustomFormField> allFields = form.getCustomFormFields ();
        List<CustomFormField> customFields = new ArrayList ();

        log.debug ("********CustomFields **********");
        Iterator<CustomFormField> i;
        for (i = allFields.iterator (); i.hasNext (); ) {
            Object item = i.next ();
            CustomFormField myField = (CustomFormField) item;

            log.debug ("CustomField DeveloperName " + myField.getDeveloperName ());
            customFields.add (myField);

        }
        log.debug ("Number of CustomFields=" + customFields.size ());
        return customFields;

    }

    public static List<FormField> getExpectedStandardFields(FormDef form) {

        List allFields = form.getFormFields ();
        List<FormField> standardFields = new ArrayList<FormField> ();

        Iterator<FormField> i;
        for (i = allFields.iterator (); i.hasNext (); ) {
            Object item = i.next ();
            FormField myField = (FormField) item;


            log.debug ("StandardField " + myField.getName ());
            standardFields.add (myField);

        }

        log.debug ("Number of StandardFields=" + standardFields.size ());
        return standardFields;
    }

    public static void checkPicklistExistsWithValues(FormField standardField) {

        // Firstly is the picklist displayed
        theActorInTheSpotlight ()
                .attemptsTo (Ensure.that (ElementLocated.by (standardField.getName ())).isDisplayed ());

        // iF option values are expected check that they exist
        Optional optionalField = Optional.ofNullable (standardField.getOptionValues ());

        if (optionalField.isPresent ()) {
            log.error ("\r\n\r\n*****Standard Picklist Field: ********" + standardField.getName ());
            theActorInTheSpotlight ()
                    .attemptsTo (
                            Ensure.that (By.name (standardField.getName ()))
                                    .values ()
                                    .contains (standardField.getInputTerm ().toString ()));
        } else {
            log.error (Constants.FRM_OPTION_PL_WITH_NO_VALUES + standardField.getInputTerm ().toString ());
        }
    }


    public static void checkTextFieldExists(FormField standardField) {

        // Firstly is the picklist displayed
        theActorInTheSpotlight ()
                .attemptsTo (Ensure.that (ElementLocated.by (standardField.getName ())).isDisplayed ());

    }

}
