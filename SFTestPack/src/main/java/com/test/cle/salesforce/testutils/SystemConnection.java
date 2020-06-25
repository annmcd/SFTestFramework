package com.test.cle.salesforce.testutils;

import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;

public class SystemConnection {

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private SystemConnection() {

        throw new IllegalStateException("Static class");
    }

    /**
     * Return an sql connection for a given Database URL (Microsoft)
     */
    public static Connection getDbConnection(String connectionURL) {

        java.sql.Connection connection = null;
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();

            connection = DriverManager.getConnection(connectionURL);
            log.debug("Connection Catalog returned " + connection.getCatalog());

        } catch (Exception e) {

            log.error("step failed! {}", e);
        }

        return connection;
    }
}
