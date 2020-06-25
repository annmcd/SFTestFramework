package com.test.cle.salesforce.discovery;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.tooling.SessionHeader_element;
import com.sforce.soap.tooling.SoapConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.xml.soap.SOAPException;

public class ToolingConnectionTest {

    static String traceFile = "trace.xml";


    public static void main(String[] args)  {
        System.out.println("start");
        getToolingConnection();
        System.out.println("End");
    }

    public static void getToolingConnection()  {
        try {
        ConnectorConfig toolingConfig = new ConnectorConfig();
        PartnerConnection partnerConnection = getPartnerConnection();

        com.sforce.soap.tooling.QueryResult result = null;
        SessionHeader_element element = null;

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
        toolingConfig.setProxy("ilserverproxy", 8080);
        toolingConfig.setTraceMessage(true);

        File f = new File(traceFile);
       boolean deleted = f.delete();
       if (deleted==false){
           System.out.println("warning file not deleted  "+ traceFile);
       }

        toolingConfig.setTraceFile(traceFile);
        toolingConfig.setPrettyPrintXml(true);


            toolingConnection = com.sforce.soap.tooling.Connector.newConnection(toolingConfig);
            String soql = "Select Id, DeveloperName from CustomField where TableEnumOrId = 'Lead'";
            toolingConnection.query(soql);

        } catch (RuntimeException e) {
            // it will fault

            doHandleResponse();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }
    }

    // @Step
    public static PartnerConnection getPartnerConnection() throws ConnectionException, IOException {

        String authEndPoint = "https://test.salesforce.com/services/Soap/u/40.0";
        ConnectorConfig config = new ConnectorConfig();
        config.setUsername("integration.user@canadalife.de.dev");
        config.setPassword("Testing2019_11qC0oK0SyoE5BByNwr8DfUI5eE");
        config.setAuthEndpoint(authEndPoint);
        config.setProxy("ilserverproxy", 8080);
        PartnerConnection pConnection = new PartnerConnection(config);

        return pConnection;
    }

    private static Map doHandleResponse() {

        String xmlInput = getResponseEnvelope().trim();
        System.out.println(xmlInput);
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
                    //  String nodeName = innerResultList.item(l).getNodeName();
                    if (innerResultList.item(l).getNodeName().equalsIgnoreCase("result")) {
                        // only one result
                        NodeList resultList = innerResultList.item(l).getChildNodes();

                        for (int x = 0; x < resultList.getLength(); x++) {

                            if (resultList.item(x).getNodeName().equals("records")) {
                                //  System.out.println("Found Records");
                                org.w3c.dom.Node item = resultList.item(x);
                                NodeList children = item.getChildNodes();
                                String id = "";
                                String developerName;
                                for (int y = 0; y < children.getLength(); y++) {
                                    org.w3c.dom.Node childNode = children.item(y);
                                    String childnodeName = childNode.getNodeName();

                                    if (childnodeName.equalsIgnoreCase("sf:id")) {
                                        String childNodeValue = childNode.getTextContent().trim();
                                        ;

                                        id = childNodeValue;
                                    } else if (childnodeName.equalsIgnoreCase("sf:DeveloperName")) {
                                        String childNodeValue = childNode.getTextContent().trim();
                                        ;

                                        developerName = childNodeValue;
                                        System.out.println(developerName + " " + id);
                                        map.put(developerName, id);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static String getResponseEnvelope() {

        String allContent = readLineByLine();

        String content =
                StringUtils.substringBetween(
                        allContent,
                        "------------ Response start ----------",
                        "------------ Response end   ----------");

        return content;
    }

    private static String readLineByLine() {

        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(traceFile), StandardCharsets.UTF_8)) {

            stream.forEach(s -> contentBuilder.append(s).append("\n"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
