package com.test.cle.salesforce.yamlverification;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


//ToDo Incomplete
@SuppressWarnings("PMD")
public class YamlVerificationImpl {

    public static class BatchVerifierImpl implements BatchVerifierInterface {

        public boolean fileExists(String yamlfile) {

            Path path = Paths.get(yamlfile);

            return Files.exists(path);
        }

        public boolean serialialize(String entityYamlFile, String entityType) throws IOException {
            InputStream inputStream=null;
            boolean result = false;
            try {
                Yaml yaml = new Yaml(new Constructor(Class.forName(entityType)));

                 inputStream = new FileInputStream(new File(entityYamlFile));

                yaml.load(inputStream);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if(inputStream!=null){
                    inputStream.close();
                }
            }
            System.out.println("Serialization result=" + result);
            return result;
        }
    }
}
