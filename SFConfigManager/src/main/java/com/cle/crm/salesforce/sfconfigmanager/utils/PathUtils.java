package com.cle.crm.salesforce.sfconfigmanager.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtils {


    public static String getMainResourcesDirectory() {
        Path resourceDirectory;
        String userDirectory = Paths.get ("")
                .toAbsolutePath ()
                .toString ();

        if (userDirectory.endsWith ("SFTestFramework")) {
            resourceDirectory = Paths.get (Const.MODULE_NAME, "src", "main", "resources");

        } else {
            resourceDirectory = Paths.get ("src", "main", "resources");

        }

        return resourceDirectory.toAbsolutePath ().toString ();
    }


    public static String getTestResourcesDirectory() {
        Path resourceDirectory;
        String userDirectory = Paths.get ("")
                .toAbsolutePath ()
                .toString ();

        if (userDirectory.endsWith ("SFTestFramework")) {
            resourceDirectory = Paths.get (Const.MODULE_NAME, "src", "test", "resources");

        } else {
            resourceDirectory = Paths.get ("src", "test", "resources");

        }

        return resourceDirectory.toString ();
    }


    public boolean fileExists(String yamlfile) {

        Path path = Paths.get (yamlfile);

        return Files.exists (path);
    }

}
