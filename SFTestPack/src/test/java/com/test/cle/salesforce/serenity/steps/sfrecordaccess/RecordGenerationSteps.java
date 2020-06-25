package com.test.cle.salesforce.serenity.steps.sfrecordaccess;

import com.test.cle.salesforce.csv.CSVEntityDef;
import com.test.cle.salesforce.csv.CSVRegion;
import com.test.cle.salesforce.csv.CSVUserRole;
import com.test.cle.salesforce.testutils.Constants;
import com.test.cle.salesforce.yamlelements.security.RecordColumnHeader;
import com.test.cle.salesforce.yamlelements.security.RecordElements;
import net.thucydides.core.annotations.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.test.cle.salesforce.testutils.FileHandler.writeRowDataToFile;

public class RecordGenerationSteps {

    static Logger log = Logger.getLogger (MethodHandles.lookup ().lookupClass ().getName ());
    static String dataYaml = null;

    static boolean assignedToRegion = false;


    public static void setDataYaml(String file) {

        dataYaml = file;
    }

    @Step
    public static void writeBatchIdfile() throws IOException {

        Path currentRelativePath = Paths.get ("");
        String s = currentRelativePath.toAbsolutePath ().toString () + "/" + "TempFiles/batchId.txt";

        log.debug ("BatchId file " + s);
        try (FileWriter fw = new FileWriter (new File (s).getAbsoluteFile ())) {
            BufferedWriter bw = new BufferedWriter (fw);
            // Write to file
            bw.write (Constants.BATCH_ID_AUTO);
            bw.close ();
        }


    }

    @Step
    public static void doStartCSVGenerationProcess(
            String dataFilePath, String yamlFileWithCSVStructure, boolean assignedRegion) {

        assignedToRegion = assignedRegion;
        try {
            deleteTempFiles ();
            log.debug ("Set the data yaml to " + dataFilePath);
            setDataYaml (dataFilePath);
            log.debug (
                    "Received DataFile " + dataFilePath + " received csv file " + yamlFileWithCSVStructure);

            log.info ("Checking if yaml is serializable file: " + yamlFileWithCSVStructure);
            Path path = Paths.get (yamlFileWithCSVStructure);
            if (Files.notExists (path)) {
                Assert.fail (yamlFileWithCSVStructure + " does not exist");
            }
            Yaml yaml = new Yaml (new Constructor (CSVEntityDef.class));
            Iterable<Object> csvStructureObject;
            Iterable<Object> dataObjects;
            try (InputStream inputStream = new FileInputStream (new File (yamlFileWithCSVStructure))) {
                csvStructureObject = yaml.loadAll (inputStream);

                Assert.assertNotNull (yamlFileWithCSVStructure, csvStructureObject);

                yaml = new Yaml (new Constructor (RecordElements.class));
                try (InputStream inputStreamData = new FileInputStream (new File (dataFilePath))) {
                    dataObjects = yaml.loadAll (inputStreamData);
                    Assert.assertNotNull (dataFilePath, dataObjects);

                    for (Object object : csvStructureObject) {
                        CSVEntityDef csvDef = (CSVEntityDef) object;
                        log.debug ("Header=" + csvDef.getHeader ());
                        createCSVForEntity (dataFilePath, csvDef);
                    }
                }
            }
        } catch (IOException e) {
            log.error (e);
        }
    }


    public static void deleteTempFiles() {

        Path currentRelativePath = Paths.get ("");
        String s = currentRelativePath.toAbsolutePath ().toString () + "/" + "TempFiles";
        Path path = Paths.get (s);

        if (Files.exists (path)) {
            Arrays.stream (new File (s).listFiles ()).forEach (File::delete);
        }

        s = currentRelativePath.toAbsolutePath ().toString () + "/" + "Generated_yaml";
        path = Paths.get (s);

        if (Files.exists (path)) {
            Arrays.stream (new File (s).listFiles ()).forEach (File::delete);
        }
    }

    /** public static Iterable<Object> getCSVEntity(String yamlFile) throws FileNotFoundException {

     Yaml yaml = new Yaml(new Constructor(CSVEntityDef.class));

     InputStream inputStream = new FileInputStream(new File(yamlFile));

     return yaml.loadAll(inputStream);

     }**/

    /**public static Iterable<Object> getDataEntity(String yamlFile) throws FileNotFoundException {

     Yaml yaml = new Yaml(new Constructor(RecordElements.class));

     InputStream inputStream = new FileInputStream(new File(yamlFile));

     return yaml.loadAll(inputStream);

     }**/

