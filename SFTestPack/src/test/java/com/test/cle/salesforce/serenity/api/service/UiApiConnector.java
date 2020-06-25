package com.test.cle.salesforce.serenity.api.service;

import com.test.cle.salesforce.serenity.api.objects.Record;
import com.test.cle.salesforce.testutils.LoadProperties;
import com.test.cle.salesforce.utils.Secret;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static io.restassured.specification.ProxySpecification.host;


public class UiApiConnector {

    private static UiApiConnector instance;

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private Response response;
    private String accessToken;
    private String instanceUrl;

    private String key;
    private String userName;
    private String uiApiClientID;
    private String uiApiClientSecret;
    private String uiApiAuthEndpoint;

    private int proxyPort;
    private boolean useProxy;
    private String proxyServer;

    public static synchronized UiApiConnector getInstance(){
        if(instance == null){
            instance = new UiApiConnector();

            try {
                instance.loadProperties();
                instance.login();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private UiApiConnector() {
        // prevent external instantiation
    }

    private void loadProperties () {
        log.debug("Loading properties for UI API CONNECTOR");
        String targetEnvironment = System.getProperty("target_salesforce_environment");
        Path resourceDirectory = Paths.get("src", "test", "resources");
        String path = resourceDirectory.toAbsolutePath().toString();
        String sFile = path + "/" + targetEnvironment + "_configuration" + ".properties";
        log.debug("Path of the properties file = " + path);
        log.debug("target environment=" + targetEnvironment);

        Properties propertyFileSettings = null;
        try {
            propertyFileSettings = LoadProperties.getProperties(sFile);
        } catch (IOException e) {
            System.out.println("Issue Loading properties file: " + sFile);
        }

        userName = propertyFileSettings.getProperty("user");
        key = Secret.decrypt(propertyFileSettings.getProperty("key"));
        uiApiClientID = propertyFileSettings.getProperty("ui_api_client_id");
        uiApiClientSecret = propertyFileSettings.getProperty("ui_api_client_secret");
        uiApiAuthEndpoint = propertyFileSettings.getProperty("ui_api_auth_endpoint");
        useProxy = propertyFileSettings.getProperty("use_proxy").equalsIgnoreCase ("true");
        if (useProxy) {
             proxyServer = propertyFileSettings.getProperty ("proxyServer");
             proxyPort = Integer.parseInt(propertyFileSettings.getProperty("proxyPort"));
        }
    }

    private void login() {

        if(useProxy) {
            RestAssured.proxy = host(proxyServer).withPort(proxyPort);
        }

        Response oauth2Response = requestOauth2Token();
        accessToken = oauth2Response.jsonPath().getString("access_token");
        instanceUrl = oauth2Response.jsonPath().getString("instance_url");
    }

    public void createRecord(Record record) {
        createRecord(record.getApiName(), record.getFields());
    }

    public void createRecord(String objectApiName, JSONObject fields) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("apiName", objectApiName);
        jsonObj.put("fields", fields);

        postRecordRequest(jsonObj);

        String id = response.then().extract().jsonPath().getString("id");
        Context.storeLatestRecordId(id);
    }

    public void deleteRecord(String recordID) {
        Response response = RestAssured.
                given()
                .auth().oauth2(accessToken)
                .when()
                .delete(instanceUrl + "/services/data/v48.0/ui-api/records/" + recordID);

        boolean deleteSuccess = (response.then().extract().statusCode()==204);
        System.out.print("Janitor success: " + deleteSuccess);
    }

    private void postRecordRequest(JSONObject requestBody) {
        response = RestAssured.
                given()
                .auth().oauth2(accessToken)
                .contentType("application/json")
                .body(requestBody.toString())
                .when()
                .post(instanceUrl + "/services/data/v48.0/ui-api/records");

        String id = response.then().extract().jsonPath().getString("id");
        if (id!=null) {
            Janitor.getInstance().addRecordForCleanup(id);
        }
    }

    public Response getLastResponse() {
        return response;
    }

    private Response requestOauth2Token() {

        return RestAssured.
                given()
                .contentType("multipart/form-data")
                .multiPart("username", userName)
                .multiPart("password", key)
                .multiPart("grant_type", "password")
                .multiPart("client_id", uiApiClientID)
                .multiPart("client_secret", uiApiClientSecret)
                .when()
                .post(uiApiAuthEndpoint);
    }

}
