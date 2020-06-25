package com.test.cle.salesforce.serenity.stepdefinitions.sfentityconfig;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.stepdefinitions.common.BaseDefinition;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.entityconfigsteps.RoleHierarchySteps;
import com.test.cle.salesforce.testutils.Constants;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.After;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class VerifyRoleHierarchy extends BaseDefinition {

    static String environment = System.getProperty("target_salesforce_environment");
    static EnterpriseConnection enterpriseConnection = null;
    static List<String> roles = null;
    static String actualRoles = null;

    @Steps
    static SalesforceConnectionSteps salesforceConnectionSteps = null;
   // static RoleHierarchySteps roleHierarchySteps = null;

    @Given(
            "^I  have a connection to salesforce Org in a given environment and Roles have been deployed$")
    public void
    i_have_a_connection_to_salesforce_Org_in_a_given_environment_and_Roles_have_been_deployed() {

        enterpriseConnection =
                salesforceConnectionSteps.getSalesforceEnterpriseConnection((environment));

        Assert.assertNotNull(Constants.ENT_CON_CHECK,enterpriseConnection);
    }

    @When(
            "^I query the role hierarchy against the levels LevelOne  \"([^\"]*)\" and LevelTwo \"([^\"]*)\" LevelThree "
                    + "\"([^\"]*)\" and LevelFour \"([^\"]*)\"$")
    public void
    i_query_the_role_hierarchy_against_the_levels_LevelOne_and_LevelTwo_LevelThree_and_LevelFour(
            String levelOne, String levelTwo, String levelThree, String levelFour)
            throws ConnectionException {

        roles = new ArrayList();
        if (!levelFour.trim().equalsIgnoreCase("")) {
            roles.add(levelFour);
        }
        if (!levelThree.trim().equalsIgnoreCase("")) {
            roles.add(levelThree);
        }
        if (!levelTwo.trim().equalsIgnoreCase("")) {
            roles.add(levelTwo);
        }
        if (!levelOne.trim().equalsIgnoreCase("")) {
            roles.add(levelOne);
        }

        actualRoles = RoleHierarchySteps.processRoles(roles, enterpriseConnection);
        roles.remove(0);
    }

    @Then("^I expect the results to match the hierarchy defined$")
    public void i_expect_the_results_to_match_the_hierarchy_defined() {

        if(!roles.toString().equals ( actualRoles)){
            log.error ("Expected Roles: " +  roles.toString () + " Actual Roles: " + actualRoles);
        }
        Assert.assertEquals(roles.toString(), actualRoles);
    }

    @After
    public void logout() {
        salesforceConnectionSteps.logoutEnterpriseConnection (enterpriseConnection);

    }
}
