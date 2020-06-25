package com.test.cle.salesforce.serenity.steps.sfetl;

import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.testutils.SystemConnection;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;

public class ETLPreExportVerificationSteps {

  private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

  @Step
  public static Connection getDBConnection() throws IOException {

    String environment = System.getProperty("target_salesforce_environment");


    log.debug("Environment=" + environment);
    Path resourceDirectory = Paths.get("src", "test", "resources");
    String path = resourceDirectory.toAbsolutePath().toString();

    log.debug("Path=" + path);
    String sFile = path + "/" + environment + "_configuration" + ".properties";

    log.debug("The file to load = " + sFile + " seeking property " + Constants.STAGING_DB_URL);

    String url;
    url = com.test.cle.salesforce.testutils.LoadProperties.getProperties (sFile)
            .getProperty (Constants.STAGING_DB_URL);
    log.debug("The staging url = " + url);
    return SystemConnection.getDbConnection(url);
  }

  // assume the proc takes no params and return an integer value
  @Step
  public static int executeStoredProcedure(String testType, Connection con, String proc) {

    int procStatus = -1;
    try (CallableStatement cstmt = con.prepareCall("{? = call " + proc + " }"); ) {
      cstmt.registerOutParameter(1, java.sql.Types.INTEGER);

      cstmt.execute();
      procStatus = cstmt.getInt(1);
      log.debug(testType + " " + proc + " return code " + procStatus);
    } catch (Exception e) {
      log.error(testType + " " + proc, e);
    }
    return procStatus;
  }
}
