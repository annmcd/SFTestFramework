package com.test.cle.salesforce.utils;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class RemoveDuplicateLines {

    public static void removeDuplicateLines(String inputFile) {
        String outputFile = inputFile+".filtered";
        BufferedWriter bw = null;
        BufferedReader objReader = null;
        try {
            String strCurrentLine;
            LinkedHashSet<String> hashset = new LinkedHashSet<String> ();
            objReader = new BufferedReader (new FileReader (inputFile));

            while ((strCurrentLine = objReader.readLine ()) != null) {

                if (strCurrentLine.contains ("ConsoleLoggingListener")) {
                    strCurrentLine = strCurrentLine.substring (20);
                    System.out.println (strCurrentLine);
                    hashset.add (strCurrentLine);
                } else {
                    hashset.add (strCurrentLine);
                }
            }
            File file = new File (outputFile);
            if (!file.exists ()) {
              boolean created= file.createNewFile ();
              if(created==false) System.out.println ("Error, could not create file " + outputFile);
            }
            FileWriter fw = new FileWriter (file);
            bw = new BufferedWriter (fw);
            Iterator<String> itr = hashset.iterator ();
            System.out.println ("Traversing over Set using Iterator");
            while (itr.hasNext ()) {
                bw.write (itr.next () + "\r\n");
            }
            System.out.println ("File written Successfully" + outputFile);
        } catch (IOException e) {

            e.printStackTrace ();

        } finally {

            try {
                if (bw != null) {
                    bw.close ();
                }
                if (objReader != null)
                    objReader.close ();
            } catch (IOException ex) {
                ex.printStackTrace ();
            }
        }
    }


    public static void main(String[] args) {

        String inputFile = args[0];

        System.out.println("file received = " + inputFile);
        File f = new File(inputFile.trim ());
        if(!f.exists ()) {
            System.out.println ("File does not exist" + inputFile);
        }


        removeDuplicateLines (inputFile);

    }
}