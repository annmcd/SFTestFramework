package com.test.cle.salesforce.serenity.steps.sfetl;

import com.opencsv.CSVReader;
import com.test.cle.salesforce.testutils.Constants;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.test.cle.salesforce.testutils.Constants.CSV_FILE_ENCODING;
import static com.test.cle.salesforce.testutils.Constants.STEP_FAILED_IND;

public class CSVValidationSteps {

    private static Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());
    private static List<String[]> csvRecords;
    private static String csvFile;

    //Not currently used but leaving as it will be
    public static List<String[]> getCsvRecords() {

        return csvRecords;
    }

    public static void setCSVRecords(String filePath) {

        try (BufferedReader reader = new BufferedReader (new
                InputStreamReader (new FileInputStream (filePath), "cp1251"))) {

            CSVReader csvReader = new CSVReader (reader);
            // Read all rows at once
            List<String[]> records = csvReader.readAll ();
            if (records == null) log.debug (("Empty File" + filePath));
            csvRecords = records;
            csvFile = filePath;

        } catch (IOException e) {

            log.error (STEP_FAILED_IND + " Check File, it cannot be read using CSVReader csvReader = new CSVReader(reader)" + filePath, e);
            Assert.fail (" Check File, it cannot be read using CSVReader " + filePath);
        }
    }

    /**
     * Check if a file exists
     *
     * @param filePath Full path to file
     */
    public static boolean fileExists(String filePath) {

        return Files.exists (Paths.get (filePath));
    }

    @Step
    public static String getExportDirectory(String environment) {

        String csvExportDirectory = null;
        try {

            Path resourceDirectory = Paths.get ("src", "test", "resources");
            String path = resourceDirectory.toAbsolutePath ().toString ();

            log.debug ("Getting environment config file " + path);
            String sFile = path + "/" + environment + "_configuration" + ".properties";

            log.debug ("The file to load = " + sFile + " seeking property ExportDirectoryPath");

            csvExportDirectory =
                    com.test.cle.salesforce.testutils.LoadProperties.getProperties
                            (sFile)
                            .getProperty (Constants.CSV_EXPORT_DIRECTORY)
                            + File.separator
                            + environment
                            + File.separator
                            + "data";
            log.debug ("The CSV File Export Directory  = " + csvExportDirectory);

        } catch (IOException e) {
            log.error (STEP_FAILED_IND, e);
        }

        return csvExportDirectory;
    }

    @Step
    public static long getCSVRowCount(String file) {

        long l = 0;

        try (CSVReader reader = new CSVReader (new FileReader (file))) {

            while ((reader.readNext ()) != null) {
                l++;
            }
            log.debug ("Found " + l + " records including header in " + file);
        } catch (IOException e) {
            log.error (STEP_FAILED_IND, e);
        }
        return l - 1;
    }

    @Step
    public static boolean verifyEncoding(String csvFile) {
        //verify that we can read this file for the encoding specified
        boolean fileOk = false;

        File fileDir = new File (csvFile);

        try (BufferedReader in = new BufferedReader (new InputStreamReader (new FileInputStream (fileDir), CSV_FILE_ENCODING))) {

            String data = in.readLine ();
            if (data != null) {
                fileOk = true;
            }

        } catch (IOException ex) {
            log.error (STEP_FAILED_IND, ex);
        }
        return fileOk;
    }

    /**
     * Check records for leading and trailing spaces in row data
     */
    @Step
    public static void assertCSVHasNoLeadingTrailingSpaces() {

        boolean result = true;
        log.debug ("TestTrace:" + "checking for spaces \n");
        List<String> errorMessages = new ArrayList<>();
        int rowCount = 0;

        for (String[] row : csvRecords) {

            for (String cellValue : row) {
                if (cellValue.startsWith (" ") || cellValue.endsWith (" ")) {
                    String errorMessage = "ETLExport row:"
                            + (rowCount+1)
                            + " found leading/trailing spaces in cellvalue "
                            + cellValue;

                    log.error (errorMessage);
                    log.debug (errorMessage);
                    errorMessages.add(errorMessage);
                    result = false;

                }
            }
            rowCount++;
        }

        String readerFriendlyMessages = String.join("\n\n", errorMessages);
        Assert.assertTrue(readerFriendlyMessages, result);
    }

    public static void assertCSVHasNoRowTrailingComma() {

        boolean result = true;
        List<String> errorMessages = new ArrayList<>();

        int rowCount = 0;

        for (String[] row : csvRecords) {
            String rowValue = Arrays.toString (row);
            if (rowValue.endsWith (",]")) {

                String errorMessage = "TestError: row:" + (rowCount+1) + " has trailing comma";

                log.debug (errorMessage);
                errorMessages.add(errorMessage);
                result = false;
                break;
            }
            rowCount++;
        }

        String readerFriendlyMessages = String.join("\n\n", errorMessages);
        Assert.assertTrue(readerFriendlyMessages, result);
    }

}
