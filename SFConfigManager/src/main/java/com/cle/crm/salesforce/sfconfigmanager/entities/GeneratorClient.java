package com.cle.crm.salesforce.sfconfigmanager.entities;

import com.cle.crm.salesforce.sfconfigmanager.utils.PathUtils;
import com.test.cle.salesforce.yamlelements.security.EntityDef;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.cle.crm.salesforce.sfconfigmanager.entities.EntityGeneratorImpl.log;

public class GeneratorClient {

    //comma separated list of entity types expected
    public static void main(String[] args) {

        EntityGeneratorImpl impl = new EntityGeneratorImpl();

        for (String arg : args) {
            System.out.println(arg);
        }
        String result = String.join(",", args).toLowerCase();
        System.out.println("list=" + result.toLowerCase());

        impl.doInit(result);

        List<String> tabs = impl.getTabList();

        tabs.forEach(tabName -> {

            System.out.println("Generate files for=> " + tabName);

            String entityFile = impl.doGenerateEntityYamlFile(tabName.trim().toLowerCase());

            if (Files.exists(Paths.get(entityFile))) {
                EntityDef def = impl.doSerialise(entityFile);
                log.debug("Serialised " + def.getName());
            } else {
                log.error(entityFile + " not serialised");
                try {
                    throw new Error(entityFile + " not serialised");
                } catch (Exception e) {
                    log.error(e);
                }

            }

        });

    }
}
