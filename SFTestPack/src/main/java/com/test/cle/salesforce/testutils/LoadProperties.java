package com.test.cle.salesforce.testutils;

import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class LoadProperties {

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private LoadProperties() {

        throw new IllegalStateException("Utility class");
    }

    public static String getEnvConfigProperty(String environment, String propertyName)  {
       String propertyValue=null;
       try {
           Path resourceDirectory = Paths.get("src", "test", "resources");

           String path = resourceDirectory.toAbsolutePath().toString();

           log.debug("Path=" + path);
           String sFile = path + "/" + environment + "_configuration" + ".properties";

           log.debug("Findfile = " + sFile + " seeking property " + propertyName);

           propertyValue =
                   com.test.cle.salesforce.testutils.LoadProperties.getProperties(sFile)
                           .getProperty(propertyName.trim());
           log.debug("property value = " + propertyValue);
       } catch(IOException e){
            log.error (propertyName , e);
           }
        return propertyValue;
    }

    /**
     * } Get properties object from a properties file
     */
    public static Properties getProperties(String filePath) throws IOException {

        Properties prop = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream(filePath);
            prop.load(input);

        } catch (Exception e) {
            log.error("step failed! getResultsSet {}", e);
        } finally {
            if (input != null) {
                input.close();
            }
        }

        return prop;
    }


    public static String getPropertiesFileNameForEnvironment(String env) {
        Path resourceDirectory = Paths.get("src", "test", "resources");
        String path = resourceDirectory.toAbsolutePath().toString();
        return path + "/" + env + "_configuration" + ".properties";
    }

    public static final String getTestResourcesDirectory() {
        Path resourceDirectory = Paths.get("src", "test", "resources");
        return resourceDirectory.toAbsolutePath().toString();
    }


    public static boolean fileExists(String yamlfile) {

        Path path = Paths.get (yamlfile);

        return Files.exists (path);
    }
}
