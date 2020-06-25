package com.test.cle.salesforce.serenity.steps.sfetl;

import com.test.cle.salesforce.testutils.Constants;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

final public class DBStructureSteps {

    private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    //private  ResultSet stagingRS = null;
  //  private  ResultSet warehouseRS = null;

    /**
     * Issue a query which returns a single value only based on a named value
     *
     * @param column       Column value to retrieve
     * @param query        Query to Execute
     * @param dbConnection Microsoft SQL Server DB Connection
     */
    @Step
    public static int getSingleIntValueFromQuery(
            String column, String query, Connection dbConnection) {

        int result = 0;
        try (PreparedStatement stmt = dbConnection.prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result = rs.getInt(column);
                }
            }
        } catch (SQLException e) {
            log.error(Constants.ERR_STEP_FAILED, e);
        }
        log.debug("Query Result for " + query + "=" + result + "\n");

        return result;
    }

    /**
     * auto close version Return the results Set for a given query
     *
     * @param query        Query to Execute
     * @param dbConnection Microsoft SQL Server DB Connection
     */
    @Step
    public static ResultSet getResultSetFromQuery1(String query, Connection dbConnection) {

        ResultSet rs = null;
        try (PreparedStatement stmt = dbConnection.prepareStatement(query)) {

            rs = stmt.executeQuery();

        } catch (SQLException e) {

            log.error(Constants.ERR_STEP_FAILED, e);
        }

        return rs;
    }

    public static ResultSet getResultSetFromQuery(String query, Connection dbConnection) {

        ResultSet rs = null;

        try (PreparedStatement stmt = dbConnection.prepareStatement(query)) {

            rs = stmt.executeQuery();

        } catch (SQLException e) {

            log.error(Constants.ERR_STEP_FAILED, e);
        }

        return rs;
    }


}
