package com.test.cle.salesforce.serenity.stepdefinitions.common;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.test.cle.salesforce.testutils.LoadProperties;
import com.test.cle.salesforce.utils.Secret;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


public class SalesforceEnterprisePartnerConnections {

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    static String targetEnvironment;
    static EnterpriseConnection enterpriseConn;

    @Step
    public static EnterpriseConnection login() {

        targetEnvironment = System.getProperty("target_salesforce_environment");
        try {
            Path resourceDirectory = Paths.get("src", "test", "resources");
            String path = resourceDirectory.toAbsolutePath().toString();
            String sFile = path + "/" + targetEnvironment + "_configuration" + ".properties";

            log.debug("Path of the properties file = " + path);

            log.debug("target environment=" + targetEnvironment);

            Properties propertyFileSettings = LoadProperties.getProperties(sFile);
            String authEndPoint = propertyFileSettings.getProperty("endPoint");

            authEndPoint = authEndPoint.replaceAll("/u/", "/c/");

            log.debug("end Point" + authEndPoint);
            ConnectorConfig config = new ConnectorConfig();
            String userName = propertyFileSettings.getProperty("user");
            config.setUsername(userName);

            String key = Secret.decrypt(propertyFileSettings.getProperty("key"));
            config.setPassword(key);

            config.setAuthEndpoint(authEndPoint);
            String useProxy=propertyFileSettings.getProperty ("use_proxy");
            if(useProxy.equalsIgnoreCase ("true")) {
                config.setProxy (
                        propertyFileSettings.getProperty ("proxyServer"),
                        Integer.parseInt (propertyFileSettings.getProperty ("proxyPort")));
            }
            enterpriseConn = new EnterpriseConnection(config);

        } catch (Exception e) {
            log.error("stepdef failed! {}", e);
        }

        return enterpriseConn;
    }




    @Step
    public static void logout(EnterpriseConnection conn) throws ConnectionException {
        conn.logout ();
    }

}
