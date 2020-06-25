package com.test.cle.salesforce.serenity.steps.sfpermissionsmanager;

import net.thucydides.core.annotations.Step;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserManagementSteps {

    @Step
    public int triggerSFPermissionsManager(
            String propertiesFile,
            String spreadsheet,
            boolean synchUsers,
            boolean synchForecasts,
            boolean updateEmail)
            throws IOException, InterruptedException {

        System.out.println(
                "About to trigger SFPermissionsManager with config spreadsheet " + spreadsheet);
        String paramList =
                propertiesFile
                        + " "
                        + spreadsheet
                        + " "
                        + synchUsers
                        + " "
                        + synchForecasts
                        + " "
                        + updateEmail;

        ProcessBuilder pb =
                new ProcessBuilder(
                        "java", "-jar", "target/TempRuntime/SFPermissionsManager.jar", paramList);
        Process p = pb.start();
        System.out.println("Triggered SFPermissionsManager with param list " + paramList);
        int status;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            String s = "";
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }
             status = p.waitFor();
            System.out.println("SFPermissionsManager Exited status: " + status);
        }


        return status;
    }
}
