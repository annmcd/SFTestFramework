package com.test.cle.salesforce.serenity.steps.common;

import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionSteps {

    private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Step
    public static Connection getDBConnection(String environment, String propertyName) {

        Connection conn = null;
        try {
            Path resourceDirectory = Paths.get("src", "test", "resources");
            String path = resourceDirectory.toAbsolutePath().toString();
            String sFile = path + "/" + environment + "_configuration" + ".properties";

            log.debug("The file to load = " + sFile + " seeking property " + propertyName);

            String url =
                    com.test.cle.salesforce.testutils.LoadProperties.getProperties(sFile)
                            .getProperty(propertyName);
            log.debug("The connection url = " + url);
            conn = com.test.cle.salesforce.testutils.SystemConnection.getDbConnection(url);

        } catch (Exception e) {
            log.error("step failed! getResultsSet {}", e);
        }

        return conn;
    }

    @Step
    public void closeConnection(Connection conn) throws SQLException {

        if (conn != null) {
            conn.close();
        }
    }
}
