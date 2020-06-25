package com.test.cle.salesforce.serenity.steps.sfsbusinessprocess;

import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.testutils.LoadProperties;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;

public class ApexSteps {

    private static String user;
    private static String clientId;
    private static String loginURL;
    private static String loginCmd;
    final private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static String env;

    @Step
    public static void setup() {

        env = System.getProperty("target_salesforce_environment");
        user = Constants.SFDX_USER + "." + env;

        clientId = LoadProperties.getEnvConfigProperty(env, "sfdx_client_id");
        Assert.assertNotNull("sfdx_clientId specified?", clientId);

        loginURL = LoadProperties.getEnvConfigProperty(env, "sfdx_login_url");
    }


    @Step
    public static boolean authoriseOrg() {
        boolean authorised;
        loginCmd =
                "sfdx force:auth:jwt:grant --clientid "
                        + clientId
                        + " --jwtkeyfile "
                        + Constants.SFDX_PRIVATE_KEY
                        + " --username "
                        + user
                        + " --instanceurl "
                        + loginURL +
                        " --setdefaultusername -a " + env + "sandbox";

        log.debug("loginCmd=" + loginCmd);

        authorised = runCmd(loginCmd);
        //  String cmd2="sfdx force:config:set instanceUrl=https://cle--" + env+".my.salesforce.com --global";

        //  log.debug("setinstance=" + cmd2);

        // runCmd(cmd2);

        return authorised;
    }

    public static boolean runCmd(String command) {

        boolean ok = false;
        try {
            StringBuilder output = new StringBuilder();
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/C", command);
            Process process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append("dxResponse ").append(line).append("\n");
                }
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                log.debug("sfdx " + output);

            } else {

                log.error("error executing cmd " + command);
            }
            if (output.toString().toLowerCase().contains("success")) {
                ok = true;
                log.debug("success dx cmd " + command);
            }

        } catch (IOException e) {
            log.error(e);
        } catch (InterruptedException e) {
            log.error(e);
        }
        return ok;
    }

    public static void main(String[] args) {

        authoriseOrg();
    }

    @Step
    public boolean runAllTests() {

        String testCmd =
                "sfdx force:apex:test:run -u "
                        + env + "sandbox";
        log.debug("testCmd" + testCmd);


        return runCmd(testCmd);

    }
}
