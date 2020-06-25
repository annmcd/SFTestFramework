package com.test.cle.salesforce.serenity.steps.common;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.LoginResult;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.testutils.LoadProperties;
import com.test.cle.salesforce.utils.Secret;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class SalesforceConnectionSteps {

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    Path resourceDirectory = Paths.get("src", "test", "resources");

    @Step
    public static PartnerConnection partnerLogin() {

        String targetEnvironment = System.getProperty("target_salesforce_environment");
        PartnerConnection conn = null;
        try {

         String file= LoadProperties.getPropertiesFileNameForEnvironment(targetEnvironment);

            log.error("Path of the properties file = " + file);

            log.error("target environment=" + targetEnvironment);

            Properties propertyFileSettings = LoadProperties.getProperties(file);
            String authEndPoint = propertyFileSettings.getProperty("endPoint");

            log.error("end Point" + authEndPoint);
            ConnectorConfig config = new ConnectorConfig();
            String userName = propertyFileSettings.getProperty("user");
            config.setUsername(userName);

            String key = Secret.decrypt(propertyFileSettings.getProperty("key"));
            config.setPassword(key);
            log.error("userName = " + userName + " key=" + key);
            config.setAuthEndpoint(authEndPoint);
            String use_proxy = propertyFileSettings.getProperty("use_proxy");
            if(use_proxy.equalsIgnoreCase ("true")) {
                config.setProxy (
                        propertyFileSettings.getProperty ("proxyServer"),
                        Integer.parseInt (propertyFileSettings.getProperty ("proxyPort")));
            }
            log.error("auth end point after config = " + config.getAuthEndpoint());

        } catch (Exception e) {
            log.error("stepdef failed! {}", e);
        }
        return conn;
    }

    @Step
    public static MetadataConnection getMetadataConnection(EnterpriseConnection eConnection) {

        MetadataConnection mconnection = null;
        try {
            String targetEnvironment = System.getProperty("target_salesforce_environment");
            String file= LoadProperties.getPropertiesFileNameForEnvironment(targetEnvironment);
            Properties propertyFileSettings = LoadProperties.getProperties(file);

            LoginResult lr =
                    eConnection.login(
                            eConnection.getConfig().getUsername(), eConnection.getConfig().getPassword());

            ConnectorConfig connConfig = new ConnectorConfig();
            connConfig.setSessionId(lr.getSessionId());
            connConfig.setServiceEndpoint(lr.getMetadataServerUrl());
            String use_proxy = propertyFileSettings.getProperty("use_proxy");
            if(use_proxy.equalsIgnoreCase ("true")) {
                connConfig.setProxy (
                        propertyFileSettings.getProperty ("proxyServer"),
                        Integer.parseInt (propertyFileSettings.getProperty ("proxyPort")));
            }
            mconnection = new MetadataConnection(connConfig);

            log.debug(mconnection.getSessionHeader());

        } catch (Exception e) {
            log.error("getMetadataConnectionSetep failed!  {}", e);
        }
        return mconnection;
    }

   /** @Step
    public String getEnvironmentPropertyFile(String environment) {

        String path = resourceDirectory.toAbsolutePath().toString();
        return path + "/" + environment + "_configuration" + ".properties";

    }**/

    @Step
    public String getBulDataConfigFile(String environment, String yamlFile) {

        String path =
                resourceDirectory.toAbsolutePath().toString() + File.pathSeparator + "sfbulkdatadefs";
        String file = path + File.separator + environment + "_" + yamlFile.trim();
        log.debug("yaml config file" + file);
        return file;
    }

    @Step
    public Properties getPropertiesObject(String environment) throws IOException {

        String path = resourceDirectory.toAbsolutePath().toString();
        String file = path + "/" + environment + "_configuration" + ".properties";
        return com.test.cle.salesforce.testutils.LoadProperties.getProperties(file);
    }

    @Step
    public Properties getThisPropertiesObject(String file) throws IOException {

        return com.test.cle.salesforce.testutils.LoadProperties.getProperties(file);
    }

    @Step
    public EnterpriseConnection getSalesforceEnterpriseConnection(String environment) {

        EnterpriseConnection eConnection = null;
        ConnectorConfig config = null;
        try {

            String path = resourceDirectory.toAbsolutePath().toString();
            String sFile = path + "/" + environment + "_configuration" + ".properties";
            Properties propertyFileSettings = getPropertiesObject(environment);
            String authEndPoint = propertyFileSettings.getProperty("endPoint");
            authEndPoint = authEndPoint.replaceAll("/u/", "/c/");

            log.debug("The properties file to load = " + sFile);
            config = new ConnectorConfig();
            String userName = propertyFileSettings.getProperty("user");
            config.setUsername(userName);
            String key = Secret.decrypt(propertyFileSettings.getProperty("key").trim());

            config.setPassword(key);
          //  log.debug("userName = " + userName + " key=" + key);
            config.setAuthEndpoint(authEndPoint);
            String useProxy=propertyFileSettings.getProperty ("use_proxy");
            if(useProxy.equalsIgnoreCase ("true")) {
                config.setProxy (
                        propertyFileSettings.getProperty ("proxyServer"),
                        Integer.parseInt (propertyFileSettings.getProperty ("proxyPort")));
            }
            eConnection = new EnterpriseConnection(config);
        } catch (Exception e) {
            log.error("step failed!  {}", e);
        }
        return eConnection;
    }

     @Step
    public void logoutEnterpriseConnection(EnterpriseConnection conn)  {
        try {
            if (conn != null) {
                conn.logout ();
            }
        }catch (ConnectionException e){
            log.error("logout",e);
        }

    }

    @Step
    public void logoutPartnerConnection(PartnerConnection conn)  {
        try {
            if (conn != null) {
                conn.logout ();
            }
        }catch (ConnectionException e){
            log.error("logout",e);
        }

    }


    @Step
    public PartnerConnection getSalesforcePartnerConnection(String environment) {

        PartnerConnection pConnection = null;
        try {

            String path = resourceDirectory.toAbsolutePath().toString();
            String sFile = path + "/" + environment + "_configuration" + ".properties";
            Properties propertyFileSettings = getPropertiesObject(environment);
            String authEndPoint = propertyFileSettings.getProperty("endPoint");

            log.debug("The properties file to load = " + sFile);

            ConnectorConfig config = new ConnectorConfig();
            String userName = propertyFileSettings.getProperty("user");
            config.setUsername(userName);

            String key = Secret.decrypt(propertyFileSettings.getProperty("key"));
            config.setPassword(key);
            log.debug("userName = " + userName);
            config.setAuthEndpoint(authEndPoint);
            String useProxy=propertyFileSettings.getProperty ("use_proxy");
            if(useProxy.equalsIgnoreCase ("true")) {
                config.setProxy (
                        propertyFileSettings.getProperty ("proxyServer"),
                        Integer.parseInt (propertyFileSettings.getProperty ("proxyPort")));
            }
            pConnection = new PartnerConnection(config);
        } catch (Exception e) {
            log.error(Constants.STEP_FAILED_IND, e);
        }
        return pConnection;
    }
}
