package com.test.cle.salesforce.serenity.steps.common;

import com.sforce.soap.metadata.CustomObjectTranslation;
import com.sforce.soap.metadata.Metadata;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.ReadResult;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.testutils.Constants;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;

public class TranslationValidator {

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Step
    public static CustomObjectTranslation getCutomObjectTranslation(
            MetadataConnection metadataConnection, String type) {

        CustomObjectTranslation objectTranslation = null;
        try {

            log.debug("gettting translation for " + type);
            ReadResult result =
                    metadataConnection.readMetadata("CustomObjectTranslation", new String[]{type});
            for (Metadata data : result.getRecords()) {
                objectTranslation = (CustomObjectTranslation) data;
                log.debug("iterating though customObjectTranlsation records");
            }
        } catch (ConnectionException e) {
            log.error(Constants.STEP_FAILED_IND, e);
        }
        return objectTranslation;
    }
}
