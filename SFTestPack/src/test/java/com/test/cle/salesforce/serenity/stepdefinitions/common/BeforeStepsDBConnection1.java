package com.test.cle.salesforce.serenity.stepdefinitions.common;

import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.testutils.SystemConnection;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

public class BeforeStepsDBConnection1 {

    final private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private static Connection connectionStaging;


    public static void get_connection_forEnv(String environment)  throws IOException{

        log.debug("Environment=" + environment);

        Path resourceDirectory = Paths.get("src", "test", "resources");
        String path = resourceDirectory.toAbsolutePath().toString();

        log.debug("Path=" + path);
        String sFile = path + "/" + environment + "_configuration" + ".properties";

        log.debug("The file to load = " + sFile + " seeking property " + Constants.STAGING_DB_URL);

        String url =
                com.test.cle.salesforce.testutils.LoadProperties.getProperties(sFile)
                        .getProperty(Constants.STAGING_DB_URL);
        log.debug("The staging url = " + url);
        connectionStaging = SystemConnection.getDbConnection(url);
    }

    public static Connection getStagingConnection() {

        return connectionStaging;
    }
}