    /**
     * Create a CSV for the particular CSV Entity
     */
    @Step
    public static void createCSVForEntity(String dataFilePath, CSVEntityDef csvEntity)
            throws IOException {

        List<CSVRegion> regions = csvEntity.getRegionList ();
        List<CSVUserRole> userRoles = csvEntity.getUserRoleList ();
        String outputDir = getCSVOutputDirectory ();
        String[] header = csvEntity.getHeader ().split (",");
        List<String> headerList = Arrays.asList (header);

        log.debug ("\r\n Processing CSV Request " + csvEntity.getCsv () + "\r\n");
        log.debug ("Value of regions = " + regions + " assign to region=" + assignedToRegion);

        writeRowDataToFile (
                outputDir,
                csvEntity.getCsv (),
                csvEntity.getHeader () + Constants.EXTRA_HEADER + "\r",
                false);
        log.debug ("header data written " + csvEntity.getHeader () + " to " + csvEntity.getCsv ());

        if (regions != null && assignedToRegion) {

            // if we are ignoring regions then do the following
            for (CSVRegion region : regions) {
                String targetLookup = csvEntity.getEtlEntity () + "." + region.getRegion ();
                log.debug ("The lookup target is " + targetLookup);

                String rowData = getRowDataForMatchingLookup (dataFilePath, targetLookup, headerList);

                writeRowDataToFile (outputDir, csvEntity.getCsv (), rowData, false);
            }
        } else {
            // we assume ignore regions
            if (userRoles != null) {
                for (CSVUserRole userRole : userRoles) {
                    String targetLookup = csvEntity.getEtlEntity () + "." + userRole.getUserRole ();
                    log.debug ("The lookup target is " + targetLookup);

                    String rowData = getRowDataForMatchingLookup (dataFilePath, targetLookup, headerList);

                    writeRowDataToFile (outputDir, csvEntity.getCsv (), rowData, false);
                }
            } else {
                log.warn ("\r\n NO ROLE DEFINED FOR " + csvEntity.getEtlEntity ());
            }
        }
    }

    private static String getCSVOutputDirectory() throws IOException {

        Path currentRelativePath = Paths.get ("");
        String s = currentRelativePath.toAbsolutePath ().toString () + "/" + "TempFiles";
        log.debug ("CSV Output Directory is: " + s);

        Path path = Paths.get (s);

        if (Files.notExists (path)) {
            log.info ("Target file \"" + s + "\" will be created.");

            Files.createDirectories (path);
        }

        return s;
    }

    public static String getRowDataForMatchingLookup(
            String dataFile, String targetLookup, List<String> structureHeaderList)
             {

        log.debug ("Data file = " + dataFile);
        Yaml yaml = new Yaml (new Constructor (RecordElements.class));
        StringBuilder value= new StringBuilder ("");
        try {
            try (InputStream inputStream = new FileInputStream (new File (dataFile))) {

                value = new StringBuilder ();
                for (Object object : yaml.loadAll (inputStream)) {

                    if (object instanceof RecordElements) {

                        RecordElements element = (RecordElements) object;

                        String lookupName = element.getLookupName ();
                        // log.debug("Comparing: " + lookupName + " with target " + targetLookup);
                        if (lookupName.equalsIgnoreCase (targetLookup)) {
                            // iterate through the list of column headers defined in the structure and compare them
                            // against
                            // properties defined in the
                            // columnHeaderList header settings
                            log.debug ("TargetLookup Found: " + lookupName);

                            value = new StringBuilder ();
                            for (String targetHeaderName : structureHeaderList) {
                                log.debug ("The header we will search for in the data file = " + targetHeaderName);
                                List<RecordColumnHeader> columnHeaders = element.getColumnHeaders ();
                                boolean targetFound = false;
                                for (RecordColumnHeader recordHeader : columnHeaders) {
                                    log.debug (
                                            "The headers in the data file for the target lookup "
                                                    + targetLookup
                                                    + " are "
                                                    + recordHeader.getHeader ());
                                    String[] headerSet = recordHeader.getHeader ().split ("=");
                                    log.debug ("Comparing " + targetHeaderName + " with " + headerSet[0].trim ());
                                    if (targetHeaderName.equalsIgnoreCase (headerSet[0].trim ())) {
                                        log.debug ("\r\n\r\n FOUND A MATCH " + targetHeaderName);
                                        value.append (headerSet[1].trim ()).append (",");
                                        targetFound = true;
                                        break;
                                    }
                                }
                                if (!targetFound) {
                                    log.error ("ERROR NO MATCH FOUND FOR" + targetHeaderName);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error (e);
        }
        value = new StringBuilder (value.toString ().replace ("$environment", System.getProperty ("target_salesforce_environment")));
        if (!value.toString ().equals ("")) {
            value.append ("1,2,sftestpack").append ("\r\n");
        }
        return value.toString ();
    }
}
