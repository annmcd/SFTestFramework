package com.test.cle.salesforce.serenity.stepdefinitions.sfsecurity;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;
import com.test.cle.salesforce.serenity.steps.common.SalesforceConnectionSteps;
import com.test.cle.salesforce.serenity.steps.sfsecurity.VerifyGroups;
import com.test.cle.salesforce.serenity.steps.sfsecurity.VerifyPermissionSet;
import com.test.cle.salesforce.testutils.Constants;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import static com.test.cle.salesforce.testutils.Constants.TARGET_ORG;

public class GroupStepfDefs {

    private static String environment = System.getProperty (TARGET_ORG);
    @Steps
    private static SalesforceConnectionSteps salesforceConnectionSteps = null;


    private Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());
    private EnterpriseConnection enterpriseConnection = null;
    private String queueName;
    private String memberGroup;
    private ArrayList<String> membersList = new ArrayList ();
    private String groupDeveloperName;
    private String groupType;
    StringBuffer failures = new StringBuffer ();

    @Given("^I know that queue \"([^\"]*)\" should have as a member \"([^\"]*)\"$")
    public void i_know_that_queue_should_have_as_a_member(String queue, String group) {

        queueName = queue;
        memberGroup = group;


        enterpriseConnection = salesforceConnectionSteps.getSalesforceEnterpriseConnection (environment);
        Assert.assertNotNull (Constants.ENT_CON_CHECK, enterpriseConnection);
        log.debug ("Connected to salesforce org " + environment);
    }

    @When("^I check queue membership$")
    public void i_check_queue_membership() throws ConnectionException {

        membersList =
                com.test.cle.salesforce.serenity.steps.sfpermissionsmanager.SalesforceJobSettingsSteps
                        .getGroupGroupMembers (enterpriseConnection, queueName);
        if(membersList.size ()==0) failures.append(queueName + " does not have members");

        //Assert.assertTrue (queueName + " Has Members?", membersList.size () > 0);
    }




    @Given("^I have connect to salesforce to verify groups$")
    public void i_have_connect_to_salesforce_to_verify_groups() {
        enterpriseConnection = salesforceConnectionSteps.getSalesforceEnterpriseConnection (environment);
        Assert.assertNotNull (Constants.ENT_CON_CHECK, enterpriseConnection);
    }



    @When("^I check existencce of group \"([^\"]*)\" of type \"([^\"]*)\"$")
    public void i_check_existencce_of_group_of_type(String groupName, String type) {
        groupDeveloperName = groupName;
        groupType = type;

        boolean result = VerifyGroups.checkGroupExists (enterpriseConnection, groupName, type);
        if (!result) {
           failures.append (Constants.MISSING_GROUP + "  DeveloperName: " + groupDeveloperName + ", Type: " + groupType +Constants.CR);
        }

    }

    //group check feature
    @Then("^The group should exist$")
    public void then_the_group_should_exist() {


        boolean result = VerifyGroups.checkGroupExists (enterpriseConnection, groupDeveloperName, groupType);
        if(!result){
            failures.append (Constants.MISSING_GROUP + "  DeveloperName: " + groupDeveloperName + ", Type: " + groupType +Constants.CR);
        }
        VerifyGroups.assertFailures (failures);
    }

    //queue membership check feature
    @Then("^Then the group should be a member of the queue$")
    public void then_the_group_should_be_a_member_of_the_queue() {

        Assert.assertTrue (
                memberGroup + " Is a member of queue " + queueName,
                membersList.contains (memberGroup));


        if(!membersList.contains (memberGroup)){

            failures.append ("Membership of queue " + queueName + " expected:" + memberGroup + Constants.CR);
        }
        VerifyGroups.assertFailures (failures);
    }


    @After
    public void logout() {
        salesforceConnectionSteps.logoutEnterpriseConnection (enterpriseConnection);

    }
}
