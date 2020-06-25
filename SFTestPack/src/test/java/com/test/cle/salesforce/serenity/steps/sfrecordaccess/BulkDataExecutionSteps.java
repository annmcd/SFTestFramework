package com.test.cle.salesforce.serenity.steps.sfrecordaccess;

import com.test.cle.salesforce.testutils.Constants;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class BulkDataExecutionSteps {

   final private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    protected static String getTodaysDate() {

        DateFormat sdf = new SimpleDateFormat(Constants.FOLDER_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        return sdf.format(cal.getTime());
    }

    @Step
    public static int executeBulkData() throws IOException, InterruptedException {

        Path resourceDirectory = Paths.get("src", "test", "resources");
        String sfBulkDataYamlConfig =
                resourceDirectory.toAbsolutePath().toString()
                        + File.separator
                        + Constants.FOLDER_RECORD_ACCESS
                        + File.separator
                        + System.getProperty("target_salesforce_environment")
                        + Constants.CONFIG_FILE_SFBULKDATA;

        boolean bExists = new File(sfBulkDataYamlConfig).exists();
       log.debug("SFBulkData Yaml Configfile file exists = " + bExists);

        Assert.assertTrue(sfBulkDataYamlConfig, bExists);

        // this bulkdata jar includes the dependencies tell it the location of the log4j dir and pass it
        // the
        // salesforce environment
        String logDir = System.getProperty("Log4SFBulkDataDir");
        String env = System.getProperty("target_salesforce_environment");

        log.debug("About to trigger BulkData with config " + sfBulkDataYamlConfig);
        Runtime rt = Runtime.getRuntime();

        String command =
                "java -DLog4JDir="
                        + logDir
                        + " -Dtarget_salesforce_environment="
                        + env
                        + " -Dtodays_date="
                        + getTodaysDate()
                        + " -cp target/TempRuntime/SFBulkData.jar"
                        + " com.cle.crm.salesforce.sfbulkdata.core.SFBulkDataManager "
                        + sfBulkDataYamlConfig;

        log.debug("command line=" + command);
        Process pr = rt.exec(command);
        int status;
        log.debug("Triggered sFBulkData jar " + sfBulkDataYamlConfig);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()))) {
            String s = "";
            while ((s = in.readLine()) != null) {
                log.debug(s);
            }
             status = pr.waitFor();

            log.debug("SFBulkData exit status code=" + status);

            if (pr.isAlive()) {
                pr.destroyForcibly();
            }
    }


        return status;
    }

    /**
     * Execute main in a debug scenario only Need to mimic the commandline java
     * -Dlog4JDir=\\WintstAI1250\logs\dev -cp SFBulkData.jar;log4j.properties; com.cle.crm
     * .salesforce.sfbulkdata.core.SFBulkDataManager
     * C:\workspace\SFTestPack\src\test\resources\recordAccessDefs\dev_sfbulkdata_config.yaml
     *
     * <p>public static void main(String[] args) throws Exception{ //
     * System.setProperty("log4JDir","\\\\WintstAI1250\\logs\\dev"); //
     * System.setProperty("target_salesforce_environment","dev"); executeBulkData ();
     *
     * <p>}
     */
}
