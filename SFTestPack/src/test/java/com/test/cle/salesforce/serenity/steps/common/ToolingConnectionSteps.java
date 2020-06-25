package com.test.cle.salesforce.serenity.steps.common;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.tooling.SoapConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.utils.Secret;
import net.thucydides.core.annotations.Step;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public class ToolingConnectionSteps {

    static Path resourceDirectory = Paths.get("src", "test", "resources");

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    static String traceFile = null;

    public static SoapConnection getToolingConnection(String entityName)
            throws ConnectionException, IOException {

        ConnectorConfig toolingConfig = new ConnectorConfig();
        PartnerConnection partnerConnection = getPartnerConnection();

        SoapConnection toolingConnection = null;
        com.sforce.soap.partner.LoginResult lr =
                partnerConnection.login(
                        partnerConnection.getConfig().getUsername(),
                        partnerConnection.getConfig().getPassword());
        toolingConfig.setServiceEndpoint(lr.getServerUrl().replace('u', 'T'));
        toolingConfig.setSessionId(lr.getSessionId());
        toolingConfig.setProxy("ilserverproxy", 8080);
        toolingConfig.setUsername(partnerConnection.getConfig().getUsername());
        toolingConfig.setPassword(partnerConnection.getConfig().getPassword());
        toolingConfig.setCompression(false);
        toolingConfig.setTraceMessage(true);

        // File f = new File(traceFile);
        //   if (f.exists()) f.delete();

        traceFile = entityName + ".txt";

        toolingConfig.setTraceFile(traceFile);
        toolingConfig.setPrettyPrintXml(true);

        try {
            toolingConnection = com.sforce.soap.tooling.Connector.newConnection(toolingConfig);

        } catch (ConnectionException e) {

            log.error(e);
        }
        return toolingConnection;
    }

    @Step
    public static PartnerConnection getPartnerConnection() throws ConnectionException, IOException {

        String environment = System.getProperty("target_salesforce_environment");
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

       // log.debug("userName = " + userName + " key=" + key);
        config.setAuthEndpoint(authEndPoint);

        config.setProxy(
                propertyFileSettings.getProperty("proxyServer"),
                Integer.parseInt(propertyFileSettings.getProperty("proxyPort")));

        return  new PartnerConnection(config);


    }

    public static Properties getPropertiesObject(String environment) throws IOException {

        String path = resourceDirectory.toAbsolutePath().toString();

        String file = path + "/" + environment + "_configuration" + ".properties";

        return com.test.cle.salesforce.testutils.LoadProperties.getProperties(file);
    }


    @Step
    public static Map doHandleResponse() {

        String xmlInput = getResponseEnvelope().trim();
        log.debug(xmlInput);
        Map map = new HashMap();

        try {

            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage message =
                    factory.createMessage(
                            new MimeHeaders(),
                            new ByteArrayInputStream(xmlInput.getBytes(StandardCharsets.UTF_8)));

            SOAPBody body = message.getSOAPBody();
            NodeList returnList = body.getChildNodes();

            for (int k = 0; k < returnList.getLength(); k++) {
                NodeList innerResultList = returnList.item(k).getChildNodes();
                for (int l = 0; l < innerResultList.getLength(); l++) {

                    if (innerResultList.item(l).getNodeName().equalsIgnoreCase(Constants.SOAP_RESULT)) {
                        // only one result
                        NodeList resultList = innerResultList.item(l).getChildNodes();

                        for (int x = 0; x < resultList.getLength(); x++) {

                            if (resultList.item(x).getNodeName().equals("records")) {

                                org.w3c.dom.Node item = resultList.item(x);
                                NodeList children = item.getChildNodes();
                                String id = "";
                                String developerName;
                                for (int y = 0; y < children.getLength(); y++) {
                                    org.w3c.dom.Node childNode = children.item(y);
                                    String childnodeName = childNode.getNodeName();

                                    if (childnodeName.equalsIgnoreCase(Constants.SOAP_ID)) {
                                        String childNodeValue = childNode.getTextContent().trim();

                                        id = childNodeValue;
                                    } else if (childnodeName.equalsIgnoreCase(Constants.SOAP_DEVELOPER_NAME)) {
                                        String childNodeValue = childNode.getTextContent().trim();

                                        developerName = childNodeValue;
                                        log.debug("DeveloperName/ID=" + developerName + " " + id);
                                        map.put(developerName, id);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (SOAPException | IOException e) {
            log.error(e);
        }

        return map;
    }

    public static String getResponseEnvelope() {

        String allContent = readLineByLine();

      return
                StringUtils.substringBetween(
                        allContent,
                        "------------ Response start ----------",
                        "------------ Response end   ----------");


    }

    private static String readLineByLine() {

        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(traceFile), StandardCharsets.UTF_8)) {

            stream.forEach(s -> contentBuilder.append(s).append("\n"));

        } catch (IOException e) {
            log.error(e);
        }
        return contentBuilder.toString();
    }
}
