package com.test.cle.salesforce.serenity.steps.common;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;

public class SalesForceQuerySteps {

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Step
    public static boolean execQuery(String soQLQuery, PartnerConnection partnerConnection) {

        log.debug(" enterprise session header " + partnerConnection.getSessionHeader());

        boolean b = true;

        log.debug("Get ResultsSet for  " + soQLQuery);

        try {
            log.debug("Getting Query Results ");
            partnerConnection.query(soQLQuery);

            log.debug("Got query result ");
        } catch (Exception e) {

            b = false;

            log.error("step failed! getResultsSet {}", e);
        }

        return b;
    }

    @Step
    public static int getQueryRowCount(String soQLQuery, PartnerConnection partnerConnection) {

        log.debug("enterprise session header " + partnerConnection.getSessionHeader());
        QueryResult qr = null;
        int size = 0;

        log.debug("Get ResultsSet for  " + soQLQuery);

        try {
            log.debug("Getting Query Results ");
            qr = partnerConnection.query(soQLQuery);

            size = qr.getSize();
            log.debug("Got query result size of resultset = " + size);
        } catch (Exception e) {

            log.error("step failed! getResultsSet {}", e);
        }

        return size;
    }
}
