package com.test.cle.salesforce.serenity.steps.sfbulkdata;

import com.test.cle.salesforce.testutils.Constants;
import net.thucydides.core.annotations.Step;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BulkDataResponses {

    /**
     * Test all of the exit codes from the Feature File. Based on the yaml file passed in we expected
     * a varying set of results Dependencies, SFBulkData version to be retrieved from maven VSTS repo
     * and not from included package Dependencies to be copied using resources plugin to a
     * "testruntime" folder in the currentDirectory
     */
    @Step
    public int triggerBulkData(String sfBulkDataYamlConfig, String log4jDir)
            throws IOException, InterruptedException {

        System.out.println(
                "About to trigger BulkData with config " + sfBulkDataYamlConfig + "log4jDir " + log4jDir);

        ProcessBuilder pb =
                new ProcessBuilder(
                        "java ",
                        " -Dlog4JDir=" + log4jDir,
                        "-jar",
                        "target/TempRuntime/SFBulkData.jar",
                        sfBulkDataYamlConfig);
        Process p = pb.start();
        System.out.println("Triggered sFBulkData" + sfBulkDataYamlConfig);
        int status;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream(),Constants.CSV_FILE_ENCODING))) {
            String s = "";
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }
            status = p.waitFor();
            System.out.println("SFBulkData Exited status: " + status);
        }


        return status;
    }
}
