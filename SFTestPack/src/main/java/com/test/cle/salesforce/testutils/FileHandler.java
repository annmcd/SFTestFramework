package com.test.cle.salesforce.testutils;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;

public class FileHandler {

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static void writeRowDataToFile (
            String directory, String fileName, String rowData, boolean deleteFile) throws IOException {
        BufferedWriter writer=null;
        try {
        if (deleteFile) {
            File f = new File(directory);
            boolean deleted =f.delete();
            if(deleted!=true) {
                throw new IOException("Failed to write Data to file " + directory);
            }
        }
        directory = directory + "/" + fileName;


           writer= new BufferedWriter
                    (new OutputStreamWriter(new FileOutputStream(directory,true), StandardCharsets.UTF_8));
            writer.append(rowData);
            writer.close();

        } catch (IOException e) {

            log.error("Error writing to file ", e);
        }finally{
            if(writer!=null ){
                writer.close();
            }
        }
    }


}
