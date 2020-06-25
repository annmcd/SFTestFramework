package com.test.cle.salesforce.serenity.steps.sfsecurity;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.testutils.Constants;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.lang.invoke.MethodHandles;


public class VerifyGroups {

    static Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());

    //mode true for log only false for assertion
    @Step
    public static boolean checkGroupExists(EnterpriseConnection enterpriseConnection,
                                  String developerName, String type) {
        boolean result=false;
        String soQlQuery =
                "select DeveloperName from group where DeveloperName =" + "'" +
                        developerName + "' AND type='" + type + "'";

        log.debug ("SoQL query for group check =  " + soQlQuery);

        QueryResult qr = null;
        try {
            qr = enterpriseConnection.query (soQlQuery);
        } catch (ConnectionException e) {
            log.error (e);
        }
        if (qr.getSize () > 0) {
            result = true;

        }
        return result;
    }

    @Step
    public static void assertFailures(StringBuffer buff) {

        if(buff.length ()>0){
            log.error (buff.toString ());
            Assert.assertTrue (buff.toString (),buff.length ()==0);
            buff.delete(0, buff.length());
        }
    }

}



