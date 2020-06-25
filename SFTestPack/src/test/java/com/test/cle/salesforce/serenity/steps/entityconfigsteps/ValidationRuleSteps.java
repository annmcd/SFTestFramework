package com.test.cle.salesforce.serenity.steps.entityconfigsteps;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.metadata.*;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.testutils.Constants;
import net.thucydides.core.annotations.Step;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static com.test.cle.salesforce.testutils.LoadProperties.getProperties;

public class ValidationRuleSteps {

    static EnterpriseConnection enterpriseConnection;
    static MetadataConnection metadataConnection;
    static com.sforce.soap.metadata.ValidationRule validationRule;
    static String fullRuleName;

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Step
    public static void ruleExists(
            EnterpriseConnection eConn, MetadataConnection mConn, String ruleName, String entityName)
            throws ConnectionException {

        enterpriseConnection = eConn;
        metadataConnection = mConn;

        // Get the full rule name as we need it to verify what the rule expects to identify the correct
        // validation rule
        ListMetadataQuery query = new ListMetadataQuery();
        query.setType("ValidationRule");
        FileProperties[] lmr =
                metadataConnection.listMetadata(new ListMetadataQuery[]{query}, Constants.API_VERSION);
        if (lmr != null) {
            for (FileProperties n : lmr) {
                log.debug("ValidationRules: fullName: " + n.getFullName());
            }
        }

        // User.Department_should_be_from_specific_list"
        fullRuleName = entityName + "." + ruleName;
        ReadResult readResult =
                metadataConnection.readMetadata("ValidationRule", new String[]{fullRuleName});

        Metadata[] metadata = readResult.getRecords();
        boolean bFound = false;

        for (Metadata md : metadata) {
            if (md != null) {
                validationRule = (com.sforce.soap.metadata.ValidationRule) md;
                log.debug("Comparing: " + fullRuleName + " with " + validationRule.getFullName());

                if (validationRule.getFullName().equalsIgnoreCase(fullRuleName)) {
                    bFound = true;
                    log.info(entityName + "\r\n formula= " + validationRule.getErrorConditionFormula());
                    break;
                }
            }
        }

        if (!bFound) {
            log.error(Constants.VR_RULE_MISSING + " " + fullRuleName);
        }
        Assert.assertTrue("Rule Exists Check: for Rule: " + fullRuleName, bFound);
    }

    @Step
    public static void isErrorConditionFormulaOK(String propertiesFileName) {

        // Lookup properties file to get the value of the key i.e the formula field
        // ruleFullName is the key
        Path resourceDirectory = Paths.get("src", "test", "resources");
        String filePath = resourceDirectory + "/entitydefs/" + propertiesFileName;
        try {
            Properties props = getProperties(filePath);
            String expFormula = props.getProperty(fullRuleName).trim ();

            log.debug(
                    "**************Comparing ErrorConditionFormula on "
                            + fullRuleName
                            + " ******************");

            String validationRuleFormula = validationRule.getErrorConditionFormula().trim();

            log.debug("\r\n\r\n" + expFormula + "\r\n\r\n vs actual\r\n\r\n " + validationRuleFormula);
            int exLen = expFormula.trim ().length();
            int actLen = validationRule.getErrorConditionFormula().length();

            if (exLen != actLen) {
                log.error(
                        "expected: "+ expFormula +"\r\n" +
                        "actual:   "  + validationRuleFormula+"\r\n"    +
                        "Expected string length = " + exLen + " actual salesforce formula length=" + actLen);
            }
            if (expFormula.equals(validationRuleFormula)) {
                log.debug(
                        "ErrorConditionFormula OK for "
                                + fullRuleName
                                + "\r\n actual salesforce value = "
                                + validationRuleFormula
                                + " \r\n");
            } else {
                log.error(
                        "***********Validation Rule Error "
                                + fullRuleName
                                + "******************************\r\n");
                log.error(
                        Constants.VR_FORMULA_MISMATCH
                                + " "
                                + fullRuleName
                                + "\r\n Error condition formula not matching expected =\r\n"
                                + expFormula
                                + "\r\n actual\r\n"
                                + validationRuleFormula);

                String difference = StringUtils.difference(expFormula, validationRuleFormula);
                log.error("ErrorContionFormula NotOK \r\n\r\n Difference= " + difference);
            }


            Assert.assertEquals("ErrorConditionFormula same length check: ", exLen, actLen);

            Assert.assertEquals(
                    "ErrorConditionFormula " + fullRuleName,
                    expFormula,
                    validationRule.getErrorConditionFormula());

        } catch (IOException ex) {
            Assert.fail("IO Exception occured " + filePath);
            log.error(ex);
        }
    }

    @Step
    public static void isErrorConditionMessageOk(String errorMessage) {

        if (!validationRule.getErrorMessage().equalsIgnoreCase(errorMessage)) {
            log.error(
                    fullRuleName
                            + Constants.VR_ERROR_MSG_INVALID
                            + " expected ="
                            + errorMessage
                            + "\r\n actual=\r\n "
                            + validationRule.getErrorMessage());
        }
        Assert.assertEquals(
                Constants.VR_ERROR_MSG_INVALID + fullRuleName,
                errorMessage,
                validationRule.getErrorMessage());
    }

    @Step
    public static void isActiveState(boolean activeState) {

        Assert.assertEquals(
                Constants.VR_ERROR_STATE + " " + fullRuleName, activeState, validationRule.getActive());
    }
}
